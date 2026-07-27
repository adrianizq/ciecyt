package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ImpactosEsperadosService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.ImpactosEsperadosDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ImpactosEsperados}.
 */
@RestController
@RequestMapping("/api")
public class ImpactosEsperadosResource {

    private final Logger log = LoggerFactory.getLogger(ImpactosEsperadosResource.class);

    private static final String ENTITY_NAME = "impactosEsperados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpactosEsperadosService impactosEsperadosService;

    public ImpactosEsperadosResource(ImpactosEsperadosService impactosEsperadosService) {
        this.impactosEsperadosService = impactosEsperadosService;
    }

    /**
     * {@code POST  /impactos-esperados} : Create a new impactosEsperados.
     *
     * @param impactosEsperadosDTO the impactosEsperadosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impactosEsperadosDTO, or with status {@code 400 (Bad Request)} if the impactosEsperados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/impactos-esperados")
    public ResponseEntity<ImpactosEsperadosDTO> createImpactosEsperados(@RequestBody ImpactosEsperadosDTO impactosEsperadosDTO) throws URISyntaxException {
        log.debug("REST request to save ImpactosEsperados : {}", impactosEsperadosDTO);
        if (impactosEsperadosDTO.getId() != null) {
            throw new BadRequestAlertException("A new impactosEsperados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImpactosEsperadosDTO result = impactosEsperadosService.save(impactosEsperadosDTO);
        return ResponseEntity.created(new URI("/api/impactos-esperados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /impactos-esperados} : Updates an existing impactosEsperados.
     *
     * @param impactosEsperadosDTO the impactosEsperadosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impactosEsperadosDTO,
     * or with status {@code 400 (Bad Request)} if the impactosEsperadosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impactosEsperadosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/impactos-esperados")
    public ResponseEntity<ImpactosEsperadosDTO> updateImpactosEsperados(@RequestBody ImpactosEsperadosDTO impactosEsperadosDTO) throws URISyntaxException {
        log.debug("REST request to update ImpactosEsperados : {}", impactosEsperadosDTO);
        if (impactosEsperadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImpactosEsperadosDTO result = impactosEsperadosService.save(impactosEsperadosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, impactosEsperadosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /impactos-esperados} : get all the impactosEsperados.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impactosEsperados in body.
     */
    @GetMapping("/impactos-esperados")
    public ResponseEntity<List<ImpactosEsperadosDTO>> getAllImpactosEsperados(Pageable pageable) {
        log.debug("REST request to get a page of ImpactosEsperados");
        Page<ImpactosEsperadosDTO> page = impactosEsperadosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /impactos-esperados/:id} : get the "id" impactosEsperados.
     *
     * @param id the id of the impactosEsperadosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impactosEsperadosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/impactos-esperados/{id}")
    public ResponseEntity<ImpactosEsperadosDTO> getImpactosEsperados(@PathVariable Long id) {
        log.debug("REST request to get ImpactosEsperados : {}", id);
        Optional<ImpactosEsperadosDTO> impactosEsperadosDTO = impactosEsperadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impactosEsperadosDTO);
    }

    /**
     * {@code DELETE  /impactos-esperados/:id} : delete the "id" impactosEsperados.
     *
     * @param id the id of the impactosEsperadosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/impactos-esperados/{id}")
    public ResponseEntity<Void> deleteImpactosEsperados(@PathVariable Long id) {
        log.debug("REST request to delete ImpactosEsperados : {}", id);
        impactosEsperadosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /////////////777777777777777777777777777777777777777777777

    /**
     * {@code GET  /impactos-esperados-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the impactosEsperadosProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impactosEsperadosProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/impactos-esperados-proyecto/{idProyecto}")
    public ResponseEntity<?> getImpactosEsperadosProyecto(@PathVariable Long idProyecto) {
        log.debug("REST request to get ImpactosEsperadosProyecto : {}", idProyecto);
        try {
            final List<ImpactosEsperadosDTO> impactosEsperadosDTO = impactosEsperadosService.findByImpactosEsperadoProyectoId(idProyecto);
            return new ResponseEntity<>(impactosEsperadosDTO, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
