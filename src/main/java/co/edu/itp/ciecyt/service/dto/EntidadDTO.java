package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Entidad} entity.
 */
public class EntidadDTO implements Serializable {

    private Long id;

    private String entidad;

    private String nit;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntidadDTO entidadDTO = (EntidadDTO) o;
        if (entidadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entidadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntidadDTO{" +
            "id=" + getId() +
            ", entidad='" + getEntidad() + "'" +
            ", nit='" + getNit() + "'" +
            "}";
    }
}
