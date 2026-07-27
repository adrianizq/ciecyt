package co.edu.itp.ciecyt.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Municipio.
 */
@Entity
@Table(name = "municipio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Municipio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "region")
    private String region;

    @Column(name = "codigo_dane_departamento")
    private String codigoDaneDepartamento;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "codigo_dane_municipio")
    private String codigoDaneMunicipio;

    @Column(name = "municipio")
    private String municipio;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public Municipio region(String region) {
        this.region = region;
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCodigoDaneDepartamento() {
        return codigoDaneDepartamento;
    }

    public Municipio codigoDaneDepartamento(String codigoDaneDepartamento) {
        this.codigoDaneDepartamento = codigoDaneDepartamento;
        return this;
    }

    public void setCodigoDaneDepartamento(String codigoDaneDepartamento) {
        this.codigoDaneDepartamento = codigoDaneDepartamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public Municipio departamento(String departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigoDaneMunicipio() {
        return codigoDaneMunicipio;
    }

    public Municipio codigoDaneMunicipio(String codigoDaneMunicipio) {
        this.codigoDaneMunicipio = codigoDaneMunicipio;
        return this;
    }

    public void setCodigoDaneMunicipio(String codigoDaneMunicipio) {
        this.codigoDaneMunicipio = codigoDaneMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Municipio municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Municipio)) {
            return false;
        }
        return id != null && id.equals(((Municipio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Municipio{" +
            "id=" + getId() +
            ", region='" + getRegion() + "'" +
            ", codigoDaneDepartamento='" + getCodigoDaneDepartamento() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", codigoDaneMunicipio='" + getCodigoDaneMunicipio() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            "}";
    }
}
