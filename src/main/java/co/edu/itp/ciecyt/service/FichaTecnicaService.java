package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.FichaTecnicaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.FichaTecnica}.
 */
public interface FichaTecnicaService {

    /**
     * Save a fichaTecnica.
     *
     * @param fichaTecnicaDTO the entity to save.
     * @return the persisted entity.
     */
    FichaTecnicaDTO save(FichaTecnicaDTO fichaTecnicaDTO);

    /**
     * Get all the fichaTecnicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FichaTecnicaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fichaTecnica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FichaTecnicaDTO> findOne(Long id);

    /**
     * Delete the "id" fichaTecnica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
