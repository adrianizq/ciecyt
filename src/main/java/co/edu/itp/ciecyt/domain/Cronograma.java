package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Cronograma.
 */
@Entity
@Table(name = "cronograma")
public class Cronograma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "actividad")
    private String actividad;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "orden_vista")
    private Integer ordenVista;

    @ManyToOne
    @JsonIgnoreProperties("cronogramas")
    private Proyecto cronogramaProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActividad() {
        return actividad;
    }

    public Cronograma actividad(String actividad) {
        this.actividad = actividad;
        return this;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public Cronograma duracion(Integer duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public Cronograma fechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Cronograma fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getOrdenVista() {
        return ordenVista;
    }

    public Cronograma ordenVista(Integer ordenVista) {
        this.ordenVista = ordenVista;
        return this;
    }

    public void setOrdenVista(Integer ordenVista) {
        this.ordenVista = ordenVista;
    }

    public Proyecto getCronogramaProyecto() {
        return cronogramaProyecto;
    }

    public Cronograma cronogramaProyecto(Proyecto proyecto) {
        this.cronogramaProyecto = proyecto;
        return this;
    }

    public void setCronogramaProyecto(Proyecto proyecto) {
        this.cronogramaProyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cronograma)) {
            return false;
        }
        return id != null && id.equals(((Cronograma) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cronograma{" +
            "id=" + getId() +
            ", actividad='" + getActividad() + "'" +
            ", duracion=" + getDuracion() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", ordenVista=" + getOrdenVista() +
            "}";
    }
}
