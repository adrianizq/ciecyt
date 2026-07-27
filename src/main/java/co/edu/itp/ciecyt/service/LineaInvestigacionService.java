package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.LineaInvestigacionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.LineaInvestigacion}.
 */
public interface LineaInvestigacionService {

    /**
     * Save a lineaInvestigacion.
     *
     * @param lineaInvestigacionDTO the entity to save.
     * @return the persisted entity.
     */
    LineaInvestigacionDTO save(LineaInvestigacionDTO lineaInvestigacionDTO);

    /**
     * Get all the lineaInvestigacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LineaInvestigacionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" lineaInvestigacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LineaInvestigacionDTO> findOne(Long id);

    /**
     * Delete the "id" lineaInvestigacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
