package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.Pregunta;
import co.edu.itp.ciecyt.service.dto.ElementoDTO;
import co.edu.itp.ciecyt.service.dto.PreguntaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Pregunta}.
 */
public interface PreguntaService {

    /**
     * Save a pregunta.
     *
     * @param preguntaDTO the entity to save.
     * @return the persisted entity.
     */
    PreguntaDTO save(PreguntaDTO preguntaDTO);

    PreguntaDTO saveModalidadAuthority(PreguntaDTO preguntaDTO);

    /**
     * Get all the preguntas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PreguntaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pregunta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PreguntaDTO> findOne(Long id);

    /**
     * Delete the "id" pregunta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    public List<PreguntaDTO> findByPreguntaModalidadId(Long idModalidad) throws Exception;
    public List<PreguntaDTO> findByPreguntaModalidadIdAndPreguntaFaseId(Long idModalidad, Long idFase) throws Exception;
    public List<PreguntaDTO> findByPreguntaModalidadIdAndPreguntaFaseIdAndAuthority(Long idModalidad, Long idFase, String authority) throws Exception;
}
