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
 * A LineaDeInvestigacion.
 */
@Entity
@Table(name = "linea_de_investigacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LineaDeInvestigacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "linea")
    private String linea;

    @OneToMany(mappedBy = "lineaDeInvestigacion")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Proyecto> proyectos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("lineaDeInvestigacions")
    private LineaDeInvestigacion padre;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinea() {
        return linea;
    }

    public LineaDeInvestigacion linea(String linea) {
        this.linea = linea;
        return this;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public LineaDeInvestigacion proyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
        return this;
    }

    public LineaDeInvestigacion addProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
        proyecto.setLineaDeInvestigacion(this);
        return this;
    }

    public LineaDeInvestigacion removeProyecto(Proyecto proyecto) {
        this.proyectos.remove(proyecto);
        proyecto.setLineaDeInvestigacion(null);
        return this;
    }

    public void setProyectos(Set<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public LineaDeInvestigacion getPadre() {
        return padre;
    }

    public LineaDeInvestigacion padre(LineaDeInvestigacion lineaDeInvestigacion) {
        this.padre = lineaDeInvestigacion;
        return this;
    }

    public void setPadre(LineaDeInvestigacion lineaDeInvestigacion) {
        this.padre = lineaDeInvestigacion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineaDeInvestigacion)) {
            return false;
        }
        return id != null && id.equals(((LineaDeInvestigacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LineaDeInvestigacion{" +
            "id=" + getId() +
            ", linea='" + getLinea() + "'" +
            "}";
    }
}
