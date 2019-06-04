package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.PreguntaRespuesta;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PreguntaRespuesta}.
 */
public interface PreguntaRespuestaService {

    /**
     * Save a preguntaRespuesta.
     *
     * @param preguntaRespuesta the entity to save.
     * @return the persisted entity.
     */
    PreguntaRespuesta save(PreguntaRespuesta preguntaRespuesta);

    /**
     * Get all the preguntaRespuestas.
     *
     * @return the list of entities.
     */
    List<PreguntaRespuesta> findAll();


    /**
     * Get the "id" preguntaRespuesta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreguntaRespuesta> findOne(Long id);

    /**
     * Delete the "id" preguntaRespuesta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
