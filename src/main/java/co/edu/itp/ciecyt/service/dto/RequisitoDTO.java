package co.edu.itp.ciecyt.service.dto;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.domain.enumeration.TipoRequisito;
import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Requisito} entity.
 */
public class RequisitoDTO implements Serializable {

    private Long id;

    private String codigo;

    private String nombre;

    private String descripcion;

    private TipoRequisito tipo;

    private Boolean obligatorio;

    private Boolean activo;

    private Long requisitoModalidadId;

    private String requisitoModalidadModalidad;

    private EnumEstadoProyecto requisitoEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoRequisito getTipo() {
        return tipo;
    }

    public void setTipo(TipoRequisito tipo) {
        this.tipo = tipo;
    }

    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getRequisitoModalidadId() {
        return requisitoModalidadId;
    }

    public void setRequisitoModalidadId(Long requisitoModalidadId) {
        this.requisitoModalidadId = requisitoModalidadId;
    }

    public String getRequisitoModalidadModalidad() {
        return requisitoModalidadModalidad;
    }

    public void setRequisitoModalidadModalidad(String requisitoModalidadModalidad) {
        this.requisitoModalidadModalidad = requisitoModalidadModalidad;
    }

    public EnumEstadoProyecto getRequisitoEstado() {
        return requisitoEstado;
    }

    public void setRequisitoEstado(EnumEstadoProyecto requisitoEstado) {
        this.requisitoEstado = requisitoEstado;
    }

    @Override
    public String toString() {
        return "RequisitoDTO{" +
            "id=" + id +
            ", codigo='" + codigo + '\'' +
            ", nombre='" + nombre + '\'' +
            ", tipo=" + tipo +
            '}';
    }
}
