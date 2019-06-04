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
 * A RolesUsuarioProyecto.
 */
@Entity
@Table(name = "roles_usuario_proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RolesUsuarioProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rol")
    private Integer rol;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "rolesUsuarioProyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IntegrantesProyecto> integrantesProyectos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRol() {
        return rol;
    }

    public RolesUsuarioProyecto rol(Integer rol) {
        this.rol = rol;
        return this;
    }

    public void setRol(Integer rol) {
        this.rol = rol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public RolesUsuarioProyecto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<IntegrantesProyecto> getIntegrantesProyectos() {
        return integrantesProyectos;
    }

    public RolesUsuarioProyecto integrantesProyectos(Set<IntegrantesProyecto> integrantesProyectos) {
        this.integrantesProyectos = integrantesProyectos;
        return this;
    }

    public RolesUsuarioProyecto addIntegrantesProyecto(IntegrantesProyecto integrantesProyecto) {
        this.integrantesProyectos.add(integrantesProyecto);
        integrantesProyecto.setRolesUsuarioProyecto(this);
        return this;
    }

    public RolesUsuarioProyecto removeIntegrantesProyecto(IntegrantesProyecto integrantesProyecto) {
        this.integrantesProyectos.remove(integrantesProyecto);
        integrantesProyecto.setRolesUsuarioProyecto(null);
        return this;
    }

    public void setIntegrantesProyectos(Set<IntegrantesProyecto> integrantesProyectos) {
        this.integrantesProyectos = integrantesProyectos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RolesUsuarioProyecto)) {
            return false;
        }
        return id != null && id.equals(((RolesUsuarioProyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RolesUsuarioProyecto{" +
            "id=" + getId() +
            ", rol=" + getRol() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
