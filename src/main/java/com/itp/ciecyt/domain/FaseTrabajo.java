package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FaseTrabajo.
 */
@Entity
@Table(name = "fase_trabajo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FaseTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fase_trabajo")
    private String faseTrabajo;

    @OneToMany(mappedBy = "faseTrabajo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Preguntas> faseTrabajos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFaseTrabajo() {
        return faseTrabajo;
    }

    public FaseTrabajo faseTrabajo(String faseTrabajo) {
        this.faseTrabajo = faseTrabajo;
        return this;
    }

    public void setFaseTrabajo(String faseTrabajo) {
        this.faseTrabajo = faseTrabajo;
    }

    public Set<Preguntas> getFaseTrabajos() {
        return faseTrabajos;
    }

    public FaseTrabajo faseTrabajos(Set<Preguntas> preguntas) {
        this.faseTrabajos = preguntas;
        return this;
    }

    public FaseTrabajo addFaseTrabajo(Preguntas preguntas) {
        this.faseTrabajos.add(preguntas);
        preguntas.setFaseTrabajo(this);
        return this;
    }

    public FaseTrabajo removeFaseTrabajo(Preguntas preguntas) {
        this.faseTrabajos.remove(preguntas);
        preguntas.setFaseTrabajo(null);
        return this;
    }

    public void setFaseTrabajos(Set<Preguntas> preguntas) {
        this.faseTrabajos = preguntas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FaseTrabajo)) {
            return false;
        }
        return id != null && id.equals(((FaseTrabajo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FaseTrabajo{" +
            "id=" + getId() +
            ", faseTrabajo='" + getFaseTrabajo() + "'" +
            "}";
    }
}
