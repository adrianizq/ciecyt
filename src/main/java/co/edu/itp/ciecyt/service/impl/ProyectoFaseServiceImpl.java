package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.ProyectoFaseService;
import co.edu.itp.ciecyt.domain.ProyectoFase;
import co.edu.itp.ciecyt.repository.ProyectoFaseRepository;
import co.edu.itp.ciecyt.service.dto.ProyectoFaseDTO;
import co.edu.itp.ciecyt.service.mapper.ProyectoFaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ProyectoFase}.
 */
@Service
@Transactional
public class ProyectoFaseServiceImpl implements ProyectoFaseService {

    private final Logger log = LoggerFactory.getLogger(ProyectoFaseServiceImpl.class);

    private final ProyectoFaseRepository proyectoFaseRepository;

    private final ProyectoFaseMapper proyectoFaseMapper;

    public ProyectoFaseServiceImpl(ProyectoFaseRepository proyectoFaseRepository, ProyectoFaseMapper proyectoFaseMapper) {
        this.proyectoFaseRepository = proyectoFaseRepository;
        this.proyectoFaseMapper = proyectoFaseMapper;
    }

    /**
     * Save a proyectoFase.
     *
     * @param proyectoFaseDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProyectoFaseDTO save(ProyectoFaseDTO proyectoFaseDTO) {
        log.debug("Request to save ProyectoFase : {}", proyectoFaseDTO);
        ProyectoFase proyectoFase = proyectoFaseMapper.toEntity(proyectoFaseDTO);
        proyectoFase = proyectoFaseRepository.save(proyectoFase);
        return proyectoFaseMapper.toDto(proyectoFase);
    }

    /**
     * Get all the proyectoFases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoFaseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProyectoFases");
        return proyectoFaseRepository.findAll(pageable)
            .map(proyectoFaseMapper::toDto);
    }


    /**
     * Get one proyectoFase by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoFaseDTO> findOne(Long id) {
        log.debug("Request to get ProyectoFase : {}", id);
        return proyectoFaseRepository.findById(id)
            .map(proyectoFaseMapper::toDto);
    }

    /**
     * Delete the proyectoFase by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProyectoFase : {}", id);
        proyectoFaseRepository.deleteById(id);
    }
}
