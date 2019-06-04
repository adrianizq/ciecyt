package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.LineaDeInvestigacion;
import com.itp.ciecyt.repository.LineaDeInvestigacionRepository;
import com.itp.ciecyt.service.LineaDeInvestigacionService;
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
 * Integration tests for the {@Link LineaDeInvestigacionResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class LineaDeInvestigacionResourceIT {

    private static final String DEFAULT_LINEA = "AAAAAAAAAA";
    private static final String UPDATED_LINEA = "BBBBBBBBBB";

    @Autowired
    private LineaDeInvestigacionRepository lineaDeInvestigacionRepository;

    @Autowired
    private LineaDeInvestigacionService lineaDeInvestigacionService;

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

    private MockMvc restLineaDeInvestigacionMockMvc;

    private LineaDeInvestigacion lineaDeInvestigacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LineaDeInvestigacionResource lineaDeInvestigacionResource = new LineaDeInvestigacionResource(lineaDeInvestigacionService);
        this.restLineaDeInvestigacionMockMvc = MockMvcBuilders.standaloneSetup(lineaDeInvestigacionResource)
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
    public static LineaDeInvestigacion createEntity(EntityManager em) {
        LineaDeInvestigacion lineaDeInvestigacion = new LineaDeInvestigacion()
            .linea(DEFAULT_LINEA);
        return lineaDeInvestigacion;
    }

    @BeforeEach
    public void initTest() {
        lineaDeInvestigacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createLineaDeInvestigacion() throws Exception {
        int databaseSizeBeforeCreate = lineaDeInvestigacionRepository.findAll().size();

        // Create the LineaDeInvestigacion
        restLineaDeInvestigacionMockMvc.perform(post("/api/linea-de-investigacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineaDeInvestigacion)))
            .andExpect(status().isCreated());

        // Validate the LineaDeInvestigacion in the database
        List<LineaDeInvestigacion> lineaDeInvestigacionList = lineaDeInvestigacionRepository.findAll();
        assertThat(lineaDeInvestigacionList).hasSize(databaseSizeBeforeCreate + 1);
        LineaDeInvestigacion testLineaDeInvestigacion = lineaDeInvestigacionList.get(lineaDeInvestigacionList.size() - 1);
        assertThat(testLineaDeInvestigacion.getLinea()).isEqualTo(DEFAULT_LINEA);
    }

    @Test
    @Transactional
    public void createLineaDeInvestigacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineaDeInvestigacionRepository.findAll().size();

        // Create the LineaDeInvestigacion with an existing ID
        lineaDeInvestigacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineaDeInvestigacionMockMvc.perform(post("/api/linea-de-investigacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineaDeInvestigacion)))
            .andExpect(status().isBadRequest());

        // Validate the LineaDeInvestigacion in the database
        List<LineaDeInvestigacion> lineaDeInvestigacionList = lineaDeInvestigacionRepository.findAll();
        assertThat(lineaDeInvestigacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLineaDeInvestigacions() throws Exception {
        // Initialize the database
        lineaDeInvestigacionRepository.saveAndFlush(lineaDeInvestigacion);

        // Get all the lineaDeInvestigacionList
        restLineaDeInvestigacionMockMvc.perform(get("/api/linea-de-investigacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineaDeInvestigacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].linea").value(hasItem(DEFAULT_LINEA.toString())));
    }
    
    @Test
    @Transactional
    public void getLineaDeInvestigacion() throws Exception {
        // Initialize the database
        lineaDeInvestigacionRepository.saveAndFlush(lineaDeInvestigacion);

        // Get the lineaDeInvestigacion
        restLineaDeInvestigacionMockMvc.perform(get("/api/linea-de-investigacions/{id}", lineaDeInvestigacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lineaDeInvestigacion.getId().intValue()))
            .andExpect(jsonPath("$.linea").value(DEFAULT_LINEA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLineaDeInvestigacion() throws Exception {
        // Get the lineaDeInvestigacion
        restLineaDeInvestigacionMockMvc.perform(get("/api/linea-de-investigacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineaDeInvestigacion() throws Exception {
        // Initialize the database
        lineaDeInvestigacionService.save(lineaDeInvestigacion);

        int databaseSizeBeforeUpdate = lineaDeInvestigacionRepository.findAll().size();

        // Update the lineaDeInvestigacion
        LineaDeInvestigacion updatedLineaDeInvestigacion = lineaDeInvestigacionRepository.findById(lineaDeInvestigacion.getId()).get();
        // Disconnect from session so that the updates on updatedLineaDeInvestigacion are not directly saved in db
        em.detach(updatedLineaDeInvestigacion);
        updatedLineaDeInvestigacion
            .linea(UPDATED_LINEA);

        restLineaDeInvestigacionMockMvc.perform(put("/api/linea-de-investigacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLineaDeInvestigacion)))
            .andExpect(status().isOk());

        // Validate the LineaDeInvestigacion in the database
        List<LineaDeInvestigacion> lineaDeInvestigacionList = lineaDeInvestigacionRepository.findAll();
        assertThat(lineaDeInvestigacionList).hasSize(databaseSizeBeforeUpdate);
        LineaDeInvestigacion testLineaDeInvestigacion = lineaDeInvestigacionList.get(lineaDeInvestigacionList.size() - 1);
        assertThat(testLineaDeInvestigacion.getLinea()).isEqualTo(UPDATED_LINEA);
    }

    @Test
    @Transactional
    public void updateNonExistingLineaDeInvestigacion() throws Exception {
        int databaseSizeBeforeUpdate = lineaDeInvestigacionRepository.findAll().size();

        // Create the LineaDeInvestigacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLineaDeInvestigacionMockMvc.perform(put("/api/linea-de-investigacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineaDeInvestigacion)))
            .andExpect(status().isBadRequest());

        // Validate the LineaDeInvestigacion in the database
        List<LineaDeInvestigacion> lineaDeInvestigacionList = lineaDeInvestigacionRepository.findAll();
        assertThat(lineaDeInvestigacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLineaDeInvestigacion() throws Exception {
        // Initialize the database
        lineaDeInvestigacionService.save(lineaDeInvestigacion);

        int databaseSizeBeforeDelete = lineaDeInvestigacionRepository.findAll().size();

        // Delete the lineaDeInvestigacion
        restLineaDeInvestigacionMockMvc.perform(delete("/api/linea-de-investigacions/{id}", lineaDeInvestigacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<LineaDeInvestigacion> lineaDeInvestigacionList = lineaDeInvestigacionRepository.findAll();
        assertThat(lineaDeInvestigacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineaDeInvestigacion.class);
        LineaDeInvestigacion lineaDeInvestigacion1 = new LineaDeInvestigacion();
        lineaDeInvestigacion1.setId(1L);
        LineaDeInvestigacion lineaDeInvestigacion2 = new LineaDeInvestigacion();
        lineaDeInvestigacion2.setId(lineaDeInvestigacion1.getId());
        assertThat(lineaDeInvestigacion1).isEqualTo(lineaDeInvestigacion2);
        lineaDeInvestigacion2.setId(2L);
        assertThat(lineaDeInvestigacion1).isNotEqualTo(lineaDeInvestigacion2);
        lineaDeInvestigacion1.setId(null);
        assertThat(lineaDeInvestigacion1).isNotEqualTo(lineaDeInvestigacion2);
    }
}
