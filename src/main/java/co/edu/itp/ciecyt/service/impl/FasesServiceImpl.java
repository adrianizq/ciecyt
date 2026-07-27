package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.service.FasesService;
import co.edu.itp.ciecyt.domain.Fases;
import co.edu.itp.ciecyt.repository.FasesRepository;
import co.edu.itp.ciecyt.service.dto.FasesDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.mapper.FasesMapper;
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
 * Service Implementation for managing {@link Fases}.
 */
@Service
@Transactional
public class FasesServiceImpl implements FasesService {

    private final Logger log = LoggerFactory.getLogger(FasesServiceImpl.class);

    private final FasesRepository fasesRepository;

    private final FasesMapper fasesMapper;

    public FasesServiceImpl(FasesRepository fasesRepository, FasesMapper fasesMapper) {
        this.fasesRepository = fasesRepository;
        this.fasesMapper = fasesMapper;
    }

    /**
     * Save a fases.
     *
     * @param fasesDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FasesDTO save(FasesDTO fasesDTO) {
        log.debug("Request to save Fases : {}", fasesDTO);
        Fases fases = fasesMapper.toEntity(fasesDTO);
        fases = fasesRepository.save(fases);
        return fasesMapper.toDto(fases);
    }

    /**
     * Get all the fases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FasesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fases");
        return fasesRepository.findAll(pageable)
            .map(fasesMapper::toDto);
    }


    /**
     * Get one fases by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FasesDTO> findOne(Long id) {
        log.debug("Request to get Fases : {}", id);
        return fasesRepository.findById(id)
            .map(fasesMapper::toDto);
    }

    /**
     * Delete the fases by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fases : {}", id);
        fasesRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public FasesDTO findByFase(String fase) throws Exception {
        Fases f = fasesRepository.findByFase(fase);
        return fasesMapper.toDto(f);
    }

/*
    @Override
    @Transactional(readOnly = true)
    public List<FasesDTO> findByFasesModalidadId(Long idModalidad) throws Exception {
        List<Fases> lista = new ArrayList<>();
        lista = fasesRepository.findByFasesModalidadId(idModalidad);
        List<FasesDTO> listDTO = new ArrayList<>();
        if (lista != null) {
            for (Fases p : lista) {
                listDTO.add(fasesMapper.toDto(p));
            }
            return listDTO;
        } else {
            return null;

        }
    }

 */
}
