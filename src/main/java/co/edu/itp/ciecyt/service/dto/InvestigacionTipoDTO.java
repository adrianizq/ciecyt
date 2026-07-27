package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.InvestigacionTipo} entity.
 */
public class InvestigacionTipoDTO implements Serializable {
    
    private Long id;

    private String investigacionTipo;

    private String investigacionTipoDescripcion;

    private String tipo;

    private String tipoDescripcion;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvestigacionTipo() {
        return investigacionTipo;
    }

    public void setInvestigacionTipo(String investigacionTipo) {
        this.investigacionTipo = investigacionTipo;
    }

    public String getInvestigacionTipoDescripcion() {
        return investigacionTipoDescripcion;
    }

    public void setInvestigacionTipoDescripcion(String investigacionTipoDescripcion) {
        this.investigacionTipoDescripcion = investigacionTipoDescripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipoDescripcion() {
        return tipoDescripcion;
    }

    public void setTipoDescripcion(String tipoDescripcion) {
        this.tipoDescripcion = tipoDescripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvestigacionTipoDTO)) {
            return false;
        }

        return id != null && id.equals(((InvestigacionTipoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvestigacionTipoDTO{" +
            "id=" + getId() +
            ", investigacionTipo='" + getInvestigacionTipo() + "'" +
            ", investigacionTipoDescripcion='" + getInvestigacionTipoDescripcion() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", tipoDescripcion='" + getTipoDescripcion() + "'" +
            "}";
    }
}
