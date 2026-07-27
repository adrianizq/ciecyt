package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.InvestigacionTipo;
import co.edu.itp.ciecyt.repository.InvestigacionTipoRepository;
import co.edu.itp.ciecyt.service.InvestigacionTipoService;
import co.edu.itp.ciecyt.service.dto.InvestigacionTipoDTO;
import co.edu.itp.ciecyt.service.mapper.InvestigacionTipoMapper;

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
 * Integration tests for the {@link InvestigacionTipoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvestigacionTipoResourceIT {

    private static final String DEFAULT_INVESTIGACION_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_INVESTIGACION_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_INVESTIGACION_TIPO_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_INVESTIGACION_TIPO_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private InvestigacionTipoRepository investigacionTipoRepository;

    @Autowired
    private InvestigacionTipoMapper investigacionTipoMapper;

    @Autowired
    private InvestigacionTipoService investigacionTipoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvestigacionTipoMockMvc;

    private InvestigacionTipo investigacionTipo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvestigacionTipo createEntity(EntityManager em) {
        InvestigacionTipo investigacionTipo = new InvestigacionTipo()
            .investigacionTipo(DEFAULT_INVESTIGACION_TIPO)
            .investigacionTipoDescripcion(DEFAULT_INVESTIGACION_TIPO_DESCRIPCION)
            .tipo(DEFAULT_TIPO)
            .tipoDescripcion(DEFAULT_TIPO_DESCRIPCION);
        return investigacionTipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvestigacionTipo createUpdatedEntity(EntityManager em) {
        InvestigacionTipo investigacionTipo = new InvestigacionTipo()
            .investigacionTipo(UPDATED_INVESTIGACION_TIPO)
            .investigacionTipoDescripcion(UPDATED_INVESTIGACION_TIPO_DESCRIPCION)
            .tipo(UPDATED_TIPO)
            .tipoDescripcion(UPDATED_TIPO_DESCRIPCION);
        return investigacionTipo;
    }

    @BeforeEach
    public void initTest() {
        investigacionTipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvestigacionTipo() throws Exception {
        int databaseSizeBeforeCreate = investigacionTipoRepository.findAll().size();
        // Create the InvestigacionTipo
        InvestigacionTipoDTO investigacionTipoDTO = investigacionTipoMapper.toDto(investigacionTipo);
        restInvestigacionTipoMockMvc.perform(post("/api/investigacion-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(investigacionTipoDTO)))
            .andExpect(status().isCreated());

        // Validate the InvestigacionTipo in the database
        List<InvestigacionTipo> investigacionTipoList = investigacionTipoRepository.findAll();
        assertThat(investigacionTipoList).hasSize(databaseSizeBeforeCreate + 1);
        InvestigacionTipo testInvestigacionTipo = investigacionTipoList.get(investigacionTipoList.size() - 1);
        assertThat(testInvestigacionTipo.getInvestigacionTipo()).isEqualTo(DEFAULT_INVESTIGACION_TIPO);
        assertThat(testInvestigacionTipo.getInvestigacionTipoDescripcion()).isEqualTo(DEFAULT_INVESTIGACION_TIPO_DESCRIPCION);
        assertThat(testInvestigacionTipo.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testInvestigacionTipo.getTipoDescripcion()).isEqualTo(DEFAULT_TIPO_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createInvestigacionTipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = investigacionTipoRepository.findAll().size();

        // Create the InvestigacionTipo with an existing ID
        investigacionTipo.setId(1L);
        InvestigacionTipoDTO investigacionTipoDTO = investigacionTipoMapper.toDto(investigacionTipo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvestigacionTipoMockMvc.perform(post("/api/investigacion-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(investigacionTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvestigacionTipo in the database
        List<InvestigacionTipo> investigacionTipoList = investigacionTipoRepository.findAll();
        assertThat(investigacionTipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvestigacionTipos() throws Exception {
        // Initialize the database
        investigacionTipoRepository.saveAndFlush(investigacionTipo);

        // Get all the investigacionTipoList
        restInvestigacionTipoMockMvc.perform(get("/api/investigacion-tipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(investigacionTipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].investigacionTipo").value(hasItem(DEFAULT_INVESTIGACION_TIPO)))
            .andExpect(jsonPath("$.[*].investigacionTipoDescripcion").value(hasItem(DEFAULT_INVESTIGACION_TIPO_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].tipoDescripcion").value(hasItem(DEFAULT_TIPO_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getInvestigacionTipo() throws Exception {
        // Initialize the database
        investigacionTipoRepository.saveAndFlush(investigacionTipo);

        // Get the investigacionTipo
        restInvestigacionTipoMockMvc.perform(get("/api/investigacion-tipos/{id}", investigacionTipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(investigacionTipo.getId().intValue()))
            .andExpect(jsonPath("$.investigacionTipo").value(DEFAULT_INVESTIGACION_TIPO))
            .andExpect(jsonPath("$.investigacionTipoDescripcion").value(DEFAULT_INVESTIGACION_TIPO_DESCRIPCION))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.tipoDescripcion").value(DEFAULT_TIPO_DESCRIPCION));
    }
    @Test
    @Transactional
    public void getNonExistingInvestigacionTipo() throws Exception {
        // Get the investigacionTipo
        restInvestigacionTipoMockMvc.perform(get("/api/investigacion-tipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvestigacionTipo() throws Exception {
        // Initialize the database
        investigacionTipoRepository.saveAndFlush(investigacionTipo);

        int databaseSizeBeforeUpdate = investigacionTipoRepository.findAll().size();

        // Update the investigacionTipo
        InvestigacionTipo updatedInvestigacionTipo = investigacionTipoRepository.findById(investigacionTipo.getId()).get();
        // Disconnect from session so that the updates on updatedInvestigacionTipo are not directly saved in db
        em.detach(updatedInvestigacionTipo);
        updatedInvestigacionTipo
            .investigacionTipo(UPDATED_INVESTIGACION_TIPO)
            .investigacionTipoDescripcion(UPDATED_INVESTIGACION_TIPO_DESCRIPCION)
            .tipo(UPDATED_TIPO)
            .tipoDescripcion(UPDATED_TIPO_DESCRIPCION);
        InvestigacionTipoDTO investigacionTipoDTO = investigacionTipoMapper.toDto(updatedInvestigacionTipo);

        restInvestigacionTipoMockMvc.perform(put("/api/investigacion-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(investigacionTipoDTO)))
            .andExpect(status().isOk());

        // Validate the InvestigacionTipo in the database
        List<InvestigacionTipo> investigacionTipoList = investigacionTipoRepository.findAll();
        assertThat(investigacionTipoList).hasSize(databaseSizeBeforeUpdate);
        InvestigacionTipo testInvestigacionTipo = investigacionTipoList.get(investigacionTipoList.size() - 1);
        assertThat(testInvestigacionTipo.getInvestigacionTipo()).isEqualTo(UPDATED_INVESTIGACION_TIPO);
        assertThat(testInvestigacionTipo.getInvestigacionTipoDescripcion()).isEqualTo(UPDATED_INVESTIGACION_TIPO_DESCRIPCION);
        assertThat(testInvestigacionTipo.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testInvestigacionTipo.getTipoDescripcion()).isEqualTo(UPDATED_TIPO_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingInvestigacionTipo() throws Exception {
        int databaseSizeBeforeUpdate = investigacionTipoRepository.findAll().size();

        // Create the InvestigacionTipo
        InvestigacionTipoDTO investigacionTipoDTO = investigacionTipoMapper.toDto(investigacionTipo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvestigacionTipoMockMvc.perform(put("/api/investigacion-tipos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(investigacionTipoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InvestigacionTipo in the database
        List<InvestigacionTipo> investigacionTipoList = investigacionTipoRepository.findAll();
        assertThat(investigacionTipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvestigacionTipo() throws Exception {
        // Initialize the database
        investigacionTipoRepository.saveAndFlush(investigacionTipo);

        int databaseSizeBeforeDelete = investigacionTipoRepository.findAll().size();

        // Delete the investigacionTipo
        restInvestigacionTipoMockMvc.perform(delete("/api/investigacion-tipos/{id}", investigacionTipo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvestigacionTipo> investigacionTipoList = investigacionTipoRepository.findAll();
        assertThat(investigacionTipoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
