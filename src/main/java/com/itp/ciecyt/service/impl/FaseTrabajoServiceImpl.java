package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.FaseTrabajoService;
import com.itp.ciecyt.domain.FaseTrabajo;
import com.itp.ciecyt.repository.FaseTrabajoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link FaseTrabajo}.
 */
@Service
@Transactional
public class FaseTrabajoServiceImpl implements FaseTrabajoService {

    private final Logger log = LoggerFactory.getLogger(FaseTrabajoServiceImpl.class);

    private final FaseTrabajoRepository faseTrabajoRepository;

    public FaseTrabajoServiceImpl(FaseTrabajoRepository faseTrabajoRepository) {
        this.faseTrabajoRepository = faseTrabajoRepository;
    }

    /**
     * Save a faseTrabajo.
     *
     * @param faseTrabajo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FaseTrabajo save(FaseTrabajo faseTrabajo) {
        log.debug("Request to save FaseTrabajo : {}", faseTrabajo);
        return faseTrabajoRepository.save(faseTrabajo);
    }

    /**
     * Get all the faseTrabajos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FaseTrabajo> findAll() {
        log.debug("Request to get all FaseTrabajos");
        return faseTrabajoRepository.findAll();
    }


    /**
     * Get one faseTrabajo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FaseTrabajo> findOne(Long id) {
        log.debug("Request to get FaseTrabajo : {}", id);
        return faseTrabajoRepository.findById(id);
    }

    /**
     * Delete the faseTrabajo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FaseTrabajo : {}", id);
        faseTrabajoRepository.deleteById(id);
    }
}
