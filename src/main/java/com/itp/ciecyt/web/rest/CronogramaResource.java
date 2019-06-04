package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Cronograma;
import com.itp.ciecyt.service.CronogramaService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Cronograma}.
 */
@RestController
@RequestMapping("/api")
public class CronogramaResource {

    private final Logger log = LoggerFactory.getLogger(CronogramaResource.class);

    private static final String ENTITY_NAME = "cronograma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CronogramaService cronogramaService;

    public CronogramaResource(CronogramaService cronogramaService) {
        this.cronogramaService = cronogramaService;
    }

    /**
     * {@code POST  /cronogramas} : Create a new cronograma.
     *
     * @param cronograma the cronograma to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cronograma, or with status {@code 400 (Bad Request)} if the cronograma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cronogramas")
    public ResponseEntity<Cronograma> createCronograma(@RequestBody Cronograma cronograma) throws URISyntaxException {
        log.debug("REST request to save Cronograma : {}", cronograma);
        if (cronograma.getId() != null) {
            throw new BadRequestAlertException("A new cronograma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cronograma result = cronogramaService.save(cronograma);
        return ResponseEntity.created(new URI("/api/cronogramas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cronogramas} : Updates an existing cronograma.
     *
     * @param cronograma the cronograma to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cronograma,
     * or with status {@code 400 (Bad Request)} if the cronograma is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cronograma couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cronogramas")
    public ResponseEntity<Cronograma> updateCronograma(@RequestBody Cronograma cronograma) throws URISyntaxException {
        log.debug("REST request to update Cronograma : {}", cronograma);
        if (cronograma.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cronograma result = cronogramaService.save(cronograma);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cronograma.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cronogramas} : get all the cronogramas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cronogramas in body.
     */
    @GetMapping("/cronogramas")
    public List<Cronograma> getAllCronogramas() {
        log.debug("REST request to get all Cronogramas");
        return cronogramaService.findAll();
    }

    /**
     * {@code GET  /cronogramas/:id} : get the "id" cronograma.
     *
     * @param id the id of the cronograma to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cronograma, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cronogramas/{id}")
    public ResponseEntity<Cronograma> getCronograma(@PathVariable Long id) {
        log.debug("REST request to get Cronograma : {}", id);
        Optional<Cronograma> cronograma = cronogramaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cronograma);
    }

    /**
     * {@code DELETE  /cronogramas/:id} : delete the "id" cronograma.
     *
     * @param id the id of the cronograma to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cronogramas/{id}")
    public ResponseEntity<Void> deleteCronograma(@PathVariable Long id) {
        log.debug("REST request to delete Cronograma : {}", id);
        cronogramaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
