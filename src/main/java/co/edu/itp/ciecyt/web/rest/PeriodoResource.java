package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.Periodo;
import co.edu.itp.ciecyt.errors.BadRequestAlertException;
import co.edu.itp.ciecyt.repository.PeriodoRepository;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Catalogo de periodos academicos.
 */
@RestController
@RequestMapping("/api")
public class PeriodoResource {

    private static final String ENTITY_NAME = "periodo";
    private final Logger log = LoggerFactory.getLogger(PeriodoResource.class);
    private final PeriodoRepository periodoRepository;

    public PeriodoResource(PeriodoRepository periodoRepository) {
        this.periodoRepository = periodoRepository;
    }

    @GetMapping("/periodos")
    @PreAuthorize("hasAnyRole('ADMIN', 'DECANO', 'CIECYT')")
    public List<Periodo> getAll() {
        return periodoRepository.findAllByOrderByNombreDesc();
    }

    @GetMapping("/periodos/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DECANO', 'CIECYT')")
    public ResponseEntity<Periodo> get(@PathVariable Long id) {
        return periodoRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/periodos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Periodo> create(@RequestBody Periodo periodo) throws URISyntaxException {
        if (periodo.getId() != null) {
            throw new BadRequestAlertException("Un periodo nuevo no puede tener id", ENTITY_NAME, "idexists");
        }
        if (periodo.getNombre() == null || periodo.getNombre().trim().isEmpty()) {
            throw new BadRequestAlertException("Indique el nombre del periodo", ENTITY_NAME, "nombrerequerido");
        }
        Periodo saved = periodoRepository.save(periodo);
        return ResponseEntity.created(new URI("/api/periodos/" + saved.getId())).body(saved);
    }

    @PutMapping("/periodos")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Periodo> update(@RequestBody Periodo periodo) {
        if (periodo.getId() == null) {
            throw new BadRequestAlertException("Periodo sin id", ENTITY_NAME, "idnull");
        }
        if (periodo.getNombre() == null || periodo.getNombre().trim().isEmpty()) {
            throw new BadRequestAlertException("Indique el nombre del periodo", ENTITY_NAME, "nombrerequerido");
        }
        Periodo saved = periodoRepository.save(periodo);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/periodos/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Periodo : {}", id);
        periodoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
