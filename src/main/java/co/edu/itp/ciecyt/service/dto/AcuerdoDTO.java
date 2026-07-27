package co.edu.itp.ciecyt.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Acuerdo} entity.
 */
public class AcuerdoDTO implements Serializable {

    private Long id;

    private String acuerdo;

    private String version;

    private String codigo;

    private LocalDate fecha;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcuerdo() {
        return acuerdo;
    }

    public void setAcuerdo(String acuerdo) {
        this.acuerdo = acuerdo;
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

        AcuerdoDTO acuerdoDTO = (AcuerdoDTO) o;
        if (acuerdoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acuerdoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AcuerdoDTO{" +
            "id=" + getId() +
            ", acuerdo='" + getAcuerdo() + "'" +
            ", version='" + getVersion() + "'" +
            ", codigo='" + getCodigo() + "'" +
            ", fecha='" + getFecha() + "'" +
            "}";
    }
}
