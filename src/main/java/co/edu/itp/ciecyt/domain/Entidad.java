package co.edu.itp.ciecyt.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Entidad.
 */
@Entity
@Table(name = "entidad")
public class Entidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "entidad")
    private String entidad;

    @Column(name = "nit")
    private String nit;

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

    public Entidad entidad(String entidad) {
        this.entidad = entidad;
        return this;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getNit() {
        return nit;
    }

    public Entidad nit(String nit) {
        this.nit = nit;
        return this;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entidad)) {
            return false;
        }
        return id != null && id.equals(((Entidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Entidad{" +
            "id=" + getId() +
            ", entidad='" + getEntidad() + "'" +
            ", nit='" + getNit() + "'" +
            "}";
    }
}
