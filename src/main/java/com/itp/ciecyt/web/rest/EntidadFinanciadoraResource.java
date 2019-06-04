package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.EntidadFinanciadora;
import com.itp.ciecyt.service.EntidadFinanciadoraService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.EntidadFinanciadora}.
 */
@RestController
@RequestMapping("/api")
public class EntidadFinanciadoraResource {

    private final Logger log = LoggerFactory.getLogger(EntidadFinanciadoraResource.class);

    private static final String ENTITY_NAME = "entidadFinanciadora";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntidadFinanciadoraService entidadFinanciadoraService;

    public EntidadFinanciadoraResource(EntidadFinanciadoraService entidadFinanciadoraService) {
        this.entidadFinanciadoraService = entidadFinanciadoraService;
    }

    /**
     * {@code POST  /entidad-financiadoras} : Create a new entidadFinanciadora.
     *
     * @param entidadFinanciadora the entidadFinanciadora to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entidadFinanciadora, or with status {@code 400 (Bad Request)} if the entidadFinanciadora has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entidad-financiadoras")
    public ResponseEntity<EntidadFinanciadora> createEntidadFinanciadora(@RequestBody EntidadFinanciadora entidadFinanciadora) throws URISyntaxException {
        log.debug("REST request to save EntidadFinanciadora : {}", entidadFinanciadora);
        if (entidadFinanciadora.getId() != null) {
            throw new BadRequestAlertException("A new entidadFinanciadora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntidadFinanciadora result = entidadFinanciadoraService.save(entidadFinanciadora);
        return ResponseEntity.created(new URI("/api/entidad-financiadoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entidad-financiadoras} : Updates an existing entidadFinanciadora.
     *
     * @param entidadFinanciadora the entidadFinanciadora to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidadFinanciadora,
     * or with status {@code 400 (Bad Request)} if the entidadFinanciadora is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entidadFinanciadora couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entidad-financiadoras")
    public ResponseEntity<EntidadFinanciadora> updateEntidadFinanciadora(@RequestBody EntidadFinanciadora entidadFinanciadora) throws URISyntaxException {
        log.debug("REST request to update EntidadFinanciadora : {}", entidadFinanciadora);
        if (entidadFinanciadora.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntidadFinanciadora result = entidadFinanciadoraService.save(entidadFinanciadora);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidadFinanciadora.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entidad-financiadoras} : get all the entidadFinanciadoras.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entidadFinanciadoras in body.
     */
    @GetMapping("/entidad-financiadoras")
    public List<EntidadFinanciadora> getAllEntidadFinanciadoras() {
        log.debug("REST request to get all EntidadFinanciadoras");
        return entidadFinanciadoraService.findAll();
    }

    /**
     * {@code GET  /entidad-financiadoras/:id} : get the "id" entidadFinanciadora.
     *
     * @param id the id of the entidadFinanciadora to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidadFinanciadora, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entidad-financiadoras/{id}")
    public ResponseEntity<EntidadFinanciadora> getEntidadFinanciadora(@PathVariable Long id) {
        log.debug("REST request to get EntidadFinanciadora : {}", id);
        Optional<EntidadFinanciadora> entidadFinanciadora = entidadFinanciadoraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entidadFinanciadora);
    }

    /**
     * {@code DELETE  /entidad-financiadoras/:id} : delete the "id" entidadFinanciadora.
     *
     * @param id the id of the entidadFinanciadora to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entidad-financiadoras/{id}")
    public ResponseEntity<Void> deleteEntidadFinanciadora(@PathVariable Long id) {
        log.debug("REST request to delete EntidadFinanciadora : {}", id);
        entidadFinanciadoraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
