package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A EntidadFinanciadora.
 */
@Entity
@Table(name = "entidad_financiadora")
public class EntidadFinanciadora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "aprobada")
    private Boolean aprobada;

    @ManyToOne
    @JsonIgnoreProperties("entidadFinanciadoras")
    private Entidad entidadFinanciadoraEntidad;

    @ManyToOne
    @JsonIgnoreProperties("entidadFinanciadoras")
    private Proyecto entidadFinanciadoraProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public EntidadFinanciadora valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean isAprobada() {
        return aprobada;
    }

    public EntidadFinanciadora aprobada(Boolean aprobada) {
        this.aprobada = aprobada;
        return this;
    }

    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public Entidad getEntidadFinanciadoraEntidad() {
        return entidadFinanciadoraEntidad;
    }

    public EntidadFinanciadora entidadFinanciadoraEntidad(Entidad entidad) {
        this.entidadFinanciadoraEntidad = entidad;
        return this;
    }

    public void setEntidadFinanciadoraEntidad(Entidad entidad) {
        this.entidadFinanciadoraEntidad = entidad;
    }

    public Proyecto getEntidadFinanciadoraProyecto() {
        return entidadFinanciadoraProyecto;
    }

    public EntidadFinanciadora entidadFinanciadoraProyecto(Proyecto proyecto) {
        this.entidadFinanciadoraProyecto = proyecto;
        return this;
    }

    public void setEntidadFinanciadoraProyecto(Proyecto proyecto) {
        this.entidadFinanciadoraProyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntidadFinanciadora)) {
            return false;
        }
        return id != null && id.equals(((EntidadFinanciadora) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntidadFinanciadora{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", aprobada='" + isAprobada() + "'" +
            "}";
    }
}
