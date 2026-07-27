package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.PresupuestoValorService;
import co.edu.itp.ciecyt.domain.PresupuestoValor;
import co.edu.itp.ciecyt.repository.PresupuestoValorRepository;
import co.edu.itp.ciecyt.service.dto.PresupuestoValorDTO;
import co.edu.itp.ciecyt.service.mapper.PresupuestoValorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
/**
 * Service Implementation for managing {@link PresupuestoValor}.
 */
@Service
@Transactional
public class PresupuestoValorServiceImpl implements PresupuestoValorService {

    private final Logger log = LoggerFactory.getLogger(PresupuestoValorServiceImpl.class);

    private final PresupuestoValorRepository presupuestoValorRepository;

    private final PresupuestoValorMapper presupuestoValorMapper;

    public PresupuestoValorServiceImpl(PresupuestoValorRepository presupuestoValorRepository, PresupuestoValorMapper presupuestoValorMapper) {
        this.presupuestoValorRepository = presupuestoValorRepository;
        this.presupuestoValorMapper = presupuestoValorMapper;
    }

    /**
     * Save a presupuestoValor.
     *
     * @param presupuestoValorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PresupuestoValorDTO save(PresupuestoValorDTO presupuestoValorDTO) {
        log.debug("Request to save PresupuestoValor : {}", presupuestoValorDTO);
        PresupuestoValor presupuestoValor = presupuestoValorMapper.toEntity(presupuestoValorDTO);
        presupuestoValor = presupuestoValorRepository.save(presupuestoValor);
        return presupuestoValorMapper.toDto(presupuestoValor);
    }

    /**
     * Get all the presupuestoValors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PresupuestoValorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PresupuestoValors");
        return presupuestoValorRepository.findAll(pageable)
            .map(presupuestoValorMapper::toDto);
    }


    /**
     * Get one presupuestoValor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PresupuestoValorDTO> findOne(Long id) {
        log.debug("Request to get PresupuestoValor : {}", id);
        return presupuestoValorRepository.findById(id)
            .map(presupuestoValorMapper::toDto);
    }

    /**
     * Delete the presupuestoValor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PresupuestoValor : {}", id);
        presupuestoValorRepository.deleteById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<PresupuestoValorDTO> findByPresupuestoValorProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get all PresupuestoValor whit a idProyecto");
        List <PresupuestoValorDTO> listDTO = new ArrayList<>();
        List <PresupuestoValor> list = presupuestoValorRepository.findByPresupuestoValorProyectoIdOrderByOrdenVista( idProyecto);


        for (PresupuestoValor presupuesto : list) {
			listDTO.add( presupuestoValorMapper.toDto(presupuesto));
		}
        return listDTO;

    }
}
