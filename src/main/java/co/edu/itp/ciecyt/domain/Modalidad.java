package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Modalidad.
 */
@Entity
@Table(name = "modalidad")
public class Modalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "modalidad")
    private String modalidad;

    @Column(name = "contiene_linea")
    private boolean contieneLinea;

    @OneToMany(mappedBy = "modalidad")
    private Set<CicloPropedeutico> modalidadCicloPropedeuticos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("modalidads")
    private Acuerdo modalidadAcuerdo;

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

    public Modalidad modalidad(String modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    ////////////////////////////
    public boolean getContieneLinea() {
        return contieneLinea;
    }

    public Modalidad contieneLinea(boolean contieneLinea) {
        this.contieneLinea = contieneLinea;
        return this;
    }

    public void setContieneLinea(boolean contieneLinea) {
        this.contieneLinea = contieneLinea;
    }
    ////////////////////////////////////////7

    public Set<CicloPropedeutico> getModalidadCicloPropedeuticos() {
        return modalidadCicloPropedeuticos;
    }

    public Modalidad modalidadCicloPropedeuticos(Set<CicloPropedeutico> cicloPropedeuticos) {
        this.modalidadCicloPropedeuticos = cicloPropedeuticos;
        return this;
    }

    public Modalidad addModalidadCicloPropedeutico(CicloPropedeutico cicloPropedeutico) {
        this.modalidadCicloPropedeuticos.add(cicloPropedeutico);
        cicloPropedeutico.setModalidad(this);
        return this;
    }

    public Modalidad removeModalidadCicloPropedeutico(CicloPropedeutico cicloPropedeutico) {
        this.modalidadCicloPropedeuticos.remove(cicloPropedeutico);
        cicloPropedeutico.setModalidad(null);
        return this;
    }

    public void setModalidadCicloPropedeuticos(Set<CicloPropedeutico> cicloPropedeuticos) {
        this.modalidadCicloPropedeuticos = cicloPropedeuticos;
    }

    public Acuerdo getModalidadAcuerdo() {
        return modalidadAcuerdo;
    }

    public Modalidad modalidadAcuerdo(Acuerdo acuerdo) {
        this.modalidadAcuerdo = acuerdo;
        return this;
    }

    public void setModalidadAcuerdo(Acuerdo acuerdo) {
        this.modalidadAcuerdo = acuerdo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Modalidad)) {
            return false;
        }
        return id != null && id.equals(((Modalidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Modalidad{" +
            "id=" + getId() +
            ", modalidad='" + getModalidad() + "'" +
            ", contieneLinea='" + getContieneLinea() + "'" +
            "}";
    }
}
