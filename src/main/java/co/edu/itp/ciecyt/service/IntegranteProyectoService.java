package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.IntegranteProyecto}.
 */
public interface IntegranteProyectoService {
    /**
     * Save a integranteProyecto.
     *
     * @param integranteProyectoDTO the entity to save.
     * @return the persisted entity.
     */
    IntegranteProyectoDTO save(IntegranteProyectoDTO integranteProyectoDTO);

    /**
     * Get all the integranteProyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IntegranteProyectoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" integranteProyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IntegranteProyectoDTO> findOne(Long id);

    /**
     * Delete the "id" integranteProyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<IntegranteProyectoDTO> findByIntegranteProyectoProyectoId(Long idProyecto) throws Exception;
    List<IntegranteProyectoDTO> findEstudiantesIntegranteProyectoId(Long idProyecto) throws Exception;
    List<IntegranteProyectoDTO> findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(
        Long idProyecto,
        Long idRolModalidad
    )
        throws Exception;
    IntegranteProyectoDTO findOneOrderById(Long id) throws Exception;
    //devuelve los IntegranteProyecto de un idUsuario
    List<IntegranteProyecto> findByIntegranteProyectoUserId(Long idUsuario) throws Exception;

    // List<IntegranteProyecto> findByIntegranteProyectoUserAutority (Long idUsuario, String authority)throws Exception;
    List<IntegranteProyecto> findByIntegranteProyectoAuthority(Long idUsuario, String authority) throws Exception;
    List<IntegranteProyecto> findByIntegranteProyectoRol(Long idUsuario, String rol) throws Exception;

    List<IntegranteProyectoDTO> findJuradosIntegranteProyectoId(Long idProyecto, String tipoJurado) throws Exception;
    List<IntegranteProyectoDTO> findAsesoresIntegranteProyectoId(Long idProyecto) throws Exception;
}
