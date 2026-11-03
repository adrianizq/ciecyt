package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.service.PlazoNotificacionService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ejecuta manualmente la revision de plazos del Acuerdo 025.
 */
@RestController
@RequestMapping("/api")
public class PlazoNotificacionResource {

    private final PlazoNotificacionService plazoNotificacionService;

    public PlazoNotificacionResource(PlazoNotificacionService plazoNotificacionService) {
        this.plazoNotificacionService = plazoNotificacionService;
    }

    @PostMapping("/plazos/verificar")
    @PreAuthorize("hasAnyRole('CIECYT', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> verificarPlazos() {
        int creadas = plazoNotificacionService.verificarPlazos();
        return ResponseEntity.ok(Map.of("notificacionesCreadas", creadas));
    }
}
