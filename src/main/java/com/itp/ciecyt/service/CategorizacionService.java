package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Categorizacion;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Categorizacion}.
 */
public interface CategorizacionService {

    /**
     * Save a categorizacion.
     *
     * @param categorizacion the entity to save.
     * @return the persisted entity.
     */
    Categorizacion save(Categorizacion categorizacion);

    /**
     * Get all the categorizacions.
     *
     * @return the list of entities.
     */
    List<Categorizacion> findAll();


    /**
     * Get the "id" categorizacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Categorizacion> findOne(Long id);

    /**
     * Delete the "id" categorizacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
