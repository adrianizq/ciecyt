package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Rubro;
import co.edu.itp.ciecyt.repository.RubroRepository;
import co.edu.itp.ciecyt.service.RubroService;
import co.edu.itp.ciecyt.service.dto.RubroDTO;
import co.edu.itp.ciecyt.service.mapper.RubroMapper;

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
 * Integration tests for the {@link RubroResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RubroResourceIT {

    private static final String DEFAULT_RUBRO = "AAAAAAAAAA";
    private static final String UPDATED_RUBRO = "BBBBBBBBBB";

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private RubroMapper rubroMapper;

    @Autowired
    private RubroService rubroService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRubroMockMvc;

    private Rubro rubro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rubro createEntity(EntityManager em) {
        Rubro rubro = new Rubro()
            .rubro(DEFAULT_RUBRO);
        return rubro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rubro createUpdatedEntity(EntityManager em) {
        Rubro rubro = new Rubro()
            .rubro(UPDATED_RUBRO);
        return rubro;
    }

    @BeforeEach
    public void initTest() {
        rubro = createEntity(em);
    }

    @Test
    @Transactional
    public void createRubro() throws Exception {
        int databaseSizeBeforeCreate = rubroRepository.findAll().size();
        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);
        restRubroMockMvc.perform(post("/api/rubros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rubroDTO)))
            .andExpect(status().isCreated());

        // Validate the Rubro in the database
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeCreate + 1);
        Rubro testRubro = rubroList.get(rubroList.size() - 1);
        assertThat(testRubro.getRubro()).isEqualTo(DEFAULT_RUBRO);
    }

    @Test
    @Transactional
    public void createRubroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rubroRepository.findAll().size();

        // Create the Rubro with an existing ID
        rubro.setId(1L);
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRubroMockMvc.perform(post("/api/rubros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rubroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rubro in the database
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRubros() throws Exception {
        // Initialize the database
        rubroRepository.saveAndFlush(rubro);

        // Get all the rubroList
        restRubroMockMvc.perform(get("/api/rubros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rubro.getId().intValue())))
            .andExpect(jsonPath("$.[*].rubro").value(hasItem(DEFAULT_RUBRO)));
    }
    
    @Test
    @Transactional
    public void getRubro() throws Exception {
        // Initialize the database
        rubroRepository.saveAndFlush(rubro);

        // Get the rubro
        restRubroMockMvc.perform(get("/api/rubros/{id}", rubro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rubro.getId().intValue()))
            .andExpect(jsonPath("$.rubro").value(DEFAULT_RUBRO));
    }
    @Test
    @Transactional
    public void getNonExistingRubro() throws Exception {
        // Get the rubro
        restRubroMockMvc.perform(get("/api/rubros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRubro() throws Exception {
        // Initialize the database
        rubroRepository.saveAndFlush(rubro);

        int databaseSizeBeforeUpdate = rubroRepository.findAll().size();

        // Update the rubro
        Rubro updatedRubro = rubroRepository.findById(rubro.getId()).get();
        // Disconnect from session so that the updates on updatedRubro are not directly saved in db
        em.detach(updatedRubro);
        updatedRubro
            .rubro(UPDATED_RUBRO);
        RubroDTO rubroDTO = rubroMapper.toDto(updatedRubro);

        restRubroMockMvc.perform(put("/api/rubros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rubroDTO)))
            .andExpect(status().isOk());

        // Validate the Rubro in the database
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeUpdate);
        Rubro testRubro = rubroList.get(rubroList.size() - 1);
        assertThat(testRubro.getRubro()).isEqualTo(UPDATED_RUBRO);
    }

    @Test
    @Transactional
    public void updateNonExistingRubro() throws Exception {
        int databaseSizeBeforeUpdate = rubroRepository.findAll().size();

        // Create the Rubro
        RubroDTO rubroDTO = rubroMapper.toDto(rubro);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRubroMockMvc.perform(put("/api/rubros")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rubroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rubro in the database
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRubro() throws Exception {
        // Initialize the database
        rubroRepository.saveAndFlush(rubro);

        int databaseSizeBeforeDelete = rubroRepository.findAll().size();

        // Delete the rubro
        restRubroMockMvc.perform(delete("/api/rubros/{id}", rubro.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rubro> rubroList = rubroRepository.findAll();
        assertThat(rubroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
