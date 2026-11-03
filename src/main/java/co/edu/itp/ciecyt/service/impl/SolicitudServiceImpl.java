package co.edu.itp.ciecyt.service.impl;

import co.edu.itp.ciecyt.service.SolicitudService;
import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.Solicitud;
import co.edu.itp.ciecyt.repository.IntegranteProyectoRepository;
import co.edu.itp.ciecyt.repository.SolicitudRepository;
import co.edu.itp.ciecyt.service.NotificacionService;
import co.edu.itp.ciecyt.service.dto.SolicitudDTO;
import co.edu.itp.ciecyt.service.mapper.SolicitudMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Solicitud}.
 */
@Service
@Transactional
public class SolicitudServiceImpl implements SolicitudService {

    private final Logger log = LoggerFactory.getLogger(SolicitudServiceImpl.class);

    private final SolicitudRepository solicitudRepository;

    private final SolicitudMapper solicitudMapper;

    private final IntegranteProyectoRepository integranteProyectoRepository;

    private final NotificacionService notificacionService;

    public SolicitudServiceImpl(
        SolicitudRepository solicitudRepository,
        SolicitudMapper solicitudMapper,
        IntegranteProyectoRepository integranteProyectoRepository,
        NotificacionService notificacionService
    ) {
        this.solicitudRepository = solicitudRepository;
        this.solicitudMapper = solicitudMapper;
        this.integranteProyectoRepository = integranteProyectoRepository;
        this.notificacionService = notificacionService;
    }

    /**
     * Save a solicitud.
     *
     * @param solicitudDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SolicitudDTO save(SolicitudDTO solicitudDTO) {
        log.debug("Request to save Solicitud : {}", solicitudDTO);
        Optional<Solicitud> anterior = solicitudDTO.getId() != null
            ? solicitudRepository.findById(solicitudDTO.getId())
            : Optional.empty();
        boolean estabaResuelta = anterior.map(item -> Boolean.TRUE.equals(item.isEstado())).orElse(true);
        Solicitud solicitud = solicitudMapper.toEntity(solicitudDTO);
        solicitud = solicitudRepository.save(solicitud);
        if (!estabaResuelta && Boolean.TRUE.equals(solicitud.isEstado())) {
            notificarSolicitudResuelta(solicitudDTO);
        }
        return solicitudMapper.toDto(solicitud);
    }

    /**
     * Avisa al integrante que radico la solicitud cuando el CIECYT la marca como resuelta.
     * El aviso se genera una sola vez, en la transicion de pendiente a resuelta.
     */
    private void notificarSolicitudResuelta(SolicitudDTO solicitudDTO) {
        log.debug("Notificando solicitud resuelta id={} integrante={}", solicitudDTO.getId(), solicitudDTO.getSolicitudIntegranteProyectoId());
        if (solicitudDTO.getSolicitudIntegranteProyectoId() == null) {
            return;
        }
        IntegranteProyecto integrante = integranteProyectoRepository
            .findById(solicitudDTO.getSolicitudIntegranteProyectoId())
            .orElse(null);
        if (
            integrante == null ||
            integrante.getIntegranteProyectoUser() == null ||
            integrante.getIntegranteProyectoProyecto() == null
        ) {
            return;
        }
        String asunto = solicitudDTO.getAsunto() != null && !solicitudDTO.getAsunto().trim().isEmpty()
            ? solicitudDTO.getAsunto()
            : "solicitud";
        notificacionService.crearNotificacion(
            integrante.getIntegranteProyectoUser().getId(),
            "Solicitud resuelta",
            "El CIECYT resolvio su solicitud: " + asunto,
            integrante.getIntegranteProyectoProyecto().getId(),
            "SOLICITUD"
        );
    }

    /**
     * Get all the solicituds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SolicitudDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Solicituds");
        return solicitudRepository.findAll(pageable)
            .map(solicitudMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudDTO> findByProyectoId(Long proyectoId) {
        return solicitudRepository.findByProyectoId(proyectoId).stream()
            .map(solicitudMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get one solicitud by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SolicitudDTO> findOne(Long id) {
        log.debug("Request to get Solicitud : {}", id);
        return solicitudRepository.findById(id)
            .map(solicitudMapper::toDto);
    }

    /**
     * Delete the solicitud by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Solicitud : {}", id);
        solicitudRepository.deleteById(id);
    }
}
