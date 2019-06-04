package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.GrupoSemillero;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GrupoSemillero}.
 */
public interface GrupoSemilleroService {

    /**
     * Save a grupoSemillero.
     *
     * @param grupoSemillero the entity to save.
     * @return the persisted entity.
     */
    GrupoSemillero save(GrupoSemillero grupoSemillero);

    /**
     * Get all the grupoSemilleros.
     *
     * @return the list of entities.
     */
    List<GrupoSemillero> findAll();


    /**
     * Get the "id" grupoSemillero.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GrupoSemillero> findOne(Long id);

    /**
     * Delete the "id" grupoSemillero.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
