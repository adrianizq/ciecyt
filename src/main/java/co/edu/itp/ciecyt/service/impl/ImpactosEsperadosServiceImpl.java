package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.ImpactosEsperadosService;
import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import co.edu.itp.ciecyt.repository.ImpactosEsperadosRepository;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import co.edu.itp.ciecyt.service.mapper.ImpactosEsperadosMapper;
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
 * Service Implementation for managing {@link ImpactosEsperados}.
 */
@Service
@Transactional
public class ImpactosEsperadosServiceImpl implements ImpactosEsperadosService {

    private final Logger log = LoggerFactory.getLogger(ImpactosEsperadosServiceImpl.class);

    private final ImpactosEsperadosRepository impactosEsperadosRepository;

    private final ImpactosEsperadosMapper impactosEsperadosMapper;

    public ImpactosEsperadosServiceImpl(ImpactosEsperadosRepository impactosEsperadosRepository, ImpactosEsperadosMapper impactosEsperadosMapper) {
        this.impactosEsperadosRepository = impactosEsperadosRepository;
        this.impactosEsperadosMapper = impactosEsperadosMapper;
    }

    /**
     * Save a impactosEsperados.
     *
     * @param impactosEsperadosDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ImpactosEsperadosDTO save(ImpactosEsperadosDTO impactosEsperadosDTO) {
        log.debug("Request to save ImpactosEsperados : {}", impactosEsperadosDTO);
        ImpactosEsperados impactosEsperados = impactosEsperadosMapper.toEntity(impactosEsperadosDTO);
        impactosEsperados = impactosEsperadosRepository.save(impactosEsperados);
        return impactosEsperadosMapper.toDto(impactosEsperados);
    }

    /**
     * Get all the impactosEsperados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ImpactosEsperadosDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ImpactosEsperados");
        return impactosEsperadosRepository.findAll(pageable)
            .map(impactosEsperadosMapper::toDto);
    }


    /**
     * Get one impactosEsperados by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ImpactosEsperadosDTO> findOne(Long id) {
        log.debug("Request to get ImpactosEsperados : {}", id);
        return impactosEsperadosRepository.findById(id)
            .map(impactosEsperadosMapper::toDto);
    }

    /**
     * Delete the impactosEsperados by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImpactosEsperados : {}", id);
        impactosEsperadosRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImpactosEsperadosDTO> findByImpactosEsperadoProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get all ImpactosEsperados whit a idProyecto");
        List<ImpactosEsperadosDTO> listDTO = new ArrayList<>();
        List<ImpactosEsperados> list = impactosEsperadosRepository.findByImpactosEsperadoProyectoIdOrderByOrdenVista(idProyecto);


        for (ImpactosEsperados obj : list) {
            listDTO.add(impactosEsperadosMapper.toDto(obj));
        }
        return listDTO;
    }
}
