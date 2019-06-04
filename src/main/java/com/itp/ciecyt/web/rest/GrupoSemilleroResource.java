package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.GrupoSemillero;
import com.itp.ciecyt.service.GrupoSemilleroService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.GrupoSemillero}.
 */
@RestController
@RequestMapping("/api")
public class GrupoSemilleroResource {

    private final Logger log = LoggerFactory.getLogger(GrupoSemilleroResource.class);

    private static final String ENTITY_NAME = "grupoSemillero";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GrupoSemilleroService grupoSemilleroService;

    public GrupoSemilleroResource(GrupoSemilleroService grupoSemilleroService) {
        this.grupoSemilleroService = grupoSemilleroService;
    }

    /**
     * {@code POST  /grupo-semilleros} : Create a new grupoSemillero.
     *
     * @param grupoSemillero the grupoSemillero to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new grupoSemillero, or with status {@code 400 (Bad Request)} if the grupoSemillero has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/grupo-semilleros")
    public ResponseEntity<GrupoSemillero> createGrupoSemillero(@RequestBody GrupoSemillero grupoSemillero) throws URISyntaxException {
        log.debug("REST request to save GrupoSemillero : {}", grupoSemillero);
        if (grupoSemillero.getId() != null) {
            throw new BadRequestAlertException("A new grupoSemillero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GrupoSemillero result = grupoSemilleroService.save(grupoSemillero);
        return ResponseEntity.created(new URI("/api/grupo-semilleros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /grupo-semilleros} : Updates an existing grupoSemillero.
     *
     * @param grupoSemillero the grupoSemillero to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated grupoSemillero,
     * or with status {@code 400 (Bad Request)} if the grupoSemillero is not valid,
     * or with status {@code 500 (Internal Server Error)} if the grupoSemillero couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/grupo-semilleros")
    public ResponseEntity<GrupoSemillero> updateGrupoSemillero(@RequestBody GrupoSemillero grupoSemillero) throws URISyntaxException {
        log.debug("REST request to update GrupoSemillero : {}", grupoSemillero);
        if (grupoSemillero.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GrupoSemillero result = grupoSemilleroService.save(grupoSemillero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, grupoSemillero.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /grupo-semilleros} : get all the grupoSemilleros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of grupoSemilleros in body.
     */
    @GetMapping("/grupo-semilleros")
    public List<GrupoSemillero> getAllGrupoSemilleros() {
        log.debug("REST request to get all GrupoSemilleros");
        return grupoSemilleroService.findAll();
    }

    /**
     * {@code GET  /grupo-semilleros/:id} : get the "id" grupoSemillero.
     *
     * @param id the id of the grupoSemillero to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the grupoSemillero, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/grupo-semilleros/{id}")
    public ResponseEntity<GrupoSemillero> getGrupoSemillero(@PathVariable Long id) {
        log.debug("REST request to get GrupoSemillero : {}", id);
        Optional<GrupoSemillero> grupoSemillero = grupoSemilleroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(grupoSemillero);
    }

    /**
     * {@code DELETE  /grupo-semilleros/:id} : delete the "id" grupoSemillero.
     *
     * @param id the id of the grupoSemillero to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/grupo-semilleros/{id}")
    public ResponseEntity<Void> deleteGrupoSemillero(@PathVariable Long id) {
        log.debug("REST request to delete GrupoSemillero : {}", id);
        grupoSemilleroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
