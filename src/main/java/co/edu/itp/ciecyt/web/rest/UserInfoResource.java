package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.UserInfoQueryService;
import co.edu.itp.ciecyt.service.UserInfoService;
import co.edu.itp.ciecyt.service.UserService;
import co.edu.itp.ciecyt.service.dto.UserDTO;
import co.edu.itp.ciecyt.service.dto.UserInfoCriteria;
import co.edu.itp.ciecyt.service.dto.UserInfoDTO;
import co.edu.itp.ciecyt.service.dto.UsuarioDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.List;
import java.util.Objects;
import java.util.Objects;
import java.util.Optional;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.UserInfo}.
 */
@RestController
@RequestMapping("/api")
public class UserInfoResource {

    private final Logger log = LoggerFactory.getLogger(UserInfoResource.class);

    private static final String ENTITY_NAME = "userInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserInfoService userInfoService;

    private final UserInfoQueryService userInfoQueryService;

    private final UserService userService;

    public UserInfoResource(UserInfoService userInfoService, UserInfoQueryService userInfoQueryService, UserService userService) {
        this.userInfoService = userInfoService;
        this.userInfoQueryService = userInfoQueryService;
        this.userService = userService;
    }

    /**
     * {@code POST  /user-infos} : Create a new usuario.
     *
     * @param userInfoDTO the usuarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new usuarioDTO, or with status {@code 400 (Bad Request)} if the usuario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-info")
    public ResponseEntity< UserInfoDTO> createUsuario(@RequestBody  UserInfoDTO userInfoDTO) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", userInfoDTO);
        if (userInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new userInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserInfoDTO result = userInfoService.save(userInfoDTO);
        return ResponseEntity.created(new URI("/api/user-info/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-info} : Updates an existing userInfo.
     *
     * @param userInfoDTO the userInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInfoDTO,
     * or with status {@code 400 (Bad Request)} if the userInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-info")
    public ResponseEntity<UserInfoDTO> updateUserInfo(@Valid @RequestBody UserInfoDTO userInfoDTO) throws URISyntaxException {
        log.debug("REST request to update UserInfo : {}", userInfoDTO);
        if (userInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        /*byte[] file = userInfoDTO.getAvatarImage();
        //Setea en nulo la url de la imagen.
        userInfoDTO.setAvatarImage(null); */
        UserInfoDTO result = userInfoService.save(userInfoDTO);

        /* if(file != null ){
            String fileName = userInfoService.attachFile(result, file, userInfoDTO.getAvatarImageContentType());
            userService.getUserWithAuthoritiesDTO(userInfoDTO.getId()).ifPresent(user->{
                user.setImageUrl(fileName);
                userService.updateUser(user);
            });


        }*/

        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-infos} : get all the userInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userInfos in body.
     */
    /*  @GetMapping("/user-info")
    public ResponseEntity<List<UserInfoDTO>> getAllUserInfos(UserInfoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserInfos by criteria: {}", criteria);
        Page<UserInfoDTO> page = userInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/
    //copie lo de UsuarioResource Funciona
    @GetMapping("/user-info")
    public ResponseEntity<List<UserInfoDTO>> getAllUsuarios(Pageable pageable) {
        log.debug("REST request to get a page of Usuarios");
        Page<UserInfoDTO> page = userInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-infos/count} : count all the userInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    /* @GetMapping("/user-info/count")
    public ResponseEntity<Long> countUserInfos(UserInfoCriteria criteria) {
        log.debug("REST request to count UserInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(userInfoQueryService.countByCriteria(criteria));
    }*/

    /**
     * {@code GET  /user-infos/:id} : get the "id" userInfo.
     *
     * @param id the id of the userInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInfoDTO, or with status {@code 404 (Not Found)}.
     */
    /* @GetMapping("/user-info/{id}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long id) {
        log.debug("REST request to get UserInfo : {}", id);
        Optional<UserInfoDTO> userInfoDTO = userInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userInfoDTO);
    }*/

    @GetMapping("/user-info/{id}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long id) {
        log.debug("REST request to get UserInfo : {}", id);
        Optional<UserInfoDTO> userInfoDTO = userInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userInfoDTO);
    }
}
