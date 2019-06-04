package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Cronograma;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Cronograma}.
 */
public interface CronogramaService {

    /**
     * Save a cronograma.
     *
     * @param cronograma the entity to save.
     * @return the persisted entity.
     */
    Cronograma save(Cronograma cronograma);

    /**
     * Get all the cronogramas.
     *
     * @return the list of entities.
     */
    List<Cronograma> findAll();


    /**
     * Get the "id" cronograma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cronograma> findOne(Long id);

    /**
     * Delete the "id" cronograma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
