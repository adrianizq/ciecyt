package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.ElementoProyecto} entity.
 */
public class ElementoProyectoDTO implements Serializable {
    
    private Long id;

    private String dato;

    private String elementoProyectoProyectoDescripcion;

    private Long elementoFasesId;


    private Long elementoProyectoElementoId;

    private String elementoProyectoElementoElemento;

    private Long elementoProyectoProyectoId;

    private String elementoProyectoProyectoTitulo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getElementoProyectoProyectoDescripcion() {
        return elementoProyectoProyectoDescripcion;
    }

    public void setElementoProyectoProyectoDescripcion(String elementoProyectoProyectoDescripcion) {
        this.elementoProyectoProyectoDescripcion = elementoProyectoProyectoDescripcion;
    }

    public Long getElementoFasesId() {
        return elementoFasesId;
    }

    public void setElementoFasesId(Long elementoFasesId) {
        this.elementoFasesId = elementoFasesId;
    }

    public Long getElementoProyectoElementoId() {
        return elementoProyectoElementoId;
    }

    public void setElementoProyectoElementoId(Long elementoId) {
        this.elementoProyectoElementoId = elementoId;
    }

    public String getElementoProyectoElementoElemento() {
        return elementoProyectoElementoElemento;
    }

    public void setElementoProyectoElementoElemento(String elementoElemento) {
        this.elementoProyectoElementoElemento = elementoElemento;
    }

    public Long getElementoProyectoProyectoId() {
        return elementoProyectoProyectoId;
    }

    public void setElementoProyectoProyectoId(Long proyectoId) {
        this.elementoProyectoProyectoId = proyectoId;
    }

    public String getElementoProyectoProyectoTitulo() {
        return elementoProyectoProyectoTitulo;
    }

    public void setElementoProyectoProyectoTitulo(String proyectoTitulo) {
        this.elementoProyectoProyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementoProyectoDTO)) {
            return false;
        }

        return id != null && id.equals(((ElementoProyectoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementoProyectoDTO{" +
            "id=" + getId() +
            ", dato='" + getDato() + "'" +
            ", elementoProyectoProyectoDescripcion='" + getElementoProyectoProyectoDescripcion() + "'" +
            ", elementoFasesId=" + getElementoFasesId() +
            ", elementoProyectoElementoId=" + getElementoProyectoElementoId() +
            ", elementoProyectoElementoElemento='" + getElementoProyectoElementoElemento() + "'" +
            ", elementoProyectoProyectoId=" + getElementoProyectoProyectoId() +
            ", elementoProyectoProyectoTitulo='" + getElementoProyectoProyectoTitulo() + "'" +
            "}";
    }
}
