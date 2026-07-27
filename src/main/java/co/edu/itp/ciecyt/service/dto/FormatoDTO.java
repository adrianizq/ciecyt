package co.edu.itp.ciecyt.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Formato} entity.
 */
public class FormatoDTO implements Serializable {

    private Long id;

    private String formato;

    private String version;

    private String codigo;

    private LocalDate fecha;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FormatoDTO formatoDTO = (FormatoDTO) o;
        if (formatoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), formatoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FormatoDTO{" +
            "id=" + getId() +
            ", formato='" + getFormato() + "'" +
            ", version='" + getVersion() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
