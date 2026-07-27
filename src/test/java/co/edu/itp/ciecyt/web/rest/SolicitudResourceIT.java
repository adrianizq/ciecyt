package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Solicitud;
import co.edu.itp.ciecyt.repository.SolicitudRepository;
import co.edu.itp.ciecyt.service.SolicitudService;
import co.edu.itp.ciecyt.service.dto.SolicitudDTO;
import co.edu.itp.ciecyt.service.mapper.SolicitudMapper;

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
 * Integration tests for the {@link SolicitudResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SolicitudResourceIT {

    private static final Boolean DEFAULT_ESTADO = false;
    private static final Boolean UPDATED_ESTADO = true;

    private static final String DEFAULT_ASUNTO = "AAAAAAAAAA";
    private static final String UPDATED_ASUNTO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_SOLICITUD = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_SOLICITUD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_SOLICITUD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_SOLICITUD = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private SolicitudMapper solicitudMapper;

    @Autowired
    private SolicitudService solicitudService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSolicitudMockMvc;

    private Solicitud solicitud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitud createEntity(EntityManager em) {
        Solicitud solicitud = new Solicitud()
            .estado(DEFAULT_ESTADO)
            .asunto(DEFAULT_ASUNTO)
            .textoSolicitud(DEFAULT_TEXTO_SOLICITUD)
            .fechaSolicitud(DEFAULT_FECHA_SOLICITUD);
        return solicitud;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Solicitud createUpdatedEntity(EntityManager em) {
        Solicitud solicitud = new Solicitud()
            .estado(UPDATED_ESTADO)
            .asunto(UPDATED_ASUNTO)
            .textoSolicitud(UPDATED_TEXTO_SOLICITUD)
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD);
        return solicitud;
    }

    @BeforeEach
    public void initTest() {
        solicitud = createEntity(em);
    }

    @Test
    @Transactional
    public void createSolicitud() throws Exception {
        int databaseSizeBeforeCreate = solicitudRepository.findAll().size();
        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);
        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isCreated());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeCreate + 1);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.isEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testSolicitud.getAsunto()).isEqualTo(DEFAULT_ASUNTO);
        assertThat(testSolicitud.getTextoSolicitud()).isEqualTo(DEFAULT_TEXTO_SOLICITUD);
        assertThat(testSolicitud.getFechaSolicitud()).isEqualTo(DEFAULT_FECHA_SOLICITUD);
    }

    @Test
    @Transactional
    public void createSolicitudWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = solicitudRepository.findAll().size();

        // Create the Solicitud with an existing ID
        solicitud.setId(1L);
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSolicitudMockMvc.perform(post("/api/solicituds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSolicituds() throws Exception {
        // Initialize the database
        solicitudRepository.saveAndFlush(solicitud);

        // Get all the solicitudList
        restSolicitudMockMvc.perform(get("/api/solicituds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(solicitud.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.booleanValue())))
            .andExpect(jsonPath("$.[*].asunto").value(hasItem(DEFAULT_ASUNTO)))
            .andExpect(jsonPath("$.[*].textoSolicitud").value(hasItem(DEFAULT_TEXTO_SOLICITUD)))
            .andExpect(jsonPath("$.[*].fechaSolicitud").value(hasItem(DEFAULT_FECHA_SOLICITUD.toString())));
    }
    
    @Test
    @Transactional
    public void getSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.saveAndFlush(solicitud);

        // Get the solicitud
        restSolicitudMockMvc.perform(get("/api/solicituds/{id}", solicitud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(solicitud.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.booleanValue()))
            .andExpect(jsonPath("$.asunto").value(DEFAULT_ASUNTO))
            .andExpect(jsonPath("$.textoSolicitud").value(DEFAULT_TEXTO_SOLICITUD))
            .andExpect(jsonPath("$.fechaSolicitud").value(DEFAULT_FECHA_SOLICITUD.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSolicitud() throws Exception {
        // Get the solicitud
        restSolicitudMockMvc.perform(get("/api/solicituds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.saveAndFlush(solicitud);

        int databaseSizeBeforeUpdate = solicitudRepository.findAll().size();

        // Update the solicitud
        Solicitud updatedSolicitud = solicitudRepository.findById(solicitud.getId()).get();
        // Disconnect from session so that the updates on updatedSolicitud are not directly saved in db
        em.detach(updatedSolicitud);
        updatedSolicitud
            .estado(UPDATED_ESTADO)
            .asunto(UPDATED_ASUNTO)
            .textoSolicitud(UPDATED_TEXTO_SOLICITUD)
            .fechaSolicitud(UPDATED_FECHA_SOLICITUD);
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(updatedSolicitud);

        restSolicitudMockMvc.perform(put("/api/solicituds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isOk());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
        Solicitud testSolicitud = solicitudList.get(solicitudList.size() - 1);
        assertThat(testSolicitud.isEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testSolicitud.getAsunto()).isEqualTo(UPDATED_ASUNTO);
        assertThat(testSolicitud.getTextoSolicitud()).isEqualTo(UPDATED_TEXTO_SOLICITUD);
        assertThat(testSolicitud.getFechaSolicitud()).isEqualTo(UPDATED_FECHA_SOLICITUD);
    }

    @Test
    @Transactional
    public void updateNonExistingSolicitud() throws Exception {
        int databaseSizeBeforeUpdate = solicitudRepository.findAll().size();

        // Create the Solicitud
        SolicitudDTO solicitudDTO = solicitudMapper.toDto(solicitud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSolicitudMockMvc.perform(put("/api/solicituds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(solicitudDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Solicitud in the database
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSolicitud() throws Exception {
        // Initialize the database
        solicitudRepository.saveAndFlush(solicitud);

        int databaseSizeBeforeDelete = solicitudRepository.findAll().size();

        // Delete the solicitud
        restSolicitudMockMvc.perform(delete("/api/solicituds/{id}", solicitud.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Solicitud> solicitudList = solicitudRepository.findAll();
        assertThat(solicitudList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
