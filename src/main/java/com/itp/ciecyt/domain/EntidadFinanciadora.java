package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A EntidadFinanciadora.
 */
@Entity
@Table(name = "entidad_financiadora")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EntidadFinanciadora implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "estado_financiacion")
    private Boolean estadoFinanciacion;

    @ManyToOne
    @JsonIgnoreProperties("entidadFinanciadoras")
    private Proyecto proyecto;

    @OneToMany(mappedBy = "entidadFinanciadora")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Empresas> empresas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public EntidadFinanciadora valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean isEstadoFinanciacion() {
        return estadoFinanciacion;
    }

    public EntidadFinanciadora estadoFinanciacion(Boolean estadoFinanciacion) {
        this.estadoFinanciacion = estadoFinanciacion;
        return this;
    }

    public void setEstadoFinanciacion(Boolean estadoFinanciacion) {
        this.estadoFinanciacion = estadoFinanciacion;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public EntidadFinanciadora proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Set<Empresas> getEmpresas() {
        return empresas;
    }

    public EntidadFinanciadora empresas(Set<Empresas> empresas) {
        this.empresas = empresas;
        return this;
    }

    public EntidadFinanciadora addEmpresas(Empresas empresas) {
        this.empresas.add(empresas);
        empresas.setEntidadFinanciadora(this);
        return this;
    }

    public EntidadFinanciadora removeEmpresas(Empresas empresas) {
        this.empresas.remove(empresas);
        empresas.setEntidadFinanciadora(null);
        return this;
    }

    public void setEmpresas(Set<Empresas> empresas) {
        this.empresas = empresas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntidadFinanciadora)) {
            return false;
        }
        return id != null && id.equals(((EntidadFinanciadora) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntidadFinanciadora{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", estadoFinanciacion='" + isEstadoFinanciacion() + "'" +
            "}";
    }
}
