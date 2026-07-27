package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.LineaInvestigacionService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.LineaInvestigacionDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.LineaInvestigacion}.
 */
@RestController
@RequestMapping("/api")
public class LineaInvestigacionResource {

    private final Logger log = LoggerFactory.getLogger(LineaInvestigacionResource.class);

    private static final String ENTITY_NAME = "lineaInvestigacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LineaInvestigacionService lineaInvestigacionService;

    public LineaInvestigacionResource(LineaInvestigacionService lineaInvestigacionService) {
        this.lineaInvestigacionService = lineaInvestigacionService;
    }

    /**
     * {@code POST  /linea-investigacions} : Create a new lineaInvestigacion.
     *
     * @param lineaInvestigacionDTO the lineaInvestigacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lineaInvestigacionDTO, or with status {@code 400 (Bad Request)} if the lineaInvestigacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linea-investigacions")
    public ResponseEntity<LineaInvestigacionDTO> createLineaInvestigacion(@RequestBody LineaInvestigacionDTO lineaInvestigacionDTO) throws URISyntaxException {
        log.debug("REST request to save LineaInvestigacion : {}", lineaInvestigacionDTO);
        if (lineaInvestigacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new lineaInvestigacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LineaInvestigacionDTO result = lineaInvestigacionService.save(lineaInvestigacionDTO);
        return ResponseEntity.created(new URI("/api/linea-investigacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linea-investigacions} : Updates an existing lineaInvestigacion.
     *
     * @param lineaInvestigacionDTO the lineaInvestigacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lineaInvestigacionDTO,
     * or with status {@code 400 (Bad Request)} if the lineaInvestigacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lineaInvestigacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linea-investigacions")
    public ResponseEntity<LineaInvestigacionDTO> updateLineaInvestigacion(@RequestBody LineaInvestigacionDTO lineaInvestigacionDTO) throws URISyntaxException {
        log.debug("REST request to update LineaInvestigacion : {}", lineaInvestigacionDTO);
        if (lineaInvestigacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LineaInvestigacionDTO result = lineaInvestigacionService.save(lineaInvestigacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lineaInvestigacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /linea-investigacions} : get all the lineaInvestigacions.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lineaInvestigacions in body.
     */
    @GetMapping("/linea-investigacions")
    public ResponseEntity<List<LineaInvestigacionDTO>> getAllLineaInvestigacions(Pageable pageable) {
        log.debug("REST request to get a page of LineaInvestigacions");
        Page<LineaInvestigacionDTO> page = lineaInvestigacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /linea-investigacions/:id} : get the "id" lineaInvestigacion.
     *
     * @param id the id of the lineaInvestigacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lineaInvestigacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linea-investigacions/{id}")
    public ResponseEntity<LineaInvestigacionDTO> getLineaInvestigacion(@PathVariable Long id) {
        log.debug("REST request to get LineaInvestigacion : {}", id);
        Optional<LineaInvestigacionDTO> lineaInvestigacionDTO = lineaInvestigacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lineaInvestigacionDTO);
    }

    /**
     * {@code DELETE  /linea-investigacions/:id} : delete the "id" lineaInvestigacion.
     *
     * @param id the id of the lineaInvestigacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linea-investigacions/{id}")
    public ResponseEntity<Void> deleteLineaInvestigacion(@PathVariable Long id) {
        log.debug("REST request to delete LineaInvestigacion : {}", id);
        lineaInvestigacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
