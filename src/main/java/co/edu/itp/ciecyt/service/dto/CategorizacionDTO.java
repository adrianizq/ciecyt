package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Categorizacion} entity.
 */
public class CategorizacionDTO implements Serializable {

    private Long id;

    private String categoria;

    private String descripcion;


    private Long categorizacionProyectoId;

    private String categorizacionProyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getCategorizacionProyectoId() {
        return categorizacionProyectoId;
    }

    public void setCategorizacionProyectoId(Long proyectoId) {
        this.categorizacionProyectoId = proyectoId;
    }

    public String getCategorizacionProyectoTitulo() {
        return categorizacionProyectoTitulo;
    }

    public void setCategorizacionProyectoTitulo(String proyectoTitulo) {
        this.categorizacionProyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategorizacionDTO categorizacionDTO = (CategorizacionDTO) o;
        if (categorizacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), categorizacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CategorizacionDTO{" +
            "id=" + getId() +
            ", categoria='" + getCategoria() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", categorizacionProyecto=" + getCategorizacionProyectoId() +
            ", categorizacionProyecto='" + getCategorizacionProyectoTitulo() + "'" +
            "}";
    }
}
