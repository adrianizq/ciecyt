package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.TipoPregunta;
import com.itp.ciecyt.service.TipoPreguntaService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.TipoPregunta}.
 */
@RestController
@RequestMapping("/api")
public class TipoPreguntaResource {

    private final Logger log = LoggerFactory.getLogger(TipoPreguntaResource.class);

    private static final String ENTITY_NAME = "tipoPregunta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoPreguntaService tipoPreguntaService;

    public TipoPreguntaResource(TipoPreguntaService tipoPreguntaService) {
        this.tipoPreguntaService = tipoPreguntaService;
    }

    /**
     * {@code POST  /tipo-preguntas} : Create a new tipoPregunta.
     *
     * @param tipoPregunta the tipoPregunta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoPregunta, or with status {@code 400 (Bad Request)} if the tipoPregunta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-preguntas")
    public ResponseEntity<TipoPregunta> createTipoPregunta(@RequestBody TipoPregunta tipoPregunta) throws URISyntaxException {
        log.debug("REST request to save TipoPregunta : {}", tipoPregunta);
        if (tipoPregunta.getId() != null) {
            throw new BadRequestAlertException("A new tipoPregunta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPregunta result = tipoPreguntaService.save(tipoPregunta);
        return ResponseEntity.created(new URI("/api/tipo-preguntas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-preguntas} : Updates an existing tipoPregunta.
     *
     * @param tipoPregunta the tipoPregunta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPregunta,
     * or with status {@code 400 (Bad Request)} if the tipoPregunta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoPregunta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-preguntas")
    public ResponseEntity<TipoPregunta> updateTipoPregunta(@RequestBody TipoPregunta tipoPregunta) throws URISyntaxException {
        log.debug("REST request to update TipoPregunta : {}", tipoPregunta);
        if (tipoPregunta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoPregunta result = tipoPreguntaService.save(tipoPregunta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPregunta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-preguntas} : get all the tipoPreguntas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoPreguntas in body.
     */
    @GetMapping("/tipo-preguntas")
    public List<TipoPregunta> getAllTipoPreguntas() {
        log.debug("REST request to get all TipoPreguntas");
        return tipoPreguntaService.findAll();
    }

    /**
     * {@code GET  /tipo-preguntas/:id} : get the "id" tipoPregunta.
     *
     * @param id the id of the tipoPregunta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoPregunta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-preguntas/{id}")
    public ResponseEntity<TipoPregunta> getTipoPregunta(@PathVariable Long id) {
        log.debug("REST request to get TipoPregunta : {}", id);
        Optional<TipoPregunta> tipoPregunta = tipoPreguntaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoPregunta);
    }

    /**
     * {@code DELETE  /tipo-preguntas/:id} : delete the "id" tipoPregunta.
     *
     * @param id the id of the tipoPregunta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-preguntas/{id}")
    public ResponseEntity<Void> deleteTipoPregunta(@PathVariable Long id) {
        log.debug("REST request to delete TipoPregunta : {}", id);
        tipoPreguntaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
