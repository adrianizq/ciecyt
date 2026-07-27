package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.CronogramaCiecytService;
import co.edu.itp.ciecyt.domain.CronogramaCiecyt;
import co.edu.itp.ciecyt.repository.CronogramaCiecytRepository;
import co.edu.itp.ciecyt.service.dto.CronogramaCiecytDTO;
import co.edu.itp.ciecyt.service.mapper.CronogramaCiecytMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CronogramaCiecyt}.
 */
@Service
@Transactional
public class CronogramaCiecytServiceImpl implements CronogramaCiecytService {

    private final Logger log = LoggerFactory.getLogger(CronogramaCiecytServiceImpl.class);

    private final CronogramaCiecytRepository cronogramaCiecytRepository;

    private final CronogramaCiecytMapper cronogramaCiecytMapper;

    public CronogramaCiecytServiceImpl(CronogramaCiecytRepository cronogramaCiecytRepository, CronogramaCiecytMapper cronogramaCiecytMapper) {
        this.cronogramaCiecytRepository = cronogramaCiecytRepository;
        this.cronogramaCiecytMapper = cronogramaCiecytMapper;
    }

    /**
     * Save a cronogramaCiecyt.
     *
     * @param cronogramaCiecytDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CronogramaCiecytDTO save(CronogramaCiecytDTO cronogramaCiecytDTO) {
        log.debug("Request to save CronogramaCiecyt : {}", cronogramaCiecytDTO);
        CronogramaCiecyt cronogramaCiecyt = cronogramaCiecytMapper.toEntity(cronogramaCiecytDTO);
        cronogramaCiecyt = cronogramaCiecytRepository.save(cronogramaCiecyt);
        return cronogramaCiecytMapper.toDto(cronogramaCiecyt);
    }

    /**
     * Get all the cronogramaCiecyts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CronogramaCiecytDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CronogramaCiecyts");
        return cronogramaCiecytRepository.findAll(pageable)
            .map(cronogramaCiecytMapper::toDto);
    }


    /**
     * Get one cronogramaCiecyt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CronogramaCiecytDTO> findOne(Long id) {
        log.debug("Request to get CronogramaCiecyt : {}", id);
        return cronogramaCiecytRepository.findById(id)
            .map(cronogramaCiecytMapper::toDto);
    }

    /**
     * Delete the cronogramaCiecyt by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CronogramaCiecyt : {}", id);
        cronogramaCiecytRepository.deleteById(id);
    }
}
