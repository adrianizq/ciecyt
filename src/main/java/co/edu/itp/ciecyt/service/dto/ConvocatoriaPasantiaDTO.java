package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link co.edu.itp.ciecyt.domain.ConvocatoriaPasantia}.
 */
public class ConvocatoriaPasantiaDTO implements Serializable {

    private Long id;
    private String titulo;
    private String descripcion;
    private Long modalidadId;
    private String entidad;
    private String convenio;
    private Integer cupos;
    private LocalDate fechaApertura;
    private LocalDate fechaCierre;
    private String requisitos;
    private String criteriosSeleccion;
    private String estado;
    private Boolean activo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Long modalidadId) {
        this.modalidadId = modalidadId;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public Integer getCupos() {
        return cupos;
    }

    public void setCupos(Integer cupos) {
        this.cupos = cupos;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getCriteriosSeleccion() {
        return criteriosSeleccion;
    }

    public void setCriteriosSeleccion(String criteriosSeleccion) {
        this.criteriosSeleccion = criteriosSeleccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
