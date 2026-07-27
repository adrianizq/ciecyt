package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CronogramaCiecyt.
 */
@Entity
@Table(name = "cronograma_ciecyt")
public class CronogramaCiecyt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo_cronograma")
    private String tituloCronograma;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "observaciones")
    private String observaciones;

    @ManyToOne
    @JsonIgnoreProperties("cronogramaCiecyts")
    private Modalidad cronogramaCiecytModalidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloCronograma() {
        return tituloCronograma;
    }

    public CronogramaCiecyt tituloCronograma(String tituloCronograma) {
        this.tituloCronograma = tituloCronograma;
        return this;
    }

    public void setTituloCronograma(String tituloCronograma) {
        this.tituloCronograma = tituloCronograma;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public CronogramaCiecyt fechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public CronogramaCiecyt fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public CronogramaCiecyt observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Modalidad getCronogramaCiecytModalidad() {
        return cronogramaCiecytModalidad;
    }

    public CronogramaCiecyt cronogramaCiecytModalidad(Modalidad modalidad) {
        this.cronogramaCiecytModalidad = modalidad;
        return this;
    }

    public void setCronogramaCiecytModalidad(Modalidad modalidad) {
        this.cronogramaCiecytModalidad = modalidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CronogramaCiecyt)) {
            return false;
        }
        return id != null && id.equals(((CronogramaCiecyt) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CronogramaCiecyt{" +
            "id=" + getId() +
            ", tituloCronograma='" + getTituloCronograma() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            "}";
    }
}
