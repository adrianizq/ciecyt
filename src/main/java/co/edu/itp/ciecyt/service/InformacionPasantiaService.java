package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import  co.edu.itp.ciecyt.service.dto.InformacionPasantiaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;



/**
 * Service Interface for managing {@link package co.edu.itp.domain.InformacionPasantia}.
 */
public interface InformacionPasantiaService {

    /**
     * Save a informacionPasantia.
     *
     * @param informacionPasantiaDTO the entity to save.
     * @return the persisted entity.
     */
    InformacionPasantiaDTO save(InformacionPasantiaDTO informacionPasantiaDTO);

    /**
     * Get all the informacionPasantias.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InformacionPasantiaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" informacionPasantia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InformacionPasantiaDTO> findOne(Long id);

    /**
     * Delete the "id" informacionPasantia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /////////7
    InformacionPasantiaDTO findByInformacionPasantiaProyectoId(Long idProyecto) throws Exception ;
}
