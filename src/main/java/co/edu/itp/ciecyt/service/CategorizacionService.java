package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.CategorizacionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Categorizacion}.
 */
public interface CategorizacionService {

    /**
     * Save a categorizacion.
     *
     * @param categorizacionDTO the entity to save.
     * @return the persisted entity.
     */
    CategorizacionDTO save(CategorizacionDTO categorizacionDTO);

    /**
     * Get all the categorizacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorizacionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" categorizacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorizacionDTO> findOne(Long id);

    /**
     * Delete the "id" categorizacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
