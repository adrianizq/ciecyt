package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.ResultadosEsperados;
import com.itp.ciecyt.repository.ResultadosEsperadosRepository;
import com.itp.ciecyt.service.ResultadosEsperadosService;
import com.itp.ciecyt.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.itp.ciecyt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ResultadosEsperadosResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class ResultadosEsperadosResourceIT {

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final String DEFAULT_INDICADOR = "AAAAAAAAAA";
    private static final String UPDATED_INDICADOR = "BBBBBBBBBB";

    private static final String DEFAULT_BENEFICIARIO = "AAAAAAAAAA";
    private static final String UPDATED_BENEFICIARIO = "BBBBBBBBBB";

    @Autowired
    private ResultadosEsperadosRepository resultadosEsperadosRepository;

    @Autowired
    private ResultadosEsperadosService resultadosEsperadosService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restResultadosEsperadosMockMvc;

    private ResultadosEsperados resultadosEsperados;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResultadosEsperadosResource resultadosEsperadosResource = new ResultadosEsperadosResource(resultadosEsperadosService);
        this.restResultadosEsperadosMockMvc = MockMvcBuilders.standaloneSetup(resultadosEsperadosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

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
            .beneficiario(DEFAULT_BENEFICIARIO);
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
        restResultadosEsperadosMockMvc.perform(post("/api/resultados-esperados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadosEsperados)))
            .andExpect(status().isCreated());

        // Validate the ResultadosEsperados in the database
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeCreate + 1);
        ResultadosEsperados testResultadosEsperados = resultadosEsperadosList.get(resultadosEsperadosList.size() - 1);
        assertThat(testResultadosEsperados.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testResultadosEsperados.getIndicador()).isEqualTo(DEFAULT_INDICADOR);
        assertThat(testResultadosEsperados.getBeneficiario()).isEqualTo(DEFAULT_BENEFICIARIO);
    }

    @Test
    @Transactional
    public void createResultadosEsperadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultadosEsperadosRepository.findAll().size();

        // Create the ResultadosEsperados with an existing ID
        resultadosEsperados.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultadosEsperadosMockMvc.perform(post("/api/resultados-esperados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadosEsperados)))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultadosEsperados.getId().intValue())))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO.toString())))
            .andExpect(jsonPath("$.[*].indicador").value(hasItem(DEFAULT_INDICADOR.toString())))
            .andExpect(jsonPath("$.[*].beneficiario").value(hasItem(DEFAULT_BENEFICIARIO.toString())));
    }
    
    @Test
    @Transactional
    public void getResultadosEsperados() throws Exception {
        // Initialize the database
        resultadosEsperadosRepository.saveAndFlush(resultadosEsperados);

        // Get the resultadosEsperados
        restResultadosEsperadosMockMvc.perform(get("/api/resultados-esperados/{id}", resultadosEsperados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resultadosEsperados.getId().intValue()))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO.toString()))
            .andExpect(jsonPath("$.indicador").value(DEFAULT_INDICADOR.toString()))
            .andExpect(jsonPath("$.beneficiario").value(DEFAULT_BENEFICIARIO.toString()));
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
        resultadosEsperadosService.save(resultadosEsperados);

        int databaseSizeBeforeUpdate = resultadosEsperadosRepository.findAll().size();

        // Update the resultadosEsperados
        ResultadosEsperados updatedResultadosEsperados = resultadosEsperadosRepository.findById(resultadosEsperados.getId()).get();
        // Disconnect from session so that the updates on updatedResultadosEsperados are not directly saved in db
        em.detach(updatedResultadosEsperados);
        updatedResultadosEsperados
            .resultado(UPDATED_RESULTADO)
            .indicador(UPDATED_INDICADOR)
            .beneficiario(UPDATED_BENEFICIARIO);

        restResultadosEsperadosMockMvc.perform(put("/api/resultados-esperados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResultadosEsperados)))
            .andExpect(status().isOk());

        // Validate the ResultadosEsperados in the database
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeUpdate);
        ResultadosEsperados testResultadosEsperados = resultadosEsperadosList.get(resultadosEsperadosList.size() - 1);
        assertThat(testResultadosEsperados.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testResultadosEsperados.getIndicador()).isEqualTo(UPDATED_INDICADOR);
        assertThat(testResultadosEsperados.getBeneficiario()).isEqualTo(UPDATED_BENEFICIARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingResultadosEsperados() throws Exception {
        int databaseSizeBeforeUpdate = resultadosEsperadosRepository.findAll().size();

        // Create the ResultadosEsperados

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultadosEsperadosMockMvc.perform(put("/api/resultados-esperados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resultadosEsperados)))
            .andExpect(status().isBadRequest());

        // Validate the ResultadosEsperados in the database
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultadosEsperados() throws Exception {
        // Initialize the database
        resultadosEsperadosService.save(resultadosEsperados);

        int databaseSizeBeforeDelete = resultadosEsperadosRepository.findAll().size();

        // Delete the resultadosEsperados
        restResultadosEsperadosMockMvc.perform(delete("/api/resultados-esperados/{id}", resultadosEsperados.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ResultadosEsperados> resultadosEsperadosList = resultadosEsperadosRepository.findAll();
        assertThat(resultadosEsperadosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadosEsperados.class);
        ResultadosEsperados resultadosEsperados1 = new ResultadosEsperados();
        resultadosEsperados1.setId(1L);
        ResultadosEsperados resultadosEsperados2 = new ResultadosEsperados();
        resultadosEsperados2.setId(resultadosEsperados1.getId());
        assertThat(resultadosEsperados1).isEqualTo(resultadosEsperados2);
        resultadosEsperados2.setId(2L);
        assertThat(resultadosEsperados1).isNotEqualTo(resultadosEsperados2);
        resultadosEsperados1.setId(null);
        assertThat(resultadosEsperados1).isNotEqualTo(resultadosEsperados2);
    }
}
