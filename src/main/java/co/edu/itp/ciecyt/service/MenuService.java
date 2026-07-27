package co.edu.itp.ciecyt.service;

import co.edu.itp.ciecyt.domain.Menu;
import co.edu.itp.ciecyt.service.dto.MenuDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link co.edu.itp.ciecyt.domain.Menu}.
 */
public interface MenuService {

    /**
     * Save a menu.
     *
     * @param menuDTO the entity to save.
     * @return the persisted entity.
     */
    MenuDTO save(MenuDTO menuDTO);

    /**
     * Get all the menus.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MenuDTO> findAll(Pageable pageable);


    //////// traer de un string varios roles para ser buscados en los menus
    @Transactional(readOnly = true)
    List<MenuDTO> findByRoles(String rol) throws Exception;

    /**
     * Get the "id" menu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MenuDTO> findOne(Long id);

    /**
     * Delete the "id" menu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);


    List<Menu> findAllFathers() throws Exception;

    Page<MenuDTO> buscarAllByUser(Long userId, Pageable pageable) throws Exception;

	List<MenuDTO> buscarAllByUserNoPage(Long userId) throws Exception;

	List<MenuDTO> getAllMenuSystem() throws Exception;

	List <MenuDTO> findAllOrderByOrden() throws Exception;

    List <MenuDTO> findByRolOrderByOrden(String rol) throws Exception;
}
