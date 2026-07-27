package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.PreguntaModalidad} entity.
 */
public class PreguntaModalidadDTO implements Serializable {
    
    private Long id;


    private Long preguntaId;

    private String preguntaPregunta;

    private Long modalidad2Id;

    private String modalidad2Modalidad;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }

    public String getPreguntaPregunta() {
        return preguntaPregunta;
    }

    public void setPreguntaPregunta(String preguntaPregunta) {
        this.preguntaPregunta = preguntaPregunta;
    }

    public Long getModalidad2Id() {
        return modalidad2Id;
    }

    public void setModalidad2Id(Long modalidadId) {
        this.modalidad2Id = modalidadId;
    }

    public String getModalidad2Modalidad() {
        return modalidad2Modalidad;
    }

    public void setModalidad2Modalidad(String modalidadModalidad) {
        this.modalidad2Modalidad = modalidadModalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreguntaModalidadDTO)) {
            return false;
        }

        return id != null && id.equals(((PreguntaModalidadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreguntaModalidadDTO{" +
            "id=" + getId() +
            ", preguntaId=" + getPreguntaId() +
            ", preguntaPregunta='" + getPreguntaPregunta() + "'" +
            ", modalidad2Id=" + getModalidad2Id() +
            ", modalidad2Modalidad='" + getModalidad2Modalidad() + "'" +
            "}";
    }
}
