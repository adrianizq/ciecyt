package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.RolesModalidad;
import co.edu.itp.ciecyt.repository.RolesModalidadRepository;
import co.edu.itp.ciecyt.service.RolesModalidadService;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.service.dto.RolesModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.RolesModalidadMapper;
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
 * Service Implementation for managing {@link RolesModalidad}.
 */
@Service
@Transactional
public class RolesModalidadServiceImpl implements RolesModalidadService {

    private final Logger log = LoggerFactory.getLogger(RolesModalidadServiceImpl.class);

    private final RolesModalidadRepository rolesModalidadRepository;

    private final RolesModalidadMapper rolesModalidadMapper;

    public RolesModalidadServiceImpl(RolesModalidadRepository rolesModalidadRepository, RolesModalidadMapper rolesModalidadMapper) {
        this.rolesModalidadRepository = rolesModalidadRepository;
        this.rolesModalidadMapper = rolesModalidadMapper;
    }

    /**
     * Save a rolesModalidad.
     *
     * @param rolesModalidadDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RolesModalidadDTO save(RolesModalidadDTO rolesModalidadDTO) {
        log.debug("Request to save RolesModalidad : {}", rolesModalidadDTO);
        RolesModalidad rolesModalidad = rolesModalidadMapper.toEntity(rolesModalidadDTO);
        rolesModalidad = rolesModalidadRepository.save(rolesModalidad);
        return rolesModalidadMapper.toDto(rolesModalidad);
    }

    /**
     * Get all the rolesModalidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RolesModalidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RolesModalidads");
        return rolesModalidadRepository.findAll(pageable).map(rolesModalidadMapper::toDto);
    }

    /**
     * Get one rolesModalidad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RolesModalidadDTO> findOne(Long id) {
        log.debug("Request to get RolesModalidad : {}", id);
        return rolesModalidadRepository.findById(id).map(rolesModalidadMapper::toDto);
    }

    /**
     * Delete the rolesModalidad by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RolesModalidad : {}", id);
        rolesModalidadRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public RolesModalidadDTO findByRolAndRolesModalidadModalidadId(String rol, Long rolModalidad) throws Exception {
        log.debug("Request to get all ImpactosEsperados whit a idProyecto");
        RolesModalidad rolesModalidad = rolesModalidadRepository.findByRolAndRolesModalidadModalidadId(rol, rolModalidad);
        return rolesModalidadMapper.toDto(rolesModalidad);
    }

    public List<RolesModalidad> findByRolesModalidadAuthorityName(String authority) throws Exception {
        log.debug("Request to get all RolesModalidad whit a authority");
        List<RolesModalidad> list = rolesModalidadRepository.findByRolesModalidadAuthorityName(authority);
        return list;
    }

    public List<RolesModalidadDTO> findByRolesModalidadAuthorityNameDTO(String authority) throws Exception {
        log.debug("Request to get all IntegranteProyectos whit a idProyecto");
        List<RolesModalidadDTO> listDTO = new ArrayList<>();
        List<RolesModalidad> list = rolesModalidadRepository.findByRolesModalidadAuthorityName(authority);
        for (RolesModalidad o : list) {
            listDTO.add(rolesModalidadMapper.toDto(o));
        }
        return listDTO;
    }

    //    List<RolesModalidad> findByRol(String rol);
    // por Rol
    public List<RolesModalidad> findByRol(String rol) throws Exception {
        log.debug("Request to get all RolesModalidad whit a rol");
        List<RolesModalidad> list = rolesModalidadRepository.findByRol(rol);
        return list;
    }

    public List<RolesModalidadDTO> findByRolDTO(String rol) throws Exception {
        log.debug("Request to get all IntegranteProyectos whit a rol");
        List<RolesModalidadDTO> listDTO = new ArrayList<>();
        List<RolesModalidad> list = rolesModalidadRepository.findByRol(rol);
        for (RolesModalidad o : list) {
            listDTO.add(rolesModalidadMapper.toDto(o));
        }
        return listDTO;
    }
}
