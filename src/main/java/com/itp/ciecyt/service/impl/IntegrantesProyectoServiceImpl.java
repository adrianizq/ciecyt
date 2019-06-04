package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.IntegrantesProyectoService;
import com.itp.ciecyt.domain.IntegrantesProyecto;
import com.itp.ciecyt.repository.IntegrantesProyectoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link IntegrantesProyecto}.
 */
@Service
@Transactional
public class IntegrantesProyectoServiceImpl implements IntegrantesProyectoService {

    private final Logger log = LoggerFactory.getLogger(IntegrantesProyectoServiceImpl.class);

    private final IntegrantesProyectoRepository integrantesProyectoRepository;

    public IntegrantesProyectoServiceImpl(IntegrantesProyectoRepository integrantesProyectoRepository) {
        this.integrantesProyectoRepository = integrantesProyectoRepository;
    }

    /**
     * Save a integrantesProyecto.
     *
     * @param integrantesProyecto the entity to save.
     * @return the persisted entity.
     */
    @Override
    public IntegrantesProyecto save(IntegrantesProyecto integrantesProyecto) {
        log.debug("Request to save IntegrantesProyecto : {}", integrantesProyecto);
        return integrantesProyectoRepository.save(integrantesProyecto);
    }

    /**
     * Get all the integrantesProyectos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<IntegrantesProyecto> findAll() {
        log.debug("Request to get all IntegrantesProyectos");
        return integrantesProyectoRepository.findAll();
    }


    /**
     * Get one integrantesProyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IntegrantesProyecto> findOne(Long id) {
        log.debug("Request to get IntegrantesProyecto : {}", id);
        return integrantesProyectoRepository.findById(id);
    }

    /**
     * Delete the integrantesProyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IntegrantesProyecto : {}", id);
        integrantesProyectoRepository.deleteById(id);
    }
}
