package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.ProyectoHistorialEstado;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.repository.NotificacionRepository;
import co.edu.itp.ciecyt.repository.ProyectoHistorialEstadoRepository;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.security.AuthoritiesConstants;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Revisa los plazos del Acuerdo 025 y genera notificaciones al CIECYT cuando
 * un plazo esta por vencer o ya vencio.
 */
@Service
public class PlazoNotificacionService {

    private static final Logger log = LoggerFactory.getLogger(PlazoNotificacionService.class);

    private static final String TIPO_PLAZO = "PLAZO";
    private static final int DIAS_JURADO = 7;
    private static final int DIAS_EVALUACION = 10;
    private static final int UMBRAL_AVISO = 2;

    private static final Set<Long> MODALIDADES_CON_JURADO = Set.of(9002L, 9004L);
    private static final Set<EnumEstadoProyecto> ESTADOS_CON_PLAZO = EnumSet.of(
        EnumEstadoProyecto.HABILITADO,
        EnumEstadoProyecto.EN_REVISION_ASESOR,
        EnumEstadoProyecto.APROBADA_POR_ASESOR,
        EnumEstadoProyecto.EN_REVISION_JURADO_PROPUESTA,
        EnumEstadoProyecto.EN_REVISION_JURADO_PROYECTO,
        EnumEstadoProyecto.CORRECCIONES_ASESOR,
        EnumEstadoProyecto.CORRECCIONES_JURADO_PROPUESTA,
        EnumEstadoProyecto.CORRECCIONES_ASESOR_PROYECTO,
        EnumEstadoProyecto.CORRECCIONES_JURADO_PROYECTO
    );

    private final ProyectoRepository proyectoRepository;
    private final ProyectoHistorialEstadoRepository historialRepository;
    private final NotificacionRepository notificacionRepository;
    private final NotificacionService notificacionService;
    private final IntegranteProyectoService integranteProyectoService;
    private final UserRepository userRepository;

    public PlazoNotificacionService(
        ProyectoRepository proyectoRepository,
        ProyectoHistorialEstadoRepository historialRepository,
        NotificacionRepository notificacionRepository,
        NotificacionService notificacionService,
        IntegranteProyectoService integranteProyectoService,
        UserRepository userRepository
    ) {
        this.proyectoRepository = proyectoRepository;
        this.historialRepository = historialRepository;
        this.notificacionRepository = notificacionRepository;
        this.notificacionService = notificacionService;
        this.integranteProyectoService = integranteProyectoService;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 7 * * ?")
    @Transactional
    public void verificarPlazosProgramado() {
        int creadas = verificarPlazos();
        if (creadas > 0) {
            log.info("Se generaron {} notificaciones de plazos del Acuerdo 025", creadas);
        }
    }

    @Transactional
    public int verificarPlazos() {
        int creadas = 0;
        for (Proyecto proyecto : proyectoRepository.findAll()) {
            if (proyecto.getEstado() == null || !ESTADOS_CON_PLAZO.contains(proyecto.getEstado())) {
                continue;
            }
            try {
                creadas += verificarProyecto(proyecto);
            } catch (Exception e) {
                log.warn("No se pudo verificar el plazo del proyecto {}: {}", proyecto.getId(), e.getMessage());
            }
        }
        return creadas;
    }

    private int verificarProyecto(Proyecto proyecto) {
        Long modalidadId = proyecto.getProyectoModalidad() != null ? proyecto.getProyectoModalidad().getId() : null;
        if (modalidadId == null) {
            return 0;
        }
        List<ProyectoHistorialEstado> historial = historialRepository.findByProyectoIdOrderByFechaCambioDesc(proyecto.getId());
        Instant ahora = Instant.now();

        if (
            MODALIDADES_CON_JURADO.contains(modalidadId) &&
            (proyecto.getEstado() == EnumEstadoProyecto.HABILITADO ||
                proyecto.getEstado() == EnumEstadoProyecto.EN_REVISION_ASESOR ||
                proyecto.getEstado() == EnumEstadoProyecto.APROBADA_POR_ASESOR)
        ) {
            if (sinJurado(proyecto.getId())) {
                Instant inicio = fechaDe(historial, EnumEstadoProyecto.HABILITADO);
                int creadas = notificarSiAplica(proyecto, inicio, DIAS_JURADO, "Designación de jurado", ahora);
                if (creadas > 0) {
                    return creadas;
                }
            }
        }

        if (proyecto.getEstado() == EnumEstadoProyecto.EN_REVISION_JURADO_PROPUESTA) {
            Instant inicio = fechaDe(historial, EnumEstadoProyecto.EN_REVISION_JURADO_PROPUESTA);
            return notificarSiAplica(proyecto, inicio, DIAS_EVALUACION, "Revisión de la propuesta por el jurado", ahora);
        }

        if (proyecto.getEstado() == EnumEstadoProyecto.EN_REVISION_JURADO_PROYECTO) {
            Instant inicio = fechaDe(historial, EnumEstadoProyecto.EN_REVISION_JURADO_PROYECTO);
            return notificarSiAplica(proyecto, inicio, DIAS_EVALUACION, "Revisión del documento final", ahora);
        }

        if (proyecto.getEstado().name().startsWith("CORRECCIONES_")) {
            Instant inicio = fechaDe(historial, proyecto.getEstado());
            return notificarSiAplica(proyecto, inicio, DIAS_EVALUACION, "Correcciones", ahora);
        }

        return 0;
    }

    private boolean sinJurado(Long proyectoId) {
        try {
            return integranteProyectoService.findJuradosIntegranteProyectoId(proyectoId, "Jurado").isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private Instant fechaDe(List<ProyectoHistorialEstado> historial, EnumEstadoProyecto estado) {
        return historial
            .stream()
            .filter(item -> item.getEstadoNuevo() == estado)
            .map(ProyectoHistorialEstado::getFechaCambio)
            .findFirst()
            .orElse(null);
    }

    private int notificarSiAplica(Proyecto proyecto, Instant inicio, int diasPlazo, String etiqueta, Instant ahora) {
        if (inicio == null) {
            return 0;
        }
        long transcurridos = diasHabilesEntre(inicio, ahora);
        long restantes = diasPlazo - transcurridos;
        if (restantes > UMBRAL_AVISO) {
            return 0;
        }

        boolean vencido = restantes < 0;
        String titulo = (vencido ? "Plazo vencido: " : "Plazo por vencer: ") + etiqueta;
        if (notificacionRepository.countSince(proyecto.getId(), TIPO_PLAZO, titulo, inicio) > 0) {
            return 0;
        }

        String mensaje = vencido
            ? String.format(
                "El proyecto '%s' superó el plazo de %d días hábiles para %s (retraso de %d días hábiles).",
                proyecto.getTitulo() != null ? proyecto.getTitulo() : "Sin título",
                diasPlazo,
                etiqueta.toLowerCase(),
                Math.abs(restantes)
            )
            : String.format(
                "El proyecto '%s' está a %d día(s) hábil(es) del plazo de %d días hábiles para %s.",
                proyecto.getTitulo() != null ? proyecto.getTitulo() : "Sin título",
                restantes,
                diasPlazo,
                etiqueta.toLowerCase()
            );

        int creadas = 0;
        for (User user : userRepository.findAllByAuthoritiesName(AuthoritiesConstants.CIECYT)) {
            notificacionService.crearNotificacion(user, titulo, mensaje, proyecto, TIPO_PLAZO);
            creadas++;
        }
        return creadas;
    }

    private long diasHabilesEntre(Instant desde, Instant hasta) {
        LocalDate inicio = desde.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fin = hasta.atZone(ZoneId.systemDefault()).toLocalDate();
        long cuenta = 0;
        for (LocalDate cursor = inicio; !cursor.isAfter(fin); cursor = cursor.plusDays(1)) {
            DayOfWeek dia = cursor.getDayOfWeek();
            if (dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY) {
                cuenta++;
            }
        }
        return cuenta;
    }
}
