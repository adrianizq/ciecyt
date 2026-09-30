package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.LineaInvestigacion;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.Programa;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.ProyectoHistorialEstado;
import co.edu.itp.ciecyt.domain.TransicionEstado;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoContinuidad;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoRequisito;
import co.edu.itp.ciecyt.repository.EstadoModalidadRepository;
import co.edu.itp.ciecyt.repository.LineaInvestigacionRepository;
import co.edu.itp.ciecyt.repository.ProgramaRepository;
import co.edu.itp.ciecyt.repository.ProyectoHistorialEstadoRepository;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.repository.TransicionEstadoRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.security.SecurityUtils;
import co.edu.itp.ciecyt.security.AuthoritiesConstants;
import co.edu.itp.ciecyt.service.DecanoFacultadService;
import co.edu.itp.ciecyt.service.IntegranteProyectoService;
import co.edu.itp.ciecyt.service.NotificacionService;
import co.edu.itp.ciecyt.service.ProyectoAutorizacionService;
import co.edu.itp.ciecyt.service.ProyectoService;
import co.edu.itp.ciecyt.service.RequisitoProyectoService;
import co.edu.itp.ciecyt.service.RolesModalidadService;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.dto.RequisitoProyectoDTO;
import co.edu.itp.ciecyt.service.dto.RolesModalidadDTO;
import co.edu.itp.ciecyt.service.dto.TransicionEstadoDTO;
import co.edu.itp.ciecyt.service.mapper.ProyectoMapper;
import co.edu.itp.ciecyt.config.Constants;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Proyecto}.
 */
@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    /**
     * Estados asociados a la sustentación, socialización y titulación. Se bloquean
     * cuando el proyecto pierde el derecho por agotar los tres (3) periodos de continuidad.
     */
    private static final Set<EnumEstadoProyecto> ESTADOS_SUSTENTACION_TITULACION = Collections.unmodifiableSet(
        EnumSet.of(
            EnumEstadoProyecto.LISTO_PARA_SUSTENTAR,
            EnumEstadoProyecto.SUSTENTACION_PROGRAMADA,
            EnumEstadoProyecto.SUSTENTACION_REALIZADA,
            EnumEstadoProyecto.EN_EVALUACION_SUSTENTACION,
            EnumEstadoProyecto.AJUSTES_SUSTENTACION,
            EnumEstadoProyecto.LISTO_PARA_SOCIALIZAR,
            EnumEstadoProyecto.SOCIALIZACION_PROGRAMADA,
            EnumEstadoProyecto.SOCIALIZACION_REALIZADA,
            EnumEstadoProyecto.EN_EVALUACION_SOCIALIZACION,
            EnumEstadoProyecto.NOTA_DEFINITIVA,
            EnumEstadoProyecto.FINALIZADO
        )
    );

    private final Logger log = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    private final ProyectoRepository proyectoRepository;
    private final IntegranteProyectoService integranteProyectoService;
    //private final IntegranteProyectoRepository integranteProyectoRepository;
    private final RolesModalidadService rolesModalidadService;
    private final LineaInvestigacionRepository lineaInvestigacionRepository;
    private final ProgramaRepository programaRepository;
    private final ProyectoHistorialEstadoRepository proyectoHistorialEstadoRepository;
    private final NotificacionService notificacionService;
    private final TransicionEstadoRepository transicionEstadoRepository;
    private final EstadoModalidadRepository estadoModalidadRepository;
    private final UserRepository userRepository;
    private final RequisitoProyectoService requisitoProyectoService;
    private final ProyectoAutorizacionService proyectoAutorizacionService;

    private final DecanoFacultadService decanoFacultadService;

    private final ProyectoMapper proyectoMapper;

    public ProyectoServiceImpl(
        ProyectoRepository proyectoRepository,
        ProyectoMapper proyectoMapper,
        IntegranteProyectoService integranteProyectoService,
        RolesModalidadService rolesModalidadService,
        LineaInvestigacionRepository lineaInvestigacionRepository,
        ProgramaRepository programaRepository,
        ProyectoHistorialEstadoRepository proyectoHistorialEstadoRepository,
        NotificacionService notificacionService,
        TransicionEstadoRepository transicionEstadoRepository,
        EstadoModalidadRepository estadoModalidadRepository,
        UserRepository userRepository,
        RequisitoProyectoService requisitoProyectoService,
        ProyectoAutorizacionService proyectoAutorizacionService,
        DecanoFacultadService decanoFacultadService
    ) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
        this.integranteProyectoService = integranteProyectoService;
        this.rolesModalidadService = rolesModalidadService;
        this.lineaInvestigacionRepository = lineaInvestigacionRepository;
        this.programaRepository = programaRepository;
        this.proyectoHistorialEstadoRepository = proyectoHistorialEstadoRepository;
        this.notificacionService = notificacionService;
        this.transicionEstadoRepository = transicionEstadoRepository;
        this.estadoModalidadRepository = estadoModalidadRepository;
        this.userRepository = userRepository;
        this.proyectoAutorizacionService = proyectoAutorizacionService;
        this.decanoFacultadService = decanoFacultadService;
        this.requisitoProyectoService = requisitoProyectoService;
    }

    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProyectoDTO save(ProyectoDTO proyectoDTO) {
        log.debug("Request to save Proyecto : {}", proyectoDTO);
        log.debug("proyectoLineaInvestigacionId: {}, subLineaLineaInvestigacionId: {}",
            proyectoDTO.getProyectoLineaInvestigacionId(), proyectoDTO.getSubLineaLineaInvestigacionId());
        Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);

        // La creacion y la actualizacion entran por el mismo metodo, asi que la autorizacion se
        // resuelve aqui: en alta no hay proyecto todavia, en edicion el proyecto ya existe y
        // hay que comprobar que quien lo modifica es parte de el y que sigue en elaboracion.
        if (proyecto.getId() != null) {
            Proyecto existente = proyectoRepository.findById(proyecto.getId()).orElse(null);
            if (existente == null) {
                throw new EntityNotFoundException("El proyecto que se intenta modificar no existe");
            }
            if (!proyectoAutorizacionService.puedeEditar(existente)) {
                throw new AccessDeniedException(
                    "No tiene permisos para modificar este proyecto. Solo puede editarlo mientras "
                        + "esté en elaboracion y sea parte de él, o bien si actúa como CIECYT o administrador."
                );
            }
            proyecto = actualizarDesdeGuardado(proyectoDTO, proyecto, existente);
        }

        if (proyecto.getId() == null && proyecto.getEstado() == null) {
            proyecto.setEstado(estadoInicialDe(proyecto.getProyectoModalidad()));
        }

        if (proyecto.getId() == null && (proyectoDTO.getEstadoContinuidad() == null || proyectoDTO.getEstadoContinuidad().isEmpty())) {
            proyecto.setEstadoContinuidad(EnumEstadoContinuidad.REGULAR);
        }

        // Asegurar que se persistan las relaciones provenientes del DTO
        if (proyectoDTO.getProyectoLineaInvestigacionId() != null && proyecto.getProyectoLineaInvestigacion() == null) {
            LineaInvestigacion linea = lineaInvestigacionRepository.findById(proyectoDTO.getProyectoLineaInvestigacionId()).orElse(null);
            proyecto.setProyectoLineaInvestigacion(linea);
        }
        if (proyectoDTO.getSubLineaLineaInvestigacionId() != null && proyecto.getSubLineaLineaInvestigacion() == null) {
            LineaInvestigacion subLinea = lineaInvestigacionRepository.findById(proyectoDTO.getSubLineaLineaInvestigacionId()).orElse(null);
            proyecto.setSubLineaLineaInvestigacion(subLinea);
        }
        if (proyectoDTO.getProyectoProgramaId() != null && proyecto.getProyectoPrograma() == null) {
            Programa programa = programaRepository.findById(proyectoDTO.getProyectoProgramaId()).orElse(null);
            proyecto.setProyectoPrograma(programa);
        }

        proyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Cambia el estado de un proyecto registrando el historial.
     *
     * @param proyectoId id del proyecto.
     * @param nuevoEstado estado destino.
     * @param observacion observación opcional.
     * @return el proyecto actualizado.
     */
    @Override
    public ProyectoDTO cambiarEstado(Long proyectoId, EnumEstadoProyecto nuevoEstado, String observacion) {
        log.debug("Request to cambiarEstado Proyecto : {}, nuevoEstado : {}", proyectoId, nuevoEstado);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));

        // Antes de mirar el flujo hay que mirar de que proyecto se trata. La validacion de
        // transicion solo comprueba el rol, no la pertenencia, asi que sin esta comprobacion
        // un rol sin relacion con el proyecto podia moverlo de estado.
        if (!proyectoAutorizacionService.puedeOperarFlujoDe(proyectoId)) {
            log.warn("Intento de cambio de estado sobre el proyecto {} por un usuario sin relacion con el", proyectoId);
            throw new AccessDeniedException(
                "No tiene relacion con el proyecto " + proyectoId
                    + ", no puede operarlo. Solo puede moverlo quien sea parte de el o bien CIECYT o un administrador."
            );
        }

        EnumEstadoProyecto estadoAnterior = proyecto.getEstado();
        if (estadoAnterior == nuevoEstado) {
            return proyectoMapper.toDto(proyecto);
        }

        validarContinuidadParaSustentacion(proyecto, nuevoEstado);

        validarTransicionPermitida(proyecto, estadoAnterior, nuevoEstado);

        proyecto.setEstado(nuevoEstado);
        sincronizarFlagsLegacy(proyecto, nuevoEstado);
        proyecto = proyectoRepository.save(proyecto);

        ProyectoHistorialEstado historial = new ProyectoHistorialEstado();
        historial.setProyecto(proyecto);
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(nuevoEstado);
        historial.setObservacion(observacion);
        historial.setUsuarioLogin(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
        historial.setFechaCambio(Instant.now());
        proyectoHistorialEstadoRepository.save(historial);

        notificarResponsables(proyecto, estadoAnterior, nuevoEstado);

        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Valida que la transición de estado sea permitida para la modalidad del proyecto
     * y que el usuario autenticado tenga el rol requerido. Si la modalidad no tiene
     * transiciones configuradas (Acuerdo 29), se mantiene el comportamiento permisivo.
     */

    /**
     * Estado en el que nace un proyecto segun el flujo de su modalidad.
     *
     * <p>El punto de entrada se deduce de la configuracion: es el estado del que salen
     * transiciones y al que nunca se entra. Antes se fijaba EN_ELABORACION_PROPUESTA para
     * todas las modalidades, pero Diplomado (9005) y Especializacion (9007) no tienen fase de
     * propuesta: su flujo arranca en PREINSCRITA y pasa directo a validacion documental. Con el
     * valor fijo, un proyecto nuevo de esas modalidades caia en un estado sin transicion de
     * salida y quedaba bloqueado.
     */
    private EnumEstadoProyecto estadoInicialDe(Modalidad modalidad) {
        if (modalidad == null || modalidad.getId() == null) {
            return EnumEstadoProyecto.PREINSCRITA;
        }

        List<TransicionEstado> transiciones = transicionEstadoRepository.findByTransicionEstadoModalidadIdAndActivoTrue(modalidad.getId());
        if (transiciones.isEmpty()) {
            return EnumEstadoProyecto.PREINSCRITA;
        }

        Set<EnumEstadoProyecto> destinos = new HashSet<>();
        for (TransicionEstado transicion : transiciones) {
            destinos.add(transicion.getEstadoDestino());
        }

        Set<EnumEstadoProyecto> entradas = new HashSet<>();
        for (TransicionEstado transicion : transiciones) {
            if (!destinos.contains(transicion.getEstadoOrigen())) {
                entradas.add(transicion.getEstadoOrigen());
            }
        }

        if (entradas.size() == 1) {
            return entradas.iterator().next();
        }

        // Configuracion ambigua o ciclica: se usa el estado de inscripcion, que es el punto de
        // partida declarado por el Acuerdo 25 para cualquier modalidad.
        log.warn(
            "La modalidad '{}' tiene {} puntos de entrada ({}); se usara PREINSCRITA como estado inicial",
            modalidad.getId(),
            entradas.size(),
            entradas
        );
        return EnumEstadoProyecto.PREINSCRITA;
    }

    private void validarTransicionPermitida(Proyecto proyecto, EnumEstadoProyecto estadoAnterior, EnumEstadoProyecto nuevoEstado) {        Long modalidadId = proyecto.getProyectoModalidad() != null ? proyecto.getProyectoModalidad().getId() : null;
        if (modalidadId == null) {
            throw new IllegalArgumentException(
                "El proyecto no tiene modalidad asignada, no se puede validar la transición de estado."
            );
        }

        List<TransicionEstado> transiciones = transicionEstadoRepository
            .findByTransicionEstadoModalidadIdAndActivoTrueAndEstadoOrigen(modalidadId, estadoAnterior);

        // Sin transiciones configuradas la modalidad se queda sin flujo. Antes se permitia el
        // cambio y con eso cualquier usuario autenticado podia llevar un proyecto a cualquier
        // estado; ahora se rechaza porque el silencio de la configuracion no habilita el paso.
        if (transiciones.isEmpty()) {
            throw new IllegalArgumentException(
                String.format(
                    "La modalidad '%s' no tiene transiciones configuradas desde el estado '%s'. "
                        + "La transición a '%s' no se puede validar.",
                    proyecto.getProyectoModalidad() != null ? proyecto.getProyectoModalidad().getModalidad() : "desconocida",
                    estadoAnterior != null ? estadoAnterior.name() : "INICIAL",
                    nuevoEstado
                )
            );
        }

        boolean permitida = transiciones.stream()
            .anyMatch(t -> t.getEstadoDestino() == nuevoEstado && rolPermiteTransicion(t));

        if (!permitida) {
            String login = SecurityUtils.getCurrentUserLogin().orElse("anonimo");
            throw new IllegalArgumentException(
                String.format(
                    "Transición de estado no permitida: %s -> %s para la modalidad '%s'. Usuario: %s.",
                    estadoAnterior != null ? estadoAnterior.name() : "INICIAL",
                    nuevoEstado.name(),
                    proyecto.getProyectoModalidad().getModalidad(),
                    login
                )
            );
        }
    }

    private boolean rolPermiteTransicion(TransicionEstado transicion) {
        // La misma regla que usa la autorizacion de escritura, para que una transicion no
        // pueda verse permitida al cambiar el estado y prohibida al guardar.
        return proyectoAutorizacionService.rolActualPermite(transicion.getRolRequerido());
    }

    /**
     * Notifica a los responsables del proyecto según el nuevo estado.
     */
    private void notificarResponsables(Proyecto proyecto, EnumEstadoProyecto estadoAnterior, EnumEstadoProyecto nuevoEstado) {
        String rolResponsable = null;
        boolean notificarCiecyt = false;
        switch (nuevoEstado) {
            case EN_VALIDACION_DOCUMENTAL:
                notificarCiecyt = true;
                break;
            case OBSERVACIONES_DOCUMENTACION:
            case HABILITADO:
            case CORRECCIONES_ASESOR:
            case CORRECCIONES_ASESOR_PROYECTO:
            case CORRECCIONES_JURADO_PROPUESTA:
            case CORRECCIONES_JURADO_PROYECTO:
            case APROBADA_POR_ASESOR:
            case VIABLE:
            case NO_VIABLE:
            case EN_ELABORACION_PROYECTO:
            case SUSTENTACION_PROGRAMADA:
            case AJUSTES_SUSTENTACION:
                rolResponsable = "Estudiante";
                break;
            case EN_REVISION_ASESOR:
            case EN_REVISION_ASESOR_PROYECTO:
                rolResponsable = "Asesor";
                break;
            case EN_REVISION_JURADO_PROPUESTA:
            case EN_REVISION_JURADO_PROYECTO:
            case EN_EVALUACION_SUSTENTACION:
                rolResponsable = "Jurado";
                break;
            case SOCIALIZACION_REALIZADA:
            case EN_EVALUACION_SOCIALIZACION:
                rolResponsable = "Asesor";
                break;
            case LISTO_PARA_SUSTENTAR:
            case LISTO_PARA_SOCIALIZAR:
            case SOCIALIZACION_PROGRAMADA:
            case NOTA_DEFINITIVA:
            case FINALIZADO:
                notificarCiecyt = true;
                break;
            default:
                break;
        }

        String titulo = "Nueva tarea pendiente: " + nuevoEstado.name();
        String mensaje = String.format(
            "El proyecto '%s' cambió de estado de %s a %s. Tiene una acción pendiente.",
            proyecto.getTitulo() != null ? proyecto.getTitulo() : "Sin título",
            estadoAnterior != null ? estadoAnterior.name() : "INICIAL",
            nuevoEstado.name()
        );

        if (notificarCiecyt) {
            try {
                List<User> usuariosCiecyt = userRepository.findAllByAuthoritiesName(AuthoritiesConstants.CIECYT);
                for (User user : usuariosCiecyt) {
                    notificacionService.crearNotificacion(
                        user,
                        titulo,
                        mensaje,
                        proyecto,
                        "CAMBIO_ESTADO"
                    );
                }
            } catch (Exception e) {
                log.warn("No se pudieron notificar a CIECYT del proyecto {}: {}", proyecto.getId(), e.getMessage());
            }
            return;
        }

        if (rolResponsable == null) {
            return;
        }

        try {
            List<IntegranteProyectoDTO> integrantes = integranteProyectoService.findByIntegranteProyectoProyectoId(proyecto.getId());
            if (integrantes == null) {
                return;
            }
            for (IntegranteProyectoDTO integrante : integrantes) {
                if (integrante.getIntegranteProyectoRolesModalidadRol() != null &&
                    integrante.getIntegranteProyectoRolesModalidadRol().contains(rolResponsable)) {
                    notificacionService.crearNotificacion(
                        integrante.getIntegranteProyectoUserId(),
                        titulo,
                        mensaje,
                        proyecto.getId(),
                        "CAMBIO_ESTADO"
                    );
                }
            }
        } catch (Exception e) {
            log.warn("No se pudieron notificar responsables del proyecto {}: {}", proyecto.getId(), e.getMessage());
        }
    }

    /**
     * Programa el acto público del proyecto ante CIECYT.
     * El estado destino depende de la modalidad: {@code SUSTENTACION_PROGRAMADA} para las
     * modalidades con jurado (9002 y 9004) y {@code SOCIALIZACION_PROGRAMADA} para las que
     * socializan sin jurado (9001, 9003 y 9006, Acuerdo 025 art. 9 y 14 par. 2).
     *
     * @param proyectoId id del proyecto.
     * @param fecha fecha programada del acto público.
     * @return el proyecto actualizado.
     */
    @Override
    @Transactional
    public ProyectoDTO programarActo(Long proyectoId, LocalDate fecha) {
        log.debug("Programando acto publico del proyecto : {} para {}", proyectoId, fecha);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));
        if (fecha == null) {
            throw new BadRequestAlertException("La fecha del acto público es obligatoria", "proyecto", "fecharequired");
        }
        boolean socializacion = esModalidadSocializacion(proyecto);
        EnumEstadoProyecto estadoEsperado = socializacion
            ? EnumEstadoProyecto.LISTO_PARA_SOCIALIZAR
            : EnumEstadoProyecto.LISTO_PARA_SUSTENTAR;
        if (proyecto.getEstado() != estadoEsperado) {
            throw new IllegalArgumentException(
                "El proyecto no está listo para programar el acto público (estado actual: "
                + (proyecto.getEstado() != null ? proyecto.getEstado().name() : "null")
                + ", estado esperado: " + estadoEsperado.name() + ")."
            );
        }
        proyecto.setFechaSustentacionProyecto(fecha);
        proyectoRepository.save(proyecto);
        EnumEstadoProyecto estadoProgramado = socializacion
            ? EnumEstadoProyecto.SOCIALIZACION_PROGRAMADA
            : EnumEstadoProyecto.SUSTENTACION_PROGRAMADA;
        return cambiarEstado(
            proyectoId,
            estadoProgramado,
            "Acto público (" + nombreActo(socializacion) + ") programado para el " + fecha
        );
    }

    /**
     * Registra que el acto público ya se realizó y habilita la evaluación por el jurado
     * (sustentación) o por el asesor (socialización).
     *
     * @param proyectoId id del proyecto.
     * @return el proyecto actualizado.
     */
    @Override
    @Transactional
    public ProyectoDTO registrarActoRealizado(Long proyectoId) {
        log.debug("Registrando acto publico realizado del proyecto : {}", proyectoId);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));
        boolean socializacion = proyecto.getEstado() == EnumEstadoProyecto.SOCIALIZACION_PROGRAMADA;
        EnumEstadoProyecto estadoEsperado = socializacion
            ? EnumEstadoProyecto.SOCIALIZACION_PROGRAMADA
            : EnumEstadoProyecto.SUSTENTACION_PROGRAMADA;
        if (proyecto.getEstado() != estadoEsperado) {
            throw new IllegalArgumentException(
                "El acto público no está programado, no se puede registrar como realizado (estado actual: "
                + (proyecto.getEstado() != null ? proyecto.getEstado().name() : "null") + ")."
            );
        }
        EnumEstadoProyecto estadoRealizado = socializacion
            ? EnumEstadoProyecto.SOCIALIZACION_REALIZADA
            : EnumEstadoProyecto.SUSTENTACION_REALIZADA;
        return cambiarEstado(
            proyectoId,
            estadoRealizado,
            "Acto público (" + nombreActo(socializacion) + ") realizado"
        );
    }

    /**
     * Indica si la modalidad del proyecto socializa sin jurado. Se resuelve con los estados
     * activos de la modalidad para no duplicar en código la lista de modalidades del Acuerdo 025.
     */
    private boolean esModalidadSocializacion(Proyecto proyecto) {
        Long modalidadId = proyecto.getProyectoModalidad() != null ? proyecto.getProyectoModalidad().getId() : null;
        if (modalidadId == null) {
            return false;
        }
        return estadoModalidadRepository
            .findByEstadoModalidadModalidadIdAndEstadoOrderByOrdenAsc(
                modalidadId,
                EnumEstadoProyecto.LISTO_PARA_SOCIALIZAR
            )
            .stream()
            .anyMatch(estadoModalidad -> Boolean.TRUE.equals(estadoModalidad.getActivo()));
    }

    private String nombreActo(boolean socializacion) {
        return socializacion ? "socialización" : "sustentación";
    }

    /**
     * Solicita la validación documental del proyecto ante CIECYT.
     * Genera los requisitos habilitantes pendientes y mueve el proyecto al estado
     * {@code EN_VALIDACION_DOCUMENTAL}.
     */
    @Override
    public ProyectoDTO solicitarValidacionDocumental(Long proyectoId) {        log.debug("Solicitando validacion documental del proyecto : {}", proyectoId);
        requisitoProyectoService.generarParaProyecto(proyectoId);
        return cambiarEstado(proyectoId, EnumEstadoProyecto.EN_VALIDACION_DOCUMENTAL, "Solicitud de validación documental");
    }

    /**
     * CIECYT aprueba u observa la documentación de un proyecto.
     * Si {@code aprobado} es {@code true}, verifica que todos los requisitos obligatorios
     * del estado actual estén aprobados antes de habilitar el proyecto.
     */
    @Override
    public ProyectoDTO validarDocumentacion(Long proyectoId, boolean aprobado, String observacion) {
        log.debug("Validando documentacion del proyecto : {} aprobado={}", proyectoId, aprobado);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));

        if (aprobado) {
            List<RequisitoProyectoDTO> requisitos = requisitoProyectoService.findByProyectoId(proyectoId);
            List<String> pendientes = requisitos.stream()
                .filter(r -> Boolean.TRUE.equals(r.getRequisitoProyectoRequisitoObligatorio()))
                .filter(r -> r.getRequisitoProyectoRequisitoEstado() == null
                    || r.getRequisitoProyectoRequisitoEstado() == EnumEstadoProyecto.EN_VALIDACION_DOCUMENTAL)
                .filter(r -> r.getEstado() != EnumEstadoRequisito.APROBADO)
                .map(r -> r.getRequisitoProyectoRequisitoNombre() != null
                    ? r.getRequisitoProyectoRequisitoNombre()
                    : "Requisito #" + r.getRequisitoProyectoRequisitoId())
                .collect(Collectors.toList());

            if (!pendientes.isEmpty()) {
                throw new IllegalArgumentException(
                    "No se puede habilitar el proyecto: hay requisitos obligatorios sin aprobar: " + String.join(", ", pendientes)
                );
            }
            return cambiarEstado(proyectoId, EnumEstadoProyecto.HABILITADO, observacion);
        }

        return cambiarEstado(proyectoId, EnumEstadoProyecto.OBSERVACIONES_DOCUMENTACION, observacion);
    }

    /**
     * Inicia la calidad de continuidad del proyecto (Acuerdo 025, art. 10, parágrafo 3).
     */
    @Override
    public ProyectoDTO iniciarContinuidad(Long proyectoId) {
        log.debug("Iniciando continuidad del proyecto : {}", proyectoId);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));

        if (proyecto.getEstadoContinuidad() == EnumEstadoContinuidad.CONTINUIDAD_PERDIDA) {
            throw new BadRequestAlertException(
                "El derecho a la opción de grado ya se perdió: el estudiante agotó los tres (3) periodos de continuidad sin sustentar o socializar (Acuerdo 025, art. 10, parágrafo 5).",
                "proyecto",
                "continuidadPerdida"
            );
        }
        if (proyecto.getEstadoContinuidad() == EnumEstadoContinuidad.CONTINUIDAD) {
            return proyectoMapper.toDto(proyecto);
        }

        proyecto.setEstadoContinuidad(EnumEstadoContinuidad.CONTINUIDAD);
        if (proyecto.getFechaInicioContinuidad() == null) {
            proyecto.setFechaInicioContinuidad(LocalDate.now());
        }
        proyecto = proyectoRepository.save(proyecto);

        registrarHistorialContinuidad(proyecto, "Inicio de continuidad: el estudiante dispone de tres (3) periodos académicos para sustentar o socializar la opción de grado (Acuerdo 025, art. 10, parágrafo 3).");

        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Registra una renovación de matrícula durante la continuidad (Acuerdo 025, art. 10, parágrafo 4).
     * Al completar los tres (3) periodos sin sustentar o socializar, el derecho se pierde (parágrafo 5),
     * salvo que se haya otorgado el periodo adicional excepcional (parágrafo 6), caso en el cual la
     * pérdida se produce al completar un periodo más.
     */
    @Override
    public ProyectoDTO registrarRenovacionContinuidad(Long proyectoId) {
        log.debug("Registrando renovacion de continuidad del proyecto : {}", proyectoId);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));

        if (proyecto.getEstadoContinuidad() == EnumEstadoContinuidad.CONTINUIDAD_PERDIDA) {
            throw new BadRequestAlertException(
                "El derecho a la opción de grado ya se perdió: el estudiante agotó los periodos de continuidad sin sustentar o socializar (Acuerdo 025, art. 10, parágrafo 5).",
                "proyecto",
                "continuidadPerdida"
            );
        }
        if (proyecto.getEstadoContinuidad() != EnumEstadoContinuidad.CONTINUIDAD) {
            throw new BadRequestAlertException(
                "El proyecto no está en continuidad. Inicie la continuidad antes de registrar renovaciones de matrícula.",
                "proyecto",
                "continuidadNoIniciada"
            );
        }

        int periodoLimite = periodoLimiteContinuidad(proyecto);
        int periodosUsados = proyecto.getPeriodosContinuidadUsados() + 1;
        proyecto.setPeriodosContinuidadUsados(periodosUsados);

        boolean pierdeDerecho = periodosUsados >= periodoLimite;
        if (pierdeDerecho) {
            proyecto.setEstadoContinuidad(EnumEstadoContinuidad.CONTINUIDAD_PERDIDA);
        }
        proyecto = proyectoRepository.save(proyecto);

        String marcadorPeriodos = marcadorPeriodosContinuidad(proyecto);
        if (pierdeDerecho) {
            registrarHistorialContinuidad(
                proyecto,
                "Renovación de continuidad " + periodosUsados + "/" + periodoLimite + ". El estudiante agotó los " + periodoLimite
                    + " periodos de continuidad sin sustentar o socializar la opción de grado: se pierde el derecho (Acuerdo 025, art. 10, parágrafo 5)."
            );
        } else {
            registrarHistorialContinuidad(
                proyecto,
                "Renovación de continuidad " + periodosUsados + "/" + marcadorPeriodos
                    + " con el pago de derechos complementarios más el 20% de la matrícula ordinaria (Acuerdo 025, art. 10, parágrafo 4)."
            );
        }

        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Otorga el periodo académico adicional de continuidad (Acuerdo 025, art. 10, parágrafo 6):
     * por fuerza mayor o caso fortuito debidamente justificado, el estudiante recibe un (1) periodo
     * más a los tres (3) periodos de continuidad, cancelando los derechos complementarios más el 20%
     * de la matrícula ordinaria.
     */
    @Override
    public ProyectoDTO otorgarAplazamientoContinuidad(Long proyectoId) {
        log.debug("Otorgando aplazamiento (periodo adicional) de continuidad del proyecto : {}", proyectoId);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));

        if (proyecto.getEstadoContinuidad() == EnumEstadoContinuidad.CONTINUIDAD_PERDIDA) {
            throw new BadRequestAlertException(
                "El derecho a la opción de grado ya se perdió y no es posible otorgar el periodo adicional de continuidad (Acuerdo 025, art. 10, parágrafo 5).",
                "proyecto",
                "continuidadPerdida"
            );
        }
        if (proyecto.getEstadoContinuidad() != EnumEstadoContinuidad.CONTINUIDAD) {
            throw new BadRequestAlertException(
                "El proyecto no está en continuidad. Inicie la continuidad antes de otorgar un periodo adicional.",
                "proyecto",
                "continuidadNoIniciada"
            );
        }
        if (Boolean.TRUE.equals(proyecto.getContinuidadPeriodoAdicional())) {
            throw new BadRequestAlertException(
                "El periodo académico adicional de continuidad ya fue otorgado (Acuerdo 025, art. 10, parágrafo 6).",
                "proyecto",
                "aplazamientoYaOtorgado"
            );
        }

        proyecto.setContinuidadPeriodoAdicional(true);
        proyecto = proyectoRepository.save(proyecto);

        registrarHistorialContinuidad(
            proyecto,
            "Se otorga el periodo académico adicional de continuidad (1 de 1) por fuerza mayor o caso fortuito debidamente justificado, con el pago de derechos complementarios más el 20% de la matrícula ordinaria (Acuerdo 025, art. 10, parágrafo 6)."
        );

        return proyectoMapper.toDto(proyecto);
    }

    private int periodoLimiteContinuidad(Proyecto proyecto) {
        return Boolean.TRUE.equals(proyecto.getContinuidadPeriodoAdicional()) ? 4 : 3;
    }

    private String marcadorPeriodosContinuidad(Proyecto proyecto) {
        return Boolean.TRUE.equals(proyecto.getContinuidadPeriodoAdicional()) ? "3+1" : "3";
    }

    private void registrarHistorialContinuidad(Proyecto proyecto, String observacion) {
        ProyectoHistorialEstado historial = new ProyectoHistorialEstado();
        historial.setProyecto(proyecto);
        historial.setEstadoAnterior(proyecto.getEstado());
        historial.setEstadoNuevo(proyecto.getEstado());
        historial.setObservacion(observacion);
        historial.setUsuarioLogin(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
        historial.setFechaCambio(Instant.now());
        proyectoHistorialEstadoRepository.save(historial);
    }

    /**
     * Bloquea la sustentación, socialización y titulación del proyecto cuando el derecho
     * a la opción de grado se perdió por agotar los tres (3) periodos de continuidad.
     */
    private void validarContinuidadParaSustentacion(Proyecto proyecto, EnumEstadoProyecto nuevoEstado) {
        if (EnumEstadoContinuidad.CONTINUIDAD_PERDIDA == proyecto.getEstadoContinuidad()
            && ESTADOS_SUSTENTACION_TITULACION.contains(nuevoEstado)) {
            throw new BadRequestAlertException(
                "No es posible continuar el trámite de grado: el derecho a la opción de grado se perdió por agotar los periodos de continuidad sin sustentar o socializar (Acuerdo 025, art. 10, parágrafo 5).",
                "proyecto",
                "continuidadPerdida"
            );
        }
    }

    /**
     * Obtiene (generando si faltan) los requisitos del proyecto.
     */
    @Override
    public List<RequisitoProyectoDTO> getRequisitosProyecto(Long proyectoId) {
        log.debug("Obteniendo requisitos del proyecto : {}", proyectoId);
        return requisitoProyectoService.generarParaProyecto(proyectoId);
    }

    /**
     * Obtiene las transiciones de estado permitidas para el proyecto desde su estado actual.
     */
    @Override
    public List<TransicionEstadoDTO> getTransicionesPermitidas(Long proyectoId) {
        log.debug("Obteniendo transiciones permitidas del proyecto : {}", proyectoId);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));
        Long modalidadId = proyecto.getProyectoModalidad() != null ? proyecto.getProyectoModalidad().getId() : null;
        if (modalidadId == null || proyecto.getEstado() == null) {
            return new ArrayList<>();
        }
        return transicionEstadoRepository
            .findByTransicionEstadoModalidadIdAndActivoTrueAndEstadoOrigen(modalidadId, proyecto.getEstado()).stream()
            .map(t -> {
                TransicionEstadoDTO dto = new TransicionEstadoDTO();
                dto.setId(t.getId());
                dto.setEstadoOrigen(t.getEstadoOrigen());
                dto.setEstadoDestino(t.getEstadoDestino());
                dto.setRolRequerido(t.getRolRequerido());
                dto.setActivo(t.getActivo());
                dto.setTransicionEstadoModalidadId(modalidadId);
                return dto;
            })
            .collect(Collectors.toList());
    }

    /**
     * Sincroniza los booleanos legacy con el nuevo estado para mantener compatibilidad
     * mientras el frontend transiciona a usar exclusivamente {@code estado}.
     */
    private void sincronizarFlagsLegacy(Proyecto proyecto, EnumEstadoProyecto estado) {
        switch (estado) {
            case PREINSCRITA:
            case EN_ELABORACION_PROPUESTA:
                proyecto.setPreEnviado(false);
                proyecto.setEnviado(false);
                break;
            case EN_REVISION_ASESOR:
            case CORRECCIONES_ASESOR:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                break;
            case APROBADA_POR_ASESOR:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                break;
            case EN_REVISION_JURADO_PROPUESTA:
            case CORRECCIONES_JURADO_PROPUESTA:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                break;
            case VIABLE:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                break;
            case NO_VIABLE:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("NO_VIABLE");
                proyecto.setViable(false);
                break;
            case EN_ELABORACION_PROYECTO:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                proyecto.setProyectoEnviado(false);
                break;
            case EN_REVISION_JURADO_PROYECTO:
            case CORRECCIONES_JURADO_PROYECTO:
            case EN_REVISION_ASESOR_PROYECTO:
            case CORRECCIONES_ASESOR_PROYECTO:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                proyecto.setProyectoEnviado(true);
                break;
            case LISTO_PARA_SUSTENTAR:
            case SUSTENTACION_PROGRAMADA:
            case SUSTENTACION_REALIZADA:
            case EN_EVALUACION_SUSTENTACION:
            case AJUSTES_SUSTENTACION:
            case LISTO_PARA_SOCIALIZAR:
            case SOCIALIZACION_PROGRAMADA:
            case SOCIALIZACION_REALIZADA:
            case EN_EVALUACION_SOCIALIZACION:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                proyecto.setProyectoEnviado(true);
                proyecto.setSustentar(true);
                break;
            case NOTA_DEFINITIVA:
            case FINALIZADO:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                proyecto.setProyectoEnviado(true);
                proyecto.setSustentar(true);
                break;
            default:
                break;
        }
    }

    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProyectoDTO saveAsesorProyecto(ProyectoDTO proyectoDTO) throws Exception {
        log.debug("Request to save Proyecto : {}", proyectoDTO);
        Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);
        proyecto = proyectoRepository.save(proyecto);
        //guadar integrante
        IntegranteProyectoDTO asesorDTO = new IntegranteProyectoDTO();

        asesorDTO.setIntegranteProyectoProyectoId(proyecto.getId());
        asesorDTO.setIntegranteProyectoUserId(proyectoDTO.getAsesorId());
        //arreglado

        Modalidad modalidad = proyecto.getProyectoModalidad();
        Long modalidadId = modalidad.getId(); //eje 1551

        RolesModalidadDTO rolesModalidad;
        rolesModalidad = rolesModalidadService.findByRolAndRolesModalidadModalidadId("Asesor", modalidadId);
        Long rolesModalidadId = rolesModalidad.getId();
        //asesorDTO.setIntegranteProyectoRolesModalidadId(4451L);
        asesorDTO.setIntegranteProyectoRolesModalidadId(rolesModalidadId);

        ////////////////* validamos que exista el asesor
        List<IntegranteProyectoDTO> lIntegranteProyectoDTO = new ArrayList();
        lIntegranteProyectoDTO =
            integranteProyectoService.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
                proyecto.getId(),
                rolesModalidadId
            );
        if (lIntegranteProyectoDTO != null && lIntegranteProyectoDTO.size() > 0) {
            IntegranteProyectoDTO dto = lIntegranteProyectoDTO.get(0);
            asesorDTO.setId(dto.getId());
        }
        //Long integranteProyectoId = integranteProyectoDTO.getIntegranteProyectoUserId();

        //////////////////////77

        integranteProyectoService.save(asesorDTO);
        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Get all the proyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proyectos");
        return proyectoRepository.findAll(pageable).map(proyectoMapper::toDto);
    }

    /**
     * Aplica una actualizacion partiendo de lo que esta almacenado, no de lo que llega en el
     * cuerpo de la peticion.
     *
     * <p>El endpoint PUT es el mismo que usan las pantallas de evaluacion para el borrador y el
     * envio, asi que recibe el proyecto completo. Si se reconstruyera la entidad desde el cuerpo,
     * quien esta evaluando podria tambien mover el estado del proyecto, cambiarle el titulo o
     * reasignarle la facultad: el estado pasaria a depender de un PUT en vez de pasar por
     * cambiarEstado, con lo que ni la maquina de estados ni el historial ni las notificaciones
     * se enterarian. Por eso el estado jamas se toma del cuerpo, y para quien solo esta
     * evaluando se admiten unicamente los campos del concepto.
     *
     * <p>La lista de campos depende de quien escribe: el estudiante que redacta su propuesta
     * dispone de ella casi entera, mientras que al asesor o al jurado evaluando les corresponde
     * solo el concepto. Se decide con la misma configuracion del flujo que valida la
     * transicion, no con una lista de estados.
     */
    private Proyecto actualizarDesdeGuardado(ProyectoDTO proyectoDTO, Proyecto delBody, Proyecto existente) {
        // El estado solo se mueve por cambiarEstado, que valida la transicion y deja rastro.
        delBody.setEstado(existente.getEstado());

        if (!soloEvaluaEnEsteEstado(existente)) {
            // Quien esta elaborando su propuesta si puede corregirla entera. La facultad
            // tampoco se toca aqui: define el alcance de la decanatura sobre el proyecto.
            if (!proyectoAutorizacionService.esGestorGlobal()) {
                delBody.setFacultad(existente.getFacultad());
            }
            return delBody;
        }

        // Evaluador: se parte de la entidad gestionada y se aplican solo los campos del
        // concepto, de modo que cualquier otro dato del cuerpo se descarta.
        Proyecto base = existente;
        base.setViable(proyectoDTO.getViable());
        base.setViabilidad(proyectoDTO.getViabilidad());
        base.setConclusion(proyectoDTO.getConclusion());
        base.setRecomendaciones(proyectoDTO.getRecomendaciones());
        base.setRecomendacionesAsesorPropuesta(proyectoDTO.getRecomendacionesAsesorPropuesta());
        base.setRecomendacionesAsesorProyecto(proyectoDTO.getRecomendacionesAsesorProyecto());
        base.setRecomendacionesJuradoProyecto(proyectoDTO.getRecomendacionesJuradoProyecto());
        base.setRecomendacionesJuradoSustentacion(proyectoDTO.getRecomendacionesJuradoSustentacion());
        base.setSustentar(proyectoDTO.getSustentar());
        base.setEnviado(proyectoDTO.getEnviado());
        return base;
    }

    /**
     * True si en el estado actual del proyecto el flujo solo espera a un rol evaluador, es
     * decir asesor o jurado. Es la senal de que quien escribe esta emitiendo un concepto y no
     * redactando la propuesta. Se responde con la configuracion de transiciones: si el
     * estudiante todavia tiene una accion pendiente, la propuesta sigue siendo editable.
     */
    private boolean soloEvaluaEnEsteEstado(Proyecto existente) {
        if (existente.getEstado() == null || existente.getProyectoModalidad() == null) {
            return false;
        }
        List<TransicionEstado> salientes = transicionEstadoRepository
            .findByTransicionEstadoModalidadIdAndActivoTrueAndEstadoOrigen(
                existente.getProyectoModalidad().getId(), existente.getEstado());
        if (salientes.isEmpty()) {
            return false;
        }
        boolean algunRolEvaluador = false;
        for (TransicionEstado transicion : salientes) {
            String rol = transicion.getRolRequerido();
            if (rol == null) {
                return false;
            }
            if (rol.contains("ROLE_ASESOR") || rol.contains("ROLE_JURADO")) {
                algunRolEvaluador = true;
            } else {
                // El estudiante, CIECYT o un comodin: la propuesta sigue siendo suya.
                return false;
            }
        }
        return algunRolEvaluador;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoDTO> findAllDeAlcance(Pageable pageable) {
        if (proyectoAutorizacionService.esGestorGlobal()) {
            return findAll(pageable);
        }
        List<Long> facultades = decanoFacultadService.facultadIdsDelDecanoActual();
        if (facultades.isEmpty()) {
            throw new AccessDeniedException("No tiene alcance para listar proyectos");
        }
        log.debug("Listado de proyectos acotado a las facultades {}", facultades);
        return proyectoRepository.findByFacultadIdIn(facultades, pageable).map(proyectoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoDTO> findAllProyectosIntegrantesDeAlcance() throws Exception {
        if (proyectoAutorizacionService.esGestorGlobal()) {
            return findAllProyectosIntegrantes();
        }
        List<Long> facultades = decanoFacultadService.facultadIdsDelDecanoActual();
        if (facultades.isEmpty()) {
            throw new AccessDeniedException("No tiene alcance para listar proyectos");
        }
        return aDtoConIntegrantes(proyectoRepository.findByFacultadIdIn(facultades));
    }

    /**ADR
     * REtorna todos los proyectos pero con una lista de todos los integrantes
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProyectoDTO> findAllProyectosIntegrantes() throws Exception {
        log.debug("Request to get all Proyectos");
        return aDtoConIntegrantes(proyectoRepository.findAll());
    }

    /**
     * Proyecta los proyectos a DTO agregando los integrantes y las banderas de si tiene jurado
     * o asesor, que es lo que consume la pantalla de seguimiento.
     */
    private List<ProyectoDTO> aDtoConIntegrantes(List<Proyecto> lProyectos) throws Exception {
        List<ProyectoDTO> proyectoDTOList = new ArrayList<ProyectoDTO>();
        for (Proyecto proyecto : lProyectos) {
            ProyectoDTO dto;
            dto = proyectoMapper.toDto(proyecto);
            dto.setTieneJurado(false);
            dto.setTieneAsesor(false);
            List<IntegranteProyectoDTO> lIntegrantes = integranteProyectoService.findByIntegranteProyectoProyectoId(proyecto.getId());
            if (lIntegrantes != null && lIntegrantes.size() > 0) {
                for (IntegranteProyectoDTO i : lIntegrantes) {
                    if (i.getIntegranteProyectoRolesModalidadRol().contains("Jurado")) {
                        dto.setTieneJurado(true);
                    }
                    if (i.getIntegranteProyectoRolesModalidadRol().contains("Asesor")) {
                        dto.setTieneAsesor(true);
                    }
                }
            }

            dto.setListaIntegrantesProyecto(lIntegrantes);
            proyectoDTOList.add(dto);
        }
        //return proyectoMapper.toDto(lProyectos);
        return proyectoDTOList;
    }

    /**
     * Get one proyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoDTO> findOne(Long id) {
        log.debug("Request to get Proyecto : {}", id);
        return proyectoRepository.findById(id).map(proyectoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoDTO> findOneIntegrantes(Long id) throws Exception {
        log.debug("Request to get Proyecto : {}", id);
        Optional<ProyectoDTO> odto = proyectoRepository.findById(id).map(proyectoMapper::toDto);
        ProyectoDTO dto = odto.get();
        dto.setTieneJurado(false);
        dto.setTieneAsesor(false);
        List<IntegranteProyectoDTO> lIntegrantes = integranteProyectoService.findByIntegranteProyectoProyectoId(dto.getId());
        if (lIntegrantes != null && lIntegrantes.size() > 0) {
            for (IntegranteProyectoDTO i : lIntegrantes) {
                if (i.getIntegranteProyectoRolesModalidadRol().contains("Jurado")) {
                    dto.setTieneJurado(true);
                }
                if (i.getIntegranteProyectoRolesModalidadRol().contains("Asesor")) {
                    dto.setTieneAsesor(true);
                }
            }
        }
        dto.setListaIntegrantesProyecto(lIntegrantes);
        return Optional.of(dto);
    }

    /**
     * Get one proyecto con asesor by id.
     *
     * @param idProyecto  the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    // public ProyectoDTO findOneWithAsesor(Long idProyecto, Long idRolModalkidad) throws Exception {
    public ProyectoDTO findOneWithAsesor(Long idProyecto) throws Exception {
        log.debug("Request to get Proyecto : {}", idProyecto);
        Proyecto p = new Proyecto();
        p = proyectoRepository.findByIdOrderById(idProyecto);

        Modalidad modalidad = p.getProyectoModalidad();
        Long modalidadId = modalidad.getId(); //eje 1551

        RolesModalidadDTO rolesModalidad;
        rolesModalidad = rolesModalidadService.findByRolAndRolesModalidadModalidadId("Asesor", modalidadId);
        Long rolesModalidadId = rolesModalidad.getId();

        IntegranteProyectoDTO integranteProyectoDTO = new IntegranteProyectoDTO();
        List<IntegranteProyectoDTO> lIntegranteProyectoDTO = new ArrayList();
        lIntegranteProyectoDTO =
            integranteProyectoService.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(idProyecto, rolesModalidadId);
        if (lIntegranteProyectoDTO != null) {
            integranteProyectoDTO = lIntegranteProyectoDTO.get(0);
        }
        Long integranteProyectoId = integranteProyectoDTO.getIntegranteProyectoUserId();

        ProyectoDTO proyectoDTO;

        proyectoDTO = proyectoMapper.toDto(p);
        proyectoDTO.setAsesorId(integranteProyectoId);

        log.debug("findOneWithAsesor - proyectoDTO linea: {}, sublinea: {}",
            proyectoDTO.getProyectoLineaInvestigacionId(), proyectoDTO.getSubLineaLineaInvestigacionId());

        return proyectoDTO;
    }

    /**
     * Delete the proyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proyecto : {}", id);
        proyectoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProyectoDTO> findByIntegranteProyecto(Long idUsuaro) throws Exception {
        log.debug("Request to get all Elementos de una modalidad con una idModalidad");
        List<Proyecto> listaProyecto = new ArrayList<>();
        List<IntegranteProyecto> listaIntegranteProyecto = integranteProyectoService.findByIntegranteProyectoUserId(idUsuaro);
        if (listaIntegranteProyecto != null) {
            for (IntegranteProyecto integranteProyecto : listaIntegranteProyecto) {
                Proyecto proyecto = new Proyecto();
                proyecto = integranteProyecto.getIntegranteProyectoProyecto();
                listaProyecto.add(proyecto);
            }

            List<ProyectoDTO> listDTO = new ArrayList<>();

            for (Proyecto p : listaProyecto) {
                listDTO.add(proyectoMapper.toDto(p));
            }
            return listDTO;
        } else {
            return null;
        }
    }

    ///////////////////////////////77777777777777777777777
    @Transactional(readOnly = true)
    public List<ProyectoDTO> findByIntegranteProyectoAuthority(Long idUsuario, String authority) throws Exception {
        log.debug("Request to get all Proyectos de una modalidad con una idModalidad");
        //List<RolesModalidad> lRolesModalidad = rolesModalidadService.findByRolesModalidadAuthorityName(authority);

        List<Proyecto> listaProyecto = new ArrayList<>();
        List<IntegranteProyecto> listaIntegranteProyecto = integranteProyectoService.findByIntegranteProyectoAuthority(
            idUsuario,
            authority
        );
        if (listaIntegranteProyecto != null) {
            for (IntegranteProyecto integranteProyecto : listaIntegranteProyecto) {
                Proyecto proyecto = new Proyecto();
                proyecto = integranteProyecto.getIntegranteProyectoProyecto();
                listaProyecto.add(proyecto);
            }

            List<ProyectoDTO> listDTO = new ArrayList<>();

            for (Proyecto p : listaProyecto) {
                listDTO.add(proyectoMapper.toDto(p));
            }
            return listDTO;
        } else {
            return null;
        }
    }

    //////////////////////////////////////////77777777777777

    ///////////////////////////////77777777777777777777777
    @Transactional(readOnly = true)
    public List<ProyectoDTO> findByIntegranteProyectoRol(Long idUsuario, String rol) throws Exception {
        log.debug("Request to get all Proyectos de una modalidad con una idModalidad");
        //List<RolesModalidad> lRolesModalidad = rolesModalidadService.findByRolesModalidadAuthorityName(authority);

        List<Proyecto> listaProyecto = new ArrayList<>();
        List<IntegranteProyecto> listaIntegranteProyecto = integranteProyectoService.findByIntegranteProyectoRol(idUsuario, rol);
        if (listaIntegranteProyecto != null) {
            for (IntegranteProyecto integranteProyecto : listaIntegranteProyecto) {
                Proyecto proyecto = new Proyecto();
                proyecto = integranteProyecto.getIntegranteProyectoProyecto();
                listaProyecto.add(proyecto);
            }

            List<ProyectoDTO> listDTO = new ArrayList<>();

            for (Proyecto p : listaProyecto) {
                listDTO.add(proyectoMapper.toDto(p));
            }
            return listDTO;
        } else {
            return null;
        }
    }
}
