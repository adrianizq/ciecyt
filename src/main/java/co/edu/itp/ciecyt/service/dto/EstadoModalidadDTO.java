package co.edu.itp.ciecyt.service.dto;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.EstadoModalidad} entity.
 */
public class EstadoModalidadDTO implements Serializable {

    private Long id;

    private EnumEstadoProyecto estado;

    private String nombre;

    private Integer orden;

    private Boolean activo;

    private Long estadoModalidadModalidadId;

    private String estadoModalidadModalidadModalidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstadoProyecto getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoProyecto estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getEstadoModalidadModalidadId() {
        return estadoModalidadModalidadId;
    }

    public void setEstadoModalidadModalidadId(Long estadoModalidadModalidadId) {
        this.estadoModalidadModalidadId = estadoModalidadModalidadId;
    }

    public String getEstadoModalidadModalidadModalidad() {
        return estadoModalidadModalidadModalidad;
    }

    public void setEstadoModalidadModalidadModalidad(String estadoModalidadModalidadModalidad) {
        this.estadoModalidadModalidadModalidad = estadoModalidadModalidadModalidad;
    }

    @Override
    public String toString() {
        return "EstadoModalidadDTO{" +
            "id=" + id +
            ", estado=" + estado +
            ", orden=" + orden +
            '}';
    }
}
