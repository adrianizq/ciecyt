package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.enumeration.TipoRequisito;
import co.edu.itp.ciecyt.security.AuthoritiesConstants;
import co.edu.itp.ciecyt.service.RequisitoProyectoService;
import co.edu.itp.ciecyt.service.dto.RequisitoProyectoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.RequisitoProyecto}.
 */
@RestController
@RequestMapping("/api")
public class RequisitoProyectoResource {

    private final Logger log = LoggerFactory.getLogger(RequisitoProyectoResource.class);

    private static final String ENTITY_NAME = "requisitoProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequisitoProyectoService requisitoProyectoService;

    public RequisitoProyectoResource(RequisitoProyectoService requisitoProyectoService) {
        this.requisitoProyectoService = requisitoProyectoService;
    }

    /**
     * {@code POST  /requisito-proyectos} : Create a new requisitoProyecto.
     *
     * <p>Los requisitos los crea el servicio a partir del catalogo segun la modalidad del
     * proyecto; un alta desde el cliente permitiria inyectar un requisito ajen al reglamento.
     */
    @PostMapping("/requisito-proyectos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<RequisitoProyectoDTO> createRequisitoProyecto(@RequestBody RequisitoProyectoDTO requisitoProyectoDTO)
        throws URISyntaxException {
        log.debug("REST request to save RequisitoProyecto : {}", requisitoProyectoDTO);
        if (requisitoProyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new requisitoProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RequisitoProyectoDTO result = requisitoProyectoService.save(requisitoProyectoDTO);
        return ResponseEntity.created(new URI("/api/requisito-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requisito-proyectos} : Updates an existing requisitoProyecto.
     *
     * <p>El estado solo cambia por los flujos {@code entregar} y {@code validar}, que aplican sus
     * propias reglas; un PUT directo permitiria saltarselas.
     */
    @PutMapping("/requisito-proyectos")
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<RequisitoProyectoDTO> updateRequisitoProyecto(@RequestBody RequisitoProyectoDTO requisitoProyectoDTO) {
        log.debug("REST request to update RequisitoProyecto : {}", requisitoProyectoDTO);
        if (requisitoProyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequisitoProyectoDTO result = requisitoProyectoService.save(requisitoProyectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requisitoProyectoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /requisito-proyectos} : get all the requisitoProyectos.
     *
     * <p>El listado global es el tablero de revision del CIECYT, no una consulta por proyecto.
     */
    @GetMapping("/requisito-proyectos")
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.CIECYT + "', '" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<List<RequisitoProyectoDTO>> getAllRequisitoProyectos(Pageable pageable) {
        log.debug("REST request to get a page of RequisitoProyectos");
        Page<RequisitoProyectoDTO> page = requisitoProyectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /requisito-proyectos/proyecto/{proyectoId}} : get requisitos de un proyecto.
     */
    @GetMapping("/requisito-proyectos/proyecto/{proyectoId}")
    @PreAuthorize("@proyectoAutorizacionService.puedeLeer(#proyectoId)")
    public ResponseEntity<List<RequisitoProyectoDTO>> getRequisitoProyectosByProyecto(@PathVariable Long proyectoId) {
        log.debug("REST request to get RequisitoProyectos de proyecto : {}", proyectoId);
        List<RequisitoProyectoDTO> list = requisitoProyectoService.findByProyectoId(proyectoId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code POST  /requisito-proyectos/generar/proyecto/{proyectoId}} : genera los requisitos faltantes del proyecto.
     */
    @PostMapping("/requisito-proyectos/generar/proyecto/{proyectoId}")
    @PreAuthorize("@proyectoAutorizacionService.puedeModificarAdjuntoDe(#proyectoId)")
    public ResponseEntity<List<RequisitoProyectoDTO>> generarRequisitosProyecto(@PathVariable Long proyectoId) {
        log.debug("REST request to generar RequisitoProyectos de proyecto : {}", proyectoId);
        List<RequisitoProyectoDTO> list = requisitoProyectoService.generarParaProyecto(proyectoId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code POST  /requisito-proyectos/:id/entregar} : el estudiante entrega un requisito.
     *
     * <p>Para un requisito de tipo CAMPO el cuerpo trae {@code observacion} con el dato numerico
     * (el avance del 75%); para uno de tipo DOCUMENTO trae {@code archivo}. Ver
     * {@link RequisitoProyectoService#entregar(Long, String, String)}.
     */
    @PostMapping("/requisito-proyectos/{id}/entregar")
    @PreAuthorize("@proyectoAutorizacionService.puedeEntregarRequisito(#id)")
    public ResponseEntity<RequisitoProyectoDTO> entregarRequisito(
        @PathVariable Long id,
        @RequestBody(required = false) Map<String, String> payload
    ) {
        log.debug("REST request to entregar RequisitoProyecto : {}", id);
        String archivo = payload != null ? payload.get("archivo") : null;
        String observacion = payload != null ? payload.get("observacion") : null;

        requisitoProyectoService
            .findOne(id)
            .filter(dto -> dto.getRequisitoProyectoRequisitoTipo() == TipoRequisito.CAMPO)
            .ifPresent(dto -> validarDatoNumerico(dto.getRequisitoProyectoRequisitoNombre(), observacion));

        RequisitoProyectoDTO result = requisitoProyectoService.entregar(id, archivo, observacion);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Un requisito de tipo CAMPO no recibe un archivo sino un dato, y ese dato tiene que ser
     * numerico: es el caso del avance del 75%, que se guarda en la observacion porque no hay
     * columna propia para el.
     *
     * @param nombreRequisito nombre del requisito que se esta entregando.
     * @param dato dato enviado por el estudiante.
     */
    private void validarDatoNumerico(String nombreRequisito, String dato) {
        if (dato == null || dato.trim().isEmpty()) {
            return;
        }
        try {
            Double.parseDouble(dato.trim());
        } catch (NumberFormatException e) {
            throw new BadRequestAlertException(
                "El requisito '" + nombreRequisito + "' espera un valor numerico y se recibio: " + dato,
                "requisitoProyecto", "datoNoNumerico");
        }
    }

    /**
     * {@code POST  /requisito-proyectos/:id/validar} : CIECYT aprueba o rechaza un requisito.
     */
    @PostMapping("/requisito-proyectos/{id}/validar")
    @PreAuthorize("hasAnyRole('" + AuthoritiesConstants.CIECYT + "', '" + AuthoritiesConstants.ADMIN + "')")
    public ResponseEntity<RequisitoProyectoDTO> validarRequisito(
        @PathVariable Long id,
        @RequestBody Map<String, Object> payload
    ) {
        log.debug("REST request to validar RequisitoProyecto : {}", id);
        boolean aprobado = Boolean.TRUE.equals(payload.get("aprobado"));
        String observacion = payload.get("observacion") != null ? payload.get("observacion").toString() : null;
        RequisitoProyectoDTO result = requisitoProyectoService.validar(id, aprobado, observacion);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /requisito-proyectos/:id} : get the "id" requisitoProyecto.
     */
    @GetMapping("/requisito-proyectos/{id}")
    @PreAuthorize("@proyectoAutorizacionService.puedeLeerRequisito(#id)")
    public ResponseEntity<RequisitoProyectoDTO> getRequisitoProyecto(@PathVariable Long id) {
        log.debug("REST request to get RequisitoProyecto : {}", id);
        Optional<RequisitoProyectoDTO> requisitoProyectoDTO = requisitoProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requisitoProyectoDTO);
    }

    /**
     * {@code DELETE  /requisito-proyectos/:id} : delete the "id" requisitoProyecto.
     */
    @DeleteMapping("/requisito-proyectos/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteRequisitoProyecto(@PathVariable Long id) {
        log.debug("REST request to delete RequisitoProyecto : {}", id);
        requisitoProyectoService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
