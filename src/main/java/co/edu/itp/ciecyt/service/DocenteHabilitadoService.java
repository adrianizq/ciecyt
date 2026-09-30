package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.Authority;
import co.edu.itp.ciecyt.domain.DocenteHabilitado;
import co.edu.itp.ciecyt.domain.Facultad;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.repository.DocenteHabilitadoRepository;
import co.edu.itp.ciecyt.repository.FacultadRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Padron de profesores habilitados por facultad para ser asesor o jurado.
 *
 * <p>El acuerdo obliga a que la designacion salga de una lista: el articulo 8 designa al asesor de
 * tesis "a partir de la lista de profesores habilitados por la decanatura" y el paragrafo 2
 * obliga a remitirla a CIECYT al inicio de cada periodo. Aqui vive esa lista.
 *
 * <p>La lista cambia con los semestres: entran docentes nuevos y vuelven los que habian salido.
 * Por eso nada se borra. Cuando alguien deja de estar habilitado se cierra su registro con
 * fechaHasta, y si vuelve a entrar se abre uno nuevo, de modo que se puede responder cuando estuvo
 * habilitada cada persona. Volver a habilitar a alguien que ya esta vigente no duplica el
 * registro, porque el indice ux_docente_habilitado_vigente no lo permitiria y el alta es idempotente.
 */
@Service
@Transactional
public class DocenteHabilitadoService {

    private final Logger log = LoggerFactory.getLogger(DocenteHabilitadoService.class);

    private final DocenteHabilitadoRepository docenteHabilitadoRepository;
    private final UserRepository userRepository;
    private final FacultadRepository facultadRepository;

    public DocenteHabilitadoService(
        DocenteHabilitadoRepository docenteHabilitadoRepository,
        UserRepository userRepository,
        FacultadRepository facultadRepository
    ) {
        this.docenteHabilitadoRepository = docenteHabilitadoRepository;
        this.userRepository = userRepository;
        this.facultadRepository = facultadRepository;
    }

    @Transactional(readOnly = true)
    public List<DocenteHabilitado> vigentesPorFacultad(Long facultadId) {
        return docenteHabilitadoRepository.findVigentesPorFacultad(facultadId);
    }

    @Transactional(readOnly = true)
    public List<DocenteHabilitado> vigentesPorFacultadYRol(Long facultadId, String rol) {
        return docenteHabilitadoRepository.findVigentesPorFacultadYRol(facultadId, normalizarRol(rol));
    }

    @Transactional(readOnly = true)
    public List<DocenteHabilitado> historialEnFacultad(Long userId, Long facultadId) {
        return docenteHabilitadoRepository.findHistorialDeUsuarioEnFacultad(userId, facultadId);
    }

    /**
     * Si la persona esta habilitada hoy para ese rol en esa facultad. Es la pregunta que se hace
     * al designar un asesor o un jurado.
     */
    @Transactional(readOnly = true)
    public boolean esVigente(Long userId, Long facultadId, String rol) {
        if (userId == null || facultadId == null || rol == null) {
            return false;
        }
        return docenteHabilitadoRepository.esVigente(userId, facultadId, normalizarRol(rol));
    }

    /**
     * Habilita a un docente para un rol en una facultad.
     *
     * <p>Se comprueba que la persona tenga el rol en el sistema, porque de lo contrario se
     * estaria habilitando a alguien que no podra exertir el cargo y la designacion fallaria mas
     * tarde, con el proyecto de por medio.
     */
      /**
       * Habilita a un docente que la decanatura identifica por su cedula o login.
       *
       * <p>La decanatura conoce al docente por su cedula, no por su id interno, y resolverlo aqui
       * evita que el navegador tenga que consultar el directorio de usuarios para poder mantener la
       * lista.
       */
      public DocenteHabilitado habilitarPorLogin(String login, Long facultadId, String rol, String actoResolucion, String observaciones) {
          if (login == null || login.trim().isEmpty()) {
              throw new BadRequestAlertException("Indique la cedula o el login del docente", "docenteHabilitado", "loginVacio");
          }
          User user = userRepository.findOneWithAuthoritiesByLogin(login.trim())
              .orElseThrow(() -> new BadRequestAlertException(
                  "No hay ningun usuario con la cedula o login " + login.trim(), "docenteHabilitado", "usuarioInexistente"));
          return habilitar(user.getId(), facultadId, rol, null, actoResolucion, observaciones);
      }

      public DocenteHabilitado habilitar(Long userId, Long facultadId, String rol, LocalDate fechaDesde, String actoResolucion, String observaciones) {
        String rolNormalizado = normalizarRol(rol);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BadRequestAlertException("El docente indicado no existe", "docenteHabilitado", "usuarioInexistente"));
        Facultad facultad = facultadRepository.findById(facultadId)
            .orElseThrow(() -> new BadRequestAlertException("La facultad indicada no existe", "docenteHabilitado", "facultadInexistente"));

        String autoridad = DocenteHabilitado.ROL_JURADO.equals(rolNormalizado) ? "ROLE_JURADO" : "ROLE_ASESOR";
        if (!tieneAutoridad(user, autoridad)) {
            throw new BadRequestAlertException(
                "La persona no tiene el rol " + rolNormalizado + " (" + autoridad + ") en el sistema",
                "docenteHabilitado", "sinRol");
        }

        // Si ya esta vigente no se abre un segundo registro: el indice no lo admite y la intencion
        // de la decanatura ya esta cumplida.
        List<DocenteHabilitado> vigente = docenteHabilitadoRepository.findVigente(userId, facultadId, rolNormalizado);
        if (!vigente.isEmpty()) {
            log.debug("El docente {} ya estaba habilitado como {} en la facultad {}", userId, rolNormalizado, facultadId);
            return vigente.get(0);
        }

        DocenteHabilitado docenteHabilitado = new DocenteHabilitado();
        docenteHabilitado.setUser(user);
        docenteHabilitado.setFacultad(facultad);
        docenteHabilitado.setRol(rolNormalizado);
        docenteHabilitado.setFechaDesde(fechaDesde != null ? fechaDesde : LocalDate.now());
        docenteHabilitado.setActoResolucion(actoResolucion);
        docenteHabilitado.setObservaciones(observaciones);
        return docenteHabilitadoRepository.save(docenteHabilitado);
    }

    /**
     * Cierra la vigencia de un docente habilitado. No se borra el registro: queda la fecha hasta
     * la que estuvo y, si vuelve a entrar, se abre uno nuevo.
     */
    public DocenteHabilitado cerrar(Long userId, Long facultadId, String rol, LocalDate fechaHasta) {
        String rolNormalizado = normalizarRol(rol);
        List<DocenteHabilitado> vigente = docenteHabilitadoRepository.findVigente(userId, facultadId, rolNormalizado);
        if (vigente.isEmpty()) {
            throw new BadRequestAlertException("Ese docente no está habilitado como " + rolNormalizado + " en esa facultad", "docenteHabilitado", "noHabilitado");
        }
        DocenteHabilitado docenteHabilitado = vigente.get(0);
        LocalDate cierre = fechaHasta != null ? fechaHasta : LocalDate.now();
        // Cerrar no puede dejar un registro con la misma fecha de inicio y de cierre.
        if (docenteHabilitado.getFechaDesde() != null && !cierre.isAfter(docenteHabilitado.getFechaDesde())) {
            cierre = docenteHabilitado.getFechaDesde().plusDays(1);
        }
        docenteHabilitado.setFechaHasta(cierre);
        return docenteHabilitadoRepository.save(docenteHabilitado);
    }

    private boolean tieneAutoridad(User user, String autoridad) {
        if (user.getAuthorities() == null) {
            return false;
        }
        for (Authority authority : user.getAuthorities()) {
            if (authority != null && autoridad.equals(authority.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Acepta el rol en mayusculas o minusculas y con espacios, y solo admite ASESOR o JURADO.
     */
    private String normalizarRol(String rol) {
        if (rol == null || rol.trim().isEmpty()) {
            throw new BadRequestAlertException("No se indicó si es asesor o jurado", "docenteHabilitado", "rolVacio");
        }
        String normalizado = rol.trim().toUpperCase();
        if (!DocenteHabilitado.ROL_ASESOR.equals(normalizado) && !DocenteHabilitado.ROL_JURADO.equals(normalizado)) {
            throw new BadRequestAlertException("El rol solo puede ser ASESOR o JURADO", "docenteHabilitado", "rolInvalido");
        }
        return normalizado;
    }

    @Transactional(readOnly = true)
    public Optional<DocenteHabilitado> findOne(Long id) {
        return docenteHabilitadoRepository.findById(id);
    }
}
