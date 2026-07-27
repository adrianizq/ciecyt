package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.TipoPreguntaService;
import co.edu.itp.ciecyt.domain.TipoPregunta;
import co.edu.itp.ciecyt.repository.TipoPreguntaRepository;
import co.edu.itp.ciecyt.service.dto.TipoPreguntaDTO;
import co.edu.itp.ciecyt.service.mapper.TipoPreguntaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoPregunta}.
 */
@Service
@Transactional
public class TipoPreguntaServiceImpl implements TipoPreguntaService {

    private final Logger log = LoggerFactory.getLogger(TipoPreguntaServiceImpl.class);

    private final TipoPreguntaRepository tipoPreguntaRepository;

    private final TipoPreguntaMapper tipoPreguntaMapper;

    public TipoPreguntaServiceImpl(TipoPreguntaRepository tipoPreguntaRepository, TipoPreguntaMapper tipoPreguntaMapper) {
        this.tipoPreguntaRepository = tipoPreguntaRepository;
        this.tipoPreguntaMapper = tipoPreguntaMapper;
    }

    /**
     * Save a tipoPregunta.
     *
     * @param tipoPreguntaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoPreguntaDTO save(TipoPreguntaDTO tipoPreguntaDTO) {
        log.debug("Request to save TipoPregunta : {}", tipoPreguntaDTO);
        TipoPregunta tipoPregunta = tipoPreguntaMapper.toEntity(tipoPreguntaDTO);
        tipoPregunta = tipoPreguntaRepository.save(tipoPregunta);
        return tipoPreguntaMapper.toDto(tipoPregunta);
    }

    /**
     * Get all the tipoPreguntas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TipoPreguntaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TipoPreguntas");
        return tipoPreguntaRepository.findAll(pageable)
            .map(tipoPreguntaMapper::toDto);
    }


    /**
     * Get one tipoPregunta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoPreguntaDTO> findOne(Long id) {
        log.debug("Request to get TipoPregunta : {}", id);
        return tipoPreguntaRepository.findById(id)
            .map(tipoPreguntaMapper::toDto);
    }

    /**
     * Delete the tipoPregunta by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoPregunta : {}", id);
        tipoPreguntaRepository.deleteById(id);
    }
}
