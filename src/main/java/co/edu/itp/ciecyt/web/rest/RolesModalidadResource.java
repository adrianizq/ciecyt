package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.RolesModalidadService;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.RolesModalidadDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.RolesModalidad}.
 */
@RestController
@RequestMapping("/api")
public class RolesModalidadResource {

    private final Logger log = LoggerFactory.getLogger(RolesModalidadResource.class);

    private static final String ENTITY_NAME = "rolesModalidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RolesModalidadService rolesModalidadService;

    public RolesModalidadResource(RolesModalidadService rolesModalidadService) {
        this.rolesModalidadService = rolesModalidadService;
    }

    /**
     * {@code POST  /roles-modalidads} : Create a new rolesModalidad.
     *
     * @param rolesModalidadDTO the rolesModalidadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rolesModalidadDTO, or with status {@code 400 (Bad Request)} if the rolesModalidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/roles-modalidads")
    public ResponseEntity<RolesModalidadDTO> createRolesModalidad(@RequestBody RolesModalidadDTO rolesModalidadDTO) throws URISyntaxException {
        log.debug("REST request to save RolesModalidad : {}", rolesModalidadDTO);
        if (rolesModalidadDTO.getId() != null) {
            throw new BadRequestAlertException("A new rolesModalidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RolesModalidadDTO result = rolesModalidadService.save(rolesModalidadDTO);
        return ResponseEntity.created(new URI("/api/roles-modalidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /roles-modalidads} : Updates an existing rolesModalidad.
     *
     * @param rolesModalidadDTO the rolesModalidadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rolesModalidadDTO,
     * or with status {@code 400 (Bad Request)} if the rolesModalidadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rolesModalidadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/roles-modalidads")
    public ResponseEntity<RolesModalidadDTO> updateRolesModalidad(@RequestBody RolesModalidadDTO rolesModalidadDTO) throws URISyntaxException {
        log.debug("REST request to update RolesModalidad : {}", rolesModalidadDTO);
        if (rolesModalidadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RolesModalidadDTO result = rolesModalidadService.save(rolesModalidadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rolesModalidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /roles-modalidads} : get all the rolesModalidads.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rolesModalidads in body.
     */
    @GetMapping("/roles-modalidads")
    public ResponseEntity<List<RolesModalidadDTO>> getAllRolesModalidads(Pageable pageable) {
        log.debug("REST request to get a page of RolesModalidads");
        Page<RolesModalidadDTO> page = rolesModalidadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /roles-modalidads/:id} : get the "id" rolesModalidad.
     *
     * @param id the id of the rolesModalidadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rolesModalidadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/roles-modalidads/{id}")
    public ResponseEntity<RolesModalidadDTO> getRolesModalidad(@PathVariable Long id) {
        log.debug("REST request to get RolesModalidad : {}", id);
        Optional<RolesModalidadDTO> rolesModalidadDTO = rolesModalidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rolesModalidadDTO);
    }

    /**
     * {@code DELETE  /roles-modalidads/:id} : delete the "id" rolesModalidad.
     *
     * @param id the id of the rolesModalidadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/roles-modalidads/{id}")
    public ResponseEntity<Void> deleteRolesModalidad(@PathVariable Long id) {
        log.debug("REST request to delete RolesModalidad : {}", id);
        rolesModalidadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    //ADR
    @GetMapping("/roles-modalidad-rol-modalidad/{rol}/{idModalidad}")
    public ResponseEntity<?> findByRolAndRolesModalidadModalidadId(@PathVariable String rol, @PathVariable Long idModalidad) {
        log.debug("REST request to get RolesModalidad : {}", rol);
        try {
            final RolesModalidadDTO dto = rolesModalidadService.findByRolAndRolesModalidadModalidadId(rol,idModalidad);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/roles-modalidad-authority/{authority}")
    public ResponseEntity<?> findByRolesModalidadAuthorityName(@PathVariable String authority) {
        log.debug("REST request to get RolesModalidad : {}", authority);
        try{
        final List<RolesModalidadDTO> dto = rolesModalidadService.findByRolesModalidadAuthorityNameDTO(authority);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }


}
