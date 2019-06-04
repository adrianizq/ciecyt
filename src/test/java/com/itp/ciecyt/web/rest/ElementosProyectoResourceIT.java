package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.ElementosProyecto;
import com.itp.ciecyt.repository.ElementosProyectoRepository;
import com.itp.ciecyt.service.ElementosProyectoService;
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
 * Integration tests for the {@Link ElementosProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class ElementosProyectoResourceIT {

    private static final String DEFAULT_DATO = "AAAAAAAAAA";
    private static final String UPDATED_DATO = "BBBBBBBBBB";

    @Autowired
    private ElementosProyectoRepository elementosProyectoRepository;

    @Autowired
    private ElementosProyectoService elementosProyectoService;

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

    private MockMvc restElementosProyectoMockMvc;

    private ElementosProyecto elementosProyecto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElementosProyectoResource elementosProyectoResource = new ElementosProyectoResource(elementosProyectoService);
        this.restElementosProyectoMockMvc = MockMvcBuilders.standaloneSetup(elementosProyectoResource)
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
    public static ElementosProyecto createEntity(EntityManager em) {
        ElementosProyecto elementosProyecto = new ElementosProyecto()
            .dato(DEFAULT_DATO);
        return elementosProyecto;
    }

    @BeforeEach
    public void initTest() {
        elementosProyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createElementosProyecto() throws Exception {
        int databaseSizeBeforeCreate = elementosProyectoRepository.findAll().size();

        // Create the ElementosProyecto
        restElementosProyectoMockMvc.perform(post("/api/elementos-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementosProyecto)))
            .andExpect(status().isCreated());

        // Validate the ElementosProyecto in the database
        List<ElementosProyecto> elementosProyectoList = elementosProyectoRepository.findAll();
        assertThat(elementosProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        ElementosProyecto testElementosProyecto = elementosProyectoList.get(elementosProyectoList.size() - 1);
        assertThat(testElementosProyecto.getDato()).isEqualTo(DEFAULT_DATO);
    }

    @Test
    @Transactional
    public void createElementosProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementosProyectoRepository.findAll().size();

        // Create the ElementosProyecto with an existing ID
        elementosProyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementosProyectoMockMvc.perform(post("/api/elementos-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementosProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the ElementosProyecto in the database
        List<ElementosProyecto> elementosProyectoList = elementosProyectoRepository.findAll();
        assertThat(elementosProyectoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllElementosProyectos() throws Exception {
        // Initialize the database
        elementosProyectoRepository.saveAndFlush(elementosProyecto);

        // Get all the elementosProyectoList
        restElementosProyectoMockMvc.perform(get("/api/elementos-proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementosProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].dato").value(hasItem(DEFAULT_DATO.toString())));
    }
    
    @Test
    @Transactional
    public void getElementosProyecto() throws Exception {
        // Initialize the database
        elementosProyectoRepository.saveAndFlush(elementosProyecto);

        // Get the elementosProyecto
        restElementosProyectoMockMvc.perform(get("/api/elementos-proyectos/{id}", elementosProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elementosProyecto.getId().intValue()))
            .andExpect(jsonPath("$.dato").value(DEFAULT_DATO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingElementosProyecto() throws Exception {
        // Get the elementosProyecto
        restElementosProyectoMockMvc.perform(get("/api/elementos-proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElementosProyecto() throws Exception {
        // Initialize the database
        elementosProyectoService.save(elementosProyecto);

        int databaseSizeBeforeUpdate = elementosProyectoRepository.findAll().size();

        // Update the elementosProyecto
        ElementosProyecto updatedElementosProyecto = elementosProyectoRepository.findById(elementosProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedElementosProyecto are not directly saved in db
        em.detach(updatedElementosProyecto);
        updatedElementosProyecto
            .dato(UPDATED_DATO);

        restElementosProyectoMockMvc.perform(put("/api/elementos-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElementosProyecto)))
            .andExpect(status().isOk());

        // Validate the ElementosProyecto in the database
        List<ElementosProyecto> elementosProyectoList = elementosProyectoRepository.findAll();
        assertThat(elementosProyectoList).hasSize(databaseSizeBeforeUpdate);
        ElementosProyecto testElementosProyecto = elementosProyectoList.get(elementosProyectoList.size() - 1);
        assertThat(testElementosProyecto.getDato()).isEqualTo(UPDATED_DATO);
    }

    @Test
    @Transactional
    public void updateNonExistingElementosProyecto() throws Exception {
        int databaseSizeBeforeUpdate = elementosProyectoRepository.findAll().size();

        // Create the ElementosProyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementosProyectoMockMvc.perform(put("/api/elementos-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementosProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the ElementosProyecto in the database
        List<ElementosProyecto> elementosProyectoList = elementosProyectoRepository.findAll();
        assertThat(elementosProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElementosProyecto() throws Exception {
        // Initialize the database
        elementosProyectoService.save(elementosProyecto);

        int databaseSizeBeforeDelete = elementosProyectoRepository.findAll().size();

        // Delete the elementosProyecto
        restElementosProyectoMockMvc.perform(delete("/api/elementos-proyectos/{id}", elementosProyecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ElementosProyecto> elementosProyectoList = elementosProyectoRepository.findAll();
        assertThat(elementosProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementosProyecto.class);
        ElementosProyecto elementosProyecto1 = new ElementosProyecto();
        elementosProyecto1.setId(1L);
        ElementosProyecto elementosProyecto2 = new ElementosProyecto();
        elementosProyecto2.setId(elementosProyecto1.getId());
        assertThat(elementosProyecto1).isEqualTo(elementosProyecto2);
        elementosProyecto2.setId(2L);
        assertThat(elementosProyecto1).isNotEqualTo(elementosProyecto2);
        elementosProyecto1.setId(null);
        assertThat(elementosProyecto1).isNotEqualTo(elementosProyecto2);
    }
}
