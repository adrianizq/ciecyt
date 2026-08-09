package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ProyectoHistorialEstadoService;
import co.edu.itp.ciecyt.service.dto.ProyectoHistorialEstadoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ProyectoHistorialEstado}.
 */
@RestController
@RequestMapping("/api")
public class ProyectoHistorialEstadoResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoHistorialEstadoResource.class);

    private static final String ENTITY_NAME = "proyectoHistorialEstado";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyectoHistorialEstadoService proyectoHistorialEstadoService;

    public ProyectoHistorialEstadoResource(ProyectoHistorialEstadoService proyectoHistorialEstadoService) {
        this.proyectoHistorialEstadoService = proyectoHistorialEstadoService;
    }

    /**
     * {@code POST  /proyecto-historial-estados} : Create a new proyectoHistorialEstado.
     *
     * @param proyectoHistorialEstadoDTO the proyectoHistorialEstadoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proyectoHistorialEstadoDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proyecto-historial-estados")
    public ResponseEntity<ProyectoHistorialEstadoDTO> createProyectoHistorialEstado(
        @Valid @RequestBody ProyectoHistorialEstadoDTO proyectoHistorialEstadoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProyectoHistorialEstado : {}", proyectoHistorialEstadoDTO);
        if (proyectoHistorialEstadoDTO.getId() != null) {
            throw new BadRequestAlertException("A new proyectoHistorialEstado cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProyectoHistorialEstadoDTO result = proyectoHistorialEstadoService.save(proyectoHistorialEstadoDTO);
        return ResponseEntity.created(new URI("/api/proyecto-historial-estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proyecto-historial-estados/proyecto/:proyectoId} : get all the historial for a proyecto.
     *
     * @param proyectoId the proyecto id.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list in body.
     */
    @GetMapping("/proyecto-historial-estados/proyecto/{proyectoId}")
    public ResponseEntity<List<ProyectoHistorialEstadoDTO>> getProyectoHistorialEstadosByProyecto(@PathVariable Long proyectoId) {
        log.debug("REST request to get ProyectoHistorialEstados by Proyecto : {}", proyectoId);
        List<ProyectoHistorialEstadoDTO> list = proyectoHistorialEstadoService.findByProyectoId(proyectoId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /proyecto-historial-estados/:id} : get the "id" proyectoHistorialEstado.
     *
     * @param id the id of the proyectoHistorialEstadoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectoHistorialEstadoDTO, or status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyecto-historial-estados/{id}")
    public ResponseEntity<ProyectoHistorialEstadoDTO> getProyectoHistorialEstado(@PathVariable Long id) {
        log.debug("REST request to get ProyectoHistorialEstado : {}", id);
        Optional<ProyectoHistorialEstadoDTO> proyectoHistorialEstadoDTO = proyectoHistorialEstadoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proyectoHistorialEstadoDTO);
    }

    /**
     * {@code DELETE  /proyecto-historial-estados/:id} : delete the "id" proyectoHistorialEstado.
     *
     * @param id the id of the proyectoHistorialEstadoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proyecto-historial-estados/{id}")
    public ResponseEntity<Void> deleteProyectoHistorialEstado(@PathVariable Long id) {
        log.debug("REST request to delete ProyectoHistorialEstado : {}", id);
        proyectoHistorialEstadoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
