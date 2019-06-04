package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Categorizacion;
import com.itp.ciecyt.service.CategorizacionService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Categorizacion}.
 */
@RestController
@RequestMapping("/api")
public class CategorizacionResource {

    private final Logger log = LoggerFactory.getLogger(CategorizacionResource.class);

    private static final String ENTITY_NAME = "categorizacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorizacionService categorizacionService;

    public CategorizacionResource(CategorizacionService categorizacionService) {
        this.categorizacionService = categorizacionService;
    }

    /**
     * {@code POST  /categorizacions} : Create a new categorizacion.
     *
     * @param categorizacion the categorizacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorizacion, or with status {@code 400 (Bad Request)} if the categorizacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorizacions")
    public ResponseEntity<Categorizacion> createCategorizacion(@RequestBody Categorizacion categorizacion) throws URISyntaxException {
        log.debug("REST request to save Categorizacion : {}", categorizacion);
        if (categorizacion.getId() != null) {
            throw new BadRequestAlertException("A new categorizacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Categorizacion result = categorizacionService.save(categorizacion);
        return ResponseEntity.created(new URI("/api/categorizacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorizacions} : Updates an existing categorizacion.
     *
     * @param categorizacion the categorizacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorizacion,
     * or with status {@code 400 (Bad Request)} if the categorizacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorizacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorizacions")
    public ResponseEntity<Categorizacion> updateCategorizacion(@RequestBody Categorizacion categorizacion) throws URISyntaxException {
        log.debug("REST request to update Categorizacion : {}", categorizacion);
        if (categorizacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Categorizacion result = categorizacionService.save(categorizacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorizacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categorizacions} : get all the categorizacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorizacions in body.
     */
    @GetMapping("/categorizacions")
    public List<Categorizacion> getAllCategorizacions() {
        log.debug("REST request to get all Categorizacions");
        return categorizacionService.findAll();
    }

    /**
     * {@code GET  /categorizacions/:id} : get the "id" categorizacion.
     *
     * @param id the id of the categorizacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorizacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorizacions/{id}")
    public ResponseEntity<Categorizacion> getCategorizacion(@PathVariable Long id) {
        log.debug("REST request to get Categorizacion : {}", id);
        Optional<Categorizacion> categorizacion = categorizacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorizacion);
    }

    /**
     * {@code DELETE  /categorizacions/:id} : delete the "id" categorizacion.
     *
     * @param id the id of the categorizacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorizacions/{id}")
    public ResponseEntity<Void> deleteCategorizacion(@PathVariable Long id) {
        log.debug("REST request to delete Categorizacion : {}", id);
        categorizacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
