package co.edu.itp.ciecyt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Programa.
 */
@Entity
@Table(name = "programa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Programa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "programa")
    private String programa;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "codigo_interno")
    private String codigoInterno;

    @Column(name = "codigo_snies")
    private String codigoSnies;

    @Column(name = "creditos")
    private Integer creditos;

    @Column(name = "ciclo")
    private String ciclo;

    @Column(name = "resolucion")
    private String resolucion;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "duracion_semestres")
    private Integer duracionSemestres;

    @ManyToOne
    @JsonIgnoreProperties(value = "programas", allowSetters = true)
    private Facultad programaFacultad;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrograma() {
        return programa;
    }

    public Programa programa(String programa) {
        this.programa = programa;
        return this;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Programa descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public Programa codigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
        return this;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoSnies() {
        return codigoSnies;
    }

    public Programa codigoSnies(String codigoSnies) {
        this.codigoSnies = codigoSnies;
        return this;
    }

    public void setCodigoSnies(String codigoSnies) {
        this.codigoSnies = codigoSnies;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public Programa creditos(Integer creditos) {
        this.creditos = creditos;
        return this;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getCiclo() {
        return ciclo;
    }

    public Programa ciclo(String ciclo) {
        this.ciclo = ciclo;
        return this;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getResolucion() {
        return resolucion;
    }

    public Programa resolucion(String resolucion) {
        this.resolucion = resolucion;
        return this;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getTitulo() {
        return titulo;
    }

    public Programa titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracionSemestres() {
        return duracionSemestres;
    }

    public Programa duracionSemestres(Integer duracionSemestres) {
        this.duracionSemestres = duracionSemestres;
        return this;
    }

    public void setDuracionSemestres(Integer duracionSemestres) {
        this.duracionSemestres = duracionSemestres;
    }

    public Facultad getProgramaFacultad() {
        return programaFacultad;
    }

    public Programa programaFacultad(Facultad facultad) {
        this.programaFacultad = facultad;
        return this;
    }

    public void setProgramaFacultad(Facultad facultad) {
        this.programaFacultad = facultad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Programa)) {
            return false;
        }
        return id != null && id.equals(((Programa) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Programa{" +
            "id=" + getId() +
            ", programa='" + getPrograma() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", codigoInterno='" + getCodigoInterno() + "'" +
            ", codigoSnies='" + getCodigoSnies() + "'" +
            ", creditos=" + getCreditos() +
            ", ciclo='" + getCiclo() + "'" +
            ", resolucion='" + getResolucion() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", duracionSemestres=" + getDuracionSemestres() +
            "}";
    }
}
