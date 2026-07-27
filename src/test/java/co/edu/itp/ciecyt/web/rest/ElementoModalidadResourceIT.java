package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.CiecytApp;
import co.edu.itp.ciecyt.domain.ElementoModalidad;
import co.edu.itp.ciecyt.repository.ElementoModalidadRepository;
import co.edu.itp.ciecyt.service.ElementoModalidadService;
import co.edu.itp.ciecyt.service.dto.ElementoModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.ElementoModalidadMapper;

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
 * Integration tests for the {@link ElementoModalidadResource} REST controller.
 */
@SpringBootTest(classes = CiecytApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ElementoModalidadResourceIT {

    @Autowired
    private ElementoModalidadRepository elementoModalidadRepository;

    @Autowired
    private ElementoModalidadMapper elementoModalidadMapper;

    @Autowired
    private ElementoModalidadService elementoModalidadService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restElementoModalidadMockMvc;

    private ElementoModalidad elementoModalidad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementoModalidad createEntity(EntityManager em) {
        ElementoModalidad elementoModalidad = new ElementoModalidad();
        return elementoModalidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ElementoModalidad createUpdatedEntity(EntityManager em) {
        ElementoModalidad elementoModalidad = new ElementoModalidad();
        return elementoModalidad;
    }

    @BeforeEach
    public void initTest() {
        elementoModalidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createElementoModalidad() throws Exception {
        int databaseSizeBeforeCreate = elementoModalidadRepository.findAll().size();
        // Create the ElementoModalidad
        ElementoModalidadDTO elementoModalidadDTO = elementoModalidadMapper.toDto(elementoModalidad);
        restElementoModalidadMockMvc.perform(post("/api/elemento-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoModalidadDTO)))
            .andExpect(status().isCreated());

        // Validate the ElementoModalidad in the database
        List<ElementoModalidad> elementoModalidadList = elementoModalidadRepository.findAll();
        assertThat(elementoModalidadList).hasSize(databaseSizeBeforeCreate + 1);
        ElementoModalidad testElementoModalidad = elementoModalidadList.get(elementoModalidadList.size() - 1);
    }

    @Test
    @Transactional
    public void createElementoModalidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = elementoModalidadRepository.findAll().size();

        // Create the ElementoModalidad with an existing ID
        elementoModalidad.setId(1L);
        ElementoModalidadDTO elementoModalidadDTO = elementoModalidadMapper.toDto(elementoModalidad);

        // An entity with an existing ID cannot be created, so this API call must fail
        restElementoModalidadMockMvc.perform(post("/api/elemento-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoModalidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElementoModalidad in the database
        List<ElementoModalidad> elementoModalidadList = elementoModalidadRepository.findAll();
        assertThat(elementoModalidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllElementoModalidads() throws Exception {
        // Initialize the database
        elementoModalidadRepository.saveAndFlush(elementoModalidad);

        // Get all the elementoModalidadList
        restElementoModalidadMockMvc.perform(get("/api/elemento-modalidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(elementoModalidad.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getElementoModalidad() throws Exception {
        // Initialize the database
        elementoModalidadRepository.saveAndFlush(elementoModalidad);

        // Get the elementoModalidad
        restElementoModalidadMockMvc.perform(get("/api/elemento-modalidads/{id}", elementoModalidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(elementoModalidad.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingElementoModalidad() throws Exception {
        // Get the elementoModalidad
        restElementoModalidadMockMvc.perform(get("/api/elemento-modalidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateElementoModalidad() throws Exception {
        // Initialize the database
        elementoModalidadRepository.saveAndFlush(elementoModalidad);

        int databaseSizeBeforeUpdate = elementoModalidadRepository.findAll().size();

        // Update the elementoModalidad
        ElementoModalidad updatedElementoModalidad = elementoModalidadRepository.findById(elementoModalidad.getId()).get();
        // Disconnect from session so that the updates on updatedElementoModalidad are not directly saved in db
        em.detach(updatedElementoModalidad);
        ElementoModalidadDTO elementoModalidadDTO = elementoModalidadMapper.toDto(updatedElementoModalidad);

        restElementoModalidadMockMvc.perform(put("/api/elemento-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoModalidadDTO)))
            .andExpect(status().isOk());

        // Validate the ElementoModalidad in the database
        List<ElementoModalidad> elementoModalidadList = elementoModalidadRepository.findAll();
        assertThat(elementoModalidadList).hasSize(databaseSizeBeforeUpdate);
        ElementoModalidad testElementoModalidad = elementoModalidadList.get(elementoModalidadList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingElementoModalidad() throws Exception {
        int databaseSizeBeforeUpdate = elementoModalidadRepository.findAll().size();

        // Create the ElementoModalidad
        ElementoModalidadDTO elementoModalidadDTO = elementoModalidadMapper.toDto(elementoModalidad);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElementoModalidadMockMvc.perform(put("/api/elemento-modalidads")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(elementoModalidadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ElementoModalidad in the database
        List<ElementoModalidad> elementoModalidadList = elementoModalidadRepository.findAll();
        assertThat(elementoModalidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteElementoModalidad() throws Exception {
        // Initialize the database
        elementoModalidadRepository.saveAndFlush(elementoModalidad);

        int databaseSizeBeforeDelete = elementoModalidadRepository.findAll().size();

        // Delete the elementoModalidad
        restElementoModalidadMockMvc.perform(delete("/api/elemento-modalidads/{id}", elementoModalidad.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ElementoModalidad> elementoModalidadList = elementoModalidadRepository.findAll();
        assertThat(elementoModalidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
