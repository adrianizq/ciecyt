package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.RubrosService;
import com.itp.ciecyt.domain.Rubros;
import com.itp.ciecyt.repository.RubrosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Rubros}.
 */
@Service
@Transactional
public class RubrosServiceImpl implements RubrosService {

    private final Logger log = LoggerFactory.getLogger(RubrosServiceImpl.class);

    private final RubrosRepository rubrosRepository;

    public RubrosServiceImpl(RubrosRepository rubrosRepository) {
        this.rubrosRepository = rubrosRepository;
    }

    /**
     * Save a rubros.
     *
     * @param rubros the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Rubros save(Rubros rubros) {
        log.debug("Request to save Rubros : {}", rubros);
        return rubrosRepository.save(rubros);
    }

    /**
     * Get all the rubros.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rubros> findAll() {
        log.debug("Request to get all Rubros");
        return rubrosRepository.findAll();
    }


    /**
     * Get one rubros by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Rubros> findOne(Long id) {
        log.debug("Request to get Rubros : {}", id);
        return rubrosRepository.findById(id);
    }

    /**
     * Delete the rubros by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rubros : {}", id);
        rubrosRepository.deleteById(id);
    }
}
