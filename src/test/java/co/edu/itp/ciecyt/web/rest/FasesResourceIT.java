package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Fases;
import co.edu.itp.ciecyt.repository.FasesRepository;
import co.edu.itp.ciecyt.service.FasesService;
import co.edu.itp.ciecyt.service.dto.FasesDTO;
import co.edu.itp.ciecyt.service.mapper.FasesMapper;

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
 * Integration tests for the {@link FasesResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FasesResourceIT {

    private static final String DEFAULT_FASE = "AAAAAAAAAA";
    private static final String UPDATED_FASE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NOTIFICABLE = false;
    private static final Boolean UPDATED_NOTIFICABLE = true;

    @Autowired
    private FasesRepository fasesRepository;

    @Autowired
    private FasesMapper fasesMapper;

    @Autowired
    private FasesService fasesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFasesMockMvc;

    private Fases fases;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fases createEntity(EntityManager em) {
        Fases fases = new Fases()
            .fase(DEFAULT_FASE)
            .notificable(DEFAULT_NOTIFICABLE);
        return fases;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fases createUpdatedEntity(EntityManager em) {
        Fases fases = new Fases()
            .fase(UPDATED_FASE)
            .notificable(UPDATED_NOTIFICABLE);
        return fases;
    }

    @BeforeEach
    public void initTest() {
        fases = createEntity(em);
    }

    @Test
    @Transactional
    public void createFases() throws Exception {
        int databaseSizeBeforeCreate = fasesRepository.findAll().size();
        // Create the Fases
        FasesDTO fasesDTO = fasesMapper.toDto(fases);
        restFasesMockMvc.perform(post("/api/fases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fasesDTO)))
            .andExpect(status().isCreated());

        // Validate the Fases in the database
        List<Fases> fasesList = fasesRepository.findAll();
        assertThat(fasesList).hasSize(databaseSizeBeforeCreate + 1);
        Fases testFases = fasesList.get(fasesList.size() - 1);
        assertThat(testFases.getFase()).isEqualTo(DEFAULT_FASE);
        assertThat(testFases.isNotificable()).isEqualTo(DEFAULT_NOTIFICABLE);
    }

    @Test
    @Transactional
    public void createFasesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fasesRepository.findAll().size();

        // Create the Fases with an existing ID
        fases.setId(1L);
        FasesDTO fasesDTO = fasesMapper.toDto(fases);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFasesMockMvc.perform(post("/api/fases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fasesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fases in the database
        List<Fases> fasesList = fasesRepository.findAll();
        assertThat(fasesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFases() throws Exception {
        // Initialize the database
        fasesRepository.saveAndFlush(fases);

        // Get all the fasesList
        restFasesMockMvc.perform(get("/api/fases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fases.getId().intValue())))
            .andExpect(jsonPath("$.[*].fase").value(hasItem(DEFAULT_FASE)))
            .andExpect(jsonPath("$.[*].notificable").value(hasItem(DEFAULT_NOTIFICABLE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFases() throws Exception {
        // Initialize the database
        fasesRepository.saveAndFlush(fases);

        // Get the fases
        restFasesMockMvc.perform(get("/api/fases/{id}", fases.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fases.getId().intValue()))
            .andExpect(jsonPath("$.fase").value(DEFAULT_FASE))
            .andExpect(jsonPath("$.notificable").value(DEFAULT_NOTIFICABLE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFases() throws Exception {
        // Get the fases
        restFasesMockMvc.perform(get("/api/fases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFases() throws Exception {
        // Initialize the database
        fasesRepository.saveAndFlush(fases);

        int databaseSizeBeforeUpdate = fasesRepository.findAll().size();

        // Update the fases
        Fases updatedFases = fasesRepository.findById(fases.getId()).get();
        // Disconnect from session so that the updates on updatedFases are not directly saved in db
        em.detach(updatedFases);
        updatedFases
            .fase(UPDATED_FASE)
            .notificable(UPDATED_NOTIFICABLE);
        FasesDTO fasesDTO = fasesMapper.toDto(updatedFases);

        restFasesMockMvc.perform(put("/api/fases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fasesDTO)))
            .andExpect(status().isOk());

        // Validate the Fases in the database
        List<Fases> fasesList = fasesRepository.findAll();
        assertThat(fasesList).hasSize(databaseSizeBeforeUpdate);
        Fases testFases = fasesList.get(fasesList.size() - 1);
        assertThat(testFases.getFase()).isEqualTo(UPDATED_FASE);
        assertThat(testFases.isNotificable()).isEqualTo(UPDATED_NOTIFICABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingFases() throws Exception {
        int databaseSizeBeforeUpdate = fasesRepository.findAll().size();

        // Create the Fases
        FasesDTO fasesDTO = fasesMapper.toDto(fases);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFasesMockMvc.perform(put("/api/fases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fasesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fases in the database
        List<Fases> fasesList = fasesRepository.findAll();
        assertThat(fasesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFases() throws Exception {
        // Initialize the database
        fasesRepository.saveAndFlush(fases);

        int databaseSizeBeforeDelete = fasesRepository.findAll().size();

        // Delete the fases
        restFasesMockMvc.perform(delete("/api/fases/{id}", fases.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fases> fasesList = fasesRepository.findAll();
        assertThat(fasesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
