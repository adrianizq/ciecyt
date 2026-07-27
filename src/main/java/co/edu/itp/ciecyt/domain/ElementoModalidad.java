package co.edu.itp.ciecyt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ElementoModalidad.
 */
@Entity
@Table(name = "elemento_modalidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ElementoModalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "elementoModalidads", allowSetters = true)
    private Elemento elemento;

    @ManyToOne
    @JsonIgnoreProperties(value = "elementoModalidads", allowSetters = true)
    private Modalidad modalidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public ElementoModalidad elemento(Elemento elemento) {
        this.elemento = elemento;
        return this;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public ElementoModalidad modalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementoModalidad)) {
            return false;
        }
        return id != null && id.equals(((ElementoModalidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementoModalidad{" +
            "id=" + getId() +
            "}";
    }
}
