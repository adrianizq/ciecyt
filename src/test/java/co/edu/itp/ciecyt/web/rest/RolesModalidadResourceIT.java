package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.RolesModalidad;
import co.edu.itp.ciecyt.repository.RolesModalidadRepository;
import co.edu.itp.ciecyt.service.RolesModalidadService;
import co.edu.itp.ciecyt.service.dto.RolesModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.RolesModalidadMapper;

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
 * Integration tests for the {@link RolesModalidadResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RolesModalidadResourceIT {

    private static final String DEFAULT_ROL = "AAAAAAAAAA";
    private static final String UPDATED_ROL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Boolean DEFAULT_CALIFICADOR = false;
    private static final Boolean UPDATED_CALIFICADOR = true;

    @Autowired
    private RolesModalidadRepository rolesModalidadRepository;

    @Autowired
    private RolesModalidadMapper rolesModalidadMapper;

    @Autowired
    private RolesModalidadService rolesModalidadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRolesModalidadMockMvc;

    private RolesModalidad rolesModalidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RolesModalidad createEntity(EntityManager em) {
        RolesModalidad rolesModalidad = new RolesModalidad()
            .rol(DEFAULT_ROL)
            .cantidad(DEFAULT_CANTIDAD)
            .calificador(DEFAULT_CALIFICADOR);
        return rolesModalidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RolesModalidad createUpdatedEntity(EntityManager em) {
        RolesModalidad rolesModalidad = new RolesModalidad()
            .rol(UPDATED_ROL)
            .cantidad(UPDATED_CANTIDAD)
            .calificador(UPDATED_CALIFICADOR);
        return rolesModalidad;
    }

    @BeforeEach
    public void initTest() {
        rolesModalidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createRolesModalidad() throws Exception {
        int databaseSizeBeforeCreate = rolesModalidadRepository.findAll().size();
        // Create the RolesModalidad
        RolesModalidadDTO rolesModalidadDTO = rolesModalidadMapper.toDto(rolesModalidad);
        restRolesModalidadMockMvc.perform(post("/api/roles-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rolesModalidadDTO)))
            .andExpect(status().isCreated());

        // Validate the RolesModalidad in the database
        List<RolesModalidad> rolesModalidadList = rolesModalidadRepository.findAll();
        assertThat(rolesModalidadList).hasSize(databaseSizeBeforeCreate + 1);
        RolesModalidad testRolesModalidad = rolesModalidadList.get(rolesModalidadList.size() - 1);
        assertThat(testRolesModalidad.getRol()).isEqualTo(DEFAULT_ROL);
        assertThat(testRolesModalidad.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testRolesModalidad.isCalificador()).isEqualTo(DEFAULT_CALIFICADOR);
    }

    @Test
    @Transactional
    public void createRolesModalidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rolesModalidadRepository.findAll().size();

        // Create the RolesModalidad with an existing ID
        rolesModalidad.setId(1L);
        RolesModalidadDTO rolesModalidadDTO = rolesModalidadMapper.toDto(rolesModalidad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRolesModalidadMockMvc.perform(post("/api/roles-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rolesModalidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RolesModalidad in the database
        List<RolesModalidad> rolesModalidadList = rolesModalidadRepository.findAll();
        assertThat(rolesModalidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRolesModalidads() throws Exception {
        // Initialize the database
        rolesModalidadRepository.saveAndFlush(rolesModalidad);

        // Get all the rolesModalidadList
        restRolesModalidadMockMvc.perform(get("/api/roles-modalidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rolesModalidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].rol").value(hasItem(DEFAULT_ROL)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].calificador").value(hasItem(DEFAULT_CALIFICADOR.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRolesModalidad() throws Exception {
        // Initialize the database
        rolesModalidadRepository.saveAndFlush(rolesModalidad);

        // Get the rolesModalidad
        restRolesModalidadMockMvc.perform(get("/api/roles-modalidads/{id}", rolesModalidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rolesModalidad.getId().intValue()))
            .andExpect(jsonPath("$.rol").value(DEFAULT_ROL))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.calificador").value(DEFAULT_CALIFICADOR.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingRolesModalidad() throws Exception {
        // Get the rolesModalidad
        restRolesModalidadMockMvc.perform(get("/api/roles-modalidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRolesModalidad() throws Exception {
        // Initialize the database
        rolesModalidadRepository.saveAndFlush(rolesModalidad);

        int databaseSizeBeforeUpdate = rolesModalidadRepository.findAll().size();

        // Update the rolesModalidad
        RolesModalidad updatedRolesModalidad = rolesModalidadRepository.findById(rolesModalidad.getId()).get();
        // Disconnect from session so that the updates on updatedRolesModalidad are not directly saved in db
        em.detach(updatedRolesModalidad);
        updatedRolesModalidad
            .rol(UPDATED_ROL)
            .cantidad(UPDATED_CANTIDAD)
            .calificador(UPDATED_CALIFICADOR);
        RolesModalidadDTO rolesModalidadDTO = rolesModalidadMapper.toDto(updatedRolesModalidad);

        restRolesModalidadMockMvc.perform(put("/api/roles-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rolesModalidadDTO)))
            .andExpect(status().isOk());

        // Validate the RolesModalidad in the database
        List<RolesModalidad> rolesModalidadList = rolesModalidadRepository.findAll();
        assertThat(rolesModalidadList).hasSize(databaseSizeBeforeUpdate);
        RolesModalidad testRolesModalidad = rolesModalidadList.get(rolesModalidadList.size() - 1);
        assertThat(testRolesModalidad.getRol()).isEqualTo(UPDATED_ROL);
        assertThat(testRolesModalidad.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testRolesModalidad.isCalificador()).isEqualTo(UPDATED_CALIFICADOR);
    }

    @Test
    @Transactional
    public void updateNonExistingRolesModalidad() throws Exception {
        int databaseSizeBeforeUpdate = rolesModalidadRepository.findAll().size();

        // Create the RolesModalidad
        RolesModalidadDTO rolesModalidadDTO = rolesModalidadMapper.toDto(rolesModalidad);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRolesModalidadMockMvc.perform(put("/api/roles-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(rolesModalidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RolesModalidad in the database
        List<RolesModalidad> rolesModalidadList = rolesModalidadRepository.findAll();
        assertThat(rolesModalidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRolesModalidad() throws Exception {
        // Initialize the database
        rolesModalidadRepository.saveAndFlush(rolesModalidad);

        int databaseSizeBeforeDelete = rolesModalidadRepository.findAll().size();

        // Delete the rolesModalidad
        restRolesModalidadMockMvc.perform(delete("/api/roles-modalidads/{id}", rolesModalidad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RolesModalidad> rolesModalidadList = rolesModalidadRepository.findAll();
        assertThat(rolesModalidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
