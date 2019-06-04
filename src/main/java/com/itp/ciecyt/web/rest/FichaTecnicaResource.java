package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.FichaTecnica;
import com.itp.ciecyt.service.FichaTecnicaService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.FichaTecnica}.
 */
@RestController
@RequestMapping("/api")
public class FichaTecnicaResource {

    private final Logger log = LoggerFactory.getLogger(FichaTecnicaResource.class);

    private static final String ENTITY_NAME = "fichaTecnica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FichaTecnicaService fichaTecnicaService;

    public FichaTecnicaResource(FichaTecnicaService fichaTecnicaService) {
        this.fichaTecnicaService = fichaTecnicaService;
    }

    /**
     * {@code POST  /ficha-tecnicas} : Create a new fichaTecnica.
     *
     * @param fichaTecnica the fichaTecnica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fichaTecnica, or with status {@code 400 (Bad Request)} if the fichaTecnica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ficha-tecnicas")
    public ResponseEntity<FichaTecnica> createFichaTecnica(@RequestBody FichaTecnica fichaTecnica) throws URISyntaxException {
        log.debug("REST request to save FichaTecnica : {}", fichaTecnica);
        if (fichaTecnica.getId() != null) {
            throw new BadRequestAlertException("A new fichaTecnica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaTecnica result = fichaTecnicaService.save(fichaTecnica);
        return ResponseEntity.created(new URI("/api/ficha-tecnicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ficha-tecnicas} : Updates an existing fichaTecnica.
     *
     * @param fichaTecnica the fichaTecnica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fichaTecnica,
     * or with status {@code 400 (Bad Request)} if the fichaTecnica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fichaTecnica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ficha-tecnicas")
    public ResponseEntity<FichaTecnica> updateFichaTecnica(@RequestBody FichaTecnica fichaTecnica) throws URISyntaxException {
        log.debug("REST request to update FichaTecnica : {}", fichaTecnica);
        if (fichaTecnica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichaTecnica result = fichaTecnicaService.save(fichaTecnica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fichaTecnica.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ficha-tecnicas} : get all the fichaTecnicas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fichaTecnicas in body.
     */
    @GetMapping("/ficha-tecnicas")
    public List<FichaTecnica> getAllFichaTecnicas() {
        log.debug("REST request to get all FichaTecnicas");
        return fichaTecnicaService.findAll();
    }

    /**
     * {@code GET  /ficha-tecnicas/:id} : get the "id" fichaTecnica.
     *
     * @param id the id of the fichaTecnica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fichaTecnica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ficha-tecnicas/{id}")
    public ResponseEntity<FichaTecnica> getFichaTecnica(@PathVariable Long id) {
        log.debug("REST request to get FichaTecnica : {}", id);
        Optional<FichaTecnica> fichaTecnica = fichaTecnicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichaTecnica);
    }

    /**
     * {@code DELETE  /ficha-tecnicas/:id} : delete the "id" fichaTecnica.
     *
     * @param id the id of the fichaTecnica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ficha-tecnicas/{id}")
    public ResponseEntity<Void> deleteFichaTecnica(@PathVariable Long id) {
        log.debug("REST request to delete FichaTecnica : {}", id);
        fichaTecnicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
