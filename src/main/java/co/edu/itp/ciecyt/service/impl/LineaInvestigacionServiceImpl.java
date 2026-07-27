package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.LineaInvestigacionService;
import co.edu.itp.ciecyt.domain.LineaInvestigacion;
import co.edu.itp.ciecyt.repository.LineaInvestigacionRepository;
import co.edu.itp.ciecyt.service.dto.LineaInvestigacionDTO;
import co.edu.itp.ciecyt.service.mapper.LineaInvestigacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link LineaInvestigacion}.
 */
@Service
@Transactional
public class LineaInvestigacionServiceImpl implements LineaInvestigacionService {

    private final Logger log = LoggerFactory.getLogger(LineaInvestigacionServiceImpl.class);

    private final LineaInvestigacionRepository lineaInvestigacionRepository;

    private final LineaInvestigacionMapper lineaInvestigacionMapper;

    public LineaInvestigacionServiceImpl(LineaInvestigacionRepository lineaInvestigacionRepository, LineaInvestigacionMapper lineaInvestigacionMapper) {
        this.lineaInvestigacionRepository = lineaInvestigacionRepository;
        this.lineaInvestigacionMapper = lineaInvestigacionMapper;
    }

    /**
     * Save a lineaInvestigacion.
     *
     * @param lineaInvestigacionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LineaInvestigacionDTO save(LineaInvestigacionDTO lineaInvestigacionDTO) {
        log.debug("Request to save LineaInvestigacion : {}", lineaInvestigacionDTO);
        LineaInvestigacion lineaInvestigacion = lineaInvestigacionMapper.toEntity(lineaInvestigacionDTO);
        lineaInvestigacion = lineaInvestigacionRepository.save(lineaInvestigacion);
        return lineaInvestigacionMapper.toDto(lineaInvestigacion);
    }

    /**
     * Get all the lineaInvestigacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LineaInvestigacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LineaInvestigacions");
        return lineaInvestigacionRepository.findAll(pageable)
            .map(lineaInvestigacionMapper::toDto);
    }


    /**
     * Get one lineaInvestigacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<LineaInvestigacionDTO> findOne(Long id) {
        log.debug("Request to get LineaInvestigacion : {}", id);
        return lineaInvestigacionRepository.findById(id)
            .map(lineaInvestigacionMapper::toDto);
    }

    /**
     * Delete the lineaInvestigacion by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LineaInvestigacion : {}", id);
        lineaInvestigacionRepository.deleteById(id);
    }
}
