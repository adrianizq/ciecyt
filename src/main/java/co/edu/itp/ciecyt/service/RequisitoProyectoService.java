package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoRequisito;
import co.edu.itp.ciecyt.service.dto.RequisitoProyectoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.RequisitoProyecto}.
 */
public interface RequisitoProyectoService {
    /**
     * Save a requisitoProyecto.
     *
     * @param requisitoProyectoDTO the entity to save.
     * @return the persisted entity.
     */
    RequisitoProyectoDTO save(RequisitoProyectoDTO requisitoProyectoDTO);

    /**
     * Get all the requisitoProyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequisitoProyectoDTO> findAll(Pageable pageable);

    /**
     * Get all requisitos de un proyecto.
     *
     * @param proyectoId id del proyecto.
     * @return the list of entities.
     */
    List<RequisitoProyectoDTO> findByProyectoId(Long proyectoId);

    /**
     * Get the "id" requisitoProyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequisitoProyectoDTO> findOne(Long id);

    /**
     * Delete the "id" requisitoProyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Genera los registros de requisitos habilitantes faltantes para un proyecto,
     * según los requisitos activos de su modalidad.
     *
     * @param proyectoId id del proyecto.
     * @return the list of entities.
     */
    List<RequisitoProyectoDTO> generarParaProyecto(Long proyectoId);

    /**
     * Marca un requisito como entregado por parte del estudiante.
     *
     * @param id id del requisitoProyecto.
     * @param archivo referencia al archivo cargado (opcional).
     * @return el DTO actualizado.
     */
    RequisitoProyectoDTO entregar(Long id, String archivo);

    /**
     * Aprueba o rechaza un requisito por parte de CIECYT.
     *
     * @param id id del requisitoProyecto.
     * @param aprobado true para aprobar, false para rechazar.
     * @param observacion observación de la validación.
     * @return el DTO actualizado.
     */
    RequisitoProyectoDTO validar(Long id, boolean aprobado, String observacion);
}
