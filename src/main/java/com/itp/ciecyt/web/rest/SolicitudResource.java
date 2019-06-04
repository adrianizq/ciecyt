package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Solicitud;
import com.itp.ciecyt.service.SolicitudService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Solicitud}.
 */
@RestController
@RequestMapping("/api")
public class SolicitudResource {

    private final Logger log = LoggerFactory.getLogger(SolicitudResource.class);

    private static final String ENTITY_NAME = "solicitud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SolicitudService solicitudService;

    public SolicitudResource(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    /**
     * {@code POST  /solicituds} : Create a new solicitud.
     *
     * @param solicitud the solicitud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitud, or with status {@code 400 (Bad Request)} if the solicitud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicituds")
    public ResponseEntity<Solicitud> createSolicitud(@RequestBody Solicitud solicitud) throws URISyntaxException {
        log.debug("REST request to save Solicitud : {}", solicitud);
        if (solicitud.getId() != null) {
            throw new BadRequestAlertException("A new solicitud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Solicitud result = solicitudService.save(solicitud);
        return ResponseEntity.created(new URI("/api/solicituds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicituds} : Updates an existing solicitud.
     *
     * @param solicitud the solicitud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitud,
     * or with status {@code 400 (Bad Request)} if the solicitud is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicituds")
    public ResponseEntity<Solicitud> updateSolicitud(@RequestBody Solicitud solicitud) throws URISyntaxException {
        log.debug("REST request to update Solicitud : {}", solicitud);
        if (solicitud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Solicitud result = solicitudService.save(solicitud);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, solicitud.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solicituds} : get all the solicituds.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicituds in body.
     */
    @GetMapping("/solicituds")
    public List<Solicitud> getAllSolicituds() {
        log.debug("REST request to get all Solicituds");
        return solicitudService.findAll();
    }

    /**
     * {@code GET  /solicituds/:id} : get the "id" solicitud.
     *
     * @param id the id of the solicitud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicituds/{id}")
    public ResponseEntity<Solicitud> getSolicitud(@PathVariable Long id) {
        log.debug("REST request to get Solicitud : {}", id);
        Optional<Solicitud> solicitud = solicitudService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitud);
    }

    /**
     * {@code DELETE  /solicituds/:id} : delete the "id" solicitud.
     *
     * @param id the id of the solicitud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicituds/{id}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable Long id) {
        log.debug("REST request to delete Solicitud : {}", id);
        solicitudService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
