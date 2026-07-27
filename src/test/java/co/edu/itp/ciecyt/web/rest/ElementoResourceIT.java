package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.Elemento;
import co.edu.itp.ciecyt.repository.ElementoRepository;
import co.edu.itp.ciecyt.service.ElementoService;
import co.edu.itp.ciecyt.service.dto.ElementoDTO;
import co.edu.itp.ciecyt.service.mapper.ElementoMapper;

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
 * Integration tests for the {@link ElementoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ElementoResourceIT {

    private static final String DEFAULT_ELEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_ELEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private ElementoRepository elementoRepository;

    @Autowired
    private ElementoMapper elementoMapper;

    @Autowired
    private ElementoService elementoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementoMockMvc;

    private Elemento elemento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Elemento createEntity(EntityManager em) {
        Elemento elemento = new Elemento()
            .elemento(DEFAULT_ELEMENTO)
            .descripcion(DEFAULT_DESCRIPCION);
        return elemento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Elemento createUpdatedEntity(EntityManager em) {
        Elemento elemento = new Elemento()
            .elemento(UPDATED_ELEMENTO)
            .descripcion(UPDATED_DESCRIPCION);
        return elemento;
    }

    @BeforeEach
    public void initTest() {
        elemento = createEntity(em);
    }

    @Test
    @Transactional
    public void createElemento() throws Exception {
        int databaseSizeBeforeCreate = elementoRepository.findAll().size();
        // Create the Elemento
        ElementoDTO elementoDTO = elementoMapper.toDto(elemento);
        restElementoMockMvc.perform(post("/api/elementos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoDTO)))
            .andExpect(status().isCreated());

        // Validate the Elemento in the database
        List<Elemento> elementoList = elementoRepository.findAll();
        assertThat(elementoList).hasSize(databaseSizeBeforeCreate + 1);
        Elemento testElemento = elementoList.get(elementoList.size() - 1);
        assertThat(testElemento.getElemento()).isEqualTo(DEFAULT_ELEMENTO);
        assertThat(testElemento.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createElementoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementoRepository.findAll().size();

        // Create the Elemento with an existing ID
        elemento.setId(1L);
        ElementoDTO elementoDTO = elementoMapper.toDto(elemento);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementoMockMvc.perform(post("/api/elementos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Elemento in the database
        List<Elemento> elementoList = elementoRepository.findAll();
        assertThat(elementoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllElementos() throws Exception {
        // Initialize the database
        elementoRepository.saveAndFlush(elemento);

        // Get all the elementoList
        restElementoMockMvc.perform(get("/api/elementos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elemento.getId().intValue())))
            .andExpect(jsonPath("$.[*].elemento").value(hasItem(DEFAULT_ELEMENTO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getElemento() throws Exception {
        // Initialize the database
        elementoRepository.saveAndFlush(elemento);

        // Get the elemento
        restElementoMockMvc.perform(get("/api/elementos/{id}", elemento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elemento.getId().intValue()))
            .andExpect(jsonPath("$.elemento").value(DEFAULT_ELEMENTO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }
    @Test
    @Transactional
    public void getNonExistingElemento() throws Exception {
        // Get the elemento
        restElementoMockMvc.perform(get("/api/elementos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElemento() throws Exception {
        // Initialize the database
        elementoRepository.saveAndFlush(elemento);

        int databaseSizeBeforeUpdate = elementoRepository.findAll().size();

        // Update the elemento
        Elemento updatedElemento = elementoRepository.findById(elemento.getId()).get();
        // Disconnect from session so that the updates on updatedElemento are not directly saved in db
        em.detach(updatedElemento);
        updatedElemento
            .elemento(UPDATED_ELEMENTO)
            .descripcion(UPDATED_DESCRIPCION);
        ElementoDTO elementoDTO = elementoMapper.toDto(updatedElemento);

        restElementoMockMvc.perform(put("/api/elementos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoDTO)))
            .andExpect(status().isOk());

        // Validate the Elemento in the database
        List<Elemento> elementoList = elementoRepository.findAll();
        assertThat(elementoList).hasSize(databaseSizeBeforeUpdate);
        Elemento testElemento = elementoList.get(elementoList.size() - 1);
        assertThat(testElemento.getElemento()).isEqualTo(UPDATED_ELEMENTO);
        assertThat(testElemento.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingElemento() throws Exception {
        int databaseSizeBeforeUpdate = elementoRepository.findAll().size();

        // Create the Elemento
        ElementoDTO elementoDTO = elementoMapper.toDto(elemento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementoMockMvc.perform(put("/api/elementos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Elemento in the database
        List<Elemento> elementoList = elementoRepository.findAll();
        assertThat(elementoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElemento() throws Exception {
        // Initialize the database
        elementoRepository.saveAndFlush(elemento);

        int databaseSizeBeforeDelete = elementoRepository.findAll().size();

        // Delete the elemento
        restElementoMockMvc.perform(delete("/api/elementos/{id}", elemento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Elemento> elementoList = elementoRepository.findAll();
        assertThat(elementoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
