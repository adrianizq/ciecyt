package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.FacultadService;
import com.itp.ciecyt.domain.Facultad;
import com.itp.ciecyt.repository.FacultadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Facultad}.
 */
@Service
@Transactional
public class FacultadServiceImpl implements FacultadService {

    private final Logger log = LoggerFactory.getLogger(FacultadServiceImpl.class);

    private final FacultadRepository facultadRepository;

    public FacultadServiceImpl(FacultadRepository facultadRepository) {
        this.facultadRepository = facultadRepository;
    }

    /**
     * Save a facultad.
     *
     * @param facultad the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Facultad save(Facultad facultad) {
        log.debug("Request to save Facultad : {}", facultad);
        return facultadRepository.save(facultad);
    }

    /**
     * Get all the facultads.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Facultad> findAll() {
        log.debug("Request to get all Facultads");
        return facultadRepository.findAll();
    }


    /**
     * Get one facultad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Facultad> findOne(Long id) {
        log.debug("Request to get Facultad : {}", id);
        return facultadRepository.findById(id);
    }

    /**
     * Delete the facultad by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Facultad : {}", id);
        facultadRepository.deleteById(id);
    }
}
