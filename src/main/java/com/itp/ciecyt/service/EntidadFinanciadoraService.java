package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.EntidadFinanciadora;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link EntidadFinanciadora}.
 */
public interface EntidadFinanciadoraService {

    /**
     * Save a entidadFinanciadora.
     *
     * @param entidadFinanciadora the entity to save.
     * @return the persisted entity.
     */
    EntidadFinanciadora save(EntidadFinanciadora entidadFinanciadora);

    /**
     * Get all the entidadFinanciadoras.
     *
     * @return the list of entities.
     */
    List<EntidadFinanciadora> findAll();


    /**
     * Get the "id" entidadFinanciadora.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntidadFinanciadora> findOne(Long id);

    /**
     * Delete the "id" entidadFinanciadora.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
