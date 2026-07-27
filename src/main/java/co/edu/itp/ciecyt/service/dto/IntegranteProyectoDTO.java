package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.IntegranteProyecto} entity.
 */
public class IntegranteProyectoDTO implements Serializable {

    private Long id;

    private String integrante;

    private String descripcion;


    private Long integranteProyectoUserId;

    private String integranteProyectoUserLogin;

    private Long integranteProyectoProyectoId;

    private String integranteProyectoProyectoTitulo;

    private Long integranteProyectoRolesModalidadId;

    private String integranteProyectoRolesModalidadRol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntegrante() {
        return integrante;
    }

    public void setIntegrante(String integrante) {
        this.integrante = integrante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIntegranteProyectoUserId() {
        return integranteProyectoUserId;
    }

    public void setIntegranteProyectoUserId(Long userId) {
        this.integranteProyectoUserId = userId;
    }

    public String getIntegranteProyectoUserLogin() {
        return integranteProyectoUserLogin;
    }

    public void setIntegranteProyectoUserLogin(String userLogin) {
        this.integranteProyectoUserLogin = userLogin;
    }

    public Long getIntegranteProyectoProyectoId() {
        return integranteProyectoProyectoId;
    }

    public void setIntegranteProyectoProyectoId(Long proyectoId) {
        this.integranteProyectoProyectoId = proyectoId;
    }

    public String getIntegranteProyectoProyectoTitulo() {
        return integranteProyectoProyectoTitulo;
    }

    public void setIntegranteProyectoProyectoTitulo(String proyectoTitulo) {
        this.integranteProyectoProyectoTitulo = proyectoTitulo;
    }

    public Long getIntegranteProyectoRolesModalidadId() {
        return integranteProyectoRolesModalidadId;
    }

    public void setIntegranteProyectoRolesModalidadId(Long rolesModalidadId) {
        this.integranteProyectoRolesModalidadId = rolesModalidadId;
    }

    public String getIntegranteProyectoRolesModalidadRol() {
        return integranteProyectoRolesModalidadRol;
    }

    public void setIntegranteProyectoRolesModalidadRol(String rolesModalidadRol) {
        this.integranteProyectoRolesModalidadRol = rolesModalidadRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntegranteProyectoDTO integranteProyectoDTO = (IntegranteProyectoDTO) o;
        if (integranteProyectoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), integranteProyectoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IntegranteProyectoDTO{" +
            "id=" + getId() +
            ", integrante='" + getIntegrante() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", integranteProyectoUser=" + getIntegranteProyectoUserId() +
            ", integranteProyectoUser='" + getIntegranteProyectoUserLogin() + "'" +
            ", integranteProyectoProyecto=" + getIntegranteProyectoProyectoId() +
            ", integranteProyectoProyecto='" + getIntegranteProyectoProyectoTitulo() + "'" +
            ", integranteProyectoRolesModalidad=" + getIntegranteProyectoRolesModalidadId() +
            ", integranteProyectoRolesModalidad='" + getIntegranteProyectoRolesModalidadRol() + "'" +
            "}";
    }
}
