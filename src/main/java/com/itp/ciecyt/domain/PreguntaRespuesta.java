package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PreguntaRespuesta.
 */
@Entity
@Table(name = "pregunta_respuesta")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PreguntaRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("preguntaRespuestas")
    private Preguntas preguntas;

    @ManyToOne
    @JsonIgnoreProperties("preguntaRespuestas")
    private TipoPregunta tipoPregunta;

    @ManyToOne
    @JsonIgnoreProperties("preguntaRespuestas")
    private IntegrantesProyecto integrantesProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Preguntas getPreguntas() {
        return preguntas;
    }

    public PreguntaRespuesta preguntas(Preguntas preguntas) {
        this.preguntas = preguntas;
        return this;
    }

    public void setPreguntas(Preguntas preguntas) {
        this.preguntas = preguntas;
    }

    public TipoPregunta getTipoPregunta() {
        return tipoPregunta;
    }

    public PreguntaRespuesta tipoPregunta(TipoPregunta tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
        return this;
    }

    public void setTipoPregunta(TipoPregunta tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public IntegrantesProyecto getIntegrantesProyecto() {
        return integrantesProyecto;
    }

    public PreguntaRespuesta integrantesProyecto(IntegrantesProyecto integrantesProyecto) {
        this.integrantesProyecto = integrantesProyecto;
        return this;
    }

    public void setIntegrantesProyecto(IntegrantesProyecto integrantesProyecto) {
        this.integrantesProyecto = integrantesProyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreguntaRespuesta)) {
            return false;
        }
        return id != null && id.equals(((PreguntaRespuesta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PreguntaRespuesta{" +
            "id=" + getId() +
            "}";
    }
}
