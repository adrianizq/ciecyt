package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProductoProyecto.
 */
@Entity
@Table(name = "producto_proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
    private Proyecto proyecto;

    @ManyToOne
    @JsonIgnoreProperties("productoProyectos")
    private Producto producto;

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

    public Proyecto getProyecto() {
        return proyecto;
    }

    public ProductoProyecto proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Producto getProducto() {
        return producto;
    }

    public ProductoProyecto producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
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
