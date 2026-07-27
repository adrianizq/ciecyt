package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.FichaTecnicaService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.FichaTecnicaDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.FichaTecnica}.
 */
@RestController
@RequestMapping("/api")
public class FichaTecnicaResource {

    private final Logger log = LoggerFactory.getLogger(FichaTecnicaResource.class);

    private static final String ENTITY_NAME = "fichaTecnica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FichaTecnicaService fichaTecnicaService;

    public FichaTecnicaResource(FichaTecnicaService fichaTecnicaService) {
        this.fichaTecnicaService = fichaTecnicaService;
    }

    /**
     * {@code POST  /ficha-tecnicas} : Create a new fichaTecnica.
     *
     * @param fichaTecnicaDTO the fichaTecnicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fichaTecnicaDTO, or with status {@code 400 (Bad Request)} if the fichaTecnica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ficha-tecnicas")
    public ResponseEntity<FichaTecnicaDTO> createFichaTecnica(@RequestBody FichaTecnicaDTO fichaTecnicaDTO) throws URISyntaxException {
        log.debug("REST request to save FichaTecnica : {}", fichaTecnicaDTO);
        if (fichaTecnicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new fichaTecnica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FichaTecnicaDTO result = fichaTecnicaService.save(fichaTecnicaDTO);
        return ResponseEntity.created(new URI("/api/ficha-tecnicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ficha-tecnicas} : Updates an existing fichaTecnica.
     *
     * @param fichaTecnicaDTO the fichaTecnicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fichaTecnicaDTO,
     * or with status {@code 400 (Bad Request)} if the fichaTecnicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fichaTecnicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ficha-tecnicas")
    public ResponseEntity<FichaTecnicaDTO> updateFichaTecnica(@RequestBody FichaTecnicaDTO fichaTecnicaDTO) throws URISyntaxException {
        log.debug("REST request to update FichaTecnica : {}", fichaTecnicaDTO);
        if (fichaTecnicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FichaTecnicaDTO result = fichaTecnicaService.save(fichaTecnicaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fichaTecnicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ficha-tecnicas} : get all the fichaTecnicas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fichaTecnicas in body.
     */
    @GetMapping("/ficha-tecnicas")
    public ResponseEntity<List<FichaTecnicaDTO>> getAllFichaTecnicas(Pageable pageable) {
        log.debug("REST request to get a page of FichaTecnicas");
        Page<FichaTecnicaDTO> page = fichaTecnicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ficha-tecnicas/:id} : get the "id" fichaTecnica.
     *
     * @param id the id of the fichaTecnicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fichaTecnicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ficha-tecnicas/{id}")
    public ResponseEntity<FichaTecnicaDTO> getFichaTecnica(@PathVariable Long id) {
        log.debug("REST request to get FichaTecnica : {}", id);
        Optional<FichaTecnicaDTO> fichaTecnicaDTO = fichaTecnicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichaTecnicaDTO);
    }

    /**
     * {@code DELETE  /ficha-tecnicas/:id} : delete the "id" fichaTecnica.
     *
     * @param id the id of the fichaTecnicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ficha-tecnicas/{id}")
    public ResponseEntity<Void> deleteFichaTecnica(@PathVariable Long id) {
        log.debug("REST request to delete FichaTecnica : {}", id);
        fichaTecnicaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
