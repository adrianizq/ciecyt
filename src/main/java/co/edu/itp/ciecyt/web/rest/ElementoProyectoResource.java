package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ElementoProyectoService;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ElementoProyecto}.
 */
@RestController
@RequestMapping("/api")
public class ElementoProyectoResource {

    private final Logger log = LoggerFactory.getLogger(ElementoProyectoResource.class);

    private static final String ENTITY_NAME = "elementoProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementoProyectoService elementoProyectoService;

    public ElementoProyectoResource(ElementoProyectoService elementoProyectoService) {
        this.elementoProyectoService = elementoProyectoService;
    }

    /**
     * {@code POST  /elemento-proyectos} : Create a new elementoProyecto.
     *
     * @param elementoProyectoDTO the elementoProyectoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementoProyectoDTO, or with status {@code 400 (Bad Request)} if the elementoProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/elemento-proyectos")
    public ResponseEntity<ElementoProyectoDTO> createElementoProyecto(@RequestBody ElementoProyectoDTO elementoProyectoDTO) throws URISyntaxException {
        log.debug("REST request to save ElementoProyecto : {}", elementoProyectoDTO);
        if (elementoProyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new elementoProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElementoProyectoDTO result = elementoProyectoService.save(elementoProyectoDTO);
        return ResponseEntity.created(new URI("/api/elemento-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /elemento-proyectos} : Updates an existing elementoProyecto.
     *
     * @param elementoProyectoDTO the elementoProyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementoProyectoDTO,
     * or with status {@code 400 (Bad Request)} if the elementoProyectoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementoProyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/elemento-proyectos")
    public ResponseEntity<ElementoProyectoDTO> updateElementoProyecto(@RequestBody ElementoProyectoDTO elementoProyectoDTO) throws URISyntaxException {
        log.debug("REST request to update ElementoProyecto : {}", elementoProyectoDTO);
        if (elementoProyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ElementoProyectoDTO result = elementoProyectoService.save(elementoProyectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementoProyectoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /elemento-proyectos} : get all the elementoProyectos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementoProyectos in body.
     */
    @GetMapping("/elemento-proyectos")
    public ResponseEntity<List<ElementoProyectoDTO>> getAllElementoProyectos(Pageable pageable) {
        log.debug("REST request to get a page of ElementoProyectos");
        Page<ElementoProyectoDTO> page = elementoProyectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /elemento-proyectos/:id} : get the "id" elementoProyecto.
     *
     * @param id the id of the elementoProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementoProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elemento-proyectos/{id}")
    public ResponseEntity<ElementoProyectoDTO> getElementoProyecto(@PathVariable Long id) {
        log.debug("REST request to get ElementoProyecto : {}", id);
        Optional<ElementoProyectoDTO> elementoProyectoDTO = elementoProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elementoProyectoDTO);
    }

    /**
     * {@code DELETE  /elemento-proyectos/:id} : delete the "id" elementoProyecto.
     *
     * @param id the id of the elementoProyectoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/elemento-proyectos/{id}")
    public ResponseEntity<Void> deleteElementoProyecto(@PathVariable Long id) {
        log.debug("REST request to delete ElementoProyecto : {}", id);
        elementoProyectoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /////////////777777777777777777777777777777777777777777777

    /**
     * {@code GET  /elemento-proyecto-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the elementoProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementoProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    /*@GetMapping("/elemento-proyecto-proyecto/{idProyecto}")
    public ResponseEntity<?> getElementoProyectoProyecto(@PathVariable Long idProyecto) {
        log.debug("REST request to get ElementoProyectoProyecto : {}", idProyecto);
        try {
            final List<ElementoProyectoDTO> elementoProyectoDTO = elementoProyectoService.findByElementoProyectoProyectoId(idProyecto);
            return new ResponseEntity<>(elementoProyectoDTO, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }*/

    /////////////777777777777777777777777777777777777777777777

    /**
     * {@code GET  /elemento-proyecto-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the elementoProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementoProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elemento-proyecto-proyecto/{idProyecto}/{idFase}")
    public ResponseEntity<?> getElementoProyectoProyecto(@PathVariable Long idProyecto, @PathVariable Long idFase) {
        log.debug("REST request to get ElementoProyectoProyecto : {}", idProyecto);
        try {
            final List<ElementoProyectoDTO> elementoProyectoDTO = elementoProyectoService.findByElementoProyectoProyectoId(idProyecto, idFase);
            return new ResponseEntity<>(elementoProyectoDTO, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
