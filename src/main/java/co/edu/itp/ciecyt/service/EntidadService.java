package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.EntidadDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Entidad}.
 */
public interface EntidadService {

    /**
     * Save a entidad.
     *
     * @param entidadDTO the entity to save.
     * @return the persisted entity.
     */
    EntidadDTO save(EntidadDTO entidadDTO);

    /**
     * Get all the entidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntidadDTO> findAll(Pageable pageable);


    /**
     * Get the "id" entidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntidadDTO> findOne(Long id);

    /**
     * Delete the "id" entidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
