package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.ModalidadDeGrado;
import com.itp.ciecyt.service.ModalidadDeGradoService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.ModalidadDeGrado}.
 */
@RestController
@RequestMapping("/api")
public class ModalidadDeGradoResource {

    private final Logger log = LoggerFactory.getLogger(ModalidadDeGradoResource.class);

    private static final String ENTITY_NAME = "modalidadDeGrado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModalidadDeGradoService modalidadDeGradoService;

    public ModalidadDeGradoResource(ModalidadDeGradoService modalidadDeGradoService) {
        this.modalidadDeGradoService = modalidadDeGradoService;
    }

    /**
     * {@code POST  /modalidad-de-grados} : Create a new modalidadDeGrado.
     *
     * @param modalidadDeGrado the modalidadDeGrado to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modalidadDeGrado, or with status {@code 400 (Bad Request)} if the modalidadDeGrado has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modalidad-de-grados")
    public ResponseEntity<ModalidadDeGrado> createModalidadDeGrado(@RequestBody ModalidadDeGrado modalidadDeGrado) throws URISyntaxException {
        log.debug("REST request to save ModalidadDeGrado : {}", modalidadDeGrado);
        if (modalidadDeGrado.getId() != null) {
            throw new BadRequestAlertException("A new modalidadDeGrado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModalidadDeGrado result = modalidadDeGradoService.save(modalidadDeGrado);
        return ResponseEntity.created(new URI("/api/modalidad-de-grados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modalidad-de-grados} : Updates an existing modalidadDeGrado.
     *
     * @param modalidadDeGrado the modalidadDeGrado to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modalidadDeGrado,
     * or with status {@code 400 (Bad Request)} if the modalidadDeGrado is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modalidadDeGrado couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modalidad-de-grados")
    public ResponseEntity<ModalidadDeGrado> updateModalidadDeGrado(@RequestBody ModalidadDeGrado modalidadDeGrado) throws URISyntaxException {
        log.debug("REST request to update ModalidadDeGrado : {}", modalidadDeGrado);
        if (modalidadDeGrado.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ModalidadDeGrado result = modalidadDeGradoService.save(modalidadDeGrado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modalidadDeGrado.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /modalidad-de-grados} : get all the modalidadDeGrados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modalidadDeGrados in body.
     */
    @GetMapping("/modalidad-de-grados")
    public List<ModalidadDeGrado> getAllModalidadDeGrados() {
        log.debug("REST request to get all ModalidadDeGrados");
        return modalidadDeGradoService.findAll();
    }

    /**
     * {@code GET  /modalidad-de-grados/:id} : get the "id" modalidadDeGrado.
     *
     * @param id the id of the modalidadDeGrado to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modalidadDeGrado, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modalidad-de-grados/{id}")
    public ResponseEntity<ModalidadDeGrado> getModalidadDeGrado(@PathVariable Long id) {
        log.debug("REST request to get ModalidadDeGrado : {}", id);
        Optional<ModalidadDeGrado> modalidadDeGrado = modalidadDeGradoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modalidadDeGrado);
    }

    /**
     * {@code DELETE  /modalidad-de-grados/:id} : delete the "id" modalidadDeGrado.
     *
     * @param id the id of the modalidadDeGrado to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modalidad-de-grados/{id}")
    public ResponseEntity<Void> deleteModalidadDeGrado(@PathVariable Long id) {
        log.debug("REST request to delete ModalidadDeGrado : {}", id);
        modalidadDeGradoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
