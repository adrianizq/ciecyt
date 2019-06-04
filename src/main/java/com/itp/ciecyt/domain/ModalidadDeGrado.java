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
 * A ModalidadDeGrado.
 */
@Entity
@Table(name = "modalidad_de_grado")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModalidadDeGrado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "modalidad")
    private String modalidad;

    @OneToMany(mappedBy = "modalidadDeGrado")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proyecto> proyectos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModalidad() {
        return modalidad;
    }

    public ModalidadDeGrado modalidad(String modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public ModalidadDeGrado proyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
        return this;
    }

    public ModalidadDeGrado addProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.setModalidadDeGrado(this);
        return this;
    }

    public ModalidadDeGrado removeProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.setModalidadDeGrado(null);
        return this;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModalidadDeGrado)) {
            return false;
        }
        return id != null && id.equals(((ModalidadDeGrado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ModalidadDeGrado{" +
            "id=" + getId() +
            ", modalidad='" + getModalidad() + "'" +
            "}";
    }
}
