package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.ProductoProyecto;
import co.edu.itp.ciecyt.repository.ProductoProyectoRepository;
import co.edu.itp.ciecyt.service.ProductoProyectoService;
import co.edu.itp.ciecyt.service.dto.ProductoProyectoDTO;
import co.edu.itp.ciecyt.service.mapper.ProductoProyectoMapper;

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
 * Integration tests for the {@link ProductoProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductoProyectoResourceIT {

    private static final Boolean DEFAULT_APLICA = false;
    private static final Boolean UPDATED_APLICA = true;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ProductoProyectoRepository productoProyectoRepository;

    @Autowired
    private ProductoProyectoMapper productoProyectoMapper;

    @Autowired
    private ProductoProyectoService productoProyectoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoProyectoMockMvc;

    private ProductoProyecto productoProyecto;

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
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoProyecto createUpdatedEntity(EntityManager em) {
        ProductoProyecto productoProyecto = new ProductoProyecto()
            .aplica(UPDATED_APLICA)
            .descripcion(UPDATED_DESCRIPCION);
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
        ProductoProyectoDTO productoProyectoDTO = productoProyectoMapper.toDto(productoProyecto);
        restProductoProyectoMockMvc.perform(post("/api/producto-proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productoProyectoDTO)))
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
        ProductoProyectoDTO productoProyectoDTO = productoProyectoMapper.toDto(productoProyecto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoProyectoMockMvc.perform(post("/api/producto-proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productoProyectoDTO)))
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
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].aplica").value(hasItem(DEFAULT_APLICA.booleanValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getProductoProyecto() throws Exception {
        // Initialize the database
        productoProyectoRepository.saveAndFlush(productoProyecto);

        // Get the productoProyecto
        restProductoProyectoMockMvc.perform(get("/api/producto-proyectos/{id}", productoProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoProyecto.getId().intValue()))
            .andExpect(jsonPath("$.aplica").value(DEFAULT_APLICA.booleanValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
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
        productoProyectoRepository.saveAndFlush(productoProyecto);

        int databaseSizeBeforeUpdate = productoProyectoRepository.findAll().size();

        // Update the productoProyecto
        ProductoProyecto updatedProductoProyecto = productoProyectoRepository.findById(productoProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedProductoProyecto are not directly saved in db
        em.detach(updatedProductoProyecto);
        updatedProductoProyecto
            .aplica(UPDATED_APLICA)
            .descripcion(UPDATED_DESCRIPCION);
        ProductoProyectoDTO productoProyectoDTO = productoProyectoMapper.toDto(updatedProductoProyecto);

        restProductoProyectoMockMvc.perform(put("/api/producto-proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productoProyectoDTO)))
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
        ProductoProyectoDTO productoProyectoDTO = productoProyectoMapper.toDto(productoProyecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoProyectoMockMvc.perform(put("/api/producto-proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productoProyectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoProyecto in the database
        List<ProductoProyecto> productoProyectoList = productoProyectoRepository.findAll();
        assertThat(productoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductoProyecto() throws Exception {
        // Initialize the database
        productoProyectoRepository.saveAndFlush(productoProyecto);

        int databaseSizeBeforeDelete = productoProyectoRepository.findAll().size();

        // Delete the productoProyecto
        restProductoProyectoMockMvc.perform(delete("/api/producto-proyectos/{id}", productoProyecto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoProyecto> productoProyectoList = productoProyectoRepository.findAll();
        assertThat(productoProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
