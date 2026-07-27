package co.edu.itp.ciecyt.web.rest;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.service.MenuService;

import co.edu.itp.ciecyt.service.UserService;
import co.edu.itp.ciecyt.service.dto.MenuDTO;
import co.edu.itp.ciecyt.web.rest.model.ApiMessage;
import io.github.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Menu}.
 */
@RestController
@RequestMapping("/api")
public class MenuUserResource {

	private final Logger log = LoggerFactory.getLogger(MenuResource.class);

	private static final String ENTITY_NAME = "menu";
	private final UserService userService;

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final MessageSource messageSource;

	private final MenuService menuService; //agregada ADR

	public MenuUserResource(MenuService menuService,  UserService userService, MessageSource messageSource) {

		this.menuService = menuService;
		this.userService = userService;
		this.messageSource = messageSource;
	}



	//ADR
	/**
	 * {@code GET  /menus} : get all the menus.
	 *

	 * @param pageable the pagination information.

	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menus in body.
	 */
	@GetMapping("/menus-all-user")
	//public ResponseEntity<List<MenuDTO>> getAllMenusUser(@PathVariable Long id, Pageable pageable) {
	public ResponseEntity<?> getAllMenusUser(Pageable pageable) {
		log.debug("REST request to get a page of Menus of User");
		
		Optional<User> user = userService.getUserWithAuthorities();
		Locale locale = Locale.forLanguageTag(user.get().getLangKey());

		
		try{
			
			//Page<MenuDTO> page = menuService.findAll(pageable);
			Page <MenuDTO>  page = menuService.buscarAllByUser(user.get().getId(), pageable);
			HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
			return ResponseEntity.ok().headers(headers).body(page.getContent());
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


	//ADR
	/**
	 * {@code GET  /menus} : get all the menus.
	 *

	 * @param pageable the pagination information.

	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of menus in body.
	 */


	@GetMapping("/menus-all-user-no-page")
	//public ResponseEntity<List<MenuDTO>> getAllMenusUser(@PathVariable Long id, Pageable pageable) {
	public ResponseEntity<?> getAllMenusUserNoPage() {
		log.debug("REST request to get a page of Menus of User");
		
		Optional<User> user = userService.getUserWithAuthorities();
		Locale locale = Locale.forLanguageTag(user.get().getLangKey());
		try {
			List<MenuDTO> list = menuService.buscarAllByUserNoPage(user.get().getId());
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
