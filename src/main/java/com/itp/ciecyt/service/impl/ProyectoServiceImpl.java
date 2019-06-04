package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.ProyectoService;
import com.itp.ciecyt.domain.Proyecto;
import com.itp.ciecyt.repository.ProyectoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Proyecto}.
 */
@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    private final Logger log = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    private final ProyectoRepository proyectoRepository;

    public ProyectoServiceImpl(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    /**
     * Save a proyecto.
     *
     * @param proyecto the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Proyecto save(Proyecto proyecto) {
        log.debug("Request to save Proyecto : {}", proyecto);
        return proyectoRepository.save(proyecto);
    }

    /**
     * Get all the proyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Proyecto> findAll(Pageable pageable) {
        log.debug("Request to get all Proyectos");
        return proyectoRepository.findAll(pageable);
    }


    /**
     * Get one proyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Proyecto> findOne(Long id) {
        log.debug("Request to get Proyecto : {}", id);
        return proyectoRepository.findById(id);
    }

    /**
     * Delete the proyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proyecto : {}", id);
        proyectoRepository.deleteById(id);
    }
}
