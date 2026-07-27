package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.ElementoProyecto;
import co.edu.itp.ciecyt.domain.ImpactosEsperados;
import co.edu.itp.ciecyt.repository.ElementoProyectoRepository;
import co.edu.itp.ciecyt.service.ElementoProyectoService;
import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import co.edu.itp.ciecyt.service.mapper.ElementoProyectoMapper;
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
 * Service Implementation for managing {@link ElementoProyecto}.
 */
@Service
@Transactional
public class ElementoProyectoServiceImpl implements ElementoProyectoService {

    private final Logger log = LoggerFactory.getLogger(ElementoProyectoServiceImpl.class);

    private final ElementoProyectoRepository elementoProyectoRepository;

    private final ElementoProyectoMapper elementoProyectoMapper;

    public ElementoProyectoServiceImpl(
        ElementoProyectoRepository elementoProyectoRepository,
        ElementoProyectoMapper elementoProyectoMapper
    ) {
        this.elementoProyectoRepository = elementoProyectoRepository;
        this.elementoProyectoMapper = elementoProyectoMapper;
    }

    /**
     * Save a elementoProyecto.
     *
     * @param elementoProyectoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ElementoProyectoDTO save(ElementoProyectoDTO elementoProyectoDTO) {
        log.debug("Request to save ElementoProyecto : {}", elementoProyectoDTO);
        ElementoProyecto elementoProyecto = elementoProyectoMapper.toEntity(elementoProyectoDTO);
        elementoProyecto = elementoProyectoRepository.save(elementoProyecto);
        return elementoProyectoMapper.toDto(elementoProyecto);
    }

    /**
     * Get all the elementoProyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ElementoProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ElementoProyectos");
        return elementoProyectoRepository.findAll(pageable).map(elementoProyectoMapper::toDto);
    }

    /**
     * Get one elementoProyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ElementoProyectoDTO> findOne(Long id) {
        log.debug("Request to get ElementoProyecto : {}", id);
        return elementoProyectoRepository.findById(id).map(elementoProyectoMapper::toDto);
    }

    /**
     * Delete the elementoProyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ElementoProyecto : {}", id);
        elementoProyectoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ElementoProyectoDTO> findByElementoProyectoProyectoId(Long idProyecto, Long idFase) throws Exception {
        log.debug("Request to get all ElementoProyecto whit a idProyecto");
        List<ElementoProyectoDTO> listDTO = new ArrayList<>();
        ////List<ElementoProyecto> list = elementoProyectoRepository.findByElementoProyectoProyectoId(idProyecto);
        //List<ElementoProyecto> list = elementoProyectoRepository.findByElementoProyectoProyectoIdOrderByElementoProyectoElementoId(idProyecto);
        List<ElementoProyecto> list = elementoProyectoRepository.findByElementoProyectoProyectoIdAndElementoFasesIdOrderByElementoProyectoElementoId(idProyecto, idFase);


        for (ElementoProyecto obj : list) {
            listDTO.add(elementoProyectoMapper.toDto(obj));
        }
        return listDTO;
    }
}
