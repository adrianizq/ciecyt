package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.AdjuntoProyectoFaseService;
import co.edu.itp.ciecyt.service.dto.AdjuntoProyectoFaseDTO;
import co.edu.itp.ciecyt.service.dto.IntegranteProyectoDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.AdjuntoProyectoFase}.
 */
@RestController
@RequestMapping("/api")
public class AdjuntoProyectoFaseResource {

    private final Logger log = LoggerFactory.getLogger(AdjuntoProyectoFaseResource.class);

    private static final String ENTITY_NAME = "adjuntoProyectoFase";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjuntoProyectoFaseService adjuntoProyectoFaseService;

    public AdjuntoProyectoFaseResource(AdjuntoProyectoFaseService adjuntoProyectoFaseService) {
        this.adjuntoProyectoFaseService = adjuntoProyectoFaseService;
    }

    /**
     * {@code POST  /adjunto-proyecto-fases} : Create a new adjuntoProyectoFase.
     *
     * @param adjuntoProyectoFaseDTO the adjuntoProyectoFaseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjuntoProyectoFaseDTO, or with status {@code 400 (Bad Request)} if the adjuntoProyectoFase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjunto-proyecto-fases")
    public ResponseEntity<AdjuntoProyectoFaseDTO> createAdjuntoProyectoFase(@RequestBody AdjuntoProyectoFaseDTO adjuntoProyectoFaseDTO) throws URISyntaxException {
        log.debug("REST request to save AdjuntoProyectoFase : {}", adjuntoProyectoFaseDTO);
        if (adjuntoProyectoFaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new adjuntoProyectoFase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjuntoProyectoFaseDTO result = adjuntoProyectoFaseService.save(adjuntoProyectoFaseDTO);

        //Guarda el adjunto del proyecto

        byte[] file = adjuntoProyectoFaseDTO.getArchivo();
        if (file != null) {
            adjuntoProyectoFaseService.attachFile(result, file, adjuntoProyectoFaseDTO.getArchivoContentType());
        }

        return ResponseEntity.created(new URI("/api/adjunto-proyecto-fases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    /**
     * {@code PUT  /adjunto-proyecto-fases} : Updates an existing adjuntoProyectoFase.
     *
     * @param adjuntoProyectoFaseDTO the adjuntoProyectoFaseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjuntoProyectoFaseDTO,
     * or with status {@code 400 (Bad Request)} if the adjuntoProyectoFaseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjuntoProyectoFaseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjunto-proyecto-fases")
    public ResponseEntity<AdjuntoProyectoFaseDTO> updateAdjuntoProyectoFase(@RequestBody AdjuntoProyectoFaseDTO adjuntoProyectoFaseDTO) throws URISyntaxException {
        log.debug("REST request to update AdjuntoProyectoFase : {}", adjuntoProyectoFaseDTO);
        if (adjuntoProyectoFaseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdjuntoProyectoFaseDTO result = adjuntoProyectoFaseService.save(adjuntoProyectoFaseDTO);

        //Guarda el adjunto del proyecto

        byte[] file = adjuntoProyectoFaseDTO.getArchivo();
        if (file != null) {
            adjuntoProyectoFaseService.attachFile(result, file, adjuntoProyectoFaseDTO.getArchivoContentType());
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjuntoProyectoFaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /adjunto-proyecto-fases} : get all the adjuntoProyectoFases.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjuntoProyectoFases in body.
     */
    @GetMapping("/adjunto-proyecto-fases")
    public ResponseEntity<List<AdjuntoProyectoFaseDTO>> getAllAdjuntoProyectoFases(Pageable pageable) {
        log.debug("REST request to get a page of AdjuntoProyectoFases");
        Page<AdjuntoProyectoFaseDTO> page = adjuntoProyectoFaseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /adjunto-proyecto-fases/:id} : get the "id" adjuntoProyectoFase.
     *
     * @param id the id of the adjuntoProyectoFaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjuntoProyectoFaseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjunto-proyecto-fases/{id}")
    public ResponseEntity<AdjuntoProyectoFaseDTO> getAdjuntoProyectoFase(@PathVariable Long id) {
        log.debug("REST request to get AdjuntoProyectoFase : {}", id);
        Optional<AdjuntoProyectoFaseDTO> adjuntoProyectoFaseDTO = adjuntoProyectoFaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adjuntoProyectoFaseDTO);
    }


    /**
     * {@code DELETE  /adjunto-proyecto-fases/:id} : delete the "id" adjuntoProyectoFase.
     *
     * @param id the id of the adjuntoProyectoFaseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjunto-proyecto-fases/{id}")
    public ResponseEntity<Void> deleteAdjuntoProyectoFase(@PathVariable Long id) {
        log.debug("REST request to delete AdjuntoProyectoFase : {}", id);
        adjuntoProyectoFaseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code DOWNLOAD  /adjunto-proyecto-fases/downloadFile/{id}} : download the "id" adjuntoProyectoFase.
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/adjunto-proyecto-fases/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpServletRequest request) {

        log.debug("Download AdjuntoProyectoFase : {}", id);
        Optional<AdjuntoProyectoFaseDTO> adjunto = adjuntoProyectoFaseService.findOne(id);

        Resource resource = null;

        if(adjunto.isPresent()) {

            try {
                resource = adjuntoProyectoFaseService.loadFileAsResource( adjunto.get() );
            } catch (Exception e) {
                log.error("Error cargando archivo: {} ", adjunto.get(), e);
            }
            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                log.info("Could not determine file type: {}", adjunto.get(), ex);
            }

            // Fallback to the default content type if type could not be determined

            if(contentType == null) {
                contentType = "application/octet-stream";
            }


            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);

        } else {

            return ResponseEntity.notFound().build();

        }

    }

    ////////////////////////////////////////////////////
    /**
     * {@code GET  /adjunto-proyecto-fases/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the adjuntoProyectoFaseDTO to retrieve.
     * @param idFase the id of the adjuntoProyectoFaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integranteProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjunto-proyecto-fases/traer/{idProyecto}/{idFase}")
    public ResponseEntity<?> findByProyectoFaseProyectoIdAndAdjuntoProyectoFaseFaseId(
        @PathVariable Long idProyecto,
        @PathVariable Long idFase
    ) {
        log.debug("REST request to get AdjuntoProyectoFase : {}", idProyecto, idFase);
        try {
            final List<AdjuntoProyectoFaseDTO> adjuntoProyectoFaseDTO = adjuntoProyectoFaseService.findByProyectoFaseProyectoIdAndAdjuntoProyectoFaseFaseId(
                idProyecto,
                idFase
            );
            return new ResponseEntity<>(adjuntoProyectoFaseDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
