package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.EstadoModalidad;
import co.edu.itp.ciecyt.repository.EstadoModalidadRepository;
import co.edu.itp.ciecyt.service.EstadoModalidadService;
import co.edu.itp.ciecyt.service.dto.EstadoModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.EstadoModalidadMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstadoModalidad}.
 */
@Service
@Transactional
public class EstadoModalidadServiceImpl implements EstadoModalidadService {

    private final Logger log = LoggerFactory.getLogger(EstadoModalidadServiceImpl.class);

    private final EstadoModalidadRepository estadoModalidadRepository;

    private final EstadoModalidadMapper estadoModalidadMapper;

    public EstadoModalidadServiceImpl(
        EstadoModalidadRepository estadoModalidadRepository,
        EstadoModalidadMapper estadoModalidadMapper
    ) {
        this.estadoModalidadRepository = estadoModalidadRepository;
        this.estadoModalidadMapper = estadoModalidadMapper;
    }

    @Override
    public EstadoModalidadDTO save(EstadoModalidadDTO estadoModalidadDTO) {
        log.debug("Request to save EstadoModalidad : {}", estadoModalidadDTO);
        EstadoModalidad estadoModalidad = estadoModalidadMapper.toEntity(estadoModalidadDTO);
        estadoModalidad = estadoModalidadRepository.save(estadoModalidad);
        return estadoModalidadMapper.toDto(estadoModalidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EstadoModalidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstadoModalidads");
        return estadoModalidadRepository.findAll(pageable).map(estadoModalidadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadoModalidadDTO> findByModalidadId(Long modalidadId) {
        log.debug("Request to get all EstadoModalidads of modalidad : {}", modalidadId);
        return estadoModalidadRepository.findByEstadoModalidadModalidadIdAndActivoTrueOrderByOrdenAsc(modalidadId).stream()
            .map(estadoModalidadMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadoModalidadDTO> findOne(Long id) {
        log.debug("Request to get EstadoModalidad : {}", id);
        return estadoModalidadRepository.findById(id).map(estadoModalidadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstadoModalidad : {}", id);
        estadoModalidadRepository.deleteById(id);
    }
}
