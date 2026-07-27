package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.CategorizacionService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.CategorizacionDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Categorizacion}.
 */
@RestController
@RequestMapping("/api")
public class CategorizacionResource {

    private final Logger log = LoggerFactory.getLogger(CategorizacionResource.class);

    private static final String ENTITY_NAME = "categorizacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorizacionService categorizacionService;

    public CategorizacionResource(CategorizacionService categorizacionService) {
        this.categorizacionService = categorizacionService;
    }

    /**
     * {@code POST  /categorizacions} : Create a new categorizacion.
     *
     * @param categorizacionDTO the categorizacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorizacionDTO, or with status {@code 400 (Bad Request)} if the categorizacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorizacions")
    public ResponseEntity<CategorizacionDTO> createCategorizacion(@RequestBody CategorizacionDTO categorizacionDTO) throws URISyntaxException {
        log.debug("REST request to save Categorizacion : {}", categorizacionDTO);
        if (categorizacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorizacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorizacionDTO result = categorizacionService.save(categorizacionDTO);
        return ResponseEntity.created(new URI("/api/categorizacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorizacions} : Updates an existing categorizacion.
     *
     * @param categorizacionDTO the categorizacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorizacionDTO,
     * or with status {@code 400 (Bad Request)} if the categorizacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorizacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorizacions")
    public ResponseEntity<CategorizacionDTO> updateCategorizacion(@RequestBody CategorizacionDTO categorizacionDTO) throws URISyntaxException {
        log.debug("REST request to update Categorizacion : {}", categorizacionDTO);
        if (categorizacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategorizacionDTO result = categorizacionService.save(categorizacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorizacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categorizacions} : get all the categorizacions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorizacions in body.
     */
    @GetMapping("/categorizacions")
    public ResponseEntity<List<CategorizacionDTO>> getAllCategorizacions(Pageable pageable) {
        log.debug("REST request to get a page of Categorizacions");
        Page<CategorizacionDTO> page = categorizacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorizacions/:id} : get the "id" categorizacion.
     *
     * @param id the id of the categorizacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorizacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorizacions/{id}")
    public ResponseEntity<CategorizacionDTO> getCategorizacion(@PathVariable Long id) {
        log.debug("REST request to get Categorizacion : {}", id);
        Optional<CategorizacionDTO> categorizacionDTO = categorizacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorizacionDTO);
    }

    /**
     * {@code DELETE  /categorizacions/:id} : delete the "id" categorizacion.
     *
     * @param id the id of the categorizacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorizacions/{id}")
    public ResponseEntity<Void> deleteCategorizacion(@PathVariable Long id) {
        log.debug("REST request to delete Categorizacion : {}", id);
        categorizacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
