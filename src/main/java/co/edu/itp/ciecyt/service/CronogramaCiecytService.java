package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.CronogramaCiecytDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.CronogramaCiecyt}.
 */
public interface CronogramaCiecytService {

    /**
     * Save a cronogramaCiecyt.
     *
     * @param cronogramaCiecytDTO the entity to save.
     * @return the persisted entity.
     */
    CronogramaCiecytDTO save(CronogramaCiecytDTO cronogramaCiecytDTO);

    /**
     * Get all the cronogramaCiecyts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CronogramaCiecytDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cronogramaCiecyt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CronogramaCiecytDTO> findOne(Long id);

    /**
     * Delete the "id" cronogramaCiecyt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
