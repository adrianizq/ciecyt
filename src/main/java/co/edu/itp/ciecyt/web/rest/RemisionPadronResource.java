package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.RemisionPadron;
import co.edu.itp.ciecyt.service.ProyectoAutorizacionService;
import co.edu.itp.ciecyt.service.RemisionPadronService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Remision al CIECYT de la relacion de profesores habilitados.
 *
 * <p>El paragrafo 2 del articulo 8 y el del articulo 9 obligan a las decanaturas a remitir esta
 * relacion al inicio de cada periodo academico. Aqui queda constancia de cuando se remitio y de que
 * se remitio: sin esto, tener el padron al dia no demuestra que se cumplio el parrafo.
 */
@RestController
@RequestMapping("/api")
public class RemisionPadronResource {

    private final Logger log = LoggerFactory.getLogger(RemisionPadronResource.class);

    private final RemisionPadronService remisionPadronService;
    private final ProyectoAutorizacionService autorizacion;

    public RemisionPadronResource(RemisionPadronService remisionPadronService, ProyectoAutorizacionService autorizacion) {
        this.remisionPadronService = remisionPadronService;
        this.autorizacion = autorizacion;
    }

    /**
     * Arma un borrador con la lista vigente. Lo puede hacer la decanatura de esa facultad.
     */
    @PostMapping("/remisiones-padron")
    public RemisionPadron crearBorrador(@RequestBody SolicitudRemision solicitud) {
        if (!autorizacion.puedeHabilitarDocentesEn(solicitud.getFacultadId())) {
            throw new AccessDeniedException("No puede remitir el padron de esa facultad");
        }
        log.debug("Peticion de remision del padron de la faculty {}", solicitud.getFacultadId());
        return remisionPadronService.crearBorrador(
            solicitud.getFacultadId(), solicitud.getPeriodo(), solicitud.getObservaciones());
    }

    /**
     * Envia el borrador. Lo unico que cambia despues es el estado; la lista enviada no se edita.
     */
    @PostMapping("/remisiones-padron/{id}/enviar")
    public RemisionPadron enviar(@PathVariable Long id, @RequestParam Long facultadId) {
        if (!autorizacion.puedeHabilitarDocentesEn(facultadId)) {
            throw new AccessDeniedException("No puede remitir el padron de esa facultad");
        }
        return remisionPadronService.enviar(id, facultadId);
    }

    /**
     * Remisiones de una facultad, con sus borradores. Es la vista de la decanatura.
     */
    @GetMapping("/remisiones-padron/facultad/{facultadId}")
    public List<RemisionPadron> getDeFacultad(@PathVariable Long facultadId) {
        if (!autorizacion.puedeVerHabilitadosDe(facultadId)) {
            throw new AccessDeniedException("No puede consultar el padron de esa facultad");
        }
        return remisionPadronService.findPorFacultad(facultadId);
    }

    /**
     * Todas las remisiones ya remitidas de la institucion, que es lo que el CIECYT recibe. Es de
     * solo lectura: el CIECYT consulta, no modifica la lista de ninguna facultad, y ninguna
     * facultad ve la remision de otra.
     */
    @GetMapping("/remisiones-padron")
    public List<RemisionPadron> getRemitidas() {
        if (!autorizacion.esGestorGlobal() && !autorizacion.esAdministrador()) {
            throw new AccessDeniedException("No puede consultar las remisiones de otras facultades");
        }
        return remisionPadronService.findTodasEnviadas();
    }

    public static class SolicitudRemision {
        private Long facultadId;
        private String periodo;
        private String observaciones;

        public Long getFacultadId() { return facultadId; }
        public void setFacultadId(Long facultadId) { this.facultadId = facultadId; }
        public String getPeriodo() { return periodo; }
        public void setPeriodo(String periodo) { this.periodo = periodo; }
        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }
}
