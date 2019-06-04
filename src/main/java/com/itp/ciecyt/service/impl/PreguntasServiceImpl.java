package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.PreguntasService;
import com.itp.ciecyt.domain.Preguntas;
import com.itp.ciecyt.repository.PreguntasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Preguntas}.
 */
@Service
@Transactional
public class PreguntasServiceImpl implements PreguntasService {

    private final Logger log = LoggerFactory.getLogger(PreguntasServiceImpl.class);

    private final PreguntasRepository preguntasRepository;

    public PreguntasServiceImpl(PreguntasRepository preguntasRepository) {
        this.preguntasRepository = preguntasRepository;
    }

    /**
     * Save a preguntas.
     *
     * @param preguntas the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Preguntas save(Preguntas preguntas) {
        log.debug("Request to save Preguntas : {}", preguntas);
        return preguntasRepository.save(preguntas);
    }

    /**
     * Get all the preguntas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Preguntas> findAll() {
        log.debug("Request to get all Preguntas");
        return preguntasRepository.findAll();
    }


    /**
     * Get one preguntas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Preguntas> findOne(Long id) {
        log.debug("Request to get Preguntas : {}", id);
        return preguntasRepository.findById(id);
    }

    /**
     * Delete the preguntas by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Preguntas : {}", id);
        preguntasRepository.deleteById(id);
    }
}
