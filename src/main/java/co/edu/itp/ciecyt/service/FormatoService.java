package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.FormatoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Formato}.
 */
public interface FormatoService {

    /**
     * Save a formato.
     *
     * @param formatoDTO the entity to save.
     * @return the persisted entity.
     */
    FormatoDTO save(FormatoDTO formatoDTO);

    /**
     * Get all the formatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FormatoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" formato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FormatoDTO> findOne(Long id);

    /**
     * Delete the "id" formato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    @Transactional(readOnly = true)
    FormatoDTO findByCodigo(String codigo) throws Exception;
}
