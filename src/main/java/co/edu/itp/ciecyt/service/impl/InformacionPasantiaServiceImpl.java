package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.ElementoProyecto;
import co.edu.itp.ciecyt.service.InformacionPasantiaService;
import co.edu.itp.ciecyt.domain.InformacionPasantia;
import  co.edu.itp.ciecyt.repository.InformacionPasantiaRepository;
import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import  co.edu.itp.ciecyt.service.dto.InformacionPasantiaDTO;
import co.edu.itp.ciecyt.service.mapper.InformacionPasantiaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InformacionPasantia}.
 */
@Service
@Transactional
public class InformacionPasantiaServiceImpl implements InformacionPasantiaService {

    private final Logger log = LoggerFactory.getLogger(InformacionPasantiaServiceImpl.class);

    private final InformacionPasantiaRepository informacionPasantiaRepository;

    private final InformacionPasantiaMapper informacionPasantiaMapper;

    public InformacionPasantiaServiceImpl(InformacionPasantiaRepository informacionPasantiaRepository, InformacionPasantiaMapper informacionPasantiaMapper) {
        this.informacionPasantiaRepository = informacionPasantiaRepository;
        this.informacionPasantiaMapper = informacionPasantiaMapper;
    }

    @Override
    public InformacionPasantiaDTO save(InformacionPasantiaDTO informacionPasantiaDTO) {
        log.debug("Request to save InformacionPasantia : {}", informacionPasantiaDTO);
        InformacionPasantia informacionPasantia = informacionPasantiaMapper.toEntity(informacionPasantiaDTO);
        informacionPasantia = informacionPasantiaRepository.save(informacionPasantia);
        return informacionPasantiaMapper.toDto(informacionPasantia);
    }
    /**
       * Get all the informacionPasantia.
        *
        * @param pageable the pagination information.
        * @return the list of entities.
     * */


    @Override
    @Transactional(readOnly = true)
    public Page<InformacionPasantiaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InformacionPasantia");
        return informacionPasantiaRepository.findAll(pageable)
            .map(informacionPasantiaMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InformacionPasantiaDTO> findOne(Long id) {
        log.debug("Request to get InformacionPasantia : {}", id);
        return informacionPasantiaRepository.findById(id)
            .map(informacionPasantiaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InformacionPasantia : {}", id);
        informacionPasantiaRepository.deleteById(id);
    }

    ////////////////7777777777
    @Override
    @Transactional(readOnly = true)
    public InformacionPasantiaDTO findByInformacionPasantiaProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get all InformacionPasantiaDTO whit a idProyecto");
        InformacionPasantiaDTO dto = new InformacionPasantiaDTO();
        InformacionPasantia obj = informacionPasantiaRepository.findByInformacionPasantiaProyectoId(idProyecto);
        dto = informacionPasantiaMapper.toDto(obj);
        return dto;
    }
}
