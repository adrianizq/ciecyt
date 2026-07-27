package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.CronogramaCiecytFasesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.CronogramaCiecytFases}.
 */
public interface CronogramaCiecytFasesService {

    /**
     * Save a cronogramaCiecytFases.
     *
     * @param cronogramaCiecytFasesDTO the entity to save.
     * @return the persisted entity.
     */
    CronogramaCiecytFasesDTO save(CronogramaCiecytFasesDTO cronogramaCiecytFasesDTO);

    /**
     * Get all the cronogramaCiecytFases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CronogramaCiecytFasesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cronogramaCiecytFases.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CronogramaCiecytFasesDTO> findOne(Long id);

    /**
     * Delete the "id" cronogramaCiecytFases.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
