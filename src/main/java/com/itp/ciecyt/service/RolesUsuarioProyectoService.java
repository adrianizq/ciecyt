package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.RolesUsuarioProyecto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link RolesUsuarioProyecto}.
 */
public interface RolesUsuarioProyectoService {

    /**
     * Save a rolesUsuarioProyecto.
     *
     * @param rolesUsuarioProyecto the entity to save.
     * @return the persisted entity.
     */
    RolesUsuarioProyecto save(RolesUsuarioProyecto rolesUsuarioProyecto);

    /**
     * Get all the rolesUsuarioProyectos.
     *
     * @return the list of entities.
     */
    List<RolesUsuarioProyecto> findAll();


    /**
     * Get the "id" rolesUsuarioProyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RolesUsuarioProyecto> findOne(Long id);

    /**
     * Delete the "id" rolesUsuarioProyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
