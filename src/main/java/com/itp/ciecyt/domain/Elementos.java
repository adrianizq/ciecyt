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
 * A Elementos.
 */
@Entity
@Table(name = "elementos")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Elementos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "elemento")
    private String elemento;

    @OneToMany(mappedBy = "elementos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ElementosProyecto> elementosProyectos = new HashSet<>();

    @OneToMany(mappedBy = "elementos")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Preguntas> preguntas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElemento() {
        return elemento;
    }

    public Elementos elemento(String elemento) {
        this.elemento = elemento;
        return this;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public Set<ElementosProyecto> getElementosProyectos() {
        return elementosProyectos;
    }

    public Elementos elementosProyectos(Set<ElementosProyecto> elementosProyectos) {
        this.elementosProyectos = elementosProyectos;
        return this;
    }

    public Elementos addElementosProyecto(ElementosProyecto elementosProyecto) {
        this.elementosProyectos.add(elementosProyecto);
        elementosProyecto.setElementos(this);
        return this;
    }

    public Elementos removeElementosProyecto(ElementosProyecto elementosProyecto) {
        this.elementosProyectos.remove(elementosProyecto);
        elementosProyecto.setElementos(null);
        return this;
    }

    public void setElementosProyectos(Set<ElementosProyecto> elementosProyectos) {
        this.elementosProyectos = elementosProyectos;
    }

    public Set<Preguntas> getPreguntas() {
        return preguntas;
    }

    public Elementos preguntas(Set<Preguntas> preguntas) {
        this.preguntas = preguntas;
        return this;
    }

    public Elementos addPreguntas(Preguntas preguntas) {
        this.preguntas.add(preguntas);
        preguntas.setElementos(this);
        return this;
    }

    public Elementos removePreguntas(Preguntas preguntas) {
        this.preguntas.remove(preguntas);
        preguntas.setElementos(null);
        return this;
    }

    public void setPreguntas(Set<Preguntas> preguntas) {
        this.preguntas = preguntas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Elementos)) {
            return false;
        }
        return id != null && id.equals(((Elementos) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Elementos{" +
            "id=" + getId() +
            ", elemento='" + getElemento() + "'" +
            "}";
    }
}
