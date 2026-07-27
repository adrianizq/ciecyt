package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Departamento;
import co.edu.itp.ciecyt.repository.DepartamentoRepository;
import co.edu.itp.ciecyt.service.DepartamentoService;
import co.edu.itp.ciecyt.service.dto.DepartamentoDTO;
import co.edu.itp.ciecyt.service.mapper.DepartamentoMapper;

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
 * Integration tests for the {@link DepartamentoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DepartamentoResourceIT {

    private static final String DEFAULT_CODIGO_DANE = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DANE = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTAMENTO = "BBBBBBBBBB";

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private DepartamentoMapper departamentoMapper;

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartamentoMockMvc;

    private Departamento departamento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamento createEntity(EntityManager em) {
        Departamento departamento = new Departamento()
            .codigoDane(DEFAULT_CODIGO_DANE)
            .departamento(DEFAULT_DEPARTAMENTO);
        return departamento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamento createUpdatedEntity(EntityManager em) {
        Departamento departamento = new Departamento()
            .codigoDane(UPDATED_CODIGO_DANE)
            .departamento(UPDATED_DEPARTAMENTO);
        return departamento;
    }

    @BeforeEach
    public void initTest() {
        departamento = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartamento() throws Exception {
        int databaseSizeBeforeCreate = departamentoRepository.findAll().size();
        // Create the Departamento
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);
        restDepartamentoMockMvc.perform(post("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isCreated());

        // Validate the Departamento in the database
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeCreate + 1);
        Departamento testDepartamento = departamentoList.get(departamentoList.size() - 1);
        assertThat(testDepartamento.getCodigoDane()).isEqualTo(DEFAULT_CODIGO_DANE);
        assertThat(testDepartamento.getDepartamento()).isEqualTo(DEFAULT_DEPARTAMENTO);
    }

    @Test
    @Transactional
    public void createDepartamentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departamentoRepository.findAll().size();

        // Create the Departamento with an existing ID
        departamento.setId(1L);
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentoMockMvc.perform(post("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDepartamentos() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);

        // Get all the departamentoList
        restDepartamentoMockMvc.perform(get("/api/departamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamento.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoDane").value(hasItem(DEFAULT_CODIGO_DANE)))
            .andExpect(jsonPath("$.[*].departamento").value(hasItem(DEFAULT_DEPARTAMENTO)));
    }
    
    @Test
    @Transactional
    public void getDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);

        // Get the departamento
        restDepartamentoMockMvc.perform(get("/api/departamentos/{id}", departamento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamento.getId().intValue()))
            .andExpect(jsonPath("$.codigoDane").value(DEFAULT_CODIGO_DANE))
            .andExpect(jsonPath("$.departamento").value(DEFAULT_DEPARTAMENTO));
    }
    @Test
    @Transactional
    public void getNonExistingDepartamento() throws Exception {
        // Get the departamento
        restDepartamentoMockMvc.perform(get("/api/departamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);

        int databaseSizeBeforeUpdate = departamentoRepository.findAll().size();

        // Update the departamento
        Departamento updatedDepartamento = departamentoRepository.findById(departamento.getId()).get();
        // Disconnect from session so that the updates on updatedDepartamento are not directly saved in db
        em.detach(updatedDepartamento);
        updatedDepartamento
            .codigoDane(UPDATED_CODIGO_DANE)
            .departamento(UPDATED_DEPARTAMENTO);
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(updatedDepartamento);

        restDepartamentoMockMvc.perform(put("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isOk());

        // Validate the Departamento in the database
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeUpdate);
        Departamento testDepartamento = departamentoList.get(departamentoList.size() - 1);
        assertThat(testDepartamento.getCodigoDane()).isEqualTo(UPDATED_CODIGO_DANE);
        assertThat(testDepartamento.getDepartamento()).isEqualTo(UPDATED_DEPARTAMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartamento() throws Exception {
        int databaseSizeBeforeUpdate = departamentoRepository.findAll().size();

        // Create the Departamento
        DepartamentoDTO departamentoDTO = departamentoMapper.toDto(departamento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentoMockMvc.perform(put("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Departamento in the database
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDepartamento() throws Exception {
        // Initialize the database
        departamentoRepository.saveAndFlush(departamento);

        int databaseSizeBeforeDelete = departamentoRepository.findAll().size();

        // Delete the departamento
        restDepartamentoMockMvc.perform(delete("/api/departamentos/{id}", departamento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Departamento> departamentoList = departamentoRepository.findAll();
        assertThat(departamentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
