package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.Preguntas;
import com.itp.ciecyt.repository.PreguntasRepository;
import com.itp.ciecyt.service.PreguntasService;
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
 * Integration tests for the {@Link PreguntasResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class PreguntasResourceIT {

    private static final String DEFAULT_PREGUNTA = "AAAAAAAAAA";
    private static final String UPDATED_PREGUNTA = "BBBBBBBBBB";

    @Autowired
    private PreguntasRepository preguntasRepository;

    @Autowired
    private PreguntasService preguntasService;

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

    private MockMvc restPreguntasMockMvc;

    private Preguntas preguntas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreguntasResource preguntasResource = new PreguntasResource(preguntasService);
        this.restPreguntasMockMvc = MockMvcBuilders.standaloneSetup(preguntasResource)
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
    public static Preguntas createEntity(EntityManager em) {
        Preguntas preguntas = new Preguntas()
            .pregunta(DEFAULT_PREGUNTA);
        return preguntas;
    }

    @BeforeEach
    public void initTest() {
        preguntas = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreguntas() throws Exception {
        int databaseSizeBeforeCreate = preguntasRepository.findAll().size();

        // Create the Preguntas
        restPreguntasMockMvc.perform(post("/api/preguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preguntas)))
            .andExpect(status().isCreated());

        // Validate the Preguntas in the database
        List<Preguntas> preguntasList = preguntasRepository.findAll();
        assertThat(preguntasList).hasSize(databaseSizeBeforeCreate + 1);
        Preguntas testPreguntas = preguntasList.get(preguntasList.size() - 1);
        assertThat(testPreguntas.getPregunta()).isEqualTo(DEFAULT_PREGUNTA);
    }

    @Test
    @Transactional
    public void createPreguntasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preguntasRepository.findAll().size();

        // Create the Preguntas with an existing ID
        preguntas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreguntasMockMvc.perform(post("/api/preguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preguntas)))
            .andExpect(status().isBadRequest());

        // Validate the Preguntas in the database
        List<Preguntas> preguntasList = preguntasRepository.findAll();
        assertThat(preguntasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPreguntas() throws Exception {
        // Initialize the database
        preguntasRepository.saveAndFlush(preguntas);

        // Get all the preguntasList
        restPreguntasMockMvc.perform(get("/api/preguntas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preguntas.getId().intValue())))
            .andExpect(jsonPath("$.[*].pregunta").value(hasItem(DEFAULT_PREGUNTA.toString())));
    }
    
    @Test
    @Transactional
    public void getPreguntas() throws Exception {
        // Initialize the database
        preguntasRepository.saveAndFlush(preguntas);

        // Get the preguntas
        restPreguntasMockMvc.perform(get("/api/preguntas/{id}", preguntas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preguntas.getId().intValue()))
            .andExpect(jsonPath("$.pregunta").value(DEFAULT_PREGUNTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPreguntas() throws Exception {
        // Get the preguntas
        restPreguntasMockMvc.perform(get("/api/preguntas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreguntas() throws Exception {
        // Initialize the database
        preguntasService.save(preguntas);

        int databaseSizeBeforeUpdate = preguntasRepository.findAll().size();

        // Update the preguntas
        Preguntas updatedPreguntas = preguntasRepository.findById(preguntas.getId()).get();
        // Disconnect from session so that the updates on updatedPreguntas are not directly saved in db
        em.detach(updatedPreguntas);
        updatedPreguntas
            .pregunta(UPDATED_PREGUNTA);

        restPreguntasMockMvc.perform(put("/api/preguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreguntas)))
            .andExpect(status().isOk());

        // Validate the Preguntas in the database
        List<Preguntas> preguntasList = preguntasRepository.findAll();
        assertThat(preguntasList).hasSize(databaseSizeBeforeUpdate);
        Preguntas testPreguntas = preguntasList.get(preguntasList.size() - 1);
        assertThat(testPreguntas.getPregunta()).isEqualTo(UPDATED_PREGUNTA);
    }

    @Test
    @Transactional
    public void updateNonExistingPreguntas() throws Exception {
        int databaseSizeBeforeUpdate = preguntasRepository.findAll().size();

        // Create the Preguntas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreguntasMockMvc.perform(put("/api/preguntas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preguntas)))
            .andExpect(status().isBadRequest());

        // Validate the Preguntas in the database
        List<Preguntas> preguntasList = preguntasRepository.findAll();
        assertThat(preguntasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePreguntas() throws Exception {
        // Initialize the database
        preguntasService.save(preguntas);

        int databaseSizeBeforeDelete = preguntasRepository.findAll().size();

        // Delete the preguntas
        restPreguntasMockMvc.perform(delete("/api/preguntas/{id}", preguntas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Preguntas> preguntasList = preguntasRepository.findAll();
        assertThat(preguntasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Preguntas.class);
        Preguntas preguntas1 = new Preguntas();
        preguntas1.setId(1L);
        Preguntas preguntas2 = new Preguntas();
        preguntas2.setId(preguntas1.getId());
        assertThat(preguntas1).isEqualTo(preguntas2);
        preguntas2.setId(2L);
        assertThat(preguntas1).isNotEqualTo(preguntas2);
        preguntas1.setId(null);
        assertThat(preguntas1).isNotEqualTo(preguntas2);
    }
}
