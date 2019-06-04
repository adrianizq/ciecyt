package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.IntegrantesProyecto;
import com.itp.ciecyt.repository.IntegrantesProyectoRepository;
import com.itp.ciecyt.service.IntegrantesProyectoService;
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
 * Integration tests for the {@Link IntegrantesProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class IntegrantesProyectoResourceIT {

    @Autowired
    private IntegrantesProyectoRepository integrantesProyectoRepository;

    @Autowired
    private IntegrantesProyectoService integrantesProyectoService;

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

    private MockMvc restIntegrantesProyectoMockMvc;

    private IntegrantesProyecto integrantesProyecto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntegrantesProyectoResource integrantesProyectoResource = new IntegrantesProyectoResource(integrantesProyectoService);
        this.restIntegrantesProyectoMockMvc = MockMvcBuilders.standaloneSetup(integrantesProyectoResource)
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
    public static IntegrantesProyecto createEntity(EntityManager em) {
        IntegrantesProyecto integrantesProyecto = new IntegrantesProyecto();
        return integrantesProyecto;
    }

    @BeforeEach
    public void initTest() {
        integrantesProyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntegrantesProyecto() throws Exception {
        int databaseSizeBeforeCreate = integrantesProyectoRepository.findAll().size();

        // Create the IntegrantesProyecto
        restIntegrantesProyectoMockMvc.perform(post("/api/integrantes-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrantesProyecto)))
            .andExpect(status().isCreated());

        // Validate the IntegrantesProyecto in the database
        List<IntegrantesProyecto> integrantesProyectoList = integrantesProyectoRepository.findAll();
        assertThat(integrantesProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        IntegrantesProyecto testIntegrantesProyecto = integrantesProyectoList.get(integrantesProyectoList.size() - 1);
    }

    @Test
    @Transactional
    public void createIntegrantesProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = integrantesProyectoRepository.findAll().size();

        // Create the IntegrantesProyecto with an existing ID
        integrantesProyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntegrantesProyectoMockMvc.perform(post("/api/integrantes-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrantesProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the IntegrantesProyecto in the database
        List<IntegrantesProyecto> integrantesProyectoList = integrantesProyectoRepository.findAll();
        assertThat(integrantesProyectoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllIntegrantesProyectos() throws Exception {
        // Initialize the database
        integrantesProyectoRepository.saveAndFlush(integrantesProyecto);

        // Get all the integrantesProyectoList
        restIntegrantesProyectoMockMvc.perform(get("/api/integrantes-proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(integrantesProyecto.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getIntegrantesProyecto() throws Exception {
        // Initialize the database
        integrantesProyectoRepository.saveAndFlush(integrantesProyecto);

        // Get the integrantesProyecto
        restIntegrantesProyectoMockMvc.perform(get("/api/integrantes-proyectos/{id}", integrantesProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(integrantesProyecto.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingIntegrantesProyecto() throws Exception {
        // Get the integrantesProyecto
        restIntegrantesProyectoMockMvc.perform(get("/api/integrantes-proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntegrantesProyecto() throws Exception {
        // Initialize the database
        integrantesProyectoService.save(integrantesProyecto);

        int databaseSizeBeforeUpdate = integrantesProyectoRepository.findAll().size();

        // Update the integrantesProyecto
        IntegrantesProyecto updatedIntegrantesProyecto = integrantesProyectoRepository.findById(integrantesProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedIntegrantesProyecto are not directly saved in db
        em.detach(updatedIntegrantesProyecto);

        restIntegrantesProyectoMockMvc.perform(put("/api/integrantes-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntegrantesProyecto)))
            .andExpect(status().isOk());

        // Validate the IntegrantesProyecto in the database
        List<IntegrantesProyecto> integrantesProyectoList = integrantesProyectoRepository.findAll();
        assertThat(integrantesProyectoList).hasSize(databaseSizeBeforeUpdate);
        IntegrantesProyecto testIntegrantesProyecto = integrantesProyectoList.get(integrantesProyectoList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingIntegrantesProyecto() throws Exception {
        int databaseSizeBeforeUpdate = integrantesProyectoRepository.findAll().size();

        // Create the IntegrantesProyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntegrantesProyectoMockMvc.perform(put("/api/integrantes-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(integrantesProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the IntegrantesProyecto in the database
        List<IntegrantesProyecto> integrantesProyectoList = integrantesProyectoRepository.findAll();
        assertThat(integrantesProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntegrantesProyecto() throws Exception {
        // Initialize the database
        integrantesProyectoService.save(integrantesProyecto);

        int databaseSizeBeforeDelete = integrantesProyectoRepository.findAll().size();

        // Delete the integrantesProyecto
        restIntegrantesProyectoMockMvc.perform(delete("/api/integrantes-proyectos/{id}", integrantesProyecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<IntegrantesProyecto> integrantesProyectoList = integrantesProyectoRepository.findAll();
        assertThat(integrantesProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegrantesProyecto.class);
        IntegrantesProyecto integrantesProyecto1 = new IntegrantesProyecto();
        integrantesProyecto1.setId(1L);
        IntegrantesProyecto integrantesProyecto2 = new IntegrantesProyecto();
        integrantesProyecto2.setId(integrantesProyecto1.getId());
        assertThat(integrantesProyecto1).isEqualTo(integrantesProyecto2);
        integrantesProyecto2.setId(2L);
        assertThat(integrantesProyecto1).isNotEqualTo(integrantesProyecto2);
        integrantesProyecto1.setId(null);
        assertThat(integrantesProyecto1).isNotEqualTo(integrantesProyecto2);
    }
}
