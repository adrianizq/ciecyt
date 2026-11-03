package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoConvocatoria;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Convocatoria de pasantia publicada por el CIECYT.
 */
@Entity
@Table(name = "convocatoria_pasantia")
public class ConvocatoriaPasantia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "text")
    private String descripcion;

    @Column(name = "modalidad_id")
    private Long modalidadId;

    @Column(name = "entidad")
    private String entidad;

    @Column(name = "convenio")
    private String convenio;

    @Column(name = "cupos")
    private Integer cupos;

    @Column(name = "fecha_apertura")
    private LocalDate fechaApertura;

    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;

    @Column(name = "requisitos", columnDefinition = "text")
    private String requisitos;

    @Column(name = "criterios_seleccion", columnDefinition = "text")
    private String criteriosSeleccion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EnumEstadoConvocatoria estado;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

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

    public EnumEstadoConvocatoria getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoConvocatoria estado) {
        this.estado = estado;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
