package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.InvestigacionTipo;
import co.edu.itp.ciecyt.repository.InvestigacionTipoRepository;
import co.edu.itp.ciecyt.service.InvestigacionTipoService;
import co.edu.itp.ciecyt.service.dto.InvestigacionTipoDTO;
import co.edu.itp.ciecyt.service.mapper.InvestigacionTipoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link InvestigacionTipo}.
 */
@Service
@Transactional
public class InvestigacionTipoServiceImpl implements InvestigacionTipoService {

    private final Logger log = LoggerFactory.getLogger(InvestigacionTipoServiceImpl.class);

    private final InvestigacionTipoRepository investigacionTipoRepository;

    private final InvestigacionTipoMapper investigacionTipoMapper;

    public InvestigacionTipoServiceImpl(
        InvestigacionTipoRepository investigacionTipoRepository,
        InvestigacionTipoMapper investigacionTipoMapper
    ) {
        this.investigacionTipoRepository = investigacionTipoRepository;
        this.investigacionTipoMapper = investigacionTipoMapper;
    }

    @Override
    public InvestigacionTipoDTO save(InvestigacionTipoDTO investigacionTipoDTO) {
        log.debug("Request to save InvestigacionTipo : {}", investigacionTipoDTO);
        InvestigacionTipo investigacionTipo = investigacionTipoMapper.toEntity(investigacionTipoDTO);
        investigacionTipo = investigacionTipoRepository.save(investigacionTipo);
        return investigacionTipoMapper.toDto(investigacionTipo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvestigacionTipoDTO> findAll() {
        log.debug("Request to get all InvestigacionTipos");
        return investigacionTipoRepository
            .findAll()
            .stream()
            .map(investigacionTipoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InvestigacionTipoDTO> findOne(Long id) {
        log.debug("Request to get InvestigacionTipo : {}", id);
        return investigacionTipoRepository.findById(id).map(investigacionTipoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InvestigacionTipo : {}", id);
        investigacionTipoRepository.deleteById(id);
    }

    public InvestigacionTipo findByInvestigacionTipo(String tipo) {

        return investigacionTipoRepository.findByInvestigacionTipo(tipo);
    }
}
