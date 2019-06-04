package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.CiecytApp;
import com.itp.ciecyt.domain.ProductoProyecto;
import com.itp.ciecyt.repository.ProductoProyectoRepository;
import com.itp.ciecyt.service.ProductoProyectoService;
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
 * Integration tests for the {@Link ProductoProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
public class ProductoProyectoResourceIT {

    private static final Boolean DEFAULT_APLICA = false;
    private static final Boolean UPDATED_APLICA = true;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ProductoProyectoRepository productoProyectoRepository;

    @Autowired
    private ProductoProyectoService productoProyectoService;

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

    private MockMvc restProductoProyectoMockMvc;

    private ProductoProyecto productoProyecto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoProyectoResource productoProyectoResource = new ProductoProyectoResource(productoProyectoService);
        this.restProductoProyectoMockMvc = MockMvcBuilders.standaloneSetup(productoProyectoResource)
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
    public static ProductoProyecto createEntity(EntityManager em) {
        ProductoProyecto productoProyecto = new ProductoProyecto()
            .aplica(DEFAULT_APLICA)
            .descripcion(DEFAULT_DESCRIPCION);
        return productoProyecto;
    }

    @BeforeEach
    public void initTest() {
        productoProyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductoProyecto() throws Exception {
        int databaseSizeBeforeCreate = productoProyectoRepository.findAll().size();

        // Create the ProductoProyecto
        restProductoProyectoMockMvc.perform(post("/api/producto-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoProyecto)))
            .andExpect(status().isCreated());

        // Validate the ProductoProyecto in the database
        List<ProductoProyecto> productoProyectoList = productoProyectoRepository.findAll();
        assertThat(productoProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoProyecto testProductoProyecto = productoProyectoList.get(productoProyectoList.size() - 1);
        assertThat(testProductoProyecto.isAplica()).isEqualTo(DEFAULT_APLICA);
        assertThat(testProductoProyecto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createProductoProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoProyectoRepository.findAll().size();

        // Create the ProductoProyecto with an existing ID
        productoProyecto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoProyectoMockMvc.perform(post("/api/producto-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoProyecto in the database
        List<ProductoProyecto> productoProyectoList = productoProyectoRepository.findAll();
        assertThat(productoProyectoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductoProyectos() throws Exception {
        // Initialize the database
        productoProyectoRepository.saveAndFlush(productoProyecto);

        // Get all the productoProyectoList
        restProductoProyectoMockMvc.perform(get("/api/producto-proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].aplica").value(hasItem(DEFAULT_APLICA.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getProductoProyecto() throws Exception {
        // Initialize the database
        productoProyectoRepository.saveAndFlush(productoProyecto);

        // Get the productoProyecto
        restProductoProyectoMockMvc.perform(get("/api/producto-proyectos/{id}", productoProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productoProyecto.getId().intValue()))
            .andExpect(jsonPath("$.aplica").value(DEFAULT_APLICA.booleanValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProductoProyecto() throws Exception {
        // Get the productoProyecto
        restProductoProyectoMockMvc.perform(get("/api/producto-proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductoProyecto() throws Exception {
        // Initialize the database
        productoProyectoService.save(productoProyecto);

        int databaseSizeBeforeUpdate = productoProyectoRepository.findAll().size();

        // Update the productoProyecto
        ProductoProyecto updatedProductoProyecto = productoProyectoRepository.findById(productoProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedProductoProyecto are not directly saved in db
        em.detach(updatedProductoProyecto);
        updatedProductoProyecto
            .aplica(UPDATED_APLICA)
            .descripcion(UPDATED_DESCRIPCION);

        restProductoProyectoMockMvc.perform(put("/api/producto-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductoProyecto)))
            .andExpect(status().isOk());

        // Validate the ProductoProyecto in the database
        List<ProductoProyecto> productoProyectoList = productoProyectoRepository.findAll();
        assertThat(productoProyectoList).hasSize(databaseSizeBeforeUpdate);
        ProductoProyecto testProductoProyecto = productoProyectoList.get(productoProyectoList.size() - 1);
        assertThat(testProductoProyecto.isAplica()).isEqualTo(UPDATED_APLICA);
        assertThat(testProductoProyecto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = productoProyectoRepository.findAll().size();

        // Create the ProductoProyecto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoProyectoMockMvc.perform(put("/api/producto-proyectos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoProyecto)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoProyecto in the database
        List<ProductoProyecto> productoProyectoList = productoProyectoRepository.findAll();
        assertThat(productoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductoProyecto() throws Exception {
        // Initialize the database
        productoProyectoService.save(productoProyecto);

        int databaseSizeBeforeDelete = productoProyectoRepository.findAll().size();

        // Delete the productoProyecto
        restProductoProyectoMockMvc.perform(delete("/api/producto-proyectos/{id}", productoProyecto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ProductoProyecto> productoProyectoList = productoProyectoRepository.findAll();
        assertThat(productoProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoProyecto.class);
        ProductoProyecto productoProyecto1 = new ProductoProyecto();
        productoProyecto1.setId(1L);
        ProductoProyecto productoProyecto2 = new ProductoProyecto();
        productoProyecto2.setId(productoProyecto1.getId());
        assertThat(productoProyecto1).isEqualTo(productoProyecto2);
        productoProyecto2.setId(2L);
        assertThat(productoProyecto1).isNotEqualTo(productoProyecto2);
        productoProyecto1.setId(null);
        assertThat(productoProyecto1).isNotEqualTo(productoProyecto2);
    }
}
