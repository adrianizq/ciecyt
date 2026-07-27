package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import co.edu.itp.ciecyt.service.EntidadFinanciadoraService;
import co.edu.itp.ciecyt.domain.EntidadFinanciadora;
import co.edu.itp.ciecyt.repository.EntidadFinanciadoraRepository;
import co.edu.itp.ciecyt.service.dto.EntidadFinanciadoraDTO;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import co.edu.itp.ciecyt.service.mapper.EntidadFinanciadoraMapper;
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
 * Service Implementation for managing {@link EntidadFinanciadora}.
 */
@Service
@Transactional
public class EntidadFinanciadoraServiceImpl implements EntidadFinanciadoraService {

    private final Logger log = LoggerFactory.getLogger(EntidadFinanciadoraServiceImpl.class);

    private final EntidadFinanciadoraRepository entidadFinanciadoraRepository;

    private final EntidadFinanciadoraMapper entidadFinanciadoraMapper;

    public EntidadFinanciadoraServiceImpl(EntidadFinanciadoraRepository entidadFinanciadoraRepository, EntidadFinanciadoraMapper entidadFinanciadoraMapper) {
        this.entidadFinanciadoraRepository = entidadFinanciadoraRepository;
        this.entidadFinanciadoraMapper = entidadFinanciadoraMapper;
    }

    /**
     * Save a entidadFinanciadora.
     *
     * @param entidadFinanciadoraDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EntidadFinanciadoraDTO save(EntidadFinanciadoraDTO entidadFinanciadoraDTO) {
        log.debug("Request to save EntidadFinanciadora : {}", entidadFinanciadoraDTO);
        EntidadFinanciadora entidadFinanciadora = entidadFinanciadoraMapper.toEntity(entidadFinanciadoraDTO);
        entidadFinanciadora = entidadFinanciadoraRepository.save(entidadFinanciadora);
        return entidadFinanciadoraMapper.toDto(entidadFinanciadora);
    }

    /**
     * Get all the entidadFinanciadoras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EntidadFinanciadoraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EntidadFinanciadoras");
        return entidadFinanciadoraRepository.findAll(pageable)
            .map(entidadFinanciadoraMapper::toDto);
    }


    /**
     * Get one entidadFinanciadora by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EntidadFinanciadoraDTO> findOne(Long id) {
        log.debug("Request to get EntidadFinanciadora : {}", id);
        return entidadFinanciadoraRepository.findById(id)
            .map(entidadFinanciadoraMapper::toDto);
    }

    /**
     * Delete the entidadFinanciadora by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntidadFinanciadora : {}", id);
        entidadFinanciadoraRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EntidadFinanciadoraDTO> findByEntidadFinanciadoraProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get all ImpactosEsperados whit a idProyecto");
        List<EntidadFinanciadoraDTO> listDTO = new ArrayList<>();
        List<EntidadFinanciadora> list = entidadFinanciadoraRepository.findByEntidadFinanciadoraProyectoId(idProyecto);


        for (EntidadFinanciadora obj : list) {
            listDTO.add(entidadFinanciadoraMapper.toDto(obj));
        }
        return listDTO;
    }
}
