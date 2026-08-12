package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.EstadoModalidadDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.EstadoModalidad}.
 */
public interface EstadoModalidadService {
    /**
     * Save a estadoModalidad.
     *
     * @param estadoModalidadDTO the entity to save.
     * @return the persisted entity.
     */
    EstadoModalidadDTO save(EstadoModalidadDTO estadoModalidadDTO);

    /**
     * Get all the estadoModalidads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EstadoModalidadDTO> findAll(Pageable pageable);

    /**
     * Get the estados del flujo de una modalidad, ordenados.
     *
     * @param modalidadId id de la modalidad.
     * @return the list of entities.
     */
    List<EstadoModalidadDTO> findByModalidadId(Long modalidadId);

    /**
     * Get the "id" estadoModalidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadoModalidadDTO> findOne(Long id);

    /**
     * Delete the "id" estadoModalidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
