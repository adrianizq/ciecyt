package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.service.dto.ResultadosEsperadosDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.ResultadosEsperados}.
 */
public interface ResultadosEsperadosService {

    /**
     * Save a resultadosEsperados.
     *
     * @param resultadosEsperadosDTO the entity to save.
     * @return the persisted entity.
     */
    ResultadosEsperadosDTO save(ResultadosEsperadosDTO resultadosEsperadosDTO);

    /**
     * Get all the resultadosEsperados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResultadosEsperadosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" resultadosEsperados.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResultadosEsperadosDTO> findOne(Long id);

    /**
     * Delete the "id" resultadosEsperados.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ResultadosEsperadosDTO> findByResultadosEsperadosProyectoId(Long idProyecto) throws Exception ;
}
