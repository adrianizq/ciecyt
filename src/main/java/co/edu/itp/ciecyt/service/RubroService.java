package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.RubroDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Rubro}.
 */
public interface RubroService {

    /**
     * Save a rubro.
     *
     * @param rubroDTO the entity to save.
     * @return the persisted entity.
     */
    RubroDTO save(RubroDTO rubroDTO);

    /**
     * Get all the rubros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RubroDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rubro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RubroDTO> findOne(Long id);

    /**
     * Delete the "id" rubro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
