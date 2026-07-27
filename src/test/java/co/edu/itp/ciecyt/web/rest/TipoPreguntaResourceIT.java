package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.TipoPregunta;
import co.edu.itp.ciecyt.repository.TipoPreguntaRepository;
import co.edu.itp.ciecyt.service.TipoPreguntaService;
import co.edu.itp.ciecyt.service.dto.TipoPreguntaDTO;
import co.edu.itp.ciecyt.service.mapper.TipoPreguntaMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoPreguntaResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoPreguntaResourceIT {

    private static final String DEFAULT_TIPO_PREGUNTA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PREGUNTA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_DATO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_DATO = "BBBBBBBBBB";

    @Autowired
    private TipoPreguntaRepository tipoPreguntaRepository;

    @Autowired
    private TipoPreguntaMapper tipoPreguntaMapper;

    @Autowired
    private TipoPreguntaService tipoPreguntaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoPreguntaMockMvc;

    private TipoPregunta tipoPregunta;

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
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPregunta createUpdatedEntity(EntityManager em) {
        TipoPregunta tipoPregunta = new TipoPregunta()
            .tipoPregunta(UPDATED_TIPO_PREGUNTA)
            .tipoDato(UPDATED_TIPO_DATO);
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
        TipoPreguntaDTO tipoPreguntaDTO = tipoPreguntaMapper.toDto(tipoPregunta);
        restTipoPreguntaMockMvc.perform(post("/api/tipo-preguntas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPreguntaDTO)))
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
        TipoPreguntaDTO tipoPreguntaDTO = tipoPreguntaMapper.toDto(tipoPregunta);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPreguntaMockMvc.perform(post("/api/tipo-preguntas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPreguntaDTO)))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPregunta.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPregunta").value(hasItem(DEFAULT_TIPO_PREGUNTA)))
            .andExpect(jsonPath("$.[*].tipoDato").value(hasItem(DEFAULT_TIPO_DATO)));
    }
    
    @Test
    @Transactional
    public void getTipoPregunta() throws Exception {
        // Initialize the database
        tipoPreguntaRepository.saveAndFlush(tipoPregunta);

        // Get the tipoPregunta
        restTipoPreguntaMockMvc.perform(get("/api/tipo-preguntas/{id}", tipoPregunta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPregunta.getId().intValue()))
            .andExpect(jsonPath("$.tipoPregunta").value(DEFAULT_TIPO_PREGUNTA))
            .andExpect(jsonPath("$.tipoDato").value(DEFAULT_TIPO_DATO));
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
        tipoPreguntaRepository.saveAndFlush(tipoPregunta);

        int databaseSizeBeforeUpdate = tipoPreguntaRepository.findAll().size();

        // Update the tipoPregunta
        TipoPregunta updatedTipoPregunta = tipoPreguntaRepository.findById(tipoPregunta.getId()).get();
        // Disconnect from session so that the updates on updatedTipoPregunta are not directly saved in db
        em.detach(updatedTipoPregunta);
        updatedTipoPregunta
            .tipoPregunta(UPDATED_TIPO_PREGUNTA)
            .tipoDato(UPDATED_TIPO_DATO);
        TipoPreguntaDTO tipoPreguntaDTO = tipoPreguntaMapper.toDto(updatedTipoPregunta);

        restTipoPreguntaMockMvc.perform(put("/api/tipo-preguntas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPreguntaDTO)))
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
        TipoPreguntaDTO tipoPreguntaDTO = tipoPreguntaMapper.toDto(tipoPregunta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPreguntaMockMvc.perform(put("/api/tipo-preguntas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPreguntaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPregunta in the database
        List<TipoPregunta> tipoPreguntaList = tipoPreguntaRepository.findAll();
        assertThat(tipoPreguntaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoPregunta() throws Exception {
        // Initialize the database
        tipoPreguntaRepository.saveAndFlush(tipoPregunta);

        int databaseSizeBeforeDelete = tipoPreguntaRepository.findAll().size();

        // Delete the tipoPregunta
        restTipoPreguntaMockMvc.perform(delete("/api/tipo-preguntas/{id}", tipoPregunta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoPregunta> tipoPreguntaList = tipoPreguntaRepository.findAll();
        assertThat(tipoPreguntaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
