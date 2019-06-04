package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.FichaTecnicaService;
import com.itp.ciecyt.domain.FichaTecnica;
import com.itp.ciecyt.repository.FichaTecnicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link FichaTecnica}.
 */
@Service
@Transactional
public class FichaTecnicaServiceImpl implements FichaTecnicaService {

    private final Logger log = LoggerFactory.getLogger(FichaTecnicaServiceImpl.class);

    private final FichaTecnicaRepository fichaTecnicaRepository;

    public FichaTecnicaServiceImpl(FichaTecnicaRepository fichaTecnicaRepository) {
        this.fichaTecnicaRepository = fichaTecnicaRepository;
    }

    /**
     * Save a fichaTecnica.
     *
     * @param fichaTecnica the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FichaTecnica save(FichaTecnica fichaTecnica) {
        log.debug("Request to save FichaTecnica : {}", fichaTecnica);
        return fichaTecnicaRepository.save(fichaTecnica);
    }

    /**
     * Get all the fichaTecnicas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FichaTecnica> findAll() {
        log.debug("Request to get all FichaTecnicas");
        return fichaTecnicaRepository.findAll();
    }


    /**
     * Get one fichaTecnica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FichaTecnica> findOne(Long id) {
        log.debug("Request to get FichaTecnica : {}", id);
        return fichaTecnicaRepository.findById(id);
    }

    /**
     * Delete the fichaTecnica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FichaTecnica : {}", id);
        fichaTecnicaRepository.deleteById(id);
    }
}
