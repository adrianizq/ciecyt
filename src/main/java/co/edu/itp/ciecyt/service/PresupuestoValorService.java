package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.PresupuestoValorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.PresupuestoValor}.
 */
public interface PresupuestoValorService {

    /**
     * Save a presupuestoValor.
     *
     * @param presupuestoValorDTO the entity to save.
     * @return the persisted entity.
     */
    PresupuestoValorDTO save(PresupuestoValorDTO presupuestoValorDTO);

    /**
     * Get all the presupuestoValors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PresupuestoValorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" presupuestoValor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PresupuestoValorDTO> findOne(Long id);

    /**
     * Delete the "id" presupuestoValor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<PresupuestoValorDTO> findByPresupuestoValorProyectoId(Long idProyecto) throws Exception ;

}
