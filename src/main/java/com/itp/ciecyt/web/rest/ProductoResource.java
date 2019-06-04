package com.itp.ciecyt.web.rest;

import com.itp.ciecyt.domain.Producto;
import com.itp.ciecyt.service.ProductoService;
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
 * REST controller for managing {@link com.itp.ciecyt.domain.Producto}.
 */
@RestController
@RequestMapping("/api")
public class ProductoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoResource.class);

    private static final String ENTITY_NAME = "producto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoService productoService;

    public ProductoResource(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * {@code POST  /productos} : Create a new producto.
     *
     * @param producto the producto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new producto, or with status {@code 400 (Bad Request)} if the producto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/productos")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to save Producto : {}", producto);
        if (producto.getId() != null) {
            throw new BadRequestAlertException("A new producto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Producto result = productoService.save(producto);
        return ResponseEntity.created(new URI("/api/productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /productos} : Updates an existing producto.
     *
     * @param producto the producto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated producto,
     * or with status {@code 400 (Bad Request)} if the producto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the producto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/productos")
    public ResponseEntity<Producto> updateProducto(@RequestBody Producto producto) throws URISyntaxException {
        log.debug("REST request to update Producto : {}", producto);
        if (producto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Producto result = productoService.save(producto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, producto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /productos} : get all the productos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productos in body.
     */
    @GetMapping("/productos")
    public List<Producto> getAllProductos() {
        log.debug("REST request to get all Productos");
        return productoService.findAll();
    }

    /**
     * {@code GET  /productos/:id} : get the "id" producto.
     *
     * @param id the id of the producto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the producto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        log.debug("REST request to get Producto : {}", id);
        Optional<Producto> producto = productoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(producto);
    }

    /**
     * {@code DELETE  /productos/:id} : delete the "id" producto.
     *
     * @param id the id of the producto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        log.debug("REST request to delete Producto : {}", id);
        productoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
