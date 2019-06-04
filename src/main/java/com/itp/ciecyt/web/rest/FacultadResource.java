package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Facultad;
import com.itp.ciecyt.service.FacultadService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Facultad}.
 */
@RestController
@RequestMapping("/api")
public class FacultadResource {

    private final Logger log = LoggerFactory.getLogger(FacultadResource.class);

    private static final String ENTITY_NAME = "facultad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacultadService facultadService;

    public FacultadResource(FacultadService facultadService) {
        this.facultadService = facultadService;
    }

    /**
     * {@code POST  /facultads} : Create a new facultad.
     *
     * @param facultad the facultad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facultad, or with status {@code 400 (Bad Request)} if the facultad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facultads")
    public ResponseEntity<Facultad> createFacultad(@RequestBody Facultad facultad) throws URISyntaxException {
        log.debug("REST request to save Facultad : {}", facultad);
        if (facultad.getId() != null) {
            throw new BadRequestAlertException("A new facultad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Facultad result = facultadService.save(facultad);
        return ResponseEntity.created(new URI("/api/facultads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facultads} : Updates an existing facultad.
     *
     * @param facultad the facultad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facultad,
     * or with status {@code 400 (Bad Request)} if the facultad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facultad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facultads")
    public ResponseEntity<Facultad> updateFacultad(@RequestBody Facultad facultad) throws URISyntaxException {
        log.debug("REST request to update Facultad : {}", facultad);
        if (facultad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Facultad result = facultadService.save(facultad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facultad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facultads} : get all the facultads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facultads in body.
     */
    @GetMapping("/facultads")
    public List<Facultad> getAllFacultads() {
        log.debug("REST request to get all Facultads");
        return facultadService.findAll();
    }

    /**
     * {@code GET  /facultads/:id} : get the "id" facultad.
     *
     * @param id the id of the facultad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facultad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facultads/{id}")
    public ResponseEntity<Facultad> getFacultad(@PathVariable Long id) {
        log.debug("REST request to get Facultad : {}", id);
        Optional<Facultad> facultad = facultadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facultad);
    }

    /**
     * {@code DELETE  /facultads/:id} : delete the "id" facultad.
     *
     * @param id the id of the facultad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facultads/{id}")
    public ResponseEntity<Void> deleteFacultad(@PathVariable Long id) {
        log.debug("REST request to delete Facultad : {}", id);
        facultadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
