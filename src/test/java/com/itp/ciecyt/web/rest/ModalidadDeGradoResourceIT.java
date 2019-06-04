package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.ModalidadDeGrado;
import com.itp.ciecyt.repository.ModalidadDeGradoRepository;
import com.itp.ciecyt.service.ModalidadDeGradoService;
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
 * Integration tests for the {@Link ModalidadDeGradoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class ModalidadDeGradoResourceIT {

    private static final String DEFAULT_MODALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_MODALIDAD = "BBBBBBBBBB";

    @Autowired
    private ModalidadDeGradoRepository modalidadDeGradoRepository;

    @Autowired
    private ModalidadDeGradoService modalidadDeGradoService;

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

    private MockMvc restModalidadDeGradoMockMvc;

    private ModalidadDeGrado modalidadDeGrado;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ModalidadDeGradoResource modalidadDeGradoResource = new ModalidadDeGradoResource(modalidadDeGradoService);
        this.restModalidadDeGradoMockMvc = MockMvcBuilders.standaloneSetup(modalidadDeGradoResource)
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
    public static ModalidadDeGrado createEntity(EntityManager em) {
        ModalidadDeGrado modalidadDeGrado = new ModalidadDeGrado()
            .modalidad(DEFAULT_MODALIDAD);
        return modalidadDeGrado;
    }

    @BeforeEach
    public void initTest() {
        modalidadDeGrado = createEntity(em);
    }

    @Test
    @Transactional
    public void createModalidadDeGrado() throws Exception {
        int databaseSizeBeforeCreate = modalidadDeGradoRepository.findAll().size();

        // Create the ModalidadDeGrado
        restModalidadDeGradoMockMvc.perform(post("/api/modalidad-de-grados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadDeGrado)))
            .andExpect(status().isCreated());

        // Validate the ModalidadDeGrado in the database
        List<ModalidadDeGrado> modalidadDeGradoList = modalidadDeGradoRepository.findAll();
        assertThat(modalidadDeGradoList).hasSize(databaseSizeBeforeCreate + 1);
        ModalidadDeGrado testModalidadDeGrado = modalidadDeGradoList.get(modalidadDeGradoList.size() - 1);
        assertThat(testModalidadDeGrado.getModalidad()).isEqualTo(DEFAULT_MODALIDAD);
    }

    @Test
    @Transactional
    public void createModalidadDeGradoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = modalidadDeGradoRepository.findAll().size();

        // Create the ModalidadDeGrado with an existing ID
        modalidadDeGrado.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restModalidadDeGradoMockMvc.perform(post("/api/modalidad-de-grados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadDeGrado)))
            .andExpect(status().isBadRequest());

        // Validate the ModalidadDeGrado in the database
        List<ModalidadDeGrado> modalidadDeGradoList = modalidadDeGradoRepository.findAll();
        assertThat(modalidadDeGradoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllModalidadDeGrados() throws Exception {
        // Initialize the database
        modalidadDeGradoRepository.saveAndFlush(modalidadDeGrado);

        // Get all the modalidadDeGradoList
        restModalidadDeGradoMockMvc.perform(get("/api/modalidad-de-grados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modalidadDeGrado.getId().intValue())))
            .andExpect(jsonPath("$.[*].modalidad").value(hasItem(DEFAULT_MODALIDAD.toString())));
    }
    
    @Test
    @Transactional
    public void getModalidadDeGrado() throws Exception {
        // Initialize the database
        modalidadDeGradoRepository.saveAndFlush(modalidadDeGrado);

        // Get the modalidadDeGrado
        restModalidadDeGradoMockMvc.perform(get("/api/modalidad-de-grados/{id}", modalidadDeGrado.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(modalidadDeGrado.getId().intValue()))
            .andExpect(jsonPath("$.modalidad").value(DEFAULT_MODALIDAD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingModalidadDeGrado() throws Exception {
        // Get the modalidadDeGrado
        restModalidadDeGradoMockMvc.perform(get("/api/modalidad-de-grados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateModalidadDeGrado() throws Exception {
        // Initialize the database
        modalidadDeGradoService.save(modalidadDeGrado);

        int databaseSizeBeforeUpdate = modalidadDeGradoRepository.findAll().size();

        // Update the modalidadDeGrado
        ModalidadDeGrado updatedModalidadDeGrado = modalidadDeGradoRepository.findById(modalidadDeGrado.getId()).get();
        // Disconnect from session so that the updates on updatedModalidadDeGrado are not directly saved in db
        em.detach(updatedModalidadDeGrado);
        updatedModalidadDeGrado
            .modalidad(UPDATED_MODALIDAD);

        restModalidadDeGradoMockMvc.perform(put("/api/modalidad-de-grados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedModalidadDeGrado)))
            .andExpect(status().isOk());

        // Validate the ModalidadDeGrado in the database
        List<ModalidadDeGrado> modalidadDeGradoList = modalidadDeGradoRepository.findAll();
        assertThat(modalidadDeGradoList).hasSize(databaseSizeBeforeUpdate);
        ModalidadDeGrado testModalidadDeGrado = modalidadDeGradoList.get(modalidadDeGradoList.size() - 1);
        assertThat(testModalidadDeGrado.getModalidad()).isEqualTo(UPDATED_MODALIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingModalidadDeGrado() throws Exception {
        int databaseSizeBeforeUpdate = modalidadDeGradoRepository.findAll().size();

        // Create the ModalidadDeGrado

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModalidadDeGradoMockMvc.perform(put("/api/modalidad-de-grados")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(modalidadDeGrado)))
            .andExpect(status().isBadRequest());

        // Validate the ModalidadDeGrado in the database
        List<ModalidadDeGrado> modalidadDeGradoList = modalidadDeGradoRepository.findAll();
        assertThat(modalidadDeGradoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteModalidadDeGrado() throws Exception {
        // Initialize the database
        modalidadDeGradoService.save(modalidadDeGrado);

        int databaseSizeBeforeDelete = modalidadDeGradoRepository.findAll().size();

        // Delete the modalidadDeGrado
        restModalidadDeGradoMockMvc.perform(delete("/api/modalidad-de-grados/{id}", modalidadDeGrado.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ModalidadDeGrado> modalidadDeGradoList = modalidadDeGradoRepository.findAll();
        assertThat(modalidadDeGradoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModalidadDeGrado.class);
        ModalidadDeGrado modalidadDeGrado1 = new ModalidadDeGrado();
        modalidadDeGrado1.setId(1L);
        ModalidadDeGrado modalidadDeGrado2 = new ModalidadDeGrado();
        modalidadDeGrado2.setId(modalidadDeGrado1.getId());
        assertThat(modalidadDeGrado1).isEqualTo(modalidadDeGrado2);
        modalidadDeGrado2.setId(2L);
        assertThat(modalidadDeGrado1).isNotEqualTo(modalidadDeGrado2);
        modalidadDeGrado1.setId(null);
        assertThat(modalidadDeGrado1).isNotEqualTo(modalidadDeGrado2);
    }
}
