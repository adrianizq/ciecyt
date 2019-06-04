package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.Elementos;
import com.itp.ciecyt.repository.ElementosRepository;
import com.itp.ciecyt.service.ElementosService;
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
 * Integration tests for the {@Link ElementosResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class ElementosResourceIT {

    private static final String DEFAULT_ELEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ELEMENTO = "BBBBBBBBBB";

    @Autowired
    private ElementosRepository elementosRepository;

    @Autowired
    private ElementosService elementosService;

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

    private MockMvc restElementosMockMvc;

    private Elementos elementos;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElementosResource elementosResource = new ElementosResource(elementosService);
        this.restElementosMockMvc = MockMvcBuilders.standaloneSetup(elementosResource)
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
    public static Elementos createEntity(EntityManager em) {
        Elementos elementos = new Elementos()
            .elemento(DEFAULT_ELEMENTO);
        return elementos;
    }

    @BeforeEach
    public void initTest() {
        elementos = createEntity(em);
    }

    @Test
    @Transactional
    public void createElementos() throws Exception {
        int databaseSizeBeforeCreate = elementosRepository.findAll().size();

        // Create the Elementos
        restElementosMockMvc.perform(post("/api/elementos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementos)))
            .andExpect(status().isCreated());

        // Validate the Elementos in the database
        List<Elementos> elementosList = elementosRepository.findAll();
        assertThat(elementosList).hasSize(databaseSizeBeforeCreate + 1);
        Elementos testElementos = elementosList.get(elementosList.size() - 1);
        assertThat(testElementos.getElemento()).isEqualTo(DEFAULT_ELEMENTO);
    }

    @Test
    @Transactional
    public void createElementosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementosRepository.findAll().size();

        // Create the Elementos with an existing ID
        elementos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementosMockMvc.perform(post("/api/elementos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementos)))
            .andExpect(status().isBadRequest());

        // Validate the Elementos in the database
        List<Elementos> elementosList = elementosRepository.findAll();
        assertThat(elementosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllElementos() throws Exception {
        // Initialize the database
        elementosRepository.saveAndFlush(elementos);

        // Get all the elementosList
        restElementosMockMvc.perform(get("/api/elementos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementos.getId().intValue())))
            .andExpect(jsonPath("$.[*].elemento").value(hasItem(DEFAULT_ELEMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getElementos() throws Exception {
        // Initialize the database
        elementosRepository.saveAndFlush(elementos);

        // Get the elementos
        restElementosMockMvc.perform(get("/api/elementos/{id}", elementos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(elementos.getId().intValue()))
            .andExpect(jsonPath("$.elemento").value(DEFAULT_ELEMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingElementos() throws Exception {
        // Get the elementos
        restElementosMockMvc.perform(get("/api/elementos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElementos() throws Exception {
        // Initialize the database
        elementosService.save(elementos);

        int databaseSizeBeforeUpdate = elementosRepository.findAll().size();

        // Update the elementos
        Elementos updatedElementos = elementosRepository.findById(elementos.getId()).get();
        // Disconnect from session so that the updates on updatedElementos are not directly saved in db
        em.detach(updatedElementos);
        updatedElementos
            .elemento(UPDATED_ELEMENTO);

        restElementosMockMvc.perform(put("/api/elementos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElementos)))
            .andExpect(status().isOk());

        // Validate the Elementos in the database
        List<Elementos> elementosList = elementosRepository.findAll();
        assertThat(elementosList).hasSize(databaseSizeBeforeUpdate);
        Elementos testElementos = elementosList.get(elementosList.size() - 1);
        assertThat(testElementos.getElemento()).isEqualTo(UPDATED_ELEMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingElementos() throws Exception {
        int databaseSizeBeforeUpdate = elementosRepository.findAll().size();

        // Create the Elementos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementosMockMvc.perform(put("/api/elementos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(elementos)))
            .andExpect(status().isBadRequest());

        // Validate the Elementos in the database
        List<Elementos> elementosList = elementosRepository.findAll();
        assertThat(elementosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElementos() throws Exception {
        // Initialize the database
        elementosService.save(elementos);

        int databaseSizeBeforeDelete = elementosRepository.findAll().size();

        // Delete the elementos
        restElementosMockMvc.perform(delete("/api/elementos/{id}", elementos.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Elementos> elementosList = elementosRepository.findAll();
        assertThat(elementosList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Elementos.class);
        Elementos elementos1 = new Elementos();
        elementos1.setId(1L);
        Elementos elementos2 = new Elementos();
        elementos2.setId(elementos1.getId());
        assertThat(elementos1).isEqualTo(elementos2);
        elementos2.setId(2L);
        assertThat(elementos1).isNotEqualTo(elementos2);
        elementos1.setId(null);
        assertThat(elementos1).isNotEqualTo(elementos2);
    }
}
