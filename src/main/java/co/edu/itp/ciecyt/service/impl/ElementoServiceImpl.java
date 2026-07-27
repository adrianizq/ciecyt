package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.*;
import co.edu.itp.ciecyt.repository.ElementoModalidadRepository;
import co.edu.itp.ciecyt.repository.ElementoRepository;
import co.edu.itp.ciecyt.service.ElementoService;
import co.edu.itp.ciecyt.service.dto.*;
import co.edu.itp.ciecyt.service.mapper.ElementoMapper;
import co.edu.itp.ciecyt.service.mapper.ElementoModalidadMapper;
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
 * Service Implementation for managing {@link Elemento}.
 */
@Service
@Transactional
public class ElementoServiceImpl implements ElementoService {

    private final Logger log = LoggerFactory.getLogger(ElementoServiceImpl.class);

    private final ElementoRepository elementoRepository;
    private final ElementoModalidadRepository elementoModalidadRepository;

    private final ElementoMapper elementoMapper;
    private final ElementoModalidadMapper elementoModalidadMapper;

    public ElementoServiceImpl(
        ElementoRepository elementoRepository,
        ElementoModalidadRepository elementoModalidadRepository,
        ElementoMapper elementoMapper,
        ElementoModalidadMapper elementoModalidadMapper
    ) {
        this.elementoRepository = elementoRepository;
        this.elementoModalidadRepository = elementoModalidadRepository;
        this.elementoMapper = elementoMapper;
        this.elementoModalidadMapper = elementoModalidadMapper;
    }

    /**
     * Save a elemento.
     *
     * @param elementoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ElementoDTO save(ElementoDTO elementoDTO) {
        log.debug("Request to save Elemento : {}", elementoDTO);
        Elemento elemento = elementoMapper.toEntity(elementoDTO);
        elemento = elementoRepository.save(elemento);
        return elementoMapper.toDto(elemento);
    }

    ////////////////////////////////////////////////////////////////////////
    @Override
    @Transactional(readOnly = false)
    public ElementoDTO saveModalidad(ElementoDTO elementoDTO) {
        log.debug("Request to save Elemento : {}", elementoDTO);
        Elemento elemento = elementoMapper.toEntity(elementoDTO);
        elementoRepository.save(elemento);

        //Guardar las modalidades
        List<ElementoModalidad> pmL = elementoModalidadRepository.findByElementoId(elemento.getId());
        List<ElementoModalidadDTO> lpmDto = new ArrayList<>();
        //lpmDto = preguntaDTO.getPreguntaModalidads();
        lpmDto = elementoDTO.getElementoModalidads();
        for (ElementoModalidad pm : pmL) {
            elementoModalidadRepository.delete(pm);
        }
        pmL = elementoModalidadRepository.findByElementoId(elemento.getId());
        for (ElementoModalidadDTO pmDto : lpmDto) {
            pmDto.setElementoId(elemento.getId());
            ElementoModalidad pm = elementoModalidadMapper.toEntity(pmDto);
            elementoModalidadRepository.save(pm);
        }

        return elementoMapper.toDto(elemento);
    }

    /////////////////////////////////////////////////////////////////////

    /**
     * Get all the elementos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ElementoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Elementos");
        return elementoRepository.findAll(pageable).map(elementoMapper::toDto);
    }

    //   public List<ElementoDTO> findAllByOrderByIdAsc() throws Exception;

    /**
     * Get all the elementos withou page.
     *
     *  @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ElementoDTO> findAllByOrderByIdAsc() {
        log.debug("Request to get all Elementos");
        List<ElementoDTO> elementoDTOS = new ArrayList<>();
        List<Elemento> elementoList;
        elementoList = elementoRepository.findAllByOrderByIdAsc();
        for (Elemento e : elementoList) {
            ElementoDTO elementoDTO;
            elementoDTO = elementoMapper.toDto(e);
            elementoDTOS.add(elementoDTO);
        }
        return elementoDTOS;
    }

    /**
     * Get one elemento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ElementoDTO> findOne(Long id) {
        log.debug("Request to get Elemento : {}", id);
        return elementoRepository.findById(id).map(elementoMapper::toDto);
    }

    /**
     * Delete the elemento by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Elemento : {}", id);
        //borrar las Modalidades asociadas al elemento
        List<ElementoModalidad> pmL = elementoModalidadRepository.findByElementoId(id);
        for (ElementoModalidad pm : pmL) {
            elementoModalidadRepository.delete(pm);
        }
        //borrar el elemento
        elementoRepository.deleteById(id);
    }

    /*  @Override
    @Transactional(readOnly = true)
    public List<ElementoDTO> findByElementoModalidadId(Long idModalidad) throws Exception {
        log.debug("Request to get all Elementos de una modalidad con una idModalidad");
        List<ElementoDTO> listDTO = new ArrayList<>();
        List<Elemento> list = elementoRepository.findByElementoModalidadId(idModalidad);
        //listDTO = integranteProyectoMapper.usersToUserDTOs(list);

        for (Elemento elemento : list) {
            listDTO.add(elementoMapper.toDto(elemento));
        }
        return listDTO;
    }*/

    @Override
    @Transactional(readOnly = true)
    public List<ElementoDTO> findByElementoFasesId(Long idFase) throws Exception {
        log.debug("Request to get all Elementos de una modalidad con una idModalidad");
        List<ElementoDTO> listDTO = new ArrayList<>();
        List<Elemento> list = elementoRepository.findByElementoFasesIdOrderByOrden(idFase);
        //listDTO = integranteProyectoMapper.usersToUserDTOs(list);

        for (Elemento elemento : list) {
            listDTO.add(elementoMapper.toDto(elemento));
        }
        return listDTO;
    }
    //    List<Elemento> findByElementoFasesIdAndElementoFormatoId(Long idFase, Long idFormato);

    /* @Override
    @Transactional(readOnly = true)
    public List<ElementoDTO> findByElementoFasesIdAndElementoFormatoId(Long idFase, Long idFormato) throws Exception {
        log.debug("Request to get all Elementos de una modalidad con una idModalidad");
        List<ElementoDTO> listDTO = new ArrayList<>();
        List<Elemento> list = elementoRepository.findByElementoFasesIdAndElementoFormatoId(idFase, idFormato);
        //listDTO = integranteProyectoMapper.usersToUserDTOs(list);

        for (Elemento elemento : list) {
            listDTO.add(elementoMapper.toDto(elemento));
        }
        return listDTO;
    } */
}
