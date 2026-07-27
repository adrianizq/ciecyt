package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.AcuerdoService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.AcuerdoDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Acuerdo}.
 */
@RestController
@RequestMapping("/api")
public class AcuerdoResource {

    private final Logger log = LoggerFactory.getLogger(AcuerdoResource.class);

    private static final String ENTITY_NAME = "acuerdo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AcuerdoService acuerdoService;

    public AcuerdoResource(AcuerdoService acuerdoService) {
        this.acuerdoService = acuerdoService;
    }

    /**
     * {@code POST  /acuerdos} : Create a new acuerdo.
     *
     * @param acuerdoDTO the acuerdoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new acuerdoDTO, or with status {@code 400 (Bad Request)} if the acuerdo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/acuerdos")
    public ResponseEntity<AcuerdoDTO> createAcuerdo(@RequestBody AcuerdoDTO acuerdoDTO) throws URISyntaxException {
        log.debug("REST request to save Acuerdo : {}", acuerdoDTO);
        if (acuerdoDTO.getId() != null) {
            throw new BadRequestAlertException("A new acuerdo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AcuerdoDTO result = acuerdoService.save(acuerdoDTO);
        return ResponseEntity.created(new URI("/api/acuerdos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /acuerdos} : Updates an existing acuerdo.
     *
     * @param acuerdoDTO the acuerdoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated acuerdoDTO,
     * or with status {@code 400 (Bad Request)} if the acuerdoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the acuerdoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/acuerdos")
    public ResponseEntity<AcuerdoDTO> updateAcuerdo(@RequestBody AcuerdoDTO acuerdoDTO) throws URISyntaxException {
        log.debug("REST request to update Acuerdo : {}", acuerdoDTO);
        if (acuerdoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AcuerdoDTO result = acuerdoService.save(acuerdoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, acuerdoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /acuerdos} : get all the acuerdos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of acuerdos in body.
     */
    @GetMapping("/acuerdos")
    public ResponseEntity<List<AcuerdoDTO>> getAllAcuerdos(Pageable pageable) {
        log.debug("REST request to get a page of Acuerdos");
        Page<AcuerdoDTO> page = acuerdoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /acuerdos/:id} : get the "id" acuerdo.
     *
     * @param id the id of the acuerdoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the acuerdoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/acuerdos/{id}")
    public ResponseEntity<AcuerdoDTO> getAcuerdo(@PathVariable Long id) {
        log.debug("REST request to get Acuerdo : {}", id);
        Optional<AcuerdoDTO> acuerdoDTO = acuerdoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(acuerdoDTO);
    }

    /**
     * {@code DELETE  /acuerdos/:id} : delete the "id" acuerdo.
     *
     * @param id the id of the acuerdoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/acuerdos/{id}")
    public ResponseEntity<Void> deleteAcuerdo(@PathVariable Long id) {
        log.debug("REST request to delete Acuerdo : {}", id);
        acuerdoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
