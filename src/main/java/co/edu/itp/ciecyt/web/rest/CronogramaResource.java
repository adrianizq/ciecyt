package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.CronogramaService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.CronogramaDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Cronograma}.
 */
@RestController
@RequestMapping("/api")
public class CronogramaResource {

    private final Logger log = LoggerFactory.getLogger(CronogramaResource.class);

    private static final String ENTITY_NAME = "cronograma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CronogramaService cronogramaService;

    public CronogramaResource(CronogramaService cronogramaService) {
        this.cronogramaService = cronogramaService;
    }

    /**
     * {@code POST  /cronogramas} : Create a new cronograma.
     *
     * @param cronogramaDTO the cronogramaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cronogramaDTO, or with status {@code 400 (Bad Request)} if the cronograma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cronogramas")
    public ResponseEntity<CronogramaDTO> createCronograma(@RequestBody CronogramaDTO cronogramaDTO) throws URISyntaxException {
        log.debug("REST request to save Cronograma : {}", cronogramaDTO);
        if (cronogramaDTO.getId() != null) {
            throw new BadRequestAlertException("A new cronograma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CronogramaDTO result = cronogramaService.save(cronogramaDTO);
        return ResponseEntity.created(new URI("/api/cronogramas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cronogramas} : Updates an existing cronograma.
     *
     * @param cronogramaDTO the cronogramaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cronogramaDTO,
     * or with status {@code 400 (Bad Request)} if the cronogramaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cronogramaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cronogramas")
    public ResponseEntity<CronogramaDTO> updateCronograma(@RequestBody CronogramaDTO cronogramaDTO) throws URISyntaxException {
        log.debug("REST request to update Cronograma : {}", cronogramaDTO);
        if (cronogramaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CronogramaDTO result = cronogramaService.save(cronogramaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cronogramaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cronogramas} : get all the cronogramas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cronogramas in body.
     */
    @GetMapping("/cronogramas")
    public ResponseEntity<List<CronogramaDTO>> getAllCronogramas(Pageable pageable) {
        log.debug("REST request to get a page of Cronogramas");
        Page<CronogramaDTO> page = cronogramaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cronogramas/:id} : get the "id" cronograma.
     *
     * @param id the id of the cronogramaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cronogramaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cronogramas/{id}")
    public ResponseEntity<CronogramaDTO> getCronograma(@PathVariable Long id) {
        log.debug("REST request to get Cronograma : {}", id);
        Optional<CronogramaDTO> cronogramaDTO = cronogramaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cronogramaDTO);
    }

    /**
     * {@code DELETE  /cronogramas/:id} : delete the "id" cronograma.
     *
     * @param id the id of the cronogramaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cronogramas/{id}")
    public ResponseEntity<Void> deleteCronograma(@PathVariable Long id) {
        log.debug("REST request to delete Cronograma : {}", id);
        cronogramaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /////////////777777777777777777777777777777777777777777777

    /**
     * {@code GET  /cronograma-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the cronogramaProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cronogramaProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cronograma-proyecto/{idProyecto}")
    public ResponseEntity<?> getCronogramaProyecto(@PathVariable Long idProyecto) {
        log.debug("REST request to get CronogramaProyecto : {}", idProyecto);
        try {
            final List<CronogramaDTO> cronogramaDTO = cronogramaService.findByCronogramaProyectoId(idProyecto);
            ResponseEntity<List<CronogramaDTO>> listResponseEntity = new ResponseEntity<>(cronogramaDTO, HttpStatus.OK);
            return listResponseEntity;

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
