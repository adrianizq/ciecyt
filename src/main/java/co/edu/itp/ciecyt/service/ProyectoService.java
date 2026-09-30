package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.IntegranteProyecto;
import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.dto.RequisitoProyectoDTO;
import co.edu.itp.ciecyt.service.dto.TransicionEstadoDTO;
import java.time.LocalDate;
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
     * Programa el acto público del proyecto ante CIECYT, con la fecha acordada.
     * La modalidad define si el acto es una sustentación con jurado o una socialización sin jurado.
     *
     * @param proyectoId id del proyecto.
     * @param fecha fecha programada del acto público.
     * @return el proyecto actualizado.
     */
    @Transactional
    ProyectoDTO programarActo(Long proyectoId, LocalDate fecha);

    /**
     * Registra que el acto público ya se realizó, habilitando la evaluación del jurado o del asesor.
     *
     * @param proyectoId id del proyecto.
     * @return el proyecto actualizado.
     */
    @Transactional
    ProyectoDTO registrarActoRealizado(Long proyectoId);

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
     * Inicia la calidad de continuidad del proyecto (Acuerdo 025, art. 10, parágrafo 3):
     * terminado el plan de estudios, el estudiante dispone de tres (3) periodos académicos
     * para sustentar o socializar su opción de grado.
     *
     * @param proyectoId id del proyecto.
     * @return el proyecto actualizado.
     */
    ProyectoDTO iniciarContinuidad(Long proyectoId);

    /**
     * Registra una renovación de matrícula durante la continuidad (Acuerdo 025, art. 10, parágrafo 4).
     * Incluye el pago de los derechos complementarios más el 20% de la matrícula ordinaria.
     * Al completar los tres (3) periodos sin sustentar o socializar, el derecho a la opción
     * de grado se pierde definitivamente (parágrafo 5).
     *
     * @param proyectoId id del proyecto.
     * @return el proyecto actualizado.
     */
    ProyectoDTO registrarRenovacionContinuidad(Long proyectoId);

    /**
     * Otorga el periodo académico adicional de continuidad (Acuerdo 025, art. 10, parágrafo 6):
     * excepcionalmente, por fuerza mayor o caso fortuito debidamente justificado, el estudiante
     * podrá solicitar un aplazamiento que le otorga un (1) periodo más a los tres (3) periodos
     * de continuidad, cancelando los derechos complementarios más el 20% de la matrícula ordinaria.
     *
     * @param proyectoId id del proyecto.
     * @return el proyecto actualizado.
     */
    ProyectoDTO otorgarAplazamientoContinuidad(Long proyectoId);

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

    /**
     * Listado de proyectos acotado al alcance del usuario: el gestor global ve todo y el decano
     * solo los de las facultades que tiene asignadas. Un rol sin ninguna de las dos condiciones
     * no puede listar.
     */
    Page<ProyectoDTO> findAllDeAlcance(Pageable pageable);

    /**
     * Igual que findAllDeAlcance pero con la lista de integrantes de cada proyecto.
     */
    List<ProyectoDTO> findAllProyectosIntegrantesDeAlcance() throws Exception;

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
