package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ProyectoRespuestasDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.ProyectoRespuestas}.
 */
public interface ProyectoRespuestasService {

    /**
     * Save a proyectoRespuestas.
     *
     * @param proyectoRespuestasDTO the entity to save.
     * @return the persisted entity.
     */
    ProyectoRespuestasDTO save(ProyectoRespuestasDTO proyectoRespuestasDTO);

    /**
     * Get all the proyectoRespuestas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProyectoRespuestasDTO> findAll(Pageable pageable);


    /**
     * Get the "id" proyectoRespuestas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProyectoRespuestasDTO> findOne(Long id);

    /**
     * Delete the "id" proyectoRespuestas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ProyectoRespuestasDTO> findByProyectoRespuestasProyectoId(Long idProyecto) throws Exception ;

    List<ProyectoRespuestasDTO> findByProyectoRespuestasProyectoIdFaseAuthority(Long idProyecto, Long idFase, String Authority) throws Exception;
}
