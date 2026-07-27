package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CicloPropedeutico.
 */
@Entity
@Table(name = "ciclo_propedeutico")
public class CicloPropedeutico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ciclo")
    private String ciclo;

    @ManyToOne
    @JsonIgnoreProperties("cicloPropedeuticos")
    private Modalidad modalidad;

    @ManyToOne
    @JsonIgnoreProperties("cicloPropedeuticos")
    private Acuerdo cicloPropedeuticoAcuerdo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiclo() {
        return ciclo;
    }

    public CicloPropedeutico ciclo(String ciclo) {
        this.ciclo = ciclo;
        return this;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public CicloPropedeutico modalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
        return this;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Acuerdo getCicloPropedeuticoAcuerdo() {
        return cicloPropedeuticoAcuerdo;
    }

    public CicloPropedeutico cicloPropedeuticoAcuerdo(Acuerdo acuerdo) {
        this.cicloPropedeuticoAcuerdo = acuerdo;
        return this;
    }

    public void setCicloPropedeuticoAcuerdo(Acuerdo acuerdo) {
        this.cicloPropedeuticoAcuerdo = acuerdo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CicloPropedeutico)) {
            return false;
        }
        return id != null && id.equals(((CicloPropedeutico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CicloPropedeutico{" +
            "id=" + getId() +
            ", ciclo='" + getCiclo() + "'" +
            "}";
    }
}
