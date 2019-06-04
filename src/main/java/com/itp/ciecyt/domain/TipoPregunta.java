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
 * A TipoPregunta.
 */
@Entity
@Table(name = "tipo_pregunta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoPregunta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "tipo_pregunta")
    private String tipoPregunta;

    @Column(name = "tipo_dato")
    private String tipoDato;

    @OneToMany(mappedBy = "tipoPregunta")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PreguntaRespuesta> preguntaRespuestas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoPregunta() {
        return tipoPregunta;
    }

    public TipoPregunta tipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
        return this;
    }

    public void setTipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public TipoPregunta tipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
        return this;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public Set<PreguntaRespuesta> getPreguntaRespuestas() {
        return preguntaRespuestas;
    }

    public TipoPregunta preguntaRespuestas(Set<PreguntaRespuesta> preguntaRespuestas) {
        this.preguntaRespuestas = preguntaRespuestas;
        return this;
    }

    public TipoPregunta addPreguntaRespuesta(PreguntaRespuesta preguntaRespuesta) {
        this.preguntaRespuestas.add(preguntaRespuesta);
        preguntaRespuesta.setTipoPregunta(this);
        return this;
    }

    public TipoPregunta removePreguntaRespuesta(PreguntaRespuesta preguntaRespuesta) {
        this.preguntaRespuestas.remove(preguntaRespuesta);
        preguntaRespuesta.setTipoPregunta(null);
        return this;
    }

    public void setPreguntaRespuestas(Set<PreguntaRespuesta> preguntaRespuestas) {
        this.preguntaRespuestas = preguntaRespuestas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoPregunta)) {
            return false;
        }
        return id != null && id.equals(((TipoPregunta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoPregunta{" +
            "id=" + getId() +
            ", tipoPregunta='" + getTipoPregunta() + "'" +
            ", tipoDato='" + getTipoDato() + "'" +
            "}";
    }
}
