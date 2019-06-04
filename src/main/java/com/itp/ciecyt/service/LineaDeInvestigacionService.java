package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.LineaDeInvestigacion;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LineaDeInvestigacion}.
 */
public interface LineaDeInvestigacionService {

    /**
     * Save a lineaDeInvestigacion.
     *
     * @param lineaDeInvestigacion the entity to save.
     * @return the persisted entity.
     */
    LineaDeInvestigacion save(LineaDeInvestigacion lineaDeInvestigacion);

    /**
     * Get all the lineaDeInvestigacions.
     *
     * @return the list of entities.
     */
    List<LineaDeInvestigacion> findAll();


    /**
     * Get the "id" lineaDeInvestigacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LineaDeInvestigacion> findOne(Long id);

    /**
     * Delete the "id" lineaDeInvestigacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
