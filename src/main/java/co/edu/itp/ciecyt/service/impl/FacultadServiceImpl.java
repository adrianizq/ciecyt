package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Facultad;
import co.edu.itp.ciecyt.repository.FacultadRepository;
import co.edu.itp.ciecyt.service.FacultadService;
import co.edu.itp.ciecyt.service.dto.FacultadDTO;
import co.edu.itp.ciecyt.service.mapper.FacultadMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Facultad}.
 */
@Service
@Transactional
public class FacultadServiceImpl implements FacultadService {

    private final Logger log = LoggerFactory.getLogger(FacultadServiceImpl.class);

    private final FacultadRepository facultadRepository;

    private final FacultadMapper facultadMapper;

    public FacultadServiceImpl(FacultadRepository facultadRepository, FacultadMapper facultadMapper) {
        this.facultadRepository = facultadRepository;
        this.facultadMapper = facultadMapper;
    }

    /**
     * Save a facultad.
     *
     * @param facultadDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FacultadDTO save(FacultadDTO facultadDTO) {
        log.debug("Request to save Facultad : {}", facultadDTO);
        Facultad facultad = facultadMapper.toEntity(facultadDTO);
        facultad = facultadRepository.save(facultad);
        return facultadMapper.toDto(facultad);
    }

    /**
     * Get all the facultads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FacultadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Facultads");
        return facultadRepository.findAll(pageable).map(facultadMapper::toDto);
    }

    /**
     * Get one facultad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FacultadDTO> findOne(Long id) {
        log.debug("Request to get Facultad : {}", id);
        return facultadRepository.findById(id).map(facultadMapper::toDto);
    }

    /**
     * Delete the facultad by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Facultad : {}", id);
        facultadRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Facultad> buscarAll() {
        log.debug("Request to get all Modalidad de una idPRegunta con una idModalidad");
        List<Facultad> list = new ArrayList<>();
        list = facultadRepository.findAll();
        return list;
    }
}
