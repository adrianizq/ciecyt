package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.DocenteHabilitado;
import co.edu.itp.ciecyt.service.DocenteHabilitadoService;
import co.edu.itp.ciecyt.service.ProyectoAutorizacionService;
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

import java.time.LocalDate;
import java.util.List;

/**
 * Padron de profesores habilitados por facultad.
 *
 * <p>Lo mantiene la decanatura de cada facultad y es la lista de la que el CIECYT designa al
 * asesor y al jurado, segun los articulos 8 y 9. La lista cambia cada semestre, asi que se puede
 * cerrar una vigencia y volver a abrir otra, y queda el historial de cuando estuvo cada persona.
 */
@RestController
@RequestMapping("/api")
public class DocenteHabilitadoResource {

    private final Logger log = LoggerFactory.getLogger(DocenteHabilitadoResource.class);

    private final DocenteHabilitadoService docenteHabilitadoService;
    private final ProyectoAutorizacionService autorizacion;

    public DocenteHabilitadoResource(
        DocenteHabilitadoService docenteHabilitadoService,
        ProyectoAutorizacionService autorizacion
    ) {
        this.docenteHabilitadoService = docenteHabilitadoService;
        this.autorizacion = autorizacion;
    }

    /**
     * La lista vigente de una facultad, de la que se puede designar. Es la consulta que alimenta
     * el selector de asesores y de jurados.
     */
    @GetMapping("/docente-habilitados/facultad/{facultadId}")
    public List<DocenteHabilitado> getVigentes(@PathVariable Long facultadId,
                                               @RequestParam(required = false) String rol) {
        if (!autorizacion.puedeVerHabilitadosDe(facultadId)) {
            throw new AccessDeniedException("No puede consultar el padrón de docentes habilitados de esa facultad");
        }
        if (rol != null && !rol.trim().isEmpty()) {
            return docenteHabilitadoService.vigentesPorFacultadYRol(facultadId, rol);
        }
        return docenteHabilitadoService.vigentesPorFacultad(facultadId);
    }

    /**
     * El historial de habilitaciones de un docente en una facultad, para saber en que periodos
     * estuvo. Se pide por facultad y autorizacion sobre esa facultad: de otro modo a quien puede
     * ver una facultad le bastaria con una sola habilitacion en ella para que le llegaran tambien
     * las habilitaciones de la persona en las demas facultades.
     */
    @GetMapping("/docente-habilitados/facultad/{facultadId}/historial/{userId}")
    public List<DocenteHabilitado> getHistorialDeFacultad(@PathVariable Long facultadId, @PathVariable Long userId) {
        if (!autorizacion.puedeVerHabilitadosDe(facultadId)) {
            throw new AccessDeniedException("No puede consultar ese historial");
        }
        return docenteHabilitadoService.historialEnFacultad(userId, facultadId);
    }

    /**
     * Habilita a un docente. Lo puede hacer la decanatura de esa facultad.
     */
    @PostMapping("/docente-habilitados")
    public DocenteHabilitado habilitar(@RequestBody SolicitudHabilitacion solicitud) {
        if (!autorizacion.puedeHabilitarDocentesEn(solicitud.getFacultadId())) {
            throw new AccessDeniedException("No puede habilitar docentes en esa facultad");
        }
        if (solicitud.getUserId() == null) {
            // La decanatura escribe la cedula del docente, no un id interno. Se resuelve aqui para
            // que el navegador no tenga que consultar el directorio de usuarios.
            return docenteHabilitadoService.habilitarPorLogin(
                solicitud.getLogin(), solicitud.getFacultadId(), solicitud.getRol(),
                solicitud.getActoResolucion(), solicitud.getObservaciones());
        }
        return docenteHabilitadoService.habilitar(
            solicitud.getUserId(), solicitud.getFacultadId(), solicitud.getRol(),
            solicitud.getFechaDesde(), solicitud.getActoResolucion(), solicitud.getObservaciones());
    }

    /**
     * Cierra la habilitacion de un docente. No borra el registro: deja la fecha hasta la que
     * estuvo, para que quede el historico y pueda volver a entrar despues.
     */
    @PostMapping("/docente-habilitados/cerrar")
    public DocenteHabilitado cerrar(@RequestBody SolicitudHabilitacion solicitud) {
        if (!autorizacion.puedeHabilitarDocentesEn(solicitud.getFacultadId())) {
            throw new AccessDeniedException("No puede modificar el padrón de esa facultad");
        }
        return docenteHabilitadoService.cerrar(
            solicitud.getUserId(), solicitud.getFacultadId(), solicitud.getRol(), solicitud.getFechaHasta());
    }

    /**
     * Datos de una habilitacion. Se envia suelta y no el estado completo para que quien llama no
     * pueda fijar la vigencia o la facultad por su cuenta.
     */
    public static class SolicitudHabilitacion {
        private Long userId;
        private String login;
        private Long facultadId;
        private String rol;
        private LocalDate fechaDesde;
        private LocalDate fechaHasta;
        private String actoResolucion;
        private String observaciones;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getLogin() { return login; }
        public void setLogin(String login) { this.login = login; }
        public Long getFacultadId() { return facultadId; }
        public void setFacultadId(Long facultadId) { this.facultadId = facultadId; }
        public String getRol() { return rol; }
        public void setRol(String rol) { this.rol = rol; }
        public LocalDate getFechaDesde() { return fechaDesde; }
        public void setFechaDesde(LocalDate fechaDesde) { this.fechaDesde = fechaDesde; }
        public LocalDate getFechaHasta() { return fechaHasta; }
        public void setFechaHasta(LocalDate fechaHasta) { this.fechaHasta = fechaHasta; }
        public String getActoResolucion() { return actoResolucion; }
        public void setActoResolucion(String actoResolucion) { this.actoResolucion = actoResolucion; }
        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }
}
