package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A FichaTecnica.
 */
@Entity
@Table(name = "ficha_tecnica")
public class FichaTecnica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo_profesional")
    private String tituloProfesional;

    @Column(name = "titulo_postgrado")
    private String tituloPostgrado;

    @Column(name = "experiencia")
    private String experiencia;

    @ManyToOne
    @JsonIgnoreProperties("fichaTecnicas")
    private User fichaTecnicaUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloProfesional() {
        return tituloProfesional;
    }

    public FichaTecnica tituloProfesional(String tituloProfesional) {
        this.tituloProfesional = tituloProfesional;
        return this;
    }

    public void setTituloProfesional(String tituloProfesional) {
        this.tituloProfesional = tituloProfesional;
    }

    public String getTituloPostgrado() {
        return tituloPostgrado;
    }

    public FichaTecnica tituloPostgrado(String tituloPostgrado) {
        this.tituloPostgrado = tituloPostgrado;
        return this;
    }

    public void setTituloPostgrado(String tituloPostgrado) {
        this.tituloPostgrado = tituloPostgrado;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public FichaTecnica experiencia(String experiencia) {
        this.experiencia = experiencia;
        return this;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public User getFichaTecnicaUser() {
        return fichaTecnicaUser;
    }

    public FichaTecnica fichaTecnicaUser(User user) {
        this.fichaTecnicaUser = user;
        return this;
    }

    public void setFichaTecnicaUser(User user) {
        this.fichaTecnicaUser = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FichaTecnica)) {
            return false;
        }
        return id != null && id.equals(((FichaTecnica) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FichaTecnica{" +
            "id=" + getId() +
            ", tituloProfesional='" + getTituloProfesional() + "'" +
            ", tituloPostgrado='" + getTituloPostgrado() + "'" +
            ", experiencia='" + getExperiencia() + "'" +
            "}";
    }
}
