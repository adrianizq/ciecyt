package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import co.edu.itp.ciecyt.repository.ImpactosEsperadosRepository;
import co.edu.itp.ciecyt.service.ImpactosEsperadosService;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import co.edu.itp.ciecyt.service.mapper.ImpactosEsperadosMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ImpactosEsperadosResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ImpactosEsperadosResourceIT {

    private static final String DEFAULT_IMPACTO = "AAAAAAAAAA";
    private static final String UPDATED_IMPACTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_PLAZO = 1;
    private static final Integer UPDATED_PLAZO = 2;

    private static final String DEFAULT_INDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_INDICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_SUPUESTOS = "AAAAAAAAAA";
    private static final String UPDATED_SUPUESTOS = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEN_VISTA = 1;
    private static final Integer UPDATED_ORDEN_VISTA = 2;

    @Autowired
    private ImpactosEsperadosRepository impactosEsperadosRepository;

    @Autowired
    private ImpactosEsperadosMapper impactosEsperadosMapper;

    @Autowired
    private ImpactosEsperadosService impactosEsperadosService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImpactosEsperadosMockMvc;

    private ImpactosEsperados impactosEsperados;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpactosEsperados createEntity(EntityManager em) {
        ImpactosEsperados impactosEsperados = new ImpactosEsperados()
            .impacto(DEFAULT_IMPACTO)
            .plazo(DEFAULT_PLAZO)
            .indicador(DEFAULT_INDICADOR)
            .supuestos(DEFAULT_SUPUESTOS)
            .ordenVista(DEFAULT_ORDEN_VISTA);
        return impactosEsperados;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImpactosEsperados createUpdatedEntity(EntityManager em) {
        ImpactosEsperados impactosEsperados = new ImpactosEsperados()
            .impacto(UPDATED_IMPACTO)
            .plazo(UPDATED_PLAZO)
            .indicador(UPDATED_INDICADOR)
            .supuestos(UPDATED_SUPUESTOS)
            .ordenVista(UPDATED_ORDEN_VISTA);
        return impactosEsperados;
    }

    @BeforeEach
    public void initTest() {
        impactosEsperados = createEntity(em);
    }

    @Test
    @Transactional
    public void createImpactosEsperados() throws Exception {
        int databaseSizeBeforeCreate = impactosEsperadosRepository.findAll().size();
        // Create the ImpactosEsperados
        ImpactosEsperadosDTO impactosEsperadosDTO = impactosEsperadosMapper.toDto(impactosEsperados);
        restImpactosEsperadosMockMvc.perform(post("/api/impactos-esperados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(impactosEsperadosDTO)))
            .andExpect(status().isCreated());

        // Validate the ImpactosEsperados in the database
        List<ImpactosEsperados> impactosEsperadosList = impactosEsperadosRepository.findAll();
        assertThat(impactosEsperadosList).hasSize(databaseSizeBeforeCreate + 1);
        ImpactosEsperados testImpactosEsperados = impactosEsperadosList.get(impactosEsperadosList.size() - 1);
        assertThat(testImpactosEsperados.getImpacto()).isEqualTo(DEFAULT_IMPACTO);
        assertThat(testImpactosEsperados.getPlazo()).isEqualTo(DEFAULT_PLAZO);
        assertThat(testImpactosEsperados.getIndicador()).isEqualTo(DEFAULT_INDICADOR);
        assertThat(testImpactosEsperados.getSupuestos()).isEqualTo(DEFAULT_SUPUESTOS);
        assertThat(testImpactosEsperados.getOrdenVista()).isEqualTo(DEFAULT_ORDEN_VISTA);
    }

    @Test
    @Transactional
    public void createImpactosEsperadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = impactosEsperadosRepository.findAll().size();

        // Create the ImpactosEsperados with an existing ID
        impactosEsperados.setId(1L);
        ImpactosEsperadosDTO impactosEsperadosDTO = impactosEsperadosMapper.toDto(impactosEsperados);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpactosEsperadosMockMvc.perform(post("/api/impactos-esperados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(impactosEsperadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImpactosEsperados in the database
        List<ImpactosEsperados> impactosEsperadosList = impactosEsperadosRepository.findAll();
        assertThat(impactosEsperadosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImpactosEsperados() throws Exception {
        // Initialize the database
        impactosEsperadosRepository.saveAndFlush(impactosEsperados);

        // Get all the impactosEsperadosList
        restImpactosEsperadosMockMvc.perform(get("/api/impactos-esperados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impactosEsperados.getId().intValue())))
            .andExpect(jsonPath("$.[*].impacto").value(hasItem(DEFAULT_IMPACTO)))
            .andExpect(jsonPath("$.[*].plazo").value(hasItem(DEFAULT_PLAZO)))
            .andExpect(jsonPath("$.[*].indicador").value(hasItem(DEFAULT_INDICADOR)))
            .andExpect(jsonPath("$.[*].supuestos").value(hasItem(DEFAULT_SUPUESTOS)))
            .andExpect(jsonPath("$.[*].ordenVista").value(hasItem(DEFAULT_ORDEN_VISTA)));
    }
    
    @Test
    @Transactional
    public void getImpactosEsperados() throws Exception {
        // Initialize the database
        impactosEsperadosRepository.saveAndFlush(impactosEsperados);

        // Get the impactosEsperados
        restImpactosEsperadosMockMvc.perform(get("/api/impactos-esperados/{id}", impactosEsperados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(impactosEsperados.getId().intValue()))
            .andExpect(jsonPath("$.impacto").value(DEFAULT_IMPACTO))
            .andExpect(jsonPath("$.plazo").value(DEFAULT_PLAZO))
            .andExpect(jsonPath("$.indicador").value(DEFAULT_INDICADOR))
            .andExpect(jsonPath("$.supuestos").value(DEFAULT_SUPUESTOS))
            .andExpect(jsonPath("$.ordenVista").value(DEFAULT_ORDEN_VISTA));
    }
    @Test
    @Transactional
    public void getNonExistingImpactosEsperados() throws Exception {
        // Get the impactosEsperados
        restImpactosEsperadosMockMvc.perform(get("/api/impactos-esperados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImpactosEsperados() throws Exception {
        // Initialize the database
        impactosEsperadosRepository.saveAndFlush(impactosEsperados);

        int databaseSizeBeforeUpdate = impactosEsperadosRepository.findAll().size();

        // Update the impactosEsperados
        ImpactosEsperados updatedImpactosEsperados = impactosEsperadosRepository.findById(impactosEsperados.getId()).get();
        // Disconnect from session so that the updates on updatedImpactosEsperados are not directly saved in db
        em.detach(updatedImpactosEsperados);
        updatedImpactosEsperados
            .impacto(UPDATED_IMPACTO)
            .plazo(UPDATED_PLAZO)
            .indicador(UPDATED_INDICADOR)
            .supuestos(UPDATED_SUPUESTOS)
            .ordenVista(UPDATED_ORDEN_VISTA);
        ImpactosEsperadosDTO impactosEsperadosDTO = impactosEsperadosMapper.toDto(updatedImpactosEsperados);

        restImpactosEsperadosMockMvc.perform(put("/api/impactos-esperados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(impactosEsperadosDTO)))
            .andExpect(status().isOk());

        // Validate the ImpactosEsperados in the database
        List<ImpactosEsperados> impactosEsperadosList = impactosEsperadosRepository.findAll();
        assertThat(impactosEsperadosList).hasSize(databaseSizeBeforeUpdate);
        ImpactosEsperados testImpactosEsperados = impactosEsperadosList.get(impactosEsperadosList.size() - 1);
        assertThat(testImpactosEsperados.getImpacto()).isEqualTo(UPDATED_IMPACTO);
        assertThat(testImpactosEsperados.getPlazo()).isEqualTo(UPDATED_PLAZO);
        assertThat(testImpactosEsperados.getIndicador()).isEqualTo(UPDATED_INDICADOR);
        assertThat(testImpactosEsperados.getSupuestos()).isEqualTo(UPDATED_SUPUESTOS);
        assertThat(testImpactosEsperados.getOrdenVista()).isEqualTo(UPDATED_ORDEN_VISTA);
    }

    @Test
    @Transactional
    public void updateNonExistingImpactosEsperados() throws Exception {
        int databaseSizeBeforeUpdate = impactosEsperadosRepository.findAll().size();

        // Create the ImpactosEsperados
        ImpactosEsperadosDTO impactosEsperadosDTO = impactosEsperadosMapper.toDto(impactosEsperados);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpactosEsperadosMockMvc.perform(put("/api/impactos-esperados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(impactosEsperadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImpactosEsperados in the database
        List<ImpactosEsperados> impactosEsperadosList = impactosEsperadosRepository.findAll();
        assertThat(impactosEsperadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImpactosEsperados() throws Exception {
        // Initialize the database
        impactosEsperadosRepository.saveAndFlush(impactosEsperados);

        int databaseSizeBeforeDelete = impactosEsperadosRepository.findAll().size();

        // Delete the impactosEsperados
        restImpactosEsperadosMockMvc.perform(delete("/api/impactos-esperados/{id}", impactosEsperados.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImpactosEsperados> impactosEsperadosList = impactosEsperadosRepository.findAll();
        assertThat(impactosEsperadosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
