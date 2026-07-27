package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A LineaInvestigacion.
 */
@Entity
@Table(name = "linea_investigacion")
public class LineaInvestigacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "linea")
    private String linea;

    @Column(name = "codigo_linea")
    private String codigoLinea;

    @ManyToOne
    @JsonIgnoreProperties("lineaInvestigacions")
    private LineaInvestigacion lineaPadre;

    @ManyToOne
    @JsonIgnoreProperties("lineaInvestigacions")
    private Programa lineaInvestigacionPrograma;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLinea() {
        return linea;
    }

    public LineaInvestigacion linea(String linea) {
        this.linea = linea;
        return this;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public String getCodigoLinea() {
        return codigoLinea;
    }

    public LineaInvestigacion codigoLinea(String codigoLinea) {
        this.codigoLinea = codigoLinea;
        return this;
    }

    public void setCodigoLinea(String codigoLinea) {
        this.codigoLinea = codigoLinea;
    }

    public LineaInvestigacion getLineaPadre() {
        return lineaPadre;
    }

    public LineaInvestigacion lineaPadre(LineaInvestigacion lineaInvestigacion) {
        this.lineaPadre = lineaInvestigacion;
        return this;
    }

    public void setLineaPadre(LineaInvestigacion lineaInvestigacion) {
        this.lineaPadre = lineaInvestigacion;
    }

    public Programa getLineaInvestigacionPrograma() {
        return lineaInvestigacionPrograma;
    }

    public LineaInvestigacion lineaInvestigacionPrograma(Programa programa) {
        this.lineaInvestigacionPrograma = programa;
        return this;
    }

    public void setLineaInvestigacionPrograma(Programa programa) {
        this.lineaInvestigacionPrograma = programa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LineaInvestigacion)) {
            return false;
        }
        return id != null && id.equals(((LineaInvestigacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LineaInvestigacion{" +
            "id=" + getId() +
            ", linea='" + getLinea() + "'" +
            ", codigoLinea='" + getCodigoLinea() + "'" +
            "}";
    }
}
