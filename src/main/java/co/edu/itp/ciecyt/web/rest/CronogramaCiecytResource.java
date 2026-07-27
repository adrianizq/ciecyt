package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.CronogramaCiecytService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.CronogramaCiecytDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.CronogramaCiecyt}.
 */
@RestController
@RequestMapping("/api")
public class CronogramaCiecytResource {

    private final Logger log = LoggerFactory.getLogger(CronogramaCiecytResource.class);

    private static final String ENTITY_NAME = "cronogramaCiecyt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CronogramaCiecytService cronogramaCiecytService;

    public CronogramaCiecytResource(CronogramaCiecytService cronogramaCiecytService) {
        this.cronogramaCiecytService = cronogramaCiecytService;
    }

    /**
     * {@code POST  /cronograma-ciecyts} : Create a new cronogramaCiecyt.
     *
     * @param cronogramaCiecytDTO the cronogramaCiecytDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cronogramaCiecytDTO, or with status {@code 400 (Bad Request)} if the cronogramaCiecyt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cronograma-ciecyts")
    public ResponseEntity<CronogramaCiecytDTO> createCronogramaCiecyt(@RequestBody CronogramaCiecytDTO cronogramaCiecytDTO) throws URISyntaxException {
        log.debug("REST request to save CronogramaCiecyt : {}", cronogramaCiecytDTO);
        if (cronogramaCiecytDTO.getId() != null) {
            throw new BadRequestAlertException("A new cronogramaCiecyt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CronogramaCiecytDTO result = cronogramaCiecytService.save(cronogramaCiecytDTO);
        return ResponseEntity.created(new URI("/api/cronograma-ciecyts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cronograma-ciecyts} : Updates an existing cronogramaCiecyt.
     *
     * @param cronogramaCiecytDTO the cronogramaCiecytDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cronogramaCiecytDTO,
     * or with status {@code 400 (Bad Request)} if the cronogramaCiecytDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cronogramaCiecytDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cronograma-ciecyts")
    public ResponseEntity<CronogramaCiecytDTO> updateCronogramaCiecyt(@RequestBody CronogramaCiecytDTO cronogramaCiecytDTO) throws URISyntaxException {
        log.debug("REST request to update CronogramaCiecyt : {}", cronogramaCiecytDTO);
        if (cronogramaCiecytDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CronogramaCiecytDTO result = cronogramaCiecytService.save(cronogramaCiecytDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cronogramaCiecytDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cronograma-ciecyts} : get all the cronogramaCiecyts.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cronogramaCiecyts in body.
     */
    @GetMapping("/cronograma-ciecyts")
    public ResponseEntity<List<CronogramaCiecytDTO>> getAllCronogramaCiecyts(Pageable pageable) {
        log.debug("REST request to get a page of CronogramaCiecyts");
        Page<CronogramaCiecytDTO> page = cronogramaCiecytService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cronograma-ciecyts/:id} : get the "id" cronogramaCiecyt.
     *
     * @param id the id of the cronogramaCiecytDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cronogramaCiecytDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cronograma-ciecyts/{id}")
    public ResponseEntity<CronogramaCiecytDTO> getCronogramaCiecyt(@PathVariable Long id) {
        log.debug("REST request to get CronogramaCiecyt : {}", id);
        Optional<CronogramaCiecytDTO> cronogramaCiecytDTO = cronogramaCiecytService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cronogramaCiecytDTO);
    }

    /**
     * {@code DELETE  /cronograma-ciecyts/:id} : delete the "id" cronogramaCiecyt.
     *
     * @param id the id of the cronogramaCiecytDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cronograma-ciecyts/{id}")
    public ResponseEntity<Void> deleteCronogramaCiecyt(@PathVariable Long id) {
        log.debug("REST request to delete CronogramaCiecyt : {}", id);
        cronogramaCiecytService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
