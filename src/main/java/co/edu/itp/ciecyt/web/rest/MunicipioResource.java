package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.MunicipioService;
import co.edu.itp.ciecyt.service.dto.MunicipioDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Municipio}.
 */
@RestController
@RequestMapping("/api")
public class MunicipioResource {

    private final Logger log = LoggerFactory.getLogger(MunicipioResource.class);

    private static final String ENTITY_NAME = "municipio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MunicipioService municipioService;

    public MunicipioResource(MunicipioService municipioService) {
        this.municipioService = municipioService;
    }

    /**
     * {@code POST  /municipios} : Create a new municipio.
     *
     * @param municipioDTO the municipioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new municipioDTO, or with status {@code 400 (Bad Request)} if the municipio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/municipios")
    public ResponseEntity<MunicipioDTO> createMunicipio(@RequestBody MunicipioDTO municipioDTO) throws URISyntaxException {
        log.debug("REST request to save Municipio : {}", municipioDTO);
        if (municipioDTO.getId() != null) {
            throw new BadRequestAlertException("A new municipio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MunicipioDTO result = municipioService.save(municipioDTO);
        return ResponseEntity
            .created(new URI("/api/municipios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /municipios} : Updates an existing municipio.
     *
     * @param municipioDTO the municipioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated municipioDTO,
     * or with status {@code 400 (Bad Request)} if the municipioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the municipioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/municipios")
    public ResponseEntity<MunicipioDTO> updateMunicipio(@RequestBody MunicipioDTO municipioDTO) throws URISyntaxException {
        log.debug("REST request to update Municipio : {}", municipioDTO);
        if (municipioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MunicipioDTO result = municipioService.save(municipioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, municipioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /municipios} : get all the municipios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of municipios in body.
     */
    @GetMapping("/municipios")
    public ResponseEntity<List<MunicipioDTO>> getAllMunicipios(Pageable pageable) {
        log.debug("REST request to get a page of Municipios");
        Page<MunicipioDTO> page = municipioService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    ///////////////////7777
    @GetMapping("/municipios-no-page")
    public ResponseEntity<?> getAllMunicipioNoPage() throws Exception {
        log.debug("REST request to get a page of Municipio");

        try {
            List<MunicipioDTO> mDTOS = municipioService.findAllNoPage();

            ResponseEntity<MunicipioDTO> responseEntity = new ResponseEntity(mDTOS, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    ///////////////////7777
    /////////////////////77

    ///////////////////7777
    @GetMapping("/municipios-departamento/{departamento}")
    public ResponseEntity<?> getMunicipioDpto(@PathVariable String departamento) throws Exception {
        log.debug("REST request to get a page of Municipio");

        try {
            List<MunicipioDTO> mDTOS = municipioService.findByDepartamento(departamento);

            ResponseEntity<MunicipioDTO> responseEntity = new ResponseEntity(mDTOS, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /////////////////////77

    /**
     * {@code GET  /municipios/:id} : get the "id" municipio.
     *
     * @param id the id of the municipioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the municipioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/municipios/{id}")
    public ResponseEntity<MunicipioDTO> getMunicipio(@PathVariable Long id) {
        log.debug("REST request to get Municipio : {}", id);
        Optional<MunicipioDTO> municipioDTO = municipioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(municipioDTO);
    }

    /**
     * {@code DELETE  /municipios/:id} : delete the "id" municipio.
     *
     * @param id the id of the municipioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/municipios/{id}")
    public ResponseEntity<Void> deleteMunicipio(@PathVariable Long id) {
        log.debug("REST request to delete Municipio : {}", id);
        municipioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
