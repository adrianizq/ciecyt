package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.RequisitoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Requisito}.
 */
public interface RequisitoService {
    /**
     * Save a requisito.
     *
     * @param requisitoDTO the entity to save.
     * @return the persisted entity.
     */
    RequisitoDTO save(RequisitoDTO requisitoDTO);

    /**
     * Get all the requisitos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequisitoDTO> findAll(Pageable pageable);

    /**
     * Get all active requisitos.
     *
     * @return the list of entities.
     */
    List<RequisitoDTO> findAllActivos();

    /**
     * Get all active requisitos de una modalidad (incluye los globales sin modalidad).
     *
     * @param modalidadId id de la modalidad.
     * @return the list of entities.
     */
    List<RequisitoDTO> findByModalidad(Long modalidadId);

    /**
     * Get the "id" requisito.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequisitoDTO> findOne(Long id);

    /**
     * Delete the "id" requisito.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
