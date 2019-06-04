package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.FichaTecnica;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FichaTecnica}.
 */
public interface FichaTecnicaService {

    /**
     * Save a fichaTecnica.
     *
     * @param fichaTecnica the entity to save.
     * @return the persisted entity.
     */
    FichaTecnica save(FichaTecnica fichaTecnica);

    /**
     * Get all the fichaTecnicas.
     *
     * @return the list of entities.
     */
    List<FichaTecnica> findAll();


    /**
     * Get the "id" fichaTecnica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FichaTecnica> findOne(Long id);

    /**
     * Delete the "id" fichaTecnica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
