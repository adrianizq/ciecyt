package co.edu.itp.ciecyt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import co.edu.itp.ciecyt.service.dto.AdjuntoProyectoFaseDTO;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.AdjuntoProyectoFase}.
 */
public interface AdjuntoProyectoFaseService {

    /**
     * Save a adjuntoProyectoFase.
     *
     * @param adjuntoProyectoFaseDTO the entity to save.
     * @return the persisted entity.
     */
    AdjuntoProyectoFaseDTO save(AdjuntoProyectoFaseDTO adjuntoProyectoFaseDTO);

    /**
     * Get all the adjuntoProyectoFases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdjuntoProyectoFaseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" adjuntoProyectoFase.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdjuntoProyectoFaseDTO> findOne(Long id);

    /**
     * Delete the "id" adjuntoProyectoFase.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Carga un archivo al directorio de adjuntos de los proyectos
     * @param dto
     * @param file
     * @param contentType
     */
    void attachFile(AdjuntoProyectoFaseDTO dto, byte[] file, String contentType);

    /**
     * Carga un archivo del directorio base
     * @param dto
     * @return
     * @throws Exception
     */
    Resource loadFileAsResource(AdjuntoProyectoFaseDTO dto) throws Exception;

    List<AdjuntoProyectoFaseDTO> findByProyectoFaseProyectoIdAndAdjuntoProyectoFaseFaseId(Long idProyecto, Long idFase) throws Exception;

}
