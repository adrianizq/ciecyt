package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.PreguntaAuthority;
import co.edu.itp.ciecyt.repository.PreguntaAuthorityRepository;
import co.edu.itp.ciecyt.service.PreguntaAuthorityService;
import co.edu.itp.ciecyt.service.dto.PreguntaAuthorityDTO;
import co.edu.itp.ciecyt.service.mapper.PreguntaAuthorityMapper;

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
 * Integration tests for the {@link PreguntaAuthorityResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PreguntaAuthorityResourceIT {

    @Autowired
    private PreguntaAuthorityRepository preguntaAuthorityRepository;

    @Autowired
    private PreguntaAuthorityMapper preguntaAuthorityMapper;

    @Autowired
    private PreguntaAuthorityService preguntaAuthorityService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPreguntaAuthorityMockMvc;

    private PreguntaAuthority preguntaAuthority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreguntaAuthority createEntity(EntityManager em) {
        PreguntaAuthority preguntaAuthority = new PreguntaAuthority();
        return preguntaAuthority;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PreguntaAuthority createUpdatedEntity(EntityManager em) {
        PreguntaAuthority preguntaAuthority = new PreguntaAuthority();
        return preguntaAuthority;
    }

    @BeforeEach
    public void initTest() {
        preguntaAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreguntaAuthority() throws Exception {
        int databaseSizeBeforeCreate = preguntaAuthorityRepository.findAll().size();
        // Create the PreguntaAuthority
        PreguntaAuthorityDTO preguntaAuthorityDTO = preguntaAuthorityMapper.toDto(preguntaAuthority);
        restPreguntaAuthorityMockMvc.perform(post("/api/pregunta-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preguntaAuthorityDTO)))
            .andExpect(status().isCreated());

        // Validate the PreguntaAuthority in the database
        List<PreguntaAuthority> preguntaAuthorityList = preguntaAuthorityRepository.findAll();
        assertThat(preguntaAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        PreguntaAuthority testPreguntaAuthority = preguntaAuthorityList.get(preguntaAuthorityList.size() - 1);
    }

    @Test
    @Transactional
    public void createPreguntaAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preguntaAuthorityRepository.findAll().size();

        // Create the PreguntaAuthority with an existing ID
        preguntaAuthority.setId(1L);
        PreguntaAuthorityDTO preguntaAuthorityDTO = preguntaAuthorityMapper.toDto(preguntaAuthority);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreguntaAuthorityMockMvc.perform(post("/api/pregunta-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preguntaAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PreguntaAuthority in the database
        List<PreguntaAuthority> preguntaAuthorityList = preguntaAuthorityRepository.findAll();
        assertThat(preguntaAuthorityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPreguntaAuthorities() throws Exception {
        // Initialize the database
        preguntaAuthorityRepository.saveAndFlush(preguntaAuthority);

        // Get all the preguntaAuthorityList
        restPreguntaAuthorityMockMvc.perform(get("/api/pregunta-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preguntaAuthority.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getPreguntaAuthority() throws Exception {
        // Initialize the database
        preguntaAuthorityRepository.saveAndFlush(preguntaAuthority);

        // Get the preguntaAuthority
        restPreguntaAuthorityMockMvc.perform(get("/api/pregunta-authorities/{id}", preguntaAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(preguntaAuthority.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingPreguntaAuthority() throws Exception {
        // Get the preguntaAuthority
        restPreguntaAuthorityMockMvc.perform(get("/api/pregunta-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreguntaAuthority() throws Exception {
        // Initialize the database
        preguntaAuthorityRepository.saveAndFlush(preguntaAuthority);

        int databaseSizeBeforeUpdate = preguntaAuthorityRepository.findAll().size();

        // Update the preguntaAuthority
        PreguntaAuthority updatedPreguntaAuthority = preguntaAuthorityRepository.findById(preguntaAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedPreguntaAuthority are not directly saved in db
        em.detach(updatedPreguntaAuthority);
        PreguntaAuthorityDTO preguntaAuthorityDTO = preguntaAuthorityMapper.toDto(updatedPreguntaAuthority);

        restPreguntaAuthorityMockMvc.perform(put("/api/pregunta-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preguntaAuthorityDTO)))
            .andExpect(status().isOk());

        // Validate the PreguntaAuthority in the database
        List<PreguntaAuthority> preguntaAuthorityList = preguntaAuthorityRepository.findAll();
        assertThat(preguntaAuthorityList).hasSize(databaseSizeBeforeUpdate);
        PreguntaAuthority testPreguntaAuthority = preguntaAuthorityList.get(preguntaAuthorityList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPreguntaAuthority() throws Exception {
        int databaseSizeBeforeUpdate = preguntaAuthorityRepository.findAll().size();

        // Create the PreguntaAuthority
        PreguntaAuthorityDTO preguntaAuthorityDTO = preguntaAuthorityMapper.toDto(preguntaAuthority);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPreguntaAuthorityMockMvc.perform(put("/api/pregunta-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(preguntaAuthorityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PreguntaAuthority in the database
        List<PreguntaAuthority> preguntaAuthorityList = preguntaAuthorityRepository.findAll();
        assertThat(preguntaAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePreguntaAuthority() throws Exception {
        // Initialize the database
        preguntaAuthorityRepository.saveAndFlush(preguntaAuthority);

        int databaseSizeBeforeDelete = preguntaAuthorityRepository.findAll().size();

        // Delete the preguntaAuthority
        restPreguntaAuthorityMockMvc.perform(delete("/api/pregunta-authorities/{id}", preguntaAuthority.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PreguntaAuthority> preguntaAuthorityList = preguntaAuthorityRepository.findAll();
        assertThat(preguntaAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
