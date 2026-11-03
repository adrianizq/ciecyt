package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link co.edu.itp.ciecyt.domain.PostulacionPasantia}.
 */
public class PostulacionPasantiaDTO implements Serializable {

    private Long id;
    private Long convocatoriaId;
    private String convocatoriaTitulo;
    private Long proyectoId;
    private String proyectoTitulo;
    private Long usuarioId;
    private String usuarioLogin;
    private String usuarioNombre;
    private String estado;
    private LocalDate fechaPostulacion;
    private String observacion;
    private LocalDate fechaDecision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConvocatoriaId() {
        return convocatoriaId;
    }

    public void setConvocatoriaId(Long convocatoriaId) {
        this.convocatoriaId = convocatoriaId;
    }

    public String getConvocatoriaTitulo() {
        return convocatoriaTitulo;
    }

    public void setConvocatoriaTitulo(String convocatoriaTitulo) {
        this.convocatoriaTitulo = convocatoriaTitulo;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getProyectoTitulo() {
        return proyectoTitulo;
    }

    public void setProyectoTitulo(String proyectoTitulo) {
        this.proyectoTitulo = proyectoTitulo;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(LocalDate fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDate getFechaDecision() {
        return fechaDecision;
    }

    public void setFechaDecision(LocalDate fechaDecision) {
        this.fechaDecision = fechaDecision;
    }
}
