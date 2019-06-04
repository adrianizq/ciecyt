package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.CategorizacionService;
import com.itp.ciecyt.domain.Categorizacion;
import com.itp.ciecyt.repository.CategorizacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Categorizacion}.
 */
@Service
@Transactional
public class CategorizacionServiceImpl implements CategorizacionService {

    private final Logger log = LoggerFactory.getLogger(CategorizacionServiceImpl.class);

    private final CategorizacionRepository categorizacionRepository;

    public CategorizacionServiceImpl(CategorizacionRepository categorizacionRepository) {
        this.categorizacionRepository = categorizacionRepository;
    }

    /**
     * Save a categorizacion.
     *
     * @param categorizacion the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Categorizacion save(Categorizacion categorizacion) {
        log.debug("Request to save Categorizacion : {}", categorizacion);
        return categorizacionRepository.save(categorizacion);
    }

    /**
     * Get all the categorizacions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Categorizacion> findAll() {
        log.debug("Request to get all Categorizacions");
        return categorizacionRepository.findAll();
    }


    /**
     * Get one categorizacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Categorizacion> findOne(Long id) {
        log.debug("Request to get Categorizacion : {}", id);
        return categorizacionRepository.findById(id);
    }

    /**
     * Delete the categorizacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categorizacion : {}", id);
        categorizacionRepository.deleteById(id);
    }
}
