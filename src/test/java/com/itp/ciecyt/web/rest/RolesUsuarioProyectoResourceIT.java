package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.RolesUsuarioProyecto;
import com.itp.ciecyt.repository.RolesUsuarioProyectoRepository;
import com.itp.ciecyt.service.RolesUsuarioProyectoService;
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
import java.util.List;

import static com.itp.ciecyt.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link RolesUsuarioProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class RolesUsuarioProyectoResourceIT {

    private static final Integer DEFAULT_ROL = 1;
    private static final Integer UPDATED_ROL = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private RolesUsuarioProyectoRepository rolesUsuarioProyectoRepository;

    @Autowired
    private RolesUsuarioProyectoService rolesUsuarioProyectoService;

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

    private MockMvc restRolesUsuarioProyectoMockMvc;

    private RolesUsuarioProyecto rolesUsuarioProyecto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RolesUsuarioProyectoResource rolesUsuarioProyectoResource = new RolesUsuarioProyectoResource(rolesUsuarioProyectoService);
        this.restRolesUsuarioProyectoMockMvc = MockMvcBuilders.standaloneSetup(rolesUsuarioProyectoResource)
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
    public static RolesUsuarioProyecto createEntity(EntityManager em) {
        RolesUsuarioProyecto rolesUsuarioProyecto = new RolesUsuarioProyecto()
            .rol(DEFAULT_ROL)
            .descripcion(DEFAULT_DESCRIPCION);
        return rolesUsuarioProyecto;
    }

    @BeforeEach
    public void initTest() {
        rolesUsuarioProyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createRolesUsuarioProyecto() throws Exception {
        int databaseSizeBeforeCreate = rolesUsuarioProyectoRepository.findAll().size();

        // Create the RolesUsuarioProyecto
        restRolesUsuarioProyectoMockMvc.perform(post("/api/roles-usuario-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolesUsuarioProyecto)))
            .andExpect(status().isCreated());

        // Validate the RolesUsuarioProyecto in the database
        List<RolesUsuarioProyecto> rolesUsuarioProyectoList = rolesUsuarioProyectoRepository.findAll();
        assertThat(rolesUsuarioProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        RolesUsuarioProyecto testRolesUsuarioProyecto = rolesUsuarioProyectoList.get(rolesUsuarioProyectoList.size() - 1);
        assertThat(testRolesUsuarioProyecto.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testRolesUsuarioProyecto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createRolesUsuarioProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rolesUsuarioProyectoRepository.findAll().size();

        // Create the RolesUsuarioProyecto with an existing ID
        rolesUsuarioProyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolesUsuarioProyectoMockMvc.perform(post("/api/roles-usuario-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolesUsuarioProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the RolesUsuarioProyecto in the database
        List<RolesUsuarioProyecto> rolesUsuarioProyectoList = rolesUsuarioProyectoRepository.findAll();
        assertThat(rolesUsuarioProyectoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRolesUsuarioProyectos() throws Exception {
        // Initialize the database
        rolesUsuarioProyectoRepository.saveAndFlush(rolesUsuarioProyecto);

        // Get all the rolesUsuarioProyectoList
        restRolesUsuarioProyectoMockMvc.perform(get("/api/roles-usuario-proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rolesUsuarioProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getRolesUsuarioProyecto() throws Exception {
        // Initialize the database
        rolesUsuarioProyectoRepository.saveAndFlush(rolesUsuarioProyecto);

        // Get the rolesUsuarioProyecto
        restRolesUsuarioProyectoMockMvc.perform(get("/api/roles-usuario-proyectos/{id}", rolesUsuarioProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rolesUsuarioProyecto.getId().intValue()))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRolesUsuarioProyecto() throws Exception {
        // Get the rolesUsuarioProyecto
        restRolesUsuarioProyectoMockMvc.perform(get("/api/roles-usuario-proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRolesUsuarioProyecto() throws Exception {
        // Initialize the database
        rolesUsuarioProyectoService.save(rolesUsuarioProyecto);

        int databaseSizeBeforeUpdate = rolesUsuarioProyectoRepository.findAll().size();

        // Update the rolesUsuarioProyecto
        RolesUsuarioProyecto updatedRolesUsuarioProyecto = rolesUsuarioProyectoRepository.findById(rolesUsuarioProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedRolesUsuarioProyecto are not directly saved in db
        em.detach(updatedRolesUsuarioProyecto);
        updatedRolesUsuarioProyecto
            .rol(UPDATED_ROL)
            .descripcion(UPDATED_DESCRIPCION);

        restRolesUsuarioProyectoMockMvc.perform(put("/api/roles-usuario-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRolesUsuarioProyecto)))
            .andExpect(status().isOk());

        // Validate the RolesUsuarioProyecto in the database
        List<RolesUsuarioProyecto> rolesUsuarioProyectoList = rolesUsuarioProyectoRepository.findAll();
        assertThat(rolesUsuarioProyectoList).hasSize(databaseSizeBeforeUpdate);
        RolesUsuarioProyecto testRolesUsuarioProyecto = rolesUsuarioProyectoList.get(rolesUsuarioProyectoList.size() - 1);
        assertThat(testRolesUsuarioProyecto.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testRolesUsuarioProyecto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingRolesUsuarioProyecto() throws Exception {
        int databaseSizeBeforeUpdate = rolesUsuarioProyectoRepository.findAll().size();

        // Create the RolesUsuarioProyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesUsuarioProyectoMockMvc.perform(put("/api/roles-usuario-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rolesUsuarioProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the RolesUsuarioProyecto in the database
        List<RolesUsuarioProyecto> rolesUsuarioProyectoList = rolesUsuarioProyectoRepository.findAll();
        assertThat(rolesUsuarioProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRolesUsuarioProyecto() throws Exception {
        // Initialize the database
        rolesUsuarioProyectoService.save(rolesUsuarioProyecto);

        int databaseSizeBeforeDelete = rolesUsuarioProyectoRepository.findAll().size();

        // Delete the rolesUsuarioProyecto
        restRolesUsuarioProyectoMockMvc.perform(delete("/api/roles-usuario-proyectos/{id}", rolesUsuarioProyecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RolesUsuarioProyecto> rolesUsuarioProyectoList = rolesUsuarioProyectoRepository.findAll();
        assertThat(rolesUsuarioProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RolesUsuarioProyecto.class);
        RolesUsuarioProyecto rolesUsuarioProyecto1 = new RolesUsuarioProyecto();
        rolesUsuarioProyecto1.setId(1L);
        RolesUsuarioProyecto rolesUsuarioProyecto2 = new RolesUsuarioProyecto();
        rolesUsuarioProyecto2.setId(rolesUsuarioProyecto1.getId());
        assertThat(rolesUsuarioProyecto1).isEqualTo(rolesUsuarioProyecto2);
        rolesUsuarioProyecto2.setId(2L);
        assertThat(rolesUsuarioProyecto1).isNotEqualTo(rolesUsuarioProyecto2);
        rolesUsuarioProyecto1.setId(null);
        assertThat(rolesUsuarioProyecto1).isNotEqualTo(rolesUsuarioProyecto2);
    }
}
