package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.TipoPreguntaService;
import com.itp.ciecyt.domain.TipoPregunta;
import com.itp.ciecyt.repository.TipoPreguntaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoPregunta}.
 */
@Service
@Transactional
public class TipoPreguntaServiceImpl implements TipoPreguntaService {

    private final Logger log = LoggerFactory.getLogger(TipoPreguntaServiceImpl.class);

    private final TipoPreguntaRepository tipoPreguntaRepository;

    public TipoPreguntaServiceImpl(TipoPreguntaRepository tipoPreguntaRepository) {
        this.tipoPreguntaRepository = tipoPreguntaRepository;
    }

    /**
     * Save a tipoPregunta.
     *
     * @param tipoPregunta the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoPregunta save(TipoPregunta tipoPregunta) {
        log.debug("Request to save TipoPregunta : {}", tipoPregunta);
        return tipoPreguntaRepository.save(tipoPregunta);
    }

    /**
     * Get all the tipoPreguntas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoPregunta> findAll() {
        log.debug("Request to get all TipoPreguntas");
        return tipoPreguntaRepository.findAll();
    }


    /**
     * Get one tipoPregunta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPregunta> findOne(Long id) {
        log.debug("Request to get TipoPregunta : {}", id);
        return tipoPreguntaRepository.findById(id);
    }

    /**
     * Delete the tipoPregunta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPregunta : {}", id);
        tipoPreguntaRepository.deleteById(id);
    }
}
