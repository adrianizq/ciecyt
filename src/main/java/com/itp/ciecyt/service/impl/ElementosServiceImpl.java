package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.ElementosService;
import com.itp.ciecyt.domain.Elementos;
import com.itp.ciecyt.repository.ElementosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Elementos}.
 */
@Service
@Transactional
public class ElementosServiceImpl implements ElementosService {

    private final Logger log = LoggerFactory.getLogger(ElementosServiceImpl.class);

    private final ElementosRepository elementosRepository;

    public ElementosServiceImpl(ElementosRepository elementosRepository) {
        this.elementosRepository = elementosRepository;
    }

    /**
     * Save a elementos.
     *
     * @param elementos the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Elementos save(Elementos elementos) {
        log.debug("Request to save Elementos : {}", elementos);
        return elementosRepository.save(elementos);
    }

    /**
     * Get all the elementos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Elementos> findAll() {
        log.debug("Request to get all Elementos");
        return elementosRepository.findAll();
    }


    /**
     * Get one elementos by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Elementos> findOne(Long id) {
        log.debug("Request to get Elementos : {}", id);
        return elementosRepository.findById(id);
    }

    /**
     * Delete the elementos by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Elementos : {}", id);
        elementosRepository.deleteById(id);
    }
}
