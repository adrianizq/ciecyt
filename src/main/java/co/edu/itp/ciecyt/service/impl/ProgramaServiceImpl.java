package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.ProgramaService;
import co.edu.itp.ciecyt.domain.Programa;
import co.edu.itp.ciecyt.repository.ProgramaRepository;
import co.edu.itp.ciecyt.service.dto.ProgramaDTO;
import co.edu.itp.ciecyt.service.mapper.ProgramaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Programa}.
 */
@Service
@Transactional
public class ProgramaServiceImpl implements ProgramaService {

    private final Logger log = LoggerFactory.getLogger(ProgramaServiceImpl.class);

    private final ProgramaRepository programaRepository;

    private final ProgramaMapper programaMapper;

    public ProgramaServiceImpl(ProgramaRepository programaRepository, ProgramaMapper programaMapper) {
        this.programaRepository = programaRepository;
        this.programaMapper = programaMapper;
    }

    @Override
    public ProgramaDTO save(ProgramaDTO programaDTO) {
        log.debug("Request to save Programa : {}", programaDTO);
        Programa programa = programaMapper.toEntity(programaDTO);
        programa = programaRepository.save(programa);
        return programaMapper.toDto(programa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramaDTO> findAll() {
        log.debug("Request to get all Programas");
        return programaRepository.findAll().stream()
            .map(programaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramaDTO> findOne(Long id) {
        log.debug("Request to get Programa : {}", id);
        return programaRepository.findById(id)
            .map(programaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Programa : {}", id);
        programaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramaDTO> findByCiclo(String ciclo) {
        log.debug("Request to get Programas by ciclo : {}", ciclo);
        String normalized = normalizeAccents(ciclo);
        return programaRepository.findAll().stream()
            .filter(p -> p.getCiclo() != null && normalizeAccents(p.getCiclo()).equalsIgnoreCase(normalized))
            .map(programaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private String normalizeAccents(String text) {
        if (text == null) return "";
        return text
            .replace('á', 'a').replace('é', 'e').replace('í', 'i').replace('ó', 'o').replace('ú', 'u')
            .replace('Á', 'A').replace('É', 'E').replace('Í', 'I').replace('Ó', 'O').replace('Ú', 'U')
            .replace('ñ', 'n').replace('Ñ', 'N');
    }
}
