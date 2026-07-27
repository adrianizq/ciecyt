package co.edu.itp.ciecyt.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ciclo.
 */
@Entity
@Table(name = "ciclo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ciclo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ciclo")
    private String ciclo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "modalidad_ciclo",
        joinColumns = @JoinColumn(name = "ciclo_id"),
        inverseJoinColumns = @JoinColumn(name = "modalidad_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Modalidad> modalidades = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiclo() {
        return ciclo;
    }

    public Ciclo ciclo(String ciclo) {
        this.ciclo = ciclo;
        return this;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Set<Modalidad> getModalidades() {
        return modalidades;
    }

    public Ciclo modalidades(Set<Modalidad> modalidades) {
        this.modalidades = modalidades;
        return this;
    }

    public Ciclo addModalidad(Modalidad modalidad) {
        this.modalidades.add(modalidad);
        return this;
    }

    public Ciclo removeModalidad(Modalidad modalidad) {
        this.modalidades.remove(modalidad);
        return this;
    }

    public void setModalidades(Set<Modalidad> modalidades) {
        this.modalidades = modalidades;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ciclo)) {
            return false;
        }
        return id != null && id.equals(((Ciclo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ciclo{" +
            "id=" + getId() +
            ", ciclo='" + getCiclo() + "'" +
            "}";
    }
}
