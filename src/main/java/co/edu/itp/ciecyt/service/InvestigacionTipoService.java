package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.InvestigacionTipo;
import co.edu.itp.ciecyt.service.dto.InvestigacionTipoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.InvestigacionTipo}.
 */
public interface InvestigacionTipoService {
    /**
     * Save a investigacionTipo.
     *
     * @param investigacionTipoDTO the entity to save.
     * @return the persisted entity.
     */
    InvestigacionTipoDTO save(InvestigacionTipoDTO investigacionTipoDTO);

    /**
     * Get all the investigacionTipos.
     *
     * @return the list of entities.
     */
    List<InvestigacionTipoDTO> findAll();

    /**
     * Get the "id" investigacionTipo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InvestigacionTipoDTO> findOne(Long id);

    /**
     * Delete the "id" investigacionTipo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    InvestigacionTipo findByInvestigacionTipo(String tipo);
}
