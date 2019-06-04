package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.Empresas;
import com.itp.ciecyt.repository.EmpresasRepository;
import com.itp.ciecyt.service.EmpresasService;
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
 * Integration tests for the {@Link EmpresasResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class EmpresasResourceIT {

    private static final String DEFAULT_ENTIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ENTIDAD = "BBBBBBBBBB";

    @Autowired
    private EmpresasRepository empresasRepository;

    @Autowired
    private EmpresasService empresasService;

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

    private MockMvc restEmpresasMockMvc;

    private Empresas empresas;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmpresasResource empresasResource = new EmpresasResource(empresasService);
        this.restEmpresasMockMvc = MockMvcBuilders.standaloneSetup(empresasResource)
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
    public static Empresas createEntity(EntityManager em) {
        Empresas empresas = new Empresas()
            .entidad(DEFAULT_ENTIDAD);
        return empresas;
    }

    @BeforeEach
    public void initTest() {
        empresas = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmpresas() throws Exception {
        int databaseSizeBeforeCreate = empresasRepository.findAll().size();

        // Create the Empresas
        restEmpresasMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresas)))
            .andExpect(status().isCreated());

        // Validate the Empresas in the database
        List<Empresas> empresasList = empresasRepository.findAll();
        assertThat(empresasList).hasSize(databaseSizeBeforeCreate + 1);
        Empresas testEmpresas = empresasList.get(empresasList.size() - 1);
        assertThat(testEmpresas.getEntidad()).isEqualTo(DEFAULT_ENTIDAD);
    }

    @Test
    @Transactional
    public void createEmpresasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = empresasRepository.findAll().size();

        // Create the Empresas with an existing ID
        empresas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresasMockMvc.perform(post("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresas)))
            .andExpect(status().isBadRequest());

        // Validate the Empresas in the database
        List<Empresas> empresasList = empresasRepository.findAll();
        assertThat(empresasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmpresas() throws Exception {
        // Initialize the database
        empresasRepository.saveAndFlush(empresas);

        // Get all the empresasList
        restEmpresasMockMvc.perform(get("/api/empresas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresas.getId().intValue())))
            .andExpect(jsonPath("$.[*].entidad").value(hasItem(DEFAULT_ENTIDAD.toString())));
    }
    
    @Test
    @Transactional
    public void getEmpresas() throws Exception {
        // Initialize the database
        empresasRepository.saveAndFlush(empresas);

        // Get the empresas
        restEmpresasMockMvc.perform(get("/api/empresas/{id}", empresas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(empresas.getId().intValue()))
            .andExpect(jsonPath("$.entidad").value(DEFAULT_ENTIDAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmpresas() throws Exception {
        // Get the empresas
        restEmpresasMockMvc.perform(get("/api/empresas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmpresas() throws Exception {
        // Initialize the database
        empresasService.save(empresas);

        int databaseSizeBeforeUpdate = empresasRepository.findAll().size();

        // Update the empresas
        Empresas updatedEmpresas = empresasRepository.findById(empresas.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresas are not directly saved in db
        em.detach(updatedEmpresas);
        updatedEmpresas
            .entidad(UPDATED_ENTIDAD);

        restEmpresasMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmpresas)))
            .andExpect(status().isOk());

        // Validate the Empresas in the database
        List<Empresas> empresasList = empresasRepository.findAll();
        assertThat(empresasList).hasSize(databaseSizeBeforeUpdate);
        Empresas testEmpresas = empresasList.get(empresasList.size() - 1);
        assertThat(testEmpresas.getEntidad()).isEqualTo(UPDATED_ENTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingEmpresas() throws Exception {
        int databaseSizeBeforeUpdate = empresasRepository.findAll().size();

        // Create the Empresas

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresasMockMvc.perform(put("/api/empresas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(empresas)))
            .andExpect(status().isBadRequest());

        // Validate the Empresas in the database
        List<Empresas> empresasList = empresasRepository.findAll();
        assertThat(empresasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmpresas() throws Exception {
        // Initialize the database
        empresasService.save(empresas);

        int databaseSizeBeforeDelete = empresasRepository.findAll().size();

        // Delete the empresas
        restEmpresasMockMvc.perform(delete("/api/empresas/{id}", empresas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Empresas> empresasList = empresasRepository.findAll();
        assertThat(empresasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empresas.class);
        Empresas empresas1 = new Empresas();
        empresas1.setId(1L);
        Empresas empresas2 = new Empresas();
        empresas2.setId(empresas1.getId());
        assertThat(empresas1).isEqualTo(empresas2);
        empresas2.setId(2L);
        assertThat(empresas1).isNotEqualTo(empresas2);
        empresas1.setId(null);
        assertThat(empresas1).isNotEqualTo(empresas2);
    }
}
