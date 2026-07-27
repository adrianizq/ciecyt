package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;

import co.edu.itp.ciecyt.service.dto.ResultadosEsperadosDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.ImpactosEsperados}.
 */
public interface ImpactosEsperadosService {

    /**
     * Save a impactosEsperados.
     *
     * @param impactosEsperadosDTO the entity to save.
     * @return the persisted entity.
     */
    ImpactosEsperadosDTO save(ImpactosEsperadosDTO impactosEsperadosDTO);

    /**
     * Get all the impactosEsperados.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImpactosEsperadosDTO> findAll(Pageable pageable);


    /**
     * Get the "id" impactosEsperados.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImpactosEsperadosDTO> findOne(Long id);

    /**
     * Delete the "id" impactosEsperados.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ImpactosEsperadosDTO> findByImpactosEsperadoProyectoId(Long idProyecto) throws Exception ;
}
