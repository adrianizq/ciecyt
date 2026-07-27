package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.ElementoProyecto;
import co.edu.itp.ciecyt.repository.ElementoProyectoRepository;
import co.edu.itp.ciecyt.service.ElementoProyectoService;
import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import co.edu.itp.ciecyt.service.mapper.ElementoProyectoMapper;

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
 * Integration tests for the {@link ElementoProyectoResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ElementoProyectoResourceIT {

    private static final String DEFAULT_DATO = "AAAAAAAAAA";
    private static final String UPDATED_DATO = "BBBBBBBBBB";

    private static final String DEFAULT_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION = "BBBBBBBBBB";

    private static final Long DEFAULT_ELEMENTO_FASES_ID = 1L;
    private static final Long UPDATED_ELEMENTO_FASES_ID = 2L;

    @Autowired
    private ElementoProyectoRepository elementoProyectoRepository;

    @Autowired
    private ElementoProyectoMapper elementoProyectoMapper;

    @Autowired
    private ElementoProyectoService elementoProyectoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementoProyectoMockMvc;

    private ElementoProyecto elementoProyecto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementoProyecto createEntity(EntityManager em) {
        ElementoProyecto elementoProyecto = new ElementoProyecto()
            .dato(DEFAULT_DATO)
            .elementoProyectoProyectoDescripcion(DEFAULT_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION)
            .elementoFasesId(DEFAULT_ELEMENTO_FASES_ID);
        return elementoProyecto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementoProyecto createUpdatedEntity(EntityManager em) {
        ElementoProyecto elementoProyecto = new ElementoProyecto()
            .dato(UPDATED_DATO)
            .elementoProyectoProyectoDescripcion(UPDATED_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION)
            .elementoFasesId(UPDATED_ELEMENTO_FASES_ID);
        return elementoProyecto;
    }

    @BeforeEach
    public void initTest() {
        elementoProyecto = createEntity(em);
    }

    @Test
    @Transactional
    public void createElementoProyecto() throws Exception {
        int databaseSizeBeforeCreate = elementoProyectoRepository.findAll().size();
        // Create the ElementoProyecto
        ElementoProyectoDTO elementoProyectoDTO = elementoProyectoMapper.toDto(elementoProyecto);
        restElementoProyectoMockMvc.perform(post("/api/elemento-proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoProyectoDTO)))
            .andExpect(status().isCreated());

        // Validate the ElementoProyecto in the database
        List<ElementoProyecto> elementoProyectoList = elementoProyectoRepository.findAll();
        assertThat(elementoProyectoList).hasSize(databaseSizeBeforeCreate + 1);
        ElementoProyecto testElementoProyecto = elementoProyectoList.get(elementoProyectoList.size() - 1);
        assertThat(testElementoProyecto.getDato()).isEqualTo(DEFAULT_DATO);
        assertThat(testElementoProyecto.getElementoProyectoProyectoDescripcion()).isEqualTo(DEFAULT_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION);
        assertThat(testElementoProyecto.getElementoFasesId()).isEqualTo(DEFAULT_ELEMENTO_FASES_ID);
    }

    @Test
    @Transactional
    public void createElementoProyectoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementoProyectoRepository.findAll().size();

        // Create the ElementoProyecto with an existing ID
        elementoProyecto.setId(1L);
        ElementoProyectoDTO elementoProyectoDTO = elementoProyectoMapper.toDto(elementoProyecto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementoProyectoMockMvc.perform(post("/api/elemento-proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoProyectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElementoProyecto in the database
        List<ElementoProyecto> elementoProyectoList = elementoProyectoRepository.findAll();
        assertThat(elementoProyectoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllElementoProyectos() throws Exception {
        // Initialize the database
        elementoProyectoRepository.saveAndFlush(elementoProyecto);

        // Get all the elementoProyectoList
        restElementoProyectoMockMvc.perform(get("/api/elemento-proyectos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementoProyecto.getId().intValue())))
            .andExpect(jsonPath("$.[*].dato").value(hasItem(DEFAULT_DATO)))
            .andExpect(jsonPath("$.[*].elementoProyectoProyectoDescripcion").value(hasItem(DEFAULT_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].elementoFasesId").value(hasItem(DEFAULT_ELEMENTO_FASES_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getElementoProyecto() throws Exception {
        // Initialize the database
        elementoProyectoRepository.saveAndFlush(elementoProyecto);

        // Get the elementoProyecto
        restElementoProyectoMockMvc.perform(get("/api/elemento-proyectos/{id}", elementoProyecto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementoProyecto.getId().intValue()))
            .andExpect(jsonPath("$.dato").value(DEFAULT_DATO))
            .andExpect(jsonPath("$.elementoProyectoProyectoDescripcion").value(DEFAULT_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION))
            .andExpect(jsonPath("$.elementoFasesId").value(DEFAULT_ELEMENTO_FASES_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingElementoProyecto() throws Exception {
        // Get the elementoProyecto
        restElementoProyectoMockMvc.perform(get("/api/elemento-proyectos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElementoProyecto() throws Exception {
        // Initialize the database
        elementoProyectoRepository.saveAndFlush(elementoProyecto);

        int databaseSizeBeforeUpdate = elementoProyectoRepository.findAll().size();

        // Update the elementoProyecto
        ElementoProyecto updatedElementoProyecto = elementoProyectoRepository.findById(elementoProyecto.getId()).get();
        // Disconnect from session so that the updates on updatedElementoProyecto are not directly saved in db
        em.detach(updatedElementoProyecto);
        updatedElementoProyecto
            .dato(UPDATED_DATO)
            .elementoProyectoProyectoDescripcion(UPDATED_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION)
            .elementoFasesId(UPDATED_ELEMENTO_FASES_ID);
        ElementoProyectoDTO elementoProyectoDTO = elementoProyectoMapper.toDto(updatedElementoProyecto);

        restElementoProyectoMockMvc.perform(put("/api/elemento-proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoProyectoDTO)))
            .andExpect(status().isOk());

        // Validate the ElementoProyecto in the database
        List<ElementoProyecto> elementoProyectoList = elementoProyectoRepository.findAll();
        assertThat(elementoProyectoList).hasSize(databaseSizeBeforeUpdate);
        ElementoProyecto testElementoProyecto = elementoProyectoList.get(elementoProyectoList.size() - 1);
        assertThat(testElementoProyecto.getDato()).isEqualTo(UPDATED_DATO);
        assertThat(testElementoProyecto.getElementoProyectoProyectoDescripcion()).isEqualTo(UPDATED_ELEMENTO_PROYECTO_PROYECTO_DESCRIPCION);
        assertThat(testElementoProyecto.getElementoFasesId()).isEqualTo(UPDATED_ELEMENTO_FASES_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingElementoProyecto() throws Exception {
        int databaseSizeBeforeUpdate = elementoProyectoRepository.findAll().size();

        // Create the ElementoProyecto
        ElementoProyectoDTO elementoProyectoDTO = elementoProyectoMapper.toDto(elementoProyecto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementoProyectoMockMvc.perform(put("/api/elemento-proyectos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoProyectoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElementoProyecto in the database
        List<ElementoProyecto> elementoProyectoList = elementoProyectoRepository.findAll();
        assertThat(elementoProyectoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElementoProyecto() throws Exception {
        // Initialize the database
        elementoProyectoRepository.saveAndFlush(elementoProyecto);

        int databaseSizeBeforeDelete = elementoProyectoRepository.findAll().size();

        // Delete the elementoProyecto
        restElementoProyectoMockMvc.perform(delete("/api/elemento-proyectos/{id}", elementoProyecto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElementoProyecto> elementoProyectoList = elementoProyectoRepository.findAll();
        assertThat(elementoProyectoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
