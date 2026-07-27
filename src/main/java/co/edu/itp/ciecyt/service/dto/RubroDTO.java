package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Rubro} entity.
 */
public class RubroDTO implements Serializable {

    private Long id;

    private String rubro;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RubroDTO rubroDTO = (RubroDTO) o;
        if (rubroDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rubroDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RubroDTO{" +
            "id=" + getId() +
            ", rubro='" + getRubro() + "'" +
            "}";
    }
}
