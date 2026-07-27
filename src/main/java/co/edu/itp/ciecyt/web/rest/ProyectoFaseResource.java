package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ProyectoFaseService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.ProyectoFaseDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ProyectoFase}.
 */
@RestController
@RequestMapping("/api")
public class ProyectoFaseResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoFaseResource.class);

    private static final String ENTITY_NAME = "proyectoFase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyectoFaseService proyectoFaseService;

    public ProyectoFaseResource(ProyectoFaseService proyectoFaseService) {
        this.proyectoFaseService = proyectoFaseService;
    }

    /**
     * {@code POST  /proyecto-fases} : Create a new proyectoFase.
     *
     * @param proyectoFaseDTO the proyectoFaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proyectoFaseDTO, or with status {@code 400 (Bad Request)} if the proyectoFase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proyecto-fases")
    public ResponseEntity<ProyectoFaseDTO> createProyectoFase(@RequestBody ProyectoFaseDTO proyectoFaseDTO) throws URISyntaxException {
        log.debug("REST request to save ProyectoFase : {}", proyectoFaseDTO);
        if (proyectoFaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new proyectoFase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProyectoFaseDTO result = proyectoFaseService.save(proyectoFaseDTO);
        return ResponseEntity.created(new URI("/api/proyecto-fases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proyecto-fases} : Updates an existing proyectoFase.
     *
     * @param proyectoFaseDTO the proyectoFaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectoFaseDTO,
     * or with status {@code 400 (Bad Request)} if the proyectoFaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proyectoFaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proyecto-fases")
    public ResponseEntity<ProyectoFaseDTO> updateProyectoFase(@RequestBody ProyectoFaseDTO proyectoFaseDTO) throws URISyntaxException {
        log.debug("REST request to update ProyectoFase : {}", proyectoFaseDTO);
        if (proyectoFaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProyectoFaseDTO result = proyectoFaseService.save(proyectoFaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyectoFaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proyecto-fases} : get all the proyectoFases.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyectoFases in body.
     */
    @GetMapping("/proyecto-fases")
    public ResponseEntity<List<ProyectoFaseDTO>> getAllProyectoFases(Pageable pageable) {
        log.debug("REST request to get a page of ProyectoFases");
        Page<ProyectoFaseDTO> page = proyectoFaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /proyecto-fases/:id} : get the "id" proyectoFase.
     *
     * @param id the id of the proyectoFaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectoFaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyecto-fases/{id}")
    public ResponseEntity<ProyectoFaseDTO> getProyectoFase(@PathVariable Long id) {
        log.debug("REST request to get ProyectoFase : {}", id);
        Optional<ProyectoFaseDTO> proyectoFaseDTO = proyectoFaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proyectoFaseDTO);
    }

    /**
     * {@code DELETE  /proyecto-fases/:id} : delete the "id" proyectoFase.
     *
     * @param id the id of the proyectoFaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proyecto-fases/{id}")
    public ResponseEntity<Void> deleteProyectoFase(@PathVariable Long id) {
        log.debug("REST request to delete ProyectoFase : {}", id);
        proyectoFaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
