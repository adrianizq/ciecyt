package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ResultadosEsperados.
 */
@Entity
@Table(name = "resultados_esperados")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResultadosEsperados implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "indicador")
    private String indicador;

    @Column(name = "beneficiario")
    private String beneficiario;

    @ManyToOne
    @JsonIgnoreProperties("resultadosEsperados")
    private Proyecto proyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public ResultadosEsperados resultado(String resultado) {
        this.resultado = resultado;
        return this;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getIndicador() {
        return indicador;
    }

    public ResultadosEsperados indicador(String indicador) {
        this.indicador = indicador;
        return this;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public ResultadosEsperados beneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
        return this;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public ResultadosEsperados proyecto(Proyecto proyecto) {
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
        if (!(o instanceof ResultadosEsperados)) {
            return false;
        }
        return id != null && id.equals(((ResultadosEsperados) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ResultadosEsperados{" +
            "id=" + getId() +
            ", resultado='" + getResultado() + "'" +
            ", indicador='" + getIndicador() + "'" +
            ", beneficiario='" + getBeneficiario() + "'" +
            "}";
    }
}
