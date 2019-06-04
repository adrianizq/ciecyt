package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Solicitud;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Solicitud}.
 */
public interface SolicitudService {

    /**
     * Save a solicitud.
     *
     * @param solicitud the entity to save.
     * @return the persisted entity.
     */
    Solicitud save(Solicitud solicitud);

    /**
     * Get all the solicituds.
     *
     * @return the list of entities.
     */
    List<Solicitud> findAll();


    /**
     * Get the "id" solicitud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Solicitud> findOne(Long id);

    /**
     * Delete the "id" solicitud.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
