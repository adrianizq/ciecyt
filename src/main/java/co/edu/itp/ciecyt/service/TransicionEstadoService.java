package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.service.dto.TransicionEstadoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.TransicionEstado}.
 */
public interface TransicionEstadoService {
    /**
     * Save a transicionEstado.
     *
     * @param transicionEstadoDTO the entity to save.
     * @return the persisted entity.
     */
    TransicionEstadoDTO save(TransicionEstadoDTO transicionEstadoDTO);

    /**
     * Get all the transicionEstados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransicionEstadoDTO> findAll(Pageable pageable);

    /**
     * Get the transiciones activas de una modalidad.
     *
     * @param modalidadId id de la modalidad.
     * @return the list of entities.
     */
    List<TransicionEstadoDTO> findByModalidadId(Long modalidadId);

    /**
     * Get the transiciones activas desde un estado origen en una modalidad.
     *
     * @param modalidadId id de la modalidad.
     * @param estadoOrigen estado origen.
     * @return the list of entities.
     */
    List<TransicionEstadoDTO> findByModalidadIdAndEstadoOrigen(Long modalidadId, EnumEstadoProyecto estadoOrigen);

    /**
     * Get the "id" transicionEstado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransicionEstadoDTO> findOne(Long id);

    /**
     * Delete the "id" transicionEstado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
