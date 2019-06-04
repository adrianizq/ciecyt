package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.LineaDeInvestigacion;
import com.itp.ciecyt.service.LineaDeInvestigacionService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.LineaDeInvestigacion}.
 */
@RestController
@RequestMapping("/api")
public class LineaDeInvestigacionResource {

    private final Logger log = LoggerFactory.getLogger(LineaDeInvestigacionResource.class);

    private static final String ENTITY_NAME = "lineaDeInvestigacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineaDeInvestigacionService lineaDeInvestigacionService;

    public LineaDeInvestigacionResource(LineaDeInvestigacionService lineaDeInvestigacionService) {
        this.lineaDeInvestigacionService = lineaDeInvestigacionService;
    }

    /**
     * {@code POST  /linea-de-investigacions} : Create a new lineaDeInvestigacion.
     *
     * @param lineaDeInvestigacion the lineaDeInvestigacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lineaDeInvestigacion, or with status {@code 400 (Bad Request)} if the lineaDeInvestigacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linea-de-investigacions")
    public ResponseEntity<LineaDeInvestigacion> createLineaDeInvestigacion(@RequestBody LineaDeInvestigacion lineaDeInvestigacion) throws URISyntaxException {
        log.debug("REST request to save LineaDeInvestigacion : {}", lineaDeInvestigacion);
        if (lineaDeInvestigacion.getId() != null) {
            throw new BadRequestAlertException("A new lineaDeInvestigacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LineaDeInvestigacion result = lineaDeInvestigacionService.save(lineaDeInvestigacion);
        return ResponseEntity.created(new URI("/api/linea-de-investigacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linea-de-investigacions} : Updates an existing lineaDeInvestigacion.
     *
     * @param lineaDeInvestigacion the lineaDeInvestigacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineaDeInvestigacion,
     * or with status {@code 400 (Bad Request)} if the lineaDeInvestigacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lineaDeInvestigacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linea-de-investigacions")
    public ResponseEntity<LineaDeInvestigacion> updateLineaDeInvestigacion(@RequestBody LineaDeInvestigacion lineaDeInvestigacion) throws URISyntaxException {
        log.debug("REST request to update LineaDeInvestigacion : {}", lineaDeInvestigacion);
        if (lineaDeInvestigacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LineaDeInvestigacion result = lineaDeInvestigacionService.save(lineaDeInvestigacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lineaDeInvestigacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /linea-de-investigacions} : get all the lineaDeInvestigacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lineaDeInvestigacions in body.
     */
    @GetMapping("/linea-de-investigacions")
    public List<LineaDeInvestigacion> getAllLineaDeInvestigacions() {
        log.debug("REST request to get all LineaDeInvestigacions");
        return lineaDeInvestigacionService.findAll();
    }

    /**
     * {@code GET  /linea-de-investigacions/:id} : get the "id" lineaDeInvestigacion.
     *
     * @param id the id of the lineaDeInvestigacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lineaDeInvestigacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linea-de-investigacions/{id}")
    public ResponseEntity<LineaDeInvestigacion> getLineaDeInvestigacion(@PathVariable Long id) {
        log.debug("REST request to get LineaDeInvestigacion : {}", id);
        Optional<LineaDeInvestigacion> lineaDeInvestigacion = lineaDeInvestigacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lineaDeInvestigacion);
    }

    /**
     * {@code DELETE  /linea-de-investigacions/:id} : delete the "id" lineaDeInvestigacion.
     *
     * @param id the id of the lineaDeInvestigacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linea-de-investigacions/{id}")
    public ResponseEntity<Void> deleteLineaDeInvestigacion(@PathVariable Long id) {
        log.debug("REST request to delete LineaDeInvestigacion : {}", id);
        lineaDeInvestigacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
