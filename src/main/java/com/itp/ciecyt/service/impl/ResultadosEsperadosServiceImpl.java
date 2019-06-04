package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.ResultadosEsperadosService;
import com.itp.ciecyt.domain.ResultadosEsperados;
import com.itp.ciecyt.repository.ResultadosEsperadosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ResultadosEsperados}.
 */
@Service
@Transactional
public class ResultadosEsperadosServiceImpl implements ResultadosEsperadosService {

    private final Logger log = LoggerFactory.getLogger(ResultadosEsperadosServiceImpl.class);

    private final ResultadosEsperadosRepository resultadosEsperadosRepository;

    public ResultadosEsperadosServiceImpl(ResultadosEsperadosRepository resultadosEsperadosRepository) {
        this.resultadosEsperadosRepository = resultadosEsperadosRepository;
    }

    /**
     * Save a resultadosEsperados.
     *
     * @param resultadosEsperados the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ResultadosEsperados save(ResultadosEsperados resultadosEsperados) {
        log.debug("Request to save ResultadosEsperados : {}", resultadosEsperados);
        return resultadosEsperadosRepository.save(resultadosEsperados);
    }

    /**
     * Get all the resultadosEsperados.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResultadosEsperados> findAll() {
        log.debug("Request to get all ResultadosEsperados");
        return resultadosEsperadosRepository.findAll();
    }


    /**
     * Get one resultadosEsperados by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResultadosEsperados> findOne(Long id) {
        log.debug("Request to get ResultadosEsperados : {}", id);
        return resultadosEsperadosRepository.findById(id);
    }

    /**
     * Delete the resultadosEsperados by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResultadosEsperados : {}", id);
        resultadosEsperadosRepository.deleteById(id);
    }
}
