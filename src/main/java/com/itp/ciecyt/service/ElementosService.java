package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Elementos;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Elementos}.
 */
public interface ElementosService {

    /**
     * Save a elementos.
     *
     * @param elementos the entity to save.
     * @return the persisted entity.
     */
    Elementos save(Elementos elementos);

    /**
     * Get all the elementos.
     *
     * @return the list of entities.
     */
    List<Elementos> findAll();


    /**
     * Get the "id" elementos.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Elementos> findOne(Long id);

    /**
     * Delete the "id" elementos.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
