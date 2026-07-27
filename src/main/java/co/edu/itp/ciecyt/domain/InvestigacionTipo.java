package co.edu.itp.ciecyt.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A InvestigacionTipo.
 */
@Entity
@Table(name = "investigacion_tipo")
public class InvestigacionTipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "investigacion_tipo")
    private String investigacionTipo;

    @Column(name = "investigacion_tipo_descripcion")
    private String investigacionTipoDescripcion;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "tipo_descripcion")
    private String tipoDescripcion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvestigacionTipo() {
        return investigacionTipo;
    }

    public InvestigacionTipo investigacionTipo(String investigacionTipo) {
        this.investigacionTipo = investigacionTipo;
        return this;
    }

    public void setInvestigacionTipo(String investigacionTipo) {
        this.investigacionTipo = investigacionTipo;
    }

    public String getInvestigacionTipoDescripcion() {
        return investigacionTipoDescripcion;
    }

    public InvestigacionTipo investigacionTipoDescripcion(String investigacionTipoDescripcion) {
        this.investigacionTipoDescripcion = investigacionTipoDescripcion;
        return this;
    }

    public void setInvestigacionTipoDescripcion(String investigacionTipoDescripcion) {
        this.investigacionTipoDescripcion = investigacionTipoDescripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public InvestigacionTipo tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoDescripcion() {
        return tipoDescripcion;
    }

    public InvestigacionTipo tipoDescripcion(String tipoDescripcion) {
        this.tipoDescripcion = tipoDescripcion;
        return this;
    }

    public void setTipoDescripcion(String tipoDescripcion) {
        this.tipoDescripcion = tipoDescripcion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvestigacionTipo)) {
            return false;
        }
        return id != null && id.equals(((InvestigacionTipo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvestigacionTipo{" +
            "id=" + getId() +
            ", investigacionTipo='" + getInvestigacionTipo() + "'" +
            ", investigacionTipoDescripcion='" + getInvestigacionTipoDescripcion() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", tipoDescripcion='" + getTipoDescripcion() + "'" +
            "}";
    }
}
