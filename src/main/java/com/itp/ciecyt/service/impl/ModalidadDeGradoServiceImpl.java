package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.ModalidadDeGradoService;
import com.itp.ciecyt.domain.ModalidadDeGrado;
import com.itp.ciecyt.repository.ModalidadDeGradoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ModalidadDeGrado}.
 */
@Service
@Transactional
public class ModalidadDeGradoServiceImpl implements ModalidadDeGradoService {

    private final Logger log = LoggerFactory.getLogger(ModalidadDeGradoServiceImpl.class);

    private final ModalidadDeGradoRepository modalidadDeGradoRepository;

    public ModalidadDeGradoServiceImpl(ModalidadDeGradoRepository modalidadDeGradoRepository) {
        this.modalidadDeGradoRepository = modalidadDeGradoRepository;
    }

    /**
     * Save a modalidadDeGrado.
     *
     * @param modalidadDeGrado the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ModalidadDeGrado save(ModalidadDeGrado modalidadDeGrado) {
        log.debug("Request to save ModalidadDeGrado : {}", modalidadDeGrado);
        return modalidadDeGradoRepository.save(modalidadDeGrado);
    }

    /**
     * Get all the modalidadDeGrados.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ModalidadDeGrado> findAll() {
        log.debug("Request to get all ModalidadDeGrados");
        return modalidadDeGradoRepository.findAll();
    }


    /**
     * Get one modalidadDeGrado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModalidadDeGrado> findOne(Long id) {
        log.debug("Request to get ModalidadDeGrado : {}", id);
        return modalidadDeGradoRepository.findById(id);
    }

    /**
     * Delete the modalidadDeGrado by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModalidadDeGrado : {}", id);
        modalidadDeGradoRepository.deleteById(id);
    }
}
