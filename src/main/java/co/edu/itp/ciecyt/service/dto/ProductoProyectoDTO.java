package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.ProductoProyecto} entity.
 */
public class ProductoProyectoDTO implements Serializable {

    private Long id;

    private Boolean aplica;

    private String descripcion;


    private Long productoProyectoProductoId;

    private String productoProyectoProductoProducto;

    private Long productoProyectoProyectoId;

    private String productoProyectoProyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAplica() {
        return aplica;
    }

    public void setAplica(Boolean aplica) {
        this.aplica = aplica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getProductoProyectoProductoId() {
        return productoProyectoProductoId;
    }

    public void setProductoProyectoProductoId(Long productoId) {
        this.productoProyectoProductoId = productoId;
    }

    public String getProductoProyectoProductoProducto() {
        return productoProyectoProductoProducto;
    }

    public void setProductoProyectoProductoProducto(String productoProducto) {
        this.productoProyectoProductoProducto = productoProducto;
    }

    public Long getProductoProyectoProyectoId() {
        return productoProyectoProyectoId;
    }

    public void setProductoProyectoProyectoId(Long proyectoId) {
        this.productoProyectoProyectoId = proyectoId;
    }

    public String getProductoProyectoProyectoTitulo() {
        return productoProyectoProyectoTitulo;
    }

    public void setProductoProyectoProyectoTitulo(String proyectoTitulo) {
        this.productoProyectoProyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoProyectoDTO productoProyectoDTO = (ProductoProyectoDTO) o;
        if (productoProyectoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoProyectoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoProyectoDTO{" +
            "id=" + getId() +
            ", aplica='" + isAplica() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", productoProyectoProducto=" + getProductoProyectoProductoId() +
            ", productoProyectoProducto='" + getProductoProyectoProductoProducto() + "'" +
            ", productoProyectoProyecto=" + getProductoProyectoProyectoId() +
            ", productoProyectoProyecto='" + getProductoProyectoProyectoTitulo() + "'" +
            "}";
    }
}
