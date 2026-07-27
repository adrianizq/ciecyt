package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.Proyecto;
import co.edu.itp.ciecyt.repository.ProyectoRepository;
import co.edu.itp.ciecyt.service.ProyectoService;
//import co.edu.itp.ciecyt.service.ReportService;
import co.edu.itp.ciecyt.service.dto.ProyectoDTO;
import co.edu.itp.ciecyt.service.mapper.AdjuntoProyectoFaseMapper;
import co.edu.itp.ciecyt.service.mapper.ProyectoMapper;
import co.edu.itp.ciecyt.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
//import net.sf.jasperreports.engine.JRException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.Proyecto}.
 */
@RestController
@RequestMapping("/api")
public class ProyectoResource {



    private final Logger log = LoggerFactory.getLogger(ProyectoResource.class);

    private static final String ENTITY_NAME = "proyecto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProyectoService proyectoService;
    private final ProyectoRepository proyectoRepository;
    private ProyectoMapper proyectoMapper;
    //private final ReportService reportService;

    //private final IntegranteProyectoService integranteProyectoService;

    public ProyectoResource(ProyectoService proyectoService,
                            ProyectoRepository proyectoRepository,
                            ProyectoMapper proyectoMapper) {

        this.proyectoService = proyectoService;

        this.proyectoRepository = proyectoRepository;

        this.proyectoMapper = proyectoMapper;
    }


    /**
     * {@code POST  /proyectos} : Create a new proyecto.
     *
     * @param proyectoDTO the proyectoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proyectoDTO, or with status {@code 400 (Bad Request)} if the proyecto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proyectos")
    public ResponseEntity<ProyectoDTO> createProyecto(@RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        log.debug("REST request to save Proyecto : {}", proyectoDTO);
        if (proyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new proyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }

        try {
            ProyectoDTO result = proyectoService.save(proyectoDTO);
            return ResponseEntity
                .created(new URI("/api/proyectos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            log.error("Error saving Proyecto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * {@code PUT  /proyectos} : Updates an existing proyecto.
     *
     * @param proyectoDTO the proyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectoDTO,
     * or with status {@code 400 (Bad Request)} if the proyectoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proyectos")
    public ResponseEntity<ProyectoDTO> updateProyecto(@RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        log.debug("REST request to update Proyecto : {}", proyectoDTO);
        if (proyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        try {
            ProyectoDTO result = proyectoService.save(proyectoDTO);
            return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyectoDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            log.error("Error updating Proyecto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * {@code GET  /proyectos} : get all the proyectos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proyectos in body.
     */
    @GetMapping("/proyectos")
    public ResponseEntity<List<ProyectoDTO>> getAllProyectos(Pageable pageable) {
        log.debug("REST request to get a page of Proyectos");
        Page<ProyectoDTO> page = proyectoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    //ADR Retorna todos los protectos con una lista de los intgrantes
    @GetMapping("/proyectosIntegrantes")
    public ResponseEntity<List<ProyectoDTO>> getAllProyectosIntegrantes() throws Exception {
        log.debug("REST request to get a page of Proyectos");
        List<ProyectoDTO> listDTO = proyectoService.findAllProyectosIntegrantes();
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping("/proyectos/{id}")
    public ResponseEntity<ProyectoDTO> getProyecto(@PathVariable Long id) {
        log.debug("REST request to get Proyecto : {}", id);
        Optional<ProyectoDTO> proyectoDTO = proyectoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(proyectoDTO);
    }

    @GetMapping("/proyectoIntegrantes/{id}")
    public ResponseEntity<ProyectoDTO> getProyectoIntegrante(@PathVariable Long id) throws Exception {
        log.debug("REST request to get Proyecto : {}", id);
        Optional<ProyectoDTO> proyectoDTO = proyectoService.findOneIntegrantes(id);
        return ResponseUtil.wrapOrNotFound(proyectoDTO);
    }

    /** ADR
     * {@code GET  /proyectos/:id} : get the "id" proyecto.
     *
     * @param idProyecto the id of the proyectoDTO to retrieve.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proyectoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proyectosWithAsesor/{idProyecto}")
    public ResponseEntity<?> getProyectoWithAsesor(@PathVariable Long idProyecto) {
        log.debug("REST request to get Proyecto : {}", idProyecto);

        //ProyectoDTO proyectoDTO = proyectoService.findOne(id);

        try {
            // proyectoDTO = proyectoService.findOneWithAsesor(id);

            final ProyectoDTO proyectoDTO = proyectoService.findOneWithAsesor(idProyecto);
            ResponseEntity<ProyectoDTO> responseEntity = new ResponseEntity(proyectoDTO, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * {@code DELETE  /proyectos/:id} : delete the "id" proyecto.
     *
     * @param id the id of the proyectoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proyectos/{id}")
    public ResponseEntity<Void> deleteProyecto(@PathVariable Long id) {
        log.debug("REST request to delete Proyecto : {}", id);
        proyectoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    ///////////////////////////////////////////////////////////////7777777777777777777777777

    @GetMapping("/proyectos-integrante/{idUsuario}")
    public ResponseEntity<?> findByIntegranteProyecto(@PathVariable Long idUsuario) {
        log.debug("REST request to get Proyecto : {}", idUsuario);

        try {
            final List<ProyectoDTO> proyectoDTO = proyectoService.findByIntegranteProyecto(idUsuario);
            ResponseEntity<ProyectoDTO> responseEntity = new ResponseEntity(proyectoDTO, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //////////////////////////////////////////////////////////////////777777777777777777777

    @GetMapping("/proyectos-integrante/{idUsuario}/{authority}")
    public ResponseEntity<?> findByIntegranteProyectoAuthority(@PathVariable Long idUsuario, @PathVariable String authority, Pageable pageable) {
        log.debug("REST request to get Proyecto : {}", idUsuario, authority);

        try {
            final List<ProyectoDTO> proyectoDTO = proyectoService.findByIntegranteProyectoAuthority(idUsuario, authority);
            //ResponseEntity<ProyectoDTO> responseEntity = new ResponseEntity(proyectoDTO, HttpStatus.OK);
            //return responseEntity;
            int totalElements = proyectoDTO.size();
            if (pageable == null) {
                pageable = PageRequest.of(0, 20);
            }
            int fromIndex = pageable.getPageSize() * pageable.getPageNumber();
            int toIndex = pageable.getPageSize() * (pageable.getPageNumber() + 1);
            if (toIndex > totalElements) {
                toIndex = totalElements;
            }
            List<ProyectoDTO> indexObjects = proyectoDTO.subList(fromIndex, toIndex);
            Page<ProyectoDTO> page = new PageImpl<>(indexObjects, pageable, totalElements);

            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


//https://www.programcreek.com/java-api-examples/?class=org.springframework.data.domain.Pageable&method=getPageSize
    @GetMapping("/proyectos-integrante-rol/{idUsuario}/{rol}")
    public ResponseEntity<?> findByIntegranteProyectoRol(@PathVariable Long idUsuario, @PathVariable String rol, Pageable pageable) {
        log.debug("REST request to get Proyecto : {}", idUsuario, rol);

        try {

            final List<ProyectoDTO> proyectoDTO = proyectoService.findByIntegranteProyectoRol(idUsuario, rol);
            int totalElements = proyectoDTO.size();
            if (pageable == null) {
                pageable = PageRequest.of(0, 20);
            }
            int fromIndex = pageable.getPageSize() * pageable.getPageNumber();
            int toIndex = pageable.getPageSize() * (pageable.getPageNumber() + 1);
            if (toIndex > totalElements) {
                toIndex = totalElements;
            }
            List<ProyectoDTO> indexObjects = proyectoDTO.subList(fromIndex, toIndex);
            Page<ProyectoDTO> page = new PageImpl<>(indexObjects, pageable, totalElements);

            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }


    @PostMapping("/proyects")
    //public ResponseEntity<ProyectoDTO> createProyecto(@RequestBody ProyectoDTO proyectoDTO, @RequestBody IntegranteProyectoDTO integranteProyectoDTO) throws URISyntaxException {
    public ResponseEntity<ProyectoDTO> createProyect(@RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        log.debug("REST request to save Proyecto : {}", proyectoDTO);
        if (proyectoDTO.getId() != null) {
            throw new BadRequestAlertException("A new proyecto cannot already have an ID", ENTITY_NAME, "idexists");
        }

        try {
            ProyectoDTO result = proyectoService.save(proyectoDTO);
            return ResponseEntity.created(new URI("/api/proyects/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            log.error("Error saving Proyecto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * {@code PUT  /proyects} : Updates an existing proyecto.
     *
     * @param proyectoDTO the proyectoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proyectoDTO,
     * or with status {@code 400 (Bad Request)} if the proyectoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proyectoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proyects")
    public ResponseEntity<ProyectoDTO> updateProyect(@RequestBody ProyectoDTO proyectoDTO) throws URISyntaxException {
        log.debug("REST request to update Proyecto : {}", proyectoDTO);
        if (proyectoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        try {
            ProyectoDTO result = proyectoService.save(proyectoDTO);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proyectoDTO.getId().toString()))
                .body(result);
        } catch (Exception e) {
            log.error("Error updating Proyecto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/proyectos/{cadBusq}/searchtitulo")
    public ResponseEntity<List<ProyectoDTO>> searchProyectosTitulo(@PathVariable String cadBusq,
                                                               @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Proyectos by search titulo: {}", cadBusq);

        List<Proyecto> proyectoList = new ArrayList<>();

        try {
            List<ProyectoDTO> proyectoDTOList = new ArrayList<>();
            proyectoList = proyectoRepository.findByTituloContainingIgnoreCase (cadBusq);
            //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            //return ResponseEntity.ok().headers(headers).body(page.getContent());
            for (Proyecto l:proyectoList
            ) {
                proyectoDTOList.add(proyectoMapper.toDto(l));
            }


            Page<ProyectoDTO> page = (Page<ProyectoDTO>) toPage(proyectoDTOList,pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    @GetMapping("/proyectos/{idPrograma}/searchprograma")
    public ResponseEntity<List<ProyectoDTO>> searchProyectosPrograma(@PathVariable Long idPrograma,
                                                                   @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Proyectos by search programa: {}", idPrograma);

        List<Proyecto> proyectoList = new ArrayList<>();


        try {
            List<ProyectoDTO> proyectoDTOList = new ArrayList<>();
            proyectoList = proyectoRepository.findByProyectoProgramaId (idPrograma);
            //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            //return ResponseEntity.ok().headers(headers).body(page.getContent());
            for (Proyecto l:proyectoList
            ) {
                proyectoDTOList.add(proyectoMapper.toDto(l));
            }


            Page<ProyectoDTO> page = (Page<ProyectoDTO>) toPage(proyectoDTOList,pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
    }

    public Page<?> toPage(List<?> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if(start > list.size())
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }


}
