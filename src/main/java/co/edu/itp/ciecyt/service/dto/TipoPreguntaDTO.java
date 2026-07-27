package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.TipoPregunta} entity.
 */
public class TipoPreguntaDTO implements Serializable {

    private Long id;

    private String tipoPregunta;

    private String tipoDato;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TipoPreguntaDTO tipoPreguntaDTO = (TipoPreguntaDTO) o;
        if (tipoPreguntaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoPreguntaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoPreguntaDTO{" +
            "id=" + getId() +
            ", tipoPregunta='" + getTipoPregunta() + "'" +
            ", tipoDato='" + getTipoDato() + "'" +
            "}";
    }
}
