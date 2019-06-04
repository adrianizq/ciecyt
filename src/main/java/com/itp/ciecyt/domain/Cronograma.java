package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Cronograma.
 */
@Entity
@Table(name = "cronograma")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @Column(name = "fecha_ini")
    private LocalDate fechaIni;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @ManyToOne
    @JsonIgnoreProperties("cronogramas")
    private Proyecto proyecto;

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

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public Cronograma fechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
        return this;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
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

    public Proyecto getProyecto() {
        return proyecto;
    }

    public Cronograma proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
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
            ", fechaIni='" + getFechaIni() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}
