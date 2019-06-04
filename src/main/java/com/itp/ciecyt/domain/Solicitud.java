package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Solicitud.
 */
@Entity
@Table(name = "solicitud")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "fecha_creancion")
    private LocalDate fechaCreancion;

    @ManyToOne
    @JsonIgnoreProperties("solicituds")
    private IntegrantesProyecto integrantesProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Solicitud estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getAsunto() {
        return asunto;
    }

    public Solicitud asunto(String asunto) {
        this.asunto = asunto;
        return this;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public LocalDate getFechaCreancion() {
        return fechaCreancion;
    }

    public Solicitud fechaCreancion(LocalDate fechaCreancion) {
        this.fechaCreancion = fechaCreancion;
        return this;
    }

    public void setFechaCreancion(LocalDate fechaCreancion) {
        this.fechaCreancion = fechaCreancion;
    }

    public IntegrantesProyecto getIntegrantesProyecto() {
        return integrantesProyecto;
    }

    public Solicitud integrantesProyecto(IntegrantesProyecto integrantesProyecto) {
        this.integrantesProyecto = integrantesProyecto;
        return this;
    }

    public void setIntegrantesProyecto(IntegrantesProyecto integrantesProyecto) {
        this.integrantesProyecto = integrantesProyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Solicitud)) {
            return false;
        }
        return id != null && id.equals(((Solicitud) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
            "id=" + getId() +
            ", estado='" + isEstado() + "'" +
            ", asunto='" + getAsunto() + "'" +
            ", fechaCreancion='" + getFechaCreancion() + "'" +
            "}";
    }
}
