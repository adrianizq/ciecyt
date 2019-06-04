package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.ModalidadDeGrado;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ModalidadDeGrado}.
 */
public interface ModalidadDeGradoService {

    /**
     * Save a modalidadDeGrado.
     *
     * @param modalidadDeGrado the entity to save.
     * @return the persisted entity.
     */
    ModalidadDeGrado save(ModalidadDeGrado modalidadDeGrado);

    /**
     * Get all the modalidadDeGrados.
     *
     * @return the list of entities.
     */
    List<ModalidadDeGrado> findAll();


    /**
     * Get the "id" modalidadDeGrado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModalidadDeGrado> findOne(Long id);

    /**
     * Delete the "id" modalidadDeGrado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
