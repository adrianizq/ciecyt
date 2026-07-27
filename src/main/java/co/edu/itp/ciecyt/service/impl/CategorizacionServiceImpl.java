package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.CategorizacionService;
import co.edu.itp.ciecyt.domain.Categorizacion;
import co.edu.itp.ciecyt.repository.CategorizacionRepository;
import co.edu.itp.ciecyt.service.dto.CategorizacionDTO;
import co.edu.itp.ciecyt.service.mapper.CategorizacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Categorizacion}.
 */
@Service
@Transactional
public class CategorizacionServiceImpl implements CategorizacionService {

    private final Logger log = LoggerFactory.getLogger(CategorizacionServiceImpl.class);

    private final CategorizacionRepository categorizacionRepository;

    private final CategorizacionMapper categorizacionMapper;

    public CategorizacionServiceImpl(CategorizacionRepository categorizacionRepository, CategorizacionMapper categorizacionMapper) {
        this.categorizacionRepository = categorizacionRepository;
        this.categorizacionMapper = categorizacionMapper;
    }

    /**
     * Save a categorizacion.
     *
     * @param categorizacionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CategorizacionDTO save(CategorizacionDTO categorizacionDTO) {
        log.debug("Request to save Categorizacion : {}", categorizacionDTO);
        Categorizacion categorizacion = categorizacionMapper.toEntity(categorizacionDTO);
        categorizacion = categorizacionRepository.save(categorizacion);
        return categorizacionMapper.toDto(categorizacion);
    }

    /**
     * Get all the categorizacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CategorizacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Categorizacions");
        return categorizacionRepository.findAll(pageable)
            .map(categorizacionMapper::toDto);
    }


    /**
     * Get one categorizacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CategorizacionDTO> findOne(Long id) {
        log.debug("Request to get Categorizacion : {}", id);
        return categorizacionRepository.findById(id)
            .map(categorizacionMapper::toDto);
    }

    /**
     * Delete the categorizacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categorizacion : {}", id);
        categorizacionRepository.deleteById(id);
    }
}
