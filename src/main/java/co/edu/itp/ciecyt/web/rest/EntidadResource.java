package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.EntidadService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.EntidadDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Entidad}.
 */
@RestController
@RequestMapping("/api")
public class EntidadResource {

    private final Logger log = LoggerFactory.getLogger(EntidadResource.class);

    private static final String ENTITY_NAME = "entidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntidadService entidadService;

    public EntidadResource(EntidadService entidadService) {
        this.entidadService = entidadService;
    }

    /**
     * {@code POST  /entidads} : Create a new entidad.
     *
     * @param entidadDTO the entidadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entidadDTO, or with status {@code 400 (Bad Request)} if the entidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entidads")
    public ResponseEntity<EntidadDTO> createEntidad(@RequestBody EntidadDTO entidadDTO) throws URISyntaxException {
        log.debug("REST request to save Entidad : {}", entidadDTO);
        if (entidadDTO.getId() != null) {
            throw new BadRequestAlertException("A new entidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntidadDTO result = entidadService.save(entidadDTO);
        return ResponseEntity.created(new URI("/api/entidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entidads} : Updates an existing entidad.
     *
     * @param entidadDTO the entidadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidadDTO,
     * or with status {@code 400 (Bad Request)} if the entidadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entidadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entidads")
    public ResponseEntity<EntidadDTO> updateEntidad(@RequestBody EntidadDTO entidadDTO) throws URISyntaxException {
        log.debug("REST request to update Entidad : {}", entidadDTO);
        if (entidadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntidadDTO result = entidadService.save(entidadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entidads} : get all the entidads.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entidads in body.
     */
    @GetMapping("/entidads")
    public ResponseEntity<List<EntidadDTO>> getAllEntidads(Pageable pageable) {
        log.debug("REST request to get a page of Entidads");
        Page<EntidadDTO> page = entidadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entidads/:id} : get the "id" entidad.
     *
     * @param id the id of the entidadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entidads/{id}")
    public ResponseEntity<EntidadDTO> getEntidad(@PathVariable Long id) {
        log.debug("REST request to get Entidad : {}", id);
        Optional<EntidadDTO> entidadDTO = entidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entidadDTO);
    }

    /**
     * {@code DELETE  /entidads/:id} : delete the "id" entidad.
     *
     * @param id the id of the entidadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entidads/{id}")
    public ResponseEntity<Void> deleteEntidad(@PathVariable Long id) {
        log.debug("REST request to delete Entidad : {}", id);
        entidadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
