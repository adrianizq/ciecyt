package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.ResultadosEsperados;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ResultadosEsperados}.
 */
public interface ResultadosEsperadosService {

    /**
     * Save a resultadosEsperados.
     *
     * @param resultadosEsperados the entity to save.
     * @return the persisted entity.
     */
    ResultadosEsperados save(ResultadosEsperados resultadosEsperados);

    /**
     * Get all the resultadosEsperados.
     *
     * @return the list of entities.
     */
    List<ResultadosEsperados> findAll();


    /**
     * Get the "id" resultadosEsperados.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResultadosEsperados> findOne(Long id);

    /**
     * Delete the "id" resultadosEsperados.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
