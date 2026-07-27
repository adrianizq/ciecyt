package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A ProyectoFase.
 */
@Entity
@Table(name = "proyecto_fase")
public class ProyectoFase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "cumplida")
    private Boolean cumplida;

    @Column(name = "fecha_cumplimiento")
    private LocalDate fechaCumplimiento;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JsonIgnoreProperties("proyectoFases")
    private Fases proyectoFaseFases;

    @ManyToOne
    @JsonIgnoreProperties("proyectoFases")
    private Proyecto proyectoFaseProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public ProyectoFase titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Boolean isCumplida() {
        return cumplida;
    }

    public ProyectoFase cumplida(Boolean cumplida) {
        this.cumplida = cumplida;
        return this;
    }

    public void setCumplida(Boolean cumplida) {
        this.cumplida = cumplida;
    }

    public LocalDate getFechaCumplimiento() {
        return fechaCumplimiento;
    }

    public ProyectoFase fechaCumplimiento(LocalDate fechaCumplimiento) {
        this.fechaCumplimiento = fechaCumplimiento;
        return this;
    }

    public void setFechaCumplimiento(LocalDate fechaCumplimiento) {
        this.fechaCumplimiento = fechaCumplimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public ProyectoFase observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Fases getProyectoFaseFases() {
        return proyectoFaseFases;
    }

    public ProyectoFase proyectoFaseFases(Fases fases) {
        this.proyectoFaseFases = fases;
        return this;
    }

    public void setProyectoFaseFases(Fases fases) {
        this.proyectoFaseFases = fases;
    }

    public Proyecto getProyectoFaseProyecto() {
        return proyectoFaseProyecto;
    }

    public ProyectoFase proyectoFaseProyecto(Proyecto proyecto) {
        this.proyectoFaseProyecto = proyecto;
        return this;
    }

    public void setProyectoFaseProyecto(Proyecto proyecto) {
        this.proyectoFaseProyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoFase)) {
            return false;
        }
        return id != null && id.equals(((ProyectoFase) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProyectoFase{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", cumplida='" + isCumplida() + "'" +
            ", fechaCumplimiento='" + getFechaCumplimiento() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
