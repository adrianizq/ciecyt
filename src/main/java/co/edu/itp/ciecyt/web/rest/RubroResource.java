package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.RubroService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.RubroDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Rubro}.
 */
@RestController
@RequestMapping("/api")
public class RubroResource {

    private final Logger log = LoggerFactory.getLogger(RubroResource.class);

    private static final String ENTITY_NAME = "rubro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RubroService rubroService;

    public RubroResource(RubroService rubroService) {
        this.rubroService = rubroService;
    }

    /**
     * {@code POST  /rubros} : Create a new rubro.
     *
     * @param rubroDTO the rubroDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rubroDTO, or with status {@code 400 (Bad Request)} if the rubro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rubros")
    public ResponseEntity<RubroDTO> createRubro(@RequestBody RubroDTO rubroDTO) throws URISyntaxException {
        log.debug("REST request to save Rubro : {}", rubroDTO);
        if (rubroDTO.getId() != null) {
            throw new BadRequestAlertException("A new rubro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RubroDTO result = rubroService.save(rubroDTO);
        return ResponseEntity.created(new URI("/api/rubros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rubros} : Updates an existing rubro.
     *
     * @param rubroDTO the rubroDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rubroDTO,
     * or with status {@code 400 (Bad Request)} if the rubroDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rubroDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rubros")
    public ResponseEntity<RubroDTO> updateRubro(@RequestBody RubroDTO rubroDTO) throws URISyntaxException {
        log.debug("REST request to update Rubro : {}", rubroDTO);
        if (rubroDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RubroDTO result = rubroService.save(rubroDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rubroDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rubros} : get all the rubros.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rubros in body.
     */
    @GetMapping("/rubros")
    public ResponseEntity<List<RubroDTO>> getAllRubros(Pageable pageable) {
        log.debug("REST request to get a page of Rubros");
        Page<RubroDTO> page = rubroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rubros/:id} : get the "id" rubro.
     *
     * @param id the id of the rubroDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rubroDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rubros/{id}")
    public ResponseEntity<RubroDTO> getRubro(@PathVariable Long id) {
        log.debug("REST request to get Rubro : {}", id);
        Optional<RubroDTO> rubroDTO = rubroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rubroDTO);
    }

    /**
     * {@code DELETE  /rubros/:id} : delete the "id" rubro.
     *
     * @param id the id of the rubroDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rubros/{id}")
    public ResponseEntity<Void> deleteRubro(@PathVariable Long id) {
        log.debug("REST request to delete Rubro : {}", id);
        rubroService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
