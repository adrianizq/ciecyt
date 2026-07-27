package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.FichaTecnicaService;
import co.edu.itp.ciecyt.domain.FichaTecnica;
import co.edu.itp.ciecyt.repository.FichaTecnicaRepository;
import co.edu.itp.ciecyt.service.dto.FichaTecnicaDTO;
import co.edu.itp.ciecyt.service.mapper.FichaTecnicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FichaTecnica}.
 */
@Service
@Transactional
public class FichaTecnicaServiceImpl implements FichaTecnicaService {

    private final Logger log = LoggerFactory.getLogger(FichaTecnicaServiceImpl.class);

    private final FichaTecnicaRepository fichaTecnicaRepository;

    private final FichaTecnicaMapper fichaTecnicaMapper;

    public FichaTecnicaServiceImpl(FichaTecnicaRepository fichaTecnicaRepository, FichaTecnicaMapper fichaTecnicaMapper) {
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.fichaTecnicaMapper = fichaTecnicaMapper;
    }

    /**
     * Save a fichaTecnica.
     *
     * @param fichaTecnicaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FichaTecnicaDTO save(FichaTecnicaDTO fichaTecnicaDTO) {
        log.debug("Request to save FichaTecnica : {}", fichaTecnicaDTO);
        FichaTecnica fichaTecnica = fichaTecnicaMapper.toEntity(fichaTecnicaDTO);
        fichaTecnica = fichaTecnicaRepository.save(fichaTecnica);
        return fichaTecnicaMapper.toDto(fichaTecnica);
    }

    /**
     * Get all the fichaTecnicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FichaTecnicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FichaTecnicas");
        return fichaTecnicaRepository.findAll(pageable)
            .map(fichaTecnicaMapper::toDto);
    }


    /**
     * Get one fichaTecnica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FichaTecnicaDTO> findOne(Long id) {
        log.debug("Request to get FichaTecnica : {}", id);
        return fichaTecnicaRepository.findById(id)
            .map(fichaTecnicaMapper::toDto);
    }

    /**
     * Delete the fichaTecnica by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FichaTecnica : {}", id);
        fichaTecnicaRepository.deleteById(id);
    }
}
