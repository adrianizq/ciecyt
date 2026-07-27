package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Departamento} entity.
 */
public class DepartamentoDTO implements Serializable {
    
    private Long id;

    private String codigoDane;

    private String departamento;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartamentoDTO)) {
            return false;
        }

        return id != null && id.equals(((DepartamentoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartamentoDTO{" +
            "id=" + getId() +
            ", codigoDane='" + getCodigoDane() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            "}";
    }
}
