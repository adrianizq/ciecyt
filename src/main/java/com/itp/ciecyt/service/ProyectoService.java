package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.Proyecto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Proyecto}.
 */
public interface ProyectoService {

    /**
     * Save a proyecto.
     *
     * @param proyecto the entity to save.
     * @return the persisted entity.
     */
    Proyecto save(Proyecto proyecto);

    /**
     * Get all the proyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Proyecto> findAll(Pageable pageable);


    /**
     * Get the "id" proyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Proyecto> findOne(Long id);

    /**
     * Delete the "id" proyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
