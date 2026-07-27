package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ProyectoRespuestasService;
import co.edu.itp.ciecyt.service.dto.ElementoProyectoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.ProyectoRespuestasDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ProyectoRespuestas}.
 */
@RestController
@RequestMapping("/api")
public class ProyectoRespuestasResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoRespuestasResource.class);

    private static final String ENTITY_NAME = "proyectoRespuestas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyectoRespuestasService proyectoRespuestasService;

    public ProyectoRespuestasResource(ProyectoRespuestasService proyectoRespuestasService) {
        this.proyectoRespuestasService = proyectoRespuestasService;
    }

    /**
     * {@code POST  /proyecto-respuestas} : Create a new proyectoRespuestas.
     *
     * @param proyectoRespuestasDTO the proyectoRespuestasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proyectoRespuestasDTO, or with status {@code 400 (Bad Request)} if the proyectoRespuestas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proyecto-respuestas")
    public ResponseEntity<ProyectoRespuestasDTO> createProyectoRespuestas(@RequestBody ProyectoRespuestasDTO proyectoRespuestasDTO) throws URISyntaxException {
        log.debug("REST request to save ProyectoRespuestas : {}", proyectoRespuestasDTO);
        if (proyectoRespuestasDTO.getId() != null) {
            throw new BadRequestAlertException("A new proyectoRespuestas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProyectoRespuestasDTO result = proyectoRespuestasService.save(proyectoRespuestasDTO);
        return ResponseEntity.created(new URI("/api/proyecto-respuestas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proyecto-respuestas} : Updates an existing proyectoRespuestas.
     *
     * @param proyectoRespuestasDTO the proyectoRespuestasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectoRespuestasDTO,
     * or with status {@code 400 (Bad Request)} if the proyectoRespuestasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proyectoRespuestasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proyecto-respuestas")
    public ResponseEntity<ProyectoRespuestasDTO> updateProyectoRespuestas(@RequestBody ProyectoRespuestasDTO proyectoRespuestasDTO) throws URISyntaxException {
        log.debug("REST request to update ProyectoRespuestas : {}", proyectoRespuestasDTO);
        if (proyectoRespuestasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProyectoRespuestasDTO result = proyectoRespuestasService.save(proyectoRespuestasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyectoRespuestasDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proyecto-respuestas} : get all the proyectoRespuestas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyectoRespuestas in body.
     */
    @GetMapping("/proyecto-respuestas")
    public ResponseEntity<List<ProyectoRespuestasDTO>> getAllProyectoRespuestas(Pageable pageable) {
        log.debug("REST request to get a page of ProyectoRespuestas");
        Page<ProyectoRespuestasDTO> page = proyectoRespuestasService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /proyecto-respuestas/:id} : get the "id" proyectoRespuestas.
     *
     * @param id the id of the proyectoRespuestasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectoRespuestasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyecto-respuestas/{id}")
    public ResponseEntity<ProyectoRespuestasDTO> getProyectoRespuestas(@PathVariable Long id) {
        log.debug("REST request to get ProyectoRespuestas : {}", id);
        Optional<ProyectoRespuestasDTO> proyectoRespuestasDTO = proyectoRespuestasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proyectoRespuestasDTO);
    }

    /**
     * {@code DELETE  /proyecto-respuestas/:id} : delete the "id" proyectoRespuestas.
     *
     * @param id the id of the proyectoRespuestasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proyecto-respuestas/{id}")
    public ResponseEntity<Void> deleteProyectoRespuestas(@PathVariable Long id) {
        log.debug("REST request to delete ProyectoRespuestas : {}", id);
        proyectoRespuestasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    /////////////777777777777777777777777777777777777777777777
    //se reemplaza con el metodo siguiente a este

    /**
     * {@code GET  /proyecto-respuestas-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the proyectoRespuestasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectoRespuestasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyecto-respuestas-proyecto/{idProyecto}")
    public ResponseEntity<?> getProyectoRespuestasProyecto(@PathVariable Long idProyecto) {
        log.debug("REST request to get ProyectoRespuestasProyecto : {}", idProyecto);
        try {
            final List<ProyectoRespuestasDTO> proyectoRespuestasDTO = proyectoRespuestasService.findByProyectoRespuestasProyectoId(idProyecto);
            return new ResponseEntity<>(proyectoRespuestasDTO, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * {@code GET   : get the "id" idProyecto.
     *
     * @param idProyecto the id of the proyectoRespuestasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectoRespuestasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyecto-respuestas-proyecto-fase-authority/{idProyecto}/{idFase}/{authority}")
    public ResponseEntity<?> getProyectoRespuestasProyectoFaseAuthority(@PathVariable Long idProyecto, @PathVariable Long idFase,@PathVariable String authority) {
        log.debug("REST request to get ProyectoRespuestasProyecto : {}", idProyecto);
        try {
            final List<ProyectoRespuestasDTO> proyectoRespuestasDTO = proyectoRespuestasService.findByProyectoRespuestasProyectoIdFaseAuthority(idProyecto,idFase,authority);
            return new ResponseEntity<>(proyectoRespuestasDTO, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
