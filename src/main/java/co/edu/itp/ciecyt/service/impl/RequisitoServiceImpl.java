package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Requisito;
import co.edu.itp.ciecyt.repository.RequisitoRepository;
import co.edu.itp.ciecyt.service.RequisitoService;
import co.edu.itp.ciecyt.service.dto.RequisitoDTO;
import co.edu.itp.ciecyt.service.mapper.RequisitoMapper;
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
 * Service Implementation for managing {@link Requisito}.
 */
@Service
@Transactional
public class RequisitoServiceImpl implements RequisitoService {

    private final Logger log = LoggerFactory.getLogger(RequisitoServiceImpl.class);

    private final RequisitoRepository requisitoRepository;

    private final RequisitoMapper requisitoMapper;

    public RequisitoServiceImpl(RequisitoRepository requisitoRepository, RequisitoMapper requisitoMapper) {
        this.requisitoRepository = requisitoRepository;
        this.requisitoMapper = requisitoMapper;
    }

    @Override
    public RequisitoDTO save(RequisitoDTO requisitoDTO) {
        log.debug("Request to save Requisito : {}", requisitoDTO);
        Requisito requisito = requisitoMapper.toEntity(requisitoDTO);
        requisito = requisitoRepository.save(requisito);
        return requisitoMapper.toDto(requisito);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequisitoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requisitos");
        return requisitoRepository.findAll(pageable).map(requisitoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequisitoDTO> findAllActivos() {
        log.debug("Request to get all active Requisitos");
        return requisitoRepository.findAllByActivoTrueOrderByNombreAsc().stream()
            .map(requisitoMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequisitoDTO> findByModalidad(Long modalidadId) {
        log.debug("Request to get all active Requisitos of modalidad : {}", modalidadId);
        return requisitoRepository.findByRequisitoModalidadIdOrRequisitoModalidadIsNullOrderByNombreAsc(modalidadId).stream()
            .filter(r -> Boolean.TRUE.equals(r.getActivo()))
            .map(requisitoMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequisitoDTO> findOne(Long id) {
        log.debug("Request to get Requisito : {}", id);
        return requisitoRepository.findById(id).map(requisitoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Requisito : {}", id);
        requisitoRepository.deleteById(id);
    }
}
