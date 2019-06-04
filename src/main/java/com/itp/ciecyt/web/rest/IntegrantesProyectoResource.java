package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.IntegrantesProyecto;
import com.itp.ciecyt.service.IntegrantesProyectoService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.IntegrantesProyecto}.
 */
@RestController
@RequestMapping("/api")
public class IntegrantesProyectoResource {

    private final Logger log = LoggerFactory.getLogger(IntegrantesProyectoResource.class);

    private static final String ENTITY_NAME = "integrantesProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegrantesProyectoService integrantesProyectoService;

    public IntegrantesProyectoResource(IntegrantesProyectoService integrantesProyectoService) {
        this.integrantesProyectoService = integrantesProyectoService;
    }

    /**
     * {@code POST  /integrantes-proyectos} : Create a new integrantesProyecto.
     *
     * @param integrantesProyecto the integrantesProyecto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integrantesProyecto, or with status {@code 400 (Bad Request)} if the integrantesProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/integrantes-proyectos")
    public ResponseEntity<IntegrantesProyecto> createIntegrantesProyecto(@RequestBody IntegrantesProyecto integrantesProyecto) throws URISyntaxException {
        log.debug("REST request to save IntegrantesProyecto : {}", integrantesProyecto);
        if (integrantesProyecto.getId() != null) {
            throw new BadRequestAlertException("A new integrantesProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntegrantesProyecto result = integrantesProyectoService.save(integrantesProyecto);
        return ResponseEntity.created(new URI("/api/integrantes-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /integrantes-proyectos} : Updates an existing integrantesProyecto.
     *
     * @param integrantesProyecto the integrantesProyecto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integrantesProyecto,
     * or with status {@code 400 (Bad Request)} if the integrantesProyecto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integrantesProyecto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/integrantes-proyectos")
    public ResponseEntity<IntegrantesProyecto> updateIntegrantesProyecto(@RequestBody IntegrantesProyecto integrantesProyecto) throws URISyntaxException {
        log.debug("REST request to update IntegrantesProyecto : {}", integrantesProyecto);
        if (integrantesProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IntegrantesProyecto result = integrantesProyectoService.save(integrantesProyecto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integrantesProyecto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /integrantes-proyectos} : get all the integrantesProyectos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integrantesProyectos in body.
     */
    @GetMapping("/integrantes-proyectos")
    public List<IntegrantesProyecto> getAllIntegrantesProyectos() {
        log.debug("REST request to get all IntegrantesProyectos");
        return integrantesProyectoService.findAll();
    }

    /**
     * {@code GET  /integrantes-proyectos/:id} : get the "id" integrantesProyecto.
     *
     * @param id the id of the integrantesProyecto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integrantesProyecto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integrantes-proyectos/{id}")
    public ResponseEntity<IntegrantesProyecto> getIntegrantesProyecto(@PathVariable Long id) {
        log.debug("REST request to get IntegrantesProyecto : {}", id);
        Optional<IntegrantesProyecto> integrantesProyecto = integrantesProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integrantesProyecto);
    }

    /**
     * {@code DELETE  /integrantes-proyectos/:id} : delete the "id" integrantesProyecto.
     *
     * @param id the id of the integrantesProyecto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/integrantes-proyectos/{id}")
    public ResponseEntity<Void> deleteIntegrantesProyecto(@PathVariable Long id) {
        log.debug("REST request to delete IntegrantesProyecto : {}", id);
        integrantesProyectoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
