package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.PreguntaRespuesta;
import com.itp.ciecyt.service.PreguntaRespuestaService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.PreguntaRespuesta}.
 */
@RestController
@RequestMapping("/api")
public class PreguntaRespuestaResource {

    private final Logger log = LoggerFactory.getLogger(PreguntaRespuestaResource.class);

    private static final String ENTITY_NAME = "preguntaRespuesta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreguntaRespuestaService preguntaRespuestaService;

    public PreguntaRespuestaResource(PreguntaRespuestaService preguntaRespuestaService) {
        this.preguntaRespuestaService = preguntaRespuestaService;
    }

    /**
     * {@code POST  /pregunta-respuestas} : Create a new preguntaRespuesta.
     *
     * @param preguntaRespuesta the preguntaRespuesta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preguntaRespuesta, or with status {@code 400 (Bad Request)} if the preguntaRespuesta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pregunta-respuestas")
    public ResponseEntity<PreguntaRespuesta> createPreguntaRespuesta(@RequestBody PreguntaRespuesta preguntaRespuesta) throws URISyntaxException {
        log.debug("REST request to save PreguntaRespuesta : {}", preguntaRespuesta);
        if (preguntaRespuesta.getId() != null) {
            throw new BadRequestAlertException("A new preguntaRespuesta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreguntaRespuesta result = preguntaRespuestaService.save(preguntaRespuesta);
        return ResponseEntity.created(new URI("/api/pregunta-respuestas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pregunta-respuestas} : Updates an existing preguntaRespuesta.
     *
     * @param preguntaRespuesta the preguntaRespuesta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preguntaRespuesta,
     * or with status {@code 400 (Bad Request)} if the preguntaRespuesta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preguntaRespuesta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pregunta-respuestas")
    public ResponseEntity<PreguntaRespuesta> updatePreguntaRespuesta(@RequestBody PreguntaRespuesta preguntaRespuesta) throws URISyntaxException {
        log.debug("REST request to update PreguntaRespuesta : {}", preguntaRespuesta);
        if (preguntaRespuesta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PreguntaRespuesta result = preguntaRespuestaService.save(preguntaRespuesta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preguntaRespuesta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pregunta-respuestas} : get all the preguntaRespuestas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preguntaRespuestas in body.
     */
    @GetMapping("/pregunta-respuestas")
    public List<PreguntaRespuesta> getAllPreguntaRespuestas() {
        log.debug("REST request to get all PreguntaRespuestas");
        return preguntaRespuestaService.findAll();
    }

    /**
     * {@code GET  /pregunta-respuestas/:id} : get the "id" preguntaRespuesta.
     *
     * @param id the id of the preguntaRespuesta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preguntaRespuesta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pregunta-respuestas/{id}")
    public ResponseEntity<PreguntaRespuesta> getPreguntaRespuesta(@PathVariable Long id) {
        log.debug("REST request to get PreguntaRespuesta : {}", id);
        Optional<PreguntaRespuesta> preguntaRespuesta = preguntaRespuestaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preguntaRespuesta);
    }

    /**
     * {@code DELETE  /pregunta-respuestas/:id} : delete the "id" preguntaRespuesta.
     *
     * @param id the id of the preguntaRespuesta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pregunta-respuestas/{id}")
    public ResponseEntity<Void> deletePreguntaRespuesta(@PathVariable Long id) {
        log.debug("REST request to delete PreguntaRespuesta : {}", id);
        preguntaRespuestaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
