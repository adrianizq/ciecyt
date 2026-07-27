package co.edu.itp.ciecyt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Fases.
 */
@Entity
@Table(name = "fases")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fases implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fase")
    private String fase;

    @Column(name = "notificable")
    private Boolean notificable;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFase() {
        return fase;
    }

    public Fases fase(String fase) {
        this.fase = fase;
        return this;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Boolean isNotificable() {
        return notificable;
    }

    public Fases notificable(Boolean notificable) {
        this.notificable = notificable;
        return this;
    }

    public void setNotificable(Boolean notificable) {
        this.notificable = notificable;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fases)) {
            return false;
        }
        return id != null && id.equals(((Fases) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fases{" +
            "id=" + getId() +
            ", fase='" + getFase() + "'" +
            ", notificable='" + isNotificable() + "'" +
            "}";
    }
}
