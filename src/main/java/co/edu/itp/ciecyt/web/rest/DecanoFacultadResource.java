package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.DecanoFacultad;
import co.edu.itp.ciecyt.service.DecanoFacultadService;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller para la gestion de las decanaturas.
 *
 * A diferencia de la mayoria de recursos del proyecto, este si declara @PreAuthorize: escribir
 * la decanatura de una facultad le concede a alguien acceso a los proyectos de esa facultad,
 * asi que la escritura es solo de administracion. La lectura de la propia facultad si la puede
 * hacer su decano, porque es el dato que necesita para saber si tiene alcance.
 */
@RestController
@RequestMapping("/api")
public class DecanoFacultadResource {

    private final Logger log = LoggerFactory.getLogger(DecanoFacultadResource.class);

    private static final String ENTITY_NAME = "decanoFacultad";

    private final DecanoFacultadService decanoFacultadService;

    public DecanoFacultadResource(DecanoFacultadService decanoFacultadService) {
        this.decanoFacultadService = decanoFacultadService;
    }

    /**
     * Todas las decanaturas. Solo administracion: es un dato transversal de la institucion.
     */
    @GetMapping("/decanos-facultad")
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<DecanoFacultad> getAllDecanoFacultads() {
        log.debug("REST request to get all DecanoFacultad");
        return decanoFacultadService.findAll();
    }

    /**
     * Facultades que el usuario en sesion puede ver como decano. Es el endpoint que usa el menu
     * de decania: si no tiene ninguna facultad asignada devuelve vacio.
     */
    @GetMapping("/decanos-facultad/mis-facultades")
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('DECANO', 'ADMIN', 'CIECYT')")
    public List<Long> getMisFacultades() {
        log.debug("REST request to get the facultades in the current user's scope");
        return decanoFacultadService.facultadIdsDelDecanoActual();
    }

    @GetMapping("/decanos-facultad/{id}")
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DecanoFacultad> getDecanoFacultad(@PathVariable Long id) {
        log.debug("REST request to get DecanoFacultad : {}", id);
        Optional<DecanoFacultad> result = decanoFacultadService.findOne(id);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Historial de decanos de una facultad. La puede consultar el decano vigente de esa misma
     * facultad, porque el Acuerdo exige dejar rastro de quien actuo en cada momento.
     */
    @GetMapping("/decanos-facultad/facultad/{facultadId}")
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN') or @decanoFacultadService.esDecanoDeFacultad(#facultadId)")
    public List<DecanoFacultad> getByFacultad(@PathVariable Long facultadId) {
        log.debug("REST request to get the decanos of facultad : {}", facultadId);
        return decanoFacultadService.findByFacultad(facultadId);
    }

    @PostMapping("/decanos-facultad")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DecanoFacultad> createDecanoFacultad(@RequestBody DecanoFacultad decanoFacultad)
        throws URISyntaxException {
        log.debug("REST request to save DecanoFacultad : {}", decanoFacultad);
        if (decanoFacultad.getId() != null) {
            throw new BadRequestAlertException("A new decanatura cannot already have an id", ENTITY_NAME, "idexists");
        }
        DecanoFacultad result;
        try {
            result = decanoFacultadService.registrar(decanoFacultad);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "regla-decanatura");
        }
        return ResponseEntity
            .created(new URI("/api/decanos-facultad/" + result.getId()))
            .body(result);
    }

    @PutMapping("/decanos-facultad")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DecanoFacultad> updateDecanoFacultad(@RequestBody DecanoFacultad decanoFacultad) {
        log.debug("REST request to update DecanoFacultad : {}", decanoFacultad);
        if (decanoFacultad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!decanoFacultadService.findOne(decanoFacultad.getId()).isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        DecanoFacultad result;
        try {
            result = decanoFacultadService.registrar(decanoFacultad);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "regla-decanatura");
        }
        return ResponseEntity.ok(result);
    }

    /**
     * Cierra la vigencia del decano de la facultad. Separado del update para que el cambio de
     * decano sea siempre una operacion explicita y quede el historico.
     */
    @PostMapping("/decanos-facultad/facultad/{facultadId}/cerrar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<DecanoFacultad> cerrarVigencia(@PathVariable Long facultadId, @RequestBody java.time.LocalDate fechaHasta) {
        log.debug("REST request to close the decano's term in facultad : {}", facultadId);
        try {
            return ResponseEntity.ok(decanoFacultadService.cerrarVigencia(facultadId, fechaHasta));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestAlertException(ex.getMessage(), ENTITY_NAME, "regla-decanatura");
        }
    }

    @DeleteMapping("/decanos-facultad/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteDecanoFacultad(@PathVariable Long id) {
        log.debug("REST request to delete DecanoFacultad : {}", id);
        decanoFacultadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
