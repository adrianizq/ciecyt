package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.TipoPregunta;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link TipoPregunta}.
 */
public interface TipoPreguntaService {

    /**
     * Save a tipoPregunta.
     *
     * @param tipoPregunta the entity to save.
     * @return the persisted entity.
     */
    TipoPregunta save(TipoPregunta tipoPregunta);

    /**
     * Get all the tipoPreguntas.
     *
     * @return the list of entities.
     */
    List<TipoPregunta> findAll();


    /**
     * Get the "id" tipoPregunta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoPregunta> findOne(Long id);

    /**
     * Delete the "id" tipoPregunta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
