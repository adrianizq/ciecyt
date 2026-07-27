package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.Menu;
import co.edu.itp.ciecyt.service.PreguntaModalidadService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.PreguntaModalidadDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.PreguntaModalidad}.
 */
@RestController
@RequestMapping("/api")
public class PreguntaModalidadResource {

    private final Logger log = LoggerFactory.getLogger(PreguntaModalidadResource.class);

    private static final String ENTITY_NAME = "preguntaModalidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreguntaModalidadService preguntaModalidadService;

    public PreguntaModalidadResource(PreguntaModalidadService preguntaModalidadService) {
        this.preguntaModalidadService = preguntaModalidadService;
    }

    /**
     * {@code POST  /pregunta-modalidads} : Create a new preguntaModalidad.
     *
     * @param preguntaModalidadDTO the preguntaModalidadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preguntaModalidadDTO, or with status {@code 400 (Bad Request)} if the preguntaModalidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pregunta-modalidads")
    public ResponseEntity<PreguntaModalidadDTO> createPreguntaModalidad(@RequestBody PreguntaModalidadDTO preguntaModalidadDTO) throws URISyntaxException {
        log.debug("REST request to save PreguntaModalidad : {}", preguntaModalidadDTO);
        if (preguntaModalidadDTO.getId() != null) {
            throw new BadRequestAlertException("A new preguntaModalidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreguntaModalidadDTO result = preguntaModalidadService.save(preguntaModalidadDTO);
        return ResponseEntity.created(new URI("/api/pregunta-modalidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pregunta-modalidads} : Updates an existing preguntaModalidad.
     *
     * @param preguntaModalidadDTO the preguntaModalidadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preguntaModalidadDTO,
     * or with status {@code 400 (Bad Request)} if the preguntaModalidadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preguntaModalidadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pregunta-modalidads")
    public ResponseEntity<PreguntaModalidadDTO> updatePreguntaModalidad(@RequestBody PreguntaModalidadDTO preguntaModalidadDTO) throws URISyntaxException {
        log.debug("REST request to update PreguntaModalidad : {}", preguntaModalidadDTO);
        if (preguntaModalidadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PreguntaModalidadDTO result = preguntaModalidadService.save(preguntaModalidadDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preguntaModalidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pregunta-modalidads} : get all the preguntaModalidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preguntaModalidads in body.
     */
    @GetMapping("/pregunta-modalidads")
    public List<PreguntaModalidadDTO> getAllPreguntaModalidads() {
        log.debug("REST request to get all PreguntaModalidads");
        return preguntaModalidadService.findAll();
    }

    /**
     * {@code GET  /pregunta-modalidads/:id} : get the "id" preguntaModalidad.
     *
     * @param id the id of the preguntaModalidadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preguntaModalidadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pregunta-modalidads/{id}")
    public ResponseEntity<PreguntaModalidadDTO> getPreguntaModalidad(@PathVariable Long id) {
        log.debug("REST request to get PreguntaModalidad : {}", id);
        Optional<PreguntaModalidadDTO> preguntaModalidadDTO = preguntaModalidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preguntaModalidadDTO);
    }

    /*@GetMapping("/pregunta-modalidad-preguntaid-modalidadid/{idPregunta/{idModalidad}}")
    public ResponseEntity<PreguntaModalidadDTO> getPreguntaModalidad(@PathVariable Long idPregunta, @PathVariable Long idModalidad) {
        log.debug("REST request to get PreguntaModalidad : {}", idPregunta);
        PreguntaModalidadDTO preguntaModalidadDTO = preguntaModalidadService.findByPreguntaIdAndModalidad2Id(idPregunta,idModalidad);
        return ResponseUtil.wrapOrNotFound(preguntaModalidadDTO);
    }*/
    //findByPreguntaIdAndModalidad2Id

    /**
     * {@code DELETE  /pregunta-modalidads/:id} : delete the "id" preguntaModalidad.
     *
     * @param id the id of the preguntaModalidadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pregunta-modalidads/{id}")
    public ResponseEntity<Void> deletePreguntaModalidad(@PathVariable Long id) {
        log.debug("REST request to delete PreguntaModalidad : {}", id);
        preguntaModalidadService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/pregunta-pregunta-modalidad/{idModalidad}")
    public ResponseEntity<?> getPreguntaPreguntaModalidad(@PathVariable Long idModalidad) {
        log.debug("REST request to get PreguntaPreguntaModalidad : {}", idModalidad);
        try {
            List<PreguntaModalidadDTO> preguntaModalidadDTO = preguntaModalidadService.findByModalidad2Id(idModalidad);
            return new ResponseEntity<>(preguntaModalidadDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }


    @GetMapping("/pregunta-modalidad-preguntaid/{idPregunta}")
    public ResponseEntity<?> getPreguntaModalidadIdPregunta(@PathVariable Long idPregunta) {
        log.debug("REST request to get PreguntaPreguntaModalidad : {}", idPregunta);
        try {
            List<PreguntaModalidadDTO> preguntaModalidadDTO = preguntaModalidadService.findByPreguntaId(idPregunta);
            return new ResponseEntity<>(preguntaModalidadDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

}
