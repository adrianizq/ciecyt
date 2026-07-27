package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.TipoPreguntaService;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.TipoPreguntaDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.TipoPregunta}.
 */
@RestController
@RequestMapping("/api")
public class TipoPreguntaResource {

    private final Logger log = LoggerFactory.getLogger(TipoPreguntaResource.class);

    private static final String ENTITY_NAME = "tipoPregunta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoPreguntaService tipoPreguntaService;

    public TipoPreguntaResource(TipoPreguntaService tipoPreguntaService) {
        this.tipoPreguntaService = tipoPreguntaService;
    }

    /**
     * {@code POST  /tipo-preguntas} : Create a new tipoPregunta.
     *
     * @param tipoPreguntaDTO the tipoPreguntaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoPreguntaDTO, or with status {@code 400 (Bad Request)} if the tipoPregunta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-preguntas")
    public ResponseEntity<TipoPreguntaDTO> createTipoPregunta(@RequestBody TipoPreguntaDTO tipoPreguntaDTO) throws URISyntaxException {
        log.debug("REST request to save TipoPregunta : {}", tipoPreguntaDTO);
        if (tipoPreguntaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoPregunta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPreguntaDTO result = tipoPreguntaService.save(tipoPreguntaDTO);
        return ResponseEntity.created(new URI("/api/tipo-preguntas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-preguntas} : Updates an existing tipoPregunta.
     *
     * @param tipoPreguntaDTO the tipoPreguntaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPreguntaDTO,
     * or with status {@code 400 (Bad Request)} if the tipoPreguntaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoPreguntaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-preguntas")
    public ResponseEntity<TipoPreguntaDTO> updateTipoPregunta(@RequestBody TipoPreguntaDTO tipoPreguntaDTO) throws URISyntaxException {
        log.debug("REST request to update TipoPregunta : {}", tipoPreguntaDTO);
        if (tipoPreguntaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoPreguntaDTO result = tipoPreguntaService.save(tipoPreguntaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPreguntaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-preguntas} : get all the tipoPreguntas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoPreguntas in body.
     */
    @GetMapping("/tipo-preguntas")
    public ResponseEntity<List<TipoPreguntaDTO>> getAllTipoPreguntas(Pageable pageable) {
        log.debug("REST request to get a page of TipoPreguntas");
        Page<TipoPreguntaDTO> page = tipoPreguntaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-preguntas/:id} : get the "id" tipoPregunta.
     *
     * @param id the id of the tipoPreguntaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoPreguntaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-preguntas/{id}")
    public ResponseEntity<TipoPreguntaDTO> getTipoPregunta(@PathVariable Long id) {
        log.debug("REST request to get TipoPregunta : {}", id);
        Optional<TipoPreguntaDTO> tipoPreguntaDTO = tipoPreguntaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoPreguntaDTO);
    }

    /**
     * {@code DELETE  /tipo-preguntas/:id} : delete the "id" tipoPregunta.
     *
     * @param id the id of the tipoPreguntaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-preguntas/{id}")
    public ResponseEntity<Void> deleteTipoPregunta(@PathVariable Long id) {
        log.debug("REST request to delete TipoPregunta : {}", id);
        tipoPreguntaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
