package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.EntidadFinanciadora;
import com.itp.ciecyt.repository.EntidadFinanciadoraRepository;
import com.itp.ciecyt.service.EntidadFinanciadoraService;
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
 * Integration tests for the {@Link EntidadFinanciadoraResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class EntidadFinanciadoraResourceIT {

    private static final Double DEFAULT_VALOR = 1D;
    private static final Double UPDATED_VALOR = 2D;

    private static final Boolean DEFAULT_ESTADO_FINANCIACION = false;
    private static final Boolean UPDATED_ESTADO_FINANCIACION = true;

    @Autowired
    private EntidadFinanciadoraRepository entidadFinanciadoraRepository;

    @Autowired
    private EntidadFinanciadoraService entidadFinanciadoraService;

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

    private MockMvc restEntidadFinanciadoraMockMvc;

    private EntidadFinanciadora entidadFinanciadora;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntidadFinanciadoraResource entidadFinanciadoraResource = new EntidadFinanciadoraResource(entidadFinanciadoraService);
        this.restEntidadFinanciadoraMockMvc = MockMvcBuilders.standaloneSetup(entidadFinanciadoraResource)
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
    public static EntidadFinanciadora createEntity(EntityManager em) {
        EntidadFinanciadora entidadFinanciadora = new EntidadFinanciadora()
            .valor(DEFAULT_VALOR)
            .estadoFinanciacion(DEFAULT_ESTADO_FINANCIACION);
        return entidadFinanciadora;
    }

    @BeforeEach
    public void initTest() {
        entidadFinanciadora = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntidadFinanciadora() throws Exception {
        int databaseSizeBeforeCreate = entidadFinanciadoraRepository.findAll().size();

        // Create the EntidadFinanciadora
        restEntidadFinanciadoraMockMvc.perform(post("/api/entidad-financiadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciadora)))
            .andExpect(status().isCreated());

        // Validate the EntidadFinanciadora in the database
        List<EntidadFinanciadora> entidadFinanciadoraList = entidadFinanciadoraRepository.findAll();
        assertThat(entidadFinanciadoraList).hasSize(databaseSizeBeforeCreate + 1);
        EntidadFinanciadora testEntidadFinanciadora = entidadFinanciadoraList.get(entidadFinanciadoraList.size() - 1);
        assertThat(testEntidadFinanciadora.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testEntidadFinanciadora.isEstadoFinanciacion()).isEqualTo(DEFAULT_ESTADO_FINANCIACION);
    }

    @Test
    @Transactional
    public void createEntidadFinanciadoraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entidadFinanciadoraRepository.findAll().size();

        // Create the EntidadFinanciadora with an existing ID
        entidadFinanciadora.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadFinanciadoraMockMvc.perform(post("/api/entidad-financiadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciadora)))
            .andExpect(status().isBadRequest());

        // Validate the EntidadFinanciadora in the database
        List<EntidadFinanciadora> entidadFinanciadoraList = entidadFinanciadoraRepository.findAll();
        assertThat(entidadFinanciadoraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEntidadFinanciadoras() throws Exception {
        // Initialize the database
        entidadFinanciadoraRepository.saveAndFlush(entidadFinanciadora);

        // Get all the entidadFinanciadoraList
        restEntidadFinanciadoraMockMvc.perform(get("/api/entidad-financiadoras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidadFinanciadora.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].estadoFinanciacion").value(hasItem(DEFAULT_ESTADO_FINANCIACION.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEntidadFinanciadora() throws Exception {
        // Initialize the database
        entidadFinanciadoraRepository.saveAndFlush(entidadFinanciadora);

        // Get the entidadFinanciadora
        restEntidadFinanciadoraMockMvc.perform(get("/api/entidad-financiadoras/{id}", entidadFinanciadora.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entidadFinanciadora.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.estadoFinanciacion").value(DEFAULT_ESTADO_FINANCIACION.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEntidadFinanciadora() throws Exception {
        // Get the entidadFinanciadora
        restEntidadFinanciadoraMockMvc.perform(get("/api/entidad-financiadoras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntidadFinanciadora() throws Exception {
        // Initialize the database
        entidadFinanciadoraService.save(entidadFinanciadora);

        int databaseSizeBeforeUpdate = entidadFinanciadoraRepository.findAll().size();

        // Update the entidadFinanciadora
        EntidadFinanciadora updatedEntidadFinanciadora = entidadFinanciadoraRepository.findById(entidadFinanciadora.getId()).get();
        // Disconnect from session so that the updates on updatedEntidadFinanciadora are not directly saved in db
        em.detach(updatedEntidadFinanciadora);
        updatedEntidadFinanciadora
            .valor(UPDATED_VALOR)
            .estadoFinanciacion(UPDATED_ESTADO_FINANCIACION);

        restEntidadFinanciadoraMockMvc.perform(put("/api/entidad-financiadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntidadFinanciadora)))
            .andExpect(status().isOk());

        // Validate the EntidadFinanciadora in the database
        List<EntidadFinanciadora> entidadFinanciadoraList = entidadFinanciadoraRepository.findAll();
        assertThat(entidadFinanciadoraList).hasSize(databaseSizeBeforeUpdate);
        EntidadFinanciadora testEntidadFinanciadora = entidadFinanciadoraList.get(entidadFinanciadoraList.size() - 1);
        assertThat(testEntidadFinanciadora.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testEntidadFinanciadora.isEstadoFinanciacion()).isEqualTo(UPDATED_ESTADO_FINANCIACION);
    }

    @Test
    @Transactional
    public void updateNonExistingEntidadFinanciadora() throws Exception {
        int databaseSizeBeforeUpdate = entidadFinanciadoraRepository.findAll().size();

        // Create the EntidadFinanciadora

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadFinanciadoraMockMvc.perform(put("/api/entidad-financiadoras")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entidadFinanciadora)))
            .andExpect(status().isBadRequest());

        // Validate the EntidadFinanciadora in the database
        List<EntidadFinanciadora> entidadFinanciadoraList = entidadFinanciadoraRepository.findAll();
        assertThat(entidadFinanciadoraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntidadFinanciadora() throws Exception {
        // Initialize the database
        entidadFinanciadoraService.save(entidadFinanciadora);

        int databaseSizeBeforeDelete = entidadFinanciadoraRepository.findAll().size();

        // Delete the entidadFinanciadora
        restEntidadFinanciadoraMockMvc.perform(delete("/api/entidad-financiadoras/{id}", entidadFinanciadora.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<EntidadFinanciadora> entidadFinanciadoraList = entidadFinanciadoraRepository.findAll();
        assertThat(entidadFinanciadoraList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadFinanciadora.class);
        EntidadFinanciadora entidadFinanciadora1 = new EntidadFinanciadora();
        entidadFinanciadora1.setId(1L);
        EntidadFinanciadora entidadFinanciadora2 = new EntidadFinanciadora();
        entidadFinanciadora2.setId(entidadFinanciadora1.getId());
        assertThat(entidadFinanciadora1).isEqualTo(entidadFinanciadora2);
        entidadFinanciadora2.setId(2L);
        assertThat(entidadFinanciadora1).isNotEqualTo(entidadFinanciadora2);
        entidadFinanciadora1.setId(null);
        assertThat(entidadFinanciadora1).isNotEqualTo(entidadFinanciadora2);
    }
}
