package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ElementoModalidadService;
import co.edu.itp.ciecyt.service.dto.ElementoModalidadDTO;
import co.edu.itp.ciecyt.service.dto.ModalidadDTO;
import co.edu.itp.ciecyt.service.dto.PreguntaModalidadDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ElementoModalidad}.
 */
@RestController
@RequestMapping("/api")
public class ElementoModalidadResource {

    private final Logger log = LoggerFactory.getLogger(ElementoModalidadResource.class);

    private static final String ENTITY_NAME = "elementoModalidad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElementoModalidadService elementoModalidadService;

    public ElementoModalidadResource(ElementoModalidadService elementoModalidadService) {
        this.elementoModalidadService = elementoModalidadService;
    }

    /**
     * {@code POST  /elemento-modalidads} : Create a new elementoModalidad.
     *
     * @param elementoModalidadDTO the elementoModalidadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new elementoModalidadDTO, or with status {@code 400 (Bad Request)} if the elementoModalidad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/elemento-modalidads")
    public ResponseEntity<ElementoModalidadDTO> createElementoModalidad(@RequestBody ElementoModalidadDTO elementoModalidadDTO)
        throws URISyntaxException {
        log.debug("REST request to save ElementoModalidad : {}", elementoModalidadDTO);
        if (elementoModalidadDTO.getId() != null) {
            throw new BadRequestAlertException("A new elementoModalidad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ElementoModalidadDTO result = elementoModalidadService.save(elementoModalidadDTO);
        return ResponseEntity
            .created(new URI("/api/elemento-modalidads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /elemento-modalidads} : Updates an existing elementoModalidad.
     *
     * @param elementoModalidadDTO the elementoModalidadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated elementoModalidadDTO,
     * or with status {@code 400 (Bad Request)} if the elementoModalidadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the elementoModalidadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/elemento-modalidads")
    public ResponseEntity<ElementoModalidadDTO> updateElementoModalidad(@RequestBody ElementoModalidadDTO elementoModalidadDTO)
        throws URISyntaxException {
        log.debug("REST request to update ElementoModalidad : {}", elementoModalidadDTO);
        if (elementoModalidadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ElementoModalidadDTO result = elementoModalidadService.save(elementoModalidadDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, elementoModalidadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /elemento-modalidads} : get all the elementoModalidads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elementoModalidads in body.
     */
    @GetMapping("/elemento-modalidads")
    public List<ElementoModalidadDTO> getAllElementoModalidads() {
        log.debug("REST request to get all ElementoModalidads");
        return elementoModalidadService.findAll();
    }

    /**
     * {@code GET  /elemento-modalidads/:id} : get the "id" elementoModalidad.
     *
     * @param id the id of the elementoModalidadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the elementoModalidadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elemento-modalidads/{id}")
    public ResponseEntity<ElementoModalidadDTO> getElementoModalidad(@PathVariable Long id) {
        log.debug("REST request to get ElementoModalidad : {}", id);
        Optional<ElementoModalidadDTO> elementoModalidadDTO = elementoModalidadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(elementoModalidadDTO);
    }

    /**
     * {@code DELETE  /elemento-modalidads/:id} : delete the "id" elementoModalidad.
     *
     * @param id the id of the elementoModalidadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/elemento-modalidads/{id}")
    public ResponseEntity<Void> deleteElementoModalidad(@PathVariable Long id) {
        log.debug("REST request to delete ElementoModalidad : {}", id);
        elementoModalidadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    ////////////////////////////////////////////////////7
    @GetMapping("/elemento-elemento-modalidad/{idModalidad}")
    public ResponseEntity<?> getElementoElementoModalidad(@PathVariable Long idModalidad) {
        log.debug("REST request to get ElementoElementoModalidad : {}", idModalidad);
        try {
            List<ElementoModalidadDTO> elementoModalidadDTO = elementoModalidadService.findByModalidadId(idModalidad);
            return new ResponseEntity<>(elementoModalidadDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    ////////////////////////////////7777
    /*@GetMapping("/elemento-modalidad-elemento/{idElemento}")
    public ResponseEntity<?> getElementoModalidadElemento(@PathVariable Long idElemento) {
        log.debug("REST request to get elemento Modalidad : {}", idElemento);
        try{
            final List<ModalidadDTO> DTO = elementoModalidadService.findByElementoId(idElemento);

            return new ResponseEntity<>(DTO, HttpStatus.OK);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }*/
    //////////////////////////////////////////7


    //trar una lista de elementoModalidad por idElemento*
    @GetMapping("/elemento-modalidad-elementoid/{idElemento}")
    public ResponseEntity<?> getElementoModalidadIdElemento(@PathVariable Long idElemento) {
        log.debug("REST request to get ElementoElementoModalidad : {}", idElemento);
        try {
            List<ModalidadDTO> elementoModalidadDTO = elementoModalidadService.findByElementoId(idElemento);
            return new ResponseEntity<>(elementoModalidadDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }
}
