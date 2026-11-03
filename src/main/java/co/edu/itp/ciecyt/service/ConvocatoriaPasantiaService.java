package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.ConvocatoriaPasantia;
import co.edu.itp.ciecyt.domain.PostulacionPasantia;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoConvocatoria;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoPostulacion;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.repository.ConvocatoriaPasantiaRepository;
import co.edu.itp.ciecyt.repository.PostulacionPasantiaRepository;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.security.AuthoritiesConstants;
import co.edu.itp.ciecyt.security.SecurityUtils;
import co.edu.itp.ciecyt.service.dto.ConvocatoriaPasantiaDTO;
import co.edu.itp.ciecyt.service.dto.PostulacionPasantiaDTO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de convocatorias de pasantia (Acuerdo 025, art. 16 par. 2 y art. 19).
 */
@Service
@Transactional
public class ConvocatoriaPasantiaService {

    private static final Logger log = LoggerFactory.getLogger(ConvocatoriaPasantiaService.class);
    private static final String ENTITY_NAME = "convocatoriaPasantia";

    private final ConvocatoriaPasantiaRepository convocatoriaRepository;
    private final PostulacionPasantiaRepository postulacionRepository;
    private final ProyectoRepository proyectoRepository;
    private final IntegranteProyectoService integranteProyectoService;
    private final UserRepository userRepository;
    private final NotificacionService notificacionService;

    public ConvocatoriaPasantiaService(
        ConvocatoriaPasantiaRepository convocatoriaRepository,
        PostulacionPasantiaRepository postulacionRepository,
        ProyectoRepository proyectoRepository,
        IntegranteProyectoService integranteProyectoService,
        UserRepository userRepository,
        NotificacionService notificacionService
    ) {
        this.convocatoriaRepository = convocatoriaRepository;
        this.postulacionRepository = postulacionRepository;
        this.proyectoRepository = proyectoRepository;
        this.integranteProyectoService = integranteProyectoService;
        this.userRepository = userRepository;
        this.notificacionService = notificacionService;
    }

    @Transactional(readOnly = true)
    public List<ConvocatoriaPasantiaDTO> findAll() {
        return convocatoriaRepository.findAllByOrderByFechaCierreDesc().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ConvocatoriaPasantiaDTO> findAbiertas() {
        LocalDate hoy = LocalDate.now();
        return convocatoriaRepository
            .findByEstadoAndActivoTrueOrderByFechaCierreAsc(EnumEstadoConvocatoria.ABIERTA)
            .stream()
            .filter(convocatoria ->
                (convocatoria.getFechaApertura() == null || !convocatoria.getFechaApertura().isAfter(hoy)) &&
                (convocatoria.getFechaCierre() == null || !convocatoria.getFechaCierre().isBefore(hoy))
            )
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ConvocatoriaPasantiaDTO> findOne(Long id) {
        return convocatoriaRepository.findById(id).map(this::toDto);
    }

    public ConvocatoriaPasantiaDTO save(ConvocatoriaPasantiaDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new BadRequestAlertException("La convocatoria requiere titulo", ENTITY_NAME, "titulorequerido");
        }
        ConvocatoriaPasantia entity = dto.getId() != null
            ? convocatoriaRepository.findById(dto.getId()).orElseThrow(() -> new BadRequestAlertException("Convocatoria no encontrada", ENTITY_NAME, "noexiste"))
            : new ConvocatoriaPasantia();

        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setModalidadId(dto.getModalidadId());
        entity.setEntidad(dto.getEntidad());
        entity.setConvenio(dto.getConvenio());
        entity.setCupos(dto.getCupos());
        entity.setFechaApertura(dto.getFechaApertura());
        entity.setFechaCierre(dto.getFechaCierre());
        entity.setRequisitos(dto.getRequisitos());
        entity.setCriteriosSeleccion(dto.getCriteriosSeleccion());
        if (dto.getEstado() != null) {
            entity.setEstado(EnumEstadoConvocatoria.valueOf(dto.getEstado()));
        } else if (entity.getEstado() == null) {
            entity.setEstado(EnumEstadoConvocatoria.ABIERTA);
        }
        entity.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return toDto(convocatoriaRepository.save(entity));
    }

    public void delete(Long id) {
        convocatoriaRepository.deleteById(id);
    }

    public ConvocatoriaPasantiaDTO cambiarEstado(Long id, String estado) {
        ConvocatoriaPasantia entity = convocatoriaRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Convocatoria no encontrada", ENTITY_NAME, "noexiste"));
        entity.setEstado(EnumEstadoConvocatoria.valueOf(estado));
        return toDto(convocatoriaRepository.save(entity));
    }

    public PostulacionPasantiaDTO postular(Long convocatoriaId, Long proyectoId, String observacion) {
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccessDeniedException("Usuario no autenticado"));
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));
        if (proyectoId == null) {
            throw new BadRequestAlertException("El proyecto es obligatorio para postularse", ENTITY_NAME, "proyectorequerido");
        }
        ConvocatoriaPasantia convocatoria = convocatoriaRepository
            .findById(convocatoriaId)
            .orElseThrow(() -> new BadRequestAlertException("Convocatoria no encontrada", ENTITY_NAME, "noexiste"));
        if (Boolean.FALSE.equals(convocatoria.getActivo())) {
            throw new BadRequestAlertException("La convocatoria no esta activa", ENTITY_NAME, "convocatoriainactiva");
        }
        if (convocatoria.getEstado() != EnumEstadoConvocatoria.ABIERTA) {
            throw new BadRequestAlertException("La convocatoria no esta abierta", ENTITY_NAME, "convocatoriaCerrada");
        }
        if (convocatoria.getFechaApertura() != null && convocatoria.getFechaApertura().isAfter(LocalDate.now())) {
            throw new BadRequestAlertException("La convocatoria aun no ha abierto", ENTITY_NAME, "convocatorianoabierta");
        }
        if (convocatoria.getFechaCierre() != null && convocatoria.getFechaCierre().isBefore(LocalDate.now())) {
            throw new BadRequestAlertException("La convocatoria ya cerro", ENTITY_NAME, "convocatoriacensurada");
        }
        Proyecto proyecto = proyectoRepository
            .findById(proyectoId)
            .orElseThrow(() -> new BadRequestAlertException("Proyecto no encontrado", ENTITY_NAME, "proyectonoexiste"));
        Long modalidadProyecto = proyecto.getProyectoModalidad() != null ? proyecto.getProyectoModalidad().getId() : null;
        if (modalidadProyecto == null || !Set.of(9001L, 9002L, 9003L).contains(modalidadProyecto)) {
            throw new BadRequestAlertException("El proyecto no es una pasantia", ENTITY_NAME, "modalidadinvalida");
        }
        if (convocatoria.getModalidadId() != null && !convocatoria.getModalidadId().equals(modalidadProyecto)) {
            throw new BadRequestAlertException("La convocatoria no corresponde a la modalidad del proyecto", ENTITY_NAME, "modalidadinvalida");
        }
        boolean esEstudiante;
        try {
            esEstudiante = integranteProyectoService
                .findByIntegranteProyectoProyectoId(proyectoId)
                .stream()
                .anyMatch(i -> "Estudiante".equals(i.getIntegranteProyectoRolesModalidadRol()) && login.equals(i.getIntegranteProyectoUserLogin()));
        } catch (Exception e) {
            throw new BadRequestAlertException("No se pudo verificar el integrante del proyecto", ENTITY_NAME, "errorintegrantes");
        }
        if (!esEstudiante) {
            throw new AccessDeniedException("Solo un estudiante del proyecto puede postularse");
        }
        if (postulacionRepository.findByConvocatoriaIdAndProyectoId(convocatoriaId, proyectoId).isPresent()) {
            throw new BadRequestAlertException("Ya existe una postulacion para este proyecto en la convocatoria", ENTITY_NAME, "postulacionduplicada");
        }
        if (convocatoria.getCupos() != null && convocatoria.getCupos() > 0) {
            long seleccionados = postulacionRepository.countByConvocatoriaIdAndEstado(convocatoriaId, EnumEstadoPostulacion.SELECCIONADO);
            if (seleccionados >= convocatoria.getCupos()) {
                throw new BadRequestAlertException("La convocatoria no tiene cupos disponibles", ENTITY_NAME, "sincupos");
            }
        }

        PostulacionPasantia postulacion = new PostulacionPasantia();
        postulacion.setConvocatoria(convocatoria);
        postulacion.setProyecto(proyecto);
        postulacion.setUsuarioId(user.getId());
        postulacion.setEstado(EnumEstadoPostulacion.PENDIENTE);
        postulacion.setFechaPostulacion(LocalDate.now());
        postulacion.setObservacion(observacion);
        postulacion = postulacionRepository.save(postulacion);

        notificarCiecyt(
            convocatoria,
            "Nueva postulacion a convocatoria",
            String.format(
                "El estudiante %s se postulo a la convocatoria '%s' con el proyecto '%s'.",
                login,
                convocatoria.getTitulo(),
                proyecto.getTitulo()
            ),
            proyecto
        );
        return toDto(postulacion);
    }

    @Transactional(readOnly = true)
    public List<PostulacionPasantiaDTO> findPostulaciones(Long convocatoriaId) {
        return postulacionRepository.findByConvocatoriaIdOrderByFechaPostulacionDesc(convocatoriaId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostulacionPasantiaDTO> findMisPostulaciones() {
        String login = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccessDeniedException("Usuario no autenticado"));
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new AccessDeniedException("Usuario no encontrado"));
        return postulacionRepository.findByUsuarioIdOrderByFechaPostulacionDesc(user.getId()).stream().map(this::toDto).collect(Collectors.toList());
    }

    public PostulacionPasantiaDTO seleccionar(Long postulacionId) {
        PostulacionPasantia postulacion = postulacionRepository
            .findById(postulacionId)
            .orElseThrow(() -> new BadRequestAlertException("Postulacion no encontrada", ENTITY_NAME, "noexiste"));
        if (postulacion.getEstado() != EnumEstadoPostulacion.PENDIENTE) {
            throw new BadRequestAlertException("Solo se pueden seleccionar postulaciones pendientes", ENTITY_NAME, "postulacionnoPendiente");
        }
        ConvocatoriaPasantia convocatoria = postulacion.getConvocatoria();
        if (convocatoria.getCupos() != null && convocatoria.getCupos() > 0) {
            long seleccionados = postulacionRepository.countByConvocatoriaIdAndEstado(
                convocatoria.getId(),
                EnumEstadoPostulacion.SELECCIONADO
            );
            if (seleccionados >= convocatoria.getCupos()) {
                throw new BadRequestAlertException("La convocatoria no tiene cupos disponibles", ENTITY_NAME, "sincupos");
            }
        }
        postulacion.setEstado(EnumEstadoPostulacion.SELECCIONADO);
        postulacion.setFechaDecision(LocalDate.now());
        postulacion = postulacionRepository.save(postulacion);
        notificarEstudiante(postulacion, "Postulacion seleccionada", "Su postulacion fue seleccionada por el CIECYT.");
        cerrarSiSinCupos(postulacion.getConvocatoria());
        return toDto(postulacion);
    }

    public PostulacionPasantiaDTO rechazar(Long postulacionId, String observacion) {
        PostulacionPasantia postulacion = postulacionRepository
            .findById(postulacionId)
            .orElseThrow(() -> new BadRequestAlertException("Postulacion no encontrada", ENTITY_NAME, "noexiste"));
        if (postulacion.getEstado() != EnumEstadoPostulacion.PENDIENTE) {
            throw new BadRequestAlertException("Solo se pueden rechazar postulaciones pendientes", ENTITY_NAME, "postulacionnoPendiente");
        }
        postulacion.setEstado(EnumEstadoPostulacion.RECHAZADO);
        postulacion.setObservacion(observacion);
        postulacion.setFechaDecision(LocalDate.now());
        postulacion = postulacionRepository.save(postulacion);
        notificarEstudiante(
            postulacion,
            "Postulacion rechazada",
            observacion != null && !observacion.trim().isEmpty()
                ? "Su postulacion fue rechazada: " + observacion
                : "Su postulacion fue rechazada por el CIECYT."
        );
        return toDto(postulacion);
    }

    @Transactional(readOnly = true)
    public boolean tienePostulacionSeleccionada(Long proyectoId) {
        return proyectoId != null && postulacionRepository.existsByProyectoIdAndEstado(proyectoId, EnumEstadoPostulacion.SELECCIONADO);
    }

    private void cerrarSiSinCupos(ConvocatoriaPasantia convocatoria) {
        if (convocatoria.getCupos() == null || convocatoria.getCupos() <= 0) {
            return;
        }
        long seleccionados = postulacionRepository.countByConvocatoriaIdAndEstado(convocatoria.getId(), EnumEstadoPostulacion.SELECCIONADO);
        if (seleccionados >= convocatoria.getCupos()) {
            convocatoria.setEstado(EnumEstadoConvocatoria.CERRADA);
            convocatoriaRepository.save(convocatoria);
        }
    }

    private void notificarCiecyt(ConvocatoriaPasantia convocatoria, String titulo, String mensaje, Proyecto proyecto) {
        for (User user : userRepository.findAllByAuthoritiesName(AuthoritiesConstants.CIECYT)) {
            notificacionService.crearNotificacion(user, titulo, mensaje, proyecto, "CONVOCATORIA");
        }
    }

    private void notificarEstudiante(PostulacionPasantia postulacion, String titulo, String mensaje) {
        userRepository
            .findById(postulacion.getUsuarioId())
            .ifPresent(user -> notificacionService.crearNotificacion(user, titulo, mensaje, postulacion.getProyecto(), "CONVOCATORIA"));
    }

    private ConvocatoriaPasantiaDTO toDto(ConvocatoriaPasantia entity) {
        ConvocatoriaPasantiaDTO dto = new ConvocatoriaPasantiaDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setModalidadId(entity.getModalidadId());
        dto.setEntidad(entity.getEntidad());
        dto.setConvenio(entity.getConvenio());
        dto.setCupos(entity.getCupos());
        dto.setFechaApertura(entity.getFechaApertura());
        dto.setFechaCierre(entity.getFechaCierre());
        dto.setRequisitos(entity.getRequisitos());
        dto.setCriteriosSeleccion(entity.getCriteriosSeleccion());
        dto.setEstado(entity.getEstado() != null ? entity.getEstado().name() : null);
        dto.setActivo(entity.getActivo());
        return dto;
    }

    private PostulacionPasantiaDTO toDto(PostulacionPasantia entity) {
        PostulacionPasantiaDTO dto = new PostulacionPasantiaDTO();
        dto.setId(entity.getId());
        dto.setConvocatoriaId(entity.getConvocatoria() != null ? entity.getConvocatoria().getId() : null);
        dto.setConvocatoriaTitulo(entity.getConvocatoria() != null ? entity.getConvocatoria().getTitulo() : null);
        dto.setProyectoId(entity.getProyecto() != null ? entity.getProyecto().getId() : null);
        dto.setProyectoTitulo(entity.getProyecto() != null ? entity.getProyecto().getTitulo() : null);
        dto.setUsuarioId(entity.getUsuarioId());
        User usuario = entity.getUsuarioId() != null ? userRepository.findById(entity.getUsuarioId()).orElse(null) : null;
        if (usuario != null) {
            dto.setUsuarioLogin(usuario.getLogin());
            String nombre = (
                (usuario.getFirstName() != null ? usuario.getFirstName() : "") +
                " " +
                (usuario.getLastName() != null ? usuario.getLastName() : "")
            )
                .trim()
                .replaceAll("\\s+", " ");
            dto.setUsuarioNombre(nombre.isEmpty() ? usuario.getLogin() : nombre);
        }
        dto.setEstado(entity.getEstado() != null ? entity.getEstado().name() : null);
        dto.setFechaPostulacion(entity.getFechaPostulacion());
        dto.setObservacion(entity.getObservacion());
        dto.setFechaDecision(entity.getFechaDecision());
        return dto;
    }
}
