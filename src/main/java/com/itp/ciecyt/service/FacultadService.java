package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Facultad;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Facultad}.
 */
public interface FacultadService {

    /**
     * Save a facultad.
     *
     * @param facultad the entity to save.
     * @return the persisted entity.
     */
    Facultad save(Facultad facultad);

    /**
     * Get all the facultads.
     *
     * @return the list of entities.
     */
    List<Facultad> findAll();


    /**
     * Get the "id" facultad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Facultad> findOne(Long id);

    /**
     * Delete the "id" facultad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
