package com.itp.ciecyt.service.impl;

import com.itp.ciecyt.service.EmpresasService;
import com.itp.ciecyt.domain.Empresas;
import com.itp.ciecyt.repository.EmpresasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Empresas}.
 */
@Service
@Transactional
public class EmpresasServiceImpl implements EmpresasService {

    private final Logger log = LoggerFactory.getLogger(EmpresasServiceImpl.class);

    private final EmpresasRepository empresasRepository;

    public EmpresasServiceImpl(EmpresasRepository empresasRepository) {
        this.empresasRepository = empresasRepository;
    }

    /**
     * Save a empresas.
     *
     * @param empresas the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Empresas save(Empresas empresas) {
        log.debug("Request to save Empresas : {}", empresas);
        return empresasRepository.save(empresas);
    }

    /**
     * Get all the empresas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Empresas> findAll() {
        log.debug("Request to get all Empresas");
        return empresasRepository.findAll();
    }


    /**
     * Get one empresas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Empresas> findOne(Long id) {
        log.debug("Request to get Empresas : {}", id);
        return empresasRepository.findById(id);
    }

    /**
     * Delete the empresas by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Empresas : {}", id);
        empresasRepository.deleteById(id);
    }
}
