package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.ImpactosEsperados;
import com.itp.ciecyt.service.ImpactosEsperadosService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.ImpactosEsperados}.
 */
@RestController
@RequestMapping("/api")
public class ImpactosEsperadosResource {

    private final Logger log = LoggerFactory.getLogger(ImpactosEsperadosResource.class);

    private static final String ENTITY_NAME = "impactosEsperados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpactosEsperadosService impactosEsperadosService;

    public ImpactosEsperadosResource(ImpactosEsperadosService impactosEsperadosService) {
        this.impactosEsperadosService = impactosEsperadosService;
    }

    /**
     * {@code POST  /impactos-esperados} : Create a new impactosEsperados.
     *
     * @param impactosEsperados the impactosEsperados to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impactosEsperados, or with status {@code 400 (Bad Request)} if the impactosEsperados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/impactos-esperados")
    public ResponseEntity<ImpactosEsperados> createImpactosEsperados(@RequestBody ImpactosEsperados impactosEsperados) throws URISyntaxException {
        log.debug("REST request to save ImpactosEsperados : {}", impactosEsperados);
        if (impactosEsperados.getId() != null) {
            throw new BadRequestAlertException("A new impactosEsperados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImpactosEsperados result = impactosEsperadosService.save(impactosEsperados);
        return ResponseEntity.created(new URI("/api/impactos-esperados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /impactos-esperados} : Updates an existing impactosEsperados.
     *
     * @param impactosEsperados the impactosEsperados to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impactosEsperados,
     * or with status {@code 400 (Bad Request)} if the impactosEsperados is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impactosEsperados couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/impactos-esperados")
    public ResponseEntity<ImpactosEsperados> updateImpactosEsperados(@RequestBody ImpactosEsperados impactosEsperados) throws URISyntaxException {
        log.debug("REST request to update ImpactosEsperados : {}", impactosEsperados);
        if (impactosEsperados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImpactosEsperados result = impactosEsperadosService.save(impactosEsperados);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impactosEsperados.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /impactos-esperados} : get all the impactosEsperados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impactosEsperados in body.
     */
    @GetMapping("/impactos-esperados")
    public List<ImpactosEsperados> getAllImpactosEsperados() {
        log.debug("REST request to get all ImpactosEsperados");
        return impactosEsperadosService.findAll();
    }

    /**
     * {@code GET  /impactos-esperados/:id} : get the "id" impactosEsperados.
     *
     * @param id the id of the impactosEsperados to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impactosEsperados, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/impactos-esperados/{id}")
    public ResponseEntity<ImpactosEsperados> getImpactosEsperados(@PathVariable Long id) {
        log.debug("REST request to get ImpactosEsperados : {}", id);
        Optional<ImpactosEsperados> impactosEsperados = impactosEsperadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impactosEsperados);
    }

    /**
     * {@code DELETE  /impactos-esperados/:id} : delete the "id" impactosEsperados.
     *
     * @param id the id of the impactosEsperados to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/impactos-esperados/{id}")
    public ResponseEntity<Void> deleteImpactosEsperados(@PathVariable Long id) {
        log.debug("REST request to delete ImpactosEsperados : {}", id);
        impactosEsperadosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
