package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Empresas.
 */
@Entity
@Table(name = "empresas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Empresas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "entidad")
    private String entidad;

    @ManyToOne
    @JsonIgnoreProperties("empresas")
    private EntidadFinanciadora entidadFinanciadora;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidad() {
        return entidad;
    }

    public Empresas entidad(String entidad) {
        this.entidad = entidad;
        return this;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public EntidadFinanciadora getEntidadFinanciadora() {
        return entidadFinanciadora;
    }

    public Empresas entidadFinanciadora(EntidadFinanciadora entidadFinanciadora) {
        this.entidadFinanciadora = entidadFinanciadora;
        return this;
    }

    public void setEntidadFinanciadora(EntidadFinanciadora entidadFinanciadora) {
        this.entidadFinanciadora = entidadFinanciadora;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresas)) {
            return false;
        }
        return id != null && id.equals(((Empresas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Empresas{" +
            "id=" + getId() +
            ", entidad='" + getEntidad() + "'" +
            "}";
    }
}
