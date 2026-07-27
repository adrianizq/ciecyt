package co.edu.itp.ciecyt.service.dto;

import co.edu.itp.ciecyt.domain.PreguntaAuthority;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.PreguntaAuthority} entity.
 */
public class PreguntaAuthorityDTO implements Serializable {

    private Long id;


    private Long pregunta3Id;

    private String pregunta3Pregunta;

    private String authorityName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPregunta3Id() {
        return pregunta3Id;
    }

    public void setPregunta3Id(Long preguntaId) {
        this.pregunta3Id = preguntaId;
    }

    public String getPregunta3Pregunta() {
        return pregunta3Pregunta;
    }

    public void setPregunta3Pregunta(String preguntaPregunta) {
        this.pregunta3Pregunta = preguntaPregunta;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreguntaAuthorityDTO)) {
            return false;
        }

        return id != null && id.equals(((PreguntaAuthorityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreguntaAuthorityDTO{" +
            "id=" + getId() +
            ", pregunta3Id=" + getPregunta3Id() +
            ", pregunta3Pregunta='" + getPregunta3Pregunta() + "'" +
            ", authorityName='" + getAuthorityName() + "'" +
            "}";
    }
}
