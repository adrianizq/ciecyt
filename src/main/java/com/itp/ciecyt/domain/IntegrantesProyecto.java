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
 * A IntegrantesProyecto.
 */
@Entity
@Table(name = "integrantes_proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class IntegrantesProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("integrantesProyectos")
    private Proyecto proyecto;

    @OneToMany(mappedBy = "integrantesProyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PreguntaRespuesta> preguntaRespuestas = new HashSet<>();

    @OneToMany(mappedBy = "integrantesProyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Solicitud> solicituds = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("integrantesProyectos")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("integrantesProyectos")
    private RolesUsuarioProyecto rolesUsuarioProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public IntegrantesProyecto proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Set<PreguntaRespuesta> getPreguntaRespuestas() {
        return preguntaRespuestas;
    }

    public IntegrantesProyecto preguntaRespuestas(Set<PreguntaRespuesta> preguntaRespuestas) {
        this.preguntaRespuestas = preguntaRespuestas;
        return this;
    }

    public IntegrantesProyecto addPreguntaRespuesta(PreguntaRespuesta preguntaRespuesta) {
        this.preguntaRespuestas.add(preguntaRespuesta);
        preguntaRespuesta.setIntegrantesProyecto(this);
        return this;
    }

    public IntegrantesProyecto removePreguntaRespuesta(PreguntaRespuesta preguntaRespuesta) {
        this.preguntaRespuestas.remove(preguntaRespuesta);
        preguntaRespuesta.setIntegrantesProyecto(null);
        return this;
    }

    public void setPreguntaRespuestas(Set<PreguntaRespuesta> preguntaRespuestas) {
        this.preguntaRespuestas = preguntaRespuestas;
    }

    public Set<Solicitud> getSolicituds() {
        return solicituds;
    }

    public IntegrantesProyecto solicituds(Set<Solicitud> solicituds) {
        this.solicituds = solicituds;
        return this;
    }

    public IntegrantesProyecto addSolicitud(Solicitud solicitud) {
        this.solicituds.add(solicitud);
        solicitud.setIntegrantesProyecto(this);
        return this;
    }

    public IntegrantesProyecto removeSolicitud(Solicitud solicitud) {
        this.solicituds.remove(solicitud);
        solicitud.setIntegrantesProyecto(null);
        return this;
    }

    public void setSolicituds(Set<Solicitud> solicituds) {
        this.solicituds = solicituds;
    }

    public User getUser() {
        return user;
    }

    public IntegrantesProyecto user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RolesUsuarioProyecto getRolesUsuarioProyecto() {
        return rolesUsuarioProyecto;
    }

    public IntegrantesProyecto rolesUsuarioProyecto(RolesUsuarioProyecto rolesUsuarioProyecto) {
        this.rolesUsuarioProyecto = rolesUsuarioProyecto;
        return this;
    }

    public void setRolesUsuarioProyecto(RolesUsuarioProyecto rolesUsuarioProyecto) {
        this.rolesUsuarioProyecto = rolesUsuarioProyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegrantesProyecto)) {
            return false;
        }
        return id != null && id.equals(((IntegrantesProyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IntegrantesProyecto{" +
            "id=" + getId() +
            "}";
    }
}
