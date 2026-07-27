package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.CronogramaCiecytFasesService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.CronogramaCiecytFasesDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.CronogramaCiecytFases}.
 */
@RestController
@RequestMapping("/api")
public class CronogramaCiecytFasesResource {

    private final Logger log = LoggerFactory.getLogger(CronogramaCiecytFasesResource.class);

    private static final String ENTITY_NAME = "cronogramaCiecytFases";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CronogramaCiecytFasesService cronogramaCiecytFasesService;

    public CronogramaCiecytFasesResource(CronogramaCiecytFasesService cronogramaCiecytFasesService) {
        this.cronogramaCiecytFasesService = cronogramaCiecytFasesService;
    }

    /**
     * {@code POST  /cronograma-ciecyt-fases} : Create a new cronogramaCiecytFases.
     *
     * @param cronogramaCiecytFasesDTO the cronogramaCiecytFasesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cronogramaCiecytFasesDTO, or with status {@code 400 (Bad Request)} if the cronogramaCiecytFases has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cronograma-ciecyt-fases")
    public ResponseEntity<CronogramaCiecytFasesDTO> createCronogramaCiecytFases(@RequestBody CronogramaCiecytFasesDTO cronogramaCiecytFasesDTO) throws URISyntaxException {
        log.debug("REST request to save CronogramaCiecytFases : {}", cronogramaCiecytFasesDTO);
        if (cronogramaCiecytFasesDTO.getId() != null) {
            throw new BadRequestAlertException("A new cronogramaCiecytFases cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CronogramaCiecytFasesDTO result = cronogramaCiecytFasesService.save(cronogramaCiecytFasesDTO);
        return ResponseEntity.created(new URI("/api/cronograma-ciecyt-fases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cronograma-ciecyt-fases} : Updates an existing cronogramaCiecytFases.
     *
     * @param cronogramaCiecytFasesDTO the cronogramaCiecytFasesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cronogramaCiecytFasesDTO,
     * or with status {@code 400 (Bad Request)} if the cronogramaCiecytFasesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cronogramaCiecytFasesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cronograma-ciecyt-fases")
    public ResponseEntity<CronogramaCiecytFasesDTO> updateCronogramaCiecytFases(@RequestBody CronogramaCiecytFasesDTO cronogramaCiecytFasesDTO) throws URISyntaxException {
        log.debug("REST request to update CronogramaCiecytFases : {}", cronogramaCiecytFasesDTO);
        if (cronogramaCiecytFasesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CronogramaCiecytFasesDTO result = cronogramaCiecytFasesService.save(cronogramaCiecytFasesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cronogramaCiecytFasesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cronograma-ciecyt-fases} : get all the cronogramaCiecytFases.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cronogramaCiecytFases in body.
     */
    @GetMapping("/cronograma-ciecyt-fases")
    public ResponseEntity<List<CronogramaCiecytFasesDTO>> getAllCronogramaCiecytFases(Pageable pageable) {
        log.debug("REST request to get a page of CronogramaCiecytFases");
        Page<CronogramaCiecytFasesDTO> page = cronogramaCiecytFasesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cronograma-ciecyt-fases/:id} : get the "id" cronogramaCiecytFases.
     *
     * @param id the id of the cronogramaCiecytFasesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cronogramaCiecytFasesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cronograma-ciecyt-fases/{id}")
    public ResponseEntity<CronogramaCiecytFasesDTO> getCronogramaCiecytFases(@PathVariable Long id) {
        log.debug("REST request to get CronogramaCiecytFases : {}", id);
        Optional<CronogramaCiecytFasesDTO> cronogramaCiecytFasesDTO = cronogramaCiecytFasesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cronogramaCiecytFasesDTO);
    }

    /**
     * {@code DELETE  /cronograma-ciecyt-fases/:id} : delete the "id" cronogramaCiecytFases.
     *
     * @param id the id of the cronogramaCiecytFasesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cronograma-ciecyt-fases/{id}")
    public ResponseEntity<Void> deleteCronogramaCiecytFases(@PathVariable Long id) {
        log.debug("REST request to delete CronogramaCiecytFases : {}", id);
        cronogramaCiecytFasesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
