package co.edu.itp.ciecyt.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.AdjuntoRetroalimentacion;
import co.edu.itp.ciecyt.repository.AdjuntoRetroalimentacionRepository;
import co.edu.itp.ciecyt.service.AdjuntoRetroalimentacionService;
import co.edu.itp.ciecyt.service.dto.AdjuntoRetroalimentacionDTO;
import co.edu.itp.ciecyt.service.mapper.AdjuntoRetroalimentacionMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link AdjuntoRetroalimentacionResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdjuntoRetroalimentacionResourceIT {

    private static final String DEFAULT_NOMBRE_ADJUNTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ADJUNTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CREACION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_ESTADO_ADJUNTO = 1;
    private static final Integer UPDATED_ESTADO_ADJUNTO = 2;

    private static final String DEFAULT_ADJUNTO_RETROALIMENTACION = "AAAAAAAAAA";
    private static final String UPDATED_ADJUNTO_RETROALIMENTACION = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_ARCHIVO_ORIGINAL = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ARCHIVO_ORIGINAL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

   // private static final byte[] DEFAULT_ARCHIVO = TestUtil.createByteArray(1, "0");
   // private static final byte[] UPDATED_ARCHIVO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIVO = "BBBBBBBBBB";

    private static final String DEFAULT_ARCHIVO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ARCHIVO_CONTENT_TYPE = "image/png";


    private static final String DEFAULT_AUTHORITY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORITY = "BBBBBBBBBB";

    private static final String DEFAULT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_FILE = "BBBBBBBBBB";

    @Autowired
    private AdjuntoRetroalimentacionRepository adjuntoRetroalimentacionRepository;

    @Autowired
    private AdjuntoRetroalimentacionMapper adjuntoRetroalimentacionMapper;

    @Autowired
    private AdjuntoRetroalimentacionService adjuntoRetroalimentacionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdjuntoRetroalimentacionMockMvc;

    private AdjuntoRetroalimentacion adjuntoRetroalimentacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjuntoRetroalimentacion createEntity(EntityManager em) {
        AdjuntoRetroalimentacion adjuntoRetroalimentacion = new AdjuntoRetroalimentacion()
            .nombreAdjunto(DEFAULT_NOMBRE_ADJUNTO)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .estadoAdjunto(DEFAULT_ESTADO_ADJUNTO)
            .adjuntoRetroalimentacion(DEFAULT_ADJUNTO_RETROALIMENTACION)
            .nombreArchivoOriginal(DEFAULT_NOMBRE_ARCHIVO_ORIGINAL)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .archivo(DEFAULT_ARCHIVO)
            .archivoContentType(DEFAULT_ARCHIVO_CONTENT_TYPE)
            .authority(DEFAULT_AUTHORITY);
            //.file(DEFAULT_FILE);
        return adjuntoRetroalimentacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AdjuntoRetroalimentacion createUpdatedEntity(EntityManager em) {
        AdjuntoRetroalimentacion adjuntoRetroalimentacion = new AdjuntoRetroalimentacion()
            .nombreAdjunto(UPDATED_NOMBRE_ADJUNTO)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .estadoAdjunto(UPDATED_ESTADO_ADJUNTO)
            .adjuntoRetroalimentacion(UPDATED_ADJUNTO_RETROALIMENTACION)
            .nombreArchivoOriginal(UPDATED_NOMBRE_ARCHIVO_ORIGINAL)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .archivo(UPDATED_ARCHIVO)
            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE)
            .authority(UPDATED_AUTHORITY);
            //.file(UPDATED_FILE);
        return adjuntoRetroalimentacion;
    }

    @BeforeEach
    public void initTest() {
        adjuntoRetroalimentacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdjuntoRetroalimentacion() throws Exception {
        int databaseSizeBeforeCreate = adjuntoRetroalimentacionRepository.findAll().size();
        // Create the AdjuntoRetroalimentacion
        AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO = adjuntoRetroalimentacionMapper.toDto(adjuntoRetroalimentacion);
        restAdjuntoRetroalimentacionMockMvc
            .perform(
                post("/api/adjunto-retro-alimentacions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjuntoRetroalimentacionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AdjuntoRetroalimentacion in the database
        List<AdjuntoRetroalimentacion> adjuntoRetroalimentacionList = adjuntoRetroalimentacionRepository.findAll();
        assertThat(adjuntoRetroalimentacionList).hasSize(databaseSizeBeforeCreate + 1);
        AdjuntoRetroalimentacion testAdjuntoRetroalimentacion = adjuntoRetroalimentacionList.get(adjuntoRetroalimentacionList.size() - 1);
        assertThat(testAdjuntoRetroalimentacion.getNombreAdjunto()).isEqualTo(DEFAULT_NOMBRE_ADJUNTO);
        assertThat(testAdjuntoRetroalimentacion.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testAdjuntoRetroalimentacion.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testAdjuntoRetroalimentacion.getEstadoAdjunto()).isEqualTo(DEFAULT_ESTADO_ADJUNTO);
        assertThat(testAdjuntoRetroalimentacion.getAdjuntoRetroalimentacion()).isEqualTo(DEFAULT_ADJUNTO_RETROALIMENTACION);
        assertThat(testAdjuntoRetroalimentacion.getNombreArchivoOriginal()).isEqualTo(DEFAULT_NOMBRE_ARCHIVO_ORIGINAL);
        assertThat(testAdjuntoRetroalimentacion.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testAdjuntoRetroalimentacion.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testAdjuntoRetroalimentacion.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testAdjuntoRetroalimentacion.getArchivoContentType()).isEqualTo(DEFAULT_ARCHIVO_CONTENT_TYPE);
        assertThat(testAdjuntoRetroalimentacion.getArchivoContentType()).isEqualTo(DEFAULT_ARCHIVO_CONTENT_TYPE);
        assertThat(testAdjuntoRetroalimentacion.getAuthority()).isEqualTo(DEFAULT_AUTHORITY);
        //assertThat(testAdjuntoRetroalimentacion.getFile()).isEqualTo(DEFAULT_FILE);
    }

    @Test
    @Transactional
    public void createAdjuntoRetroalimentacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adjuntoRetroalimentacionRepository.findAll().size();

        // Create the AdjuntoRetroalimentacion with an existing ID
        adjuntoRetroalimentacion.setId(1L);
        AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO = adjuntoRetroalimentacionMapper.toDto(adjuntoRetroalimentacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdjuntoRetroalimentacionMockMvc
            .perform(
                post("/api/adjunto-retro-alimentacions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjuntoRetroalimentacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjuntoRetroalimentacion in the database
        List<AdjuntoRetroalimentacion> adjuntoRetroalimentacionList = adjuntoRetroalimentacionRepository.findAll();
        assertThat(adjuntoRetroalimentacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdjuntoRetroalimentacions() throws Exception {
        // Initialize the database
        adjuntoRetroalimentacionRepository.saveAndFlush(adjuntoRetroalimentacion);

        // Get all the adjuntoRetroalimentacionList
        restAdjuntoRetroalimentacionMockMvc
            .perform(get("/api/adjunto-retro-alimentacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adjuntoRetroalimentacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreAdjunto").value(hasItem(DEFAULT_NOMBRE_ADJUNTO)))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].estadoAdjunto").value(hasItem(DEFAULT_ESTADO_ADJUNTO)))
            .andExpect(jsonPath("$.[*].adjuntoRetroalimentacion").value(hasItem(DEFAULT_ADJUNTO_RETROALIMENTACION)))
            .andExpect(jsonPath("$.[*].nombreArchivoOriginal").value(hasItem(DEFAULT_NOMBRE_ARCHIVO_ORIGINAL)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].archivoContentType").value(hasItem(DEFAULT_ARCHIVO_CONTENT_TYPE)))
            //.andExpect(jsonPath("$.[*].archivo").value(hasItem(Base64Utils.encodeToString(DEFAULT_ARCHIVO))))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(DEFAULT_ARCHIVO)))
            .andExpect(jsonPath("$.[*].archivoContentType").value(hasItem(DEFAULT_ARCHIVO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].authority").value(hasItem(DEFAULT_AUTHORITY)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(DEFAULT_FILE)));
    }

    @Test
    @Transactional
    public void getAdjuntoRetroalimentacion() throws Exception {
        // Initialize the database
        adjuntoRetroalimentacionRepository.saveAndFlush(adjuntoRetroalimentacion);

        // Get the adjuntoRetroalimentacion
        restAdjuntoRetroalimentacionMockMvc
            .perform(get("/api/adjunto-retro-alimentacions/{id}", adjuntoRetroalimentacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adjuntoRetroalimentacion.getId().intValue()))
            .andExpect(jsonPath("$.nombreAdjunto").value(DEFAULT_NOMBRE_ADJUNTO))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.estadoAdjunto").value(DEFAULT_ESTADO_ADJUNTO))
            .andExpect(jsonPath("$.adjuntoRetroalimentacion").value(DEFAULT_ADJUNTO_RETROALIMENTACION))
            .andExpect(jsonPath("$.nombreArchivoOriginal").value(DEFAULT_NOMBRE_ARCHIVO_ORIGINAL))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.archivoContentType").value(DEFAULT_ARCHIVO_CONTENT_TYPE))
            //.andExpect(jsonPath("$.archivo").value(Base64Utils.encodeToString(DEFAULT_ARCHIVO)))
            .andExpect(jsonPath("$.archivo").value(DEFAULT_ARCHIVO))
            .andExpect(jsonPath("$.archivoContentType").value(DEFAULT_ARCHIVO_CONTENT_TYPE))
            .andExpect(jsonPath("$.authority").value(DEFAULT_AUTHORITY))
            .andExpect(jsonPath("$.file").value(DEFAULT_FILE));
    }

    @Test
    @Transactional
    public void getNonExistingAdjuntoRetroalimentacion() throws Exception {
        // Get the adjuntoRetroalimentacion
        restAdjuntoRetroalimentacionMockMvc
            .perform(get("/api/adjunto-retro-alimentacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdjuntoRetroalimentacion() throws Exception {
        // Initialize the database
        adjuntoRetroalimentacionRepository.saveAndFlush(adjuntoRetroalimentacion);

        int databaseSizeBeforeUpdate = adjuntoRetroalimentacionRepository.findAll().size();

        // Update the adjuntoRetroalimentacion
        AdjuntoRetroalimentacion updatedAdjuntoRetroalimentacion = adjuntoRetroalimentacionRepository
            .findById(adjuntoRetroalimentacion.getId())
            .get();
        // Disconnect from session so that the updates on updatedAdjuntoRetroalimentacion are not directly saved in db
        em.detach(updatedAdjuntoRetroalimentacion);
        updatedAdjuntoRetroalimentacion
            .nombreAdjunto(UPDATED_NOMBRE_ADJUNTO)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .estadoAdjunto(UPDATED_ESTADO_ADJUNTO)
            .adjuntoRetroalimentacion(UPDATED_ADJUNTO_RETROALIMENTACION)
            .nombreArchivoOriginal(UPDATED_NOMBRE_ARCHIVO_ORIGINAL)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .archivo(UPDATED_ARCHIVO)
            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE)
            .archivoContentType(UPDATED_ARCHIVO_CONTENT_TYPE)
            .authority(UPDATED_AUTHORITY);
            //.file(UPDATED_FILE);
        AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO = adjuntoRetroalimentacionMapper.toDto(updatedAdjuntoRetroalimentacion);

        restAdjuntoRetroalimentacionMockMvc
            .perform(
                put("/api/adjunto-retro-alimentacions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjuntoRetroalimentacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the AdjuntoRetroalimentacion in the database
        List<AdjuntoRetroalimentacion> adjuntoRetroalimentacionList = adjuntoRetroalimentacionRepository.findAll();
        assertThat(adjuntoRetroalimentacionList).hasSize(databaseSizeBeforeUpdate);
        AdjuntoRetroalimentacion testAdjuntoRetroalimentacion = adjuntoRetroalimentacionList.get(adjuntoRetroalimentacionList.size() - 1);
        assertThat(testAdjuntoRetroalimentacion.getNombreAdjunto()).isEqualTo(UPDATED_NOMBRE_ADJUNTO);
        assertThat(testAdjuntoRetroalimentacion.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testAdjuntoRetroalimentacion.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testAdjuntoRetroalimentacion.getEstadoAdjunto()).isEqualTo(UPDATED_ESTADO_ADJUNTO);
        assertThat(testAdjuntoRetroalimentacion.getAdjuntoRetroalimentacion()).isEqualTo(UPDATED_ADJUNTO_RETROALIMENTACION);
        assertThat(testAdjuntoRetroalimentacion.getNombreArchivoOriginal()).isEqualTo(UPDATED_NOMBRE_ARCHIVO_ORIGINAL);
        assertThat(testAdjuntoRetroalimentacion.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testAdjuntoRetroalimentacion.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testAdjuntoRetroalimentacion.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testAdjuntoRetroalimentacion.getArchivoContentType()).isEqualTo(UPDATED_ARCHIVO_CONTENT_TYPE);
        assertThat(testAdjuntoRetroalimentacion.getArchivoContentType()).isEqualTo(UPDATED_ARCHIVO_CONTENT_TYPE);
        assertThat(testAdjuntoRetroalimentacion.getAuthority()).isEqualTo(UPDATED_AUTHORITY);
        //assertThat(testAdjuntoRetroalimentacion.getFile()).isEqualTo(UPDATED_FILE);
    }

    @Test
    @Transactional
    public void updateNonExistingAdjuntoRetroalimentacion() throws Exception {
        int databaseSizeBeforeUpdate = adjuntoRetroalimentacionRepository.findAll().size();

        // Create the AdjuntoRetroalimentacion
        AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO = adjuntoRetroalimentacionMapper.toDto(adjuntoRetroalimentacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdjuntoRetroalimentacionMockMvc
            .perform(
                put("/api/adjunto-retro-alimentacions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adjuntoRetroalimentacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AdjuntoRetroalimentacion in the database
        List<AdjuntoRetroalimentacion> adjuntoRetroalimentacionList = adjuntoRetroalimentacionRepository.findAll();
        assertThat(adjuntoRetroalimentacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdjuntoRetroalimentacion() throws Exception {
        // Initialize the database
        adjuntoRetroalimentacionRepository.saveAndFlush(adjuntoRetroalimentacion);

        int databaseSizeBeforeDelete = adjuntoRetroalimentacionRepository.findAll().size();

        // Delete the adjuntoRetroalimentacion
        restAdjuntoRetroalimentacionMockMvc
            .perform(delete("/api/adjunto-retro-alimentacions/{id}", adjuntoRetroalimentacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AdjuntoRetroalimentacion> adjuntoRetroalimentacionList = adjuntoRetroalimentacionRepository.findAll();
        assertThat(adjuntoRetroalimentacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

