package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.PresupuestoValorService;
import com.itp.ciecyt.domain.PresupuestoValor;
import com.itp.ciecyt.repository.PresupuestoValorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PresupuestoValor}.
 */
@Service
@Transactional
public class PresupuestoValorServiceImpl implements PresupuestoValorService {

    private final Logger log = LoggerFactory.getLogger(PresupuestoValorServiceImpl.class);

    private final PresupuestoValorRepository presupuestoValorRepository;

    public PresupuestoValorServiceImpl(PresupuestoValorRepository presupuestoValorRepository) {
        this.presupuestoValorRepository = presupuestoValorRepository;
    }

    /**
     * Save a presupuestoValor.
     *
     * @param presupuestoValor the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PresupuestoValor save(PresupuestoValor presupuestoValor) {
        log.debug("Request to save PresupuestoValor : {}", presupuestoValor);
        return presupuestoValorRepository.save(presupuestoValor);
    }

    /**
     * Get all the presupuestoValors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PresupuestoValor> findAll() {
        log.debug("Request to get all PresupuestoValors");
        return presupuestoValorRepository.findAll();
    }


    /**
     * Get one presupuestoValor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PresupuestoValor> findOne(Long id) {
        log.debug("Request to get PresupuestoValor : {}", id);
        return presupuestoValorRepository.findById(id);
    }

    /**
     * Delete the presupuestoValor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PresupuestoValor : {}", id);
        presupuestoValorRepository.deleteById(id);
    }
}
