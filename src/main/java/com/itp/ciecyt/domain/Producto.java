package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "producto")
    private String producto;

    @OneToMany(mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductoProyecto> productoProyectos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public Producto producto(String producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Set<ProductoProyecto> getProductoProyectos() {
        return productoProyectos;
    }

    public Producto productoProyectos(Set<ProductoProyecto> productoProyectos) {
        this.productoProyectos = productoProyectos;
        return this;
    }

    public Producto addProductoProyecto(ProductoProyecto productoProyecto) {
        this.productoProyectos.add(productoProyecto);
        productoProyecto.setProducto(this);
        return this;
    }

    public Producto removeProductoProyecto(ProductoProyecto productoProyecto) {
        this.productoProyectos.remove(productoProyecto);
        productoProyecto.setProducto(null);
        return this;
    }

    public void setProductoProyectos(Set<ProductoProyecto> productoProyectos) {
        this.productoProyectos = productoProyectos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", producto='" + getProducto() + "'" +
            "}";
    }
}
