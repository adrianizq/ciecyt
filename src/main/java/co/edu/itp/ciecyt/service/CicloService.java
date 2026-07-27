package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.CicloDTO;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Ciclo}.
 */
public interface CicloService {

    /**
     * Save a ciclo.
     *
     * @param cicloDTO the entity to save.
     * @return the persisted entity.
     */
    CicloDTO save(CicloDTO cicloDTO);

    /**
     * Get all the ciclos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CicloDTO> findAll(Pageable pageable);

    /**
     * Get all the ciclos as a list.
     *
     * @return the list of entities.
     */
    List<CicloDTO> findAllList();

    /**
     * Get the "id" ciclo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CicloDTO> findOne(Long id);

    /**
     * Get modalidades for a given ciclo.
     *
     * @param cicloId the ciclo id.
     * @return the list of modalidad DTOs.
     */
    List<ModalidadDTO> findModalidadesByCicloId(Long cicloId);

    /**
     * Delete the "id" ciclo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
