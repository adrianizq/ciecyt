package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Facultad} entity.
 */
public class FacultadDTO implements Serializable {

    private Long id;

    private String codigoFacultad;

    private String facultad;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoFacultad() {
        return codigoFacultad;
    }

    public void setCodigoFacultad(String codigoFacultad) {
        this.codigoFacultad = codigoFacultad;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacultadDTO facultadDTO = (FacultadDTO) o;
        if (facultadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), facultadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FacultadDTO{" +
            "id=" + getId() +
            ", codigoFacultad='" + getCodigoFacultad() + "'" +
            ", facultad='" + getFacultad() + "'" +
            "}";
    }
}
