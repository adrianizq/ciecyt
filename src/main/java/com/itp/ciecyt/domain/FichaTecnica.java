package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FichaTecnica.
 */
@Entity
@Table(name = "ficha_tecnica")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FichaTecnica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo_profecional")
    private String tituloProfecional;

    @Column(name = "titulo_posgrado")
    private String tituloPosgrado;

    @Column(name = "experencia")
    private String experencia;

    @ManyToOne
    @JsonIgnoreProperties("fichaTecnicas")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloProfecional() {
        return tituloProfecional;
    }

    public FichaTecnica tituloProfecional(String tituloProfecional) {
        this.tituloProfecional = tituloProfecional;
        return this;
    }

    public void setTituloProfecional(String tituloProfecional) {
        this.tituloProfecional = tituloProfecional;
    }

    public String getTituloPosgrado() {
        return tituloPosgrado;
    }

    public FichaTecnica tituloPosgrado(String tituloPosgrado) {
        this.tituloPosgrado = tituloPosgrado;
        return this;
    }

    public void setTituloPosgrado(String tituloPosgrado) {
        this.tituloPosgrado = tituloPosgrado;
    }

    public String getExperencia() {
        return experencia;
    }

    public FichaTecnica experencia(String experencia) {
        this.experencia = experencia;
        return this;
    }

    public void setExperencia(String experencia) {
        this.experencia = experencia;
    }

    public User getUser() {
        return user;
    }

    public FichaTecnica user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
            ", tituloProfecional='" + getTituloProfecional() + "'" +
            ", tituloPosgrado='" + getTituloPosgrado() + "'" +
            ", experencia='" + getExperencia() + "'" +
            "}";
    }
}
