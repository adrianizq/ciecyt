package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.ResultadosEsperados;
import com.itp.ciecyt.service.ResultadosEsperadosService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.ResultadosEsperados}.
 */
@RestController
@RequestMapping("/api")
public class ResultadosEsperadosResource {

    private final Logger log = LoggerFactory.getLogger(ResultadosEsperadosResource.class);

    private static final String ENTITY_NAME = "resultadosEsperados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultadosEsperadosService resultadosEsperadosService;

    public ResultadosEsperadosResource(ResultadosEsperadosService resultadosEsperadosService) {
        this.resultadosEsperadosService = resultadosEsperadosService;
    }

    /**
     * {@code POST  /resultados-esperados} : Create a new resultadosEsperados.
     *
     * @param resultadosEsperados the resultadosEsperados to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultadosEsperados, or with status {@code 400 (Bad Request)} if the resultadosEsperados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultados-esperados")
    public ResponseEntity<ResultadosEsperados> createResultadosEsperados(@RequestBody ResultadosEsperados resultadosEsperados) throws URISyntaxException {
        log.debug("REST request to save ResultadosEsperados : {}", resultadosEsperados);
        if (resultadosEsperados.getId() != null) {
            throw new BadRequestAlertException("A new resultadosEsperados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultadosEsperados result = resultadosEsperadosService.save(resultadosEsperados);
        return ResponseEntity.created(new URI("/api/resultados-esperados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultados-esperados} : Updates an existing resultadosEsperados.
     *
     * @param resultadosEsperados the resultadosEsperados to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultadosEsperados,
     * or with status {@code 400 (Bad Request)} if the resultadosEsperados is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultadosEsperados couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultados-esperados")
    public ResponseEntity<ResultadosEsperados> updateResultadosEsperados(@RequestBody ResultadosEsperados resultadosEsperados) throws URISyntaxException {
        log.debug("REST request to update ResultadosEsperados : {}", resultadosEsperados);
        if (resultadosEsperados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultadosEsperados result = resultadosEsperadosService.save(resultadosEsperados);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultadosEsperados.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultados-esperados} : get all the resultadosEsperados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultadosEsperados in body.
     */
    @GetMapping("/resultados-esperados")
    public List<ResultadosEsperados> getAllResultadosEsperados() {
        log.debug("REST request to get all ResultadosEsperados");
        return resultadosEsperadosService.findAll();
    }

    /**
     * {@code GET  /resultados-esperados/:id} : get the "id" resultadosEsperados.
     *
     * @param id the id of the resultadosEsperados to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultadosEsperados, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultados-esperados/{id}")
    public ResponseEntity<ResultadosEsperados> getResultadosEsperados(@PathVariable Long id) {
        log.debug("REST request to get ResultadosEsperados : {}", id);
        Optional<ResultadosEsperados> resultadosEsperados = resultadosEsperadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultadosEsperados);
    }

    /**
     * {@code DELETE  /resultados-esperados/:id} : delete the "id" resultadosEsperados.
     *
     * @param id the id of the resultadosEsperados to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultados-esperados/{id}")
    public ResponseEntity<Void> deleteResultadosEsperados(@PathVariable Long id) {
        log.debug("REST request to delete ResultadosEsperados : {}", id);
        resultadosEsperadosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
