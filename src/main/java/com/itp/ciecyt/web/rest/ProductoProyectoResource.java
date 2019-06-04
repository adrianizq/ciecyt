package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.ProductoProyecto;
import com.itp.ciecyt.service.ProductoProyectoService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.ProductoProyecto}.
 */
@RestController
@RequestMapping("/api")
public class ProductoProyectoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoProyectoResource.class);

    private static final String ENTITY_NAME = "productoProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoProyectoService productoProyectoService;

    public ProductoProyectoResource(ProductoProyectoService productoProyectoService) {
        this.productoProyectoService = productoProyectoService;
    }

    /**
     * {@code POST  /producto-proyectos} : Create a new productoProyecto.
     *
     * @param productoProyecto the productoProyecto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoProyecto, or with status {@code 400 (Bad Request)} if the productoProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-proyectos")
    public ResponseEntity<ProductoProyecto> createProductoProyecto(@RequestBody ProductoProyecto productoProyecto) throws URISyntaxException {
        log.debug("REST request to save ProductoProyecto : {}", productoProyecto);
        if (productoProyecto.getId() != null) {
            throw new BadRequestAlertException("A new productoProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoProyecto result = productoProyectoService.save(productoProyecto);
        return ResponseEntity.created(new URI("/api/producto-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-proyectos} : Updates an existing productoProyecto.
     *
     * @param productoProyecto the productoProyecto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoProyecto,
     * or with status {@code 400 (Bad Request)} if the productoProyecto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoProyecto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-proyectos")
    public ResponseEntity<ProductoProyecto> updateProductoProyecto(@RequestBody ProductoProyecto productoProyecto) throws URISyntaxException {
        log.debug("REST request to update ProductoProyecto : {}", productoProyecto);
        if (productoProyecto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoProyecto result = productoProyectoService.save(productoProyecto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoProyecto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /producto-proyectos} : get all the productoProyectos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoProyectos in body.
     */
    @GetMapping("/producto-proyectos")
    public List<ProductoProyecto> getAllProductoProyectos() {
        log.debug("REST request to get all ProductoProyectos");
        return productoProyectoService.findAll();
    }

    /**
     * {@code GET  /producto-proyectos/:id} : get the "id" productoProyecto.
     *
     * @param id the id of the productoProyecto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoProyecto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-proyectos/{id}")
    public ResponseEntity<ProductoProyecto> getProductoProyecto(@PathVariable Long id) {
        log.debug("REST request to get ProductoProyecto : {}", id);
        Optional<ProductoProyecto> productoProyecto = productoProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoProyecto);
    }

    /**
     * {@code DELETE  /producto-proyectos/:id} : delete the "id" productoProyecto.
     *
     * @param id the id of the productoProyecto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-proyectos/{id}")
    public ResponseEntity<Void> deleteProductoProyecto(@PathVariable Long id) {
        log.debug("REST request to delete ProductoProyecto : {}", id);
        productoProyectoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
