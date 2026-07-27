package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.RolMenu} entity.
 */
public class RolMenuDTO implements Serializable {

    private Long id;

    private Boolean permitirAcceso;

    private Boolean permitirCrear;

    private Boolean permitirEditar;

    private Boolean permitirEliminar;

    private String authName;


    private Long rolMenuMenuId;

    private String rolMenuMenuNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPermitirAcceso() {
        return permitirAcceso;
    }

    public void setPermitirAcceso(Boolean permitirAcceso) {
        this.permitirAcceso = permitirAcceso;
    }

    public Boolean isPermitirCrear() {
        return permitirCrear;
    }

    public void setPermitirCrear(Boolean permitirCrear) {
        this.permitirCrear = permitirCrear;
    }

    public Boolean isPermitirEditar() {
        return permitirEditar;
    }

    public void setPermitirEditar(Boolean permitirEditar) {
        this.permitirEditar = permitirEditar;
    }

    public Boolean isPermitirEliminar() {
        return permitirEliminar;
    }

    public void setPermitirEliminar(Boolean permitirEliminar) {
        this.permitirEliminar = permitirEliminar;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Long getRolMenuMenuId() {
        return rolMenuMenuId;
    }

    public void setRolMenuMenuId(Long menuId) {
        this.rolMenuMenuId = menuId;
    }

    public String getRolMenuMenuNombre() {
        return rolMenuMenuNombre;
    }

    public void setRolMenuMenuNombre(String menuNombre) {
        this.rolMenuMenuNombre = menuNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RolMenuDTO rolMenuDTO = (RolMenuDTO) o;
        if (rolMenuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rolMenuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RolMenuDTO{" +
            "id=" + getId() +
            ", permitirAcceso='" + isPermitirAcceso() + "'" +
            ", permitirCrear='" + isPermitirCrear() + "'" +
            ", permitirEditar='" + isPermitirEditar() + "'" +
            ", permitirEliminar='" + isPermitirEliminar() + "'" +
            ", authName='" + getAuthName() + "'" +
            ", rolMenuMenu=" + getRolMenuMenuId() +
            ", rolMenuMenu='" + getRolMenuMenuNombre() + "'" +
            "}";
    }
}
