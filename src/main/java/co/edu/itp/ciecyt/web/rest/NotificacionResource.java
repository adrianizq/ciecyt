package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.security.SecurityUtils;
import co.edu.itp.ciecyt.service.NotificacionService;
import co.edu.itp.ciecyt.service.dto.NotificacionDTO;
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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Notificacion}.
 */
@RestController
@RequestMapping("/api")
public class NotificacionResource {

    private final Logger log = LoggerFactory.getLogger(NotificacionResource.class);

    private static final String ENTITY_NAME = "notificacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificacionService notificacionService;

    public NotificacionResource(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    /**
     * {@code POST  /notificaciones} : Create a new notificacion.
     *
     * @param notificacionDTO the notificacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificacionDTO.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notificaciones")
    public ResponseEntity<NotificacionDTO> createNotificacion(
        @Valid @RequestBody NotificacionDTO notificacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save Notificacion : {}", notificacionDTO);
        if (notificacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new notificacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificacionDTO result = notificacionService.crearNotificacion(
            notificacionDTO.getUserId(),
            notificacionDTO.getTitulo(),
            notificacionDTO.getMensaje(),
            notificacionDTO.getProyectoId(),
            notificacionDTO.getTipo()
        );
        return ResponseEntity.created(new URI("/api/notificaciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notificaciones/no-leidas} : get unread notifications for current user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list in body.
     */
    @GetMapping("/notificaciones/no-leidas")
    public ResponseEntity<List<NotificacionDTO>> getNotificacionesNoLeidas() {
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("Usuario no autenticado", ENTITY_NAME, "nouser"));
        log.debug("REST request to get Notificaciones no leidas for user : {}", login);
        List<NotificacionDTO> list = notificacionService.findNoLeidasByUserLogin(login);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code GET  /notificaciones/usuario/:userId} : get all notifications for a user.
     *
     * @param userId the user id.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list in body.
     */
    @GetMapping("/notificaciones/usuario/{userId}")
    public ResponseEntity<List<NotificacionDTO>> getNotificacionesByUser(@PathVariable Long userId) {
        log.debug("REST request to get Notificaciones by user : {}", userId);
        List<NotificacionDTO> list = notificacionService.findByUserId(userId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * {@code PUT  /notificaciones/:id/leer} : mark a notification as read.
     *
     * @param id the id of the notificacion.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     */
    @PutMapping("/notificaciones/{id}/leer")
    public ResponseEntity<Void> marcarNotificacionLeida(@PathVariable Long id) {
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new BadRequestAlertException("Usuario no autenticado", ENTITY_NAME, "nouser"));
        log.debug("REST request to marcarNotificacionLeida : {}", id);
        boolean updated = notificacionService.marcarLeida(id, login);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /notificaciones/:id} : get the "id" notificacion.
     *
     * @param id the id of the notificacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificacionDTO, or status {@code 404 (Not Found)}.
     */
    @GetMapping("/notificaciones/{id}")
    public ResponseEntity<NotificacionDTO> getNotificacion(@PathVariable Long id) {
        log.debug("REST request to get Notificacion : {}", id);
        Optional<NotificacionDTO> notificacionDTO = notificacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificacionDTO);
    }

    /**
     * {@code DELETE  /notificaciones/:id} : delete the "id" notificacion.
     *
     * @param id the id of the notificacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notificaciones/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        log.debug("REST request to delete Notificacion : {}", id);
        notificacionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
