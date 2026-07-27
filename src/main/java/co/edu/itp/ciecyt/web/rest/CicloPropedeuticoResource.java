package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.CicloPropedeuticoService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.CicloPropedeuticoDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.CicloPropedeutico}.
 */
@RestController
@RequestMapping("/api")
public class CicloPropedeuticoResource {

    private final Logger log = LoggerFactory.getLogger(CicloPropedeuticoResource.class);

    private static final String ENTITY_NAME = "cicloPropedeutico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CicloPropedeuticoService cicloPropedeuticoService;

    public CicloPropedeuticoResource(CicloPropedeuticoService cicloPropedeuticoService) {
        this.cicloPropedeuticoService = cicloPropedeuticoService;
    }

    /**
     * {@code POST  /ciclo-propedeuticos} : Create a new cicloPropedeutico.
     *
     * @param cicloPropedeuticoDTO the cicloPropedeuticoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cicloPropedeuticoDTO, or with status {@code 400 (Bad Request)} if the cicloPropedeutico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ciclo-propedeuticos")
    public ResponseEntity<CicloPropedeuticoDTO> createCicloPropedeutico(@RequestBody CicloPropedeuticoDTO cicloPropedeuticoDTO) throws URISyntaxException {
        log.debug("REST request to save CicloPropedeutico : {}", cicloPropedeuticoDTO);
        if (cicloPropedeuticoDTO.getId() != null) {
            throw new BadRequestAlertException("A new cicloPropedeutico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CicloPropedeuticoDTO result = cicloPropedeuticoService.save(cicloPropedeuticoDTO);
        return ResponseEntity.created(new URI("/api/ciclo-propedeuticos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ciclo-propedeuticos} : Updates an existing cicloPropedeutico.
     *
     * @param cicloPropedeuticoDTO the cicloPropedeuticoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cicloPropedeuticoDTO,
     * or with status {@code 400 (Bad Request)} if the cicloPropedeuticoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cicloPropedeuticoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ciclo-propedeuticos")
    public ResponseEntity<CicloPropedeuticoDTO> updateCicloPropedeutico(@RequestBody CicloPropedeuticoDTO cicloPropedeuticoDTO) throws URISyntaxException {
        log.debug("REST request to update CicloPropedeutico : {}", cicloPropedeuticoDTO);
        if (cicloPropedeuticoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CicloPropedeuticoDTO result = cicloPropedeuticoService.save(cicloPropedeuticoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cicloPropedeuticoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ciclo-propedeuticos} : get all the cicloPropedeuticos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cicloPropedeuticos in body.
     */
    @GetMapping("/ciclo-propedeuticos")
    public ResponseEntity<List<CicloPropedeuticoDTO>> getAllCicloPropedeuticos(Pageable pageable) {
        log.debug("REST request to get a page of CicloPropedeuticos");
        Page<CicloPropedeuticoDTO> page = cicloPropedeuticoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ciclo-propedeuticos/:id} : get the "id" cicloPropedeutico.
     *
     * @param id the id of the cicloPropedeuticoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cicloPropedeuticoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ciclo-propedeuticos/{id}")
    public ResponseEntity<CicloPropedeuticoDTO> getCicloPropedeutico(@PathVariable Long id) {
        log.debug("REST request to get CicloPropedeutico : {}", id);
        Optional<CicloPropedeuticoDTO> cicloPropedeuticoDTO = cicloPropedeuticoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cicloPropedeuticoDTO);
    }

    /**
     * {@code DELETE  /ciclo-propedeuticos/:id} : delete the "id" cicloPropedeutico.
     *
     * @param id the id of the cicloPropedeuticoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ciclo-propedeuticos/{id}")
    public ResponseEntity<Void> deleteCicloPropedeutico(@PathVariable Long id) {
        log.debug("REST request to delete CicloPropedeutico : {}", id);
        cicloPropedeuticoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
