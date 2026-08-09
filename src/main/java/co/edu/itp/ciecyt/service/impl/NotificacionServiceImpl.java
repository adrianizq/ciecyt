package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Notificacion;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.repository.NotificacionRepository;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.service.MailService;
import co.edu.itp.ciecyt.service.NotificacionService;
import co.edu.itp.ciecyt.service.dto.NotificacionDTO;
import co.edu.itp.ciecyt.service.mapper.NotificacionMapper;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Notificacion}.
 */
@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {

    private final Logger log = LoggerFactory.getLogger(NotificacionServiceImpl.class);

    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;
    private final UserRepository userRepository;
    private final ProyectoRepository proyectoRepository;
    private final MailService mailService;

    public NotificacionServiceImpl(
        NotificacionRepository notificacionRepository,
        NotificacionMapper notificacionMapper,
        UserRepository userRepository,
        ProyectoRepository proyectoRepository,
        MailService mailService
    ) {
        this.notificacionRepository = notificacionRepository;
        this.notificacionMapper = notificacionMapper;
        this.userRepository = userRepository;
        this.proyectoRepository = proyectoRepository;
        this.mailService = mailService;
    }

    @Override
    public NotificacionDTO crearNotificacion(User user, String titulo, String mensaje, Proyecto proyecto, String tipo) {
        log.debug("Request to crearNotificacion : user={}, titulo={}", user.getLogin(), titulo);
        Notificacion notificacion = new Notificacion();
        notificacion.setUser(user);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacion.setProyecto(proyecto);
        notificacion.setTipo(tipo);
        notificacion.setLeido(false);
        notificacion.setFechaCreacion(Instant.now());
        notificacion = notificacionRepository.save(notificacion);

        if (user.getEmail() != null) {
            mailService.sendEmail(user.getEmail(), titulo, mensaje, false, false);
        }

        return notificacionMapper.toDto(notificacion);
    }

    @Override
    public NotificacionDTO crearNotificacion(Long userId, String titulo, String mensaje, Long proyectoId, String tipo) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + userId));
        Proyecto proyecto = proyectoId != null ? proyectoRepository.findById(proyectoId).orElse(null) : null;
        return crearNotificacion(user, titulo, mensaje, proyecto, tipo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> findNoLeidasByUserLogin(String login) {
        log.debug("Request to get all no leidas Notificaciones by login : {}", login);
        return notificacionRepository.findByUserLoginAndLeidoFalseOrderByFechaCreacionDesc(login).stream()
            .map(notificacionMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotificacionDTO> findByUserId(Long userId) {
        log.debug("Request to get all Notificaciones by userId : {}", userId);
        return notificacionRepository.findByUserIdOrderByFechaCreacionDesc(userId).stream()
            .map(notificacionMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public boolean marcarLeida(Long id, String login) {
        log.debug("Request to marcarLeida Notificacion : {}, login : {}", id, login);
        return notificacionRepository.marcarLeida(id, login) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotificacionDTO> findOne(Long id) {
        log.debug("Request to get Notificacion : {}", id);
        return notificacionRepository.findById(id).map(notificacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Notificacion : {}", id);
        notificacionRepository.deleteById(id);
    }
}
