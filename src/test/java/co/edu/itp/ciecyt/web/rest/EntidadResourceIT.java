package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Entidad;
import co.edu.itp.ciecyt.repository.EntidadRepository;
import co.edu.itp.ciecyt.service.EntidadService;
import co.edu.itp.ciecyt.service.dto.EntidadDTO;
import co.edu.itp.ciecyt.service.mapper.EntidadMapper;

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
 * Integration tests for the {@link EntidadResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EntidadResourceIT {

    private static final String DEFAULT_ENTIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ENTIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_NIT = "AAAAAAAAAA";
    private static final String UPDATED_NIT = "BBBBBBBBBB";

    @Autowired
    private EntidadRepository entidadRepository;

    @Autowired
    private EntidadMapper entidadMapper;

    @Autowired
    private EntidadService entidadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntidadMockMvc;

    private Entidad entidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidad createEntity(EntityManager em) {
        Entidad entidad = new Entidad()
            .entidad(DEFAULT_ENTIDAD)
            .nit(DEFAULT_NIT);
        return entidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidad createUpdatedEntity(EntityManager em) {
        Entidad entidad = new Entidad()
            .entidad(UPDATED_ENTIDAD)
            .nit(UPDATED_NIT);
        return entidad;
    }

    @BeforeEach
    public void initTest() {
        entidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntidad() throws Exception {
        int databaseSizeBeforeCreate = entidadRepository.findAll().size();
        // Create the Entidad
        EntidadDTO entidadDTO = entidadMapper.toDto(entidad);
        restEntidadMockMvc.perform(post("/api/entidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidadDTO)))
            .andExpect(status().isCreated());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeCreate + 1);
        Entidad testEntidad = entidadList.get(entidadList.size() - 1);
        assertThat(testEntidad.getEntidad()).isEqualTo(DEFAULT_ENTIDAD);
        assertThat(testEntidad.getNit()).isEqualTo(DEFAULT_NIT);
    }

    @Test
    @Transactional
    public void createEntidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entidadRepository.findAll().size();

        // Create the Entidad with an existing ID
        entidad.setId(1L);
        EntidadDTO entidadDTO = entidadMapper.toDto(entidad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadMockMvc.perform(post("/api/entidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEntidads() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        // Get all the entidadList
        restEntidadMockMvc.perform(get("/api/entidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].entidad").value(hasItem(DEFAULT_ENTIDAD)))
            .andExpect(jsonPath("$.[*].nit").value(hasItem(DEFAULT_NIT)));
    }
    
    @Test
    @Transactional
    public void getEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        // Get the entidad
        restEntidadMockMvc.perform(get("/api/entidads/{id}", entidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entidad.getId().intValue()))
            .andExpect(jsonPath("$.entidad").value(DEFAULT_ENTIDAD))
            .andExpect(jsonPath("$.nit").value(DEFAULT_NIT));
    }
    @Test
    @Transactional
    public void getNonExistingEntidad() throws Exception {
        // Get the entidad
        restEntidadMockMvc.perform(get("/api/entidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();

        // Update the entidad
        Entidad updatedEntidad = entidadRepository.findById(entidad.getId()).get();
        // Disconnect from session so that the updates on updatedEntidad are not directly saved in db
        em.detach(updatedEntidad);
        updatedEntidad
            .entidad(UPDATED_ENTIDAD)
            .nit(UPDATED_NIT);
        EntidadDTO entidadDTO = entidadMapper.toDto(updatedEntidad);

        restEntidadMockMvc.perform(put("/api/entidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidadDTO)))
            .andExpect(status().isOk());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
        Entidad testEntidad = entidadList.get(entidadList.size() - 1);
        assertThat(testEntidad.getEntidad()).isEqualTo(UPDATED_ENTIDAD);
        assertThat(testEntidad.getNit()).isEqualTo(UPDATED_NIT);
    }

    @Test
    @Transactional
    public void updateNonExistingEntidad() throws Exception {
        int databaseSizeBeforeUpdate = entidadRepository.findAll().size();

        // Create the Entidad
        EntidadDTO entidadDTO = entidadMapper.toDto(entidad);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadMockMvc.perform(put("/api/entidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(entidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entidad in the database
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEntidad() throws Exception {
        // Initialize the database
        entidadRepository.saveAndFlush(entidad);

        int databaseSizeBeforeDelete = entidadRepository.findAll().size();

        // Delete the entidad
        restEntidadMockMvc.perform(delete("/api/entidads/{id}", entidad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entidad> entidadList = entidadRepository.findAll();
        assertThat(entidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
