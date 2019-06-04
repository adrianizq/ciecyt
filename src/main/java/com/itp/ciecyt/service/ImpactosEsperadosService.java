package com.itp.ciecyt.service;

import com.itp.ciecyt.domain.ImpactosEsperados;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ImpactosEsperados}.
 */
public interface ImpactosEsperadosService {

    /**
     * Save a impactosEsperados.
     *
     * @param impactosEsperados the entity to save.
     * @return the persisted entity.
     */
    ImpactosEsperados save(ImpactosEsperados impactosEsperados);

    /**
     * Get all the impactosEsperados.
     *
     * @return the list of entities.
     */
    List<ImpactosEsperados> findAll();


    /**
     * Get the "id" impactosEsperados.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImpactosEsperados> findOne(Long id);

    /**
     * Delete the "id" impactosEsperados.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
