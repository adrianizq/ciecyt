package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.TipoPregunta;
import com.itp.ciecyt.repository.TipoPreguntaRepository;
import com.itp.ciecyt.service.TipoPreguntaService;
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
 * Integration tests for the {@Link TipoPreguntaResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class TipoPreguntaResourceIT {

    private static final String DEFAULT_TIPO_PREGUNTA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PREGUNTA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_DATO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DATO = "BBBBBBBBBB";

    @Autowired
    private TipoPreguntaRepository tipoPreguntaRepository;

    @Autowired
    private TipoPreguntaService tipoPreguntaService;

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

    private MockMvc restTipoPreguntaMockMvc;

    private TipoPregunta tipoPregunta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoPreguntaResource tipoPreguntaResource = new TipoPreguntaResource(tipoPreguntaService);
        this.restTipoPreguntaMockMvc = MockMvcBuilders.standaloneSetup(tipoPreguntaResource)
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
    public static TipoPregunta createEntity(EntityManager em) {
        TipoPregunta tipoPregunta = new TipoPregunta()
            .tipoPregunta(DEFAULT_TIPO_PREGUNTA)
            .tipoDato(DEFAULT_TIPO_DATO);
        return tipoPregunta;
    }

    @BeforeEach
    public void initTest() {
        tipoPregunta = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoPregunta() throws Exception {
        int databaseSizeBeforeCreate = tipoPreguntaRepository.findAll().size();

        // Create the TipoPregunta
        restTipoPreguntaMockMvc.perform(post("/api/tipo-preguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPregunta)))
            .andExpect(status().isCreated());

        // Validate the TipoPregunta in the database
        List<TipoPregunta> tipoPreguntaList = tipoPreguntaRepository.findAll();
        assertThat(tipoPreguntaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoPregunta testTipoPregunta = tipoPreguntaList.get(tipoPreguntaList.size() - 1);
        assertThat(testTipoPregunta.getTipoPregunta()).isEqualTo(DEFAULT_TIPO_PREGUNTA);
        assertThat(testTipoPregunta.getTipoDato()).isEqualTo(DEFAULT_TIPO_DATO);
    }

    @Test
    @Transactional
    public void createTipoPreguntaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoPreguntaRepository.findAll().size();

        // Create the TipoPregunta with an existing ID
        tipoPregunta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPreguntaMockMvc.perform(post("/api/tipo-preguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPregunta)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPregunta in the database
        List<TipoPregunta> tipoPreguntaList = tipoPreguntaRepository.findAll();
        assertThat(tipoPreguntaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoPreguntas() throws Exception {
        // Initialize the database
        tipoPreguntaRepository.saveAndFlush(tipoPregunta);

        // Get all the tipoPreguntaList
        restTipoPreguntaMockMvc.perform(get("/api/tipo-preguntas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPregunta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPregunta").value(hasItem(DEFAULT_TIPO_PREGUNTA.toString())))
            .andExpect(jsonPath("$.[*].tipoDato").value(hasItem(DEFAULT_TIPO_DATO.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoPregunta() throws Exception {
        // Initialize the database
        tipoPreguntaRepository.saveAndFlush(tipoPregunta);

        // Get the tipoPregunta
        restTipoPreguntaMockMvc.perform(get("/api/tipo-preguntas/{id}", tipoPregunta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPregunta.getId().intValue()))
            .andExpect(jsonPath("$.tipoPregunta").value(DEFAULT_TIPO_PREGUNTA.toString()))
            .andExpect(jsonPath("$.tipoDato").value(DEFAULT_TIPO_DATO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoPregunta() throws Exception {
        // Get the tipoPregunta
        restTipoPreguntaMockMvc.perform(get("/api/tipo-preguntas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoPregunta() throws Exception {
        // Initialize the database
        tipoPreguntaService.save(tipoPregunta);

        int databaseSizeBeforeUpdate = tipoPreguntaRepository.findAll().size();

        // Update the tipoPregunta
        TipoPregunta updatedTipoPregunta = tipoPreguntaRepository.findById(tipoPregunta.getId()).get();
        // Disconnect from session so that the updates on updatedTipoPregunta are not directly saved in db
        em.detach(updatedTipoPregunta);
        updatedTipoPregunta
            .tipoPregunta(UPDATED_TIPO_PREGUNTA)
            .tipoDato(UPDATED_TIPO_DATO);

        restTipoPreguntaMockMvc.perform(put("/api/tipo-preguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoPregunta)))
            .andExpect(status().isOk());

        // Validate the TipoPregunta in the database
        List<TipoPregunta> tipoPreguntaList = tipoPreguntaRepository.findAll();
        assertThat(tipoPreguntaList).hasSize(databaseSizeBeforeUpdate);
        TipoPregunta testTipoPregunta = tipoPreguntaList.get(tipoPreguntaList.size() - 1);
        assertThat(testTipoPregunta.getTipoPregunta()).isEqualTo(UPDATED_TIPO_PREGUNTA);
        assertThat(testTipoPregunta.getTipoDato()).isEqualTo(UPDATED_TIPO_DATO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoPregunta() throws Exception {
        int databaseSizeBeforeUpdate = tipoPreguntaRepository.findAll().size();

        // Create the TipoPregunta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPreguntaMockMvc.perform(put("/api/tipo-preguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoPregunta)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPregunta in the database
        List<TipoPregunta> tipoPreguntaList = tipoPreguntaRepository.findAll();
        assertThat(tipoPreguntaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoPregunta() throws Exception {
        // Initialize the database
        tipoPreguntaService.save(tipoPregunta);

        int databaseSizeBeforeDelete = tipoPreguntaRepository.findAll().size();

        // Delete the tipoPregunta
        restTipoPreguntaMockMvc.perform(delete("/api/tipo-preguntas/{id}", tipoPregunta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TipoPregunta> tipoPreguntaList = tipoPreguntaRepository.findAll();
        assertThat(tipoPreguntaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPregunta.class);
        TipoPregunta tipoPregunta1 = new TipoPregunta();
        tipoPregunta1.setId(1L);
        TipoPregunta tipoPregunta2 = new TipoPregunta();
        tipoPregunta2.setId(tipoPregunta1.getId());
        assertThat(tipoPregunta1).isEqualTo(tipoPregunta2);
        tipoPregunta2.setId(2L);
        assertThat(tipoPregunta1).isNotEqualTo(tipoPregunta2);
        tipoPregunta1.setId(null);
        assertThat(tipoPregunta1).isNotEqualTo(tipoPregunta2);
    }
}
