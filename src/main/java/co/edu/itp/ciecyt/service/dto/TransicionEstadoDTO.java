package co.edu.itp.ciecyt.service.dto;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.TransicionEstado} entity.
 */
public class TransicionEstadoDTO implements Serializable {

    private Long id;

    private EnumEstadoProyecto estadoOrigen;

    private EnumEstadoProyecto estadoDestino;

    private String rolRequerido;

    private Boolean activo;

    private Long transicionEstadoModalidadId;

    private String transicionEstadoModalidadModalidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstadoProyecto getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(EnumEstadoProyecto estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public EnumEstadoProyecto getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(EnumEstadoProyecto estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String getRolRequerido() {
        return rolRequerido;
    }

    public void setRolRequerido(String rolRequerido) {
        this.rolRequerido = rolRequerido;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getTransicionEstadoModalidadId() {
        return transicionEstadoModalidadId;
    }

    public void setTransicionEstadoModalidadId(Long transicionEstadoModalidadId) {
        this.transicionEstadoModalidadId = transicionEstadoModalidadId;
    }

    public String getTransicionEstadoModalidadModalidad() {
        return transicionEstadoModalidadModalidad;
    }

    public void setTransicionEstadoModalidadModalidad(String transicionEstadoModalidadModalidad) {
        this.transicionEstadoModalidadModalidad = transicionEstadoModalidadModalidad;
    }

    @Override
    public String toString() {
        return "TransicionEstadoDTO{" +
            "id=" + id +
            ", estadoOrigen=" + estadoOrigen +
            ", estadoDestino=" + estadoDestino +
            ", rolRequerido='" + rolRequerido + '\'' +
            '}';
    }
}
