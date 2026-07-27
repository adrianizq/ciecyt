package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.InformacionPasantia;

import co.edu.itp.ciecyt.service.InformacionPasantiaService;

import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import co.edu.itp.ciecyt.service.dto.InformacionPasantiaDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;

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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.InformacionPasantia}.
 */
@RestController
@RequestMapping("/api")

public class InformacionPasantiaResource {

    private final Logger log = LoggerFactory.getLogger(InformacionPasantiaResource.class);

    private static final String ENTITY_NAME = "informacionPasantia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    //private final InformacionPasantiaRepository informacionPasantiaRepository;
    private final InformacionPasantiaService informacionPasantiaService;

    public InformacionPasantiaResource(InformacionPasantiaService informacionPasantiaService) {
        this.informacionPasantiaService = informacionPasantiaService;
    }

    /**
     * {@code POST  /informacion-pasantias} : Create a new informacionPasantia.
     *
     * @param informacionPasantiaDTO the informacionPasantia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new informacionPasantia, or with status {@code 400 (Bad Request)} if the informacionPasantia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/informacion-pasantias")
    public ResponseEntity<InformacionPasantiaDTO> createInformacionPasantia(@RequestBody InformacionPasantiaDTO informacionPasantiaDTO) throws URISyntaxException {
        log.debug("REST request to save InformacionPasantia : {}", informacionPasantiaDTO);
        if (informacionPasantiaDTO.getId() != null) {
            throw new BadRequestAlertException("A new informacionPasantia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InformacionPasantiaDTO result = informacionPasantiaService.save(informacionPasantiaDTO);
        return ResponseEntity.created(new URI("/api/informacion-pasantias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /informacion-pasantias} : Updates an existing informacionPasantia.
     *
     * @param informacionPasantiaDTO the informacionPasantia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated informacionPasantia,
     * or with status {@code 400 (Bad Request)} if the informacionPasantia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the informacionPasantia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/informacion-pasantias")
    public ResponseEntity<InformacionPasantiaDTO> updateInformacionPasantia(@RequestBody InformacionPasantiaDTO informacionPasantiaDTO) throws URISyntaxException {
        log.debug("REST request to update InformacionPasantia : {}", informacionPasantiaDTO);
        if (informacionPasantiaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InformacionPasantiaDTO result = informacionPasantiaService.save(informacionPasantiaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, informacionPasantiaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /informacion-pasantias} : get all the informacionPasantias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informacionPasantias in body.
     */

    public ResponseEntity<List<InformacionPasantiaDTO>> getAllInformacionPasantias(Pageable pageable) {
        log.debug("REST request to get a page of InformacionPasantias");
        Page<InformacionPasantiaDTO> page = informacionPasantiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    ///////////////////////////////////////////////////////////77
    /**
     * {@code GET  /informacion-pasantia} : get all the informacionPasantia.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of informacionPasantia in body.
     */
    @GetMapping("/informacion-pasantias")
    public ResponseEntity<List<InformacionPasantiaDTO>> getAllElementoProyectos(Pageable pageable) {
        log.debug("REST request to get a page of InformacionPasantia");
        Page<InformacionPasantiaDTO> page = informacionPasantiaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    //////////////////////////////////////////////////////////////////







    /**
     * {@code GET  /informacion-pasantias/:id} : get the "id" informacionPasantia.
     *
     * @param id the id of the informacionPasantia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informacionPasantia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/informacion-pasantias/{id}")
    public ResponseEntity<InformacionPasantiaDTO> getInformacionPasantia(@PathVariable Long id) {
        log.debug("REST request to get InformacionPasantia : {}", id);
        Optional<InformacionPasantiaDTO> informacionPasantiaDTO = informacionPasantiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(informacionPasantiaDTO);
    }

    /**
     * {@code DELETE  /informacion-pasantias/:id} : delete the "id" informacionPasantia.
     *
     * @param id the id of the informacionPasantia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/informacion-pasantias/{id}")
    public ResponseEntity<Void> deleteInformacionPasantia(@PathVariable Long id) {
        log.debug("REST request to delete InformacionPasantia : {}", id);
        informacionPasantiaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }



    /////////////777777777777777777777777777777777777777777777

    /**
     * {@code GET  /informacion-pasantia-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the informacionPasantiaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the informacionPasantiaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/informacion-pasantia-proyecto/{idProyecto}")
    public ResponseEntity<?> findByInformacionPasantiaProyectoId(@PathVariable Long idProyecto) {
        log.debug("REST request to get ElementoProyectoProyecto : {}", idProyecto);
        try {
            final InformacionPasantiaDTO dto = informacionPasantiaService.findByInformacionPasantiaProyectoId(idProyecto);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
