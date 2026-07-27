package co.edu.itp.ciecyt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PreguntaModalidad.
 */
@Entity
@Table(name = "pregunta_modalidad")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PreguntaModalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "preguntaModalidads", allowSetters = true)
    private Pregunta pregunta;

    @ManyToOne
    @JsonIgnoreProperties(value = "preguntaModalidads", allowSetters = true)
    private Modalidad modalidad2;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public PreguntaModalidad pregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
        return this;
    }

    public void setPregunta(Pregunta pregunta) {
        this.pregunta = pregunta;
    }

    public Modalidad getModalidad2() {
        return modalidad2;
    }

    public PreguntaModalidad modalidad2(Modalidad modalidad) {
        this.modalidad2 = modalidad;
        return this;
    }

    public void setModalidad2(Modalidad modalidad) {
        this.modalidad2 = modalidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreguntaModalidad)) {
            return false;
        }
        return id != null && id.equals(((PreguntaModalidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreguntaModalidad{" +
            "id=" + getId() +
            "}";
    }
}
