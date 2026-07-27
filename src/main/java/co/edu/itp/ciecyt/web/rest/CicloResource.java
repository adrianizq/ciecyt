package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.CicloService;
import co.edu.itp.ciecyt.service.dto.CicloDTO;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Ciclo}.
 */
@RestController
@RequestMapping("/api")
public class CicloResource {

    private final Logger log = LoggerFactory.getLogger(CicloResource.class);

    private static final String ENTITY_NAME = "ciclo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CicloService cicloService;

    public CicloResource(CicloService cicloService) {
        this.cicloService = cicloService;
    }

    /**
     * {@code POST  /ciclos} : Create a new ciclo.
     *
     * @param cicloDTO the cicloDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cicloDTO, or with status {@code 400 (Bad Request)} if the ciclo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ciclos")
    public ResponseEntity<CicloDTO> createCiclo(@Valid @RequestBody CicloDTO cicloDTO) throws URISyntaxException {
        log.debug("REST request to save Ciclo : {}", cicloDTO);
        if (cicloDTO.getId() != null) {
            throw new BadRequestAlertException("A new ciclo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CicloDTO result = cicloService.save(cicloDTO);
        return ResponseEntity.created(new URI("/api/ciclos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ciclos} : Updates an existing ciclo.
     *
     * @param cicloDTO the cicloDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cicloDTO,
     * or with status {@code 400 (Bad Request)} if the cicloDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cicloDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ciclos")
    public ResponseEntity<CicloDTO> updateCiclo(@Valid @RequestBody CicloDTO cicloDTO) throws URISyntaxException {
        log.debug("REST request to update Ciclo : {}", cicloDTO);
        if (cicloDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CicloDTO result = cicloService.save(cicloDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cicloDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ciclos} : get all the ciclos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ciclos in body.
     */
    @GetMapping("/ciclos")
    public ResponseEntity<List<CicloDTO>> getAllCiclos(Pageable pageable) {
        log.debug("REST request to get a page of Ciclos");
        Page<CicloDTO> page = cicloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ciclos/all} : get all the ciclos as a list.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ciclos in body.
     */
    @GetMapping("/ciclos/all")
    public ResponseEntity<List<CicloDTO>> getAllCiclosList() {
        log.debug("REST request to get all Ciclos as list");
        List<CicloDTO> ciclos = cicloService.findAllList();
        return ResponseEntity.ok().body(ciclos);
    }

    /**
     * {@code GET  /ciclos/:id} : get the "id" ciclo.
     *
     * @param id the id of the cicloDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cicloDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ciclos/{id}")
    public ResponseEntity<CicloDTO> getCiclo(@PathVariable Long id) {
        log.debug("REST request to get Ciclo : {}", id);
        Optional<CicloDTO> cicloDTO = cicloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cicloDTO);
    }

    /**
     * {@code GET  /ciclos/:id/modalidades} : get modalidades for a given ciclo.
     *
     * @param id the id of the ciclo.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modalidades in body.
     */
    @GetMapping("/ciclos/{id}/modalidades")
    public ResponseEntity<List<ModalidadDTO>> getModalidadesByCiclo(@PathVariable Long id) {
        log.debug("REST request to get modalidades for Ciclo : {}", id);
        List<ModalidadDTO> modalidades = cicloService.findModalidadesByCicloId(id);
        return ResponseEntity.ok().body(modalidades);
    }

    /**
     * {@code DELETE  /ciclos/:id} : delete the "id" ciclo.
     *
     * @param id the id of the cicloDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ciclos/{id}")
    public ResponseEntity<Void> deleteCiclo(@PathVariable Long id) {
        log.debug("REST request to delete Ciclo : {}", id);
        cicloService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
