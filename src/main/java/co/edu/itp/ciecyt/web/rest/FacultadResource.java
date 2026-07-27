package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.FacultadService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.FacultadDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Facultad}.
 */
@RestController
@RequestMapping("/api")
public class FacultadResource {

    private final Logger log = LoggerFactory.getLogger(FacultadResource.class);

    private static final String ENTITY_NAME = "facultad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FacultadService facultadService;

    public FacultadResource(FacultadService facultadService) {
        this.facultadService = facultadService;
    }

    /**
     * {@code POST  /facultads} : Create a new facultad.
     *
     * @param facultadDTO the facultadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new facultadDTO, or with status {@code 400 (Bad Request)} if the facultad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/facultads")
    public ResponseEntity<FacultadDTO> createFacultad(@RequestBody FacultadDTO facultadDTO) throws URISyntaxException {
        log.debug("REST request to save Facultad : {}", facultadDTO);
        if (facultadDTO.getId() != null) {
            throw new BadRequestAlertException("A new facultad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FacultadDTO result = facultadService.save(facultadDTO);
        return ResponseEntity.created(new URI("/api/facultads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /facultads} : Updates an existing facultad.
     *
     * @param facultadDTO the facultadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated facultadDTO,
     * or with status {@code 400 (Bad Request)} if the facultadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the facultadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/facultads")
    public ResponseEntity<FacultadDTO> updateFacultad(@RequestBody FacultadDTO facultadDTO) throws URISyntaxException {
        log.debug("REST request to update Facultad : {}", facultadDTO);
        if (facultadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FacultadDTO result = facultadService.save(facultadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, facultadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /facultads} : get all the facultads.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of facultads in body.
     */
    @GetMapping("/facultads")
    public ResponseEntity<List<FacultadDTO>> getAllFacultads(Pageable pageable) {
        log.debug("REST request to get a page of Facultads");
        Page<FacultadDTO> page = facultadService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /facultads/:id} : get the "id" facultad.
     *
     * @param id the id of the facultadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the facultadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/facultads/{id}")
    public ResponseEntity<FacultadDTO> getFacultad(@PathVariable Long id) {
        log.debug("REST request to get Facultad : {}", id);
        Optional<FacultadDTO> facultadDTO = facultadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(facultadDTO);
    }

    /**
     * {@code DELETE  /facultads/:id} : delete the "id" facultad.
     *
     * @param id the id of the facultadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/facultads/{id}")
    public ResponseEntity<Void> deleteFacultad(@PathVariable Long id) {
        log.debug("REST request to delete Facultad : {}", id);
        facultadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
