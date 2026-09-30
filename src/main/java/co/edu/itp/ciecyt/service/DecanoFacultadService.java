package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.DecanoFacultad;
import co.edu.itp.ciecyt.domain.Facultad;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.repository.DecanoFacultadRepository;
import co.edu.itp.ciecyt.repository.FacultadRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.security.SecurityUtils;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing the scope of the decanos.
 *
 * El Acuerdo 025 reparte funciones entre CIECYT y las decanaturas, asi que la autorizacion no
 * puede resolverse solo con el rol: dos decanos de facultades distintas no pueden ver ni actuar
 * sobre los proyectos de la otra. El alcance se lee de la asignacion vigente en
 * decano_facultad, de modo que agregar una facultad o cambiar de decano es solo un dato.
 */
@Service
@Transactional
public class DecanoFacultadService {

    private final Logger log = LoggerFactory.getLogger(DecanoFacultadService.class);

    private final DecanoFacultadRepository decanoFacultadRepository;

    private final FacultadRepository facultadRepository;

    private final UserRepository userRepository;

    public DecanoFacultadService(
        DecanoFacultadRepository decanoFacultadRepository,
        FacultadRepository facultadRepository,
        UserRepository userRepository
    ) {
        this.decanoFacultadRepository = decanoFacultadRepository;
        this.facultadRepository = facultadRepository;
        this.userRepository = userRepository;
    }

    /**
     * Decano vigente de la facultad a la que pertenece el proyecto.
     */
    @Transactional(readOnly = true)
    public Optional<DecanoFacultad> findDecanoVigenteDeProyecto(Proyecto proyecto) {
        Long facultadId = proyecto != null && proyecto.getFacultad() != null ? proyecto.getFacultad().getId() : null;
        if (facultadId == null) {
            return Optional.empty();
        }
        return decanoFacultadRepository.findDecanoVigenteDeFacultad(facultadId);
    }

    /**
     * Indica si el usuario en sesion es el decano vigente de la facultad del proyecto.
     * No se concede por rol: se compara la persona asignada, por eso un decano de otra
     * facultad queda fuera aunque tenga el rol.
     */
    @Transactional(readOnly = true)
    public boolean esDecanoDeProyecto(Proyecto proyecto) {
        return esDecanoDeProyecto(proyecto, SecurityUtils.getCurrentUserLogin().orElse(null));
    }

    @Transactional(readOnly = true)
    public boolean esDecanoDeProyecto(Proyecto proyecto, String login) {
        if (proyecto == null || login == null || login.isEmpty()) {
            return false;
        }
        return findDecanoVigenteDeProyecto(proyecto)
            .filter(decano -> decano.getUser() != null)
            .filter(decano -> login.equalsIgnoreCase(decano.getUser().getLogin()))
            .isPresent();
    }

    /**
     * Indica si el usuario en sesion es el decano vigente de esa facultad. Se usa desde los
     * @PreAuthorize, por eso no recibe argumentos de la peticion.
     */
    @Transactional(readOnly = true)
    public boolean esDecanoDeFacultad(Long facultadId) {
        String login = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (facultadId == null || login == null || login.isEmpty()) {
            return false;
        }
        return decanoFacultadRepository
            .findDecanoVigenteDeFacultad(facultadId)
            .filter(decano -> decano.getUser() != null)
            .filter(decano -> login.equalsIgnoreCase(decano.getUser().getLogin()))
            .isPresent();
    }

    /**
     * Facultades sobre las que el usuario en sesion tiene alcance como decano o delegado.
     */
    @Transactional(readOnly = true)
    public List<Long> facultadIdsDelDecanoActual() {
        return facultadIdsDelDecano(SecurityUtils.getCurrentUserLogin().orElse(null));
    }

    @Transactional(readOnly = true)
    public List<Long> facultadIdsDelDecano(String login) {
        if (login == null || login.isEmpty()) {
            return Collections.emptyList();
        }
        return decanoFacultadRepository
            .findVigentesByLogin(login)
            .stream()
            .map(decano -> decano.getFacultad() != null ? decano.getFacultad().getId() : null)
            .filter(java.util.Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * True si el usuario en sesion tiene el rol ROLE_DECANO y ademas tiene alguna facultad
     * vigente asignada. Evita mostrar un menu de decania a alguien con el rol pero sin
     * facultad, que es el caso de una asignacion mal hecha o de un decano que ya no esta.
     */
    @Transactional(readOnly = true)
    public boolean esDecanoActivo() {
        return SecurityUtils.isCurrentUserInRole(co.edu.itp.ciecyt.security.AuthoritiesConstants.DECANO)
            && !facultadIdsDelDecanoActual().isEmpty();
    }

    /**
     * El proyecto cae dentro del alcance del usuario en sesion como decano.
     */
    @Transactional(readOnly = true)
    public boolean estaEnAlcanceDeDecanoActual(Proyecto proyecto) {
        if (proyecto == null || !esDecanoActivo()) {
            return false;
        }
        return esDecanoDeProyecto(proyecto);
    }

    /**
     * Solo lectura: el registro nuevo debe ser el unico decano vigente de la facultad y el
     * usuario debe existir. La unicidad la impone el indice parcial de la base, esta validacion
     * es para devolver un mensaje util en vez de una violacion de constraint.
     */
    public DecanoFacultad registrar(DecanoFacultad decanoFacultad) {
        if (decanoFacultad.getFacultad() == null || decanoFacultad.getFacultad().getId() == null) {
            throw new IllegalArgumentException("La facultad es obligatoria");
        }
        if (decanoFacultad.getUser() == null || decanoFacultad.getUser().getId() == null) {
            throw new IllegalArgumentException("El decano es obligatoria");
        }
        // Se resuelven desde la base y no se usan los objetos del cuerpo de la peticion: asi se
        // validan las referencias y la respuesta trae los datos completos de la facultad y del
        // usuario, no solo el id.
        Facultad facultad = facultadRepository
            .findById(decanoFacultad.getFacultad().getId())
            .orElseThrow(() -> new IllegalArgumentException("La facultad no existe"));
        User usuario = userRepository
            .findById(decanoFacultad.getUser().getId())
            .orElseThrow(() -> new IllegalArgumentException("El usuario decano no existe"));
        decanoFacultad.setFacultad(facultad);
        decanoFacultad.setUser(usuario);

        Optional<DecanoFacultad> vigente = decanoFacultadRepository.findDecanoVigenteDeFacultad(facultad.getId());
        if (vigente.isPresent() && !vigente.get().getId().equals(decanoFacultad.getId())) {
            throw new IllegalArgumentException(
                "La facultad ya tiene un decano vigente. Ciérrelo con fecha hasta antes de registrar otro."
            );
        }
        if (decanoFacultad.getCargo() == null || decanoFacultad.getCargo().isEmpty()) {
            decanoFacultad.setCargo("Decano");
        }
        if (decanoFacultad.getEsDelegado() == null) {
            decanoFacultad.setEsDelegado(Boolean.FALSE);
        }
        DecanoFacultad guardado = decanoFacultadRepository.save(decanoFacultad);
        log.debug("Decanatura registrada: {}", guardado);
        return guardado;
    }

    /**
     * Cierra la vigencia del decano actual de la facultad. Es el cambio de decano: el
     * historico queda y el nuevo entra con fecha_hasta nula.
     */
    public DecanoFacultad cerrarVigencia(Long facultadId, java.time.LocalDate fechaHasta) {
        DecanoFacultad vigente = decanoFacultadRepository
            .findDecanoVigenteDeFacultad(facultadId)
            .orElseThrow(() -> new IllegalArgumentException("La facultad no tiene decano vigente"));
        if (fechaHasta == null) {
            throw new IllegalArgumentException("La fecha de cierre es obligatoria");
        }
        vigente.setFechaHasta(fechaHasta);
        return decanoFacultadRepository.save(vigente);
    }

    @Transactional(readOnly = true)
    public List<DecanoFacultad> findAll() {
        return decanoFacultadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DecanoFacultad> findOne(Long id) {
        return decanoFacultadRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<DecanoFacultad> findByFacultad(Long facultadId) {
        return decanoFacultadRepository.findByFacultadId(facultadId);
    }

    @Transactional(readOnly = true)
    public List<DecanoFacultad> findByLogin(String login) {
        return decanoFacultadRepository.findVigentesByLogin(login);
    }

    public void delete(Long id) {
        decanoFacultadRepository.deleteById(id);
    }

    /**
     * Facultades que el usuario en sesion puede consultar como decano. Si no es decano
     * devuelve la lista vacia, para que un filtro no termine vacando la tabla.
     */
    @Transactional(readOnly = true)
    public List<Facultad> facultadesDeAlcance() {
        List<Long> ids = facultadIdsDelDecanoActual();
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return ids.stream()
            .map(facultadRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
}
