package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.SolicitudService;
import com.itp.ciecyt.domain.Solicitud;
import com.itp.ciecyt.repository.SolicitudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Solicitud}.
 */
@Service
@Transactional
public class SolicitudServiceImpl implements SolicitudService {

    private final Logger log = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    private final SolicitudRepository solicitudRepository;

    public SolicitudServiceImpl(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    /**
     * Save a solicitud.
     *
     * @param solicitud the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Solicitud save(Solicitud solicitud) {
        log.debug("Request to save Solicitud : {}", solicitud);
        return solicitudRepository.save(solicitud);
    }

    /**
     * Get all the solicituds.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Solicitud> findAll() {
        log.debug("Request to get all Solicituds");
        return solicitudRepository.findAll();
    }


    /**
     * Get one solicitud by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Solicitud> findOne(Long id) {
        log.debug("Request to get Solicitud : {}", id);
        return solicitudRepository.findById(id);
    }

    /**
     * Delete the solicitud by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Solicitud : {}", id);
        solicitudRepository.deleteById(id);
    }
}
