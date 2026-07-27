package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.ProyectoFaseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.ProyectoFase}.
 */
public interface ProyectoFaseService {

    /**
     * Save a proyectoFase.
     *
     * @param proyectoFaseDTO the entity to save.
     * @return the persisted entity.
     */
    ProyectoFaseDTO save(ProyectoFaseDTO proyectoFaseDTO);

    /**
     * Get all the proyectoFases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProyectoFaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" proyectoFase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProyectoFaseDTO> findOne(Long id);

    /**
     * Delete the "id" proyectoFase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
