package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.RetroalimentacionService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.RetroalimentacionDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Retroalimentacion}.
 */
@RestController
@RequestMapping("/api")
public class RetroalimentacionResource {

    private final Logger log = LoggerFactory.getLogger(RetroalimentacionResource.class);

    private static final String ENTITY_NAME = "retroalimentacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RetroalimentacionService retroalimentacionService;

    public RetroalimentacionResource(RetroalimentacionService retroalimentacionService) {
        this.retroalimentacionService = retroalimentacionService;
    }

    /**
     * {@code POST  /retroalimentacions} : Create a new retroalimentacion.
     *
     * @param retroalimentacionDTO the retroalimentacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new retroalimentacionDTO, or with status {@code 400 (Bad Request)} if the retroalimentacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/retroalimentacions")
    public ResponseEntity<RetroalimentacionDTO> createRetroalimentacion(@RequestBody RetroalimentacionDTO retroalimentacionDTO) throws URISyntaxException {
        log.debug("REST request to save Retroalimentacion : {}", retroalimentacionDTO);
        if (retroalimentacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new retroalimentacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RetroalimentacionDTO result = retroalimentacionService.save(retroalimentacionDTO);
        return ResponseEntity.created(new URI("/api/retroalimentacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /retroalimentacions} : Updates an existing retroalimentacion.
     *
     * @param retroalimentacionDTO the retroalimentacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated retroalimentacionDTO,
     * or with status {@code 400 (Bad Request)} if the retroalimentacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the retroalimentacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/retroalimentacions")
    public ResponseEntity<RetroalimentacionDTO> updateRetroalimentacion(@RequestBody RetroalimentacionDTO retroalimentacionDTO) throws URISyntaxException {
        log.debug("REST request to update Retroalimentacion : {}", retroalimentacionDTO);
        if (retroalimentacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RetroalimentacionDTO result = retroalimentacionService.save(retroalimentacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, retroalimentacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /retroalimentacions} : get all the retroalimentacions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of retroalimentacions in body.
     */
    @GetMapping("/retroalimentacions")
    public ResponseEntity<List<RetroalimentacionDTO>> getAllRetroalimentacions(Pageable pageable) {
        log.debug("REST request to get a page of Retroalimentacions");
        Page<RetroalimentacionDTO> page = retroalimentacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /retroalimentacions/:id} : get the "id" retroalimentacion.
     *
     * @param id the id of the retroalimentacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the retroalimentacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/retroalimentacions/{id}")
    public ResponseEntity<RetroalimentacionDTO> getRetroalimentacion(@PathVariable Long id) {
        log.debug("REST request to get Retroalimentacion : {}", id);
        Optional<RetroalimentacionDTO> retroalimentacionDTO = retroalimentacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(retroalimentacionDTO);
    }

    /**
     * {@code DELETE  /retroalimentacions/:id} : delete the "id" retroalimentacion.
     *
     * @param id the id of the retroalimentacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/retroalimentacions/{id}")
    public ResponseEntity<Void> deleteRetroalimentacion(@PathVariable Long id) {
        log.debug("REST request to delete Retroalimentacion : {}", id);
        retroalimentacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
