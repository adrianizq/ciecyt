package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.GrupoSemilleroService;
import com.itp.ciecyt.domain.GrupoSemillero;
import com.itp.ciecyt.repository.GrupoSemilleroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link GrupoSemillero}.
 */
@Service
@Transactional
public class GrupoSemilleroServiceImpl implements GrupoSemilleroService {

    private final Logger log = LoggerFactory.getLogger(GrupoSemilleroServiceImpl.class);

    private final GrupoSemilleroRepository grupoSemilleroRepository;

    public GrupoSemilleroServiceImpl(GrupoSemilleroRepository grupoSemilleroRepository) {
        this.grupoSemilleroRepository = grupoSemilleroRepository;
    }

    /**
     * Save a grupoSemillero.
     *
     * @param grupoSemillero the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GrupoSemillero save(GrupoSemillero grupoSemillero) {
        log.debug("Request to save GrupoSemillero : {}", grupoSemillero);
        return grupoSemilleroRepository.save(grupoSemillero);
    }

    /**
     * Get all the grupoSemilleros.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GrupoSemillero> findAll() {
        log.debug("Request to get all GrupoSemilleros");
        return grupoSemilleroRepository.findAll();
    }


    /**
     * Get one grupoSemillero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GrupoSemillero> findOne(Long id) {
        log.debug("Request to get GrupoSemillero : {}", id);
        return grupoSemilleroRepository.findById(id);
    }

    /**
     * Delete the grupoSemillero by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GrupoSemillero : {}", id);
        grupoSemilleroRepository.deleteById(id);
    }
}
