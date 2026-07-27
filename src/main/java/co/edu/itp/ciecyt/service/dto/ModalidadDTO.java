package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Modalidad} entity.
 */
public class ModalidadDTO implements Serializable {

    private Long id;

    private String modalidad;


    private Long modalidadAcuerdoId;

    private String modalidadAcuerdoAcuerdo;

    private boolean contieneLinea;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    ///////////////////////////
    public boolean getContieneLinea() {
        return contieneLinea;
    }

    /*public Modalidad contieneLinea(boolean contieneLinea) {
        this.contieneLinea = contieneLinea;
        return this;
    }*/

    public void setContieneLinea(boolean contieneLinea) {
        this.contieneLinea = contieneLinea;
    }
    ////////////////////////////////////////7

    public Long getModalidadAcuerdoId() {
        return modalidadAcuerdoId;
    }

    public void setModalidadAcuerdoId(Long acuerdoId) {
        this.modalidadAcuerdoId = acuerdoId;
    }

    public String getModalidadAcuerdoAcuerdo() {
        return modalidadAcuerdoAcuerdo;
    }

    public void setModalidadAcuerdoAcuerdo(String acuerdoAcuerdo) {
        this.modalidadAcuerdoAcuerdo = acuerdoAcuerdo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ModalidadDTO modalidadDTO = (ModalidadDTO) o;
        if (modalidadDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), modalidadDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ModalidadDTO{" +
            "id=" + getId() +
            ", modalidad='" + getModalidad() + "'" +
            ", contieneLinea='" + getContieneLinea() + "'" +
            ", modalidadAcuerdo=" + getModalidadAcuerdoId() +
            ", modalidadAcuerdo='" + getModalidadAcuerdoAcuerdo() + "'" +
            "}";
    }
}
