package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ProductoProyectoService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.ProductoProyectoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ProductoProyecto}.
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
     * @param productoProyectoDTO the productoProyectoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoProyectoDTO, or with status {@code 400 (Bad Request)} if the productoProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-proyectos")
    public ResponseEntity<ProductoProyectoDTO> createProductoProyecto(@RequestBody ProductoProyectoDTO productoProyectoDTO) throws URISyntaxException {
        log.debug("REST request to save ProductoProyecto : {}", productoProyectoDTO);
        if (productoProyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new productoProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoProyectoDTO result = productoProyectoService.save(productoProyectoDTO);
        return ResponseEntity.created(new URI("/api/producto-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-proyectos} : Updates an existing productoProyecto.
     *
     * @param productoProyectoDTO the productoProyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoProyectoDTO,
     * or with status {@code 400 (Bad Request)} if the productoProyectoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoProyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-proyectos")
    public ResponseEntity<ProductoProyectoDTO> updateProductoProyecto(@RequestBody ProductoProyectoDTO productoProyectoDTO) throws URISyntaxException {
        log.debug("REST request to update ProductoProyecto : {}", productoProyectoDTO);
        if (productoProyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductoProyectoDTO result = productoProyectoService.save(productoProyectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoProyectoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /producto-proyectos} : get all the productoProyectos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoProyectos in body.
     */
    @GetMapping("/producto-proyectos")
    public ResponseEntity<List<ProductoProyectoDTO>> getAllProductoProyectos(Pageable pageable) {
        log.debug("REST request to get a page of ProductoProyectos");
        Page<ProductoProyectoDTO> page = productoProyectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-proyectos/:id} : get the "id" productoProyecto.
     *
     * @param id the id of the productoProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-proyectos/{id}")
    public ResponseEntity<ProductoProyectoDTO> getProductoProyecto(@PathVariable Long id) {
        log.debug("REST request to get ProductoProyecto : {}", id);
        Optional<ProductoProyectoDTO> productoProyectoDTO = productoProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoProyectoDTO);
    }

    /**
     * {@code DELETE  /producto-proyectos/:id} : delete the "id" productoProyecto.
     *
     * @param id the id of the productoProyectoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-proyectos/{id}")
    public ResponseEntity<Void> deleteProductoProyecto(@PathVariable Long id) {
        log.debug("REST request to delete ProductoProyecto : {}", id);
        productoProyectoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
