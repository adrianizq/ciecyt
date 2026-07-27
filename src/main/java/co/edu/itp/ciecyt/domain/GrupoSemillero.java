package co.edu.itp.ciecyt.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A GrupoSemillero.
 */
@Entity
@Table(name = "grupo_semillero")
public class GrupoSemillero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private Boolean tipo;

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

    public GrupoSemillero nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isTipo() {
        return tipo;
    }

    public GrupoSemillero tipo(Boolean tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoSemillero)) {
            return false;
        }
        return id != null && id.equals(((GrupoSemillero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "GrupoSemillero{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + isTipo() + "'" +
            "}";
    }
}
