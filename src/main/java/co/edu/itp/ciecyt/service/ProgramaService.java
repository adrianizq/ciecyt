package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.ProgramaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Programa}.
 */
public interface ProgramaService {

    /**
     * Save a programa.
     *
     * @param programaDTO the entity to save.
     * @return the persisted entity.
     */
    ProgramaDTO save(ProgramaDTO programaDTO);

    /**
     * Get all the programas.
     *
     * @return the list of entities.
     */
    List<ProgramaDTO> findAll();


    /**
     * Get the "id" programa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProgramaDTO> findOne(Long id);

    /**
     * Delete the "id" programa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get programas by ciclo name.
     *
     * @param ciclo the ciclo name.
     * @return the list of entities.
     */
    List<ProgramaDTO> findByCiclo(String ciclo);
}
