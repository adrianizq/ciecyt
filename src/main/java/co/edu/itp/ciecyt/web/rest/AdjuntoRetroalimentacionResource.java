package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.AdjuntoRetroalimentacionService;
import co.edu.itp.ciecyt.service.dto.AdjuntoProyectoFaseDTO;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.service.dto.AdjuntoRetroalimentacionDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.AdjuntoRetroalimentacion}.
 */
@RestController
@RequestMapping("/api")
public class AdjuntoRetroalimentacionResource {

    private final Logger log = LoggerFactory.getLogger(AdjuntoRetroalimentacionResource.class);

    private static final String ENTITY_NAME = "adjuntoRetroalimentacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdjuntoRetroalimentacionService adjuntoRetroalimentacionService;

    public AdjuntoRetroalimentacionResource(AdjuntoRetroalimentacionService adjuntoRetroalimentacionService) {
        this.adjuntoRetroalimentacionService = adjuntoRetroalimentacionService;
    }

    /**
     * {@code POST  /adjunto-retroalimentacions} : Create a new adjuntoRetroalimentacion.
     *
     * @param adjuntoRetroalimentacionDTO the adjuntoRetroalimentacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new adjuntoRetroalimentacionDTO, or with status {@code 400 (Bad Request)} if the adjuntoRetroalimentacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/adjunto-retroalimentacions")
    public ResponseEntity<AdjuntoRetroalimentacionDTO> createAdjuntoRetroalimentacion(@RequestBody AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO) throws URISyntaxException {
        log.debug("REST request to save AdjuntoRetroalimentacion : {}", adjuntoRetroalimentacionDTO);
        if (adjuntoRetroalimentacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new adjuntoRetroalimentacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdjuntoRetroalimentacionDTO result = adjuntoRetroalimentacionService.save(adjuntoRetroalimentacionDTO);

        byte[] file = adjuntoRetroalimentacionDTO.getArchivo();
        if (file != null) {
            adjuntoRetroalimentacionService.attachFile(result, file, adjuntoRetroalimentacionDTO.getArchivoContentType());
        }
        return ResponseEntity.created(new URI("/api/adjunto-retroalimentacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /adjunto-retroalimentacions} : Updates an existing adjuntoRetroalimentacion.
     *
     * @param adjuntoRetroalimentacionDTO the adjuntoRetroalimentacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated adjuntoRetroalimentacionDTO,
     * or with status {@code 400 (Bad Request)} if the adjuntoRetroalimentacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the adjuntoRetroalimentacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/adjunto-retroalimentacions")
    public ResponseEntity<AdjuntoRetroalimentacionDTO> updateAdjuntoRetroalimentacion(@RequestBody AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO) throws URISyntaxException {
        log.debug("REST request to update AdjuntoRetroalimentacion : {}", adjuntoRetroalimentacionDTO);
        if (adjuntoRetroalimentacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AdjuntoRetroalimentacionDTO result = adjuntoRetroalimentacionService.save(adjuntoRetroalimentacionDTO);

        //Guarda el adjunto del proyecto

        byte[] file = adjuntoRetroalimentacionDTO.getArchivo();
        if (file != null) {
            adjuntoRetroalimentacionService.attachFile(result, file, adjuntoRetroalimentacionDTO.getArchivoContentType());
        }


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, adjuntoRetroalimentacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /adjunto-retroalimentacions} : get all the adjuntoRetroalimentacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of adjuntoRetroalimentacions in body.
     */
    @GetMapping("/adjunto-retroalimentacions")
    public ResponseEntity<List<AdjuntoRetroalimentacionDTO>> getAllAdjuntoRetroalimentacions(Pageable pageable) {
        log.debug("REST request to get a page of AdjuntoRetroalimentacions");
        Page<AdjuntoRetroalimentacionDTO> page = adjuntoRetroalimentacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /adjunto-retroalimentacions/:id} : get the "id" adjuntoRetroalimentacion.
     *
     * @param id the id of the adjuntoRetroalimentacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adjuntoRetroalimentacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjunto-retroalimentacions/{id}")
    public ResponseEntity<AdjuntoRetroalimentacionDTO> getAdjuntoRetroalimentacion(@PathVariable Long id) {
        log.debug("REST request to get AdjuntoRetroalimentacion : {}", id);
        Optional<AdjuntoRetroalimentacionDTO> adjuntoRetroalimentacionDTO = adjuntoRetroalimentacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(adjuntoRetroalimentacionDTO);
    }

    /**
     * {@code DELETE  /adjunto-retroalimentacions/:id} : delete the "id" adjuntoRetroalimentacion.
     *
     * @param id the id of the adjuntoRetroalimentacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/adjunto-retroalimentacions/{id}")
    public ResponseEntity<Void> deleteAdjuntoRetroalimentacion(@PathVariable Long id) {
        log.debug("REST request to delete AdjuntoRetroalimentacion : {}", id);
        adjuntoRetroalimentacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code DOWNLOAD  /adjunto-retroalimentacions/downloadFile/{id}} : download the "id" adjuntoRetroalimentacion.
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/adjunto-retroalimentacions/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id, HttpServletRequest request) {

        log.debug("Download AdjuntoProyectoFase : {}", id);
        Optional<AdjuntoRetroalimentacionDTO> adjunto = adjuntoRetroalimentacionService.findOne(id);

        Resource resource = null;

        if(adjunto.isPresent()) {

            try {
                resource = adjuntoRetroalimentacionService.loadFileAsResource( adjunto.get() );
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

    /**
     * {@code GET  /adjunto-retroalimentacions/:id} : get the "id" idProyecto.
     *
     * @param idProyecto the id of the adjuntoProyectoFaseDTO to retrieve.
     * @param idFase the id of the adjuntoProyectoFaseDTO to retrieve.
     * @param authority the id of the adjuntoProyectoFaseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the integranteProyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/adjunto-retroalimentacions/traer/{idProyecto}/{idFase}/{authority}")
    public ResponseEntity<?> findByAdjuntoRetroalimentacionProyectoIdAndAdjuntoRetroalimentacionFaseIdAndAuthority(
        @PathVariable Long idProyecto,
        @PathVariable Long idFase,
        @PathVariable String authority
    ) {
        log.debug("REST request to get AdjuntoRetroalimentacion : {}", idProyecto, idFase, authority);
        try {
            final List<AdjuntoRetroalimentacionDTO> adjuntoRetroalimentacionDTO = adjuntoRetroalimentacionService.findByAdjuntoRetroalimentacionProyectoIdAndAdjuntoRetroalimentacionFaseIdAndAuthority(
                idProyecto,
                idFase,
                authority
            );
            return new ResponseEntity<>(adjuntoRetroalimentacionDTO, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
