package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.RetroalimentacionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Retroalimentacion}.
 */
public interface RetroalimentacionService {

    /**
     * Save a retroalimentacion.
     *
     * @param retroalimentacionDTO the entity to save.
     * @return the persisted entity.
     */
    RetroalimentacionDTO save(RetroalimentacionDTO retroalimentacionDTO);

    /**
     * Get all the retroalimentacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RetroalimentacionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" retroalimentacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RetroalimentacionDTO> findOne(Long id);

    /**
     * Delete the "id" retroalimentacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
