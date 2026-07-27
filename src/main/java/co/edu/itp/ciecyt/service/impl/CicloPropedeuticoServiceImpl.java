package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.CicloPropedeuticoService;
import co.edu.itp.ciecyt.domain.CicloPropedeutico;
import co.edu.itp.ciecyt.repository.CicloPropedeuticoRepository;
import co.edu.itp.ciecyt.service.dto.CicloPropedeuticoDTO;
import co.edu.itp.ciecyt.service.mapper.CicloPropedeuticoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CicloPropedeutico}.
 */
@Service
@Transactional
public class CicloPropedeuticoServiceImpl implements CicloPropedeuticoService {

    private final Logger log = LoggerFactory.getLogger(CicloPropedeuticoServiceImpl.class);

    private final CicloPropedeuticoRepository cicloPropedeuticoRepository;

    private final CicloPropedeuticoMapper cicloPropedeuticoMapper;

    public CicloPropedeuticoServiceImpl(CicloPropedeuticoRepository cicloPropedeuticoRepository, CicloPropedeuticoMapper cicloPropedeuticoMapper) {
        this.cicloPropedeuticoRepository = cicloPropedeuticoRepository;
        this.cicloPropedeuticoMapper = cicloPropedeuticoMapper;
    }

    /**
     * Save a cicloPropedeutico.
     *
     * @param cicloPropedeuticoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CicloPropedeuticoDTO save(CicloPropedeuticoDTO cicloPropedeuticoDTO) {
        log.debug("Request to save CicloPropedeutico : {}", cicloPropedeuticoDTO);
        CicloPropedeutico cicloPropedeutico = cicloPropedeuticoMapper.toEntity(cicloPropedeuticoDTO);
        cicloPropedeutico = cicloPropedeuticoRepository.save(cicloPropedeutico);
        return cicloPropedeuticoMapper.toDto(cicloPropedeutico);
    }

    /**
     * Get all the cicloPropedeuticos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CicloPropedeuticoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CicloPropedeuticos");
        return cicloPropedeuticoRepository.findAll(pageable)
            .map(cicloPropedeuticoMapper::toDto);
    }


    /**
     * Get one cicloPropedeutico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CicloPropedeuticoDTO> findOne(Long id) {
        log.debug("Request to get CicloPropedeutico : {}", id);
        return cicloPropedeuticoRepository.findById(id)
            .map(cicloPropedeuticoMapper::toDto);
    }

    /**
     * Delete the cicloPropedeutico by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CicloPropedeutico : {}", id);
        cicloPropedeuticoRepository.deleteById(id);
    }
}
