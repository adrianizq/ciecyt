package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.RolesModalidad;
import co.edu.itp.ciecyt.repository.IntegranteProyectoRepository;
import co.edu.itp.ciecyt.domain.Authority;
import co.edu.itp.ciecyt.domain.DocenteHabilitado;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.repository.UserInfoRepository;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.DocenteHabilitadoService;
import co.edu.itp.ciecyt.service.IntegranteProyectoService;
import co.edu.itp.ciecyt.service.RolesModalidadService;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.dto.RolesModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.IntegranteProyectoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IntegranteProyecto}.
 */
@Service
@Transactional
public class IntegranteProyectoServiceImpl implements IntegranteProyectoService {

    private final Logger log = LoggerFactory.getLogger(IntegranteProyectoServiceImpl.class);

    private final IntegranteProyectoRepository integranteProyectoRepository;

    private final IntegranteProyectoMapper integranteProyectoMapper;
    private final ProyectoRepository proyectoRepository;
    private final RolesModalidadService rolesModalidadService;

    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    private final DocenteHabilitadoService docenteHabilitadoService;

    public IntegranteProyectoServiceImpl(
        IntegranteProyectoRepository integranteProyectoRepository,
        IntegranteProyectoMapper integranteProyectoMapper,
        ProyectoRepository proyectoRepository,
        RolesModalidadService rolesModalidadService,
        UserRepository userRepository,
        UserInfoRepository userInfoRepository,
        DocenteHabilitadoService docenteHabilitadoService
    ) {
        this.integranteProyectoRepository = integranteProyectoRepository;
        this.integranteProyectoMapper = integranteProyectoMapper;
        this.proyectoRepository = proyectoRepository;
        this.rolesModalidadService = rolesModalidadService;
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.docenteHabilitadoService = docenteHabilitadoService;
    }

    /**
     * Convierte el integrante a DTO y le agrega la identificacion del usuario.
     *
     * <p>User no tiene la cedula ni el codigo ITP: estan en UserInfo, que comparte llave con User.
     * Sin esto el front no podria identificar al estudiante por cedula y nombres.
     *
     * @param integranteProyecto integrante a convertir.
     * @return el DTO con la identificacion del usuario cargada.
     */
    private IntegranteProyectoDTO toDto(IntegranteProyecto integranteProyecto) {
        IntegranteProyectoDTO dto = integranteProyectoMapper.toDto(integranteProyecto);
        if (dto != null && dto.getIntegranteProyectoUserId() != null) {
            userInfoRepository.findById(dto.getIntegranteProyectoUserId()).ifPresent(userInfo -> {
                dto.setIntegranteProyectoUserNuip(userInfo.getNuip());
                dto.setIntegranteProyectoUserCodigoItp(userInfo.getCodigoItp());
            });
        }
        return dto;
    }

    /**
     * Save a integranteProyecto.
     *
     * @param integranteProyectoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IntegranteProyectoDTO save(IntegranteProyectoDTO integranteProyectoDTO) {
        log.debug("Request to save IntegranteProyecto : {}", integranteProyectoDTO);
        validarDesignacion(integranteProyectoDTO);
        IntegranteProyecto integranteProyecto = integranteProyectoMapper.toEntity(integranteProyectoDTO);
        integranteProyecto = integranteProyectoRepository.save(integranteProyecto);
        return toDto(integranteProyecto);
    }

    /**
     * El asesor y el jurado se designan de la lista de profesores habilitados por la decanatura.
     *
     * <p>El articulo 8 lo dice para tesis y el 9 para los jurados, asi que el chequeo se hace en
     * los dos casos y con la misma fuente: el padron vigente de la facultad a la que pertenece el
     * proyecto, no una lista de favoritos.
     *
     * <p>El padron solo se exige cuando la designacion es nueva. Una designacion ya hecha no se
     * vuelve a revisar contra el padron porque el asesor y el jurado se mantienen hasta el final
     * del proceso: si alguien sale de la lista del semestre siguiente, su proyecto sigue siendo
     * suyo. Lo que si se revisa es el cambio de titular, porque en ese caso hay una designacion
     * nueva y para ella si se necesita estar habilitado.
     *
     * <p>Sin facultad no se puede cotejar contra el padron, porque la habilitacion es por
     * facultad, y no se deja pasar: primero se le asigna la facultad al proyecto.
     */
    private void validarPadronDeHabilitados(IntegranteProyectoDTO dto, Long idProyecto, String autoridad) {
        if (autoridad == null) {
            return;
        }
        String rol = "ROLE_JURADO".equals(autoridad)
            ? DocenteHabilitado.ROL_JURADO : DocenteHabilitado.ROL_ASESOR;
        if (DocenteHabilitado.ROL_ESTUDIANTE_MARKER.equals(autoridad)) {
            return;
        }

        if (dto.getId() != null) {
            IntegranteProyecto actual = integranteProyectoRepository.findById(dto.getId()).orElse(null);
            if (actual != null && actual.getIntegranteProyectoUser() != null
                && Objects.equals(actual.getIntegranteProyectoUser().getId(), dto.getIntegranteProyectoUserId())) {
                // Misma persona, misma designacion: no se vuelve a consultar el padron.
                return;
            }
        }

        Proyecto proyecto = proyectoRepository.findById(idProyecto).orElse(null);
        if (proyecto == null) {
            return;
        }
        Long idFacultad = proyecto.getFacultad() != null ? proyecto.getFacultad().getId() : null;
        if (idFacultad == null) {
            throw new BadRequestAlertException(
                "El proyecto no tiene facultad y sin ella no se puede consultar el padrón de habilitados",
                "integranteProyecto", "sinFacultad");
        }
        if (!docenteHabilitadoService.esVigente(dto.getIntegranteProyectoUserId(), idFacultad, rol)) {
            throw new BadRequestAlertException(
                "La persona no está habilitada como " + rol + " en la facultad del proyecto; "
                    + "la designación sale de la lista que remite la decanatura",
                "integranteProyecto", "noHabilitado");
        }
    }

    /**
     * Revisa que la designacion sea coherente antes de guardarla.
     *
     * <p>Sin esto, guardar un integrante es escribir sin condiciones el registro que decide quien
     * evalua el proyecto: se podria nombrar asesor a alguien que no tiene ese rol, repetir un
     * cargo que el reglamento limita a uno, o mover un integrante de un proyecto a otro.
     *
     * <p>Las tres comprobaciones se hacen sobre el proyecto al que pertenece el integrante, y en
     * una actualizacion se descuenta la propia fila que se esta editando, para que reasignar el
     * titular de un cargo que ya esta lleno no se cuente como un caso nuevo.
     */
    private void validarDesignacion(IntegranteProyectoDTO dto) {
        if (dto == null) {
            throw new BadRequestAlertException("No se recibio el integrante a designar", "integranteProyecto", "designacion");
        }
        if (dto.getIntegranteProyectoUserId() == null && dto.getIntegranteProyectoExternoId() == null) {
            throw new BadRequestAlertException("No se indico a quien se designa", "integranteProyecto", "designacion");
        }
        if (dto.getIntegranteProyectoRolesModalidadId() == null) {
            throw new BadRequestAlertException("No se indico el cargo a asignar", "integranteProyecto", "designacion");
        }

        Long idProyecto = dto.getIntegranteProyectoProyectoId();
        if (dto.getId() != null) {
            // Una actualizacion con un id que no existe se estaria covertiendo en un alta: se
            // rechaza para que no se invente una designacion por la via del PUT.
            IntegranteProyecto actual = integranteProyectoRepository.findById(dto.getId())
                .orElseThrow(() -> new BadRequestAlertException(
                    "El integrante indicado no existe", "integranteProyecto", "designacion"));
            if (actual.getIntegranteProyectoProyecto() != null
                && !Objects.equals(actual.getIntegranteProyectoProyecto().getId(), idProyecto)) {
                throw new BadRequestAlertException(
                    "No se puede mover un integrante a otro proyecto: desvinculelo del anterior", "integranteProyecto", "designacion");
            }
        }
        if (idProyecto == null) {
            return;
        }

        RolesModalidadDTO rol = rolesModalidadService
            .findOne(dto.getIntegranteProyectoRolesModalidadId())
            .orElseThrow(() -> new BadRequestAlertException("El cargo indicado no existe", "integranteProyecto", "designacion"));
        String autoridad = rol.getRolesModalidadAuthorityName();

        // La persona designada tiene que tener el rol que se le esta asignando. Eso aplica al
        // usuario institucional; el profesional externo no tiene cuenta y su idoneidad para el
        // cargo la verifico el CIECYT (la valida el recurso antes de llegar aqui).
        if (autoridad != null && !autoridad.trim().isEmpty() && dto.getIntegranteProyectoUserId() != null) {
            User usuario = userRepository.findOneWithAuthoritiesById(dto.getIntegranteProyectoUserId())
                .orElseThrow(() -> new BadRequestAlertException("El usuario indicado no existe", "integranteProyecto", "designacion"));
            boolean tiene = false;
            if (usuario.getAuthorities() != null) {
                for (Authority authority : usuario.getAuthorities()) {
                    if (authority != null && autoridad.equals(authority.getName())) {
                        tiene = true;
                        break;
                    }
                }
            }
            if (!tiene) {
                throw new BadRequestAlertException(
                    "La persona designada no tiene el rol " + rol.getRol() + " (" + autoridad + ")",
                    "integranteProyecto", "designacion");
            }
        }

        // La designacion tiene que salir del padron de habilitados de la facultad. El externo no
        // pertenece al padron: para el validaron la idoneidad el CIECYT y la facultad, y eso se
        // corresponde con el paragrafo 1 de los articulos 8 y 9 del Acuerdo 25.
        if (dto.getIntegranteProyectoExternoId() == null) {
            validarPadronDeHabilitados(dto, idProyecto, autoridad);
        }

        // El catalogo limita cuantos integrantes de cada cargo admite un proyecto.
        Integer cantidad = rol.getCantidad();
        if (cantidad != null && cantidad > 0) {
            long existentes = integranteProyectoRepository
                .findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
                    idProyecto, dto.getIntegranteProyectoRolesModalidadId())
                .stream()
                .filter(integrante -> !Objects.equals(integrante.getId(), dto.getId()))
                .count();
            if (existentes >= cantidad) {
                throw new BadRequestAlertException(
                    "El proyecto ya tiene " + existentes + " " + rol.getRol()
                        + " y el catalogo admite " + cantidad,
                    "integranteProyecto", "designacion");
            }
        }
    }

    /**
     * Get all the integranteProyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IntegranteProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IntegranteProyectos");
        return integranteProyectoRepository.findAll(pageable).map(this::toDto);
    }

    /**
     * Get one integranteProyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IntegranteProyectoDTO> findOne(Long id) {
        log.debug("Request to get IntegranteProyecto : {}", id);
        return integranteProyectoRepository.findById(id).map(this::toDto);
    }

    /**
     * Get one integranteProyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */

    @Transactional(readOnly = true)
    public IntegranteProyectoDTO findOneOrderById(Long id) throws Exception {
        log.debug("Request to get IntegranteProyecto : {}", id);
        IntegranteProyecto i = new IntegranteProyecto();
        i = integranteProyectoRepository.findByIdOrderById(id);
        return toDto(i);
    }

    /**
     * Delete the integranteProyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntegranteProyecto : {}", id);
        integranteProyectoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IntegranteProyectoDTO> findByIntegranteProyectoProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get all IntegranteProyectos whit a idProyecto");
        List<IntegranteProyectoDTO> listDTO = new ArrayList<>();
        List<IntegranteProyecto> list = integranteProyectoRepository.findByIntegranteProyectoProyectoId(idProyecto);
        //listDTO = integranteProyectoMapper.usersToUserDTOs(list);

        for (IntegranteProyecto integrante : list) {
            listDTO.add(toDto(integrante));
        }
        return listDTO;
    }

    @Transactional(readOnly = true)
    public List<IntegranteProyectoDTO> findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
        Long idProyecto,
        Long idRolModalidad
    )
        throws Exception {
        log.debug("Request to get all IntegranteProyectos whit a idProyecto");
        List<IntegranteProyectoDTO> listDTO = new ArrayList<>();
        List<IntegranteProyecto> list = integranteProyectoRepository.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
            idProyecto,
            idRolModalidad
        );
        //listDTO = integranteProyectoMapper.usersToUserDTOs(list);

        for (IntegranteProyecto integrante : list) {
            listDTO.add(toDto(integrante));
        }
        return listDTO;
    }

    //findEstudiantesIntegranteProyectoId

    @Override
    @Transactional(readOnly = true)
    public List<IntegranteProyecto> findByIntegranteProyectoUserId(Long idUsuario) throws Exception {
        log.debug("Request to get all IntegranteProyectos whit a idUsuario");
        List<IntegranteProyecto> list = integranteProyectoRepository.findByIntegranteProyectoUserId(idUsuario);
        return list;
    }

    /////////////////////////////////////////////////////////
    //se reemplaza por findByIntegranteProyectoRol mas abajo
    @Transactional(readOnly = true)
    public List<IntegranteProyecto> findByIntegranteProyectoAuthority(Long idUsuario, String authority) throws Exception {
        log.debug("Request to get all IntegranteProyectos whit a idUsuario");
        List<IntegranteProyecto> listaNueva = new ArrayList<>();
        List<IntegranteProyecto> list = integranteProyectoRepository.findByIntegranteProyectoUserId(idUsuario);
        List<RolesModalidad> rolesModalidads = rolesModalidadService.findByRolesModalidadAuthorityName(authority);

        ///aqui
        for (IntegranteProyecto integranteProyecto : list) {
            Long idModalidad = integranteProyecto.getIntegranteProyectoRolesModalidad().getId();
            for (RolesModalidad rolesModalidad : rolesModalidads) {
                Long idModalidadRoles = rolesModalidad.getId();
                if (idModalidad == idModalidadRoles) {
                    listaNueva.add(integranteProyecto);
                }
            }
        }

        return listaNueva;
    }

    @Transactional(readOnly = true)
    public List<IntegranteProyecto> findByIntegranteProyectoRol(Long idUsuario, String rol) throws Exception {
        log.debug("Request to get all IntegranteProyectos whit a rol");
        List<IntegranteProyecto> listaNueva = new ArrayList<>();
        List<IntegranteProyecto> list = integranteProyectoRepository.findByIntegranteProyectoUserId(idUsuario);
        List<RolesModalidad> rolesModalidads = rolesModalidadService.findByRol(rol);
        for (IntegranteProyecto integranteProyecto : list) {
            Long idModalidad = integranteProyecto.getIntegranteProyectoRolesModalidad().getId();
            for (RolesModalidad rolesModalidad : rolesModalidads) {
                Long idModalidadRoles = rolesModalidad.getId();
                if (idModalidad == idModalidadRoles) {
                    listaNueva.add(integranteProyecto);
                }
            }
        }

        return listaNueva;
    }

    ///////////////////////////////////////////////////
    @Override
    @Transactional(readOnly = true)
    public List<IntegranteProyectoDTO> findEstudiantesIntegranteProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get Estudiantes IntegranteProyectos whit a idProyecto");

        Proyecto p = new Proyecto();
        p = proyectoRepository.findByIdOrderById(idProyecto);

        Modalidad modalidad = p.getProyectoModalidad();
        Long modalidadId = modalidad.getId(); //eje 1551

        RolesModalidadDTO rolesModalidad;
        rolesModalidad = rolesModalidadService.findByRolAndRolesModalidadModalidadId("Estudiante", modalidadId);
        Long rolesModalidadId = rolesModalidad.getId();
        List<IntegranteProyectoDTO> listDTO = new ArrayList<>();
        List<IntegranteProyecto> list = integranteProyectoRepository.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
            idProyecto,
            rolesModalidadId
        );

        for (IntegranteProyecto integrante : list) {
            listDTO.add(toDto(integrante));
        }
        return listDTO;
    }

    //jurado de viabilidad modificando
    @Transactional(readOnly = true)
    public List<IntegranteProyectoDTO> findJuradosIntegranteProyectoId(Long idProyecto, String tipoJurado) throws Exception {
        log.debug("Request to get Jurados IntegranteProyectos whit a idProyecto");

        Proyecto p = new Proyecto();
        p = proyectoRepository.findByIdOrderById(idProyecto);

        Modalidad modalidad = p.getProyectoModalidad();
        Long modalidadId = modalidad.getId(); //eje 1551

        RolesModalidadDTO rolesModalidad;
        rolesModalidad = rolesModalidadService.findByRolAndRolesModalidadModalidadId(tipoJurado, modalidadId);
        Long rolesModalidadId = rolesModalidad.getId();
        List<IntegranteProyectoDTO> listDTO = new ArrayList<>();
        List<IntegranteProyecto> list = integranteProyectoRepository.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
            idProyecto,
            rolesModalidadId
        );

        for (IntegranteProyecto integrante : list) {
            listDTO.add(toDto(integrante));
        }
        return listDTO;
    }

    //asesores
    @Transactional(readOnly = true)
    public List<IntegranteProyectoDTO> findAsesoresIntegranteProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get Jurados IntegranteProyectos whit a idProyecto");

        Proyecto p = new Proyecto();
        p = proyectoRepository.findByIdOrderById(idProyecto);

        Modalidad modalidad = p.getProyectoModalidad();
        Long modalidadId = modalidad.getId(); //eje 1551

        RolesModalidadDTO rolesModalidad;
        rolesModalidad = rolesModalidadService.findByRolAndRolesModalidadModalidadId("Asesor", modalidadId);
        Long rolesModalidadId = rolesModalidad.getId();
        List<IntegranteProyectoDTO> listDTO = new ArrayList<>();
        List<IntegranteProyecto> list = integranteProyectoRepository.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
            idProyecto,
            rolesModalidadId
        );

        for (IntegranteProyecto integrante : list) {
            listDTO.add(toDto(integrante));
        }
        return listDTO;
    }
}
