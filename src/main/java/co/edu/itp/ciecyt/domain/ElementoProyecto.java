package co.edu.itp.ciecyt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ElementoProyecto.
 */
@Entity
@Table(name = "elemento_proyecto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ElementoProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "dato")
    private String dato;

    @Column(name = "elemento_proyecto_proyecto_descripcion")
    private String elementoProyectoProyectoDescripcion;

    @Column(name = "elemento_fases_id")
    private Long elementoFasesId;

    @ManyToOne
    @JsonIgnoreProperties(value = "elementoProyectos", allowSetters = true)
    private Elemento elementoProyectoElemento;

    @ManyToOne
    @JsonIgnoreProperties(value = "elementoProyectos", allowSetters = true)
    private Proyecto elementoProyectoProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDato() {
        return dato;
    }

    public ElementoProyecto dato(String dato) {
        this.dato = dato;
        return this;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getElementoProyectoProyectoDescripcion() {
        return elementoProyectoProyectoDescripcion;
    }

    public ElementoProyecto elementoProyectoProyectoDescripcion(String elementoProyectoProyectoDescripcion) {
        this.elementoProyectoProyectoDescripcion = elementoProyectoProyectoDescripcion;
        return this;
    }

    public void setElementoProyectoProyectoDescripcion(String elementoProyectoProyectoDescripcion) {
        this.elementoProyectoProyectoDescripcion = elementoProyectoProyectoDescripcion;
    }

    public Long getElementoFasesId() {
        return elementoFasesId;
    }

    public ElementoProyecto elementoFasesId(Long elementoFasesId) {
        this.elementoFasesId = elementoFasesId;
        return this;
    }

    public void setElementoFasesId(Long elementoFasesId) {
        this.elementoFasesId = elementoFasesId;
    }

    public Elemento getElementoProyectoElemento() {
        return elementoProyectoElemento;
    }

    public ElementoProyecto elementoProyectoElemento(Elemento elemento) {
        this.elementoProyectoElemento = elemento;
        return this;
    }

    public void setElementoProyectoElemento(Elemento elemento) {
        this.elementoProyectoElemento = elemento;
    }

    public Proyecto getElementoProyectoProyecto() {
        return elementoProyectoProyecto;
    }

    public ElementoProyecto elementoProyectoProyecto(Proyecto proyecto) {
        this.elementoProyectoProyecto = proyecto;
        return this;
    }

    public void setElementoProyectoProyecto(Proyecto proyecto) {
        this.elementoProyectoProyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementoProyecto)) {
            return false;
        }
        return id != null && id.equals(((ElementoProyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ElementoProyecto{" +
            "id=" + getId() +
            ", dato='" + getDato() + "'" +
            ", elementoProyectoProyectoDescripcion='" + getElementoProyectoProyectoDescripcion() + "'" +
            ", elementoFasesId=" + getElementoFasesId() +
            "}";
    }
}
