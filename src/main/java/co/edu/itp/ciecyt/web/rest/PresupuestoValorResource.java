package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.PresupuestoValorService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.PresupuestoValorDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.PresupuestoValor}.
 */
@RestController
@RequestMapping("/api")
public class PresupuestoValorResource {

    private final Logger log = LoggerFactory.getLogger(PresupuestoValorResource.class);

    private static final String ENTITY_NAME = "presupuestoValor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PresupuestoValorService presupuestoValorService;

    public PresupuestoValorResource(PresupuestoValorService presupuestoValorService) {
        this.presupuestoValorService = presupuestoValorService;
    }

    /**
     * {@code POST  /presupuesto-valors} : Create a new presupuestoValor.
     *
     * @param presupuestoValorDTO the presupuestoValorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new presupuestoValorDTO, or with status {@code 400 (Bad Request)} if the presupuestoValor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/presupuesto-valors")
    public ResponseEntity<PresupuestoValorDTO> createPresupuestoValor(@RequestBody PresupuestoValorDTO presupuestoValorDTO) throws URISyntaxException {
        log.debug("REST request to save PresupuestoValor : {}", presupuestoValorDTO);
        if (presupuestoValorDTO.getId() != null) {
            throw new BadRequestAlertException("A new presupuestoValor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PresupuestoValorDTO result = presupuestoValorService.save(presupuestoValorDTO);
        return ResponseEntity.created(new URI("/api/presupuesto-valors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /presupuesto-valors} : Updates an existing presupuestoValor.
     *
     * @param presupuestoValorDTO the presupuestoValorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presupuestoValorDTO,
     * or with status {@code 400 (Bad Request)} if the presupuestoValorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the presupuestoValorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/presupuesto-valors")
    public ResponseEntity<PresupuestoValorDTO> updatePresupuestoValor(@RequestBody PresupuestoValorDTO presupuestoValorDTO) throws URISyntaxException {
        log.debug("REST request to update PresupuestoValor : {}", presupuestoValorDTO);
        if (presupuestoValorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PresupuestoValorDTO result = presupuestoValorService.save(presupuestoValorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, presupuestoValorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /presupuesto-valors} : get all the presupuestoValors.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of presupuestoValors in body.
     */
    @GetMapping("/presupuesto-valors")
    public ResponseEntity<List<PresupuestoValorDTO>> getAllPresupuestoValors(Pageable pageable) {
        log.debug("REST request to get a page of PresupuestoValors");
        Page<PresupuestoValorDTO> page = presupuestoValorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /presupuesto-valors/:id} : get the "id" presupuestoValor.
     *
     * @param id the id of the presupuestoValorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the presupuestoValorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/presupuesto-valors/{id}")
    public ResponseEntity<PresupuestoValorDTO> getPresupuestoValor(@PathVariable Long id) {
        log.debug("REST request to get PresupuestoValor : {}", id);
        Optional<PresupuestoValorDTO> presupuestoValorDTO = presupuestoValorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(presupuestoValorDTO);
    }

    /**
     * {@code DELETE  /presupuesto-valors/:id} : delete the "id" presupuestoValor.
     *
     * @param id the id of the presupuestoValorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/presupuesto-valors/{id}")
    public ResponseEntity<Void> deletePresupuestoValor(@PathVariable Long id) {
        log.debug("REST request to delete PresupuestoValor : {}", id);
        presupuestoValorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/presupuesto-valor-proyecto/{idProyecto}")
    public ResponseEntity<?> getIntegranteProyectosProyecto(@PathVariable Long idProyecto) {
        log.debug("REST request to get PresupuestoValor : {}", idProyecto);
        try{
        final List<PresupuestoValorDTO> presupuestoValorDTO = presupuestoValorService.findByPresupuestoValorProyectoId(idProyecto);
        return new ResponseEntity<>(presupuestoValorDTO, HttpStatus.OK);
        
    }catch (Exception e){
      
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
      }
    }
}
