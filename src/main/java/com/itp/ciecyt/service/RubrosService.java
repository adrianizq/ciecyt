package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Rubros;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Rubros}.
 */
public interface RubrosService {

    /**
     * Save a rubros.
     *
     * @param rubros the entity to save.
     * @return the persisted entity.
     */
    Rubros save(Rubros rubros);

    /**
     * Get all the rubros.
     *
     * @return the list of entities.
     */
    List<Rubros> findAll();


    /**
     * Get the "id" rubros.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Rubros> findOne(Long id);

    /**
     * Delete the "id" rubros.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
