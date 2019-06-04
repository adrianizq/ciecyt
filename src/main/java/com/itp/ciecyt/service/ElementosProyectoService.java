package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.ElementosProyecto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ElementosProyecto}.
 */
public interface ElementosProyectoService {

    /**
     * Save a elementosProyecto.
     *
     * @param elementosProyecto the entity to save.
     * @return the persisted entity.
     */
    ElementosProyecto save(ElementosProyecto elementosProyecto);

    /**
     * Get all the elementosProyectos.
     *
     * @return the list of entities.
     */
    List<ElementosProyecto> findAll();


    /**
     * Get the "id" elementosProyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ElementosProyecto> findOne(Long id);

    /**
     * Delete the "id" elementosProyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
