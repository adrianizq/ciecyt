package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.RolesUsuarioProyecto;
import com.itp.ciecyt.service.RolesUsuarioProyectoService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.RolesUsuarioProyecto}.
 */
@RestController
@RequestMapping("/api")
public class RolesUsuarioProyectoResource {

    private final Logger log = LoggerFactory.getLogger(RolesUsuarioProyectoResource.class);

    private static final String ENTITY_NAME = "rolesUsuarioProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RolesUsuarioProyectoService rolesUsuarioProyectoService;

    public RolesUsuarioProyectoResource(RolesUsuarioProyectoService rolesUsuarioProyectoService) {
        this.rolesUsuarioProyectoService = rolesUsuarioProyectoService;
    }

    /**
     * {@code POST  /roles-usuario-proyectos} : Create a new rolesUsuarioProyecto.
     *
     * @param rolesUsuarioProyecto the rolesUsuarioProyecto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rolesUsuarioProyecto, or with status {@code 400 (Bad Request)} if the rolesUsuarioProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/roles-usuario-proyectos")
    public ResponseEntity<RolesUsuarioProyecto> createRolesUsuarioProyecto(@RequestBody RolesUsuarioProyecto rolesUsuarioProyecto) throws URISyntaxException {
        log.debug("REST request to save RolesUsuarioProyecto : {}", rolesUsuarioProyecto);
        if (rolesUsuarioProyecto.getId() != null) {
            throw new BadRequestAlertException("A new rolesUsuarioProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RolesUsuarioProyecto result = rolesUsuarioProyectoService.save(rolesUsuarioProyecto);
        return ResponseEntity.created(new URI("/api/roles-usuario-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /roles-usuario-proyectos} : Updates an existing rolesUsuarioProyecto.
     *
     * @param rolesUsuarioProyecto the rolesUsuarioProyecto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolesUsuarioProyecto,
     * or with status {@code 400 (Bad Request)} if the rolesUsuarioProyecto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rolesUsuarioProyecto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/roles-usuario-proyectos")
    public ResponseEntity<RolesUsuarioProyecto> updateRolesUsuarioProyecto(@RequestBody RolesUsuarioProyecto rolesUsuarioProyecto) throws URISyntaxException {
        log.debug("REST request to update RolesUsuarioProyecto : {}", rolesUsuarioProyecto);
        if (rolesUsuarioProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RolesUsuarioProyecto result = rolesUsuarioProyectoService.save(rolesUsuarioProyecto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rolesUsuarioProyecto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /roles-usuario-proyectos} : get all the rolesUsuarioProyectos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rolesUsuarioProyectos in body.
     */
    @GetMapping("/roles-usuario-proyectos")
    public List<RolesUsuarioProyecto> getAllRolesUsuarioProyectos() {
        log.debug("REST request to get all RolesUsuarioProyectos");
        return rolesUsuarioProyectoService.findAll();
    }

    /**
     * {@code GET  /roles-usuario-proyectos/:id} : get the "id" rolesUsuarioProyecto.
     *
     * @param id the id of the rolesUsuarioProyecto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rolesUsuarioProyecto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/roles-usuario-proyectos/{id}")
    public ResponseEntity<RolesUsuarioProyecto> getRolesUsuarioProyecto(@PathVariable Long id) {
        log.debug("REST request to get RolesUsuarioProyecto : {}", id);
        Optional<RolesUsuarioProyecto> rolesUsuarioProyecto = rolesUsuarioProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rolesUsuarioProyecto);
    }

    /**
     * {@code DELETE  /roles-usuario-proyectos/:id} : delete the "id" rolesUsuarioProyecto.
     *
     * @param id the id of the rolesUsuarioProyecto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/roles-usuario-proyectos/{id}")
    public ResponseEntity<Void> deleteRolesUsuarioProyecto(@PathVariable Long id) {
        log.debug("REST request to delete RolesUsuarioProyecto : {}", id);
        rolesUsuarioProyectoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
