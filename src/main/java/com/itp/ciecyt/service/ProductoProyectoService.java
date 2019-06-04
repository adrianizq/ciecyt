package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.ProductoProyecto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ProductoProyecto}.
 */
public interface ProductoProyectoService {

    /**
     * Save a productoProyecto.
     *
     * @param productoProyecto the entity to save.
     * @return the persisted entity.
     */
    ProductoProyecto save(ProductoProyecto productoProyecto);

    /**
     * Get all the productoProyectos.
     *
     * @return the list of entities.
     */
    List<ProductoProyecto> findAll();


    /**
     * Get the "id" productoProyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoProyecto> findOne(Long id);

    /**
     * Delete the "id" productoProyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
