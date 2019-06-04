package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Preguntas;
import com.itp.ciecyt.service.PreguntasService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Preguntas}.
 */
@RestController
@RequestMapping("/api")
public class PreguntasResource {

    private final Logger log = LoggerFactory.getLogger(PreguntasResource.class);

    private static final String ENTITY_NAME = "preguntas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreguntasService preguntasService;

    public PreguntasResource(PreguntasService preguntasService) {
        this.preguntasService = preguntasService;
    }

    /**
     * {@code POST  /preguntas} : Create a new preguntas.
     *
     * @param preguntas the preguntas to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preguntas, or with status {@code 400 (Bad Request)} if the preguntas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/preguntas")
    public ResponseEntity<Preguntas> createPreguntas(@RequestBody Preguntas preguntas) throws URISyntaxException {
        log.debug("REST request to save Preguntas : {}", preguntas);
        if (preguntas.getId() != null) {
            throw new BadRequestAlertException("A new preguntas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Preguntas result = preguntasService.save(preguntas);
        return ResponseEntity.created(new URI("/api/preguntas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /preguntas} : Updates an existing preguntas.
     *
     * @param preguntas the preguntas to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preguntas,
     * or with status {@code 400 (Bad Request)} if the preguntas is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preguntas couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/preguntas")
    public ResponseEntity<Preguntas> updatePreguntas(@RequestBody Preguntas preguntas) throws URISyntaxException {
        log.debug("REST request to update Preguntas : {}", preguntas);
        if (preguntas.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Preguntas result = preguntasService.save(preguntas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preguntas.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /preguntas} : get all the preguntas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preguntas in body.
     */
    @GetMapping("/preguntas")
    public List<Preguntas> getAllPreguntas() {
        log.debug("REST request to get all Preguntas");
        return preguntasService.findAll();
    }

    /**
     * {@code GET  /preguntas/:id} : get the "id" preguntas.
     *
     * @param id the id of the preguntas to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preguntas, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/preguntas/{id}")
    public ResponseEntity<Preguntas> getPreguntas(@PathVariable Long id) {
        log.debug("REST request to get Preguntas : {}", id);
        Optional<Preguntas> preguntas = preguntasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preguntas);
    }

    /**
     * {@code DELETE  /preguntas/:id} : delete the "id" preguntas.
     *
     * @param id the id of the preguntas to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/preguntas/{id}")
    public ResponseEntity<Void> deletePreguntas(@PathVariable Long id) {
        log.debug("REST request to delete Preguntas : {}", id);
        preguntasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
