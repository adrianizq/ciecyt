package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.CronogramaCiecyt;
import co.edu.itp.ciecyt.repository.CronogramaCiecytRepository;
import co.edu.itp.ciecyt.service.CronogramaCiecytService;
import co.edu.itp.ciecyt.service.dto.CronogramaCiecytDTO;
import co.edu.itp.ciecyt.service.mapper.CronogramaCiecytMapper;

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
 * Integration tests for the {@link CronogramaCiecytResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CronogramaCiecytResourceIT {

    private static final String DEFAULT_TITULO_CRONOGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_CRONOGRAMA = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    @Autowired
    private CronogramaCiecytRepository cronogramaCiecytRepository;

    @Autowired
    private CronogramaCiecytMapper cronogramaCiecytMapper;

    @Autowired
    private CronogramaCiecytService cronogramaCiecytService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCronogramaCiecytMockMvc;

    private CronogramaCiecyt cronogramaCiecyt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CronogramaCiecyt createEntity(EntityManager em) {
        CronogramaCiecyt cronogramaCiecyt = new CronogramaCiecyt()
            .tituloCronograma(DEFAULT_TITULO_CRONOGRAMA)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .observaciones(DEFAULT_OBSERVACIONES);
        return cronogramaCiecyt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CronogramaCiecyt createUpdatedEntity(EntityManager em) {
        CronogramaCiecyt cronogramaCiecyt = new CronogramaCiecyt()
            .tituloCronograma(UPDATED_TITULO_CRONOGRAMA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .observaciones(UPDATED_OBSERVACIONES);
        return cronogramaCiecyt;
    }

    @BeforeEach
    public void initTest() {
        cronogramaCiecyt = createEntity(em);
    }

    @Test
    @Transactional
    public void createCronogramaCiecyt() throws Exception {
        int databaseSizeBeforeCreate = cronogramaCiecytRepository.findAll().size();
        // Create the CronogramaCiecyt
        CronogramaCiecytDTO cronogramaCiecytDTO = cronogramaCiecytMapper.toDto(cronogramaCiecyt);
        restCronogramaCiecytMockMvc.perform(post("/api/cronograma-ciecyts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cronogramaCiecytDTO)))
            .andExpect(status().isCreated());

        // Validate the CronogramaCiecyt in the database
        List<CronogramaCiecyt> cronogramaCiecytList = cronogramaCiecytRepository.findAll();
        assertThat(cronogramaCiecytList).hasSize(databaseSizeBeforeCreate + 1);
        CronogramaCiecyt testCronogramaCiecyt = cronogramaCiecytList.get(cronogramaCiecytList.size() - 1);
        assertThat(testCronogramaCiecyt.getTituloCronograma()).isEqualTo(DEFAULT_TITULO_CRONOGRAMA);
        assertThat(testCronogramaCiecyt.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testCronogramaCiecyt.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testCronogramaCiecyt.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void createCronogramaCiecytWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cronogramaCiecytRepository.findAll().size();

        // Create the CronogramaCiecyt with an existing ID
        cronogramaCiecyt.setId(1L);
        CronogramaCiecytDTO cronogramaCiecytDTO = cronogramaCiecytMapper.toDto(cronogramaCiecyt);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCronogramaCiecytMockMvc.perform(post("/api/cronograma-ciecyts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cronogramaCiecytDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CronogramaCiecyt in the database
        List<CronogramaCiecyt> cronogramaCiecytList = cronogramaCiecytRepository.findAll();
        assertThat(cronogramaCiecytList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCronogramaCiecyts() throws Exception {
        // Initialize the database
        cronogramaCiecytRepository.saveAndFlush(cronogramaCiecyt);

        // Get all the cronogramaCiecytList
        restCronogramaCiecytMockMvc.perform(get("/api/cronograma-ciecyts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cronogramaCiecyt.getId().intValue())))
            .andExpect(jsonPath("$.[*].tituloCronograma").value(hasItem(DEFAULT_TITULO_CRONOGRAMA)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)));
    }
    
    @Test
    @Transactional
    public void getCronogramaCiecyt() throws Exception {
        // Initialize the database
        cronogramaCiecytRepository.saveAndFlush(cronogramaCiecyt);

        // Get the cronogramaCiecyt
        restCronogramaCiecytMockMvc.perform(get("/api/cronograma-ciecyts/{id}", cronogramaCiecyt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cronogramaCiecyt.getId().intValue()))
            .andExpect(jsonPath("$.tituloCronograma").value(DEFAULT_TITULO_CRONOGRAMA))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES));
    }
    @Test
    @Transactional
    public void getNonExistingCronogramaCiecyt() throws Exception {
        // Get the cronogramaCiecyt
        restCronogramaCiecytMockMvc.perform(get("/api/cronograma-ciecyts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCronogramaCiecyt() throws Exception {
        // Initialize the database
        cronogramaCiecytRepository.saveAndFlush(cronogramaCiecyt);

        int databaseSizeBeforeUpdate = cronogramaCiecytRepository.findAll().size();

        // Update the cronogramaCiecyt
        CronogramaCiecyt updatedCronogramaCiecyt = cronogramaCiecytRepository.findById(cronogramaCiecyt.getId()).get();
        // Disconnect from session so that the updates on updatedCronogramaCiecyt are not directly saved in db
        em.detach(updatedCronogramaCiecyt);
        updatedCronogramaCiecyt
            .tituloCronograma(UPDATED_TITULO_CRONOGRAMA)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .observaciones(UPDATED_OBSERVACIONES);
        CronogramaCiecytDTO cronogramaCiecytDTO = cronogramaCiecytMapper.toDto(updatedCronogramaCiecyt);

        restCronogramaCiecytMockMvc.perform(put("/api/cronograma-ciecyts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cronogramaCiecytDTO)))
            .andExpect(status().isOk());

        // Validate the CronogramaCiecyt in the database
        List<CronogramaCiecyt> cronogramaCiecytList = cronogramaCiecytRepository.findAll();
        assertThat(cronogramaCiecytList).hasSize(databaseSizeBeforeUpdate);
        CronogramaCiecyt testCronogramaCiecyt = cronogramaCiecytList.get(cronogramaCiecytList.size() - 1);
        assertThat(testCronogramaCiecyt.getTituloCronograma()).isEqualTo(UPDATED_TITULO_CRONOGRAMA);
        assertThat(testCronogramaCiecyt.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testCronogramaCiecyt.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testCronogramaCiecyt.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void updateNonExistingCronogramaCiecyt() throws Exception {
        int databaseSizeBeforeUpdate = cronogramaCiecytRepository.findAll().size();

        // Create the CronogramaCiecyt
        CronogramaCiecytDTO cronogramaCiecytDTO = cronogramaCiecytMapper.toDto(cronogramaCiecyt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCronogramaCiecytMockMvc.perform(put("/api/cronograma-ciecyts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cronogramaCiecytDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CronogramaCiecyt in the database
        List<CronogramaCiecyt> cronogramaCiecytList = cronogramaCiecytRepository.findAll();
        assertThat(cronogramaCiecytList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCronogramaCiecyt() throws Exception {
        // Initialize the database
        cronogramaCiecytRepository.saveAndFlush(cronogramaCiecyt);

        int databaseSizeBeforeDelete = cronogramaCiecytRepository.findAll().size();

        // Delete the cronogramaCiecyt
        restCronogramaCiecytMockMvc.perform(delete("/api/cronograma-ciecyts/{id}", cronogramaCiecyt.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CronogramaCiecyt> cronogramaCiecytList = cronogramaCiecytRepository.findAll();
        assertThat(cronogramaCiecytList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
