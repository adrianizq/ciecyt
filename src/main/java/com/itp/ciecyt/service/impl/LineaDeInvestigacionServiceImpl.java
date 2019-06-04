package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.LineaDeInvestigacionService;
import com.itp.ciecyt.domain.LineaDeInvestigacion;
import com.itp.ciecyt.repository.LineaDeInvestigacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link LineaDeInvestigacion}.
 */
@Service
@Transactional
public class LineaDeInvestigacionServiceImpl implements LineaDeInvestigacionService {

    private final Logger log = LoggerFactory.getLogger(LineaDeInvestigacionServiceImpl.class);

    private final LineaDeInvestigacionRepository lineaDeInvestigacionRepository;

    public LineaDeInvestigacionServiceImpl(LineaDeInvestigacionRepository lineaDeInvestigacionRepository) {
        this.lineaDeInvestigacionRepository = lineaDeInvestigacionRepository;
    }

    /**
     * Save a lineaDeInvestigacion.
     *
     * @param lineaDeInvestigacion the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LineaDeInvestigacion save(LineaDeInvestigacion lineaDeInvestigacion) {
        log.debug("Request to save LineaDeInvestigacion : {}", lineaDeInvestigacion);
        return lineaDeInvestigacionRepository.save(lineaDeInvestigacion);
    }

    /**
     * Get all the lineaDeInvestigacions.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<LineaDeInvestigacion> findAll() {
        log.debug("Request to get all LineaDeInvestigacions");
        return lineaDeInvestigacionRepository.findAll();
    }


    /**
     * Get one lineaDeInvestigacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LineaDeInvestigacion> findOne(Long id) {
        log.debug("Request to get LineaDeInvestigacion : {}", id);
        return lineaDeInvestigacionRepository.findById(id);
    }

    /**
     * Delete the lineaDeInvestigacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LineaDeInvestigacion : {}", id);
        lineaDeInvestigacionRepository.deleteById(id);
    }
}
