package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.ConvocatoriaPasantiaService;
import co.edu.itp.ciecyt.service.dto.ConvocatoriaPasantiaDTO;
import co.edu.itp.ciecyt.service.dto.PostulacionPasantiaDTO;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for convocatorias de pasantia.
 */
@RestController
@RequestMapping("/api")
public class ConvocatoriaPasantiaResource {

    private final Logger log = LoggerFactory.getLogger(ConvocatoriaPasantiaResource.class);
    private final ConvocatoriaPasantiaService service;

    public ConvocatoriaPasantiaResource(ConvocatoriaPasantiaService service) {
        this.service = service;
    }

    @GetMapping("/convocatorias-pasantia")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN', 'DECANO')")
    public ResponseEntity<List<ConvocatoriaPasantiaDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/convocatorias-pasantia/abiertas")
    @PreAuthorize("hasAnyRole('USER', 'ESTUDIANTE', 'CIECYT', 'ADMIN')")
    public ResponseEntity<List<ConvocatoriaPasantiaDTO>> findAbiertas() {
        return ResponseEntity.ok(service.findAbiertas());
    }

    @GetMapping("/convocatorias-pasantia/mis-postulaciones")
    @PreAuthorize("hasAnyRole('USER', 'ESTUDIANTE')")
    public ResponseEntity<List<PostulacionPasantiaDTO>> findMisPostulaciones() {
        return ResponseEntity.ok(service.findMisPostulaciones());
    }

    @GetMapping("/convocatorias-pasantia/{id}/postulaciones")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN', 'DECANO')")
    public ResponseEntity<List<PostulacionPasantiaDTO>> findPostulaciones(@PathVariable Long id) {
        return ResponseEntity.ok(service.findPostulaciones(id));
    }

    @PostMapping("/convocatorias-pasantia")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN')")
    public ResponseEntity<ConvocatoriaPasantiaDTO> create(@RequestBody ConvocatoriaPasantiaDTO dto) {
        log.debug("REST request to save ConvocatoriaPasantia : {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping("/convocatorias-pasantia")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN')")
    public ResponseEntity<ConvocatoriaPasantiaDTO> update(@RequestBody ConvocatoriaPasantiaDTO dto) {
        log.debug("REST request to update ConvocatoriaPasantia : {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PostMapping("/convocatorias-pasantia/{id}/estado")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN')")
    public ResponseEntity<ConvocatoriaPasantiaDTO> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> payload) {
        String estado = payload.get("estado");
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }

    @DeleteMapping("/convocatorias-pasantia/{id}")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/convocatorias-pasantia/{id}/postular")
    @PreAuthorize("hasAnyRole('USER', 'ESTUDIANTE')")
    public ResponseEntity<PostulacionPasantiaDTO> postular(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        Long proyectoId = payload.get("proyectoId") != null ? Long.valueOf(payload.get("proyectoId").toString()) : null;
        String observacion = payload.get("observacion") != null ? payload.get("observacion").toString() : null;
        return ResponseEntity.ok(service.postular(id, proyectoId, observacion));
    }

    @PostMapping("/convocatorias-pasantia/postulaciones/{id}/seleccionar")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN')")
    public ResponseEntity<PostulacionPasantiaDTO> seleccionar(@PathVariable Long id) {
        return ResponseEntity.ok(service.seleccionar(id));
    }

    @PostMapping("/convocatorias-pasantia/postulaciones/{id}/rechazar")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN')")
    public ResponseEntity<PostulacionPasantiaDTO> rechazar(@PathVariable Long id, @RequestBody(required = false) Map<String, Object> payload) {
        String observacion = payload != null && payload.get("observacion") != null ? payload.get("observacion").toString() : null;
        return ResponseEntity.ok(service.rechazar(id, observacion));
    }
}
