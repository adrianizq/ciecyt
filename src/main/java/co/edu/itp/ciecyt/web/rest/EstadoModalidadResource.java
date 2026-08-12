package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.EstadoModalidadService;
import co.edu.itp.ciecyt.service.dto.EstadoModalidadDTO;
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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.EstadoModalidad}.
 */
@RestController
@RequestMapping("/api")
public class EstadoModalidadResource {

    private final Logger log = LoggerFactory.getLogger(EstadoModalidadResource.class);

    private static final String ENTITY_NAME = "estadoModalidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoModalidadService estadoModalidadService;

    public EstadoModalidadResource(EstadoModalidadService estadoModalidadService) {
        this.estadoModalidadService = estadoModalidadService;
    }

    /**
     * {@code POST  /estado-modalidads} : Create a new estadoModalidad.
     */
    @PostMapping("/estado-modalidads")
    public ResponseEntity<EstadoModalidadDTO> createEstadoModalidad(@RequestBody EstadoModalidadDTO estadoModalidadDTO)
        throws URISyntaxException {
        log.debug("REST request to save EstadoModalidad : {}", estadoModalidadDTO);
        if (estadoModalidadDTO.getId() != null) {
            throw new BadRequestAlertException("A new estadoModalidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoModalidadDTO result = estadoModalidadService.save(estadoModalidadDTO);
        return ResponseEntity.created(new URI("/api/estado-modalidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-modalidads} : Updates an existing estadoModalidad.
     */
    @PutMapping("/estado-modalidads")
    public ResponseEntity<EstadoModalidadDTO> updateEstadoModalidad(@RequestBody EstadoModalidadDTO estadoModalidadDTO) {
        log.debug("REST request to update EstadoModalidad : {}", estadoModalidadDTO);
        if (estadoModalidadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EstadoModalidadDTO result = estadoModalidadService.save(estadoModalidadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoModalidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /estado-modalidads} : get all the estadoModalidads.
     */
    @GetMapping("/estado-modalidads")
    public ResponseEntity<List<EstadoModalidadDTO>> getAllEstadoModalidads(Pageable pageable) {
        log.debug("REST request to get a page of EstadoModalidads");
        Page<EstadoModalidadDTO> page = estadoModalidadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estado-modalidads/modalidad/{modalidadId}} : get estados del flujo de una modalidad.
     */
    @GetMapping("/estado-modalidads/modalidad/{modalidadId}")
    public ResponseEntity<List<EstadoModalidadDTO>> getEstadosByModalidad(@PathVariable Long modalidadId) {
        log.debug("REST request to get EstadoModalidads de modalidad : {}", modalidadId);
        List<EstadoModalidadDTO> list = estadoModalidadService.findByModalidadId(modalidadId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /estado-modalidads/:id} : get the "id" estadoModalidad.
     */
    @GetMapping("/estado-modalidads/{id}")
    public ResponseEntity<EstadoModalidadDTO> getEstadoModalidad(@PathVariable Long id) {
        log.debug("REST request to get EstadoModalidad : {}", id);
        Optional<EstadoModalidadDTO> estadoModalidadDTO = estadoModalidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadoModalidadDTO);
    }

    /**
     * {@code DELETE  /estado-modalidads/:id} : delete the "id" estadoModalidad.
     */
    @DeleteMapping("/estado-modalidads/{id}")
    public ResponseEntity<Void> deleteEstadoModalidad(@PathVariable Long id) {
        log.debug("REST request to delete EstadoModalidad : {}", id);
        estadoModalidadService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
