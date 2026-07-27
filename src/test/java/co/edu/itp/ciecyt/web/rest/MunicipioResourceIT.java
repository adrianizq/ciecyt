package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Municipio;
import co.edu.itp.ciecyt.repository.MunicipioRepository;
import co.edu.itp.ciecyt.service.MunicipioService;
import co.edu.itp.ciecyt.service.dto.MunicipioDTO;
import co.edu.itp.ciecyt.service.mapper.MunicipioMapper;

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
 * Integration tests for the {@link MunicipioResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MunicipioResourceIT {

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_DANE_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DANE_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_DANE_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DANE_MUNICIPIO = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private MunicipioMapper municipioMapper;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMunicipioMockMvc;

    private Municipio municipio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Municipio createEntity(EntityManager em) {
        Municipio municipio = new Municipio()
            .region(DEFAULT_REGION)
            .codigoDaneDepartamento(DEFAULT_CODIGO_DANE_DEPARTAMENTO)
            .departamento(DEFAULT_DEPARTAMENTO)
            .codigoDaneMunicipio(DEFAULT_CODIGO_DANE_MUNICIPIO)
            .municipio(DEFAULT_MUNICIPIO);
        return municipio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Municipio createUpdatedEntity(EntityManager em) {
        Municipio municipio = new Municipio()
            .region(UPDATED_REGION)
            .codigoDaneDepartamento(UPDATED_CODIGO_DANE_DEPARTAMENTO)
            .departamento(UPDATED_DEPARTAMENTO)
            .codigoDaneMunicipio(UPDATED_CODIGO_DANE_MUNICIPIO)
            .municipio(UPDATED_MUNICIPIO);
        return municipio;
    }

    @BeforeEach
    public void initTest() {
        municipio = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipio() throws Exception {
        int databaseSizeBeforeCreate = municipioRepository.findAll().size();
        // Create the Municipio
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);
        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isCreated());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeCreate + 1);
        Municipio testMunicipio = municipioList.get(municipioList.size() - 1);
        assertThat(testMunicipio.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testMunicipio.getCodigoDaneDepartamento()).isEqualTo(DEFAULT_CODIGO_DANE_DEPARTAMENTO);
        assertThat(testMunicipio.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testMunicipio.getCodigoDaneMunicipio()).isEqualTo(DEFAULT_CODIGO_DANE_MUNICIPIO);
        assertThat(testMunicipio.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
    }

    @Test
    @Transactional
    public void createMunicipioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipioRepository.findAll().size();

        // Create the Municipio with an existing ID
        municipio.setId(1L);
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMunicipios() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList
        restMunicipioMockMvc.perform(get("/api/municipios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipio.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].codigoDaneDepartamento").value(hasItem(DEFAULT_CODIGO_DANE_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].departamento").value(hasItem(DEFAULT_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].codigoDaneMunicipio").value(hasItem(DEFAULT_CODIGO_DANE_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)));
    }
    
    @Test
    @Transactional
    public void getMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", municipio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(municipio.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.codigoDaneDepartamento").value(DEFAULT_CODIGO_DANE_DEPARTAMENTO))
            .andExpect(jsonPath("$.departamento").value(DEFAULT_DEPARTAMENTO))
            .andExpect(jsonPath("$.codigoDaneMunicipio").value(DEFAULT_CODIGO_DANE_MUNICIPIO))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO));
    }
    @Test
    @Transactional
    public void getNonExistingMunicipio() throws Exception {
        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        int databaseSizeBeforeUpdate = municipioRepository.findAll().size();

        // Update the municipio
        Municipio updatedMunicipio = municipioRepository.findById(municipio.getId()).get();
        // Disconnect from session so that the updates on updatedMunicipio are not directly saved in db
        em.detach(updatedMunicipio);
        updatedMunicipio
            .region(UPDATED_REGION)
            .codigoDaneDepartamento(UPDATED_CODIGO_DANE_DEPARTAMENTO)
            .departamento(UPDATED_DEPARTAMENTO)
            .codigoDaneMunicipio(UPDATED_CODIGO_DANE_MUNICIPIO)
            .municipio(UPDATED_MUNICIPIO);
        MunicipioDTO municipioDTO = municipioMapper.toDto(updatedMunicipio);

        restMunicipioMockMvc.perform(put("/api/municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isOk());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeUpdate);
        Municipio testMunicipio = municipioList.get(municipioList.size() - 1);
        assertThat(testMunicipio.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testMunicipio.getCodigoDaneDepartamento()).isEqualTo(UPDATED_CODIGO_DANE_DEPARTAMENTO);
        assertThat(testMunicipio.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
        assertThat(testMunicipio.getCodigoDaneMunicipio()).isEqualTo(UPDATED_CODIGO_DANE_MUNICIPIO);
        assertThat(testMunicipio.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipio() throws Exception {
        int databaseSizeBeforeUpdate = municipioRepository.findAll().size();

        // Create the Municipio
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMunicipioMockMvc.perform(put("/api/municipios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        int databaseSizeBeforeDelete = municipioRepository.findAll().size();

        // Delete the municipio
        restMunicipioMockMvc.perform(delete("/api/municipios/{id}", municipio.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
