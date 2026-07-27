package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.service.dto.RolMenuDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.RolMenu}.
 */
public interface RolMenuService {

    /**
     * Save a rolMenu.
     *
     * @param rolMenuDTO the entity to save.
     * @return the persisted entity.
     */
    RolMenuDTO save(RolMenuDTO rolMenuDTO);

    /**
     * Get all the rolMenus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RolMenuDTO> findAll(Pageable pageable);


    /**ADR
     * Get all the rolMenus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RolMenuDTO> buscarAllByAuthority(String authority, Pageable pageable);
    //Page<RolMenuDTO> buscarRolMenuAuthority(String authority, Pageable pageable);
    
    /**
     * Get the "id" rolMenu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RolMenuDTO> findOne(Long id);

    /**
     * Delete the "id" rolMenu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
