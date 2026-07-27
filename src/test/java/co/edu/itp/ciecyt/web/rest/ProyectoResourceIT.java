package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.service.ProyectoService;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.mapper.ProyectoMapper;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static co.edu.itp.ciecyt.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.itp.ciecyt.domain.enumeration.EnumViabilidad;
/**
 * Integration tests for the {@link ProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
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

    private static final String DEFAULT_PALABRAS_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_PALABRAS_CLAVE = "BBBBBBBBBB";

    private static final String DEFAULT_CONVOCATORIA = "AAAAAAAAAA";
    private static final String UPDATED_CONVOCATORIA = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCIAS = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCIAS = "BBBBBBBBBB";

    private static final String DEFAULT_PROGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_MUNICIPIO = "AAAAAAAAAA";
    private static final String UPDATED_MUNICIPIO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VIABLE = false;
    private static final Boolean UPDATED_VIABLE = true;

    private static final Boolean DEFAULT_ENVIADO = false;
    private static final Boolean UPDATED_ENVIADO = true;

    private static final ZonedDateTime DEFAULT_FECHA_ENVIO_PROPUESTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ENVIO_PROPUESTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_ENVIO_PROYECTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_ENVIO_PROYECTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_NOTA = 1F;
    private static final Float UPDATED_NOTA = 2F;

    private static final String DEFAULT_CONCLUSION = "AAAAAAAAAA";
    private static final String UPDATED_CONCLUSION = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMENDACIONES = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDACIONES = "BBBBBBBBBB";

    private static final EnumViabilidad DEFAULT_VIABILIDAD = EnumViabilidad.VIABLE;
    private static final EnumViabilidad UPDATED_VIABILIDAD = EnumViabilidad.PENDIENTE;

    private static final Boolean DEFAULT_PRE_ENVIADO = false;
    private static final Boolean UPDATED_PRE_ENVIADO = true;

    private static final ZonedDateTime DEFAULT_FECHA_PRE_ENVIO_PROPUESTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_PRE_ENVIO_PROPUESTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_VIABILIDAD_PROPUESTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_VIABILIDAD_PROPUESTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHA_SUSTENTACION_PROYECTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHA_SUSTENTACION_PROYECTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_SUSTENTAR = false;
    private static final Boolean UPDATED_SUSTENTAR = true;

    private static final String DEFAULT_RECOMENDACIONES_JURADO_PROYECTO = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDACIONES_JURADO_PROYECTO = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMENDACIONES_ASESOR_PROPUESTA = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDACIONES_ASESOR_PROPUESTA = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMENDACIONES_ASESOR_PROYECTO = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDACIONES_ASESOR_PROYECTO = "BBBBBBBBBB";

    private static final String DEFAULT_RECOMENDACIONES_JURADO_SUSTENTACION = "AAAAAAAAAA";
    private static final String UPDATED_RECOMENDACIONES_JURADO_SUSTENTACION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PROYECTO_ENVIADO = false;
    private static final Boolean UPDATED_PROYECTO_ENVIADO = true;

    @Autowired
    private ProyectoRepository proyectoRepository;

    @Autowired
    private ProyectoMapper proyectoMapper;

    @Autowired
    private ProyectoService proyectoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProyectoMockMvc;

    private Proyecto proyecto;

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
            .palabrasClave(DEFAULT_PALABRAS_CLAVE)
            .convocatoria(DEFAULT_CONVOCATORIA)
            .tipo(DEFAULT_TIPO)
            .referencias(DEFAULT_REFERENCIAS)
            .programa(DEFAULT_PROGRAMA)
            .departamento(DEFAULT_DEPARTAMENTO)
            .municipio(DEFAULT_MUNICIPIO)
            .viable(DEFAULT_VIABLE)
            .enviado(DEFAULT_ENVIADO)
            .fechaEnvioPropuesta(DEFAULT_FECHA_ENVIO_PROPUESTA)
            .fechaEnvioProyecto(DEFAULT_FECHA_ENVIO_PROYECTO)
            .nota(DEFAULT_NOTA)
            .conclusion(DEFAULT_CONCLUSION)
            .recomendaciones(DEFAULT_RECOMENDACIONES)
            .viabilidad(DEFAULT_VIABILIDAD)
            .preEnviado(DEFAULT_PRE_ENVIADO)
            .fechaPreEnvioPropuesta(DEFAULT_FECHA_PRE_ENVIO_PROPUESTA)
            .fechaViabilidadPropuesta(DEFAULT_FECHA_VIABILIDAD_PROPUESTA)
            .fechaSustentacionProyecto(DEFAULT_FECHA_SUSTENTACION_PROYECTO)
            .sustentar(DEFAULT_SUSTENTAR)
            .recomendacionesJuradoProyecto(DEFAULT_RECOMENDACIONES_JURADO_PROYECTO)
            .recomendacionesAsesorPropuesta(DEFAULT_RECOMENDACIONES_ASESOR_PROPUESTA)
            .recomendacionesAsesorProyecto(DEFAULT_RECOMENDACIONES_ASESOR_PROYECTO)
            .recomendacionesJuradoSustentacion(DEFAULT_RECOMENDACIONES_JURADO_SUSTENTACION)
            .proyectoEnviado(DEFAULT_PROYECTO_ENVIADO);
        return proyecto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proyecto createUpdatedEntity(EntityManager em) {
        Proyecto proyecto = new Proyecto()
            .titulo(UPDATED_TITULO)
            .url(UPDATED_URL)
            .lugarEjecucion(UPDATED_LUGAR_EJECUCION)
            .duracion(UPDATED_DURACION)
            .fechaIni(UPDATED_FECHA_INI)
            .fechaFin(UPDATED_FECHA_FIN)
            .contrapartidaPesos(UPDATED_CONTRAPARTIDA_PESOS)
            .contrapartidaEspecie(UPDATED_CONTRAPARTIDA_ESPECIE)
            .palabrasClave(UPDATED_PALABRAS_CLAVE)
            .convocatoria(UPDATED_CONVOCATORIA)
            .tipo(UPDATED_TIPO)
            .referencias(UPDATED_REFERENCIAS)
            .programa(UPDATED_PROGRAMA)
            .departamento(UPDATED_DEPARTAMENTO)
            .municipio(UPDATED_MUNICIPIO)
            .viable(UPDATED_VIABLE)
            .enviado(UPDATED_ENVIADO)
            .fechaEnvioPropuesta(UPDATED_FECHA_ENVIO_PROPUESTA)
            .fechaEnvioProyecto(UPDATED_FECHA_ENVIO_PROYECTO)
            .nota(UPDATED_NOTA)
            .conclusion(UPDATED_CONCLUSION)
            .recomendaciones(UPDATED_RECOMENDACIONES)
            .viabilidad(UPDATED_VIABILIDAD)
            .preEnviado(UPDATED_PRE_ENVIADO)
            .fechaPreEnvioPropuesta(UPDATED_FECHA_PRE_ENVIO_PROPUESTA)
            .fechaViabilidadPropuesta(UPDATED_FECHA_VIABILIDAD_PROPUESTA)
            .fechaSustentacionProyecto(UPDATED_FECHA_SUSTENTACION_PROYECTO)
            .sustentar(UPDATED_SUSTENTAR)
            .recomendacionesJuradoProyecto(UPDATED_RECOMENDACIONES_JURADO_PROYECTO)
            .recomendacionesAsesorPropuesta(UPDATED_RECOMENDACIONES_ASESOR_PROPUESTA)
            .recomendacionesAsesorProyecto(UPDATED_RECOMENDACIONES_ASESOR_PROYECTO)
            .recomendacionesJuradoSustentacion(UPDATED_RECOMENDACIONES_JURADO_SUSTENTACION)
            .proyectoEnviado(UPDATED_PROYECTO_ENVIADO);
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
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);
        restProyectoMockMvc.perform(post("/api/proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyectoDTO)))
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
        assertThat(testProyecto.getPalabrasClave()).isEqualTo(DEFAULT_PALABRAS_CLAVE);
        assertThat(testProyecto.getConvocatoria()).isEqualTo(DEFAULT_CONVOCATORIA);
        assertThat(testProyecto.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testProyecto.getReferencias()).isEqualTo(DEFAULT_REFERENCIAS);
        assertThat(testProyecto.getPrograma()).isEqualTo(DEFAULT_PROGRAMA);
        assertThat(testProyecto.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
        assertThat(testProyecto.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testProyecto.isViable()).isEqualTo(DEFAULT_VIABLE);
        assertThat(testProyecto.isEnviado()).isEqualTo(DEFAULT_ENVIADO);
        assertThat(testProyecto.getFechaEnvioPropuesta()).isEqualTo(DEFAULT_FECHA_ENVIO_PROPUESTA);
        assertThat(testProyecto.getFechaEnvioProyecto()).isEqualTo(DEFAULT_FECHA_ENVIO_PROYECTO);
        assertThat(testProyecto.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testProyecto.getConclusion()).isEqualTo(DEFAULT_CONCLUSION);
        assertThat(testProyecto.getRecomendaciones()).isEqualTo(DEFAULT_RECOMENDACIONES);
        assertThat(testProyecto.getViabilidad()).isEqualTo(DEFAULT_VIABILIDAD);
        assertThat(testProyecto.isPreEnviado()).isEqualTo(DEFAULT_PRE_ENVIADO);
        assertThat(testProyecto.getFechaPreEnvioPropuesta()).isEqualTo(DEFAULT_FECHA_PRE_ENVIO_PROPUESTA);
        assertThat(testProyecto.getFechaViabilidadPropuesta()).isEqualTo(DEFAULT_FECHA_VIABILIDAD_PROPUESTA);
        assertThat(testProyecto.getFechaSustentacionProyecto()).isEqualTo(DEFAULT_FECHA_SUSTENTACION_PROYECTO);
        assertThat(testProyecto.isSustentar()).isEqualTo(DEFAULT_SUSTENTAR);
        assertThat(testProyecto.getRecomendacionesJuradoProyecto()).isEqualTo(DEFAULT_RECOMENDACIONES_JURADO_PROYECTO);
        assertThat(testProyecto.getRecomendacionesAsesorPropuesta()).isEqualTo(DEFAULT_RECOMENDACIONES_ASESOR_PROPUESTA);
        assertThat(testProyecto.getRecomendacionesAsesorProyecto()).isEqualTo(DEFAULT_RECOMENDACIONES_ASESOR_PROYECTO);
        assertThat(testProyecto.getRecomendacionesJuradoSustentacion()).isEqualTo(DEFAULT_RECOMENDACIONES_JURADO_SUSTENTACION);
        assertThat(testProyecto.isProyectoEnviado()).isEqualTo(DEFAULT_PROYECTO_ENVIADO);
    }

    @Test
    @Transactional
    public void createProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proyectoRepository.findAll().size();

        // Create the Proyecto with an existing ID
        proyecto.setId(1L);
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProyectoMockMvc.perform(post("/api/proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyectoDTO)))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].lugarEjecucion").value(hasItem(DEFAULT_LUGAR_EJECUCION)))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)))
            .andExpect(jsonPath("$.[*].fechaIni").value(hasItem(DEFAULT_FECHA_INI.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].contrapartidaPesos").value(hasItem(DEFAULT_CONTRAPARTIDA_PESOS.doubleValue())))
            .andExpect(jsonPath("$.[*].contrapartidaEspecie").value(hasItem(DEFAULT_CONTRAPARTIDA_ESPECIE.doubleValue())))
            .andExpect(jsonPath("$.[*].palabrasClave").value(hasItem(DEFAULT_PALABRAS_CLAVE)))
            .andExpect(jsonPath("$.[*].convocatoria").value(hasItem(DEFAULT_CONVOCATORIA)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].referencias").value(hasItem(DEFAULT_REFERENCIAS)))
            .andExpect(jsonPath("$.[*].programa").value(hasItem(DEFAULT_PROGRAMA)))
            .andExpect(jsonPath("$.[*].departamento").value(hasItem(DEFAULT_DEPARTAMENTO)))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO)))
            .andExpect(jsonPath("$.[*].viable").value(hasItem(DEFAULT_VIABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].enviado").value(hasItem(DEFAULT_ENVIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaEnvioPropuesta").value(hasItem(sameInstant(DEFAULT_FECHA_ENVIO_PROPUESTA))))
            .andExpect(jsonPath("$.[*].fechaEnvioProyecto").value(hasItem(sameInstant(DEFAULT_FECHA_ENVIO_PROYECTO))))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.doubleValue())))
            .andExpect(jsonPath("$.[*].conclusion").value(hasItem(DEFAULT_CONCLUSION)))
            .andExpect(jsonPath("$.[*].recomendaciones").value(hasItem(DEFAULT_RECOMENDACIONES)))
            .andExpect(jsonPath("$.[*].viabilidad").value(hasItem(DEFAULT_VIABILIDAD.toString())))
            .andExpect(jsonPath("$.[*].preEnviado").value(hasItem(DEFAULT_PRE_ENVIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaPreEnvioPropuesta").value(hasItem(sameInstant(DEFAULT_FECHA_PRE_ENVIO_PROPUESTA))))
            .andExpect(jsonPath("$.[*].fechaViabilidadPropuesta").value(hasItem(sameInstant(DEFAULT_FECHA_VIABILIDAD_PROPUESTA))))
            .andExpect(jsonPath("$.[*].fechaSustentacionProyecto").value(hasItem(sameInstant(DEFAULT_FECHA_SUSTENTACION_PROYECTO))))
            .andExpect(jsonPath("$.[*].sustentar").value(hasItem(DEFAULT_SUSTENTAR.booleanValue())))
            .andExpect(jsonPath("$.[*].recomendacionesJuradoProyecto").value(hasItem(DEFAULT_RECOMENDACIONES_JURADO_PROYECTO)))
            .andExpect(jsonPath("$.[*].recomendacionesAsesorPropuesta").value(hasItem(DEFAULT_RECOMENDACIONES_ASESOR_PROPUESTA)))
            .andExpect(jsonPath("$.[*].recomendacionesAsesorProyecto").value(hasItem(DEFAULT_RECOMENDACIONES_ASESOR_PROYECTO)))
            .andExpect(jsonPath("$.[*].recomendacionesJuradoSustentacion").value(hasItem(DEFAULT_RECOMENDACIONES_JURADO_SUSTENTACION)))
            .andExpect(jsonPath("$.[*].proyectoEnviado").value(hasItem(DEFAULT_PROYECTO_ENVIADO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getProyecto() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);

        // Get the proyecto
        restProyectoMockMvc.perform(get("/api/proyectos/{id}", proyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proyecto.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.lugarEjecucion").value(DEFAULT_LUGAR_EJECUCION))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION))
            .andExpect(jsonPath("$.fechaIni").value(DEFAULT_FECHA_INI.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.contrapartidaPesos").value(DEFAULT_CONTRAPARTIDA_PESOS.doubleValue()))
            .andExpect(jsonPath("$.contrapartidaEspecie").value(DEFAULT_CONTRAPARTIDA_ESPECIE.doubleValue()))
            .andExpect(jsonPath("$.palabrasClave").value(DEFAULT_PALABRAS_CLAVE))
            .andExpect(jsonPath("$.convocatoria").value(DEFAULT_CONVOCATORIA))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.referencias").value(DEFAULT_REFERENCIAS))
            .andExpect(jsonPath("$.programa").value(DEFAULT_PROGRAMA))
            .andExpect(jsonPath("$.departamento").value(DEFAULT_DEPARTAMENTO))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO))
            .andExpect(jsonPath("$.viable").value(DEFAULT_VIABLE.booleanValue()))
            .andExpect(jsonPath("$.enviado").value(DEFAULT_ENVIADO.booleanValue()))
            .andExpect(jsonPath("$.fechaEnvioPropuesta").value(sameInstant(DEFAULT_FECHA_ENVIO_PROPUESTA)))
            .andExpect(jsonPath("$.fechaEnvioProyecto").value(sameInstant(DEFAULT_FECHA_ENVIO_PROYECTO)))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.doubleValue()))
            .andExpect(jsonPath("$.conclusion").value(DEFAULT_CONCLUSION))
            .andExpect(jsonPath("$.recomendaciones").value(DEFAULT_RECOMENDACIONES))
            .andExpect(jsonPath("$.viabilidad").value(DEFAULT_VIABILIDAD.toString()))
            .andExpect(jsonPath("$.preEnviado").value(DEFAULT_PRE_ENVIADO.booleanValue()))
            .andExpect(jsonPath("$.fechaPreEnvioPropuesta").value(sameInstant(DEFAULT_FECHA_PRE_ENVIO_PROPUESTA)))
            .andExpect(jsonPath("$.fechaViabilidadPropuesta").value(sameInstant(DEFAULT_FECHA_VIABILIDAD_PROPUESTA)))
            .andExpect(jsonPath("$.fechaSustentacionProyecto").value(sameInstant(DEFAULT_FECHA_SUSTENTACION_PROYECTO)))
            .andExpect(jsonPath("$.sustentar").value(DEFAULT_SUSTENTAR.booleanValue()))
            .andExpect(jsonPath("$.recomendacionesJuradoProyecto").value(DEFAULT_RECOMENDACIONES_JURADO_PROYECTO))
            .andExpect(jsonPath("$.recomendacionesAsesorPropuesta").value(DEFAULT_RECOMENDACIONES_ASESOR_PROPUESTA))
            .andExpect(jsonPath("$.recomendacionesAsesorProyecto").value(DEFAULT_RECOMENDACIONES_ASESOR_PROYECTO))
            .andExpect(jsonPath("$.recomendacionesJuradoSustentacion").value(DEFAULT_RECOMENDACIONES_JURADO_SUSTENTACION))
            .andExpect(jsonPath("$.proyectoEnviado").value(DEFAULT_PROYECTO_ENVIADO.booleanValue()));
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
        proyectoRepository.saveAndFlush(proyecto);

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
            .palabrasClave(UPDATED_PALABRAS_CLAVE)
            .convocatoria(UPDATED_CONVOCATORIA)
            .tipo(UPDATED_TIPO)
            .referencias(UPDATED_REFERENCIAS)
            .programa(UPDATED_PROGRAMA)
            .departamento(UPDATED_DEPARTAMENTO)
            .municipio(UPDATED_MUNICIPIO)
            .viable(UPDATED_VIABLE)
            .enviado(UPDATED_ENVIADO)
            .fechaEnvioPropuesta(UPDATED_FECHA_ENVIO_PROPUESTA)
            .fechaEnvioProyecto(UPDATED_FECHA_ENVIO_PROYECTO)
            .nota(UPDATED_NOTA)
            .conclusion(UPDATED_CONCLUSION)
            .recomendaciones(UPDATED_RECOMENDACIONES)
            .viabilidad(UPDATED_VIABILIDAD)
            .preEnviado(UPDATED_PRE_ENVIADO)
            .fechaPreEnvioPropuesta(UPDATED_FECHA_PRE_ENVIO_PROPUESTA)
            .fechaViabilidadPropuesta(UPDATED_FECHA_VIABILIDAD_PROPUESTA)
            .fechaSustentacionProyecto(UPDATED_FECHA_SUSTENTACION_PROYECTO)
            .sustentar(UPDATED_SUSTENTAR)
            .recomendacionesJuradoProyecto(UPDATED_RECOMENDACIONES_JURADO_PROYECTO)
            .recomendacionesAsesorPropuesta(UPDATED_RECOMENDACIONES_ASESOR_PROPUESTA)
            .recomendacionesAsesorProyecto(UPDATED_RECOMENDACIONES_ASESOR_PROYECTO)
            .recomendacionesJuradoSustentacion(UPDATED_RECOMENDACIONES_JURADO_SUSTENTACION)
            .proyectoEnviado(UPDATED_PROYECTO_ENVIADO);
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(updatedProyecto);

        restProyectoMockMvc.perform(put("/api/proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyectoDTO)))
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
        assertThat(testProyecto.getPalabrasClave()).isEqualTo(UPDATED_PALABRAS_CLAVE);
        assertThat(testProyecto.getConvocatoria()).isEqualTo(UPDATED_CONVOCATORIA);
        assertThat(testProyecto.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testProyecto.getReferencias()).isEqualTo(UPDATED_REFERENCIAS);
        assertThat(testProyecto.getPrograma()).isEqualTo(UPDATED_PROGRAMA);
        assertThat(testProyecto.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
        assertThat(testProyecto.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testProyecto.isViable()).isEqualTo(UPDATED_VIABLE);
        assertThat(testProyecto.isEnviado()).isEqualTo(UPDATED_ENVIADO);
        assertThat(testProyecto.getFechaEnvioPropuesta()).isEqualTo(UPDATED_FECHA_ENVIO_PROPUESTA);
        assertThat(testProyecto.getFechaEnvioProyecto()).isEqualTo(UPDATED_FECHA_ENVIO_PROYECTO);
        assertThat(testProyecto.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testProyecto.getConclusion()).isEqualTo(UPDATED_CONCLUSION);
        assertThat(testProyecto.getRecomendaciones()).isEqualTo(UPDATED_RECOMENDACIONES);
        assertThat(testProyecto.getViabilidad()).isEqualTo(UPDATED_VIABILIDAD);
        assertThat(testProyecto.isPreEnviado()).isEqualTo(UPDATED_PRE_ENVIADO);
        assertThat(testProyecto.getFechaPreEnvioPropuesta()).isEqualTo(UPDATED_FECHA_PRE_ENVIO_PROPUESTA);
        assertThat(testProyecto.getFechaViabilidadPropuesta()).isEqualTo(UPDATED_FECHA_VIABILIDAD_PROPUESTA);
        assertThat(testProyecto.getFechaSustentacionProyecto()).isEqualTo(UPDATED_FECHA_SUSTENTACION_PROYECTO);
        assertThat(testProyecto.isSustentar()).isEqualTo(UPDATED_SUSTENTAR);
        assertThat(testProyecto.getRecomendacionesJuradoProyecto()).isEqualTo(UPDATED_RECOMENDACIONES_JURADO_PROYECTO);
        assertThat(testProyecto.getRecomendacionesAsesorPropuesta()).isEqualTo(UPDATED_RECOMENDACIONES_ASESOR_PROPUESTA);
        assertThat(testProyecto.getRecomendacionesAsesorProyecto()).isEqualTo(UPDATED_RECOMENDACIONES_ASESOR_PROYECTO);
        assertThat(testProyecto.getRecomendacionesJuradoSustentacion()).isEqualTo(UPDATED_RECOMENDACIONES_JURADO_SUSTENTACION);
        assertThat(testProyecto.isProyectoEnviado()).isEqualTo(UPDATED_PROYECTO_ENVIADO);
    }

    @Test
    @Transactional
    public void updateNonExistingProyecto() throws Exception {
        int databaseSizeBeforeUpdate = proyectoRepository.findAll().size();

        // Create the Proyecto
        ProyectoDTO proyectoDTO = proyectoMapper.toDto(proyecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProyectoMockMvc.perform(put("/api/proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proyectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Proyecto in the database
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProyecto() throws Exception {
        // Initialize the database
        proyectoRepository.saveAndFlush(proyecto);

        int databaseSizeBeforeDelete = proyectoRepository.findAll().size();

        // Delete the proyecto
        restProyectoMockMvc.perform(delete("/api/proyectos/{id}", proyecto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proyecto> proyectoList = proyectoRepository.findAll();
        assertThat(proyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
