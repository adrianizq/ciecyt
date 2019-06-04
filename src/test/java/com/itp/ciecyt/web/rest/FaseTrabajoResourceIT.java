package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.FaseTrabajo;
import com.itp.ciecyt.repository.FaseTrabajoRepository;
import com.itp.ciecyt.service.FaseTrabajoService;
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
 * Integration tests for the {@Link FaseTrabajoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class FaseTrabajoResourceIT {

    private static final String DEFAULT_FASE_TRABAJO = "AAAAAAAAAA";
    private static final String UPDATED_FASE_TRABAJO = "BBBBBBBBBB";

    @Autowired
    private FaseTrabajoRepository faseTrabajoRepository;

    @Autowired
    private FaseTrabajoService faseTrabajoService;

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

    private MockMvc restFaseTrabajoMockMvc;

    private FaseTrabajo faseTrabajo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaseTrabajoResource faseTrabajoResource = new FaseTrabajoResource(faseTrabajoService);
        this.restFaseTrabajoMockMvc = MockMvcBuilders.standaloneSetup(faseTrabajoResource)
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
    public static FaseTrabajo createEntity(EntityManager em) {
        FaseTrabajo faseTrabajo = new FaseTrabajo()
            .faseTrabajo(DEFAULT_FASE_TRABAJO);
        return faseTrabajo;
    }

    @BeforeEach
    public void initTest() {
        faseTrabajo = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaseTrabajo() throws Exception {
        int databaseSizeBeforeCreate = faseTrabajoRepository.findAll().size();

        // Create the FaseTrabajo
        restFaseTrabajoMockMvc.perform(post("/api/fase-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseTrabajo)))
            .andExpect(status().isCreated());

        // Validate the FaseTrabajo in the database
        List<FaseTrabajo> faseTrabajoList = faseTrabajoRepository.findAll();
        assertThat(faseTrabajoList).hasSize(databaseSizeBeforeCreate + 1);
        FaseTrabajo testFaseTrabajo = faseTrabajoList.get(faseTrabajoList.size() - 1);
        assertThat(testFaseTrabajo.getFaseTrabajo()).isEqualTo(DEFAULT_FASE_TRABAJO);
    }

    @Test
    @Transactional
    public void createFaseTrabajoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faseTrabajoRepository.findAll().size();

        // Create the FaseTrabajo with an existing ID
        faseTrabajo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaseTrabajoMockMvc.perform(post("/api/fase-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseTrabajo)))
            .andExpect(status().isBadRequest());

        // Validate the FaseTrabajo in the database
        List<FaseTrabajo> faseTrabajoList = faseTrabajoRepository.findAll();
        assertThat(faseTrabajoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFaseTrabajos() throws Exception {
        // Initialize the database
        faseTrabajoRepository.saveAndFlush(faseTrabajo);

        // Get all the faseTrabajoList
        restFaseTrabajoMockMvc.perform(get("/api/fase-trabajos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faseTrabajo.getId().intValue())))
            .andExpect(jsonPath("$.[*].faseTrabajo").value(hasItem(DEFAULT_FASE_TRABAJO.toString())));
    }
    
    @Test
    @Transactional
    public void getFaseTrabajo() throws Exception {
        // Initialize the database
        faseTrabajoRepository.saveAndFlush(faseTrabajo);

        // Get the faseTrabajo
        restFaseTrabajoMockMvc.perform(get("/api/fase-trabajos/{id}", faseTrabajo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faseTrabajo.getId().intValue()))
            .andExpect(jsonPath("$.faseTrabajo").value(DEFAULT_FASE_TRABAJO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFaseTrabajo() throws Exception {
        // Get the faseTrabajo
        restFaseTrabajoMockMvc.perform(get("/api/fase-trabajos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaseTrabajo() throws Exception {
        // Initialize the database
        faseTrabajoService.save(faseTrabajo);

        int databaseSizeBeforeUpdate = faseTrabajoRepository.findAll().size();

        // Update the faseTrabajo
        FaseTrabajo updatedFaseTrabajo = faseTrabajoRepository.findById(faseTrabajo.getId()).get();
        // Disconnect from session so that the updates on updatedFaseTrabajo are not directly saved in db
        em.detach(updatedFaseTrabajo);
        updatedFaseTrabajo
            .faseTrabajo(UPDATED_FASE_TRABAJO);

        restFaseTrabajoMockMvc.perform(put("/api/fase-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaseTrabajo)))
            .andExpect(status().isOk());

        // Validate the FaseTrabajo in the database
        List<FaseTrabajo> faseTrabajoList = faseTrabajoRepository.findAll();
        assertThat(faseTrabajoList).hasSize(databaseSizeBeforeUpdate);
        FaseTrabajo testFaseTrabajo = faseTrabajoList.get(faseTrabajoList.size() - 1);
        assertThat(testFaseTrabajo.getFaseTrabajo()).isEqualTo(UPDATED_FASE_TRABAJO);
    }

    @Test
    @Transactional
    public void updateNonExistingFaseTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = faseTrabajoRepository.findAll().size();

        // Create the FaseTrabajo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaseTrabajoMockMvc.perform(put("/api/fase-trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faseTrabajo)))
            .andExpect(status().isBadRequest());

        // Validate the FaseTrabajo in the database
        List<FaseTrabajo> faseTrabajoList = faseTrabajoRepository.findAll();
        assertThat(faseTrabajoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaseTrabajo() throws Exception {
        // Initialize the database
        faseTrabajoService.save(faseTrabajo);

        int databaseSizeBeforeDelete = faseTrabajoRepository.findAll().size();

        // Delete the faseTrabajo
        restFaseTrabajoMockMvc.perform(delete("/api/fase-trabajos/{id}", faseTrabajo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FaseTrabajo> faseTrabajoList = faseTrabajoRepository.findAll();
        assertThat(faseTrabajoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FaseTrabajo.class);
        FaseTrabajo faseTrabajo1 = new FaseTrabajo();
        faseTrabajo1.setId(1L);
        FaseTrabajo faseTrabajo2 = new FaseTrabajo();
        faseTrabajo2.setId(faseTrabajo1.getId());
        assertThat(faseTrabajo1).isEqualTo(faseTrabajo2);
        faseTrabajo2.setId(2L);
        assertThat(faseTrabajo1).isNotEqualTo(faseTrabajo2);
        faseTrabajo1.setId(null);
        assertThat(faseTrabajo1).isNotEqualTo(faseTrabajo2);
    }
}
