package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Acuerdo;
import co.edu.itp.ciecyt.repository.AcuerdoRepository;
import co.edu.itp.ciecyt.service.AcuerdoService;
import co.edu.itp.ciecyt.service.dto.AcuerdoDTO;
import co.edu.itp.ciecyt.service.mapper.AcuerdoMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AcuerdoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AcuerdoResourceIT {

    private static final String DEFAULT_ACUERDO = "AAAAAAAAAA";
    private static final String UPDATED_ACUERDO = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AcuerdoRepository acuerdoRepository;

    @Autowired
    private AcuerdoMapper acuerdoMapper;

    @Autowired
    private AcuerdoService acuerdoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAcuerdoMockMvc;

    private Acuerdo acuerdo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acuerdo createEntity(EntityManager em) {
        Acuerdo acuerdo = new Acuerdo()
            .acuerdo(DEFAULT_ACUERDO)
            .version(DEFAULT_VERSION)
            .codigo(DEFAULT_CODIGO)
            .fecha(DEFAULT_FECHA);
        return acuerdo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acuerdo createUpdatedEntity(EntityManager em) {
        Acuerdo acuerdo = new Acuerdo()
            .acuerdo(UPDATED_ACUERDO)
            .version(UPDATED_VERSION)
            .codigo(UPDATED_CODIGO)
            .fecha(UPDATED_FECHA);
        return acuerdo;
    }

    @BeforeEach
    public void initTest() {
        acuerdo = createEntity(em);
    }

    @Test
    @Transactional
    public void createAcuerdo() throws Exception {
        int databaseSizeBeforeCreate = acuerdoRepository.findAll().size();
        // Create the Acuerdo
        AcuerdoDTO acuerdoDTO = acuerdoMapper.toDto(acuerdo);
        restAcuerdoMockMvc.perform(post("/api/acuerdos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acuerdoDTO)))
            .andExpect(status().isCreated());

        // Validate the Acuerdo in the database
        List<Acuerdo> acuerdoList = acuerdoRepository.findAll();
        assertThat(acuerdoList).hasSize(databaseSizeBeforeCreate + 1);
        Acuerdo testAcuerdo = acuerdoList.get(acuerdoList.size() - 1);
        assertThat(testAcuerdo.getAcuerdo()).isEqualTo(DEFAULT_ACUERDO);
        assertThat(testAcuerdo.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testAcuerdo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testAcuerdo.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    public void createAcuerdoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acuerdoRepository.findAll().size();

        // Create the Acuerdo with an existing ID
        acuerdo.setId(1L);
        AcuerdoDTO acuerdoDTO = acuerdoMapper.toDto(acuerdo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcuerdoMockMvc.perform(post("/api/acuerdos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acuerdoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Acuerdo in the database
        List<Acuerdo> acuerdoList = acuerdoRepository.findAll();
        assertThat(acuerdoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAcuerdos() throws Exception {
        // Initialize the database
        acuerdoRepository.saveAndFlush(acuerdo);

        // Get all the acuerdoList
        restAcuerdoMockMvc.perform(get("/api/acuerdos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acuerdo.getId().intValue())))
            .andExpect(jsonPath("$.[*].acuerdo").value(hasItem(DEFAULT_ACUERDO)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }
    
    @Test
    @Transactional
    public void getAcuerdo() throws Exception {
        // Initialize the database
        acuerdoRepository.saveAndFlush(acuerdo);

        // Get the acuerdo
        restAcuerdoMockMvc.perform(get("/api/acuerdos/{id}", acuerdo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acuerdo.getId().intValue()))
            .andExpect(jsonPath("$.acuerdo").value(DEFAULT_ACUERDO))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAcuerdo() throws Exception {
        // Get the acuerdo
        restAcuerdoMockMvc.perform(get("/api/acuerdos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAcuerdo() throws Exception {
        // Initialize the database
        acuerdoRepository.saveAndFlush(acuerdo);

        int databaseSizeBeforeUpdate = acuerdoRepository.findAll().size();

        // Update the acuerdo
        Acuerdo updatedAcuerdo = acuerdoRepository.findById(acuerdo.getId()).get();
        // Disconnect from session so that the updates on updatedAcuerdo are not directly saved in db
        em.detach(updatedAcuerdo);
        updatedAcuerdo
            .acuerdo(UPDATED_ACUERDO)
            .version(UPDATED_VERSION)
            .codigo(UPDATED_CODIGO)
            .fecha(UPDATED_FECHA);
        AcuerdoDTO acuerdoDTO = acuerdoMapper.toDto(updatedAcuerdo);

        restAcuerdoMockMvc.perform(put("/api/acuerdos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acuerdoDTO)))
            .andExpect(status().isOk());

        // Validate the Acuerdo in the database
        List<Acuerdo> acuerdoList = acuerdoRepository.findAll();
        assertThat(acuerdoList).hasSize(databaseSizeBeforeUpdate);
        Acuerdo testAcuerdo = acuerdoList.get(acuerdoList.size() - 1);
        assertThat(testAcuerdo.getAcuerdo()).isEqualTo(UPDATED_ACUERDO);
        assertThat(testAcuerdo.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testAcuerdo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testAcuerdo.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void updateNonExistingAcuerdo() throws Exception {
        int databaseSizeBeforeUpdate = acuerdoRepository.findAll().size();

        // Create the Acuerdo
        AcuerdoDTO acuerdoDTO = acuerdoMapper.toDto(acuerdo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAcuerdoMockMvc.perform(put("/api/acuerdos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(acuerdoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Acuerdo in the database
        List<Acuerdo> acuerdoList = acuerdoRepository.findAll();
        assertThat(acuerdoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAcuerdo() throws Exception {
        // Initialize the database
        acuerdoRepository.saveAndFlush(acuerdo);

        int databaseSizeBeforeDelete = acuerdoRepository.findAll().size();

        // Delete the acuerdo
        restAcuerdoMockMvc.perform(delete("/api/acuerdos/{id}", acuerdo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Acuerdo> acuerdoList = acuerdoRepository.findAll();
        assertThat(acuerdoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
