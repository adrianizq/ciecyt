package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.Fases;
import co.edu.itp.ciecyt.service.FasesService;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.FasesDTO;

import co.edu.itp.ciecyt.web.rest.model.ApiMessage;
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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Fases}.
 */
@RestController
@RequestMapping("/api")
public class FasesResource {

    private final Logger log = LoggerFactory.getLogger(FasesResource.class);

    private static final String ENTITY_NAME = "fases";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FasesService fasesService;

    public FasesResource(FasesService fasesService) {
        this.fasesService = fasesService;
    }

    /**
     * {@code POST  /fases} : Create a new fases.
     *
     * @param fasesDTO the fasesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fasesDTO, or with status {@code 400 (Bad Request)} if the fases has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fases")
    public ResponseEntity<FasesDTO> createFases(@RequestBody FasesDTO fasesDTO) throws URISyntaxException {
        log.debug("REST request to save Fases : {}", fasesDTO);
        if (fasesDTO.getId() != null) {
            throw new BadRequestAlertException("A new fases cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FasesDTO result = fasesService.save(fasesDTO);
        return ResponseEntity.created(new URI("/api/fases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fases} : Updates an existing fases.
     *
     * @param fasesDTO the fasesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fasesDTO,
     * or with status {@code 400 (Bad Request)} if the fasesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fasesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fases")
    public ResponseEntity<FasesDTO> updateFases(@RequestBody FasesDTO fasesDTO) throws URISyntaxException {
        log.debug("REST request to update Fases : {}", fasesDTO);
        if (fasesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FasesDTO result = fasesService.save(fasesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fasesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fases} : get all the fases.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fases in body.
     */
    @GetMapping("/fases")
    public ResponseEntity<List<FasesDTO>> getAllFases(Pageable pageable) {
        log.debug("REST request to get a page of Fases");
        Page<FasesDTO> page = fasesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fases/:id} : get the "id" fases.
     *
     * @param id the id of the fasesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fasesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fases/{id}")
    public ResponseEntity<FasesDTO> getFases(@PathVariable Long id) {
        log.debug("REST request to get Fases : {}", id);
        Optional<FasesDTO> fasesDTO = fasesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fasesDTO);
    }

    /**
     * {@code DELETE  /fases/:id} : delete the "id" fases.
     *
     * @param id the id of the fasesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fases/{id}")
    public ResponseEntity<Void> deleteFases(@PathVariable Long id) {
        log.debug("REST request to delete Fases : {}", id);
        fasesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }



    /////////////////////
    @GetMapping("/fase/{fase}")
    public ResponseEntity<?> getFase(@PathVariable String fase) {
        log.debug("REST request to get a page of Fases");
        try {
            final FasesDTO obj = fasesService.findByFase(fase);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }

/*    @GetMapping("/fase-modalidad/{idModalidad}")
    public ResponseEntity<?> getFasesModalidadId(@PathVariable Long idModalidad) throws Exception {
        log.debug("REST request to get a page of Fases");
        try {
            List<FasesDTO> obj = fasesService.findByFasesModalidadId(idModalidad);
            return new ResponseEntity<>(obj, HttpStatus.OK);

        }
        catch (Exception e){
            String error = e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ApiMessage("ERR_99", error));
        }
    }

*/

}
