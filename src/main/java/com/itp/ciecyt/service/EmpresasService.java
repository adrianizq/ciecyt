package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Empresas;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Empresas}.
 */
public interface EmpresasService {

    /**
     * Save a empresas.
     *
     * @param empresas the entity to save.
     * @return the persisted entity.
     */
    Empresas save(Empresas empresas);

    /**
     * Get all the empresas.
     *
     * @return the list of entities.
     */
    List<Empresas> findAll();


    /**
     * Get the "id" empresas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Empresas> findOne(Long id);

    /**
     * Delete the "id" empresas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
