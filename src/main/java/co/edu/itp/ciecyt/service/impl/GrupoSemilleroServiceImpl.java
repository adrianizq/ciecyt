package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.GrupoSemilleroService;
import co.edu.itp.ciecyt.domain.GrupoSemillero;
import co.edu.itp.ciecyt.repository.GrupoSemilleroRepository;
import co.edu.itp.ciecyt.service.dto.GrupoSemilleroDTO;
import co.edu.itp.ciecyt.service.mapper.GrupoSemilleroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GrupoSemillero}.
 */
@Service
@Transactional
public class GrupoSemilleroServiceImpl implements GrupoSemilleroService {

    private final Logger log = LoggerFactory.getLogger(GrupoSemilleroServiceImpl.class);

    private final GrupoSemilleroRepository grupoSemilleroRepository;

    private final GrupoSemilleroMapper grupoSemilleroMapper;

    public GrupoSemilleroServiceImpl(GrupoSemilleroRepository grupoSemilleroRepository, GrupoSemilleroMapper grupoSemilleroMapper) {
        this.grupoSemilleroRepository = grupoSemilleroRepository;
        this.grupoSemilleroMapper = grupoSemilleroMapper;
    }

    /**
     * Save a grupoSemillero.
     *
     * @param grupoSemilleroDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GrupoSemilleroDTO save(GrupoSemilleroDTO grupoSemilleroDTO) {
        log.debug("Request to save GrupoSemillero : {}", grupoSemilleroDTO);
        GrupoSemillero grupoSemillero = grupoSemilleroMapper.toEntity(grupoSemilleroDTO);
        grupoSemillero = grupoSemilleroRepository.save(grupoSemillero);
        return grupoSemilleroMapper.toDto(grupoSemillero);
    }

    /**
     * Get all the grupoSemilleros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GrupoSemilleroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GrupoSemilleros");
        return grupoSemilleroRepository.findAll(pageable)
            .map(grupoSemilleroMapper::toDto);
    }


    /**
     * Get one grupoSemillero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GrupoSemilleroDTO> findOne(Long id) {
        log.debug("Request to get GrupoSemillero : {}", id);
        return grupoSemilleroRepository.findById(id)
            .map(grupoSemilleroMapper::toDto);
    }

    /**
     * Delete the grupoSemillero by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GrupoSemillero : {}", id);
        grupoSemilleroRepository.deleteById(id);
    }
}
