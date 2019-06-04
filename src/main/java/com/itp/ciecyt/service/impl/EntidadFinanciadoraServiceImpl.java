package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.EntidadFinanciadoraService;
import com.itp.ciecyt.domain.EntidadFinanciadora;
import com.itp.ciecyt.repository.EntidadFinanciadoraRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link EntidadFinanciadora}.
 */
@Service
@Transactional
public class EntidadFinanciadoraServiceImpl implements EntidadFinanciadoraService {

    private final Logger log = LoggerFactory.getLogger(EntidadFinanciadoraServiceImpl.class);

    private final EntidadFinanciadoraRepository entidadFinanciadoraRepository;

    public EntidadFinanciadoraServiceImpl(EntidadFinanciadoraRepository entidadFinanciadoraRepository) {
        this.entidadFinanciadoraRepository = entidadFinanciadoraRepository;
    }

    /**
     * Save a entidadFinanciadora.
     *
     * @param entidadFinanciadora the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EntidadFinanciadora save(EntidadFinanciadora entidadFinanciadora) {
        log.debug("Request to save EntidadFinanciadora : {}", entidadFinanciadora);
        return entidadFinanciadoraRepository.save(entidadFinanciadora);
    }

    /**
     * Get all the entidadFinanciadoras.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EntidadFinanciadora> findAll() {
        log.debug("Request to get all EntidadFinanciadoras");
        return entidadFinanciadoraRepository.findAll();
    }


    /**
     * Get one entidadFinanciadora by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EntidadFinanciadora> findOne(Long id) {
        log.debug("Request to get EntidadFinanciadora : {}", id);
        return entidadFinanciadoraRepository.findById(id);
    }

    /**
     * Delete the entidadFinanciadora by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntidadFinanciadora : {}", id);
        entidadFinanciadoraRepository.deleteById(id);
    }
}
