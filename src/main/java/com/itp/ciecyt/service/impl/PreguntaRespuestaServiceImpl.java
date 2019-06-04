package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.PreguntaRespuestaService;
import com.itp.ciecyt.domain.PreguntaRespuesta;
import com.itp.ciecyt.repository.PreguntaRespuestaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PreguntaRespuesta}.
 */
@Service
@Transactional
public class PreguntaRespuestaServiceImpl implements PreguntaRespuestaService {

    private final Logger log = LoggerFactory.getLogger(PreguntaRespuestaServiceImpl.class);

    private final PreguntaRespuestaRepository preguntaRespuestaRepository;

    public PreguntaRespuestaServiceImpl(PreguntaRespuestaRepository preguntaRespuestaRepository) {
        this.preguntaRespuestaRepository = preguntaRespuestaRepository;
    }

    /**
     * Save a preguntaRespuesta.
     *
     * @param preguntaRespuesta the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PreguntaRespuesta save(PreguntaRespuesta preguntaRespuesta) {
        log.debug("Request to save PreguntaRespuesta : {}", preguntaRespuesta);
        return preguntaRespuestaRepository.save(preguntaRespuesta);
    }

    /**
     * Get all the preguntaRespuestas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<PreguntaRespuesta> findAll() {
        log.debug("Request to get all PreguntaRespuestas");
        return preguntaRespuestaRepository.findAll();
    }


    /**
     * Get one preguntaRespuesta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PreguntaRespuesta> findOne(Long id) {
        log.debug("Request to get PreguntaRespuesta : {}", id);
        return preguntaRespuestaRepository.findById(id);
    }

    /**
     * Delete the preguntaRespuesta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PreguntaRespuesta : {}", id);
        preguntaRespuestaRepository.deleteById(id);
    }
}
