package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Municipio} entity.
 */
public class MunicipioDTO implements Serializable {
    
    private Long id;

    private String region;

    private String codigoDaneDepartamento;

    private String departamento;

    private String codigoDaneMunicipio;

    private String municipio;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCodigoDaneDepartamento() {
        return codigoDaneDepartamento;
    }

    public void setCodigoDaneDepartamento(String codigoDaneDepartamento) {
        this.codigoDaneDepartamento = codigoDaneDepartamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigoDaneMunicipio() {
        return codigoDaneMunicipio;
    }

    public void setCodigoDaneMunicipio(String codigoDaneMunicipio) {
        this.codigoDaneMunicipio = codigoDaneMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MunicipioDTO)) {
            return false;
        }

        return id != null && id.equals(((MunicipioDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MunicipioDTO{" +
            "id=" + getId() +
            ", region='" + getRegion() + "'" +
            ", codigoDaneDepartamento='" + getCodigoDaneDepartamento() + "'" +
            ", departamento='" + getDepartamento() + "'" +
            ", codigoDaneMunicipio='" + getCodigoDaneMunicipio() + "'" +
            ", municipio='" + getMunicipio() + "'" +
            "}";
    }
}
