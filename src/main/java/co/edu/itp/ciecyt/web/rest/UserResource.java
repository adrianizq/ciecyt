package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.config.Constants;
import co.edu.itp.ciecyt.domain.User;
import co.edu.itp.ciecyt.repository.UserRepository;
import co.edu.itp.ciecyt.security.AuthoritiesConstants;
import co.edu.itp.ciecyt.service.MailService;
import co.edu.itp.ciecyt.service.UserInfoQueryService;
import co.edu.itp.ciecyt.service.UserInfoService;
import co.edu.itp.ciecyt.service.UserService;
import co.edu.itp.ciecyt.service.dto.UserDTO;
import co.edu.itp.ciecyt.service.dto.AdminPasswordResetDTO;
import co.edu.itp.ciecyt.service.dto.UserInfoDTO;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.web.rest.errors.EmailAlreadyUsedException;
import co.edu.itp.ciecyt.web.rest.errors.LoginAlreadyUsedException;
import co.edu.itp.ciecyt.web.rest.model.ApiMessage;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the {@link User} entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;

    private final UserRepository userRepository;

    private final MailService mailService;
    private UserInfoService userInfoService;
    //private final MessageSource messageSource;

    //public UserResource(UserService userService, UserRepository userRepository, MailService mailService , MessageSource messageSource) {
        public UserResource(UserService userService, UserRepository userRepository,  MailService mailService) {

        this.userService = userService;
        this.userRepository = userRepository;
        this.mailService = mailService;
        //this.messageSource = messageSource;
    }

    /**
     * {@code POST  /users}  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param userDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new user, or with status {@code 400 (Bad Request)} if the login or email is already in use.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException {@code 400 (Bad Request)} if the login or email is already in use.
     */
    @PostMapping("/users")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("REST request to save User : {}", userDTO);

        if (userDTO.getId() != null) {
            throw new BadRequestAlertException("A new user cannot already have an ID", "userManagement", "idexists");
            // Lowercase the user login before comparing with database
        } else if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            throw new LoginAlreadyUsedException();
        } else if (userRepository.findOneByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            mailService.sendCreationEmail(newUser);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getLogin()))
                .headers(HeaderUtil.createAlert(applicationName,  "userManagement.created", newUser.getLogin()))
                .body(newUser);
        }
    }

    /**
     * {@code PUT /users} : Updates an existing User.
     *
     * @param userDTO the user to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already in use.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already in use.
     */
    @PutMapping("/users")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to update User : {}", userDTO);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new EmailAlreadyUsedException();
        }
        existingUser = userRepository.findOneByLogin(userDTO.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(userDTO.getId()))) {
            throw new LoginAlreadyUsedException();
        }
        Optional<UserDTO> updatedUser = userService.updateUser(userDTO);

        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert(applicationName, "userManagement.updated", userDTO.getLogin()));
    }

    /**
     * {@code GET /users} : get all users.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/users_nopage")
    public ResponseEntity<?> getAllUsersNoPage() {
        final List<UserDTO> userDTOS = userService.getAllManagedUsersNoPage();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
   }

    /**
     * Gets a list of all roles.
     * @return a string list of all roles.
     */
    @GetMapping("/users/authorities")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * {@code GET /users/:login} : get the "login" user.
     *
     * @param login the login of the user to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "login" user, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }

    /**
     * {@code DELETE /users/:login} : delete the "login" User.
     *
     * @param login the login of the user to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        userService.deleteUser(login);
        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName,  "userManagement.deleted", login)).build();
    }

    /**
     * {@code POST /admin/users/{login}/reset-password} : reset a user's password
     * without knowing the current one. Allows ROLE_ADMIN to deliver temporary
     * credentials to a Decano/CIECYT/Asesor/Jurado/Estudiante at any time
     * without going through the email-based reset flow. Por defecto fuerza el
     * cambio al siguiente inicio de sesion; el cliente puede omitirlo con
     * {@code ?forceChange=false} cuando la entrega ya es definitiva.
     *
     * @param login the login of the user to reset.
     * @param dto   payload with the new cleartext password.
     * @return {@code 200 (OK)} with the updated user, or {@code 404 (Not Found)}
     *         if the login does not exist.
     */
    @PostMapping("/admin/users/{login:" + Constants.LOGIN_REGEX + "}/reset-password")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<UserDTO> adminResetPassword(
        @PathVariable String login,
        @RequestParam(name = "forceChange", defaultValue = "true") boolean forceChange,
        @Valid @RequestBody AdminPasswordResetDTO dto
    ) {
        log.debug("REST request to admin-reset password for User: {}", login);
        Optional<UserDTO> updated = userService.adminResetPassword(login, dto.getNewPassword(), forceChange);
        return ResponseUtil.wrapOrNotFound(
            updated,
            HeaderUtil.createAlert(applicationName, "userManagement.resetPassword", login)
        );
    }


    @GetMapping("/users/asesores")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.CIECYT + "\", \"" + AuthoritiesConstants.ADMIN + "\")")
        public ResponseEntity<?> getAllUsersAsesoresNoPage() {
        //Optional<User> user = userService.getUserWithAuthorities();
        //Locale locale = Locale.forLanguageTag(user.get().getLangKey());
        try{
       // final List<UserDTO> listAsesores = userService.getAllAsesoresNoPage();
            final List<UserDTO> listAsesores = userService.getAllAsesoresNoPage();

        return new ResponseEntity<>(listAsesores, HttpStatus.OK);

        }catch (Exception e){
          //  String det = "";
		//	String message = "api.users.search.error"; //TODO ESTE SE DEBE CONSULTAR DE LOS MESSAGES DEL SISTEMA

		//	String error = messageSource.getMessage(message, new String[] {det, e.getMessage()}, locale);

		//	log.error(error);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
			//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ApiMessage("ERR_99", error));
        }
    }

    @GetMapping("/users/estudiantes")
    // El lista de estudiantes (para conformar el grupo en una propuesta) permanece abierto a
    // usuarios autenticados: el proponente es un estudiante que arma su equipo. Los directorios
    // sensibles (asesores/jurados, que alimentan designaciones segun el Acuerdo 25) si estan
    // restringidos a CIECYT/ADMIN.
    public ResponseEntity<?> getAllUsersEstudiantesNoPage() {
        //Optional<User> user = userService.getUserWithAuthorities();
        //Locale locale = Locale.forLanguageTag(user.get().getLangKey());
        try{
        final List<UserDTO> list = userService.getAllEstudiantesNoPage();
        return new ResponseEntity<>(list, HttpStatus.OK);
        }catch (Exception e){
          //  String det = "";
		//	String message = "api.users.search.error"; //TODO ESTE SE DEBE CONSULTAR DE LOS MESSAGES DEL SISTEMA
		//	String error = messageSource.getMessage(message, new String[] {det, e.getMessage()}, locale);
		//	log.error(error);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
			//return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ApiMessage("ERR_99", error));
        }
    }


    @GetMapping("/users/jurados")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.CIECYT + "\", \"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<?> getAllUsersJuradosNoPage() {
        //Optional<User> user = userService.getUserWithAuthorities();
        //Locale locale = Locale.forLanguageTag(user.get().getLangKey());
        try{
            final List<UserDTO> list = userService.getAllJuradosNoPage();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch (Exception e){
            //  String det = "";
            //	String message = "api.users.search.error"; //TODO ESTE SE DEBE CONSULTAR DE LOS MESSAGES DEL SISTEMA
            //	String error = messageSource.getMessage(message, new String[] {det, e.getMessage()}, locale);
            //	log.error(error);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ApiMessage("ERR_99", error));
        }
    }




}
