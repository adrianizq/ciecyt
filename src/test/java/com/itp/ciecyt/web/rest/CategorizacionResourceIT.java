package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.Categorizacion;
import com.itp.ciecyt.repository.CategorizacionRepository;
import com.itp.ciecyt.service.CategorizacionService;
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
 * Integration tests for the {@Link CategorizacionResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class CategorizacionResourceIT {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    @Autowired
    private CategorizacionRepository categorizacionRepository;

    @Autowired
    private CategorizacionService categorizacionService;

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

    private MockMvc restCategorizacionMockMvc;

    private Categorizacion categorizacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CategorizacionResource categorizacionResource = new CategorizacionResource(categorizacionService);
        this.restCategorizacionMockMvc = MockMvcBuilders.standaloneSetup(categorizacionResource)
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
    public static Categorizacion createEntity(EntityManager em) {
        Categorizacion categorizacion = new Categorizacion()
            .categoria(DEFAULT_CATEGORIA);
        return categorizacion;
    }

    @BeforeEach
    public void initTest() {
        categorizacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorizacion() throws Exception {
        int databaseSizeBeforeCreate = categorizacionRepository.findAll().size();

        // Create the Categorizacion
        restCategorizacionMockMvc.perform(post("/api/categorizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorizacion)))
            .andExpect(status().isCreated());

        // Validate the Categorizacion in the database
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeCreate + 1);
        Categorizacion testCategorizacion = categorizacionList.get(categorizacionList.size() - 1);
        assertThat(testCategorizacion.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
    }

    @Test
    @Transactional
    public void createCategorizacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorizacionRepository.findAll().size();

        // Create the Categorizacion with an existing ID
        categorizacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorizacionMockMvc.perform(post("/api/categorizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorizacion)))
            .andExpect(status().isBadRequest());

        // Validate the Categorizacion in the database
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategorizacions() throws Exception {
        // Initialize the database
        categorizacionRepository.saveAndFlush(categorizacion);

        // Get all the categorizacionList
        restCategorizacionMockMvc.perform(get("/api/categorizacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorizacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }
    
    @Test
    @Transactional
    public void getCategorizacion() throws Exception {
        // Initialize the database
        categorizacionRepository.saveAndFlush(categorizacion);

        // Get the categorizacion
        restCategorizacionMockMvc.perform(get("/api/categorizacions/{id}", categorizacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(categorizacion.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCategorizacion() throws Exception {
        // Get the categorizacion
        restCategorizacionMockMvc.perform(get("/api/categorizacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorizacion() throws Exception {
        // Initialize the database
        categorizacionService.save(categorizacion);

        int databaseSizeBeforeUpdate = categorizacionRepository.findAll().size();

        // Update the categorizacion
        Categorizacion updatedCategorizacion = categorizacionRepository.findById(categorizacion.getId()).get();
        // Disconnect from session so that the updates on updatedCategorizacion are not directly saved in db
        em.detach(updatedCategorizacion);
        updatedCategorizacion
            .categoria(UPDATED_CATEGORIA);

        restCategorizacionMockMvc.perform(put("/api/categorizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategorizacion)))
            .andExpect(status().isOk());

        // Validate the Categorizacion in the database
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeUpdate);
        Categorizacion testCategorizacion = categorizacionList.get(categorizacionList.size() - 1);
        assertThat(testCategorizacion.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorizacion() throws Exception {
        int databaseSizeBeforeUpdate = categorizacionRepository.findAll().size();

        // Create the Categorizacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorizacionMockMvc.perform(put("/api/categorizacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(categorizacion)))
            .andExpect(status().isBadRequest());

        // Validate the Categorizacion in the database
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorizacion() throws Exception {
        // Initialize the database
        categorizacionService.save(categorizacion);

        int databaseSizeBeforeDelete = categorizacionRepository.findAll().size();

        // Delete the categorizacion
        restCategorizacionMockMvc.perform(delete("/api/categorizacions/{id}", categorizacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categorizacion.class);
        Categorizacion categorizacion1 = new Categorizacion();
        categorizacion1.setId(1L);
        Categorizacion categorizacion2 = new Categorizacion();
        categorizacion2.setId(categorizacion1.getId());
        assertThat(categorizacion1).isEqualTo(categorizacion2);
        categorizacion2.setId(2L);
        assertThat(categorizacion1).isNotEqualTo(categorizacion2);
        categorizacion1.setId(null);
        assertThat(categorizacion1).isNotEqualTo(categorizacion2);
    }
}
