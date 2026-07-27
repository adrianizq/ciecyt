package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.SolicitudService;
import co.edu.itp.ciecyt.domain.Solicitud;
import co.edu.itp.ciecyt.repository.SolicitudRepository;
import co.edu.itp.ciecyt.service.dto.SolicitudDTO;
import co.edu.itp.ciecyt.service.mapper.SolicitudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Solicitud}.
 */
@Service
@Transactional
public class SolicitudServiceImpl implements SolicitudService {

    private final Logger log = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    private final SolicitudRepository solicitudRepository;

    private final SolicitudMapper solicitudMapper;

    public SolicitudServiceImpl(SolicitudRepository solicitudRepository, SolicitudMapper solicitudMapper) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudMapper = solicitudMapper;
    }

    /**
     * Save a solicitud.
     *
     * @param solicitudDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SolicitudDTO save(SolicitudDTO solicitudDTO) {
        log.debug("Request to save Solicitud : {}", solicitudDTO);
        Solicitud solicitud = solicitudMapper.toEntity(solicitudDTO);
        solicitud = solicitudRepository.save(solicitud);
        return solicitudMapper.toDto(solicitud);
    }

    /**
     * Get all the solicituds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SolicitudDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Solicituds");
        return solicitudRepository.findAll(pageable)
            .map(solicitudMapper::toDto);
    }


    /**
     * Get one solicitud by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SolicitudDTO> findOne(Long id) {
        log.debug("Request to get Solicitud : {}", id);
        return solicitudRepository.findById(id)
            .map(solicitudMapper::toDto);
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
