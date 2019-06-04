package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Empresas;
import com.itp.ciecyt.service.EmpresasService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Empresas}.
 */
@RestController
@RequestMapping("/api")
public class EmpresasResource {

    private final Logger log = LoggerFactory.getLogger(EmpresasResource.class);

    private static final String ENTITY_NAME = "empresas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmpresasService empresasService;

    public EmpresasResource(EmpresasService empresasService) {
        this.empresasService = empresasService;
    }

    /**
     * {@code POST  /empresas} : Create a new empresas.
     *
     * @param empresas the empresas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresas, or with status {@code 400 (Bad Request)} if the empresas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empresas")
    public ResponseEntity<Empresas> createEmpresas(@RequestBody Empresas empresas) throws URISyntaxException {
        log.debug("REST request to save Empresas : {}", empresas);
        if (empresas.getId() != null) {
            throw new BadRequestAlertException("A new empresas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Empresas result = empresasService.save(empresas);
        return ResponseEntity.created(new URI("/api/empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empresas} : Updates an existing empresas.
     *
     * @param empresas the empresas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresas,
     * or with status {@code 400 (Bad Request)} if the empresas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empresas")
    public ResponseEntity<Empresas> updateEmpresas(@RequestBody Empresas empresas) throws URISyntaxException {
        log.debug("REST request to update Empresas : {}", empresas);
        if (empresas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Empresas result = empresasService.save(empresas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /empresas} : get all the empresas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresas in body.
     */
    @GetMapping("/empresas")
    public List<Empresas> getAllEmpresas() {
        log.debug("REST request to get all Empresas");
        return empresasService.findAll();
    }

    /**
     * {@code GET  /empresas/:id} : get the "id" empresas.
     *
     * @param id the id of the empresas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empresas/{id}")
    public ResponseEntity<Empresas> getEmpresas(@PathVariable Long id) {
        log.debug("REST request to get Empresas : {}", id);
        Optional<Empresas> empresas = empresasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresas);
    }

    /**
     * {@code DELETE  /empresas/:id} : delete the "id" empresas.
     *
     * @param id the id of the empresas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empresas/{id}")
    public ResponseEntity<Void> deleteEmpresas(@PathVariable Long id) {
        log.debug("REST request to delete Empresas : {}", id);
        empresasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
