package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.ElementoModalidad} entity.
 */
public class ElementoModalidadDTO implements Serializable {
    
    private Long id;


    private Long elementoId;

    private String elementoElemento;

    private Long modalidadId;

    private String modalidadModalidad;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElementoId() {
        return elementoId;
    }

    public void setElementoId(Long elementoId) {
        this.elementoId = elementoId;
    }

    public String getElementoElemento() {
        return elementoElemento;
    }

    public void setElementoElemento(String elementoElemento) {
        this.elementoElemento = elementoElemento;
    }

    public Long getModalidadId() {
        return modalidadId;
    }

    public void setModalidadId(Long modalidadId) {
        this.modalidadId = modalidadId;
    }

    public String getModalidadModalidad() {
        return modalidadModalidad;
    }

    public void setModalidadModalidad(String modalidadModalidad) {
        this.modalidadModalidad = modalidadModalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementoModalidadDTO)) {
            return false;
        }

        return id != null && id.equals(((ElementoModalidadDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementoModalidadDTO{" +
            "id=" + getId() +
            ", elementoId=" + getElementoId() +
            ", elementoElemento='" + getElementoElemento() + "'" +
            ", modalidadId=" + getModalidadId() +
            ", modalidadModalidad='" + getModalidadModalidad() + "'" +
            "}";
    }
}
