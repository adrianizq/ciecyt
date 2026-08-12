package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.service.TransicionEstadoService;
import co.edu.itp.ciecyt.service.dto.TransicionEstadoDTO;
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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.TransicionEstado}.
 */
@RestController
@RequestMapping("/api")
public class TransicionEstadoResource {

    private final Logger log = LoggerFactory.getLogger(TransicionEstadoResource.class);

    private static final String ENTITY_NAME = "transicionEstado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransicionEstadoService transicionEstadoService;

    public TransicionEstadoResource(TransicionEstadoService transicionEstadoService) {
        this.transicionEstadoService = transicionEstadoService;
    }

    /**
     * {@code POST  /transicion-estados} : Create a new transicionEstado.
     */
    @PostMapping("/transicion-estados")
    public ResponseEntity<TransicionEstadoDTO> createTransicionEstado(@RequestBody TransicionEstadoDTO transicionEstadoDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransicionEstado : {}", transicionEstadoDTO);
        if (transicionEstadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new transicionEstado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransicionEstadoDTO result = transicionEstadoService.save(transicionEstadoDTO);
        return ResponseEntity.created(new URI("/api/transicion-estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transicion-estados} : Updates an existing transicionEstado.
     */
    @PutMapping("/transicion-estados")
    public ResponseEntity<TransicionEstadoDTO> updateTransicionEstado(@RequestBody TransicionEstadoDTO transicionEstadoDTO) {
        log.debug("REST request to update TransicionEstado : {}", transicionEstadoDTO);
        if (transicionEstadoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransicionEstadoDTO result = transicionEstadoService.save(transicionEstadoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, transicionEstadoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transicion-estados} : get all the transicionEstados.
     */
    @GetMapping("/transicion-estados")
    public ResponseEntity<List<TransicionEstadoDTO>> getAllTransicionEstados(Pageable pageable) {
        log.debug("REST request to get a page of TransicionEstados");
        Page<TransicionEstadoDTO> page = transicionEstadoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /transicion-estados/modalidad/{modalidadId}} : get transiciones de una modalidad.
     */
    @GetMapping("/transicion-estados/modalidad/{modalidadId}")
    public ResponseEntity<List<TransicionEstadoDTO>> getTransicionesByModalidad(@PathVariable Long modalidadId) {
        log.debug("REST request to get TransicionEstados de modalidad : {}", modalidadId);
        List<TransicionEstadoDTO> list = transicionEstadoService.findByModalidadId(modalidadId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /transicion-estados/modalidad/{modalidadId}/origen/{estadoOrigen}} :
     * get transiciones de una modalidad desde un estado origen.
     */
    @GetMapping("/transicion-estados/modalidad/{modalidadId}/origen/{estadoOrigen}")
    public ResponseEntity<List<TransicionEstadoDTO>> getTransicionesByModalidadAndOrigen(
        @PathVariable Long modalidadId,
        @PathVariable EnumEstadoProyecto estadoOrigen
    ) {
        log.debug("REST request to get TransicionEstados de modalidad {} desde {}", modalidadId, estadoOrigen);
        List<TransicionEstadoDTO> list = transicionEstadoService.findByModalidadIdAndEstadoOrigen(modalidadId, estadoOrigen);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /transicion-estados/:id} : get the "id" transicionEstado.
     */
    @GetMapping("/transicion-estados/{id}")
    public ResponseEntity<TransicionEstadoDTO> getTransicionEstado(@PathVariable Long id) {
        log.debug("REST request to get TransicionEstado : {}", id);
        Optional<TransicionEstadoDTO> transicionEstadoDTO = transicionEstadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transicionEstadoDTO);
    }

    /**
     * {@code DELETE  /transicion-estados/:id} : delete the "id" transicionEstado.
     */
    @DeleteMapping("/transicion-estados/{id}")
    public ResponseEntity<Void> deleteTransicionEstado(@PathVariable Long id) {
        log.debug("REST request to delete TransicionEstado : {}", id);
        transicionEstadoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
