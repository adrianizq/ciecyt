package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.CicloPropedeuticoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.CicloPropedeutico}.
 */
public interface CicloPropedeuticoService {

    /**
     * Save a cicloPropedeutico.
     *
     * @param cicloPropedeuticoDTO the entity to save.
     * @return the persisted entity.
     */
    CicloPropedeuticoDTO save(CicloPropedeuticoDTO cicloPropedeuticoDTO);

    /**
     * Get all the cicloPropedeuticos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CicloPropedeuticoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cicloPropedeutico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CicloPropedeuticoDTO> findOne(Long id);

    /**
     * Delete the "id" cicloPropedeutico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
