package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.IntegranteProyectoService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.domain.IntegranteProyecto;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.IntegranteProyecto}.
 */
@RestController
@RequestMapping("/api")
public class IntegranteProyectoResource {

    private final Logger log = LoggerFactory.getLogger(IntegranteProyectoResource.class);

    private static final String ENTITY_NAME = "integranteProyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntegranteProyectoService integranteProyectoService;

    public IntegranteProyectoResource(IntegranteProyectoService integranteProyectoService) {
        this.integranteProyectoService = integranteProyectoService;
    }

    /**
     * {@code POST  /integrante-proyectos} : Create a new integranteProyecto.
     *
     * @param integranteProyectoDTO the integranteProyectoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new integranteProyectoDTO, or with status {@code 400 (Bad Request)} if the integranteProyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/integrante-proyectos")
    public ResponseEntity<IntegranteProyectoDTO> createIntegranteProyecto(@RequestBody IntegranteProyectoDTO integranteProyectoDTO) throws URISyntaxException {
        log.debug("REST request to save IntegranteProyecto : {}", integranteProyectoDTO);
        if (integranteProyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new integranteProyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntegranteProyectoDTO result = integranteProyectoService.save(integranteProyectoDTO);
        return ResponseEntity.created(new URI("/api/integrante-proyectos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /integrante-proyectos} : Updates an existing integranteProyecto.
     *
     * @param integranteProyectoDTO the integranteProyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated integranteProyectoDTO,
     * or with status {@code 400 (Bad Request)} if the integranteProyectoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the integranteProyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/integrante-proyectos")
    public ResponseEntity<IntegranteProyectoDTO> updateIntegranteProyecto(@RequestBody IntegranteProyectoDTO integranteProyectoDTO) throws URISyntaxException {
        log.debug("REST request to update IntegranteProyecto : {}", integranteProyectoDTO);
        if (integranteProyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IntegranteProyectoDTO result = integranteProyectoService.save(integranteProyectoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, integranteProyectoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /integrante-proyectos} : get all the integranteProyectos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of integranteProyectos in body.
     */
    @GetMapping("/integrante-proyectos")
    public ResponseEntity<List<IntegranteProyectoDTO>> getAllIntegranteProyectos(Pageable pageable) {
        log.debug("REST request to get a page of IntegranteProyectos");
        Page<IntegranteProyectoDTO> page = integranteProyectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /integrante-proyectos/:id} : get the "id" integranteProyecto.
     *
     * @param id the id of the integranteProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integranteProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integrante-proyectos/{id}")
    public ResponseEntity<IntegranteProyectoDTO> getIntegranteProyecto(@PathVariable Long id) {
        log.debug("REST request to get IntegranteProyecto : {}", id);
        Optional<IntegranteProyectoDTO> integranteProyectoDTO = integranteProyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(integranteProyectoDTO);
    }

    /**
     * {@code DELETE  /integrante-proyectos/:id} : delete the "id" integranteProyecto.
     *
     * @param id the id of the integranteProyectoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/integrante-proyectos/{id}")
    public ResponseEntity<Void> deleteIntegranteProyecto(@PathVariable Long id) {
        log.debug("REST request to delete IntegranteProyecto : {}", id);
        integranteProyectoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }




    /////////////777777777777777777777777777777777777777777777
    /**
     * {@code GET  /integrante-proyectos/:id} : get the "id" idProyecto.
     *
     * @param id the id of the integranteProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integranteProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integrante-proyectos-proyecto/{idProyecto}")
    public ResponseEntity<?> getIntegranteProyectosProyecto(@PathVariable Long idProyecto) {
        log.debug("REST request to get IntegranteProyecto : {}", idProyecto);
        try{
        final List<IntegranteProyectoDTO> integranteProyectoDTO = integranteProyectoService.findByIntegranteProyectoProyectoId(idProyecto);
        return new ResponseEntity<>(integranteProyectoDTO, HttpStatus.OK);

    }catch (Exception e){

          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
      }
    }




     /**
     * {@code GET  /integrante-proyectos/:id} : get the "id" idProyecto.
     *
     * @param id the id of the integranteProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integranteProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integrante-proyectos-proyecto-modalidad/{idProyecto}/{idRolModalidad}")
    public ResponseEntity<?> findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(@PathVariable Long idProyecto, @PathVariable Long idRolModalidad) {
        log.debug("REST request to get IntegranteProyecto : {}", idProyecto);
        try{
        final List<IntegranteProyectoDTO> integranteProyectoDTO = integranteProyectoService.findByIntegranteProyectoProyectoIdAndIntegranteProyectoRolesModalidadId(idProyecto,idRolModalidad);
        return new ResponseEntity<>(integranteProyectoDTO, HttpStatus.OK);

    }catch (Exception e){

          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
      }
    }
    ////////////////////////////////////////////////////////777


    /////////////777777777777777777777777777777777777777777777
    /**
     * {@code GET  /integrante-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the integranteProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integranteProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/integrante-proyectos-estudiantes/{idProyecto}")
    public ResponseEntity<?> findEstudiantesIntegranteProyectoId(@PathVariable Long idProyecto) {
        log.debug("REST request to get IntegranteProyecto : {}", idProyecto);
        try{
            final List<IntegranteProyectoDTO> dto = integranteProyectoService.findEstudiantesIntegranteProyectoId(idProyecto);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }


    @GetMapping("/integrante-proyectos-jurados/{idProyecto}/{tipoJurado}")
    public ResponseEntity<?> findJuradosIntegranteProyectoId(@PathVariable Long idProyecto, @PathVariable String tipoJurado) {
        log.debug("REST request to get IntegranteProyecto : {}", idProyecto);
        try{
            final List<IntegranteProyectoDTO> dto = integranteProyectoService.findJuradosIntegranteProyectoId(idProyecto,tipoJurado);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }

    @GetMapping("/integrante-proyectos-asesores/{idProyecto}")
    public ResponseEntity<?> findAsesoresIntegranteProyectoId(@PathVariable Long idProyecto) {
        log.debug("REST request to get IntegranteProyecto : {}", idProyecto);
        try{
            final List<IntegranteProyectoDTO> dto = integranteProyectoService.findAsesoresIntegranteProyectoId(idProyecto);
            return new ResponseEntity<>(dto, HttpStatus.OK);

        }catch (Exception e){

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }


}
