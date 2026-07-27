package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.CronogramaCiecytFasesService;
import co.edu.itp.ciecyt.domain.CronogramaCiecytFases;
import co.edu.itp.ciecyt.repository.CronogramaCiecytFasesRepository;
import co.edu.itp.ciecyt.service.dto.CronogramaCiecytFasesDTO;
import co.edu.itp.ciecyt.service.mapper.CronogramaCiecytFasesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CronogramaCiecytFases}.
 */
@Service
@Transactional
public class CronogramaCiecytFasesServiceImpl implements CronogramaCiecytFasesService {

    private final Logger log = LoggerFactory.getLogger(CronogramaCiecytFasesServiceImpl.class);

    private final CronogramaCiecytFasesRepository cronogramaCiecytFasesRepository;

    private final CronogramaCiecytFasesMapper cronogramaCiecytFasesMapper;

    public CronogramaCiecytFasesServiceImpl(CronogramaCiecytFasesRepository cronogramaCiecytFasesRepository, CronogramaCiecytFasesMapper cronogramaCiecytFasesMapper) {
        this.cronogramaCiecytFasesRepository = cronogramaCiecytFasesRepository;
        this.cronogramaCiecytFasesMapper = cronogramaCiecytFasesMapper;
    }

    /**
     * Save a cronogramaCiecytFases.
     *
     * @param cronogramaCiecytFasesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CronogramaCiecytFasesDTO save(CronogramaCiecytFasesDTO cronogramaCiecytFasesDTO) {
        log.debug("Request to save CronogramaCiecytFases : {}", cronogramaCiecytFasesDTO);
        CronogramaCiecytFases cronogramaCiecytFases = cronogramaCiecytFasesMapper.toEntity(cronogramaCiecytFasesDTO);
        cronogramaCiecytFases = cronogramaCiecytFasesRepository.save(cronogramaCiecytFases);
        return cronogramaCiecytFasesMapper.toDto(cronogramaCiecytFases);
    }

    /**
     * Get all the cronogramaCiecytFases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CronogramaCiecytFasesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CronogramaCiecytFases");
        return cronogramaCiecytFasesRepository.findAll(pageable)
            .map(cronogramaCiecytFasesMapper::toDto);
    }


    /**
     * Get one cronogramaCiecytFases by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CronogramaCiecytFasesDTO> findOne(Long id) {
        log.debug("Request to get CronogramaCiecytFases : {}", id);
        return cronogramaCiecytFasesRepository.findById(id)
            .map(cronogramaCiecytFasesMapper::toDto);
    }

    /**
     * Delete the cronogramaCiecytFases by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CronogramaCiecytFases : {}", id);
        cronogramaCiecytFasesRepository.deleteById(id);
    }
}
