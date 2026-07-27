package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Categorizacion;
import co.edu.itp.ciecyt.repository.CategorizacionRepository;
import co.edu.itp.ciecyt.service.CategorizacionService;
import co.edu.itp.ciecyt.service.dto.CategorizacionDTO;
import co.edu.itp.ciecyt.service.mapper.CategorizacionMapper;

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
 * Integration tests for the {@link CategorizacionResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategorizacionResourceIT {

    private static final String DEFAULT_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private CategorizacionRepository categorizacionRepository;

    @Autowired
    private CategorizacionMapper categorizacionMapper;

    @Autowired
    private CategorizacionService categorizacionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorizacionMockMvc;

    private Categorizacion categorizacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorizacion createEntity(EntityManager em) {
        Categorizacion categorizacion = new Categorizacion()
            .categoria(DEFAULT_CATEGORIA)
            .descripcion(DEFAULT_DESCRIPCION);
        return categorizacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorizacion createUpdatedEntity(EntityManager em) {
        Categorizacion categorizacion = new Categorizacion()
            .categoria(UPDATED_CATEGORIA)
            .descripcion(UPDATED_DESCRIPCION);
        return categorizacion;
    }

    @BeforeEach
    public void initTest() {
        categorizacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategorizacion() throws Exception {
        int databaseSizeBeforeCreate = categorizacionRepository.findAll().size();
        // Create the Categorizacion
        CategorizacionDTO categorizacionDTO = categorizacionMapper.toDto(categorizacion);
        restCategorizacionMockMvc.perform(post("/api/categorizacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorizacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Categorizacion in the database
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeCreate + 1);
        Categorizacion testCategorizacion = categorizacionList.get(categorizacionList.size() - 1);
        assertThat(testCategorizacion.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
        assertThat(testCategorizacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createCategorizacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categorizacionRepository.findAll().size();

        // Create the Categorizacion with an existing ID
        categorizacion.setId(1L);
        CategorizacionDTO categorizacionDTO = categorizacionMapper.toDto(categorizacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorizacionMockMvc.perform(post("/api/categorizacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorizacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categorizacion in the database
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategorizacions() throws Exception {
        // Initialize the database
        categorizacionRepository.saveAndFlush(categorizacion);

        // Get all the categorizacionList
        restCategorizacionMockMvc.perform(get("/api/categorizacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorizacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getCategorizacion() throws Exception {
        // Initialize the database
        categorizacionRepository.saveAndFlush(categorizacion);

        // Get the categorizacion
        restCategorizacionMockMvc.perform(get("/api/categorizacions/{id}", categorizacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorizacion.getId().intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }
    @Test
    @Transactional
    public void getNonExistingCategorizacion() throws Exception {
        // Get the categorizacion
        restCategorizacionMockMvc.perform(get("/api/categorizacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategorizacion() throws Exception {
        // Initialize the database
        categorizacionRepository.saveAndFlush(categorizacion);

        int databaseSizeBeforeUpdate = categorizacionRepository.findAll().size();

        // Update the categorizacion
        Categorizacion updatedCategorizacion = categorizacionRepository.findById(categorizacion.getId()).get();
        // Disconnect from session so that the updates on updatedCategorizacion are not directly saved in db
        em.detach(updatedCategorizacion);
        updatedCategorizacion
            .categoria(UPDATED_CATEGORIA)
            .descripcion(UPDATED_DESCRIPCION);
        CategorizacionDTO categorizacionDTO = categorizacionMapper.toDto(updatedCategorizacion);

        restCategorizacionMockMvc.perform(put("/api/categorizacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorizacionDTO)))
            .andExpect(status().isOk());

        // Validate the Categorizacion in the database
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeUpdate);
        Categorizacion testCategorizacion = categorizacionList.get(categorizacionList.size() - 1);
        assertThat(testCategorizacion.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
        assertThat(testCategorizacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingCategorizacion() throws Exception {
        int databaseSizeBeforeUpdate = categorizacionRepository.findAll().size();

        // Create the Categorizacion
        CategorizacionDTO categorizacionDTO = categorizacionMapper.toDto(categorizacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorizacionMockMvc.perform(put("/api/categorizacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(categorizacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categorizacion in the database
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategorizacion() throws Exception {
        // Initialize the database
        categorizacionRepository.saveAndFlush(categorizacion);

        int databaseSizeBeforeDelete = categorizacionRepository.findAll().size();

        // Delete the categorizacion
        restCategorizacionMockMvc.perform(delete("/api/categorizacions/{id}", categorizacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categorizacion> categorizacionList = categorizacionRepository.findAll();
        assertThat(categorizacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
