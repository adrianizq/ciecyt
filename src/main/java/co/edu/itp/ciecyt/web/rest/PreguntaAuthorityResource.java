package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.Authority;
import co.edu.itp.ciecyt.service.PreguntaAuthorityService;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;
import co.edu.itp.ciecyt.service.dto.PreguntaModalidadDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.PreguntaAuthorityDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.PreguntaAuthority}.
 */
@RestController
@RequestMapping("/api")
public class PreguntaAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(PreguntaAuthorityResource.class);

    private static final String ENTITY_NAME = "preguntaAuthority";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PreguntaAuthorityService preguntaAuthorityService;

    public PreguntaAuthorityResource(PreguntaAuthorityService preguntaAuthorityService) {
        this.preguntaAuthorityService = preguntaAuthorityService;
    }

    /**
     * {@code POST  /pregunta-authorities} : Create a new preguntaAuthority.
     *
     * @param preguntaAuthorityDTO the preguntaAuthorityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new preguntaAuthorityDTO, or with status {@code 400 (Bad Request)} if the preguntaAuthority has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pregunta-authorities")
    public ResponseEntity<PreguntaAuthorityDTO> createPreguntaAuthority(@RequestBody PreguntaAuthorityDTO preguntaAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to save PreguntaAuthority : {}", preguntaAuthorityDTO);
        if (preguntaAuthorityDTO.getId() != null) {
            throw new BadRequestAlertException("A new preguntaAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreguntaAuthorityDTO result = preguntaAuthorityService.save(preguntaAuthorityDTO);
        return ResponseEntity.created(new URI("/api/pregunta-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pregunta-authorities} : Updates an existing preguntaAuthority.
     *
     * @param preguntaAuthorityDTO the preguntaAuthorityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated preguntaAuthorityDTO,
     * or with status {@code 400 (Bad Request)} if the preguntaAuthorityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the preguntaAuthorityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pregunta-authorities")
    public ResponseEntity<PreguntaAuthorityDTO> updatePreguntaAuthority(@RequestBody PreguntaAuthorityDTO preguntaAuthorityDTO) throws URISyntaxException {
        log.debug("REST request to update PreguntaAuthority : {}", preguntaAuthorityDTO);
        if (preguntaAuthorityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PreguntaAuthorityDTO result = preguntaAuthorityService.save(preguntaAuthorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, preguntaAuthorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pregunta-authorities} : get all the preguntaAuthorities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of preguntaAuthorities in body.
     */
    @GetMapping("/pregunta-authorities")
    public List<PreguntaAuthorityDTO> getAllPreguntaAuthorities() {
        log.debug("REST request to get all PreguntaAuthorities");
        return preguntaAuthorityService.findAll();
    }

    /**
     * {@code GET  /pregunta-authorities/:id} : get the "id" preguntaAuthority.
     *
     * @param id the id of the preguntaAuthorityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the preguntaAuthorityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pregunta-authorities/{id}")
    public ResponseEntity<PreguntaAuthorityDTO> getPreguntaAuthority(@PathVariable Long id) {
        log.debug("REST request to get PreguntaAuthority : {}", id);
        Optional<PreguntaAuthorityDTO> preguntaAuthorityDTO = preguntaAuthorityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(preguntaAuthorityDTO);
    }

    /**
     * {@code DELETE  /pregunta-authorities/:id} : delete the "id" preguntaAuthority.
     *
     * @param id the id of the preguntaAuthorityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pregunta-authorities/{id}")
    public ResponseEntity<Void> deletePreguntaAuthority(@PathVariable Long id) {
        log.debug("REST request to delete PreguntaAuthority : {}", id);
        preguntaAuthorityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/pregunta-authority-preguntaid/{idPregunta}")
    public ResponseEntity<?> getPreguntaAuthorityIdPregunta(@PathVariable Long idPregunta) {
        log.debug("REST request to get PreguntaPreguntaModalidad : {}", idPregunta);
        try {
            List<PreguntaAuthorityDTO> preguntaAuthorityDTO = preguntaAuthorityService.findByPreguntaId(idPregunta);
            return new ResponseEntity<>(preguntaAuthorityDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }


    @GetMapping("/pregunta-authority/{idPregunta}")
    public ResponseEntity<?> getAuthorityPregunta(@PathVariable Long idPregunta) {
        log.debug("REST request to get Pregunta authority : {}", idPregunta);
        try{
            final List<String> DTO = preguntaAuthorityService.findByPreguntaAuthorityPreguntaId(idPregunta);

            return new ResponseEntity<>(DTO, HttpStatus.OK);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }
}
