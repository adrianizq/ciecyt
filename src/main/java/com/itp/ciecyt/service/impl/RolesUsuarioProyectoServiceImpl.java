package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.RolesUsuarioProyectoService;
import com.itp.ciecyt.domain.RolesUsuarioProyecto;
import com.itp.ciecyt.repository.RolesUsuarioProyectoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RolesUsuarioProyecto}.
 */
@Service
@Transactional
public class RolesUsuarioProyectoServiceImpl implements RolesUsuarioProyectoService {

    private final Logger log = LoggerFactory.getLogger(RolesUsuarioProyectoServiceImpl.class);

    private final RolesUsuarioProyectoRepository rolesUsuarioProyectoRepository;

    public RolesUsuarioProyectoServiceImpl(RolesUsuarioProyectoRepository rolesUsuarioProyectoRepository) {
        this.rolesUsuarioProyectoRepository = rolesUsuarioProyectoRepository;
    }

    /**
     * Save a rolesUsuarioProyecto.
     *
     * @param rolesUsuarioProyecto the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RolesUsuarioProyecto save(RolesUsuarioProyecto rolesUsuarioProyecto) {
        log.debug("Request to save RolesUsuarioProyecto : {}", rolesUsuarioProyecto);
        return rolesUsuarioProyectoRepository.save(rolesUsuarioProyecto);
    }

    /**
     * Get all the rolesUsuarioProyectos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RolesUsuarioProyecto> findAll() {
        log.debug("Request to get all RolesUsuarioProyectos");
        return rolesUsuarioProyectoRepository.findAll();
    }


    /**
     * Get one rolesUsuarioProyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RolesUsuarioProyecto> findOne(Long id) {
        log.debug("Request to get RolesUsuarioProyecto : {}", id);
        return rolesUsuarioProyectoRepository.findById(id);
    }

    /**
     * Delete the rolesUsuarioProyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RolesUsuarioProyecto : {}", id);
        rolesUsuarioProyectoRepository.deleteById(id);
    }
}
