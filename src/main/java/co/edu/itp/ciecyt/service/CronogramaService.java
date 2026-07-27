package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.CronogramaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Cronograma}.
 */
public interface CronogramaService {

    /**
     * Save a cronograma.
     *
     * @param cronogramaDTO the entity to save.
     * @return the persisted entity.
     */
    CronogramaDTO save(CronogramaDTO cronogramaDTO);

    /**
     * Get all the cronogramas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CronogramaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cronograma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CronogramaDTO> findOne(Long id);

    /**
     * Delete the "id" cronograma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    public List<CronogramaDTO> findByCronogramaProyectoId(Long idProyecto) throws Exception;
}
