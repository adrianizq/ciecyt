package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.SolicitudDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Solicitud}.
 */
public interface SolicitudService {

    /**
     * Save a solicitud.
     *
     * @param solicitudDTO the entity to save.
     * @return the persisted entity.
     */
    SolicitudDTO save(SolicitudDTO solicitudDTO);

    /**
     * Get all the solicituds.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SolicitudDTO> findAll(Pageable pageable);


    /**
     * Get the "id" solicitud.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SolicitudDTO> findOne(Long id);

    /**
     * Delete the "id" solicitud.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
