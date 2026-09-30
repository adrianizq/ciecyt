package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.ElementoProyecto;
import co.edu.itp.ciecyt.service.ProyectoAutorizacionService;
import co.edu.itp.ciecyt.service.ProyectoRespuestasService;
import co.edu.itp.ciecyt.domain.ProyectoRespuestas;
import co.edu.itp.ciecyt.repository.ProyectoRespuestasRepository;
import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoRespuestasDTO;
import co.edu.itp.ciecyt.service.mapper.ProyectoRespuestasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProyectoRespuestas}.
 */
@Service
@Transactional
public class ProyectoRespuestasServiceImpl implements ProyectoRespuestasService {

    private final Logger log = LoggerFactory.getLogger(ProyectoRespuestasServiceImpl.class);

    private final ProyectoRespuestasRepository proyectoRespuestasRepository;

    private final ProyectoRespuestasMapper proyectoRespuestasMapper;

    private final ProyectoAutorizacionService autorizacion;

    public ProyectoRespuestasServiceImpl(ProyectoRespuestasRepository proyectoRespuestasRepository, ProyectoRespuestasMapper proyectoRespuestasMapper, ProyectoAutorizacionService autorizacion) {
        this.proyectoRespuestasRepository = proyectoRespuestasRepository;
        this.proyectoRespuestasMapper = proyectoRespuestasMapper;
        this.autorizacion = autorizacion;
    }

    /**
     * Descarta del resultado las respuestas de un rol que aun no ha entregado su concepto.
     *
     * <p>Se filtra aqui y no en el resource para que el descarte aplique a cualquier lector, y se
     * deja en el resource el 403 de quien no puede ni ver el proyecto. Asi el estudiante que
     * consulta los comentarios de su propuesta obtiene una lista vacia, igual que si el asesor
     * aun no hubiera escrito nada, y no se le revela que hay un borrador en curso.
     *
     * <p>La decision se evalua una vez por rol y no una vez por respuesta, porque el proyecto se
     * resuelve en base de datos y las respuestas se agrupan por autoridad.
     */
    private List<ProyectoRespuestasDTO> soloRespuestasVisibles(Long idProyecto, List<ProyectoRespuestas> respuestas) {
        List<ProyectoRespuestasDTO> visibles = new ArrayList<>();
        Map<String, Boolean> porRol = new HashMap<>();
        for (ProyectoRespuestas respuesta : respuestas) {
            String authority = respuesta.getAuthority();
            // Boolean y no boolean: un null es la senal de que ese rol todavia no se consulto.
            Boolean visible = porRol.get(authority);
            if (visible == null) {
                visible = autorizacion.puedeVerRespuestasDeRolEn(idProyecto, authority);
                porRol.put(authority, visible);
            }
            if (visible) {
                visibles.add(proyectoRespuestasMapper.toDto(respuesta));
            }
        }
        return visibles;
    }

    @Override
    public ProyectoRespuestasDTO save(ProyectoRespuestasDTO proyectoRespuestasDTO) {
        log.debug("Request to save ProyectoRespuestas : {}", proyectoRespuestasDTO);
        ProyectoRespuestas proyectoRespuestas = proyectoRespuestasMapper.toEntity(proyectoRespuestasDTO);
        proyectoRespuestas = proyectoRespuestasRepository.save(proyectoRespuestas);
        return proyectoRespuestasMapper.toDto(proyectoRespuestas);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoRespuestasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProyectoRespuestas");
        return proyectoRespuestasRepository.findAll(pageable)
            .map(proyectoRespuestasMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoRespuestasDTO> findOne(Long id) {
        log.debug("Request to get ProyectoRespuestas : {}", id);
        return proyectoRespuestasRepository.findById(id)
            .map(proyectoRespuestasMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProyectoRespuestas : {}", id);
        proyectoRespuestasRepository.deleteById(id);
    }

    @Override
    public List<ProyectoRespuestasDTO> findByProyectoRespuestasProyectoId(Long idProyecto) throws Exception {
        log.debug("Request to get all ElementoProyecto whit a idProyecto");
        List<ProyectoRespuestas> list = proyectoRespuestasRepository.findByProyectoRespuestasProyectoIdOrderByProyectoRespuestasProyectoId(idProyecto);
        return soloRespuestasVisibles(idProyecto, list);
    }

    @Override
    public List<ProyectoRespuestasDTO> findByProyectoRespuestasProyectoIdFaseAuthority(Long idProyecto, Long idFase, String authority) throws Exception {
        log.debug("Request to get all ElementoProyecto whit a idProyecto");
        if (!autorizacion.puedeVerRespuestasDeRolEn(idProyecto, authority)) {
            return new ArrayList<>();
        }
        List<ProyectoRespuestas> list = proyectoRespuestasRepository.findByProyectoRespuestasProyectoIdAndFaseIdAndAuthority(idProyecto,idFase,authority);
        return soloRespuestasVisibles(idProyecto, list);
    }
}
