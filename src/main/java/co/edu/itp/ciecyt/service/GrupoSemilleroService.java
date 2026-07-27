package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.GrupoSemilleroDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.GrupoSemillero}.
 */
public interface GrupoSemilleroService {

    /**
     * Save a grupoSemillero.
     *
     * @param grupoSemilleroDTO the entity to save.
     * @return the persisted entity.
     */
    GrupoSemilleroDTO save(GrupoSemilleroDTO grupoSemilleroDTO);

    /**
     * Get all the grupoSemilleros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GrupoSemilleroDTO> findAll(Pageable pageable);


    /**
     * Get the "id" grupoSemillero.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GrupoSemilleroDTO> findOne(Long id);

    /**
     * Delete the "id" grupoSemillero.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
