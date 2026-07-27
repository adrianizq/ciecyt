package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.repository.ModalidadRepository;
import co.edu.itp.ciecyt.service.PreguntaModalidadService;
import co.edu.itp.ciecyt.domain.PreguntaModalidad;
import co.edu.itp.ciecyt.repository.PreguntaModalidadRepository;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;
import co.edu.itp.ciecyt.service.dto.PreguntaModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.PreguntaModalidadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link PreguntaModalidad}.
 */
@Service
@Transactional
public class PreguntaModalidadServiceImpl implements PreguntaModalidadService {

    private final Logger log = LoggerFactory.getLogger(PreguntaModalidadServiceImpl.class);

    private final PreguntaModalidadRepository preguntaModalidadRepository;
    private final ModalidadRepository modalidadRepository;

    private final PreguntaModalidadMapper preguntaModalidadMapper;

    public PreguntaModalidadServiceImpl(PreguntaModalidadRepository preguntaModalidadRepository, ModalidadRepository modalidadRepository, PreguntaModalidadMapper preguntaModalidadMapper) {
        this.preguntaModalidadRepository = preguntaModalidadRepository;
        this.modalidadRepository = modalidadRepository;
        this.preguntaModalidadMapper = preguntaModalidadMapper;
    }

    @Override
    public PreguntaModalidadDTO save(PreguntaModalidadDTO preguntaModalidadDTO) {
        log.debug("Request to save PreguntaModalidad : {}", preguntaModalidadDTO);
        PreguntaModalidad preguntaModalidad = preguntaModalidadMapper.toEntity(preguntaModalidadDTO);
        preguntaModalidad = preguntaModalidadRepository.save(preguntaModalidad);
        return preguntaModalidadMapper.toDto(preguntaModalidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaModalidadDTO> findAll() {
        log.debug("Request to get all PreguntaModalidads");
        return preguntaModalidadRepository.findAll().stream()
            .map(preguntaModalidadMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaModalidadDTO> findByModalidad2Id(Long idModalidad){
        return preguntaModalidadRepository.findByModalidad2Id(idModalidad)
            .stream()
            .map(preguntaModalidadMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));

    }

    /*@Override
    public PreguntaModalidadDTO findByPreguntaIdAndModalidad2Id(Long idPregunta, Long idModalidad) {
        PreguntaModalidad preguntaModalidad =preguntaModalidadRepository.findByPreguntaIdAndModalidad2Id(idPregunta, idModalidad);
        return preguntaModalidadMapper.toDto(preguntaModalidad);
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<PreguntaModalidadDTO> findByPreguntaId(Long idPregunta){
        return  preguntaModalidadRepository.findByPreguntaId(idPregunta)
            .stream()
            .map(preguntaModalidadMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }





    @Override
    @Transactional(readOnly = true)
    public Optional<PreguntaModalidadDTO> findOne(Long id) {
        log.debug("Request to get PreguntaModalidad : {}", id);
        return preguntaModalidadRepository.findById(id)
            .map(preguntaModalidadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PreguntaModalidad : {}", id);
        preguntaModalidadRepository.deleteById(id);
    }



}
