package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.RequisitoService;
import co.edu.itp.ciecyt.service.dto.RequisitoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Requisito}.
 */
@RestController
@RequestMapping("/api")
public class RequisitoResource {

    private final Logger log = LoggerFactory.getLogger(RequisitoResource.class);

    private static final String ENTITY_NAME = "requisito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequisitoService requisitoService;

    public RequisitoResource(RequisitoService requisitoService) {
        this.requisitoService = requisitoService;
    }

    /**
     * {@code POST  /requisitos} : Create a new requisito.
     */
    @PostMapping("/requisitos")
    public ResponseEntity<RequisitoDTO> createRequisito(@RequestBody RequisitoDTO requisitoDTO) throws URISyntaxException {
        log.debug("REST request to save Requisito : {}", requisitoDTO);
        if (requisitoDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitoDTO result = requisitoService.save(requisitoDTO);
        return ResponseEntity.created(new URI("/api/requisitos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requisitos} : Updates an existing requisito.
     */
    @PutMapping("/requisitos")
    public ResponseEntity<RequisitoDTO> updateRequisito(@RequestBody RequisitoDTO requisitoDTO) {
        log.debug("REST request to update Requisito : {}", requisitoDTO);
        if (requisitoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitoDTO result = requisitoService.save(requisitoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requisitoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requisitos} : get all the requisitos.
     */
    @GetMapping("/requisitos")
    public ResponseEntity<List<RequisitoDTO>> getAllRequisitos(Pageable pageable) {
        log.debug("REST request to get a page of Requisitos");
        Page<RequisitoDTO> page = requisitoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requisitos/activos} : get all the active requisitos.
     */
    @GetMapping("/requisitos/activos")
    public ResponseEntity<List<RequisitoDTO>> getAllRequisitosActivos() {
        log.debug("REST request to get all active Requisitos");
        List<RequisitoDTO> list = requisitoService.findAllActivos();
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /requisitos/modalidad/{modalidadId}} : get requisitos de una modalidad.
     */
    @GetMapping("/requisitos/modalidad/{modalidadId}")
    public ResponseEntity<List<RequisitoDTO>> getRequisitosByModalidad(@PathVariable Long modalidadId) {
        log.debug("REST request to get Requisitos de modalidad : {}", modalidadId);
        List<RequisitoDTO> list = requisitoService.findByModalidad(modalidadId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /requisitos/:id} : get the "id" requisito.
     */
    @GetMapping("/requisitos/{id}")
    public ResponseEntity<RequisitoDTO> getRequisito(@PathVariable Long id) {
        log.debug("REST request to get Requisito : {}", id);
        Optional<RequisitoDTO> requisitoDTO = requisitoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisitoDTO);
    }

    /**
     * {@code DELETE  /requisitos/:id} : delete the "id" requisito.
     */
    @DeleteMapping("/requisitos/{id}")
    public ResponseEntity<Void> deleteRequisito(@PathVariable Long id) {
        log.debug("REST request to delete Requisito : {}", id);
        requisitoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
