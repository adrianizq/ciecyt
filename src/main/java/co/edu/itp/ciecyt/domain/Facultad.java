package co.edu.itp.ciecyt.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Facultad.
 */
@Entity
@Table(name = "facultad")
public class Facultad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo_facultad")
    private String codigoFacultad;

    @Column(name = "facultad")
    private String facultad;

    @OneToMany(mappedBy = "facultad")
    private Set<Proyecto> facultadProyectos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoFacultad() {
        return codigoFacultad;
    }

    public Facultad codigoFacultad(String codigoFacultad) {
        this.codigoFacultad = codigoFacultad;
        return this;
    }

    public void setCodigoFacultad(String codigoFacultad) {
        this.codigoFacultad = codigoFacultad;
    }

    public String getFacultad() {
        return facultad;
    }

    public Facultad facultad(String facultad) {
        this.facultad = facultad;
        return this;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public Set<Proyecto> getFacultadProyectos() {
        return facultadProyectos;
    }

    public Facultad facultadProyectos(Set<Proyecto> proyectos) {
        this.facultadProyectos = proyectos;
        return this;
    }

    public Facultad addFacultadProyecto(Proyecto proyecto) {
        this.facultadProyectos.add(proyecto);
        proyecto.setFacultad(this);
        return this;
    }

    public Facultad removeFacultadProyecto(Proyecto proyecto) {
        this.facultadProyectos.remove(proyecto);
        proyecto.setFacultad(null);
        return this;
    }

    public void setFacultadProyectos(Set<Proyecto> proyectos) {
        this.facultadProyectos = proyectos;
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
            ", codigoFacultad='" + getCodigoFacultad() + "'" +
            ", facultad='" + getFacultad() + "'" +
            "}";
    }
}
