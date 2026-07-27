package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.CicloService;
import co.edu.itp.ciecyt.domain.Ciclo;
import co.edu.itp.ciecyt.repository.CicloRepository;
import co.edu.itp.ciecyt.service.dto.CicloDTO;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.CicloMapper;
import co.edu.itp.ciecyt.service.mapper.ModalidadMapper;
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
 * Service Implementation for managing {@link Ciclo}.
 */
@Service
@Transactional
public class CicloServiceImpl implements CicloService {

    private final Logger log = LoggerFactory.getLogger(CicloServiceImpl.class);

    private final CicloRepository cicloRepository;

    private final CicloMapper cicloMapper;

    private final ModalidadMapper modalidadMapper;

    public CicloServiceImpl(CicloRepository cicloRepository, CicloMapper cicloMapper, ModalidadMapper modalidadMapper) {
        this.cicloRepository = cicloRepository;
        this.cicloMapper = cicloMapper;
        this.modalidadMapper = modalidadMapper;
    }

    /**
     * Save a ciclo.
     *
     * @param cicloDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CicloDTO save(CicloDTO cicloDTO) {
        log.debug("Request to save Ciclo : {}", cicloDTO);
        Ciclo ciclo = cicloMapper.toEntity(cicloDTO);
        ciclo = cicloRepository.save(ciclo);
        return cicloMapper.toDto(ciclo);
    }

    /**
     * Get all the ciclos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CicloDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ciclos");
        return cicloRepository.findAll(pageable).map(cicloMapper::toDto);
    }

    /**
     * Get all the ciclos as a list.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CicloDTO> findAllList() {
        log.debug("Request to get all Ciclos as list");
        return cicloRepository.findAll().stream().map(cicloMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Get the "id" ciclo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CicloDTO> findOne(Long id) {
        log.debug("Request to get Ciclo : {}", id);
        return cicloRepository.findById(id).map(cicloMapper::toDto);
    }

    /**
     * Get modalidades for a given ciclo.
     *
     * @param cicloId the ciclo id.
     * @return the list of modalidad DTOs.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ModalidadDTO> findModalidadesByCicloId(Long cicloId) {
        log.debug("Request to get modalidades for Ciclo : {}", cicloId);
        return cicloRepository.findById(cicloId)
            .map(ciclo -> ciclo.getModalidades().stream()
                .map(modalidadMapper::toDto)
                .collect(Collectors.toList()))
            .orElse(java.util.Collections.emptyList());
    }

    /**
     * Delete the "id" ciclo.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Ciclo : {}", id);
        cicloRepository.deleteById(id);
    }
}
