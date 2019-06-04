package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Rubros;
import com.itp.ciecyt.service.RubrosService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Rubros}.
 */
@RestController
@RequestMapping("/api")
public class RubrosResource {

    private final Logger log = LoggerFactory.getLogger(RubrosResource.class);

    private static final String ENTITY_NAME = "rubros";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RubrosService rubrosService;

    public RubrosResource(RubrosService rubrosService) {
        this.rubrosService = rubrosService;
    }

    /**
     * {@code POST  /rubros} : Create a new rubros.
     *
     * @param rubros the rubros to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rubros, or with status {@code 400 (Bad Request)} if the rubros has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rubros")
    public ResponseEntity<Rubros> createRubros(@RequestBody Rubros rubros) throws URISyntaxException {
        log.debug("REST request to save Rubros : {}", rubros);
        if (rubros.getId() != null) {
            throw new BadRequestAlertException("A new rubros cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rubros result = rubrosService.save(rubros);
        return ResponseEntity.created(new URI("/api/rubros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rubros} : Updates an existing rubros.
     *
     * @param rubros the rubros to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rubros,
     * or with status {@code 400 (Bad Request)} if the rubros is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rubros couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rubros")
    public ResponseEntity<Rubros> updateRubros(@RequestBody Rubros rubros) throws URISyntaxException {
        log.debug("REST request to update Rubros : {}", rubros);
        if (rubros.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rubros result = rubrosService.save(rubros);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rubros.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rubros} : get all the rubros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rubros in body.
     */
    @GetMapping("/rubros")
    public List<Rubros> getAllRubros() {
        log.debug("REST request to get all Rubros");
        return rubrosService.findAll();
    }

    /**
     * {@code GET  /rubros/:id} : get the "id" rubros.
     *
     * @param id the id of the rubros to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rubros, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rubros/{id}")
    public ResponseEntity<Rubros> getRubros(@PathVariable Long id) {
        log.debug("REST request to get Rubros : {}", id);
        Optional<Rubros> rubros = rubrosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rubros);
    }

    /**
     * {@code DELETE  /rubros/:id} : delete the "id" rubros.
     *
     * @param id the id of the rubros to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rubros/{id}")
    public ResponseEntity<Void> deleteRubros(@PathVariable Long id) {
        log.debug("REST request to delete Rubros : {}", id);
        rubrosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
