package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.ImpactosEsperadosService;
import com.itp.ciecyt.domain.ImpactosEsperados;
import com.itp.ciecyt.repository.ImpactosEsperadosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ImpactosEsperados}.
 */
@Service
@Transactional
public class ImpactosEsperadosServiceImpl implements ImpactosEsperadosService {

    private final Logger log = LoggerFactory.getLogger(ImpactosEsperadosServiceImpl.class);

    private final ImpactosEsperadosRepository impactosEsperadosRepository;

    public ImpactosEsperadosServiceImpl(ImpactosEsperadosRepository impactosEsperadosRepository) {
        this.impactosEsperadosRepository = impactosEsperadosRepository;
    }

    /**
     * Save a impactosEsperados.
     *
     * @param impactosEsperados the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImpactosEsperados save(ImpactosEsperados impactosEsperados) {
        log.debug("Request to save ImpactosEsperados : {}", impactosEsperados);
        return impactosEsperadosRepository.save(impactosEsperados);
    }

    /**
     * Get all the impactosEsperados.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ImpactosEsperados> findAll() {
        log.debug("Request to get all ImpactosEsperados");
        return impactosEsperadosRepository.findAll();
    }


    /**
     * Get one impactosEsperados by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImpactosEsperados> findOne(Long id) {
        log.debug("Request to get ImpactosEsperados : {}", id);
        return impactosEsperadosRepository.findById(id);
    }

    /**
     * Delete the impactosEsperados by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImpactosEsperados : {}", id);
        impactosEsperadosRepository.deleteById(id);
    }
}
