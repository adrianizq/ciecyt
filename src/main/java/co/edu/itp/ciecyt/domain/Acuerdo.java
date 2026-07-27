package co.edu.itp.ciecyt.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Acuerdo.
 */
@Entity
@Table(name = "acuerdo")
public class Acuerdo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "acuerdo")
    private String acuerdo;

    @Column(name = "version")
    private String version;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "fecha")
    private LocalDate fecha;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcuerdo() {
        return acuerdo;
    }

    public Acuerdo acuerdo(String acuerdo) {
        this.acuerdo = acuerdo;
        return this;
    }

    public void setAcuerdo(String acuerdo) {
        this.acuerdo = acuerdo;
    }

    public String getVersion() {
        return version;
    }

    public Acuerdo version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCodigo() {
        return codigo;
    }

    public Acuerdo codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Acuerdo fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acuerdo)) {
            return false;
        }
        return id != null && id.equals(((Acuerdo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Acuerdo{" +
            "id=" + getId() +
            ", acuerdo='" + getAcuerdo() + "'" +
            ", version='" + getVersion() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
