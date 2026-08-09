package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.Notificacion;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.service.dto.NotificacionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Notificacion}.
 */
public interface NotificacionService {

    /**
     * Crea una notificación interna y envía email al usuario.
     *
     * @param user usuario destinatario.
     * @param titulo título de la notificación.
     * @param mensaje cuerpo del mensaje.
     * @param proyecto proyecto relacionado (opcional).
     * @param tipo tipo de notificación.
     * @return el DTO creado.
     */
    NotificacionDTO crearNotificacion(User user, String titulo, String mensaje, Proyecto proyecto, String tipo);

    /**
     * Crea una notificación interna y envía email al usuario.
     *
     * @param userId id del usuario destinatario.
     * @param titulo título de la notificación.
     * @param mensaje cuerpo del mensaje.
     * @param proyectoId id del proyecto relacionado (opcional).
     * @param tipo tipo de notificación.
     * @return el DTO creado.
     */
    NotificacionDTO crearNotificacion(Long userId, String titulo, String mensaje, Long proyectoId, String tipo);

    /**
     * Obtiene las notificaciones no leídas del usuario actual.
     *
     * @param login login del usuario.
     * @return lista de notificaciones.
     */
    List<NotificacionDTO> findNoLeidasByUserLogin(String login);

    /**
     * Obtiene todas las notificaciones de un usuario.
     *
     * @param userId id del usuario.
     * @return lista de notificaciones.
     */
    List<NotificacionDTO> findByUserId(Long userId);

    /**
     * Marca una notificación como leída.
     *
     * @param id id de la notificación.
     * @param login login del usuario.
     * @return true si se actualizó.
     */
    boolean marcarLeida(Long id, String login);

    /**
     * Get the "id" notificacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotificacionDTO> findOne(Long id);

    /**
     * Delete the "id" notificacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
