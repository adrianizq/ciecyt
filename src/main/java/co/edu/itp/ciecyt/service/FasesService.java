package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.Fases;
import co.edu.itp.ciecyt.service.dto.FasesDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Fases}.
 */
public interface FasesService {

    /**
     * Save a fases.
     *
     * @param fasesDTO the entity to save.
     * @return the persisted entity.
     */
    FasesDTO save(FasesDTO fasesDTO);

    /**
     * Get all the fases.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FasesDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fases.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FasesDTO> findOne(Long id);

    /**
     * Delete the "id" fases.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    @Transactional(readOnly = true)
    FasesDTO findByFase(String fase) throws Exception;

    /*@Transactional(readOnly = true)
    FasesDTO findByFaseAndFasesModalidadId(String fase, Long idModalidad) throws Exception;

    @Transactional(readOnly = true)
    public List<FasesDTO> findByFasesModalidadId(Long idModalidad) throws Exception;

     */
}
