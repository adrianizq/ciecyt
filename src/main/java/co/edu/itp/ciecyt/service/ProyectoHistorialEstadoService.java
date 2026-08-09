package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.ProyectoHistorialEstadoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.ProyectoHistorialEstado}.
 */
public interface ProyectoHistorialEstadoService {

    /**
     * Save a proyectoHistorialEstado.
     *
     * @param proyectoHistorialEstadoDTO the entity to save.
     * @return the persisted entity.
     */
    ProyectoHistorialEstadoDTO save(ProyectoHistorialEstadoDTO proyectoHistorialEstadoDTO);

    /**
     * Get all the proyectoHistorialEstados for a given proyecto.
     *
     * @param proyectoId the proyecto id.
     * @return the list of entities.
     */
    List<ProyectoHistorialEstadoDTO> findByProyectoId(Long proyectoId);

    /**
     * Get the "id" proyectoHistorialEstado.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProyectoHistorialEstadoDTO> findOne(Long id);

    /**
     * Delete the "id" proyectoHistorialEstado.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
