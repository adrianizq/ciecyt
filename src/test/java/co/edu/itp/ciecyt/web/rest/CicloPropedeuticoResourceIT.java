package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.CicloPropedeutico;
import co.edu.itp.ciecyt.repository.CicloPropedeuticoRepository;
import co.edu.itp.ciecyt.service.CicloPropedeuticoService;
import co.edu.itp.ciecyt.service.dto.CicloPropedeuticoDTO;
import co.edu.itp.ciecyt.service.mapper.CicloPropedeuticoMapper;

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
 * Integration tests for the {@link CicloPropedeuticoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CicloPropedeuticoResourceIT {

    private static final String DEFAULT_CICLO = "AAAAAAAAAA";
    private static final String UPDATED_CICLO = "BBBBBBBBBB";

    @Autowired
    private CicloPropedeuticoRepository cicloPropedeuticoRepository;

    @Autowired
    private CicloPropedeuticoMapper cicloPropedeuticoMapper;

    @Autowired
    private CicloPropedeuticoService cicloPropedeuticoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCicloPropedeuticoMockMvc;

    private CicloPropedeutico cicloPropedeutico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CicloPropedeutico createEntity(EntityManager em) {
        CicloPropedeutico cicloPropedeutico = new CicloPropedeutico()
            .ciclo(DEFAULT_CICLO);
        return cicloPropedeutico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CicloPropedeutico createUpdatedEntity(EntityManager em) {
        CicloPropedeutico cicloPropedeutico = new CicloPropedeutico()
            .ciclo(UPDATED_CICLO);
        return cicloPropedeutico;
    }

    @BeforeEach
    public void initTest() {
        cicloPropedeutico = createEntity(em);
    }

    @Test
    @Transactional
    public void createCicloPropedeutico() throws Exception {
        int databaseSizeBeforeCreate = cicloPropedeuticoRepository.findAll().size();
        // Create the CicloPropedeutico
        CicloPropedeuticoDTO cicloPropedeuticoDTO = cicloPropedeuticoMapper.toDto(cicloPropedeutico);
        restCicloPropedeuticoMockMvc.perform(post("/api/ciclo-propedeuticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cicloPropedeuticoDTO)))
            .andExpect(status().isCreated());

        // Validate the CicloPropedeutico in the database
        List<CicloPropedeutico> cicloPropedeuticoList = cicloPropedeuticoRepository.findAll();
        assertThat(cicloPropedeuticoList).hasSize(databaseSizeBeforeCreate + 1);
        CicloPropedeutico testCicloPropedeutico = cicloPropedeuticoList.get(cicloPropedeuticoList.size() - 1);
        assertThat(testCicloPropedeutico.getCiclo()).isEqualTo(DEFAULT_CICLO);
    }

    @Test
    @Transactional
    public void createCicloPropedeuticoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cicloPropedeuticoRepository.findAll().size();

        // Create the CicloPropedeutico with an existing ID
        cicloPropedeutico.setId(1L);
        CicloPropedeuticoDTO cicloPropedeuticoDTO = cicloPropedeuticoMapper.toDto(cicloPropedeutico);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCicloPropedeuticoMockMvc.perform(post("/api/ciclo-propedeuticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cicloPropedeuticoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CicloPropedeutico in the database
        List<CicloPropedeutico> cicloPropedeuticoList = cicloPropedeuticoRepository.findAll();
        assertThat(cicloPropedeuticoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCicloPropedeuticos() throws Exception {
        // Initialize the database
        cicloPropedeuticoRepository.saveAndFlush(cicloPropedeutico);

        // Get all the cicloPropedeuticoList
        restCicloPropedeuticoMockMvc.perform(get("/api/ciclo-propedeuticos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cicloPropedeutico.getId().intValue())))
            .andExpect(jsonPath("$.[*].ciclo").value(hasItem(DEFAULT_CICLO)));
    }
    
    @Test
    @Transactional
    public void getCicloPropedeutico() throws Exception {
        // Initialize the database
        cicloPropedeuticoRepository.saveAndFlush(cicloPropedeutico);

        // Get the cicloPropedeutico
        restCicloPropedeuticoMockMvc.perform(get("/api/ciclo-propedeuticos/{id}", cicloPropedeutico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cicloPropedeutico.getId().intValue()))
            .andExpect(jsonPath("$.ciclo").value(DEFAULT_CICLO));
    }
    @Test
    @Transactional
    public void getNonExistingCicloPropedeutico() throws Exception {
        // Get the cicloPropedeutico
        restCicloPropedeuticoMockMvc.perform(get("/api/ciclo-propedeuticos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCicloPropedeutico() throws Exception {
        // Initialize the database
        cicloPropedeuticoRepository.saveAndFlush(cicloPropedeutico);

        int databaseSizeBeforeUpdate = cicloPropedeuticoRepository.findAll().size();

        // Update the cicloPropedeutico
        CicloPropedeutico updatedCicloPropedeutico = cicloPropedeuticoRepository.findById(cicloPropedeutico.getId()).get();
        // Disconnect from session so that the updates on updatedCicloPropedeutico are not directly saved in db
        em.detach(updatedCicloPropedeutico);
        updatedCicloPropedeutico
            .ciclo(UPDATED_CICLO);
        CicloPropedeuticoDTO cicloPropedeuticoDTO = cicloPropedeuticoMapper.toDto(updatedCicloPropedeutico);

        restCicloPropedeuticoMockMvc.perform(put("/api/ciclo-propedeuticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cicloPropedeuticoDTO)))
            .andExpect(status().isOk());

        // Validate the CicloPropedeutico in the database
        List<CicloPropedeutico> cicloPropedeuticoList = cicloPropedeuticoRepository.findAll();
        assertThat(cicloPropedeuticoList).hasSize(databaseSizeBeforeUpdate);
        CicloPropedeutico testCicloPropedeutico = cicloPropedeuticoList.get(cicloPropedeuticoList.size() - 1);
        assertThat(testCicloPropedeutico.getCiclo()).isEqualTo(UPDATED_CICLO);
    }

    @Test
    @Transactional
    public void updateNonExistingCicloPropedeutico() throws Exception {
        int databaseSizeBeforeUpdate = cicloPropedeuticoRepository.findAll().size();

        // Create the CicloPropedeutico
        CicloPropedeuticoDTO cicloPropedeuticoDTO = cicloPropedeuticoMapper.toDto(cicloPropedeutico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCicloPropedeuticoMockMvc.perform(put("/api/ciclo-propedeuticos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cicloPropedeuticoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CicloPropedeutico in the database
        List<CicloPropedeutico> cicloPropedeuticoList = cicloPropedeuticoRepository.findAll();
        assertThat(cicloPropedeuticoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCicloPropedeutico() throws Exception {
        // Initialize the database
        cicloPropedeuticoRepository.saveAndFlush(cicloPropedeutico);

        int databaseSizeBeforeDelete = cicloPropedeuticoRepository.findAll().size();

        // Delete the cicloPropedeutico
        restCicloPropedeuticoMockMvc.perform(delete("/api/ciclo-propedeuticos/{id}", cicloPropedeutico.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CicloPropedeutico> cicloPropedeuticoList = cicloPropedeuticoRepository.findAll();
        assertThat(cicloPropedeuticoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
