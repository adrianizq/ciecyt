package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ImpactosEsperados.
 */
@Entity
@Table(name = "impactos_esperados")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImpactosEsperados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "impacto")
    private String impacto;

    @Column(name = "plazo")
    private Integer plazo;

    @Column(name = "indicador")
    private String indicador;

    @Column(name = "supuestos")
    private String supuestos;

    @ManyToOne
    @JsonIgnoreProperties("impactosEsperados")
    private Proyecto proyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImpacto() {
        return impacto;
    }

    public ImpactosEsperados impacto(String impacto) {
        this.impacto = impacto;
        return this;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public ImpactosEsperados plazo(Integer plazo) {
        this.plazo = plazo;
        return this;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public String getIndicador() {
        return indicador;
    }

    public ImpactosEsperados indicador(String indicador) {
        this.indicador = indicador;
        return this;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getSupuestos() {
        return supuestos;
    }

    public ImpactosEsperados supuestos(String supuestos) {
        this.supuestos = supuestos;
        return this;
    }

    public void setSupuestos(String supuestos) {
        this.supuestos = supuestos;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public ImpactosEsperados proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImpactosEsperados)) {
            return false;
        }
        return id != null && id.equals(((ImpactosEsperados) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ImpactosEsperados{" +
            "id=" + getId() +
            ", impacto='" + getImpacto() + "'" +
            ", plazo=" + getPlazo() +
            ", indicador='" + getIndicador() + "'" +
            ", supuestos='" + getSupuestos() + "'" +
            "}";
    }
}
