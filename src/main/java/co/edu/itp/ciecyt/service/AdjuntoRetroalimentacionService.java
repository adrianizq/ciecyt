package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.AdjuntoRetroalimentacionDTO;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.AdjuntoRetroalimentacion}.
 */
public interface AdjuntoRetroalimentacionService {

    /**
     * Save a adjuntoRetroalimentacion.
     *
     * @param adjuntoRetroalimentacionDTO the entity to save.
     * @return the persisted entity.
     */
    AdjuntoRetroalimentacionDTO save(AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO);

    /**
     * Get all the adjuntoRetroalimentacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AdjuntoRetroalimentacionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" adjuntoRetroalimentacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdjuntoRetroalimentacionDTO> findOne(Long id);

    /**
     * Delete the "id" adjuntoRetroalimentacion.
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
    void attachFile(AdjuntoRetroalimentacionDTO dto, byte[] file, String contentType);

    /**
     * Carga un archivo del directorio base
     * @param dto
     * @return
     * @throws Exception
     */
    Resource loadFileAsResource(AdjuntoRetroalimentacionDTO dto) throws Exception;

    List<AdjuntoRetroalimentacionDTO> findByAdjuntoRetroalimentacionProyectoIdAndAdjuntoRetroalimentacionFaseIdAndAuthority(Long idProyecto, Long idFase, String authority) throws Exception;


    }
