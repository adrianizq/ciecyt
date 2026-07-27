package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.ResultadosEsperados;
import co.edu.itp.ciecyt.repository.ResultadosEsperadosRepository;
import co.edu.itp.ciecyt.service.ResultadosEsperadosService;
import co.edu.itp.ciecyt.service.dto.ResultadosEsperadosDTO;
import co.edu.itp.ciecyt.service.mapper.ResultadosEsperadosMapper;

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
 * Integration tests for the {@link ResultadosEsperadosResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResultadosEsperadosResourceIT {

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final String DEFAULT_INDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_INDICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARIO = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARIO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDEN_VISTA = 1;
    private static final Integer UPDATED_ORDEN_VISTA = 2;

    @Autowired
    private ResultadosEsperadosRepository resultadosEsperadosRepository;

    @Autowired
    private ResultadosEsperadosMapper resultadosEsperadosMapper;

    @Autowired
    private ResultadosEsperadosService resultadosEsperadosService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultadosEsperadosMockMvc;

    private ResultadosEsperados resultadosEsperados;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultadosEsperados createEntity(EntityManager em) {
        ResultadosEsperados resultadosEsperados = new ResultadosEsperados()
            .resultado(DEFAULT_RESULTADO)
            .indicador(DEFAULT_INDICADOR)
            .beneficiario(DEFAULT_BENEFICIARIO)
            .ordenVista(DEFAULT_ORDEN_VISTA);
        return resultadosEsperados;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultadosEsperados createUpdatedEntity(EntityManager em) {
        ResultadosEsperados resultadosEsperados = new ResultadosEsperados()
            .resultado(UPDATED_RESULTADO)
            .indicador(UPDATED_INDICADOR)
            .beneficiario(UPDATED_BENEFICIARIO)
            .ordenVista(UPDATED_ORDEN_VISTA);
        return resultadosEsperados;
    }

    @BeforeEach
    public void initTest() {
        resultadosEsperados = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultadosEsperados() throws Exception {
        int databaseSizeBeforeCreate = resultadosEsperadosRepository.findAll().size();
        // Create the ResultadosEsperados
        ResultadosEsperadosDTO resultadosEsperadosDTO = resultadosEsperadosMapper.toDto(resultadosEsperados);
        restResultadosEsperadosMockMvc.perform(post("/api/resultados-esperados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadosEsperadosDTO)))
            .andExpect(status().isCreated());

        // Validate the ResultadosEsperados in the database
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeCreate + 1);
        ResultadosEsperados testResultadosEsperados = resultadosEsperadosList.get(resultadosEsperadosList.size() - 1);
        assertThat(testResultadosEsperados.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testResultadosEsperados.getIndicador()).isEqualTo(DEFAULT_INDICADOR);
        assertThat(testResultadosEsperados.getBeneficiario()).isEqualTo(DEFAULT_BENEFICIARIO);
        assertThat(testResultadosEsperados.getOrdenVista()).isEqualTo(DEFAULT_ORDEN_VISTA);
    }

    @Test
    @Transactional
    public void createResultadosEsperadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadosEsperadosRepository.findAll().size();

        // Create the ResultadosEsperados with an existing ID
        resultadosEsperados.setId(1L);
        ResultadosEsperadosDTO resultadosEsperadosDTO = resultadosEsperadosMapper.toDto(resultadosEsperados);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadosEsperadosMockMvc.perform(post("/api/resultados-esperados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadosEsperadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadosEsperados in the database
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllResultadosEsperados() throws Exception {
        // Initialize the database
        resultadosEsperadosRepository.saveAndFlush(resultadosEsperados);

        // Get all the resultadosEsperadosList
        restResultadosEsperadosMockMvc.perform(get("/api/resultados-esperados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadosEsperados.getId().intValue())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO)))
            .andExpect(jsonPath("$.[*].indicador").value(hasItem(DEFAULT_INDICADOR)))
            .andExpect(jsonPath("$.[*].beneficiario").value(hasItem(DEFAULT_BENEFICIARIO)))
            .andExpect(jsonPath("$.[*].ordenVista").value(hasItem(DEFAULT_ORDEN_VISTA)));
    }
    
    @Test
    @Transactional
    public void getResultadosEsperados() throws Exception {
        // Initialize the database
        resultadosEsperadosRepository.saveAndFlush(resultadosEsperados);

        // Get the resultadosEsperados
        restResultadosEsperadosMockMvc.perform(get("/api/resultados-esperados/{id}", resultadosEsperados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultadosEsperados.getId().intValue()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO))
            .andExpect(jsonPath("$.indicador").value(DEFAULT_INDICADOR))
            .andExpect(jsonPath("$.beneficiario").value(DEFAULT_BENEFICIARIO))
            .andExpect(jsonPath("$.ordenVista").value(DEFAULT_ORDEN_VISTA));
    }
    @Test
    @Transactional
    public void getNonExistingResultadosEsperados() throws Exception {
        // Get the resultadosEsperados
        restResultadosEsperadosMockMvc.perform(get("/api/resultados-esperados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultadosEsperados() throws Exception {
        // Initialize the database
        resultadosEsperadosRepository.saveAndFlush(resultadosEsperados);

        int databaseSizeBeforeUpdate = resultadosEsperadosRepository.findAll().size();

        // Update the resultadosEsperados
        ResultadosEsperados updatedResultadosEsperados = resultadosEsperadosRepository.findById(resultadosEsperados.getId()).get();
        // Disconnect from session so that the updates on updatedResultadosEsperados are not directly saved in db
        em.detach(updatedResultadosEsperados);
        updatedResultadosEsperados
            .resultado(UPDATED_RESULTADO)
            .indicador(UPDATED_INDICADOR)
            .beneficiario(UPDATED_BENEFICIARIO)
            .ordenVista(UPDATED_ORDEN_VISTA);
        ResultadosEsperadosDTO resultadosEsperadosDTO = resultadosEsperadosMapper.toDto(updatedResultadosEsperados);

        restResultadosEsperadosMockMvc.perform(put("/api/resultados-esperados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadosEsperadosDTO)))
            .andExpect(status().isOk());

        // Validate the ResultadosEsperados in the database
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeUpdate);
        ResultadosEsperados testResultadosEsperados = resultadosEsperadosList.get(resultadosEsperadosList.size() - 1);
        assertThat(testResultadosEsperados.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testResultadosEsperados.getIndicador()).isEqualTo(UPDATED_INDICADOR);
        assertThat(testResultadosEsperados.getBeneficiario()).isEqualTo(UPDATED_BENEFICIARIO);
        assertThat(testResultadosEsperados.getOrdenVista()).isEqualTo(UPDATED_ORDEN_VISTA);
    }

    @Test
    @Transactional
    public void updateNonExistingResultadosEsperados() throws Exception {
        int databaseSizeBeforeUpdate = resultadosEsperadosRepository.findAll().size();

        // Create the ResultadosEsperados
        ResultadosEsperadosDTO resultadosEsperadosDTO = resultadosEsperadosMapper.toDto(resultadosEsperados);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadosEsperadosMockMvc.perform(put("/api/resultados-esperados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultadosEsperadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadosEsperados in the database
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultadosEsperados() throws Exception {
        // Initialize the database
        resultadosEsperadosRepository.saveAndFlush(resultadosEsperados);

        int databaseSizeBeforeDelete = resultadosEsperadosRepository.findAll().size();

        // Delete the resultadosEsperados
        restResultadosEsperadosMockMvc.perform(delete("/api/resultados-esperados/{id}", resultadosEsperados.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
