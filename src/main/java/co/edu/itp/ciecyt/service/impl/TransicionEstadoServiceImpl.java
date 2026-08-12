package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.TransicionEstado;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.repository.TransicionEstadoRepository;
import co.edu.itp.ciecyt.service.TransicionEstadoService;
import co.edu.itp.ciecyt.service.dto.TransicionEstadoDTO;
import co.edu.itp.ciecyt.service.mapper.TransicionEstadoMapper;
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
 * Service Implementation for managing {@link TransicionEstado}.
 */
@Service
@Transactional
public class TransicionEstadoServiceImpl implements TransicionEstadoService {

    private final Logger log = LoggerFactory.getLogger(TransicionEstadoServiceImpl.class);

    private final TransicionEstadoRepository transicionEstadoRepository;

    private final TransicionEstadoMapper transicionEstadoMapper;

    public TransicionEstadoServiceImpl(
        TransicionEstadoRepository transicionEstadoRepository,
        TransicionEstadoMapper transicionEstadoMapper
    ) {
        this.transicionEstadoRepository = transicionEstadoRepository;
        this.transicionEstadoMapper = transicionEstadoMapper;
    }

    @Override
    public TransicionEstadoDTO save(TransicionEstadoDTO transicionEstadoDTO) {
        log.debug("Request to save TransicionEstado : {}", transicionEstadoDTO);
        TransicionEstado transicionEstado = transicionEstadoMapper.toEntity(transicionEstadoDTO);
        transicionEstado = transicionEstadoRepository.save(transicionEstado);
        return transicionEstadoMapper.toDto(transicionEstado);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransicionEstadoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransicionEstados");
        return transicionEstadoRepository.findAll(pageable).map(transicionEstadoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransicionEstadoDTO> findByModalidadId(Long modalidadId) {
        log.debug("Request to get all TransicionEstados of modalidad : {}", modalidadId);
        return transicionEstadoRepository.findByTransicionEstadoModalidadIdAndActivoTrue(modalidadId).stream()
            .map(transicionEstadoMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransicionEstadoDTO> findByModalidadIdAndEstadoOrigen(Long modalidadId, EnumEstadoProyecto estadoOrigen) {
        log.debug("Request to get TransicionEstados of modalidad {} desde {}", modalidadId, estadoOrigen);
        return transicionEstadoRepository
            .findByTransicionEstadoModalidadIdAndActivoTrueAndEstadoOrigen(modalidadId, estadoOrigen).stream()
            .map(transicionEstadoMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TransicionEstadoDTO> findOne(Long id) {
        log.debug("Request to get TransicionEstado : {}", id);
        return transicionEstadoRepository.findById(id).map(transicionEstadoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TransicionEstado : {}", id);
        transicionEstadoRepository.deleteById(id);
    }
}
