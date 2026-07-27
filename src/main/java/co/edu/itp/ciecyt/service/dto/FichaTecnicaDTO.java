package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.FichaTecnica} entity.
 */
public class FichaTecnicaDTO implements Serializable {

    private Long id;

    private String tituloProfesional;

    private String tituloPostgrado;

    private String experiencia;


    private Long fichaTecnicaUserId;

    private String fichaTecnicaUserLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloProfesional() {
        return tituloProfesional;
    }

    public void setTituloProfesional(String tituloProfesional) {
        this.tituloProfesional = tituloProfesional;
    }

    public String getTituloPostgrado() {
        return tituloPostgrado;
    }

    public void setTituloPostgrado(String tituloPostgrado) {
        this.tituloPostgrado = tituloPostgrado;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public Long getFichaTecnicaUserId() {
        return fichaTecnicaUserId;
    }

    public void setFichaTecnicaUserId(Long userId) {
        this.fichaTecnicaUserId = userId;
    }

    public String getFichaTecnicaUserLogin() {
        return fichaTecnicaUserLogin;
    }

    public void setFichaTecnicaUserLogin(String userLogin) {
        this.fichaTecnicaUserLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FichaTecnicaDTO fichaTecnicaDTO = (FichaTecnicaDTO) o;
        if (fichaTecnicaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichaTecnicaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichaTecnicaDTO{" +
            "id=" + getId() +
            ", tituloProfesional='" + getTituloProfesional() + "'" +
            ", tituloPostgrado='" + getTituloPostgrado() + "'" +
            ", experiencia='" + getExperiencia() + "'" +
            ", fichaTecnicaUser=" + getFichaTecnicaUserId() +
            ", fichaTecnicaUser='" + getFichaTecnicaUserLogin() + "'" +
            "}";
    }
}
