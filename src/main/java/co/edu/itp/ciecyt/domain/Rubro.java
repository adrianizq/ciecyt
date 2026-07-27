package co.edu.itp.ciecyt.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Rubro.
 */
@Entity
@Table(name = "rubro")
public class Rubro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rubro")
    private String rubro;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRubro() {
        return rubro;
    }

    public Rubro rubro(String rubro) {
        this.rubro = rubro;
        return this;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rubro)) {
            return false;
        }
        return id != null && id.equals(((Rubro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Rubro{" +
            "id=" + getId() +
            ", rubro='" + getRubro() + "'" +
            "}";
    }
}
