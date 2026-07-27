package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.PreguntaModalidad;
import co.edu.itp.ciecyt.service.dto.PreguntaModalidadDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.PreguntaModalidad}.
 */
public interface PreguntaModalidadService {

    /**
     * Save a preguntaModalidad.
     *
     * @param preguntaModalidadDTO the entity to save.
     * @return the persisted entity.
     */
    PreguntaModalidadDTO save(PreguntaModalidadDTO preguntaModalidadDTO);

    /**
     * Get all the preguntaModalidads.
     *
     * @return the list of entities.
     */
    List<PreguntaModalidadDTO> findAll();



    /**
     * Get the "id" preguntaModalidad.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreguntaModalidadDTO> findOne(Long id);

    /**
     * Delete the "id" preguntaModalidad.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    public List<PreguntaModalidadDTO> findByModalidad2Id(Long idModalidad);
    public List<PreguntaModalidadDTO> findByPreguntaId(Long idPregunta);


       // public PreguntaModalidad findByPreguntaIdAndModalidad2Id(Long idPregunta, Long idModalidad);
}
