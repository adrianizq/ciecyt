package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.InvestigacionTipoService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.InvestigacionTipoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.InvestigacionTipo}.
 */
@RestController
@RequestMapping("/api")
public class InvestigacionTipoResource {

    private final Logger log = LoggerFactory.getLogger(InvestigacionTipoResource.class);

    private static final String ENTITY_NAME = "investigacionTipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvestigacionTipoService investigacionTipoService;

    public InvestigacionTipoResource(InvestigacionTipoService investigacionTipoService) {
        this.investigacionTipoService = investigacionTipoService;
    }

    /**
     * {@code POST  /investigacion-tipos} : Create a new investigacionTipo.
     *
     * @param investigacionTipoDTO the investigacionTipoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new investigacionTipoDTO, or with status {@code 400 (Bad Request)} if the investigacionTipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/investigacion-tipos")
    public ResponseEntity<InvestigacionTipoDTO> createInvestigacionTipo(@RequestBody InvestigacionTipoDTO investigacionTipoDTO) throws URISyntaxException {
        log.debug("REST request to save InvestigacionTipo : {}", investigacionTipoDTO);
        if (investigacionTipoDTO.getId() != null) {
            throw new BadRequestAlertException("A new investigacionTipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InvestigacionTipoDTO result = investigacionTipoService.save(investigacionTipoDTO);
        return ResponseEntity.created(new URI("/api/investigacion-tipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /investigacion-tipos} : Updates an existing investigacionTipo.
     *
     * @param investigacionTipoDTO the investigacionTipoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated investigacionTipoDTO,
     * or with status {@code 400 (Bad Request)} if the investigacionTipoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the investigacionTipoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/investigacion-tipos")
    public ResponseEntity<InvestigacionTipoDTO> updateInvestigacionTipo(@RequestBody InvestigacionTipoDTO investigacionTipoDTO) throws URISyntaxException {
        log.debug("REST request to update InvestigacionTipo : {}", investigacionTipoDTO);
        if (investigacionTipoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvestigacionTipoDTO result = investigacionTipoService.save(investigacionTipoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, investigacionTipoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /investigacion-tipos} : get all the investigacionTipos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of investigacionTipos in body.
     */
    @GetMapping("/investigacion-tipos")
    public List<InvestigacionTipoDTO> getAllInvestigacionTipos() {
        log.debug("REST request to get all InvestigacionTipos");
        return investigacionTipoService.findAll();
    }

    /**
     * {@code GET  /investigacion-tipos/:id} : get the "id" investigacionTipo.
     *
     * @param id the id of the investigacionTipoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the investigacionTipoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/investigacion-tipos/{id}")
    public ResponseEntity<InvestigacionTipoDTO> getInvestigacionTipo(@PathVariable Long id) {
        log.debug("REST request to get InvestigacionTipo : {}", id);
        Optional<InvestigacionTipoDTO> investigacionTipoDTO = investigacionTipoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(investigacionTipoDTO);
    }

    /**
     * {@code DELETE  /investigacion-tipos/:id} : delete the "id" investigacionTipo.
     *
     * @param id the id of the investigacionTipoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/investigacion-tipos/{id}")
    public ResponseEntity<Void> deleteInvestigacionTipo(@PathVariable Long id) {
        log.debug("REST request to delete InvestigacionTipo : {}", id);
        investigacionTipoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
