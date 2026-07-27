package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.RolMenuService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.RolMenuDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.RolMenu}.
 */
@RestController
@RequestMapping("/api")
public class RolMenuResource {

    private final Logger log = LoggerFactory.getLogger(RolMenuResource.class);

    private static final String ENTITY_NAME = "rolMenu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RolMenuService rolMenuService;

    public RolMenuResource(RolMenuService rolMenuService) {
        this.rolMenuService = rolMenuService;
    }

    /**
     * {@code POST  /rol-menus} : Create a new rolMenu.
     *
     * @param rolMenuDTO the rolMenuDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rolMenuDTO, or with status {@code 400 (Bad Request)} if the rolMenu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rol-menus")
    public ResponseEntity<RolMenuDTO> createRolMenu(@RequestBody RolMenuDTO rolMenuDTO) throws URISyntaxException {
        log.debug("REST request to save RolMenu : {}", rolMenuDTO);
        if (rolMenuDTO.getId() != null) {
            throw new BadRequestAlertException("A new rolMenu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RolMenuDTO result = rolMenuService.save(rolMenuDTO);
        return ResponseEntity.created(new URI("/api/rol-menus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rol-menus} : Updates an existing rolMenu.
     *
     * @param rolMenuDTO the rolMenuDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolMenuDTO,
     * or with status {@code 400 (Bad Request)} if the rolMenuDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rolMenuDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rol-menus")
    public ResponseEntity<RolMenuDTO> updateRolMenu(@RequestBody RolMenuDTO rolMenuDTO) throws URISyntaxException {
        log.debug("REST request to update RolMenu : {}", rolMenuDTO);
        if (rolMenuDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RolMenuDTO result = rolMenuService.save(rolMenuDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rolMenuDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rol-menus} : get all the rolMenus.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rolMenus in body.
     */
    @GetMapping("/rol-menus")
    public ResponseEntity<List<RolMenuDTO>> getAllRolMenus(Pageable pageable) {
        log.debug("REST request to get a page of RolMenus");
        Page<RolMenuDTO> page = rolMenuService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rol-menus/:id} : get the "id" rolMenu.
     *
     * @param id the id of the rolMenuDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rolMenuDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rol-menus/{id}")
    public ResponseEntity<RolMenuDTO> getRolMenu(@PathVariable Long id) {
        log.debug("REST request to get RolMenu : {}", id);
        Optional<RolMenuDTO> rolMenuDTO = rolMenuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rolMenuDTO);
    }

    /**
     * {@code DELETE  /rol-menus/:id} : delete the "id" rolMenu.
     *
     * @param id the id of the rolMenuDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rol-menus/{id}")
    public ResponseEntity<Void> deleteRolMenu(@PathVariable Long id) {
        log.debug("REST request to delete RolMenu : {}", id);
        rolMenuService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    
    /**ADR
     * {@code GET  /rol-menus_authority} : get all the rolMenus.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rolMenus in body.
     */
    @GetMapping("/rol-menus-authority/{authority}")
    public ResponseEntity<List<RolMenuDTO>> getAllRolMenusAuthority(String auth, Pageable pageable) {
        log.debug("REST request to get a page of RolMenus");
        //Page<RolMenuDTO> page = rolMenuService.findAll(pageable);
        //Page<RolMenuDTO> page = rolMenuService.findAllByAuthority(auth, pageable);
        Page<RolMenuDTO> page = rolMenuService.buscarAllByAuthority(auth, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
