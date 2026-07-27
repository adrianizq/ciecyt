package co.edu.itp.ciecyt.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.edu.itp.ciecyt.domain.Menu;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.service.MenuService;
import co.edu.itp.ciecyt.service.UserService;
import co.edu.itp.ciecyt.service.dto.MenuDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.web.rest.model.ApiMessage;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Menu}.
 */
@RestController
@RequestMapping("/api")
public class MenuResource {

	private final Logger log = LoggerFactory.getLogger(MenuResource.class);

	private static final String ENTITY_NAME = "menu";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final MenuService menuService;

	private final MessageSource messageSource;

	private final UserService userService;



	public MenuResource(MenuService menuService, MessageSource messageSource, UserService userService) {
		this.menuService = menuService;
		this.messageSource = messageSource;
		this.userService = userService;

	}

	/**
	 * {@code POST  /menus} : Create a new menu.
	 *
	 * @param menuDTO the menuDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new menuDTO, or with status {@code 400 (Bad Request)} if the menu has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/menus")
	public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) throws URISyntaxException {
		log.debug("REST request to save Menu : {}", menuDTO);
		if (menuDTO.getId() != null) {
			throw new BadRequestAlertException("A new menu cannot already have an ID", ENTITY_NAME, "idexists");
		}
		MenuDTO result = menuService.save(menuDTO);
		return ResponseEntity.created(new URI("/api/menus/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /menus} : Updates an existing menu.
	 *
	 * @param menuDTO the menuDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated menuDTO,
	 * or with status {@code 400 (Bad Request)} if the menuDTO is not valid,
	 * or with status {@code 500 (Internal Server Error)} if the menuDTO couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/menus")
	public ResponseEntity<MenuDTO> updateMenu(@RequestBody MenuDTO menuDTO) throws URISyntaxException {
		log.debug("REST request to update Menu : {}", menuDTO);
		if (menuDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		MenuDTO result = menuService.save(menuDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, menuDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /menus} : get all the menus.
	 *

	 * @param pageable the pagination information.

	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menus in body.
	 */
	@GetMapping("/menus")
	public ResponseEntity<List<MenuDTO>> getAllMenus(Pageable pageable) {
		log.debug("REST request to get a page of Menus");
		Page<MenuDTO> page = menuService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

    @GetMapping("/menus-no-page")
    public  ResponseEntity<?>  getAllMenusNoPage() throws Exception {
        log.debug("REST request to get a page of Menus");


        try {
            List<MenuDTO> menuDTOS = menuService.findAllOrderByOrden();

            ResponseEntity<MenuDTO> responseEntity = new ResponseEntity(menuDTOS, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/menus-rol/{rol}")
    public  ResponseEntity<?>  getMenusByRolNoPage(@PathVariable String rol) throws Exception {
        log.debug("REST request to get a page of Menus");


        try {
           List<MenuDTO> menuDTOS = menuService.findByRolOrderByOrden(rol);

            ResponseEntity<MenuDTO> responseEntity = new ResponseEntity(menuDTOS, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    //se trabaja con el rol como string pero enviando varios valores separados por espacios
    //para los diferentes roles
    @GetMapping("/menus-roles/{rol}")
    public  ResponseEntity<?>  getMenusRoles(@PathVariable String rol) throws Exception {
        log.debug("REST request to get a page of Menus");


        try {
            List<MenuDTO> menuDTOS = menuService.findByRoles(rol);

            ResponseEntity<MenuDTO> responseEntity = new ResponseEntity(menuDTOS, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }




	/**
	 * {@code GET  /menus/:id} : get the "id" menu.
	 *
	 * @param id the id of the menuDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the menuDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/menus/{id}")
	public ResponseEntity<MenuDTO> getMenu(@PathVariable Long id) {
		log.debug("REST request to get Menu : {}", id);
		Optional<MenuDTO> menuDTO = menuService.findOne(id);
		return ResponseUtil.wrapOrNotFound(menuDTO);
	}

	/**
	 * {@code DELETE  /menus/:id} : delete the "id" menu.
	 *
	 * @param id the id of the menuDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/menus/{id}")
	public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
		log.debug("REST request to delete Menu : {}", id);
		menuService.delete(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
	}

	/**ADR
	 * {@code GET  /menus-padre} : get all the parent menu.
	 *

	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menu in body.
	 */
	@GetMapping("/menus-padre")
	public ResponseEntity<?> getAllMenuFather() {
		log.debug("REST request to get all Empleados");

		Optional<User> user = userService.getUserWithAuthorities();
		Locale locale = Locale.forLanguageTag(user.get().getLangKey());

		try{
			List<Menu> list = menuService.findAllFathers();
			//return menuService.findAllFathers();
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		catch(Exception e){

			String det = "";
			String message = "api.menus.search.error"; //TODO ESTE SE DEBE CONSULTAR DE LOS MESSAGES DEL SISTEMA

			String error = messageSource.getMessage(message, new String[] {det, e.getMessage()}, locale);

			log.error(error);

			//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ApiMessage("ERR_99", error));
		}
	}

	//JLT
	/**
	 * {@code GET  /menus-all} : get all the menu system tree.
	 *

	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menus in body.
	 */
	@GetMapping("/menus-all")
	public ResponseEntity<?> getAllMenusSystem() {
		log.debug("REST request to get Menu All System");

		Optional<User> user = userService.getUserWithAuthorities();
		Locale locale = Locale.forLanguageTag(user.get().getLangKey());

		try {
			List<MenuDTO> list = menuService.getAllMenuSystem();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			String det = "";
			String message = "api.menus.search.error"; //TODO ESTE SE DEBE CONSULTAR DE LOS MESSAGES DEL SISTEMA

			String error = messageSource.getMessage(message, new String[] {det, e.getMessage()}, locale);

			log.error(error);

			//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ApiMessage("ERR_99", error));


		}
	}



}
