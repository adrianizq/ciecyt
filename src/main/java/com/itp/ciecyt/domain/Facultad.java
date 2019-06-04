package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Facultad.
 */
@Entity
@Table(name = "facultad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Facultad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "facultad")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proyecto> proyectos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("facultads")
    private Facultad padre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Facultad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public Facultad proyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
        return this;
    }

    public Facultad addProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.setFacultad(this);
        return this;
    }

    public Facultad removeProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.setFacultad(null);
        return this;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Facultad getPadre() {
        return padre;
    }

    public Facultad padre(Facultad facultad) {
        this.padre = facultad;
        return this;
    }

    public void setPadre(Facultad facultad) {
        this.padre = facultad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Facultad)) {
            return false;
        }
        return id != null && id.equals(((Facultad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Facultad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            "}";
    }
}
