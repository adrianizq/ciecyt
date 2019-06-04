package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.PreguntaRespuesta;
import com.itp.ciecyt.repository.PreguntaRespuestaRepository;
import com.itp.ciecyt.service.PreguntaRespuestaService;
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
 * Integration tests for the {@Link PreguntaRespuestaResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class PreguntaRespuestaResourceIT {

    @Autowired
    private PreguntaRespuestaRepository preguntaRespuestaRepository;

    @Autowired
    private PreguntaRespuestaService preguntaRespuestaService;

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

    private MockMvc restPreguntaRespuestaMockMvc;

    private PreguntaRespuesta preguntaRespuesta;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreguntaRespuestaResource preguntaRespuestaResource = new PreguntaRespuestaResource(preguntaRespuestaService);
        this.restPreguntaRespuestaMockMvc = MockMvcBuilders.standaloneSetup(preguntaRespuestaResource)
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
    public static PreguntaRespuesta createEntity(EntityManager em) {
        PreguntaRespuesta preguntaRespuesta = new PreguntaRespuesta();
        return preguntaRespuesta;
    }

    @BeforeEach
    public void initTest() {
        preguntaRespuesta = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreguntaRespuesta() throws Exception {
        int databaseSizeBeforeCreate = preguntaRespuestaRepository.findAll().size();

        // Create the PreguntaRespuesta
        restPreguntaRespuestaMockMvc.perform(post("/api/pregunta-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preguntaRespuesta)))
            .andExpect(status().isCreated());

        // Validate the PreguntaRespuesta in the database
        List<PreguntaRespuesta> preguntaRespuestaList = preguntaRespuestaRepository.findAll();
        assertThat(preguntaRespuestaList).hasSize(databaseSizeBeforeCreate + 1);
        PreguntaRespuesta testPreguntaRespuesta = preguntaRespuestaList.get(preguntaRespuestaList.size() - 1);
    }

    @Test
    @Transactional
    public void createPreguntaRespuestaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preguntaRespuestaRepository.findAll().size();

        // Create the PreguntaRespuesta with an existing ID
        preguntaRespuesta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreguntaRespuestaMockMvc.perform(post("/api/pregunta-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preguntaRespuesta)))
            .andExpect(status().isBadRequest());

        // Validate the PreguntaRespuesta in the database
        List<PreguntaRespuesta> preguntaRespuestaList = preguntaRespuestaRepository.findAll();
        assertThat(preguntaRespuestaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPreguntaRespuestas() throws Exception {
        // Initialize the database
        preguntaRespuestaRepository.saveAndFlush(preguntaRespuesta);

        // Get all the preguntaRespuestaList
        restPreguntaRespuestaMockMvc.perform(get("/api/pregunta-respuestas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preguntaRespuesta.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPreguntaRespuesta() throws Exception {
        // Initialize the database
        preguntaRespuestaRepository.saveAndFlush(preguntaRespuesta);

        // Get the preguntaRespuesta
        restPreguntaRespuestaMockMvc.perform(get("/api/pregunta-respuestas/{id}", preguntaRespuesta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preguntaRespuesta.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPreguntaRespuesta() throws Exception {
        // Get the preguntaRespuesta
        restPreguntaRespuestaMockMvc.perform(get("/api/pregunta-respuestas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreguntaRespuesta() throws Exception {
        // Initialize the database
        preguntaRespuestaService.save(preguntaRespuesta);

        int databaseSizeBeforeUpdate = preguntaRespuestaRepository.findAll().size();

        // Update the preguntaRespuesta
        PreguntaRespuesta updatedPreguntaRespuesta = preguntaRespuestaRepository.findById(preguntaRespuesta.getId()).get();
        // Disconnect from session so that the updates on updatedPreguntaRespuesta are not directly saved in db
        em.detach(updatedPreguntaRespuesta);

        restPreguntaRespuestaMockMvc.perform(put("/api/pregunta-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreguntaRespuesta)))
            .andExpect(status().isOk());

        // Validate the PreguntaRespuesta in the database
        List<PreguntaRespuesta> preguntaRespuestaList = preguntaRespuestaRepository.findAll();
        assertThat(preguntaRespuestaList).hasSize(databaseSizeBeforeUpdate);
        PreguntaRespuesta testPreguntaRespuesta = preguntaRespuestaList.get(preguntaRespuestaList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPreguntaRespuesta() throws Exception {
        int databaseSizeBeforeUpdate = preguntaRespuestaRepository.findAll().size();

        // Create the PreguntaRespuesta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreguntaRespuestaMockMvc.perform(put("/api/pregunta-respuestas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preguntaRespuesta)))
            .andExpect(status().isBadRequest());

        // Validate the PreguntaRespuesta in the database
        List<PreguntaRespuesta> preguntaRespuestaList = preguntaRespuestaRepository.findAll();
        assertThat(preguntaRespuestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePreguntaRespuesta() throws Exception {
        // Initialize the database
        preguntaRespuestaService.save(preguntaRespuesta);

        int databaseSizeBeforeDelete = preguntaRespuestaRepository.findAll().size();

        // Delete the preguntaRespuesta
        restPreguntaRespuestaMockMvc.perform(delete("/api/pregunta-respuestas/{id}", preguntaRespuesta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PreguntaRespuesta> preguntaRespuestaList = preguntaRespuestaRepository.findAll();
        assertThat(preguntaRespuestaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreguntaRespuesta.class);
        PreguntaRespuesta preguntaRespuesta1 = new PreguntaRespuesta();
        preguntaRespuesta1.setId(1L);
        PreguntaRespuesta preguntaRespuesta2 = new PreguntaRespuesta();
        preguntaRespuesta2.setId(preguntaRespuesta1.getId());
        assertThat(preguntaRespuesta1).isEqualTo(preguntaRespuesta2);
        preguntaRespuesta2.setId(2L);
        assertThat(preguntaRespuesta1).isNotEqualTo(preguntaRespuesta2);
        preguntaRespuesta1.setId(null);
        assertThat(preguntaRespuesta1).isNotEqualTo(preguntaRespuesta2);
    }
}
