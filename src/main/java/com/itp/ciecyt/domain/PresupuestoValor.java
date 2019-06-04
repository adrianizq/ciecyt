package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PresupuestoValor.
 */
@Entity
@Table(name = "presupuesto_valor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PresupuestoValor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "justificacion")
    private String justificacion;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "valor_unitario")
    private Integer valorUnitario;

    @Column(name = "especie")
    private Double especie;

    @Column(name = "dinero")
    private Double dinero;

    @Column(name = "entidad_financiadora")
    private String entidadFinanciadora;

    @ManyToOne
    @JsonIgnoreProperties("presupuestoValors")
    private Proyecto proyecto;

    @ManyToOne
    @JsonIgnoreProperties("presupuestoValors")
    private Rubros rubros;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public PresupuestoValor descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public PresupuestoValor justificacion(String justificacion) {
        this.justificacion = justificacion;
        return this;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public PresupuestoValor cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getValorUnitario() {
        return valorUnitario;
    }

    public PresupuestoValor valorUnitario(Integer valorUnitario) {
        this.valorUnitario = valorUnitario;
        return this;
    }

    public void setValorUnitario(Integer valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getEspecie() {
        return especie;
    }

    public PresupuestoValor especie(Double especie) {
        this.especie = especie;
        return this;
    }

    public void setEspecie(Double especie) {
        this.especie = especie;
    }

    public Double getDinero() {
        return dinero;
    }

    public PresupuestoValor dinero(Double dinero) {
        this.dinero = dinero;
        return this;
    }

    public void setDinero(Double dinero) {
        this.dinero = dinero;
    }

    public String getEntidadFinanciadora() {
        return entidadFinanciadora;
    }

    public PresupuestoValor entidadFinanciadora(String entidadFinanciadora) {
        this.entidadFinanciadora = entidadFinanciadora;
        return this;
    }

    public void setEntidadFinanciadora(String entidadFinanciadora) {
        this.entidadFinanciadora = entidadFinanciadora;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public PresupuestoValor proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Rubros getRubros() {
        return rubros;
    }

    public PresupuestoValor rubros(Rubros rubros) {
        this.rubros = rubros;
        return this;
    }

    public void setRubros(Rubros rubros) {
        this.rubros = rubros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PresupuestoValor)) {
            return false;
        }
        return id != null && id.equals(((PresupuestoValor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PresupuestoValor{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", justificacion='" + getJustificacion() + "'" +
            ", cantidad=" + getCantidad() +
            ", valorUnitario=" + getValorUnitario() +
            ", especie=" + getEspecie() +
            ", dinero=" + getDinero() +
            ", entidadFinanciadora='" + getEntidadFinanciadora() + "'" +
            "}";
    }
}
