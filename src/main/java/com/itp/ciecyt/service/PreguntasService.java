package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Preguntas;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Preguntas}.
 */
public interface PreguntasService {

    /**
     * Save a preguntas.
     *
     * @param preguntas the entity to save.
     * @return the persisted entity.
     */
    Preguntas save(Preguntas preguntas);

    /**
     * Get all the preguntas.
     *
     * @return the list of entities.
     */
    List<Preguntas> findAll();


    /**
     * Get the "id" preguntas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Preguntas> findOne(Long id);

    /**
     * Delete the "id" preguntas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
