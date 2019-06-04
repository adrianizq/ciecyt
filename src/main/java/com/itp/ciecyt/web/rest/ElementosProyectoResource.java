package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.ElementosProyecto;
import com.itp.ciecyt.service.ElementosProyectoService;
import com.itp.ciecyt.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.itp.ciecyt.domain.ElementosProyecto}.
 */
@RestController
@RequestMapping("/api")
public class ElementosProyectoResource {

    private final Logger log = LoggerFactory.getLogger(ElementosProyectoResource.class);

    private static final String ENTITY_NAME = "elementosProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementosProyectoService elementosProyectoService;

    public ElementosProyectoResource(ElementosProyectoService elementosProyectoService) {
        this.elementosProyectoService = elementosProyectoService;
    }

    /**
     * {@code POST  /elementos-proyectos} : Create a new elementosProyecto.
     *
     * @param elementosProyecto the elementosProyecto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementosProyecto, or with status {@code 400 (Bad Request)} if the elementosProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/elementos-proyectos")
    public ResponseEntity<ElementosProyecto> createElementosProyecto(@RequestBody ElementosProyecto elementosProyecto) throws URISyntaxException {
        log.debug("REST request to save ElementosProyecto : {}", elementosProyecto);
        if (elementosProyecto.getId() != null) {
            throw new BadRequestAlertException("A new elementosProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElementosProyecto result = elementosProyectoService.save(elementosProyecto);
        return ResponseEntity.created(new URI("/api/elementos-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /elementos-proyectos} : Updates an existing elementosProyecto.
     *
     * @param elementosProyecto the elementosProyecto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementosProyecto,
     * or with status {@code 400 (Bad Request)} if the elementosProyecto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementosProyecto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/elementos-proyectos")
    public ResponseEntity<ElementosProyecto> updateElementosProyecto(@RequestBody ElementosProyecto elementosProyecto) throws URISyntaxException {
        log.debug("REST request to update ElementosProyecto : {}", elementosProyecto);
        if (elementosProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ElementosProyecto result = elementosProyectoService.save(elementosProyecto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementosProyecto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /elementos-proyectos} : get all the elementosProyectos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementosProyectos in body.
     */
    @GetMapping("/elementos-proyectos")
    public List<ElementosProyecto> getAllElementosProyectos() {
        log.debug("REST request to get all ElementosProyectos");
        return elementosProyectoService.findAll();
    }

    /**
     * {@code GET  /elementos-proyectos/:id} : get the "id" elementosProyecto.
     *
     * @param id the id of the elementosProyecto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementosProyecto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elementos-proyectos/{id}")
    public ResponseEntity<ElementosProyecto> getElementosProyecto(@PathVariable Long id) {
        log.debug("REST request to get ElementosProyecto : {}", id);
        Optional<ElementosProyecto> elementosProyecto = elementosProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elementosProyecto);
    }

    /**
     * {@code DELETE  /elementos-proyectos/:id} : delete the "id" elementosProyecto.
     *
     * @param id the id of the elementosProyecto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/elementos-proyectos/{id}")
    public ResponseEntity<Void> deleteElementosProyecto(@PathVariable Long id) {
        log.debug("REST request to delete ElementosProyecto : {}", id);
        elementosProyectoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
