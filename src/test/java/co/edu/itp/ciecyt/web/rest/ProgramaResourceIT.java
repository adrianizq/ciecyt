package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Programa;
import co.edu.itp.ciecyt.repository.ProgramaRepository;
import co.edu.itp.ciecyt.service.ProgramaService;
import co.edu.itp.ciecyt.service.dto.ProgramaDTO;
import co.edu.itp.ciecyt.service.mapper.ProgramaMapper;

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
 * Integration tests for the {@link ProgramaResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProgramaResourceIT {

    private static final String DEFAULT_PROGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_PROGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_INTERNO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_INTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO_SNIES = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_SNIES = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDITOS = 1;
    private static final Integer UPDATED_CREDITOS = 2;

    private static final String DEFAULT_CICLO = "AAAAAAAAAA";
    private static final String UPDATED_CICLO = "BBBBBBBBBB";

    private static final String DEFAULT_RESOLUCION = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUCION = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURACION_SEMESTRES = 1;
    private static final Integer UPDATED_DURACION_SEMESTRES = 2;

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private ProgramaMapper programaMapper;

    @Autowired
    private ProgramaService programaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProgramaMockMvc;

    private Programa programa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programa createEntity(EntityManager em) {
        Programa programa = new Programa()
            .programa(DEFAULT_PROGRAMA)
            .descripcion(DEFAULT_DESCRIPCION)
            .codigoInterno(DEFAULT_CODIGO_INTERNO)
            .codigoSnies(DEFAULT_CODIGO_SNIES)
            .creditos(DEFAULT_CREDITOS)
            .ciclo(DEFAULT_CICLO)
            .resolucion(DEFAULT_RESOLUCION)
            .titulo(DEFAULT_TITULO)
            .duracionSemestres(DEFAULT_DURACION_SEMESTRES);
        return programa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programa createUpdatedEntity(EntityManager em) {
        Programa programa = new Programa()
            .programa(UPDATED_PROGRAMA)
            .descripcion(UPDATED_DESCRIPCION)
            .codigoInterno(UPDATED_CODIGO_INTERNO)
            .codigoSnies(UPDATED_CODIGO_SNIES)
            .creditos(UPDATED_CREDITOS)
            .ciclo(UPDATED_CICLO)
            .resolucion(UPDATED_RESOLUCION)
            .titulo(UPDATED_TITULO)
            .duracionSemestres(UPDATED_DURACION_SEMESTRES);
        return programa;
    }

    @BeforeEach
    public void initTest() {
        programa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrograma() throws Exception {
        int databaseSizeBeforeCreate = programaRepository.findAll().size();
        // Create the Programa
        ProgramaDTO programaDTO = programaMapper.toDto(programa);
        restProgramaMockMvc.perform(post("/api/programas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programaDTO)))
            .andExpect(status().isCreated());

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeCreate + 1);
        Programa testPrograma = programaList.get(programaList.size() - 1);
        assertThat(testPrograma.getPrograma()).isEqualTo(DEFAULT_PROGRAMA);
        assertThat(testPrograma.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testPrograma.getCodigoInterno()).isEqualTo(DEFAULT_CODIGO_INTERNO);
        assertThat(testPrograma.getCodigoSnies()).isEqualTo(DEFAULT_CODIGO_SNIES);
        assertThat(testPrograma.getCreditos()).isEqualTo(DEFAULT_CREDITOS);
        assertThat(testPrograma.getCiclo()).isEqualTo(DEFAULT_CICLO);
        assertThat(testPrograma.getResolucion()).isEqualTo(DEFAULT_RESOLUCION);
        assertThat(testPrograma.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testPrograma.getDuracionSemestres()).isEqualTo(DEFAULT_DURACION_SEMESTRES);
    }

    @Test
    @Transactional
    public void createProgramaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programaRepository.findAll().size();

        // Create the Programa with an existing ID
        programa.setId(1L);
        ProgramaDTO programaDTO = programaMapper.toDto(programa);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramaMockMvc.perform(post("/api/programas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProgramas() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);

        // Get all the programaList
        restProgramaMockMvc.perform(get("/api/programas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programa.getId().intValue())))
            .andExpect(jsonPath("$.[*].programa").value(hasItem(DEFAULT_PROGRAMA)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].codigoInterno").value(hasItem(DEFAULT_CODIGO_INTERNO)))
            .andExpect(jsonPath("$.[*].codigoSnies").value(hasItem(DEFAULT_CODIGO_SNIES)))
            .andExpect(jsonPath("$.[*].creditos").value(hasItem(DEFAULT_CREDITOS)))
            .andExpect(jsonPath("$.[*].ciclo").value(hasItem(DEFAULT_CICLO)))
            .andExpect(jsonPath("$.[*].resolucion").value(hasItem(DEFAULT_RESOLUCION)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].duracionSemestres").value(hasItem(DEFAULT_DURACION_SEMESTRES)));
    }
    
    @Test
    @Transactional
    public void getPrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);

        // Get the programa
        restProgramaMockMvc.perform(get("/api/programas/{id}", programa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(programa.getId().intValue()))
            .andExpect(jsonPath("$.programa").value(DEFAULT_PROGRAMA))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.codigoInterno").value(DEFAULT_CODIGO_INTERNO))
            .andExpect(jsonPath("$.codigoSnies").value(DEFAULT_CODIGO_SNIES))
            .andExpect(jsonPath("$.creditos").value(DEFAULT_CREDITOS))
            .andExpect(jsonPath("$.ciclo").value(DEFAULT_CICLO))
            .andExpect(jsonPath("$.resolucion").value(DEFAULT_RESOLUCION))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.duracionSemestres").value(DEFAULT_DURACION_SEMESTRES));
    }
    @Test
    @Transactional
    public void getNonExistingPrograma() throws Exception {
        // Get the programa
        restProgramaMockMvc.perform(get("/api/programas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);

        int databaseSizeBeforeUpdate = programaRepository.findAll().size();

        // Update the programa
        Programa updatedPrograma = programaRepository.findById(programa.getId()).get();
        // Disconnect from session so that the updates on updatedPrograma are not directly saved in db
        em.detach(updatedPrograma);
        updatedPrograma
            .programa(UPDATED_PROGRAMA)
            .descripcion(UPDATED_DESCRIPCION)
            .codigoInterno(UPDATED_CODIGO_INTERNO)
            .codigoSnies(UPDATED_CODIGO_SNIES)
            .creditos(UPDATED_CREDITOS)
            .ciclo(UPDATED_CICLO)
            .resolucion(UPDATED_RESOLUCION)
            .titulo(UPDATED_TITULO)
            .duracionSemestres(UPDATED_DURACION_SEMESTRES);
        ProgramaDTO programaDTO = programaMapper.toDto(updatedPrograma);

        restProgramaMockMvc.perform(put("/api/programas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programaDTO)))
            .andExpect(status().isOk());

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
        Programa testPrograma = programaList.get(programaList.size() - 1);
        assertThat(testPrograma.getPrograma()).isEqualTo(UPDATED_PROGRAMA);
        assertThat(testPrograma.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testPrograma.getCodigoInterno()).isEqualTo(UPDATED_CODIGO_INTERNO);
        assertThat(testPrograma.getCodigoSnies()).isEqualTo(UPDATED_CODIGO_SNIES);
        assertThat(testPrograma.getCreditos()).isEqualTo(UPDATED_CREDITOS);
        assertThat(testPrograma.getCiclo()).isEqualTo(UPDATED_CICLO);
        assertThat(testPrograma.getResolucion()).isEqualTo(UPDATED_RESOLUCION);
        assertThat(testPrograma.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testPrograma.getDuracionSemestres()).isEqualTo(UPDATED_DURACION_SEMESTRES);
    }

    @Test
    @Transactional
    public void updateNonExistingPrograma() throws Exception {
        int databaseSizeBeforeUpdate = programaRepository.findAll().size();

        // Create the Programa
        ProgramaDTO programaDTO = programaMapper.toDto(programa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProgramaMockMvc.perform(put("/api/programas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(programaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);

        int databaseSizeBeforeDelete = programaRepository.findAll().size();

        // Delete the programa
        restProgramaMockMvc.perform(delete("/api/programas/{id}", programa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
