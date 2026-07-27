package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.MunicipioDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Municipio}.
 */
public interface MunicipioService {
    /**
     * Save a municipio.
     *
     * @param municipioDTO the entity to save.
     * @return the persisted entity.
     */
    MunicipioDTO save(MunicipioDTO municipioDTO);

    /**
     * Get all the municipios.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MunicipioDTO> findAll(Pageable pageable);

    List<MunicipioDTO> findAllNoPage() throws Exception;

    List<MunicipioDTO> findByCodigoDaneDepartamento(String codDaneDpto) throws Exception;

    List<MunicipioDTO> findByDepartamento(String departamento) throws Exception;

    /**
     * Get the "id" municipio.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MunicipioDTO> findOne(Long id);

    /**
     * Delete the "id" municipio.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
