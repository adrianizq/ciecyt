package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.ProyectoHistorialEstado;
import co.edu.itp.ciecyt.repository.ProyectoHistorialEstadoRepository;
import co.edu.itp.ciecyt.service.ProyectoHistorialEstadoService;
import co.edu.itp.ciecyt.service.dto.ProyectoHistorialEstadoDTO;
import co.edu.itp.ciecyt.service.mapper.ProyectoHistorialEstadoMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProyectoHistorialEstado}.
 */
@Service
@Transactional
public class ProyectoHistorialEstadoServiceImpl implements ProyectoHistorialEstadoService {

    private final Logger log = LoggerFactory.getLogger(ProyectoHistorialEstadoServiceImpl.class);

    private final ProyectoHistorialEstadoRepository proyectoHistorialEstadoRepository;

    private final ProyectoHistorialEstadoMapper proyectoHistorialEstadoMapper;

    public ProyectoHistorialEstadoServiceImpl(
        ProyectoHistorialEstadoRepository proyectoHistorialEstadoRepository,
        ProyectoHistorialEstadoMapper proyectoHistorialEstadoMapper
    ) {
        this.proyectoHistorialEstadoRepository = proyectoHistorialEstadoRepository;
        this.proyectoHistorialEstadoMapper = proyectoHistorialEstadoMapper;
    }

    @Override
    public ProyectoHistorialEstadoDTO save(ProyectoHistorialEstadoDTO proyectoHistorialEstadoDTO) {
        log.debug("Request to save ProyectoHistorialEstado : {}", proyectoHistorialEstadoDTO);
        ProyectoHistorialEstado proyectoHistorialEstado = proyectoHistorialEstadoMapper.toEntity(proyectoHistorialEstadoDTO);
        proyectoHistorialEstado = proyectoHistorialEstadoRepository.save(proyectoHistorialEstado);
        return proyectoHistorialEstadoMapper.toDto(proyectoHistorialEstado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoHistorialEstadoDTO> findByProyectoId(Long proyectoId) {
        log.debug("Request to get all ProyectoHistorialEstado by proyectoId : {}", proyectoId);
        return proyectoHistorialEstadoRepository.findByProyectoIdOrderByFechaCambioDesc(proyectoId).stream()
            .map(proyectoHistorialEstadoMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoHistorialEstadoDTO> findOne(Long id) {
        log.debug("Request to get ProyectoHistorialEstado : {}", id);
        return proyectoHistorialEstadoRepository.findById(id).map(proyectoHistorialEstadoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProyectoHistorialEstado : {}", id);
        proyectoHistorialEstadoRepository.deleteById(id);
    }
}
