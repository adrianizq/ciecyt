package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.RolesModalidad} entity.
 */
public class RolesModalidadDTO implements Serializable {
    
    private Long id;

    private String rol;

    private Integer cantidad;

    private Boolean calificador;


    private Long rolesModalidadModalidadId;

    private String rolesModalidadModalidadModalidad;

    private String rolesModalidadAuthorityName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean isCalificador() {
        return calificador;
    }

    public void setCalificador(Boolean calificador) {
        this.calificador = calificador;
    }

    public Long getRolesModalidadModalidadId() {
        return rolesModalidadModalidadId;
    }

    public void setRolesModalidadModalidadId(Long modalidadId) {
        this.rolesModalidadModalidadId = modalidadId;
    }

    public String getRolesModalidadModalidadModalidad() {
        return rolesModalidadModalidadModalidad;
    }

    public void setRolesModalidadModalidadModalidad(String modalidadModalidad) {
        this.rolesModalidadModalidadModalidad = modalidadModalidad;
    }

    public String getRolesModalidadAuthorityName() {
        return rolesModalidadAuthorityName;
    }

    public void setRolesModalidadAuthorityName(String authorityName) {
        this.rolesModalidadAuthorityName = authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RolesModalidadDTO)) {
            return false;
        }

        return id != null && id.equals(((RolesModalidadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RolesModalidadDTO{" +
            "id=" + getId() +
            ", rol='" + getRol() + "'" +
            ", cantidad=" + getCantidad() +
            ", calificador='" + isCalificador() + "'" +
            ", rolesModalidadModalidadId=" + getRolesModalidadModalidadId() +
            ", rolesModalidadModalidadModalidad='" + getRolesModalidadModalidadModalidad() + "'" +
            ", rolesModalidadAuthorityName='" + getRolesModalidadAuthorityName() + "'" +
            "}";
    }
}
