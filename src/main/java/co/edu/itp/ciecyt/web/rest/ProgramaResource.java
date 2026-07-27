package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ProgramaService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.ProgramaDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Programa}.
 */
@RestController
@RequestMapping("/api")
public class ProgramaResource {

    private final Logger log = LoggerFactory.getLogger(ProgramaResource.class);

    private static final String ENTITY_NAME = "programa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProgramaService programaService;

    public ProgramaResource(ProgramaService programaService) {
        this.programaService = programaService;
    }

    /**
     * {@code POST  /programas} : Create a new programa.
     *
     * @param programaDTO the programaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new programaDTO, or with status {@code 400 (Bad Request)} if the programa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/programas")
    public ResponseEntity<ProgramaDTO> createPrograma(@RequestBody ProgramaDTO programaDTO) throws URISyntaxException {
        log.debug("REST request to save Programa : {}", programaDTO);
        if (programaDTO.getId() != null) {
            throw new BadRequestAlertException("A new programa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProgramaDTO result = programaService.save(programaDTO);
        return ResponseEntity.created(new URI("/api/programas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /programas} : Updates an existing programa.
     *
     * @param programaDTO the programaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated programaDTO,
     * or with status {@code 400 (Bad Request)} if the programaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the programaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/programas")
    public ResponseEntity<ProgramaDTO> updatePrograma(@RequestBody ProgramaDTO programaDTO) throws URISyntaxException {
        log.debug("REST request to update Programa : {}", programaDTO);
        if (programaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProgramaDTO result = programaService.save(programaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, programaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /programas} : get all the programas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programas in body.
     */
    @GetMapping("/programas")
    public List<ProgramaDTO> getAllProgramas() {
        log.debug("REST request to get all Programas");
        return programaService.findAll();
    }

    /**
     * {@code GET  /programas/ciclo/:ciclo} : get programas by ciclo name.
     *
     * @param ciclo the ciclo name.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of programas in body.
     */
    @GetMapping("/programas/ciclo/{ciclo}")
    public List<ProgramaDTO> getProgramasByCiclo(@PathVariable String ciclo) {
        log.debug("REST request to get Programas by ciclo : {}", ciclo);
        return programaService.findByCiclo(ciclo);
    }

    /**
     * {@code GET  /programas/:id} : get the "id" programa.
     *
     * @param id the id of the programaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the programaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/programas/{id}")
    public ResponseEntity<ProgramaDTO> getPrograma(@PathVariable Long id) {
        log.debug("REST request to get Programa : {}", id);
        Optional<ProgramaDTO> programaDTO = programaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(programaDTO);
    }

    /**
     * {@code DELETE  /programas/:id} : delete the "id" programa.
     *
     * @param id the id of the programaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/programas/{id}")
    public ResponseEntity<Void> deletePrograma(@PathVariable Long id) {
        log.debug("REST request to delete Programa : {}", id);
        programaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
