package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.TipoPreguntaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.TipoPregunta}.
 */
public interface TipoPreguntaService {

    /**
     * Save a tipoPregunta.
     *
     * @param tipoPreguntaDTO the entity to save.
     * @return the persisted entity.
     */
    TipoPreguntaDTO save(TipoPreguntaDTO tipoPreguntaDTO);

    /**
     * Get all the tipoPreguntas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TipoPreguntaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tipoPregunta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoPreguntaDTO> findOne(Long id);

    /**
     * Delete the "id" tipoPregunta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
