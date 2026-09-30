package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.Requisito;
import co.edu.itp.ciecyt.domain.RequisitoProyecto;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoRequisito;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.repository.RequisitoProyectoRepository;
import co.edu.itp.ciecyt.repository.RequisitoRepository;
import co.edu.itp.ciecyt.security.SecurityUtils;
import co.edu.itp.ciecyt.service.RequisitoProyectoService;
import co.edu.itp.ciecyt.service.dto.RequisitoProyectoDTO;
import co.edu.itp.ciecyt.service.mapper.RequisitoProyectoMapper;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequisitoProyecto}.
 */
@Service
@Transactional
public class RequisitoProyectoServiceImpl implements RequisitoProyectoService {

    private final Logger log = LoggerFactory.getLogger(RequisitoProyectoServiceImpl.class);

    /** Codigo del requisito de avance minimo del 75% (Acuerdo 025, art. 5, par. 1). */
    private static final String CODIGO_AVANCE_75 = "REQ_AVANCE_75";

    /**
     * Modalidades en las que no se exige el avance del 75%: 9005 Diplomado y 9007 Especializacion.
     * El requisito se siembra con modalidad NULL (aplica a todas) y aqui se filtran las excepciones.
     */
    private static final Set<Long> MODALIDADES_SIN_AVANCE_75 = new HashSet<>(Arrays.asList(9005L, 9007L));

    private final RequisitoProyectoRepository requisitoProyectoRepository;
    private final RequisitoProyectoMapper requisitoProyectoMapper;
    private final RequisitoRepository requisitoRepository;
    private final ProyectoRepository proyectoRepository;

    public RequisitoProyectoServiceImpl(
        RequisitoProyectoRepository requisitoProyectoRepository,
        RequisitoProyectoMapper requisitoProyectoMapper,
        RequisitoRepository requisitoRepository,
        ProyectoRepository proyectoRepository
    ) {
        this.requisitoProyectoRepository = requisitoProyectoRepository;
        this.requisitoProyectoMapper = requisitoProyectoMapper;
        this.requisitoRepository = requisitoRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public RequisitoProyectoDTO save(RequisitoProyectoDTO requisitoProyectoDTO) {
        log.debug("Request to save RequisitoProyecto : {}", requisitoProyectoDTO);
        RequisitoProyecto requisitoProyecto = requisitoProyectoMapper.toEntity(requisitoProyectoDTO);
        requisitoProyecto = requisitoProyectoRepository.save(requisitoProyecto);
        return requisitoProyectoMapper.toDto(requisitoProyecto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequisitoProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RequisitoProyectos");
        return requisitoProyectoRepository.findAll(pageable).map(requisitoProyectoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequisitoProyectoDTO> findByProyectoId(Long proyectoId) {
        log.debug("Request to get all RequisitoProyectos of proyecto : {}", proyectoId);
        return requisitoProyectoRepository.findByRequisitoProyectoProyectoId(proyectoId).stream()
            .map(requisitoProyectoMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequisitoProyectoDTO> findOne(Long id) {
        log.debug("Request to get RequisitoProyecto : {}", id);
        return requisitoProyectoRepository.findById(id).map(requisitoProyectoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequisitoProyecto : {}", id);
        requisitoProyectoRepository.deleteById(id);
    }

    @Override
    public List<RequisitoProyectoDTO> generarParaProyecto(Long proyectoId) {
        log.debug("Generando requisitos pendientes para el proyecto : {}", proyectoId);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));

        Long modalidadId = proyecto.getProyectoModalidad() != null ? proyecto.getProyectoModalidad().getId() : null;
        if (modalidadId == null) {
            return new ArrayList<>();
        }

        List<Requisito> requisitos = requisitoRepository
            .findByRequisitoModalidadIdOrRequisitoModalidadIsNullOrderByNombreAsc(modalidadId).stream()
            .filter(r -> Boolean.TRUE.equals(r.getActivo()))
            .filter(r -> aplicaAlProyecto(r, modalidadId))
            .collect(Collectors.toList());

        for (Requisito requisito : requisitos) {
            boolean existe = requisitoProyectoRepository
                .existsByRequisitoProyectoProyectoIdAndRequisitoProyectoRequisitoId(proyectoId, requisito.getId());
            if (!existe) {
                RequisitoProyecto requisitoProyecto = new RequisitoProyecto();
                requisitoProyecto.setEstado(EnumEstadoRequisito.PENDIENTE);
                requisitoProyecto.setRequisitoProyectoRequisito(requisito);
                requisitoProyecto.setRequisitoProyectoProyecto(proyecto);
                requisitoProyectoRepository.save(requisitoProyecto);
            }
        }

        return requisitoProyectoRepository.findByRequisitoProyectoProyectoId(proyectoId).stream()
            .map(requisitoProyectoMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Indica si un requisito aplica al proyecto dada su modalidad.
     * Solo tiene reglas de exclusion el requisito de avance del 75%.
     *
     * @param requisito requisito evaluado.
     * @param modalidadId id de la modalidad del proyecto.
     * @return {@code true} si el requisito debe generarse para el proyecto.
     */
    private boolean aplicaAlProyecto(Requisito requisito, Long modalidadId) {
        if (CODIGO_AVANCE_75.equals(requisito.getCodigo())) {
            return !MODALIDADES_SIN_AVANCE_75.contains(modalidadId);
        }
        return true;
    }

    @Override
    public RequisitoProyectoDTO entregar(Long id, String archivo, String observacion) {
        log.debug("Entrega de requisito por estudiante : {}", id);
        RequisitoProyecto requisitoProyecto = requisitoProyectoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Requisito del proyecto no encontrado: " + id));

        requisitoProyecto.setEstado(EnumEstadoRequisito.ENTREGADO);
        requisitoProyecto.setArchivo(archivo);
        requisitoProyecto.setFechaEntrega(Instant.now());
        requisitoProyecto.setFechaValidacion(null);
        requisitoProyecto.setValidadoPor(null);
        requisitoProyecto.setObservacion(observacion);

        return requisitoProyectoMapper.toDto(requisitoProyectoRepository.save(requisitoProyecto));
    }

    @Override
    public RequisitoProyectoDTO validar(Long id, boolean aprobado, String observacion) {
        log.debug("Validacion de requisito por CIECYT : {} aprobado={}", id, aprobado);
        RequisitoProyecto requisitoProyecto = requisitoProyectoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Requisito del proyecto no encontrado: " + id));

        requisitoProyecto.setEstado(aprobado ? EnumEstadoRequisito.APROBADO : EnumEstadoRequisito.RECHAZADO);
        requisitoProyecto.setFechaValidacion(Instant.now());
        requisitoProyecto.setValidadoPor(SecurityUtils.getCurrentUserLogin().orElse("system"));
        // Si el CIECYT no deja comentario no se borra lo que trajo el estudiante: en un requisito
        // de tipo CAMPO ahi esta el dato del avance que acaba de revisar.
        if (observacion != null && !observacion.trim().isEmpty()) {
            requisitoProyecto.setObservacion(observacion);
        }

        return requisitoProyectoMapper.toDto(requisitoProyectoRepository.save(requisitoProyecto));
    }
}
