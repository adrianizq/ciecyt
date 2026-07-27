package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.Pregunta;
import co.edu.itp.ciecyt.domain.PreguntaModalidad;
import co.edu.itp.ciecyt.repository.ModalidadRepository;
import co.edu.itp.ciecyt.repository.PreguntaModalidadRepository;
import co.edu.itp.ciecyt.service.ModalidadService;
import co.edu.itp.ciecyt.service.PreguntaModalidadService;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;
import co.edu.itp.ciecyt.service.dto.PreguntaDTO;
import co.edu.itp.ciecyt.service.mapper.ModalidadMapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Modalidad}.
 */
@Service
@Transactional
public class ModalidadServiceImpl implements ModalidadService {

    private final Logger log = LoggerFactory.getLogger(ModalidadServiceImpl.class);

    private final ModalidadRepository modalidadRepository;

    private final ModalidadMapper modalidadMapper;
    private final PreguntaModalidadRepository preguntaModalidadRepository;

    public ModalidadServiceImpl(
        ModalidadRepository modalidadRepository,
        ModalidadMapper modalidadMapper,
        PreguntaModalidadRepository preguntaModalidadRepository
    ) {
        this.modalidadRepository = modalidadRepository;
        this.modalidadMapper = modalidadMapper;
        this.preguntaModalidadRepository = preguntaModalidadRepository;
    }

    /**
     * Save a modalidad.
     *
     * @param modalidadDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ModalidadDTO save(ModalidadDTO modalidadDTO) {
        log.debug("Request to save Modalidad : {}", modalidadDTO);
        Modalidad modalidad = modalidadMapper.toEntity(modalidadDTO);
        modalidad = modalidadRepository.save(modalidad);
        return modalidadMapper.toDto(modalidad);
    }

    /**
     * Get all the modalidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ModalidadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modalidads");
        return modalidadRepository.findAll(pageable).map(modalidadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Modalidad> buscarAll() {
        log.debug("Request to get all Modalidad de una idPRegunta con una idModalidad");
        List<Modalidad> list = new ArrayList<>();
        list = modalidadRepository.findAll();
        return list;
    }

    /**
     * Get one modalidad by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ModalidadDTO> findOne(Long id) {
        log.debug("Request to get Modalidad : {}", id);
        return modalidadRepository.findById(id).map(modalidadMapper::toDto);
    }

    /**
     * Delete the modalidad by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Modalidad : {}", id);
        modalidadRepository.deleteById(id);
    }

    ////////////////////////////////777777
    /*@Override
    @Transactional(readOnly = true)
    public List<ModalidadDTO> findByPreguntaId(Long idPregunta) throws Exception {
        log.debug("Request to get all Modalidad de una idPRegunta con una idModalidad");
        List<ModalidadDTO> listDTO = new ArrayList<>();
        List<Modalidad> list = new ArrayList<>();
        List<PreguntaModalidad> lpm = preguntaModalidadRepository.findByPreguntaId(idPregunta);
        for (PreguntaModalidad m: lpm
        ) {
            Optional<Modalidad> modalidad = modalidadRepository.findById(m.getModalidad2().getId());
            Modalidad mo = new Modalidad();
            if(modalidad.isPresent()){
                mo = modalidad.get();
            }
            list.add(mo);
        }
        for (Modalidad modalidad : list) {
            listDTO.add(modalidadMapper.toDto(modalidad));
        }
        return listDTO;
    }*/

    //este se lo hizo en ModalidadService // se hubiera podido en PreguntaModalidadService
    //para authority se hace en PreguntaAuthorityService, ya que no hay ModalidadService
    @Override
    @Transactional(readOnly = true)
    public List<ModalidadDTO> findByPreguntaId(Long idPregunta) {
        List<ModalidadDTO> listDTO = new ArrayList<>();
        List<PreguntaModalidad> pml = preguntaModalidadRepository.findByPreguntaId(idPregunta);
        List<Modalidad> lModalidades = new ArrayList<>();

        for (PreguntaModalidad p : pml) {
            Modalidad m = new Modalidad();
            Optional<Modalidad> om = modalidadRepository.findById(p.getModalidad2().getId());
            if (om.isPresent()) {
                m = om.get();
                lModalidades.add(m);
            }
        }
        for (Modalidad modalidad : lModalidades) {
            listDTO.add(modalidadMapper.toDto(modalidad));
        }
        return listDTO;
    }
}
