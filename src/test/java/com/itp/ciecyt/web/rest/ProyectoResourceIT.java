package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.Proyecto;
import com.itp.ciecyt.repository.ProyectoRepository;
import com.itp.ciecyt.service.ProyectoService;
import com.itp.ciecyt.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.itp.ciecyt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class ProyectoResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LUGAR_EJECUCION = "AAAAAAAAAA";
    private static final String UPDATED_LUGAR_EJECUCION = "BBBBBBBBBB";

    private static final String DEFAULT_DURACION = "AAAAAAAAAA";
    private static final String UPDATED_DURACION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_CONTRAPARTIDA_PESOS = 1D;
    private static final Double UPDATED_CONTRAPARTIDA_PESOS = 2D;

    private static final Double DEFAULT_CONTRAPARTIDA_ESPECIE = 1D;
    private static final Double UPDATED_CONTRAPARTIDA_ESPECIE = 2D;

    private static final String DEFAULT_PALABRA_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_PALABRA_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_CONVOCATORIA = "AAAAAAAAAA";
    private static final String UPDATED_CONVOCATORIA = "BBBBBBBBBB";

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProyectoMockMvc;

    private Proyecto proyecto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProyectoResource proyectoResource = new ProyectoResource(proyectoService);
        this.restProyectoMockMvc = MockMvcBuilders.standaloneSetup(proyectoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyecto createEntity(EntityManager em) {
        Proyecto proyecto = new Proyecto()
            .titulo(DEFAULT_TITULO)
            .url(DEFAULT_URL)
            .lugarEjecucion(DEFAULT_LUGAR_EJECUCION)
            .duracion(DEFAULT_DURACION)
            .fechaIni(DEFAULT_FECHA_INI)
            .fechaFin(DEFAULT_FECHA_FIN)
            .contrapartidaPesos(DEFAULT_CONTRAPARTIDA_PESOS)
            .contrapartidaEspecie(DEFAULT_CONTRAPARTIDA_ESPECIE)
            .palabraClave(DEFAULT_PALABRA_CLAVE)
            .convocatoria(DEFAULT_CONVOCATORIA);
        return proyecto;
    }

    @BeforeEach
    public void initTest() {
        proyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProyecto() throws Exception {
        int databaseSizeBeforeCreate = proyectoRepository.findAll().size();

        // Create the Proyecto
        restProyectoMockMvc.perform(post("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proyecto)))
            .andExpect(status().isCreated());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeCreate + 1);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testProyecto.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testProyecto.getLugarEjecucion()).isEqualTo(DEFAULT_LUGAR_EJECUCION);
        assertThat(testProyecto.getDuracion()).isEqualTo(DEFAULT_DURACION);
        assertThat(testProyecto.getFechaIni()).isEqualTo(DEFAULT_FECHA_INI);
        assertThat(testProyecto.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testProyecto.getContrapartidaPesos()).isEqualTo(DEFAULT_CONTRAPARTIDA_PESOS);
        assertThat(testProyecto.getContrapartidaEspecie()).isEqualTo(DEFAULT_CONTRAPARTIDA_ESPECIE);
        assertThat(testProyecto.getPalabraClave()).isEqualTo(DEFAULT_PALABRA_CLAVE);
        assertThat(testProyecto.getConvocatoria()).isEqualTo(DEFAULT_CONVOCATORIA);
    }

    @Test
    @Transactional
    public void createProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proyectoRepository.findAll().size();

        // Create the Proyecto with an existing ID
        proyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProyectoMockMvc.perform(post("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proyecto)))
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProyectos() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);

        // Get all the proyectoList
        restProyectoMockMvc.perform(get("/api/proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].lugarEjecucion").value(hasItem(DEFAULT_LUGAR_EJECUCION.toString())))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION.toString())))
            .andExpect(jsonPath("$.[*].fechaIni").value(hasItem(DEFAULT_FECHA_INI.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].contrapartidaPesos").value(hasItem(DEFAULT_CONTRAPARTIDA_PESOS.doubleValue())))
            .andExpect(jsonPath("$.[*].contrapartidaEspecie").value(hasItem(DEFAULT_CONTRAPARTIDA_ESPECIE.doubleValue())))
            .andExpect(jsonPath("$.[*].palabraClave").value(hasItem(DEFAULT_PALABRA_CLAVE.toString())))
            .andExpect(jsonPath("$.[*].convocatoria").value(hasItem(DEFAULT_CONVOCATORIA.toString())));
    }
    
    @Test
    @Transactional
    public void getProyecto() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);

        // Get the proyecto
        restProyectoMockMvc.perform(get("/api/proyectos/{id}", proyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(proyecto.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.lugarEjecucion").value(DEFAULT_LUGAR_EJECUCION.toString()))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION.toString()))
            .andExpect(jsonPath("$.fechaIni").value(DEFAULT_FECHA_INI.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.contrapartidaPesos").value(DEFAULT_CONTRAPARTIDA_PESOS.doubleValue()))
            .andExpect(jsonPath("$.contrapartidaEspecie").value(DEFAULT_CONTRAPARTIDA_ESPECIE.doubleValue()))
            .andExpect(jsonPath("$.palabraClave").value(DEFAULT_PALABRA_CLAVE.toString()))
            .andExpect(jsonPath("$.convocatoria").value(DEFAULT_CONVOCATORIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProyecto() throws Exception {
        // Get the proyecto
        restProyectoMockMvc.perform(get("/api/proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProyecto() throws Exception {
        // Initialize the database
        proyectoService.save(proyecto);

        int databaseSizeBeforeUpdate = proyectoRepository.findAll().size();

        // Update the proyecto
        Proyecto updatedProyecto = proyectoRepository.findById(proyecto.getId()).get();
        // Disconnect from session so that the updates on updatedProyecto are not directly saved in db
        em.detach(updatedProyecto);
        updatedProyecto
            .titulo(UPDATED_TITULO)
            .url(UPDATED_URL)
            .lugarEjecucion(UPDATED_LUGAR_EJECUCION)
            .duracion(UPDATED_DURACION)
            .fechaIni(UPDATED_FECHA_INI)
            .fechaFin(UPDATED_FECHA_FIN)
            .contrapartidaPesos(UPDATED_CONTRAPARTIDA_PESOS)
            .contrapartidaEspecie(UPDATED_CONTRAPARTIDA_ESPECIE)
            .palabraClave(UPDATED_PALABRA_CLAVE)
            .convocatoria(UPDATED_CONVOCATORIA);

        restProyectoMockMvc.perform(put("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProyecto)))
            .andExpect(status().isOk());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
        Proyecto testProyecto = proyectoList.get(proyectoList.size() - 1);
        assertThat(testProyecto.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testProyecto.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testProyecto.getLugarEjecucion()).isEqualTo(UPDATED_LUGAR_EJECUCION);
        assertThat(testProyecto.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testProyecto.getFechaIni()).isEqualTo(UPDATED_FECHA_INI);
        assertThat(testProyecto.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testProyecto.getContrapartidaPesos()).isEqualTo(UPDATED_CONTRAPARTIDA_PESOS);
        assertThat(testProyecto.getContrapartidaEspecie()).isEqualTo(UPDATED_CONTRAPARTIDA_ESPECIE);
        assertThat(testProyecto.getPalabraClave()).isEqualTo(UPDATED_PALABRA_CLAVE);
        assertThat(testProyecto.getConvocatoria()).isEqualTo(UPDATED_CONVOCATORIA);
    }

    @Test
    @Transactional
    public void updateNonExistingProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().size();

        // Create the Proyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyectoMockMvc.perform(put("/api/proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(proyecto)))
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProyecto() throws Exception {
        // Initialize the database
        proyectoService.save(proyecto);

        int databaseSizeBeforeDelete = proyectoRepository.findAll().size();

        // Delete the proyecto
        restProyectoMockMvc.perform(delete("/api/proyectos/{id}", proyecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Proyecto.class);
        Proyecto proyecto1 = new Proyecto();
        proyecto1.setId(1L);
        Proyecto proyecto2 = new Proyecto();
        proyecto2.setId(proyecto1.getId());
        assertThat(proyecto1).isEqualTo(proyecto2);
        proyecto2.setId(2L);
        assertThat(proyecto1).isNotEqualTo(proyecto2);
        proyecto1.setId(null);
        assertThat(proyecto1).isNotEqualTo(proyecto2);
    }
}
