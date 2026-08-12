package co.edu.itp.ciecyt.service.dto;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoRequisito;
import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.domain.enumeration.TipoRequisito;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.RequisitoProyecto} entity.
 */
public class RequisitoProyectoDTO implements Serializable {

    private Long id;

    private EnumEstadoRequisito estado;

    private Instant fechaEntrega;

    private Instant fechaValidacion;

    private String observacion;

    private String archivo;

    private String validadoPor;

    private Long requisitoProyectoRequisitoId;

    private String requisitoProyectoRequisitoNombre;

    private String requisitoProyectoRequisitoCodigo;

    private Boolean requisitoProyectoRequisitoObligatorio;

    private TipoRequisito requisitoProyectoRequisitoTipo;

    private EnumEstadoProyecto requisitoProyectoRequisitoEstado;

    private Long requisitoProyectoProyectoId;

    private String requisitoProyectoProyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstadoRequisito getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoRequisito estado) {
        this.estado = estado;
    }

    public Instant getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Instant fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Instant getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Instant fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getValidadoPor() {
        return validadoPor;
    }

    public void setValidadoPor(String validadoPor) {
        this.validadoPor = validadoPor;
    }

    public Long getRequisitoProyectoRequisitoId() {
        return requisitoProyectoRequisitoId;
    }

    public void setRequisitoProyectoRequisitoId(Long requisitoProyectoRequisitoId) {
        this.requisitoProyectoRequisitoId = requisitoProyectoRequisitoId;
    }

    public String getRequisitoProyectoRequisitoNombre() {
        return requisitoProyectoRequisitoNombre;
    }

    public void setRequisitoProyectoRequisitoNombre(String requisitoProyectoRequisitoNombre) {
        this.requisitoProyectoRequisitoNombre = requisitoProyectoRequisitoNombre;
    }

    public String getRequisitoProyectoRequisitoCodigo() {
        return requisitoProyectoRequisitoCodigo;
    }

    public void setRequisitoProyectoRequisitoCodigo(String requisitoProyectoRequisitoCodigo) {
        this.requisitoProyectoRequisitoCodigo = requisitoProyectoRequisitoCodigo;
    }

    public Boolean getRequisitoProyectoRequisitoObligatorio() {
        return requisitoProyectoRequisitoObligatorio;
    }

    public void setRequisitoProyectoRequisitoObligatorio(Boolean requisitoProyectoRequisitoObligatorio) {
        this.requisitoProyectoRequisitoObligatorio = requisitoProyectoRequisitoObligatorio;
    }

    public TipoRequisito getRequisitoProyectoRequisitoTipo() {
        return requisitoProyectoRequisitoTipo;
    }

    public void setRequisitoProyectoRequisitoTipo(TipoRequisito requisitoProyectoRequisitoTipo) {
        this.requisitoProyectoRequisitoTipo = requisitoProyectoRequisitoTipo;
    }

    public EnumEstadoProyecto getRequisitoProyectoRequisitoEstado() {
        return requisitoProyectoRequisitoEstado;
    }

    public void setRequisitoProyectoRequisitoEstado(EnumEstadoProyecto requisitoProyectoRequisitoEstado) {
        this.requisitoProyectoRequisitoEstado = requisitoProyectoRequisitoEstado;
    }

    public Long getRequisitoProyectoProyectoId() {
        return requisitoProyectoProyectoId;
    }

    public void setRequisitoProyectoProyectoId(Long requisitoProyectoProyectoId) {
        this.requisitoProyectoProyectoId = requisitoProyectoProyectoId;
    }

    public String getRequisitoProyectoProyectoTitulo() {
        return requisitoProyectoProyectoTitulo;
    }

    public void setRequisitoProyectoProyectoTitulo(String requisitoProyectoProyectoTitulo) {
        this.requisitoProyectoProyectoTitulo = requisitoProyectoProyectoTitulo;
    }

    @Override
    public String toString() {
        return "RequisitoProyectoDTO{" +
            "id=" + id +
            ", estado=" + estado +
            ", requisito=" + requisitoProyectoRequisitoId +
            ", proyecto=" + requisitoProyectoProyectoId +
            '}';
    }
}
