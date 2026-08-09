package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.ProyectoHistorialEstado} entity.
 */
public class ProyectoHistorialEstadoDTO implements Serializable {

    private Long id;

    private String estadoAnterior;

    private String estadoNuevo;

    private String observacion;

    private String usuarioLogin;

    private Instant fechaCambio;

    private Long proyectoId;

    private String proyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(String estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public Instant getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Instant fechaCambio) {
        this.fechaCambio = fechaCambio;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoHistorialEstadoDTO)) {
            return false;
        }

        return id != null && id.equals(((ProyectoHistorialEstadoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProyectoHistorialEstadoDTO{" +
            "id=" + getId() +
            ", estadoAnterior='" + getEstadoAnterior() + "'" +
            ", estadoNuevo='" + getEstadoNuevo() + "'" +
            ", usuarioLogin='" + getUsuarioLogin() + "'" +
            ", fechaCambio='" + getFechaCambio() + "'" +
            "}";
    }
}
