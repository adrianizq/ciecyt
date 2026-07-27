package co.edu.itp.ciecyt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A AdjuntoProyectoFase.
 */
@Entity
@Table(name = "adjunto_proyecto_fase")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AdjuntoProyectoFase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre_adjunto")
    private String nombreAdjunto;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    @Column(name = "estado_adjunto")
    private Integer estadoAdjunto;

    @Column(name = "adjunto_proyecto_fase")
    private String adjuntoProyectoFase;

    @Column(name = "nombre_archivo_original")
    private String nombreArchivoOriginal;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "archivo")
    private String archivo;

    @Column(name = "archivo_content_type")
    private String archivoContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = "adjuntoProyectoFases", allowSetters = true)
    private Proyecto proyectoFaseProyecto;

    @ManyToOne
    @JsonIgnoreProperties(value = "adjuntoProyectoFases", allowSetters = true)
    private Fases adjuntoProyectoFaseFase;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAdjunto() {
        return nombreAdjunto;
    }

    public AdjuntoProyectoFase nombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
        return this;
    }

    public void setNombreAdjunto(String nombreAdjunto) {
        this.nombreAdjunto = nombreAdjunto;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public AdjuntoProyectoFase fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaModificacion() {
        return fechaModificacion;
    }

    public AdjuntoProyectoFase fechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
        return this;
    }

    public void setFechaModificacion(LocalDate fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getEstadoAdjunto() {
        return estadoAdjunto;
    }

    public AdjuntoProyectoFase estadoAdjunto(Integer estadoAdjunto) {
        this.estadoAdjunto = estadoAdjunto;
        return this;
    }

    public void setEstadoAdjunto(Integer estadoAdjunto) {
        this.estadoAdjunto = estadoAdjunto;
    }

    public String getAdjuntoProyectoFase() {
        return adjuntoProyectoFase;
    }

    public AdjuntoProyectoFase adjuntoProyectoFase(String adjuntoProyectoFase) {
        this.adjuntoProyectoFase = adjuntoProyectoFase;
        return this;
    }

    public void setAdjuntoProyectoFase(String adjuntoProyectoFase) {
        this.adjuntoProyectoFase = adjuntoProyectoFase;
    }

    public String getNombreArchivoOriginal() {
        return nombreArchivoOriginal;
    }

    public AdjuntoProyectoFase nombreArchivoOriginal(String nombreArchivoOriginal) {
        this.nombreArchivoOriginal = nombreArchivoOriginal;
        return this;
    }

    public void setNombreArchivoOriginal(String nombreArchivoOriginal) {
        this.nombreArchivoOriginal = nombreArchivoOriginal;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public AdjuntoProyectoFase fechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public AdjuntoProyectoFase fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }




    public Proyecto getProyectoFaseProyecto() {
        return proyectoFaseProyecto;
    }

    public AdjuntoProyectoFase proyectoFaseProyecto(Proyecto proyecto) {
        this.proyectoFaseProyecto = proyecto;
        return this;
    }

    public void setProyectoFaseProyecto(Proyecto proyecto) {
        this.proyectoFaseProyecto = proyecto;
    }

    public Fases getAdjuntoProyectoFaseFase() {
        return adjuntoProyectoFaseFase;
    }

    public AdjuntoProyectoFase adjuntoProyectoFaseFase(Fases fases) {
        this.adjuntoProyectoFaseFase = fases;
        return this;
    }

    public void setAdjuntoProyectoFaseFase(Fases fases) {
        this.adjuntoProyectoFaseFase = fases;
    }

    public String getArchivo() {
        return archivo;
    }

    public AdjuntoProyectoFase archivo(String archivo) {
        this.archivo = archivo;
        return this;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public AdjuntoProyectoFase archivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
        return this;
    }

    public void setArchivoContentType(String archivoContentType) {
        this.archivoContentType = archivoContentType;
    }

    public String getArchivoContentType() {
        return archivoContentType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdjuntoProyectoFase)) {
            return false;
        }
        return id != null && id.equals(((AdjuntoProyectoFase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdjuntoProyectoFase{" +
            "id=" + getId() +
            ", nombreAdjunto='" + getNombreAdjunto() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaModificacion='" + getFechaModificacion() + "'" +
            ", estadoAdjunto=" + getEstadoAdjunto() +
            ", adjuntoProyectoFase='" + getAdjuntoProyectoFase() + "'" +
            ", nombreArchivoOriginal='" + getNombreArchivoOriginal() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", archivo='" + getArchivo() + "'" +
            ", archivoContentType='" + getArchivoContentType() + "'" +
            "}";
    }
}
