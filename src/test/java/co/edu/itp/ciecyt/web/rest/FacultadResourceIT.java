package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Facultad;
import co.edu.itp.ciecyt.repository.FacultadRepository;
import co.edu.itp.ciecyt.service.FacultadService;
import co.edu.itp.ciecyt.service.dto.FacultadDTO;
import co.edu.itp.ciecyt.service.mapper.FacultadMapper;

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
 * Integration tests for the {@link FacultadResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacultadResourceIT {

    private static final String DEFAULT_CODIGO_FACULTAD = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_FACULTAD = "BBBBBBBBBB";

    private static final String DEFAULT_FACULTAD = "AAAAAAAAAA";
    private static final String UPDATED_FACULTAD = "BBBBBBBBBB";

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private FacultadMapper facultadMapper;

    @Autowired
    private FacultadService facultadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacultadMockMvc;

    private Facultad facultad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facultad createEntity(EntityManager em) {
        Facultad facultad = new Facultad()
            .codigoFacultad(DEFAULT_CODIGO_FACULTAD)
            .facultad(DEFAULT_FACULTAD);
        return facultad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facultad createUpdatedEntity(EntityManager em) {
        Facultad facultad = new Facultad()
            .codigoFacultad(UPDATED_CODIGO_FACULTAD)
            .facultad(UPDATED_FACULTAD);
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
        FacultadDTO facultadDTO = facultadMapper.toDto(facultad);
        restFacultadMockMvc.perform(post("/api/facultads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facultadDTO)))
            .andExpect(status().isCreated());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeCreate + 1);
        Facultad testFacultad = facultadList.get(facultadList.size() - 1);
        assertThat(testFacultad.getCodigoFacultad()).isEqualTo(DEFAULT_CODIGO_FACULTAD);
        assertThat(testFacultad.getFacultad()).isEqualTo(DEFAULT_FACULTAD);
    }

    @Test
    @Transactional
    public void createFacultadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facultadRepository.findAll().size();

        // Create the Facultad with an existing ID
        facultad.setId(1L);
        FacultadDTO facultadDTO = facultadMapper.toDto(facultad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacultadMockMvc.perform(post("/api/facultads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facultadDTO)))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facultad.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoFacultad").value(hasItem(DEFAULT_CODIGO_FACULTAD)))
            .andExpect(jsonPath("$.[*].facultad").value(hasItem(DEFAULT_FACULTAD)));
    }
    
    @Test
    @Transactional
    public void getFacultad() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        // Get the facultad
        restFacultadMockMvc.perform(get("/api/facultads/{id}", facultad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facultad.getId().intValue()))
            .andExpect(jsonPath("$.codigoFacultad").value(DEFAULT_CODIGO_FACULTAD))
            .andExpect(jsonPath("$.facultad").value(DEFAULT_FACULTAD));
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
        facultadRepository.saveAndFlush(facultad);

        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();

        // Update the facultad
        Facultad updatedFacultad = facultadRepository.findById(facultad.getId()).get();
        // Disconnect from session so that the updates on updatedFacultad are not directly saved in db
        em.detach(updatedFacultad);
        updatedFacultad
            .codigoFacultad(UPDATED_CODIGO_FACULTAD)
            .facultad(UPDATED_FACULTAD);
        FacultadDTO facultadDTO = facultadMapper.toDto(updatedFacultad);

        restFacultadMockMvc.perform(put("/api/facultads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facultadDTO)))
            .andExpect(status().isOk());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
        Facultad testFacultad = facultadList.get(facultadList.size() - 1);
        assertThat(testFacultad.getCodigoFacultad()).isEqualTo(UPDATED_CODIGO_FACULTAD);
        assertThat(testFacultad.getFacultad()).isEqualTo(UPDATED_FACULTAD);
    }

    @Test
    @Transactional
    public void updateNonExistingFacultad() throws Exception {
        int databaseSizeBeforeUpdate = facultadRepository.findAll().size();

        // Create the Facultad
        FacultadDTO facultadDTO = facultadMapper.toDto(facultad);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacultadMockMvc.perform(put("/api/facultads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facultadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Facultad in the database
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacultad() throws Exception {
        // Initialize the database
        facultadRepository.saveAndFlush(facultad);

        int databaseSizeBeforeDelete = facultadRepository.findAll().size();

        // Delete the facultad
        restFacultadMockMvc.perform(delete("/api/facultads/{id}", facultad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facultad> facultadList = facultadRepository.findAll();
        assertThat(facultadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
