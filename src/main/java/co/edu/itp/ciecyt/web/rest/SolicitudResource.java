package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.SolicitudService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.SolicitudDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Solicitud}.
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
     * @param solicitudDTO the solicitudDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new solicitudDTO, or with status {@code 400 (Bad Request)} if the solicitud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solicituds")
    public ResponseEntity<SolicitudDTO> createSolicitud(@RequestBody SolicitudDTO solicitudDTO) throws URISyntaxException {
        log.debug("REST request to save Solicitud : {}", solicitudDTO);
        if (solicitudDTO.getId() != null) {
            throw new BadRequestAlertException("A new solicitud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SolicitudDTO result = solicitudService.save(solicitudDTO);
        return ResponseEntity.created(new URI("/api/solicituds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solicituds} : Updates an existing solicitud.
     *
     * @param solicitudDTO the solicitudDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated solicitudDTO,
     * or with status {@code 400 (Bad Request)} if the solicitudDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the solicitudDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solicituds")
    public ResponseEntity<SolicitudDTO> updateSolicitud(@RequestBody SolicitudDTO solicitudDTO) throws URISyntaxException {
        log.debug("REST request to update Solicitud : {}", solicitudDTO);
        if (solicitudDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SolicitudDTO result = solicitudService.save(solicitudDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, solicitudDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /solicituds} : get all the solicituds.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of solicituds in body.
     */
    @GetMapping("/solicituds")
    public ResponseEntity<List<SolicitudDTO>> getAllSolicituds(Pageable pageable) {
        log.debug("REST request to get a page of Solicituds");
        Page<SolicitudDTO> page = solicitudService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solicituds/:id} : get the "id" solicitud.
     *
     * @param id the id of the solicitudDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the solicitudDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solicituds/{id}")
    public ResponseEntity<SolicitudDTO> getSolicitud(@PathVariable Long id) {
        log.debug("REST request to get Solicitud : {}", id);
        Optional<SolicitudDTO> solicitudDTO = solicitudService.findOne(id);
        return ResponseUtil.wrapOrNotFound(solicitudDTO);
    }

    /**
     * {@code DELETE  /solicituds/:id} : delete the "id" solicitud.
     *
     * @param id the id of the solicitudDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solicituds/{id}")
    public ResponseEntity<Void> deleteSolicitud(@PathVariable Long id) {
        log.debug("REST request to delete Solicitud : {}", id);
        solicitudService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
