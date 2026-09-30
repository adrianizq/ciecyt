package co.edu.itp.ciecyt.web.rest;

import co.edu.itp.ciecyt.domain.AsesorExterno;
import co.edu.itp.ciecyt.service.AsesorExternoService;
import co.edu.itp.ciecyt.service.ProyectoAutorizacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link co.edu.itp.ciecyt.domain.AsesorExterno}.
 *
 * <p>Registrar y verificar la idoneidad es del CIECYT (y soporte administrativo): el
 * reglamento le encarga la verificacion, asi que quien no es del comite ni administrador no
 * puede crear ni marcar a nadie. Consultar sigue la misma regla que el padron de habilitados,
 * porque el externo cumple el rol en los proyectos de esa facultad.
 */
@RestController
@RequestMapping("/api")
public class AsesorExternoResource {

    private final Logger log = LoggerFactory.getLogger(AsesorExternoResource.class);

    private final AsesorExternoService asesorExternoService;
    private final ProyectoAutorizacionService autorizacion;

    public AsesorExternoResource(AsesorExternoService asesorExternoService, ProyectoAutorizacionService autorizacion) {
        this.asesorExternoService = asesorExternoService;
        this.autorizacion = autorizacion;
    }

    private void exigirGestion() {
        if (!autorizacion.esGestorGlobal() && !autorizacion.esAdministrador()) {
            throw new AccessDeniedException("Solo el CIECYT registra y verifica profesionales externos");
        }
    }

    /**
     * {@code POST /api/asesores-externos} : registra al profesional externo de una facultad.
     */
    @PostMapping("/asesores-externos")
    public ResponseEntity<AsesorExterno> registrar(@RequestBody SolicitudRegistro solicitud) {
        exigirGestion();
        log.debug("REST request to registrar asesor externo en la facultad {}", solicitud.getFacultadId());
        AsesorExterno datos = new AsesorExterno();
        datos.setNombres(solicitud.getNombres());
        datos.setApellidos(solicitud.getApellidos());
        datos.setNumeroDocumento(solicitud.getNumeroDocumento());
        datos.setCorreoElectronico(solicitud.getCorreoElectronico());
        datos.setTelefono(solicitud.getTelefono());
        datos.setTitulos(solicitud.getTitulos());
        datos.setInstitucionOrigen(solicitud.getInstitucionOrigen());
        datos.setRol(solicitud.getRol());
        datos.setFuenteVerificacion(solicitud.getFuenteVerificacion());
        datos.setObservaciones(solicitud.getObservaciones());
        AsesorExterno creado = asesorExternoService.registrar(datos, solicitud.getFacultadId());
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /**
     * {@code PUT /api/asesores-externos/{id}} : actualiza los datos del profesional externo.
     */
    @PutMapping("/asesores-externos/{id}")
    public ResponseEntity<AsesorExterno> actualizar(@PathVariable Long id, @RequestBody AsesorExterno datos) {
        exigirGestion();
        log.debug("REST request to actualizar asesor externo {}", id);
        return ResponseEntity.ok(asesorExternoService.actualizar(id, datos));
    }

    /**
     * {@code POST /api/asesores-externos/{id}/verificar} : el CIECYT constata la idoneidad
     * academica, unico paso tras el cual se puede designar.
     */
    @PostMapping("/asesores-externos/{id}/verificar")
    public ResponseEntity<AsesorExterno> verificar(@PathVariable Long id) {
        exigirGestion();
        log.debug("REST request to verificar la idoneidad del profesional externo {}", id);
        return ResponseEntity.ok(asesorExternoService.verificar(id));
    }

    /**
     * {@code GET /api/asesores-externos/facultad/{facultadId}} : listado de la facultad. Con
     * el parametro opcional {@code rol} solo devuelve los ya verificados para ese cargo, que es
     * lo que se ofrece al designar.
     */
    @GetMapping("/asesores-externos/facultad/{facultadId}")
    public ResponseEntity<List<AsesorExterno>> listar(@PathVariable Long facultadId,
                                                      @RequestParam(required = false) String rol) {
        if (!autorizacion.puedeVerHabilitadosDe(facultadId)) {
            throw new AccessDeniedException("No puede consultar los profesionales externos de esa facultad");
        }
        if (rol == null || rol.trim().isEmpty()) {
            return ResponseEntity.ok(asesorExternoService.listarPorFacultad(facultadId));
        }
        return ResponseEntity.ok(asesorExternoService.listarVerificadosPorFacultadYRol(facultadId, rol));
    }

    /**
     * {@code GET /api/asesores-externos/{id}} : detalle de un profesional externo.
     */
    @GetMapping("/asesores-externos/{id}")
    public ResponseEntity<AsesorExterno> get(@PathVariable Long id) {
        return asesorExternoService.findOne(id)
            .map(e -> {
                if (!autorizacion.puedeVerHabilitadosDe(e.getFacultad() == null ? null : e.getFacultad().getId())) {
                    throw new AccessDeniedException("No puede consultar los profesionales externos de esa facultad");
                }
                return ResponseEntity.ok(e);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public static class SolicitudRegistro {
        private Long facultadId;
        private String nombres;
        private String apellidos;
        private String numeroDocumento;
        private String correoElectronico;
        private String telefono;
        private String titulos;
        private String institucionOrigen;
        private String rol;
        private String fuenteVerificacion;
        private String observaciones;

        public Long getFacultadId() {
            return facultadId;
        }

        public void setFacultadId(Long facultadId) {
            this.facultadId = facultadId;
        }

        public String getNombres() {
            return nombres;
        }

        public void setNombres(String nombres) {
            this.nombres = nombres;
        }

        public String getApellidos() {
            return apellidos;
        }

        public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
        }

        public String getNumeroDocumento() {
            return numeroDocumento;
        }

        public void setNumeroDocumento(String numeroDocumento) {
            this.numeroDocumento = numeroDocumento;
        }

        public String getCorreoElectronico() {
            return correoElectronico;
        }

        public void setCorreoElectronico(String correoElectronico) {
            this.correoElectronico = correoElectronico;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getTitulos() {
            return titulos;
        }

        public void setTitulos(String titulos) {
            this.titulos = titulos;
        }

        public String getInstitucionOrigen() {
            return institucionOrigen;
        }

        public void setInstitucionOrigen(String institucionOrigen) {
            this.institucionOrigen = institucionOrigen;
        }

        public String getRol() {
            return rol;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }

        public String getFuenteVerificacion() {
            return fuenteVerificacion;
        }

        public void setFuenteVerificacion(String fuenteVerificacion) {
            this.fuenteVerificacion = fuenteVerificacion;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }
    }
}