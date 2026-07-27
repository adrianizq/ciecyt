package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.EntidadFinanciadoraDTO;

import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.EntidadFinanciadora}.
 */
public interface EntidadFinanciadoraService {

    /**
     * Save a entidadFinanciadora.
     *
     * @param entidadFinanciadoraDTO the entity to save.
     * @return the persisted entity.
     */
    EntidadFinanciadoraDTO save(EntidadFinanciadoraDTO entidadFinanciadoraDTO);

    /**
     * Get all the entidadFinanciadoras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntidadFinanciadoraDTO> findAll(Pageable pageable);


    /**
     * Get the "id" entidadFinanciadora.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntidadFinanciadoraDTO> findOne(Long id);

    /**
     * Delete the "id" entidadFinanciadora.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<EntidadFinanciadoraDTO> findByEntidadFinanciadoraProyectoId(Long idProyecto) throws Exception ;
}
