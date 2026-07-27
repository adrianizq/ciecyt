package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.AcuerdoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Acuerdo}.
 */
public interface AcuerdoService {

    /**
     * Save a acuerdo.
     *
     * @param acuerdoDTO the entity to save.
     * @return the persisted entity.
     */
    AcuerdoDTO save(AcuerdoDTO acuerdoDTO);

    /**
     * Get all the acuerdos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AcuerdoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" acuerdo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AcuerdoDTO> findOne(Long id);

    /**
     * Delete the "id" acuerdo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
