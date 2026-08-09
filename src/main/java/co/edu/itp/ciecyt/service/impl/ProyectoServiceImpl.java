package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.LineaInvestigacion;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.Programa;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.ProyectoHistorialEstado;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.repository.LineaInvestigacionRepository;
import co.edu.itp.ciecyt.repository.ProgramaRepository;
import co.edu.itp.ciecyt.repository.ProyectoHistorialEstadoRepository;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.security.SecurityUtils;
import co.edu.itp.ciecyt.service.IntegranteProyectoService;
import co.edu.itp.ciecyt.service.NotificacionService;
import co.edu.itp.ciecyt.service.ProyectoService;
import co.edu.itp.ciecyt.service.RolesModalidadService;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.dto.RolesModalidadDTO;
import co.edu.itp.ciecyt.service.mapper.ProyectoMapper;
import co.edu.itp.ciecyt.config.Constants;
import java.time.Instant;
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
 * Service Implementation for managing {@link Proyecto}.
 */
@Service
@Transactional
public class ProyectoServiceImpl implements ProyectoService {

    private final Logger log = LoggerFactory.getLogger(ProyectoServiceImpl.class);

    private final ProyectoRepository proyectoRepository;
    private final IntegranteProyectoService integranteProyectoService;
    //private final IntegranteProyectoRepository integranteProyectoRepository;
    private final RolesModalidadService rolesModalidadService;
    private final LineaInvestigacionRepository lineaInvestigacionRepository;
    private final ProgramaRepository programaRepository;
    private final ProyectoHistorialEstadoRepository proyectoHistorialEstadoRepository;
    private final NotificacionService notificacionService;

    private final ProyectoMapper proyectoMapper;

    public ProyectoServiceImpl(
        ProyectoRepository proyectoRepository,
        ProyectoMapper proyectoMapper,
        IntegranteProyectoService integranteProyectoService,
        RolesModalidadService rolesModalidadService,
        LineaInvestigacionRepository lineaInvestigacionRepository,
        ProgramaRepository programaRepository,
        ProyectoHistorialEstadoRepository proyectoHistorialEstadoRepository,
        NotificacionService notificacionService
    ) {
        this.proyectoRepository = proyectoRepository;
        this.proyectoMapper = proyectoMapper;
        this.integranteProyectoService = integranteProyectoService;
        this.rolesModalidadService = rolesModalidadService;
        this.lineaInvestigacionRepository = lineaInvestigacionRepository;
        this.programaRepository = programaRepository;
        this.proyectoHistorialEstadoRepository = proyectoHistorialEstadoRepository;
        this.notificacionService = notificacionService;
    }

    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProyectoDTO save(ProyectoDTO proyectoDTO) {
        log.debug("Request to save Proyecto : {}", proyectoDTO);
        log.debug("proyectoLineaInvestigacionId: {}, subLineaLineaInvestigacionId: {}",
            proyectoDTO.getProyectoLineaInvestigacionId(), proyectoDTO.getSubLineaLineaInvestigacionId());
        Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);

        if (proyecto.getId() == null && proyecto.getEstado() == null) {
            proyecto.setEstado(EnumEstadoProyecto.EN_ELABORACION_PROPUESTA);
        }

        // Asegurar que se persistan las relaciones provenientes del DTO
        if (proyectoDTO.getProyectoLineaInvestigacionId() != null && proyecto.getProyectoLineaInvestigacion() == null) {
            LineaInvestigacion linea = lineaInvestigacionRepository.findById(proyectoDTO.getProyectoLineaInvestigacionId()).orElse(null);
            proyecto.setProyectoLineaInvestigacion(linea);
        }
        if (proyectoDTO.getSubLineaLineaInvestigacionId() != null && proyecto.getSubLineaLineaInvestigacion() == null) {
            LineaInvestigacion subLinea = lineaInvestigacionRepository.findById(proyectoDTO.getSubLineaLineaInvestigacionId()).orElse(null);
            proyecto.setSubLineaLineaInvestigacion(subLinea);
        }
        if (proyectoDTO.getProyectoProgramaId() != null && proyecto.getProyectoPrograma() == null) {
            Programa programa = programaRepository.findById(proyectoDTO.getProyectoProgramaId()).orElse(null);
            proyecto.setProyectoPrograma(programa);
        }

        proyecto = proyectoRepository.save(proyecto);
        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Cambia el estado de un proyecto registrando el historial.
     *
     * @param proyectoId id del proyecto.
     * @param nuevoEstado estado destino.
     * @param observacion observación opcional.
     * @return el proyecto actualizado.
     */
    @Override
    public ProyectoDTO cambiarEstado(Long proyectoId, EnumEstadoProyecto nuevoEstado, String observacion) {
        log.debug("Request to cambiarEstado Proyecto : {}, nuevoEstado : {}", proyectoId, nuevoEstado);
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
            .orElseThrow(() -> new IllegalArgumentException("Proyecto no encontrado: " + proyectoId));

        EnumEstadoProyecto estadoAnterior = proyecto.getEstado();
        if (estadoAnterior == nuevoEstado) {
            return proyectoMapper.toDto(proyecto);
        }

        proyecto.setEstado(nuevoEstado);
        sincronizarFlagsLegacy(proyecto, nuevoEstado);
        proyecto = proyectoRepository.save(proyecto);

        ProyectoHistorialEstado historial = new ProyectoHistorialEstado();
        historial.setProyecto(proyecto);
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(nuevoEstado);
        historial.setObservacion(observacion);
        historial.setUsuarioLogin(SecurityUtils.getCurrentUserLogin().orElse(Constants.SYSTEM_ACCOUNT));
        historial.setFechaCambio(Instant.now());
        proyectoHistorialEstadoRepository.save(historial);

        notificarResponsables(proyecto, estadoAnterior, nuevoEstado);

        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Notifica a los responsables del proyecto según el nuevo estado.
     */
    private void notificarResponsables(Proyecto proyecto, EnumEstadoProyecto estadoAnterior, EnumEstadoProyecto nuevoEstado) {
        String rolResponsable = null;
        switch (nuevoEstado) {
            case EN_REVISION_ASESOR:
                rolResponsable = "Asesor";
                break;
            case EN_REVISION_JURADO_PROPUESTA:
            case EN_REVISION_JURADO_PROYECTO:
            case EN_EVALUACION_SUSTENTACION:
                rolResponsable = "Jurado";
                break;
            case CORRECCIONES_ASESOR:
            case CORRECCIONES_JURADO_PROPUESTA:
            case CORRECCIONES_JURADO_PROYECTO:
            case VIABLE:
            case EN_ELABORACION_PROYECTO:
            case SUSTENTACION_PROGRAMADA:
            case AJUSTES_SUSTENTACION:
                rolResponsable = "Estudiante";
                break;
            case LISTO_PARA_SUSTENTAR:
            case NOTA_DEFINITIVA:
                rolResponsable = "CIECYT";
                break;
            default:
                break;
        }

        if (rolResponsable == null) {
            return;
        }

        try {
            List<IntegranteProyectoDTO> integrantes = integranteProyectoService.findByIntegranteProyectoProyectoId(proyecto.getId());
            if (integrantes == null) {
                return;
            }
            for (IntegranteProyectoDTO integrante : integrantes) {
                if (integrante.getIntegranteProyectoRolesModalidadRol() != null &&
                    integrante.getIntegranteProyectoRolesModalidadRol().contains(rolResponsable)) {
                    String titulo = "Nueva tarea pendiente: " + nuevoEstado.name();
                    String mensaje = String.format(
                        "El proyecto '%s' cambió de estado de %s a %s. Tiene una acción pendiente.",
                        proyecto.getTitulo() != null ? proyecto.getTitulo() : "Sin título",
                        estadoAnterior != null ? estadoAnterior.name() : "INICIAL",
                        nuevoEstado.name()
                    );
                    notificacionService.crearNotificacion(
                        integrante.getIntegranteProyectoUserId(),
                        titulo,
                        mensaje,
                        proyecto.getId(),
                        "CAMBIO_ESTADO"
                    );
                }
            }
        } catch (Exception e) {
            log.warn("No se pudieron notificar responsables del proyecto {}: {}", proyecto.getId(), e.getMessage());
        }
    }

    /**
     * Sincroniza los booleanos legacy con el nuevo estado para mantener compatibilidad
     * mientras el frontend transiciona a usar exclusivamente {@code estado}.
     */
    private void sincronizarFlagsLegacy(Proyecto proyecto, EnumEstadoProyecto estado) {
        switch (estado) {
            case PREINSCRITA:
            case EN_ELABORACION_PROPUESTA:
                proyecto.setPreEnviado(false);
                proyecto.setEnviado(false);
                break;
            case EN_REVISION_ASESOR:
            case CORRECCIONES_ASESOR:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                break;
            case APROBADA_POR_ASESOR:
            case EN_REVISION_JURADO_PROPUESTA:
            case CORRECCIONES_JURADO_PROPUESTA:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                break;
            case VIABLE:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                break;
            case NO_VIABLE:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("NO_VIABLE");
                proyecto.setViable(false);
                break;
            case EN_ELABORACION_PROYECTO:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                proyecto.setProyectoEnviado(false);
                break;
            case EN_REVISION_JURADO_PROYECTO:
            case CORRECCIONES_JURADO_PROYECTO:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                proyecto.setProyectoEnviado(true);
                break;
            case LISTO_PARA_SUSTENTAR:
            case SUSTENTACION_PROGRAMADA:
            case SUSTENTACION_REALIZADA:
            case EN_EVALUACION_SUSTENTACION:
            case AJUSTES_SUSTENTACION:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                proyecto.setProyectoEnviado(true);
                proyecto.setSustentar(true);
                break;
            case NOTA_DEFINITIVA:
            case FINALIZADO:
                proyecto.setPreEnviado(true);
                proyecto.setEnviado(true);
                proyecto.setViabilidad("VIABLE");
                proyecto.setViable(true);
                proyecto.setProyectoEnviado(true);
                proyecto.setSustentar(true);
                break;
            default:
                break;
        }
    }

    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProyectoDTO saveAsesorProyecto(ProyectoDTO proyectoDTO) throws Exception {
        log.debug("Request to save Proyecto : {}", proyectoDTO);
        Proyecto proyecto = proyectoMapper.toEntity(proyectoDTO);
        proyecto = proyectoRepository.save(proyecto);
        //guadar integrante
        IntegranteProyectoDTO asesorDTO = new IntegranteProyectoDTO();

        asesorDTO.setIntegranteProyectoProyectoId(proyecto.getId());
        asesorDTO.setIntegranteProyectoUserId(proyectoDTO.getAsesorId());
        //arreglado

        Modalidad modalidad = proyecto.getProyectoModalidad();
        Long modalidadId = modalidad.getId(); //eje 1551

        RolesModalidadDTO rolesModalidad;
        rolesModalidad = rolesModalidadService.findByRolAndRolesModalidadModalidadId("Asesor", modalidadId);
        Long rolesModalidadId = rolesModalidad.getId();
        //asesorDTO.setIntegranteProyectoRolesModalidadId(4451L);
        asesorDTO.setIntegranteProyectoRolesModalidadId(rolesModalidadId);

        ////////////////* validamos que exista el asesor
        List<IntegranteProyectoDTO> lIntegranteProyectoDTO = new ArrayList();
        lIntegranteProyectoDTO =
            integranteProyectoService.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
                proyecto.getId(),
                rolesModalidadId
            );
        if (lIntegranteProyectoDTO != null && lIntegranteProyectoDTO.size() > 0) {
            IntegranteProyectoDTO dto = lIntegranteProyectoDTO.get(0);
            asesorDTO.setId(dto.getId());
        }
        //Long integranteProyectoId = integranteProyectoDTO.getIntegranteProyectoUserId();

        //////////////////////77

        integranteProyectoService.save(asesorDTO);
        return proyectoMapper.toDto(proyecto);
    }

    /**
     * Get all the proyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProyectoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Proyectos");
        return proyectoRepository.findAll(pageable).map(proyectoMapper::toDto);
    }

    /**ADR
     * REtorna todos los proyectos pero con una lista de todos los integrantes
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProyectoDTO> findAllProyectosIntegrantes() throws Exception {
        log.debug("Request to get all Proyectos");
        List<Proyecto> lProyectos = proyectoRepository.findAll();
        List<ProyectoDTO> proyectoDTOList = new ArrayList<ProyectoDTO>();
        for (Proyecto proyecto : lProyectos) {
            ProyectoDTO dto;
            dto = proyectoMapper.toDto(proyecto);
            dto.setTieneJurado(false);
            dto.setTieneAsesor(false);
            List<IntegranteProyectoDTO> lIntegrantes = integranteProyectoService.findByIntegranteProyectoProyectoId(proyecto.getId());
            if (lIntegrantes != null && lIntegrantes.size() > 0) {
                for (IntegranteProyectoDTO i : lIntegrantes) {
                    if (i.getIntegranteProyectoRolesModalidadRol().contains("Jurado")) {
                        dto.setTieneJurado(true);
                    }
                    if (i.getIntegranteProyectoRolesModalidadRol().contains("Asesor")) {
                        dto.setTieneAsesor(true);
                    }
                }
            }

            dto.setListaIntegrantesProyecto(lIntegrantes);
            proyectoDTOList.add(dto);
        }
        //return proyectoMapper.toDto(lProyectos);
        return proyectoDTOList;
    }

    /**
     * Get one proyecto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoDTO> findOne(Long id) {
        log.debug("Request to get Proyecto : {}", id);
        return proyectoRepository.findById(id).map(proyectoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProyectoDTO> findOneIntegrantes(Long id) throws Exception {
        log.debug("Request to get Proyecto : {}", id);
        Optional<ProyectoDTO> odto = proyectoRepository.findById(id).map(proyectoMapper::toDto);
        ProyectoDTO dto = odto.get();
        dto.setTieneJurado(false);
        dto.setTieneAsesor(false);
        List<IntegranteProyectoDTO> lIntegrantes = integranteProyectoService.findByIntegranteProyectoProyectoId(dto.getId());
        if (lIntegrantes != null && lIntegrantes.size() > 0) {
            for (IntegranteProyectoDTO i : lIntegrantes) {
                if (i.getIntegranteProyectoRolesModalidadRol().contains("Jurado")) {
                    dto.setTieneJurado(true);
                }
                if (i.getIntegranteProyectoRolesModalidadRol().contains("Asesor")) {
                    dto.setTieneAsesor(true);
                }
            }
        }
        dto.setListaIntegrantesProyecto(lIntegrantes);
        return Optional.of(dto);
    }

    /**
     * Get one proyecto con asesor by id.
     *
     * @param idProyecto  the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    // public ProyectoDTO findOneWithAsesor(Long idProyecto, Long idRolModalkidad) throws Exception {
    public ProyectoDTO findOneWithAsesor(Long idProyecto) throws Exception {
        log.debug("Request to get Proyecto : {}", idProyecto);
        Proyecto p = new Proyecto();
        p = proyectoRepository.findByIdOrderById(idProyecto);

        Modalidad modalidad = p.getProyectoModalidad();
        Long modalidadId = modalidad.getId(); //eje 1551

        RolesModalidadDTO rolesModalidad;
        rolesModalidad = rolesModalidadService.findByRolAndRolesModalidadModalidadId("Asesor", modalidadId);
        Long rolesModalidadId = rolesModalidad.getId();

        IntegranteProyectoDTO integranteProyectoDTO = new IntegranteProyectoDTO();
        List<IntegranteProyectoDTO> lIntegranteProyectoDTO = new ArrayList();
        lIntegranteProyectoDTO =
            integranteProyectoService.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(idProyecto, rolesModalidadId);
        if (lIntegranteProyectoDTO != null) {
            integranteProyectoDTO = lIntegranteProyectoDTO.get(0);
        }
        Long integranteProyectoId = integranteProyectoDTO.getIntegranteProyectoUserId();

        ProyectoDTO proyectoDTO;

        proyectoDTO = proyectoMapper.toDto(p);
        proyectoDTO.setAsesorId(integranteProyectoId);

        log.debug("findOneWithAsesor - proyectoDTO linea: {}, sublinea: {}",
            proyectoDTO.getProyectoLineaInvestigacionId(), proyectoDTO.getSubLineaLineaInvestigacionId());

        return proyectoDTO;
    }

    /**
     * Delete the proyecto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Proyecto : {}", id);
        proyectoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProyectoDTO> findByIntegranteProyecto(Long idUsuaro) throws Exception {
        log.debug("Request to get all Elementos de una modalidad con una idModalidad");
        List<Proyecto> listaProyecto = new ArrayList<>();
        List<IntegranteProyecto> listaIntegranteProyecto = integranteProyectoService.findByIntegranteProyectoUserId(idUsuaro);
        if (listaIntegranteProyecto != null) {
            for (IntegranteProyecto integranteProyecto : listaIntegranteProyecto) {
                Proyecto proyecto = new Proyecto();
                proyecto = integranteProyecto.getIntegranteProyectoProyecto();
                listaProyecto.add(proyecto);
            }

            List<ProyectoDTO> listDTO = new ArrayList<>();

            for (Proyecto p : listaProyecto) {
                listDTO.add(proyectoMapper.toDto(p));
            }
            return listDTO;
        } else {
            return null;
        }
    }

    ///////////////////////////////77777777777777777777777
    @Transactional(readOnly = true)
    public List<ProyectoDTO> findByIntegranteProyectoAuthority(Long idUsuario, String authority) throws Exception {
        log.debug("Request to get all Proyectos de una modalidad con una idModalidad");
        //List<RolesModalidad> lRolesModalidad = rolesModalidadService.findByRolesModalidadAuthorityName(authority);

        List<Proyecto> listaProyecto = new ArrayList<>();
        List<IntegranteProyecto> listaIntegranteProyecto = integranteProyectoService.findByIntegranteProyectoAuthority(
            idUsuario,
            authority
        );
        if (listaIntegranteProyecto != null) {
            for (IntegranteProyecto integranteProyecto : listaIntegranteProyecto) {
                Proyecto proyecto = new Proyecto();
                proyecto = integranteProyecto.getIntegranteProyectoProyecto();
                listaProyecto.add(proyecto);
            }

            List<ProyectoDTO> listDTO = new ArrayList<>();

            for (Proyecto p : listaProyecto) {
                listDTO.add(proyectoMapper.toDto(p));
            }
            return listDTO;
        } else {
            return null;
        }
    }

    //////////////////////////////////////////77777777777777

    ///////////////////////////////77777777777777777777777
    @Transactional(readOnly = true)
    public List<ProyectoDTO> findByIntegranteProyectoRol(Long idUsuario, String rol) throws Exception {
        log.debug("Request to get all Proyectos de una modalidad con una idModalidad");
        //List<RolesModalidad> lRolesModalidad = rolesModalidadService.findByRolesModalidadAuthorityName(authority);

        List<Proyecto> listaProyecto = new ArrayList<>();
        List<IntegranteProyecto> listaIntegranteProyecto = integranteProyectoService.findByIntegranteProyectoRol(idUsuario, rol);
        if (listaIntegranteProyecto != null) {
            for (IntegranteProyecto integranteProyecto : listaIntegranteProyecto) {
                Proyecto proyecto = new Proyecto();
                proyecto = integranteProyecto.getIntegranteProyectoProyecto();
                listaProyecto.add(proyecto);
            }

            List<ProyectoDTO> listDTO = new ArrayList<>();

            for (Proyecto p : listaProyecto) {
                listDTO.add(proyectoMapper.toDto(p));
            }
            return listDTO;
        } else {
            return null;
        }
    }
}
