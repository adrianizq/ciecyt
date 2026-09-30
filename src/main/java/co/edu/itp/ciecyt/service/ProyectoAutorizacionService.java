package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.RequisitoProyecto;
import co.edu.itp.ciecyt.domain.TransicionEstado;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.repository.AdjuntoProyectoFaseRepository;
import co.edu.itp.ciecyt.repository.AdjuntoRetroalimentacionRepository;
import co.edu.itp.ciecyt.repository.IntegranteProyectoRepository;
import co.edu.itp.ciecyt.repository.RequisitoProyectoRepository;
import co.edu.itp.ciecyt.repository.TransicionEstadoRepository;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.security.AuthoritiesConstants;
import co.edu.itp.ciecyt.security.SecurityUtils;
import co.edu.itp.ciecyt.service.dto.RolesModalidadDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de autorizacion sobre los proyectos.
 *
 * Hasta ahora el unico filtro era que /api/** exige autenticacion, asi que cualquier usuario
 * autenticado podia leer y modificar proyectos de otros. El Acuerdo 025 reparte la
 * intervencion sobre un proyecto entre el estudiante, su asesor, el jurado, CIECYT y la
 * decanura de la facultad, asi que la pertenencia se resuelve con el integrante_proyecto del
 * usuario en sesion, que es el mismo dato que ya usaba el servicio de notificaciones.
 */
@Service
@Transactional
public class ProyectoAutorizacionService {

    private final Logger log = LoggerFactory.getLogger(ProyectoAutorizacionService.class);

    private final IntegranteProyectoRepository integranteProyectoRepository;

    private final UserRepository userRepository;

    private final AdjuntoProyectoFaseRepository adjuntoProyectoFaseRepository;

    private final AdjuntoRetroalimentacionRepository adjuntoRetroalimentacionRepository;

    private final TransicionEstadoRepository transicionEstadoRepository;

    private final RequisitoProyectoRepository requisitoProyectoRepository;

    private final ProyectoRepository proyectoRepository;

    private final DecanoFacultadService decanoFacultadService;

    private final RolesModalidadService rolesModalidadService;

    public ProyectoAutorizacionService(
        IntegranteProyectoRepository integranteProyectoRepository,
        UserRepository userRepository,
        AdjuntoProyectoFaseRepository adjuntoProyectoFaseRepository,
        AdjuntoRetroalimentacionRepository adjuntoRetroalimentacionRepository,
        TransicionEstadoRepository transicionEstadoRepository,
        RequisitoProyectoRepository requisitoProyectoRepository,
        ProyectoRepository proyectoRepository,
        DecanoFacultadService decanoFacultadService,
        RolesModalidadService rolesModalidadService
    ) {
        this.integranteProyectoRepository = integranteProyectoRepository;
        this.userRepository = userRepository;
        this.adjuntoProyectoFaseRepository = adjuntoProyectoFaseRepository;
        this.adjuntoRetroalimentacionRepository = adjuntoRetroalimentacionRepository;
        this.transicionEstadoRepository = transicionEstadoRepository;
        this.requisitoProyectoRepository = requisitoProyectoRepository;
        this.proyectoRepository = proyectoRepository;
        this.decanoFacultadService = decanoFacultadService;
        this.rolesModalidadService = rolesModalidadService;
    }

    /**
     * True si el usuario en sesion tiene competencia sobre cualquier proyecto: administracion
     * o CIECYT.
     */
    @Transactional(readOnly = true)
    public boolean esGestorGlobal() {
        return SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)
            || SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.CIECYT);
    }

    /**
     * True si el usuario en sesion es administrador de la aplicacion.
     *
     * <p>Distinto de {@link #esGestorGlobal()}: el CIECYT dirige el proceso y por eso puede ver y
     * resolver cualquier proyecto, pero eso no le da competencia sobre decisiones que el reglamento
     * le carga a una autoridad concreta. Usar el override de propiedad como si fuera competency
     * sobre el padron de la facultad abriria la lista de habilitados a quien solo tiene la de leer.
     */
    @Transactional(readOnly = true)
    public boolean esAdministrador() {
        return SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN);
    }

    /**
     * Proyectos en los que el usuario en sesion tiene algun rol registrado (estudiante, autor,
     * asesor, co-asesor o jurado).
     */
    @Transactional(readOnly = true)
    public List<IntegranteProyecto> listarIntegrantesDelUsuarioActual() {
        try {
            List<IntegranteProyecto> lista = integranteProyectoRepository.findByIntegranteProyectoUserIsCurrentUser();
            return lista != null ? lista : Collections.<IntegranteProyecto>emptyList();
        } catch (Exception e) {
            log.warn("No se pudieron obtener los proyectos del usuario actual: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private Long idProyectoDe(IntegranteProyecto integrante) {
        if (integrante == null || integrante.getIntegranteProyectoProyecto() == null) {
            return null;
        }
        return integrante.getIntegranteProyectoProyecto().getId();
    }

    private List<Proyecto> proyectosDelUsuarioActual() {
        List<Proyecto> proyectos = new ArrayList<>();
        for (IntegranteProyecto integrante : listarIntegrantesDelUsuarioActual()) {
            if (integrante.getIntegranteProyectoProyecto() != null) {
                proyectos.add(integrante.getIntegranteProyectoProyecto());
            }
        }
        return proyectos;
    }

    /**
     * True si el usuario en sesion esta asociado al proyecto con algun rol.
     */
    @Transactional(readOnly = true)
    public boolean esParticipante(Proyecto proyecto) {
        if (proyecto == null || proyecto.getId() == null) {
            return false;
        }
        for (IntegranteProyecto integrante : listarIntegrantesDelUsuarioActual()) {
            if (Objects.equals(idProyectoDe(integrante), proyecto.getId())) {
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean esParticipante(Long proyectoId) {
        if (proyectoId == null) {
            return false;
        }
        for (IntegranteProyecto integrante : listarIntegrantesDelUsuarioActual()) {
            if (Objects.equals(idProyectoDe(integrante), proyectoId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * True si el usuario tiene un rol concreto sobre el proyecto.
     */
    @Transactional(readOnly = true)
    public boolean tieneRolEnProyecto(Proyecto proyecto, String rol) {
        if (proyecto == null || proyecto.getId() == null || rol == null) {
            return false;
        }
        for (IntegranteProyecto integrante : listarIntegrantesDelUsuarioActual()) {
            if (Objects.equals(idProyectoDe(integrante), proyecto.getId())
                && integrante.getIntegranteProyectoRolesModalidad() != null
                && rol.equalsIgnoreCase(integrante.getIntegranteProyectoRolesModalidad().getRol())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lectura del proyecto: La ve el actor global (CIECYT o administracion), cualquier
     * participante del proyecto y el decano de la facultad a la que pertenece.
     *
     * <p>El alcance de la decanatura se resuelve contra la asignacion vigente en
     * decano_facultad y el proyecto.facultad_id, no contra una lista de facultades en el codigo:
     * por eso dos decanos de facultades distintas no se ven entre si y agregar una facultad es
     * un dato, no un cambio de codigo.
     */
    @Transactional(readOnly = true)
    public boolean puedeLeer(Proyecto proyecto) {
        return esGestorGlobal() || esParticipante(proyecto) || esDecanoDeProyecto(proyecto);
    }

    @Transactional(readOnly = true)
    public boolean puedeLeer(Long proyectoId) {
        return esGestorGlobal() || esParticipante(proyectoId) || esDecanoDeProyecto(proyectoId);
    }

    /**
     * Lectura del contenido de las evaluaciones: respuestas y retroalimentaciones.
     *
     * <p>Es un limite mas estrecho que puedeLeer y no es una repeticion. El decano ve el
     * proyecto porque el reglamento lo hace designar al asesor, viabilizar la pasantia y
     * estudiar las solicitudes de novedades, y para eso necesita verlo. Lo que el reglamento no
     * le da es el concepto: la evaluacion esta a cargo del asesor y el jurado, y del decano se
     * dice que participa en el acto, no que lo evalua. Por eso al decano se le permite ver que
     * el proyecto existe y en que estado va, pero no lo que el asesor o el jurado escribieron.
     *
     * <p>El orden importa: el que es ademas asesor o jurado del proyecto sigue viendo las
     * evaluaciones, pero por ser participante, no por ser decano.
     */
    @Transactional(readOnly = true)
    public boolean puedeLeerEvaluacionesDe(Proyecto proyecto) {
        return esGestorGlobal() || esParticipante(proyecto);
    }

    @Transactional(readOnly = true)
    public boolean puedeLeerEvaluacionesDe(Long proyectoId) {
        if (esGestorGlobal()) {
            return true;
        }
        if (proyectoId == null) {
            return false;
        }
        return proyectoRepository
            .findById(proyectoId)
            .map(this::puedeLeerEvaluacionesDe)
            .orElse(Boolean.FALSE);
    }

    /**
     * El usuario en sesion es el decano vigente de la facultad del proyecto. No se concede por
     * tener el rol: se compara la persona asignada, de modo que un decano de otra facultad
     * queda fuera aunque tenga ROLE_DECANO.
     */
    @Transactional(readOnly = true)
    public boolean esDecanoDeProyecto(Proyecto proyecto) {
        return decanoFacultadService.estaEnAlcanceDeDecanoActual(proyecto);
    }

    @Transactional(readOnly = true)
    public boolean esDecanoDeProyecto(Long proyectoId) {
        if (proyectoId == null) {
            return false;
        }
        return proyectoRepository
            .findById(proyectoId)
            .map(this::esDecanoDeProyecto)
            .orElse(Boolean.FALSE);
    }

    /**
     * Lectura de las respuestas de evaluacion de un proyecto. Reutiliza la regla de lectura del
     * proyecto: el estudiante ve los comentarios de su asesor y de su jurado sobre su propia
     * propuesta, que es lo que las pantallas de retroalimentacion necesitan, y no los de
     * nadie mas.
     */
    @Transactional(readOnly = true)
    public boolean puedeLeerRespuestasDe(Long proyectoId) {
        return puedeLeerEvaluacionesDe(proyectoId);
    }

    /**
     * El proyecto esta esperando aun a un rol concreto.
     *
     * <p>Es la senal de que ese rol todavia no ha cerrado su evaluacion: el boton Guardar
     * borrador de las pantallas de evaluacion escribe respuestas y el proyecto, pero no cambia
     * el estado, mientras que Enviar siempre lo mueve. Por eso el estado actual dice si lo que
     * hay guardado es un avance en curso o un concepto ya entregado, sin necesitar una columna
     * nueva ni depender de un campo que cada pantalla填空 por su cuenta.
     *
     * <p>Se contesta con la configuracion de transiciones, no con una lista de estados: se
     * pregunta al flujo que pasos salen del estado actual y si alguno lo reclama el rol.
     */
    @Transactional(readOnly = true)
    public boolean elFlujoEsperaAccionDeRol(Long proyectoId, String rol) {
        if (proyectoId == null || rol == null || rol.trim().isEmpty()) {
            return false;
        }
        Proyecto proyecto = proyectoRepository.findById(proyectoId).orElse(null);
        if (proyecto == null || proyecto.getEstado() == null || proyecto.getProyectoModalidad() == null) {
            return false;
        }
        for (TransicionEstado transicion : transicionEstadoRepository
            .findByTransicionEstadoModalidadIdAndActivoTrueAndEstadoOrigen(
                proyecto.getProyectoModalidad().getId(), proyecto.getEstado())) {
            String requerido = transicion.getRolRequerido();
            if (requerido != null && requerido.contains(rol.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Lectura de las respuestas que un rol Evaluatingio ha dejado en un proyecto.
     *
     * <p>El estudiante ve la evaluacion de su asesor y de su jurado, pero solo cuando ese rol ya
     * cerro su turno: mientras el flujo siga esperando al rol, lo que hay guardado es un borrador
     * que el propio advisor todavia esta corrigiendo. El rol que esta escribiendo, el gestor
     * global y el decano no quedan restringidos, porque no son el destinatario del concepto.
     */
    @Transactional(readOnly = true)
    public boolean puedeVerRespuestasDeRolEn(Long proyectoId, String authority) {
        if (esGestorGlobal()) {
            return true;
        }
        if (!puedeLeerRespuestasDe(proyectoId)) {
            return false;
        }
        if (authority == null || authority.trim().isEmpty() || "*".equals(authority.trim())) {
            return false;
        }
        String rol = authority.trim();
        if (SecurityUtils.isCurrentUserInRole(rol)) {
            return true;
        }
        return !elFlujoEsperaAccionDeRol(proyectoId, rol);
    }

    /**
     * Designar a los integrantes de evaluacion de un proyecto: el asesor y el jurado.
     *
     * <p>Quien puede no es el mismo en todas las opciones de grado, y el reglamento lo define por
     * modalidad: para la tesis de pregrado y posgrado la designacion la hace el CIECYT a partir de
     * la lista de profesores habilitados por la decanatura, y el estudiante puede escoger
     * libremente de esa lista a su asesor e informarlo al CIECYT para la designacion formal. Para
     * pasantias y diplomados la designacion la hace el decano de la facultad.
     *
     * <p>El rol designado no es un detalle: un participante puede intentar designar el jurado o
     * mover el cargo de otra modalidad, y eso no es lo que el reglamento deja escoger. Por eso el
     * permiso se consulta con el cargo concreto, y no con el simple hecho de pertenecer a un
     * proyecto.
     *
     * <p>El CIECYT y la administracion pueden designar cualquier cargo. Quien participa en el
     * proyecto solo puede anotar su eleccion de asesor en la modalidad de tesis. La decanatura solo
     * en las modalidades de pasantia y diplomado, y solo para cargos de asesoria o jurado: el
     * estudiante y el coordinador no son de su designacion.
     *
     * <p>Las modalidades se comparan por id, no por nombre, para que cambiar el nombre de una
     * modalidad no abra ni cierre el permiso por accidente.
     */
    @Transactional(readOnly = true)
    public boolean puedeDesignarIntegrantesDe(Long proyectoId, Long idRolModalidad) {
        // El CIECYT y la administracion dirigen el proceso: designan lo que sea.
        if (esGestorGlobal()) {
            return true;
        }
        if (proyectoId == null || idRolModalidad == null) {
            return false;
        }
        Proyecto proyecto = proyectoRepository.findById(proyectoId).orElse(null);
        if (proyecto == null) {
            return false;
        }
        Long modalidad = proyecto.getProyectoModalidad() != null
            ? proyecto.getProyectoModalidad().getId() : null;
        String autoridad = rolesModalidadService.findOne(idRolModalidad)
            .map(RolesModalidadDTO::getRolesModalidadAuthorityName)
            .orElse(null);
        if (autoridad == null || autoridad.trim().isEmpty()) {
            return false;
        }

        // La decanatura designa asesores y jurados, y solo en las modalidades que el reglamento
        // le deja (pasantias y diplomado).
        boolean rolDeDecanatura = "ROLE_ASESOR".equals(autoridad) || "ROLE_JURADO".equals(autoridad);
        if (esDecanoDeProyecto(proyecto)
            && rolDeDecanatura
            && MODALIDADES_DONDE_DESIGNA_LA_DECANATURA.contains(modalidad)) {
            return true;
        }

        // El estudiante escoge a su asesor y avisa al CIECYT para que la designe formalmente.
        // Escoger el jurado o hacerlo en otra modalidad no es eleccion del estudiante.
        if (esParticipante(proyecto)) {
            return Long.valueOf(MODALIDAD_TESIS).equals(modalidad) && "ROLE_ASESOR".equals(autoridad);
        }
        return false;
    }

    /**
     * Ver el padron de habilitados de una facultad. Lo pueden consultar quien dirige el proceso,
     * la propia decanatura (es la lista que debe remitir y de la que se designa) y los
     * participantes de los proyectos de esa facultad: el proponente escoge a su asesor de la
     * lista habilitada, y no se le expone el padron de facultades ajenas.
     */
    @Transactional(readOnly = true)
    public boolean puedeVerHabilitadosDe(Long facultadId) {
        if (facultadId == null) {
            return false;
        }
        return esGestorGlobal() || decanoFacultadService.esDecanoDeFacultad(facultadId)
            || esParticipanteDeProyectoEnFacultad(facultadId);
    }

    /**
     * True si el usuario en sesion tiene algun rol (estudiante/autor/asesor/jurado) en un
     * proyecto de la facultad indicada.
     */
    @Transactional(readOnly = true)
    public boolean esParticipanteDeProyectoEnFacultad(Long facultadId) {
        for (IntegranteProyecto integrante : listarIntegrantesDelUsuarioActual()) {
            Proyecto proyecto = integrante.getIntegranteProyectoProyecto();
            if (proyecto != null && proyecto.getFacultad() != null
                    && Objects.equals(proyecto.getFacultad().getId(), facultadId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Habilitar docentes de una facultad para ser asesor o jurado.
     *
     * <p>El padron es de la decanatura: el paragrafo 2 del articulo 8 le remite a la decanatura
     * la relacion de profesores habilitados, y el CIECYT designa de ahi. El CIECYT no modifica la
     * lista, la consulta. Se admite tambien a la administracion de la aplicacion, para poder
     * corregir un padron que una facultad no ha remitido, pero sigue sin admitirse a un decano de
     * otra facultad: el padron se compara con la facultad, no con el rol.
     */
    @Transactional(readOnly = true)
    public boolean puedeHabilitarDocentesEn(Long facultadId) {
        if (facultadId == null) {
            return false;
        }
        return esAdministrador() || decanoFacultadService.esDecanoDeFacultad(facultadId);
    }

    /**
     * Modalidades cuyo asesor designa la decanatura de la facultad: las tres pasantias y el
     * diplomado. Para tesis, publicacion de articulo y especializacion la designacion no
     * corresponde a la decanatura.
     */
    private static final Set<Long> MODALIDADES_DONDE_DESIGNA_LA_DECANATURA = new HashSet<Long>(
        Arrays.asList(9001L, 9002L, 9003L, 9005L));

    /**
     * Modalidad de tesis. Es la unica en la que el reglamento deja al estudiante escoger a su
     * asesor dentro de la lista habilitada, para comunicarlo al CIECYT y que se designe
     * formalmente. En el resto, escoger el asesor no es decision del estudiante.
     */
    private static final long MODALIDAD_TESIS = 9004L;

    /**
     * Operar el flujo de estados de un proyecto.
     *
     * <p>El control de rol de una transicion dice quien puede ejecutar cada paso, pero no de
     * que proyecto se trata: sin exigir relacion con el proyecto, cualquier asesor del sistema
     * podria aprobar el proyecto de otro y cualquier cuenta con un rol trivial avanzaria los
     * pasos que la configuracion deja abiertos con "*". Por eso se exige ser parte del proyecto
     * o gestor global.
     */
    @Transactional(readOnly = true)
    public boolean puedeOperarFlujoDe(Long proyectoId) {
        return esGestorGlobal() || (proyectoId != null && esParticipante(proyectoId));
    }

    /**
     * Escritura de retroalimentacion. La retroalimentacion es la devolucion del asesor o del
     * jurado al estudiante, asi que solo la escriben esos roles y solo sobre proyectos de los
     * que son parte. No se usa puedeEditar porque el asesor entrega la observacion mientras el
     * proyecto esta en elaboracion, fase en la que el pendiente es del estudiante.
     */
    @Transactional(readOnly = true)
    public boolean puedeEscribirRetroalimentacionEn(Long proyectoId) {
        if (esGestorGlobal()) {
            return true;
        }
        if (proyectoId == null || !esParticipante(proyectoId)) {
            return false;
        }
        return SecurityUtils.isCurrentUserInRole("ROLE_ASESOR")
            || SecurityUtils.isCurrentUserInRole("ROLE_JURADO");
    }

    /**
     * Escritura de una respuesta de evaluacion firmada por un rol concreto.
     *
     * <p>Las respuestas se guardan con el rol que las produce (columna authority) y son el
     * soporte de la decision de viabilidad, asi que no puede firmarlas cualquiera: solo quien
     * participa del proyecto y tiene ese rol. Sin esta comprobacion un estudiante podia crear
     * a su nombre una evaluacion de ROLE_ASESOR o ROLE_JURADO, incluso en proyectos de otros.
     */
    @Transactional(readOnly = true)
    public boolean puedeEscribirRespuestaDe(Long proyectoId, String authority) {
        if (esGestorGlobal()) {
            return true;
        }
        if (proyectoId == null || !esParticipante(proyectoId)) {
            return false;
        }
        if (authority == null || authority.trim().isEmpty() || "*".equals(authority.trim())) {
            return false;
        }
        for (String rol : authority.split(",")) {
            if (!rol.trim().isEmpty() && SecurityUtils.isCurrentUserInRole(rol.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Escritura sobre el proyecto. Se permite a quien participa cuando el flujo de la modalidad
     * espera una accion suya: el estudiante mientras arma la propuesta y, mas adelante, el
     * asesor o el jurado mientras les toca evaluar. Asi el "guardar borrador" de las pantallas
     * de evaluacion sigue funcionando, y el estudiante no puede tocar el proyecto una vez que
     * la institucion empezo a revisarlo.
     */
    @Transactional(readOnly = true)
    public boolean puedeEditar(Proyecto proyecto) {
        if (esGestorGlobal()) {
            return true;
        }
        if (proyecto == null) {
            return false;
        }
        if (!esParticipante(proyecto)) {
            return false;
        }
        return elFlujoEsperaAccionDe(proyecto);
    }

    @Transactional(readOnly = true)
    public boolean puedeEditar(Long proyectoId) {
        if (esGestorGlobal()) {
            return true;
        }
        for (Proyecto proyecto : proyectosDelUsuarioActual()) {
            if (Objects.equals(proyecto.getId(), proyectoId)
                && elFlujoEsperaAccionDe(proyecto)) {
                return true;
            }
        }
        return false;
    }

    /**
     * True si desde el estado actual del proyecto el flujo de su modalidad espera una transicion
     * que le corresponda a alguno de los roles indicados.
     *
     * <p>La regla sale de la propia configuracion de transiciones, no de una lista de estados
     * escrita a mano: cada estado de revision tiene como unica salida util la del rol que
     * evalua (EN_REVISION_ASESOR solo sale por ROLE_ASESOR, y asi sucesivamente). Con esto el
     * asesor y el jurado pueden guardar el borrador de su evaluacion, y en cuanto la evaluacion
     * se envia el proyecto pasa a un estado cuyo pendiente ya no es de ellos.
     */
    @Transactional(readOnly = true)
    private boolean elFlujoEsperaAccionDe(Proyecto proyecto) {
        if (proyecto.getEstado() == null) {
            return true;
        }
        if (proyecto.getProyectoModalidad() == null || proyecto.getProyectoModalidad().getId() == null) {
            return false;
        }
        List<TransicionEstado> salientes = transicionEstadoRepository
            .findByTransicionEstadoModalidadIdAndActivoTrueAndEstadoOrigen(
                proyecto.getProyectoModalidad().getId(),
                proyecto.getEstado()
            );
        if (salientes.isEmpty()) {
            return false;
        }
        for (TransicionEstado transicion : salientes) {
            if (rolActualPermite(transicion.getRolRequerido())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Unico lugar donde se decide si el rol de la cuenta en sesion habilita una transicion.
     * Admite el comodin "*" y listas de roles separadas por coma, que es lo que la
     * configuracion usa hoy, y es la misma regla que aplica el servicio al ejecutar el cambio
     * de estado: asi no puede pasar que una transicion se considere permitida al ejecutarla y
     * proibida al autorizar la escritura.
     */
    public boolean rolActualPermite(String rolRequerido) {
        if (rolRequerido == null || rolRequerido.trim().isEmpty() || "*".equals(rolRequerido.trim())) {
            return true;
        }
        for (String rol : rolRequerido.split(",")) {
            if (!rol.trim().isEmpty() && SecurityUtils.isCurrentUserInRole(rol.trim())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Id de la cuenta en sesion. El frontend lo envia en los listados por rol, asi que esos
     * endpoints solo pueden responder para el propio usuario o para un gestor.
     */    @Transactional(readOnly = true)
    public Optional<Long> idUsuarioActual() {
        Optional<String> login = SecurityUtils.getCurrentUserLogin();
        if (!login.isPresent()) {
            return Optional.empty();
        }
        return userRepository
            .findOneByLogin(login.get())
            .map(user -> user.getId());
    }

    /**
     * True si el usuario consultado es el propio usuario en sesion o si quien pregunta tiene
     * competencia sobre todos los proyectos. Sin esto, cualquier autenticado podia pedir los
     * proyectos de otro usuario por su id.
     */
    @Transactional(readOnly = true)
    public boolean esUsuarioActualOGestor(Long idUsuario) {
        if (esGestorGlobal()) {
            return true;
        }
        return idUsuarioActual().map(id -> Objects.equals(id, idUsuario)).orElse(false);
    }

    /**
     * Autorizacion sobre un adjunto de proyecto. Los adjuntos no guardan autor, pero si el
     * proyecto al que pertenecen, asi que la pregunta es si el usuario participa de ese
     * proyecto. Quien lo adjunto es siempre un participante: el estudiante, su asesor o el
     * jurado, y todos necesitan poder reemplazarlo antes de evaluacion.
     */
    @Transactional(readOnly = true)
    public boolean puedeModificarAdjuntoDe(Proyecto proyecto) {
        return esGestorGlobal() || esParticipante(proyecto);
    }

    @Transactional(readOnly = true)
    public boolean puedeModificarAdjuntoDe(Long proyectoId) {
        return esGestorGlobal() || esParticipante(proyectoId);
    }

    /**
     * Autorizacion para borrar o reemplazar un adjunto de una fase del proyecto. Se resuelve
     * por el proyecto al que pertenece el adjunto, porque el adjunto no guarda su autor.
     */
    @Transactional(readOnly = true)
    public boolean puedeModificarAdjuntoProyectoFase(Long adjuntoId) {
        if (esGestorGlobal()) {
            return true;
        }
        if (adjuntoId == null) {
            return false;
        }
        return adjuntoProyectoFaseRepository
            .findById(adjuntoId)
            .map(adjunto -> puedeModificarAdjuntoDe(adjunto.getProyectoFaseProyecto()))
            .orElse(false);
    }

    /**
     * Autorizacion para borrar o reemplazar un adjunto de retroalimentacion. Quien puede
     * opinar sobre el proyecto es precisamente quien adjunta la retroalimentacion, asi que la
     * regla es la misma: ser parte del proyecto o actuar con competencia global.
     */
    @Transactional(readOnly = true)
    public boolean puedeModificarAdjuntoRetroalimentacion(Long adjuntoId) {
        if (adjuntoId == null) {
            return false;
        }
        return adjuntoRetroalimentacionRepository
            .findById(adjuntoId)
            .map(adjunto -> puedeModificarAdjuntoDe(adjunto.getAdjuntoRetroalimentacionProyecto()))
            .orElse(false);
    }

    /**
     * Proyecto al que pertenece un requisito del proyecto.
     *
     * <p>El requisito tampoco guarda autor: el permiso se resuelve contra el proyecto, igual que
     * con los adjuntos. Devuelve vacio cuando el id no existe, para que las consultas de
     * autorizacion respondan false y no filtren una excepcion.
     */
    private Optional<Proyecto> proyectoDeRequisito(Long requisitoProyectoId) {
        if (requisitoProyectoId == null) {
            return Optional.empty();
        }
        return requisitoProyectoRepository
            .findById(requisitoProyectoId)
            .map(RequisitoProyecto::getRequisitoProyectoProyecto);
    }

    /**
     * Entrega de un requisito por parte del estudiante (art. 5 del Acuerdo 25).
     *
     * <p>La entrega es una escritura sobre el proyecto, asi que quien puede hacerla es quien ya
     * puede modificar sus adjuntos: un participante del proyecto o el actor global. No se abre
     * con puedeLeer: el decano ve el proyecto para designar y viabilizar, pero no radica
     * documentos de inscripcion.
     */
    @Transactional(readOnly = true)
    public boolean puedeEntregarRequisito(Long requisitoProyectoId) {
        return proyectoDeRequisito(requisitoProyectoId).map(this::puedeModificarAdjuntoDe).orElse(false);
    }

    /**
     * Lectura de un requisito del proyecto.
     *
     * <p>Misma regla que el resto del proyecto: el actor global, cualquier participante y el
     * decano de la facultad. La lista de requisitos acompana al expediente, no es informacion
     * reservada como las evaluaciones.
     */
    @Transactional(readOnly = true)
    public boolean puedeLeerRequisito(Long requisitoProyectoId) {
        return proyectoDeRequisito(requisitoProyectoId).map(this::puedeLeer).orElse(false);
    }
}
