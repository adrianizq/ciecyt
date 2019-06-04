package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Elementos;
import com.itp.ciecyt.service.ElementosService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Elementos}.
 */
@RestController
@RequestMapping("/api")
public class ElementosResource {

    private final Logger log = LoggerFactory.getLogger(ElementosResource.class);

    private static final String ENTITY_NAME = "elementos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementosService elementosService;

    public ElementosResource(ElementosService elementosService) {
        this.elementosService = elementosService;
    }

    /**
     * {@code POST  /elementos} : Create a new elementos.
     *
     * @param elementos the elementos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementos, or with status {@code 400 (Bad Request)} if the elementos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/elementos")
    public ResponseEntity<Elementos> createElementos(@RequestBody Elementos elementos) throws URISyntaxException {
        log.debug("REST request to save Elementos : {}", elementos);
        if (elementos.getId() != null) {
            throw new BadRequestAlertException("A new elementos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Elementos result = elementosService.save(elementos);
        return ResponseEntity.created(new URI("/api/elementos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /elementos} : Updates an existing elementos.
     *
     * @param elementos the elementos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementos,
     * or with status {@code 400 (Bad Request)} if the elementos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/elementos")
    public ResponseEntity<Elementos> updateElementos(@RequestBody Elementos elementos) throws URISyntaxException {
        log.debug("REST request to update Elementos : {}", elementos);
        if (elementos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Elementos result = elementosService.save(elementos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /elementos} : get all the elementos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementos in body.
     */
    @GetMapping("/elementos")
    public List<Elementos> getAllElementos() {
        log.debug("REST request to get all Elementos");
        return elementosService.findAll();
    }

    /**
     * {@code GET  /elementos/:id} : get the "id" elementos.
     *
     * @param id the id of the elementos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elementos/{id}")
    public ResponseEntity<Elementos> getElementos(@PathVariable Long id) {
        log.debug("REST request to get Elementos : {}", id);
        Optional<Elementos> elementos = elementosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elementos);
    }

    /**
     * {@code DELETE  /elementos/:id} : delete the "id" elementos.
     *
     * @param id the id of the elementos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/elementos/{id}")
    public ResponseEntity<Void> deleteElementos(@PathVariable Long id) {
        log.debug("REST request to delete Elementos : {}", id);
        elementosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
