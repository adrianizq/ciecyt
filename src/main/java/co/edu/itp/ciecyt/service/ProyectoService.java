package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.dto.RequisitoProyectoDTO;
import co.edu.itp.ciecyt.service.dto.TransicionEstadoDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Proyecto}.
 */
public interface ProyectoService {
    /**
     * Save a proyecto.
     *
     * @param proyectoDTO the entity to save.
     * @return the persisted entity.
     */
    ProyectoDTO save(ProyectoDTO proyectoDTO);

    ProyectoDTO saveAsesorProyecto(ProyectoDTO proyectoDTO) throws Exception;

    /**
     * Cambia el estado de un proyecto y registra el historial.
     *
     * @param proyectoId id del proyecto.
     * @param nuevoEstado estado destino.
     * @param observacion observación opcional.
     * @return el proyecto actualizado.
     */
    ProyectoDTO cambiarEstado(Long proyectoId, EnumEstadoProyecto nuevoEstado, String observacion);

    /**
     * Solicita la validación documental del proyecto ante CIECYT (Acuerdo 29).
     *
     * @param proyectoId id del proyecto.
     * @return el proyecto actualizado.
     */
    ProyectoDTO solicitarValidacionDocumental(Long proyectoId);

    /**
     * CIECYT aprueba u observa la documentación del proyecto.
     *
     * @param proyectoId id del proyecto.
     * @param aprobado true para habilitar, false para observar.
     * @param observacion observación de la validación.
     * @return el proyecto actualizado.
     */
    ProyectoDTO validarDocumentacion(Long proyectoId, boolean aprobado, String observacion);

    /**
     * Obtiene (generando si faltan) los requisitos del proyecto.
     *
     * @param proyectoId id del proyecto.
     * @return lista de requisitos del proyecto.
     */
    List<RequisitoProyectoDTO> getRequisitosProyecto(Long proyectoId);

    /**
     * Obtiene las transiciones de estado permitidas para el proyecto desde su estado actual.
     *
     * @param proyectoId id del proyecto.
     * @return lista de transiciones permitidas.
     */
    List<TransicionEstadoDTO> getTransicionesPermitidas(Long proyectoId);

    /**
     * Get all the proyectos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProyectoDTO> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    //Page<ProyectoDTO> findAllProyectosIntegrantes(Pageable pageable) throws Exception;
    public List<ProyectoDTO> findAllProyectosIntegrantes() throws Exception;

    /**
     * Get the "id" proyecto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProyectoDTO> findOne(Long id);

    @Transactional(readOnly = true)
    ProyectoDTO findOneWithAsesor(Long idProyecto) throws Exception;

    /**
     * Delete the "id" proyecto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ProyectoDTO> findByIntegranteProyecto(Long idUsuario) throws Exception;
    //ProyectoDTO findOneIntegrantes(Long id) ;
    List<ProyectoDTO> findByIntegranteProyectoAuthority(Long idUsuario, String authority) throws Exception;
    List<ProyectoDTO> findByIntegranteProyectoRol(Long idUsuario, String rol) throws Exception;

    //List<IntegranteProyecto> findByIntegranteProyectoUserId(Long idUsuario, String authority);
    Optional<ProyectoDTO> findOneIntegrantes(Long id) throws Exception;
}
