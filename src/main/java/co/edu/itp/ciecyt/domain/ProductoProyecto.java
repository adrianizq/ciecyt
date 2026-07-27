package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ProductoProyecto.
 */
@Entity
@Table(name = "producto_proyecto")
public class ProductoProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "aplica")
    private Boolean aplica;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JsonIgnoreProperties("productoProyectos")
    private Producto productoProyectoProducto;

    @ManyToOne
    @JsonIgnoreProperties("productoProyectos")
    private Proyecto productoProyectoProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAplica() {
        return aplica;
    }

    public ProductoProyecto aplica(Boolean aplica) {
        this.aplica = aplica;
        return this;
    }

    public void setAplica(Boolean aplica) {
        this.aplica = aplica;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ProductoProyecto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Producto getProductoProyectoProducto() {
        return productoProyectoProducto;
    }

    public ProductoProyecto productoProyectoProducto(Producto producto) {
        this.productoProyectoProducto = producto;
        return this;
    }

    public void setProductoProyectoProducto(Producto producto) {
        this.productoProyectoProducto = producto;
    }

    public Proyecto getProductoProyectoProyecto() {
        return productoProyectoProyecto;
    }

    public ProductoProyecto productoProyectoProyecto(Proyecto proyecto) {
        this.productoProyectoProyecto = proyecto;
        return this;
    }

    public void setProductoProyectoProyecto(Proyecto proyecto) {
        this.productoProyectoProyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductoProyecto)) {
            return false;
        }
        return id != null && id.equals(((ProductoProyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductoProyecto{" +
            "id=" + getId() +
            ", aplica='" + isAplica() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
