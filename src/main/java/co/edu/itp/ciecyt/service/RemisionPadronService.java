package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.DocenteHabilitado;
import co.edu.itp.ciecyt.domain.Facultad;
import co.edu.itp.ciecyt.domain.RemisionPadron;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.repository.DocenteHabilitadoRepository;
import co.edu.itp.ciecyt.repository.FacultadRepository;
import co.edu.itp.ciecyt.repository.RemisionPadronRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.security.SecurityUtils;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Remision de la relacion de profesores habilitados al CIECYT.
 *
 * <p>El padron al dia no cumple por si solo el paragrafo 2 del articulo 8: lo que el parrafo pide
 * es remitir la relacion al inicio de cada periodo. Por eso la remision se guarda como un hecho
 * fechado, con la lista que se remitio, y una vez enviada no se toca.
 */
@Service
public class RemisionPadronService {

    private final Logger log = LoggerFactory.getLogger(RemisionPadronService.class);

    private final RemisionPadronRepository remisionPadronRepository;
    private final DocenteHabilitadoRepository docenteHabilitadoRepository;
    private final FacultadRepository facultadRepository;
    private final UserRepository userRepository;

    public RemisionPadronService(
        RemisionPadronRepository remisionPadronRepository,
        DocenteHabilitadoRepository docenteHabilitadoRepository,
        FacultadRepository facultadRepository,
        UserRepository userRepository
    ) {
        this.remisionPadronRepository = remisionPadronRepository;
        this.docenteHabilitadoRepository = docenteHabilitadoRepository;
        this.facultadRepository = facultadRepository;
        this.userRepository = userRepository;
    }

    /**
     * Arma un borrador con la lista vigente de la facultad en este momento.
     *
     * <p>Se arma en servidor y no en el navegador a proposito: si el cliente enviara la lista, la
     * remision podria no reflejar el padron real. Aqui la copia sale de la misma tabla que se usa
     * para designar, asi que lo remitido y lo designable son la misma cosa.
     */
    @Transactional
    public RemisionPadron crearBorrador(Long facultadId, String periodo, String observaciones) {
        if (periodo == null || periodo.trim().isEmpty()) {
            throw new BadRequestAlertException("Indique el periodo academico al que corresponde", "remisionPadron", "periodoVacio");
        }
        Facultad facultad = facultadRepository.findById(facultadId)
            .orElseThrow(() -> new BadRequestAlertException("La facultad indicada no existe", "remisionPadron", "facultadInexistente"));

        User remitente = usuarioEnSesion();

        RemisionPadron remision = new RemisionPadron();
        remision.setFacultad(facultad);
        remision.setPeriodo(periodo);
        remision.setFechaRemision(LocalDateTime.now());
        remision.setRemitidoPor(remitente);
        remision.setEstado(RemisionPadron.ESTADO_BORRADOR);
        remision.setObservaciones(observaciones);

        List<DocenteHabilitado> vigentes = docenteHabilitadoRepository.findVigentesPorFacultad(facultadId);
        for (DocenteHabilitado d : vigentes) {
            User docente = d.getUser();
            remision.agregarDocente(docente, d.getRol(), nombreDe(docente), docente.getEmail());
        }

        log.debug("Borrador de remision creado para la facultad {} en {} con {} docentes",
            facultadId, periodo, remision.getDocentes().size());
        return remisionPadronRepository.save(remision);
    }

    /**
     * Envia el borrador. A partir de aqui la remision no se edita mas.
     *
     * <p>No se remite un padron vacio: es mas util enterarse de que no se han habilitado docentes
     * que dejar registrada una remision en cero, que luego es indistinguible de un forgot de la
     * decanatura.
     */
    @Transactional
    public RemisionPadron enviar(Long remisionId, Long facultadId) {
        RemisionPadron remision = remisionPadronRepository.findByIdAndFacultadId(remisionId, facultadId)
            .orElseThrow(() -> new BadRequestAlertException("La remision indicada no existe", "remisionPadron", "remisionInexistente"));

        if (remision.estaEnviada()) {
            throw new BadRequestAlertException("Esa remision ya fue enviada y no se modifica", "remisionPadron", "yaEnviada");
        }
        if (remision.getDocentes().isEmpty()) {
            throw new BadRequestAlertException(
                "No hay docentes habilitados en la facultad, asi que no hay relacion que remitir",
                "remisionPadron", "padronVacio");
        }
        // El indice unico de la base lo impediria igual, pero dejaria un 500 sin explicar nada.
        // Se comprueba antes para poder decir cual es el problema: el periodo ya fue remitido.
        List<RemisionPadron> yaEnviadas = remisionPadronRepository.findEnviadaDelPeriodo(facultadId, remision.getPeriodo());
        boolean hayOtraDelMismoPeriodo = yaEnviadas.stream().anyMatch(r -> !r.getId().equals(remisionId));
        if (hayOtraDelMismoPeriodo) {
            throw new BadRequestAlertException(
                "El periodo " + remision.getPeriodo() + " ya tiene una remision enviada. Si el padron cambio, la correccion va en la remision del siguiente periodo",
                "remisionPadron", "periodoYaRemitido");
        }

        remision.setEstado(RemisionPadron.ESTADO_ENVIADO);
        remision.setFechaEnvio(LocalDateTime.now());
        log.debug("Remision {} enviada con {} docentes", remisionId, remision.getDocentes().size());
        return remisionPadronRepository.save(remision);
    }

    @Transactional(readOnly = true)
    public List<RemisionPadron> findPorFacultad(Long facultadId) {
        return remisionPadronRepository.findPorFacultad(facultadId);
    }

    @Transactional(readOnly = true)
    public List<RemisionPadron> findEnviadasPorFacultad(Long facultadId) {
        return remisionPadronRepository.findEnviadasPorFacultad(facultadId);
    }

    @Transactional(readOnly = true)
    public List<RemisionPadron> findTodasEnviadas() {
        return remisionPadronRepository.findTodasEnviadas();
    }

    private String nombreDe(User u) {
        String nombre = ((u.getFirstName() == null ? "" : u.getFirstName()) + " " + (u.getLastName() == null ? "" : u.getLastName())).trim();
        return nombre.isEmpty() ? u.getLogin() : nombre;
    }

    private User usuarioEnSesion() {
        String login = SecurityUtils.getCurrentUserLogin()
            .orElseThrow(() -> new BadRequestAlertException("No hay usuario en sesion", "remisionPadron", "sinSesion"));
        return userRepository.findOneWithAuthoritiesByLogin(login)
            .orElseThrow(() -> new BadRequestAlertException("No se encontro el usuario en sesion", "remisionPadron", "usuarioInexistente"));
    }
}
