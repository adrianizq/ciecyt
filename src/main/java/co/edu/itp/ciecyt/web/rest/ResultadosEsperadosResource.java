package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ResultadosEsperadosService;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.ResultadosEsperadosDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.ResultadosEsperados}.
 */
@RestController
@RequestMapping("/api")
public class ResultadosEsperadosResource {

    private final Logger log = LoggerFactory.getLogger(ResultadosEsperadosResource.class);

    private static final String ENTITY_NAME = "resultadosEsperados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResultadosEsperadosService resultadosEsperadosService;

    public ResultadosEsperadosResource(ResultadosEsperadosService resultadosEsperadosService) {
        this.resultadosEsperadosService = resultadosEsperadosService;
    }

    /**
     * {@code POST  /resultados-esperados} : Create a new resultadosEsperados.
     *
     * @param resultadosEsperadosDTO the resultadosEsperadosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resultadosEsperadosDTO, or with status {@code 400 (Bad Request)} if the resultadosEsperados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resultados-esperados")
    public ResponseEntity<ResultadosEsperadosDTO> createResultadosEsperados(@RequestBody ResultadosEsperadosDTO resultadosEsperadosDTO) throws URISyntaxException {
        log.debug("REST request to save ResultadosEsperados : {}", resultadosEsperadosDTO);
        if (resultadosEsperadosDTO.getId() != null) {
            throw new BadRequestAlertException("A new resultadosEsperados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResultadosEsperadosDTO result = resultadosEsperadosService.save(resultadosEsperadosDTO);
        return ResponseEntity.created(new URI("/api/resultados-esperados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resultados-esperados} : Updates an existing resultadosEsperados.
     *
     * @param resultadosEsperadosDTO the resultadosEsperadosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resultadosEsperadosDTO,
     * or with status {@code 400 (Bad Request)} if the resultadosEsperadosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resultadosEsperadosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resultados-esperados")
    public ResponseEntity<ResultadosEsperadosDTO> updateResultadosEsperados(@RequestBody ResultadosEsperadosDTO resultadosEsperadosDTO) throws URISyntaxException {
        log.debug("REST request to update ResultadosEsperados : {}", resultadosEsperadosDTO);
        if (resultadosEsperadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResultadosEsperadosDTO result = resultadosEsperadosService.save(resultadosEsperadosDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resultadosEsperadosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resultados-esperados} : get all the resultadosEsperados.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resultadosEsperados in body.
     */
    @GetMapping("/resultados-esperados")
    public ResponseEntity<List<ResultadosEsperadosDTO>> getAllResultadosEsperados(Pageable pageable) {
        log.debug("REST request to get a page of ResultadosEsperados");
        Page<ResultadosEsperadosDTO> page = resultadosEsperadosService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resultados-esperados/:id} : get the "id" resultadosEsperados.
     *
     * @param id the id of the resultadosEsperadosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resultadosEsperadosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultados-esperados/{id}")
    public ResponseEntity<ResultadosEsperadosDTO> getResultadosEsperados(@PathVariable Long id) {
        log.debug("REST request to get ResultadosEsperados : {}", id);
        Optional<ResultadosEsperadosDTO> resultadosEsperadosDTO = resultadosEsperadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resultadosEsperadosDTO);
    }

    /**
     * {@code DELETE  /resultados-esperados/:id} : delete the "id" resultadosEsperados.
     *
     * @param id the id of the resultadosEsperadosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resultados-esperados/{id}")
    public ResponseEntity<Void> deleteResultadosEsperados(@PathVariable Long id) {
        log.debug("REST request to delete ResultadosEsperados : {}", id);
        resultadosEsperadosService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }


    /////////////777777777777777777777777777777777777777777777

    /**
     * {@code GET  /integrante-proyectos/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the integranteProyectoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integranteProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resultados-esperados-proyecto/{idProyecto}")
    public ResponseEntity<?> getResultadosEsperadosProyecto(@PathVariable Long idProyecto) {
        log.debug("REST request to get IntegranteProyecto : {}", idProyecto);
        try {
            final List<ResultadosEsperadosDTO> resultadosEsperadosDTO = resultadosEsperadosService.findByResultadosEsperadosProyectoId(idProyecto);
            return new ResponseEntity<>(resultadosEsperadosDTO, HttpStatus.OK);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
