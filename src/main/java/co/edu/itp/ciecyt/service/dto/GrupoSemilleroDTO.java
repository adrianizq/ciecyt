package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.GrupoSemillero} entity.
 */
public class GrupoSemilleroDTO implements Serializable {

    private Long id;

    private String nombre;

    private Boolean tipo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean isTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GrupoSemilleroDTO grupoSemilleroDTO = (GrupoSemilleroDTO) o;
        if (grupoSemilleroDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), grupoSemilleroDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GrupoSemilleroDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", tipo='" + isTipo() + "'" +
            "}";
    }
}
