package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.Facultad;
import com.itp.ciecyt.repository.FacultadRepository;
import com.itp.ciecyt.service.FacultadService;
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
 * Integration tests for the {@Link FacultadResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class FacultadResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private FacultadService facultadService;

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

    private MockMvc restFacultadMockMvc;

    private Facultad facultad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FacultadResource facultadResource = new FacultadResource(facultadService);
        this.restFacultadMockMvc = MockMvcBuilders.standaloneSetup(facultadResource)
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
    public static Facultad createEntity(EntityManager em) {
        Facultad facultad = new Facultad()
            .nombre(DEFAULT_NOMBRE);
        return facultad;
    }

    @BeforeEach
    public void initTest() {
        facultad = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacultad() throws Exception {
        int databaseSizeBeforeCreate = facultadRepository.findAll().size();

        // Create the Facultad
        restFacultadMockMvc.perform(post("/api/facultads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facultad)))
            .andExpect(status().isCreated());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeCreate + 1);
        Facultad testFacultad = facultadList.get(facultadList.size() - 1);
        assertThat(testFacultad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createFacultadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facultadRepository.findAll().size();

        // Create the Facultad with an existing ID
        facultad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacultadMockMvc.perform(post("/api/facultads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facultad)))
            .andExpect(status().isBadRequest());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacultads() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        // Get all the facultadList
        restFacultadMockMvc.perform(get("/api/facultads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facultad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getFacultad() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        // Get the facultad
        restFacultadMockMvc.perform(get("/api/facultads/{id}", facultad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(facultad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFacultad() throws Exception {
        // Get the facultad
        restFacultadMockMvc.perform(get("/api/facultads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacultad() throws Exception {
        // Initialize the database
        facultadService.save(facultad);

        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();

        // Update the facultad
        Facultad updatedFacultad = facultadRepository.findById(facultad.getId()).get();
        // Disconnect from session so that the updates on updatedFacultad are not directly saved in db
        em.detach(updatedFacultad);
        updatedFacultad
            .nombre(UPDATED_NOMBRE);

        restFacultadMockMvc.perform(put("/api/facultads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacultad)))
            .andExpect(status().isOk());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
        Facultad testFacultad = facultadList.get(facultadList.size() - 1);
        assertThat(testFacultad.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingFacultad() throws Exception {
        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();

        // Create the Facultad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacultadMockMvc.perform(put("/api/facultads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(facultad)))
            .andExpect(status().isBadRequest());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacultad() throws Exception {
        // Initialize the database
        facultadService.save(facultad);

        int databaseSizeBeforeDelete = facultadRepository.findAll().size();

        // Delete the facultad
        restFacultadMockMvc.perform(delete("/api/facultads/{id}", facultad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facultad.class);
        Facultad facultad1 = new Facultad();
        facultad1.setId(1L);
        Facultad facultad2 = new Facultad();
        facultad2.setId(facultad1.getId());
        assertThat(facultad1).isEqualTo(facultad2);
        facultad2.setId(2L);
        assertThat(facultad1).isNotEqualTo(facultad2);
        facultad1.setId(null);
        assertThat(facultad1).isNotEqualTo(facultad2);
    }
}
