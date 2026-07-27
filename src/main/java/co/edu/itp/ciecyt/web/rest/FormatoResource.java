package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.FormatoService;
import co.edu.itp.ciecyt.service.dto.FasesDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.FormatoDTO;

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
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Formato}.
 */
@RestController
@RequestMapping("/api")
public class FormatoResource {

    private final Logger log = LoggerFactory.getLogger(FormatoResource.class);

    private static final String ENTITY_NAME = "formato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FormatoService formatoService;

    public FormatoResource(FormatoService formatoService) {
        this.formatoService = formatoService;
    }

    /**
     * {@code POST  /formatoes} : Create a new formato.
     *
     * @param formatoDTO the formatoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new formatoDTO, or with status {@code 400 (Bad Request)} if the formato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/formatoes")
    public ResponseEntity<FormatoDTO> createFormato(@RequestBody FormatoDTO formatoDTO) throws URISyntaxException {
        log.debug("REST request to save Formato : {}", formatoDTO);
        if (formatoDTO.getId() != null) {
            throw new BadRequestAlertException("A new formato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormatoDTO result = formatoService.save(formatoDTO);
        return ResponseEntity.created(new URI("/api/formatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /formatoes} : Updates an existing formato.
     *
     * @param formatoDTO the formatoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated formatoDTO,
     * or with status {@code 400 (Bad Request)} if the formatoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the formatoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/formatoes")
    public ResponseEntity<FormatoDTO> updateFormato(@RequestBody FormatoDTO formatoDTO) throws URISyntaxException {
        log.debug("REST request to update Formato : {}", formatoDTO);
        if (formatoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FormatoDTO result = formatoService.save(formatoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, formatoDTO.getId().toString()))
            .body(result);
    }

    /////////////////////
    @GetMapping("/formato/{codigo}")
    public ResponseEntity<?> getFormatoByCodigo(@PathVariable String codigo) {
        log.debug("REST request to get a page of Fases");
        try {
            final FormatoDTO obj = formatoService.findByCodigo(codigo);
            return new ResponseEntity<>(obj, HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( e.getMessage());
        }
    }

    /**
     * {@code GET  /formatoes} : get all the formatoes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of formatoes in body.
     */
    @GetMapping("/formatoes")
    public ResponseEntity<List<FormatoDTO>> getAllFormatoes(Pageable pageable) {
        log.debug("REST request to get a page of Formatoes");
        Page<FormatoDTO> page = formatoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /formatoes/:id} : get the "id" formato.
     *
     * @param id the id of the formatoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the formatoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/formatoes/{id}")
    public ResponseEntity<FormatoDTO> getFormato(@PathVariable Long id) {
        log.debug("REST request to get Formato : {}", id);
        Optional<FormatoDTO> formatoDTO = formatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(formatoDTO);
    }

    /**
     * {@code DELETE  /formatoes/:id} : delete the "id" formato.
     *
     * @param id the id of the formatoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/formatoes/{id}")
    public ResponseEntity<Void> deleteFormato(@PathVariable Long id) {
        log.debug("REST request to delete Formato : {}", id);
        formatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
