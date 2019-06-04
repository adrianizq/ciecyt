package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.ElementosProyectoService;
import com.itp.ciecyt.domain.ElementosProyecto;
import com.itp.ciecyt.repository.ElementosProyectoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ElementosProyecto}.
 */
@Service
@Transactional
public class ElementosProyectoServiceImpl implements ElementosProyectoService {

    private final Logger log = LoggerFactory.getLogger(ElementosProyectoServiceImpl.class);

    private final ElementosProyectoRepository elementosProyectoRepository;

    public ElementosProyectoServiceImpl(ElementosProyectoRepository elementosProyectoRepository) {
        this.elementosProyectoRepository = elementosProyectoRepository;
    }

    /**
     * Save a elementosProyecto.
     *
     * @param elementosProyecto the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ElementosProyecto save(ElementosProyecto elementosProyecto) {
        log.debug("Request to save ElementosProyecto : {}", elementosProyecto);
        return elementosProyectoRepository.save(elementosProyecto);
    }

    /**
     * Get all the elementosProyectos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElementosProyecto> findAll() {
        log.debug("Request to get all ElementosProyectos");
        return elementosProyectoRepository.findAll();
    }


    /**
     * Get one elementosProyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ElementosProyecto> findOne(Long id) {
        log.debug("Request to get ElementosProyecto : {}", id);
        return elementosProyectoRepository.findById(id);
    }

    /**
     * Delete the elementosProyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ElementosProyecto : {}", id);
        elementosProyectoRepository.deleteById(id);
    }
}
