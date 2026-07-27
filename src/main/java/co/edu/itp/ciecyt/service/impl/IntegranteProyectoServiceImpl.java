package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.RolesModalidad;
import co.edu.itp.ciecyt.repository.IntegranteProyectoRepository;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.service.IntegranteProyectoService;
import co.edu.itp.ciecyt.service.RolesModalidadService;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.dto.RolesModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.IntegranteProyectoMapper;
import java.util.ArrayList;
import java.util.List;
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

    public IntegranteProyectoServiceImpl(
        IntegranteProyectoRepository integranteProyectoRepository,
        IntegranteProyectoMapper integranteProyectoMapper,
        ProyectoRepository proyectoRepository,
        RolesModalidadService rolesModalidadService
    ) {
        this.integranteProyectoRepository = integranteProyectoRepository;
        this.integranteProyectoMapper = integranteProyectoMapper;
        this.proyectoRepository = proyectoRepository;
        this.rolesModalidadService = rolesModalidadService;
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
        IntegranteProyecto integranteProyecto = integranteProyectoMapper.toEntity(integranteProyectoDTO);
        integranteProyecto = integranteProyectoRepository.save(integranteProyecto);
        return integranteProyectoMapper.toDto(integranteProyecto);
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
        return integranteProyectoRepository.findAll(pageable).map(integranteProyectoMapper::toDto);
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
        return integranteProyectoRepository.findById(id).map(integranteProyectoMapper::toDto);
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
        return integranteProyectoMapper.toDto(i);
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
            listDTO.add(integranteProyectoMapper.toDto(integrante));
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
            listDTO.add(integranteProyectoMapper.toDto(integrante));
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
            listDTO.add(integranteProyectoMapper.toDto(integrante));
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
            listDTO.add(integranteProyectoMapper.toDto(integrante));
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
            listDTO.add(integranteProyectoMapper.toDto(integrante));
        }
        return listDTO;
    }
}
