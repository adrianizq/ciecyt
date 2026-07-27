package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.EntidadFinanciadoraService;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.EntidadFinanciadoraDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.EntidadFinanciadora}.
 */
@RestController
@RequestMapping("/api")
public class EntidadFinanciadoraResource {

    private final Logger log = LoggerFactory.getLogger(EntidadFinanciadoraResource.class);

    private static final String ENTITY_NAME = "entidadFinanciadora";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntidadFinanciadoraService entidadFinanciadoraService;

    public EntidadFinanciadoraResource(EntidadFinanciadoraService entidadFinanciadoraService) {
        this.entidadFinanciadoraService = entidadFinanciadoraService;
    }

    /**
     * {@code POST  /entidad-financiadoras} : Create a new entidadFinanciadora.
     *
     * @param entidadFinanciadoraDTO the entidadFinanciadoraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entidadFinanciadoraDTO, or with status {@code 400 (Bad Request)} if the entidadFinanciadora has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entidad-financiadoras")
    public ResponseEntity<EntidadFinanciadoraDTO> createEntidadFinanciadora(@RequestBody EntidadFinanciadoraDTO entidadFinanciadoraDTO) throws URISyntaxException {
        log.debug("REST request to save EntidadFinanciadora : {}", entidadFinanciadoraDTO);
        if (entidadFinanciadoraDTO.getId() != null) {
            throw new BadRequestAlertException("A new entidadFinanciadora cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntidadFinanciadoraDTO result = entidadFinanciadoraService.save(entidadFinanciadoraDTO);
        return ResponseEntity.created(new URI("/api/entidad-financiadoras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entidad-financiadoras} : Updates an existing entidadFinanciadora.
     *
     * @param entidadFinanciadoraDTO the entidadFinanciadoraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidadFinanciadoraDTO,
     * or with status {@code 400 (Bad Request)} if the entidadFinanciadoraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entidadFinanciadoraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entidad-financiadoras")
    public ResponseEntity<EntidadFinanciadoraDTO> updateEntidadFinanciadora(@RequestBody EntidadFinanciadoraDTO entidadFinanciadoraDTO) throws URISyntaxException {
        log.debug("REST request to update EntidadFinanciadora : {}", entidadFinanciadoraDTO);
        if (entidadFinanciadoraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntidadFinanciadoraDTO result = entidadFinanciadoraService.save(entidadFinanciadoraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidadFinanciadoraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entidad-financiadoras} : get all the entidadFinanciadoras.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entidadFinanciadoras in body.
     */
    @GetMapping("/entidad-financiadoras")
    public ResponseEntity<List<EntidadFinanciadoraDTO>> getAllEntidadFinanciadoras(Pageable pageable) {
        log.debug("REST request to get a page of EntidadFinanciadoras");
        Page<EntidadFinanciadoraDTO> page = entidadFinanciadoraService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entidad-financiadoras/:id} : get the "id" entidadFinanciadora.
     *
     * @param id the id of the entidadFinanciadoraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidadFinanciadoraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entidad-financiadoras/{id}")
    public ResponseEntity<EntidadFinanciadoraDTO> getEntidadFinanciadora(@PathVariable Long id) {
        log.debug("REST request to get EntidadFinanciadora : {}", id);
        Optional<EntidadFinanciadoraDTO> entidadFinanciadoraDTO = entidadFinanciadoraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entidadFinanciadoraDTO);
    }

    /**
     * {@code DELETE  /entidad-financiadoras/:id} : delete the "id" entidadFinanciadora.
     *
     * @param id the id of the entidadFinanciadoraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entidad-financiadoras/{id}")
    public ResponseEntity<Void> deleteEntidadFinanciadora(@PathVariable Long id) {
        log.debug("REST request to delete EntidadFinanciadora : {}", id);
        entidadFinanciadoraService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /////////////777777777777777777777777777777777777777777777

    /**
     * {@code GET  /entidad-financiadora-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the entidadFinanciadoraProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidadFinanciadoraProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entidad-financiadora-proyecto/{idProyecto}")
    public ResponseEntity<?> getEntidadFinanciadoraProyecto(@PathVariable Long idProyecto) {
        log.debug("REST request to get EntidadFinanciadoraProyecto : {}", idProyecto);
        try {
            final List<EntidadFinanciadoraDTO> entidadFinanciadoraDTO = entidadFinanciadoraService.findByEntidadFinanciadoraProyectoId(idProyecto);
            return new ResponseEntity<>(entidadFinanciadoraDTO, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
