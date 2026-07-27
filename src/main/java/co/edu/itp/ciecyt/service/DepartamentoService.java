package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.DepartamentoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Departamento}.
 */
public interface DepartamentoService {

    /**
     * Save a departamento.
     *
     * @param departamentoDTO the entity to save.
     * @return the persisted entity.
     */
    DepartamentoDTO save(DepartamentoDTO departamentoDTO);

    /**
     * Get all the departamentos.
     *
     * @return the list of entities.
     */
    List<DepartamentoDTO> findAll();


    /**
     * Get the "id" departamento.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepartamentoDTO> findOne(Long id);

    /**
     * Delete the "id" departamento.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
