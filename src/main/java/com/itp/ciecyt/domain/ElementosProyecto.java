package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ElementosProyecto.
 */
@Entity
@Table(name = "elementos_proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ElementosProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dato")
    private String dato;

    @ManyToOne
    @JsonIgnoreProperties("elementosProyectos")
    private Proyecto proyecto;

    @ManyToOne
    @JsonIgnoreProperties("elementosProyectos")
    private Elementos elementos;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDato() {
        return dato;
    }

    public ElementosProyecto dato(String dato) {
        this.dato = dato;
        return this;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public ElementosProyecto proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Elementos getElementos() {
        return elementos;
    }

    public ElementosProyecto elementos(Elementos elementos) {
        this.elementos = elementos;
        return this;
    }

    public void setElementos(Elementos elementos) {
        this.elementos = elementos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementosProyecto)) {
            return false;
        }
        return id != null && id.equals(((ElementosProyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ElementosProyecto{" +
            "id=" + getId() +
            ", dato='" + getDato() + "'" +
            "}";
    }
}
