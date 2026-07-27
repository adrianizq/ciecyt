package co.edu.itp.ciecyt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Elemento.
 */
@Entity
@Table(name = "elemento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Elemento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "elemento")
    private String elemento;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "orden")
    private Long orden;

    @ManyToOne
    @JsonIgnoreProperties(value = "elementos", allowSetters = true)
    private Fases elementoFases;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getElemento() {
        return elemento;
    }

    public Elemento elemento(String elemento) {
        this.elemento = elemento;
        return this;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Elemento descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Elemento orden(Long orden) {
        this.orden = orden;
        return this;
    }



    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public Fases getElementoFases() {
        return elementoFases;
    }

    public Elemento elementoFases(Fases fases) {
        this.elementoFases = fases;
        return this;
    }

    public void setElementoFases(Fases fases) {
        this.elementoFases = fases;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Elemento)) {
            return false;
        }
        return id != null && id.equals(((Elemento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Elemento{" +
            "id=" + getId() +
            ", elemento='" + getElemento() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", orden='" + getOrden() + "'" +
            "}";
    }
}
