package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.RetroalimentacionService;
import co.edu.itp.ciecyt.domain.Retroalimentacion;
import co.edu.itp.ciecyt.repository.RetroalimentacionRepository;
import co.edu.itp.ciecyt.service.dto.RetroalimentacionDTO;
import co.edu.itp.ciecyt.service.mapper.RetroalimentacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Retroalimentacion}.
 */
@Service
@Transactional
public class RetroalimentacionServiceImpl implements RetroalimentacionService {

    private final Logger log = LoggerFactory.getLogger(RetroalimentacionServiceImpl.class);

    private final RetroalimentacionRepository retroalimentacionRepository;

    private final RetroalimentacionMapper retroalimentacionMapper;

    public RetroalimentacionServiceImpl(RetroalimentacionRepository retroalimentacionRepository, RetroalimentacionMapper retroalimentacionMapper) {
        this.retroalimentacionRepository = retroalimentacionRepository;
        this.retroalimentacionMapper = retroalimentacionMapper;
    }

    /**
     * Save a retroalimentacion.
     *
     * @param retroalimentacionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RetroalimentacionDTO save(RetroalimentacionDTO retroalimentacionDTO) {
        log.debug("Request to save Retroalimentacion : {}", retroalimentacionDTO);
        Retroalimentacion retroalimentacion = retroalimentacionMapper.toEntity(retroalimentacionDTO);
        retroalimentacion = retroalimentacionRepository.save(retroalimentacion);
        return retroalimentacionMapper.toDto(retroalimentacion);
    }

    /**
     * Get all the retroalimentacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RetroalimentacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Retroalimentacions");
        return retroalimentacionRepository.findAll(pageable)
            .map(retroalimentacionMapper::toDto);
    }


    /**
     * Get one retroalimentacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RetroalimentacionDTO> findOne(Long id) {
        log.debug("Request to get Retroalimentacion : {}", id);
        return retroalimentacionRepository.findById(id)
            .map(retroalimentacionMapper::toDto);
    }

    /**
     * Delete the retroalimentacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Retroalimentacion : {}", id);
        retroalimentacionRepository.deleteById(id);
    }
}
