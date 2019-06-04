package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.Rubros;
import com.itp.ciecyt.repository.RubrosRepository;
import com.itp.ciecyt.service.RubrosService;
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
 * Integration tests for the {@Link RubrosResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class RubrosResourceIT {

    private static final String DEFAULT_RUBRO = "AAAAAAAAAA";
    private static final String UPDATED_RUBRO = "BBBBBBBBBB";

    @Autowired
    private RubrosRepository rubrosRepository;

    @Autowired
    private RubrosService rubrosService;

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

    private MockMvc restRubrosMockMvc;

    private Rubros rubros;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RubrosResource rubrosResource = new RubrosResource(rubrosService);
        this.restRubrosMockMvc = MockMvcBuilders.standaloneSetup(rubrosResource)
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
    public static Rubros createEntity(EntityManager em) {
        Rubros rubros = new Rubros()
            .rubro(DEFAULT_RUBRO);
        return rubros;
    }

    @BeforeEach
    public void initTest() {
        rubros = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubros() throws Exception {
        int databaseSizeBeforeCreate = rubrosRepository.findAll().size();

        // Create the Rubros
        restRubrosMockMvc.perform(post("/api/rubros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubros)))
            .andExpect(status().isCreated());

        // Validate the Rubros in the database
        List<Rubros> rubrosList = rubrosRepository.findAll();
        assertThat(rubrosList).hasSize(databaseSizeBeforeCreate + 1);
        Rubros testRubros = rubrosList.get(rubrosList.size() - 1);
        assertThat(testRubros.getRubro()).isEqualTo(DEFAULT_RUBRO);
    }

    @Test
    @Transactional
    public void createRubrosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rubrosRepository.findAll().size();

        // Create the Rubros with an existing ID
        rubros.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubrosMockMvc.perform(post("/api/rubros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubros)))
            .andExpect(status().isBadRequest());

        // Validate the Rubros in the database
        List<Rubros> rubrosList = rubrosRepository.findAll();
        assertThat(rubrosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRubros() throws Exception {
        // Initialize the database
        rubrosRepository.saveAndFlush(rubros);

        // Get all the rubrosList
        restRubrosMockMvc.perform(get("/api/rubros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubros.getId().intValue())))
            .andExpect(jsonPath("$.[*].rubro").value(hasItem(DEFAULT_RUBRO.toString())));
    }
    
    @Test
    @Transactional
    public void getRubros() throws Exception {
        // Initialize the database
        rubrosRepository.saveAndFlush(rubros);

        // Get the rubros
        restRubrosMockMvc.perform(get("/api/rubros/{id}", rubros.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rubros.getId().intValue()))
            .andExpect(jsonPath("$.rubro").value(DEFAULT_RUBRO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRubros() throws Exception {
        // Get the rubros
        restRubrosMockMvc.perform(get("/api/rubros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubros() throws Exception {
        // Initialize the database
        rubrosService.save(rubros);

        int databaseSizeBeforeUpdate = rubrosRepository.findAll().size();

        // Update the rubros
        Rubros updatedRubros = rubrosRepository.findById(rubros.getId()).get();
        // Disconnect from session so that the updates on updatedRubros are not directly saved in db
        em.detach(updatedRubros);
        updatedRubros
            .rubro(UPDATED_RUBRO);

        restRubrosMockMvc.perform(put("/api/rubros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRubros)))
            .andExpect(status().isOk());

        // Validate the Rubros in the database
        List<Rubros> rubrosList = rubrosRepository.findAll();
        assertThat(rubrosList).hasSize(databaseSizeBeforeUpdate);
        Rubros testRubros = rubrosList.get(rubrosList.size() - 1);
        assertThat(testRubros.getRubro()).isEqualTo(UPDATED_RUBRO);
    }

    @Test
    @Transactional
    public void updateNonExistingRubros() throws Exception {
        int databaseSizeBeforeUpdate = rubrosRepository.findAll().size();

        // Create the Rubros

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRubrosMockMvc.perform(put("/api/rubros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rubros)))
            .andExpect(status().isBadRequest());

        // Validate the Rubros in the database
        List<Rubros> rubrosList = rubrosRepository.findAll();
        assertThat(rubrosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRubros() throws Exception {
        // Initialize the database
        rubrosService.save(rubros);

        int databaseSizeBeforeDelete = rubrosRepository.findAll().size();

        // Delete the rubros
        restRubrosMockMvc.perform(delete("/api/rubros/{id}", rubros.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Rubros> rubrosList = rubrosRepository.findAll();
        assertThat(rubrosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rubros.class);
        Rubros rubros1 = new Rubros();
        rubros1.setId(1L);
        Rubros rubros2 = new Rubros();
        rubros2.setId(rubros1.getId());
        assertThat(rubros1).isEqualTo(rubros2);
        rubros2.setId(2L);
        assertThat(rubros1).isNotEqualTo(rubros2);
        rubros1.setId(null);
        assertThat(rubros1).isNotEqualTo(rubros2);
    }
}
