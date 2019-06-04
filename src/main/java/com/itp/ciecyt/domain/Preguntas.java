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
 * A Preguntas.
 */
@Entity
@Table(name = "preguntas")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Preguntas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "pregunta")
    private String pregunta;

    @ManyToOne
    @JsonIgnoreProperties("preguntas")
    private Elementos elementos;

    @OneToMany(mappedBy = "preguntas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PreguntaRespuesta> preguntaRespuestas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("preguntas")
    private FaseTrabajo faseTrabajo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public Preguntas pregunta(String pregunta) {
        this.pregunta = pregunta;
        return this;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Elementos getElementos() {
        return elementos;
    }

    public Preguntas elementos(Elementos elementos) {
        this.elementos = elementos;
        return this;
    }

    public void setElementos(Elementos elementos) {
        this.elementos = elementos;
    }

    public Set<PreguntaRespuesta> getPreguntaRespuestas() {
        return preguntaRespuestas;
    }

    public Preguntas preguntaRespuestas(Set<PreguntaRespuesta> preguntaRespuestas) {
        this.preguntaRespuestas = preguntaRespuestas;
        return this;
    }

    public Preguntas addPreguntaRespuesta(PreguntaRespuesta preguntaRespuesta) {
        this.preguntaRespuestas.add(preguntaRespuesta);
        preguntaRespuesta.setPreguntas(this);
        return this;
    }

    public Preguntas removePreguntaRespuesta(PreguntaRespuesta preguntaRespuesta) {
        this.preguntaRespuestas.remove(preguntaRespuesta);
        preguntaRespuesta.setPreguntas(null);
        return this;
    }

    public void setPreguntaRespuestas(Set<PreguntaRespuesta> preguntaRespuestas) {
        this.preguntaRespuestas = preguntaRespuestas;
    }

    public FaseTrabajo getFaseTrabajo() {
        return faseTrabajo;
    }

    public Preguntas faseTrabajo(FaseTrabajo faseTrabajo) {
        this.faseTrabajo = faseTrabajo;
        return this;
    }

    public void setFaseTrabajo(FaseTrabajo faseTrabajo) {
        this.faseTrabajo = faseTrabajo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Preguntas)) {
            return false;
        }
        return id != null && id.equals(((Preguntas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Preguntas{" +
            "id=" + getId() +
            ", pregunta='" + getPregunta() + "'" +
            "}";
    }
}
