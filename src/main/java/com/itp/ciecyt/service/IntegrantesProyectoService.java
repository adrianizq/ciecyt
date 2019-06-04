package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.IntegrantesProyecto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link IntegrantesProyecto}.
 */
public interface IntegrantesProyectoService {

    /**
     * Save a integrantesProyecto.
     *
     * @param integrantesProyecto the entity to save.
     * @return the persisted entity.
     */
    IntegrantesProyecto save(IntegrantesProyecto integrantesProyecto);

    /**
     * Get all the integrantesProyectos.
     *
     * @return the list of entities.
     */
    List<IntegrantesProyecto> findAll();


    /**
     * Get the "id" integrantesProyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntegrantesProyecto> findOne(Long id);

    /**
     * Delete the "id" integrantesProyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
