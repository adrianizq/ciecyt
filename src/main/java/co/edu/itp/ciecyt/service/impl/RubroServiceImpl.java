package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.RubroService;
import co.edu.itp.ciecyt.domain.Rubro;
import co.edu.itp.ciecyt.repository.RubroRepository;
import co.edu.itp.ciecyt.service.dto.RubroDTO;
import co.edu.itp.ciecyt.service.mapper.RubroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Rubro}.
 */
@Service
@Transactional
public class RubroServiceImpl implements RubroService {

    private final Logger log = LoggerFactory.getLogger(RubroServiceImpl.class);

    private final RubroRepository rubroRepository;

    private final RubroMapper rubroMapper;

    public RubroServiceImpl(RubroRepository rubroRepository, RubroMapper rubroMapper) {
        this.rubroRepository = rubroRepository;
        this.rubroMapper = rubroMapper;
    }

    /**
     * Save a rubro.
     *
     * @param rubroDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public RubroDTO save(RubroDTO rubroDTO) {
        log.debug("Request to save Rubro : {}", rubroDTO);
        Rubro rubro = rubroMapper.toEntity(rubroDTO);
        rubro = rubroRepository.save(rubro);
        return rubroMapper.toDto(rubro);
    }

    /**
     * Get all the rubros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RubroDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rubros");
        return rubroRepository.findAll(pageable)
            .map(rubroMapper::toDto);
    }


    /**
     * Get one rubro by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RubroDTO> findOne(Long id) {
        log.debug("Request to get Rubro : {}", id);
        return rubroRepository.findById(id)
            .map(rubroMapper::toDto);
    }

    /**
     * Delete the rubro by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rubro : {}", id);
        rubroRepository.deleteById(id);
    }
}
