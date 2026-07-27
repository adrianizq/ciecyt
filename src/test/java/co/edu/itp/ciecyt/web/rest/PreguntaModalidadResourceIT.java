package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.PreguntaModalidad;
import co.edu.itp.ciecyt.repository.PreguntaModalidadRepository;
import co.edu.itp.ciecyt.service.PreguntaModalidadService;
import co.edu.itp.ciecyt.service.dto.PreguntaModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.PreguntaModalidadMapper;

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
 * Integration tests for the {@link PreguntaModalidadResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PreguntaModalidadResourceIT {

    @Autowired
    private PreguntaModalidadRepository preguntaModalidadRepository;

    @Autowired
    private PreguntaModalidadMapper preguntaModalidadMapper;

    @Autowired
    private PreguntaModalidadService preguntaModalidadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreguntaModalidadMockMvc;

    private PreguntaModalidad preguntaModalidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreguntaModalidad createEntity(EntityManager em) {
        PreguntaModalidad preguntaModalidad = new PreguntaModalidad();
        return preguntaModalidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreguntaModalidad createUpdatedEntity(EntityManager em) {
        PreguntaModalidad preguntaModalidad = new PreguntaModalidad();
        return preguntaModalidad;
    }

    @BeforeEach
    public void initTest() {
        preguntaModalidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreguntaModalidad() throws Exception {
        int databaseSizeBeforeCreate = preguntaModalidadRepository.findAll().size();
        // Create the PreguntaModalidad
        PreguntaModalidadDTO preguntaModalidadDTO = preguntaModalidadMapper.toDto(preguntaModalidad);
        restPreguntaModalidadMockMvc.perform(post("/api/pregunta-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preguntaModalidadDTO)))
            .andExpect(status().isCreated());

        // Validate the PreguntaModalidad in the database
        List<PreguntaModalidad> preguntaModalidadList = preguntaModalidadRepository.findAll();
        assertThat(preguntaModalidadList).hasSize(databaseSizeBeforeCreate + 1);
        PreguntaModalidad testPreguntaModalidad = preguntaModalidadList.get(preguntaModalidadList.size() - 1);
    }

    @Test
    @Transactional
    public void createPreguntaModalidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preguntaModalidadRepository.findAll().size();

        // Create the PreguntaModalidad with an existing ID
        preguntaModalidad.setId(1L);
        PreguntaModalidadDTO preguntaModalidadDTO = preguntaModalidadMapper.toDto(preguntaModalidad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreguntaModalidadMockMvc.perform(post("/api/pregunta-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preguntaModalidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PreguntaModalidad in the database
        List<PreguntaModalidad> preguntaModalidadList = preguntaModalidadRepository.findAll();
        assertThat(preguntaModalidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPreguntaModalidads() throws Exception {
        // Initialize the database
        preguntaModalidadRepository.saveAndFlush(preguntaModalidad);

        // Get all the preguntaModalidadList
        restPreguntaModalidadMockMvc.perform(get("/api/pregunta-modalidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preguntaModalidad.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPreguntaModalidad() throws Exception {
        // Initialize the database
        preguntaModalidadRepository.saveAndFlush(preguntaModalidad);

        // Get the preguntaModalidad
        restPreguntaModalidadMockMvc.perform(get("/api/pregunta-modalidads/{id}", preguntaModalidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preguntaModalidad.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPreguntaModalidad() throws Exception {
        // Get the preguntaModalidad
        restPreguntaModalidadMockMvc.perform(get("/api/pregunta-modalidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreguntaModalidad() throws Exception {
        // Initialize the database
        preguntaModalidadRepository.saveAndFlush(preguntaModalidad);

        int databaseSizeBeforeUpdate = preguntaModalidadRepository.findAll().size();

        // Update the preguntaModalidad
        PreguntaModalidad updatedPreguntaModalidad = preguntaModalidadRepository.findById(preguntaModalidad.getId()).get();
        // Disconnect from session so that the updates on updatedPreguntaModalidad are not directly saved in db
        em.detach(updatedPreguntaModalidad);
        PreguntaModalidadDTO preguntaModalidadDTO = preguntaModalidadMapper.toDto(updatedPreguntaModalidad);

        restPreguntaModalidadMockMvc.perform(put("/api/pregunta-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preguntaModalidadDTO)))
            .andExpect(status().isOk());

        // Validate the PreguntaModalidad in the database
        List<PreguntaModalidad> preguntaModalidadList = preguntaModalidadRepository.findAll();
        assertThat(preguntaModalidadList).hasSize(databaseSizeBeforeUpdate);
        PreguntaModalidad testPreguntaModalidad = preguntaModalidadList.get(preguntaModalidadList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPreguntaModalidad() throws Exception {
        int databaseSizeBeforeUpdate = preguntaModalidadRepository.findAll().size();

        // Create the PreguntaModalidad
        PreguntaModalidadDTO preguntaModalidadDTO = preguntaModalidadMapper.toDto(preguntaModalidad);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreguntaModalidadMockMvc.perform(put("/api/pregunta-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preguntaModalidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PreguntaModalidad in the database
        List<PreguntaModalidad> preguntaModalidadList = preguntaModalidadRepository.findAll();
        assertThat(preguntaModalidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePreguntaModalidad() throws Exception {
        // Initialize the database
        preguntaModalidadRepository.saveAndFlush(preguntaModalidad);

        int databaseSizeBeforeDelete = preguntaModalidadRepository.findAll().size();

        // Delete the preguntaModalidad
        restPreguntaModalidadMockMvc.perform(delete("/api/pregunta-modalidads/{id}", preguntaModalidad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreguntaModalidad> preguntaModalidadList = preguntaModalidadRepository.findAll();
        assertThat(preguntaModalidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
