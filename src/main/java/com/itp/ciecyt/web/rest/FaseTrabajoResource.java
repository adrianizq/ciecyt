package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.FaseTrabajo;
import com.itp.ciecyt.service.FaseTrabajoService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.FaseTrabajo}.
 */
@RestController
@RequestMapping("/api")
public class FaseTrabajoResource {

    private final Logger log = LoggerFactory.getLogger(FaseTrabajoResource.class);

    private static final String ENTITY_NAME = "faseTrabajo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FaseTrabajoService faseTrabajoService;

    public FaseTrabajoResource(FaseTrabajoService faseTrabajoService) {
        this.faseTrabajoService = faseTrabajoService;
    }

    /**
     * {@code POST  /fase-trabajos} : Create a new faseTrabajo.
     *
     * @param faseTrabajo the faseTrabajo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faseTrabajo, or with status {@code 400 (Bad Request)} if the faseTrabajo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fase-trabajos")
    public ResponseEntity<FaseTrabajo> createFaseTrabajo(@RequestBody FaseTrabajo faseTrabajo) throws URISyntaxException {
        log.debug("REST request to save FaseTrabajo : {}", faseTrabajo);
        if (faseTrabajo.getId() != null) {
            throw new BadRequestAlertException("A new faseTrabajo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaseTrabajo result = faseTrabajoService.save(faseTrabajo);
        return ResponseEntity.created(new URI("/api/fase-trabajos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fase-trabajos} : Updates an existing faseTrabajo.
     *
     * @param faseTrabajo the faseTrabajo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faseTrabajo,
     * or with status {@code 400 (Bad Request)} if the faseTrabajo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the faseTrabajo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fase-trabajos")
    public ResponseEntity<FaseTrabajo> updateFaseTrabajo(@RequestBody FaseTrabajo faseTrabajo) throws URISyntaxException {
        log.debug("REST request to update FaseTrabajo : {}", faseTrabajo);
        if (faseTrabajo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FaseTrabajo result = faseTrabajoService.save(faseTrabajo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, faseTrabajo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fase-trabajos} : get all the faseTrabajos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faseTrabajos in body.
     */
    @GetMapping("/fase-trabajos")
    public List<FaseTrabajo> getAllFaseTrabajos() {
        log.debug("REST request to get all FaseTrabajos");
        return faseTrabajoService.findAll();
    }

    /**
     * {@code GET  /fase-trabajos/:id} : get the "id" faseTrabajo.
     *
     * @param id the id of the faseTrabajo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the faseTrabajo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fase-trabajos/{id}")
    public ResponseEntity<FaseTrabajo> getFaseTrabajo(@PathVariable Long id) {
        log.debug("REST request to get FaseTrabajo : {}", id);
        Optional<FaseTrabajo> faseTrabajo = faseTrabajoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(faseTrabajo);
    }

    /**
     * {@code DELETE  /fase-trabajos/:id} : delete the "id" faseTrabajo.
     *
     * @param id the id of the faseTrabajo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fase-trabajos/{id}")
    public ResponseEntity<Void> deleteFaseTrabajo(@PathVariable Long id) {
        log.debug("REST request to delete FaseTrabajo : {}", id);
        faseTrabajoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
