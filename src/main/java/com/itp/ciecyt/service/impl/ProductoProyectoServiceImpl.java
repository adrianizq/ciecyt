package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.ProductoProyectoService;
import com.itp.ciecyt.domain.ProductoProyecto;
import com.itp.ciecyt.repository.ProductoProyectoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProductoProyecto}.
 */
@Service
@Transactional
public class ProductoProyectoServiceImpl implements ProductoProyectoService {

    private final Logger log = LoggerFactory.getLogger(ProductoProyectoServiceImpl.class);

    private final ProductoProyectoRepository productoProyectoRepository;

    public ProductoProyectoServiceImpl(ProductoProyectoRepository productoProyectoRepository) {
        this.productoProyectoRepository = productoProyectoRepository;
    }

    /**
     * Save a productoProyecto.
     *
     * @param productoProyecto the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProductoProyecto save(ProductoProyecto productoProyecto) {
        log.debug("Request to save ProductoProyecto : {}", productoProyecto);
        return productoProyectoRepository.save(productoProyecto);
    }

    /**
     * Get all the productoProyectos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductoProyecto> findAll() {
        log.debug("Request to get all ProductoProyectos");
        return productoProyectoRepository.findAll();
    }


    /**
     * Get one productoProyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoProyecto> findOne(Long id) {
        log.debug("Request to get ProductoProyecto : {}", id);
        return productoProyectoRepository.findById(id);
    }

    /**
     * Delete the productoProyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoProyecto : {}", id);
        productoProyectoRepository.deleteById(id);
    }
}
