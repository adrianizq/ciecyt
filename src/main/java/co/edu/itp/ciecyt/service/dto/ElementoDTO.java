package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Elemento} entity.
 */
public class ElementoDTO implements Serializable {

    private Long id;

    private String elemento;

    private String descripcion;

    private Long orden;

    private Long elementoFasesId;

    private String elementoFasesFase;

    private List<ElementoModalidadDTO> elementoModalidads;

    public List<ElementoModalidadDTO> getElementoModalidads() {
        return elementoModalidads;
    }

    public void setElementoModalidads(List<ElementoModalidadDTO> elementoModalidads) {
        this.elementoModalidads = elementoModalidads;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public Long getElementoFasesId() {
        return elementoFasesId;
    }

    public void setElementoFasesId(Long fasesId) {
        this.elementoFasesId = fasesId;
    }

    public String getElementoFasesFase() {
        return elementoFasesFase;
    }

    public void setElementoFasesFase(String fasesFase) {
        this.elementoFasesFase = fasesFase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementoDTO)) {
            return false;
        }

        return id != null && id.equals(((ElementoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementoDTO{" +
            "id=" + getId() +
            ", elemento='" + getElemento() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", elementoFasesId=" + getElementoFasesId() +
            ", elementoFasesFase='" + getElementoFasesFase() + "'" +
            ", orden='" + getOrden() + "'" +
            "}";
    }
}
