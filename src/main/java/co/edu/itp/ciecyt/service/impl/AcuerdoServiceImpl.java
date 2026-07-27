package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.AcuerdoService;
import co.edu.itp.ciecyt.domain.Acuerdo;
import co.edu.itp.ciecyt.repository.AcuerdoRepository;
import co.edu.itp.ciecyt.service.dto.AcuerdoDTO;
import co.edu.itp.ciecyt.service.mapper.AcuerdoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Acuerdo}.
 */
@Service
@Transactional
public class AcuerdoServiceImpl implements AcuerdoService {

    private final Logger log = LoggerFactory.getLogger(AcuerdoServiceImpl.class);

    private final AcuerdoRepository acuerdoRepository;

    private final AcuerdoMapper acuerdoMapper;

    public AcuerdoServiceImpl(AcuerdoRepository acuerdoRepository, AcuerdoMapper acuerdoMapper) {
        this.acuerdoRepository = acuerdoRepository;
        this.acuerdoMapper = acuerdoMapper;
    }

    /**
     * Save a acuerdo.
     *
     * @param acuerdoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AcuerdoDTO save(AcuerdoDTO acuerdoDTO) {
        log.debug("Request to save Acuerdo : {}", acuerdoDTO);
        Acuerdo acuerdo = acuerdoMapper.toEntity(acuerdoDTO);
        acuerdo = acuerdoRepository.save(acuerdo);
        return acuerdoMapper.toDto(acuerdo);
    }

    /**
     * Get all the acuerdos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AcuerdoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Acuerdos");
        return acuerdoRepository.findAll(pageable)
            .map(acuerdoMapper::toDto);
    }


    /**
     * Get one acuerdo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AcuerdoDTO> findOne(Long id) {
        log.debug("Request to get Acuerdo : {}", id);
        return acuerdoRepository.findById(id)
            .map(acuerdoMapper::toDto);
    }

    /**
     * Delete the acuerdo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Acuerdo : {}", id);
        acuerdoRepository.deleteById(id);
    }
}
