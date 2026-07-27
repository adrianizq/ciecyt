package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.CicloPropedeutico} entity.
 */
public class CicloPropedeuticoDTO implements Serializable {

    private Long id;

    private String ciclo;


    private Long modalidadId;

    private Long cicloPropedeuticoAcuerdoId;

    private String cicloPropedeuticoAcuerdoAcuerdo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public Long getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Long modalidadId) {
        this.modalidadId = modalidadId;
    }

    public Long getCicloPropedeuticoAcuerdoId() {
        return cicloPropedeuticoAcuerdoId;
    }

    public void setCicloPropedeuticoAcuerdoId(Long acuerdoId) {
        this.cicloPropedeuticoAcuerdoId = acuerdoId;
    }

    public String getCicloPropedeuticoAcuerdoAcuerdo() {
        return cicloPropedeuticoAcuerdoAcuerdo;
    }

    public void setCicloPropedeuticoAcuerdoAcuerdo(String acuerdoAcuerdo) {
        this.cicloPropedeuticoAcuerdoAcuerdo = acuerdoAcuerdo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CicloPropedeuticoDTO cicloPropedeuticoDTO = (CicloPropedeuticoDTO) o;
        if (cicloPropedeuticoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cicloPropedeuticoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CicloPropedeuticoDTO{" +
            "id=" + getId() +
            ", ciclo='" + getCiclo() + "'" +
            ", modalidad=" + getModalidadId() +
            ", cicloPropedeuticoAcuerdo=" + getCicloPropedeuticoAcuerdoId() +
            ", cicloPropedeuticoAcuerdo='" + getCicloPropedeuticoAcuerdoAcuerdo() + "'" +
            "}";
    }
}
