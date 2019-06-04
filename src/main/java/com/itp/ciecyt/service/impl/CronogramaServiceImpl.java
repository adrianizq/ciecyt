package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.CronogramaService;
import com.itp.ciecyt.domain.Cronograma;
import com.itp.ciecyt.repository.CronogramaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Cronograma}.
 */
@Service
@Transactional
public class CronogramaServiceImpl implements CronogramaService {

    private final Logger log = LoggerFactory.getLogger(CronogramaServiceImpl.class);

    private final CronogramaRepository cronogramaRepository;

    public CronogramaServiceImpl(CronogramaRepository cronogramaRepository) {
        this.cronogramaRepository = cronogramaRepository;
    }

    /**
     * Save a cronograma.
     *
     * @param cronograma the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Cronograma save(Cronograma cronograma) {
        log.debug("Request to save Cronograma : {}", cronograma);
        return cronogramaRepository.save(cronograma);
    }

    /**
     * Get all the cronogramas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cronograma> findAll() {
        log.debug("Request to get all Cronogramas");
        return cronogramaRepository.findAll();
    }


    /**
     * Get one cronograma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cronograma> findOne(Long id) {
        log.debug("Request to get Cronograma : {}", id);
        return cronogramaRepository.findById(id);
    }

    /**
     * Delete the cronograma by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cronograma : {}", id);
        cronogramaRepository.deleteById(id);
    }
}
