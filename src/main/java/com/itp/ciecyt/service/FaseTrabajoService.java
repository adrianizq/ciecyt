package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.FaseTrabajo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FaseTrabajo}.
 */
public interface FaseTrabajoService {

    /**
     * Save a faseTrabajo.
     *
     * @param faseTrabajo the entity to save.
     * @return the persisted entity.
     */
    FaseTrabajo save(FaseTrabajo faseTrabajo);

    /**
     * Get all the faseTrabajos.
     *
     * @return the list of entities.
     */
    List<FaseTrabajo> findAll();


    /**
     * Get the "id" faseTrabajo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FaseTrabajo> findOne(Long id);

    /**
     * Delete the "id" faseTrabajo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
