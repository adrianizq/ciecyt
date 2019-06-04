package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.PresupuestoValor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PresupuestoValor}.
 */
public interface PresupuestoValorService {

    /**
     * Save a presupuestoValor.
     *
     * @param presupuestoValor the entity to save.
     * @return the persisted entity.
     */
    PresupuestoValor save(PresupuestoValor presupuestoValor);

    /**
     * Get all the presupuestoValors.
     *
     * @return the list of entities.
     */
    List<PresupuestoValor> findAll();


    /**
     * Get the "id" presupuestoValor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PresupuestoValor> findOne(Long id);

    /**
     * Delete the "id" presupuestoValor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
