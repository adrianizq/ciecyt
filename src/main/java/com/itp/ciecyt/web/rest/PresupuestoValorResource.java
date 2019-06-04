package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.PresupuestoValor;
import com.itp.ciecyt.service.PresupuestoValorService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.PresupuestoValor}.
 */
@RestController
@RequestMapping("/api")
public class PresupuestoValorResource {

    private final Logger log = LoggerFactory.getLogger(PresupuestoValorResource.class);

    private static final String ENTITY_NAME = "presupuestoValor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PresupuestoValorService presupuestoValorService;

    public PresupuestoValorResource(PresupuestoValorService presupuestoValorService) {
        this.presupuestoValorService = presupuestoValorService;
    }

    /**
     * {@code POST  /presupuesto-valors} : Create a new presupuestoValor.
     *
     * @param presupuestoValor the presupuestoValor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new presupuestoValor, or with status {@code 400 (Bad Request)} if the presupuestoValor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/presupuesto-valors")
    public ResponseEntity<PresupuestoValor> createPresupuestoValor(@RequestBody PresupuestoValor presupuestoValor) throws URISyntaxException {
        log.debug("REST request to save PresupuestoValor : {}", presupuestoValor);
        if (presupuestoValor.getId() != null) {
            throw new BadRequestAlertException("A new presupuestoValor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PresupuestoValor result = presupuestoValorService.save(presupuestoValor);
        return ResponseEntity.created(new URI("/api/presupuesto-valors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /presupuesto-valors} : Updates an existing presupuestoValor.
     *
     * @param presupuestoValor the presupuestoValor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presupuestoValor,
     * or with status {@code 400 (Bad Request)} if the presupuestoValor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the presupuestoValor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/presupuesto-valors")
    public ResponseEntity<PresupuestoValor> updatePresupuestoValor(@RequestBody PresupuestoValor presupuestoValor) throws URISyntaxException {
        log.debug("REST request to update PresupuestoValor : {}", presupuestoValor);
        if (presupuestoValor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PresupuestoValor result = presupuestoValorService.save(presupuestoValor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, presupuestoValor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /presupuesto-valors} : get all the presupuestoValors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of presupuestoValors in body.
     */
    @GetMapping("/presupuesto-valors")
    public List<PresupuestoValor> getAllPresupuestoValors() {
        log.debug("REST request to get all PresupuestoValors");
        return presupuestoValorService.findAll();
    }

    /**
     * {@code GET  /presupuesto-valors/:id} : get the "id" presupuestoValor.
     *
     * @param id the id of the presupuestoValor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the presupuestoValor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/presupuesto-valors/{id}")
    public ResponseEntity<PresupuestoValor> getPresupuestoValor(@PathVariable Long id) {
        log.debug("REST request to get PresupuestoValor : {}", id);
        Optional<PresupuestoValor> presupuestoValor = presupuestoValorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(presupuestoValor);
    }

    /**
     * {@code DELETE  /presupuesto-valors/:id} : delete the "id" presupuestoValor.
     *
     * @param id the id of the presupuestoValor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/presupuesto-valors/{id}")
    public ResponseEntity<Void> deletePresupuestoValor(@PathVariable Long id) {
        log.debug("REST request to delete PresupuestoValor : {}", id);
        presupuestoValorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
