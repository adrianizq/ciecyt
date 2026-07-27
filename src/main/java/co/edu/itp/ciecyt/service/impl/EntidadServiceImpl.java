package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.EntidadService;
import co.edu.itp.ciecyt.domain.Entidad;
import co.edu.itp.ciecyt.repository.EntidadRepository;
import co.edu.itp.ciecyt.service.dto.EntidadDTO;
import co.edu.itp.ciecyt.service.mapper.EntidadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Entidad}.
 */
@Service
@Transactional
public class EntidadServiceImpl implements EntidadService {

    private final Logger log = LoggerFactory.getLogger(EntidadServiceImpl.class);

    private final EntidadRepository entidadRepository;

    private final EntidadMapper entidadMapper;

    public EntidadServiceImpl(EntidadRepository entidadRepository, EntidadMapper entidadMapper) {
        this.entidadRepository = entidadRepository;
        this.entidadMapper = entidadMapper;
    }

    /**
     * Save a entidad.
     *
     * @param entidadDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EntidadDTO save(EntidadDTO entidadDTO) {
        log.debug("Request to save Entidad : {}", entidadDTO);
        Entidad entidad = entidadMapper.toEntity(entidadDTO);
        entidad = entidadRepository.save(entidad);
        return entidadMapper.toDto(entidad);
    }

    /**
     * Get all the entidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Entidads");
        return entidadRepository.findAll(pageable)
            .map(entidadMapper::toDto);
    }


    /**
     * Get one entidad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EntidadDTO> findOne(Long id) {
        log.debug("Request to get Entidad : {}", id);
        return entidadRepository.findById(id)
            .map(entidadMapper::toDto);
    }

    /**
     * Delete the entidad by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entidad : {}", id);
        entidadRepository.deleteById(id);
    }
}
