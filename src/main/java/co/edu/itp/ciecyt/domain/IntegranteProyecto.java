package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A IntegranteProyecto.
 */
@Entity
@Table(name = "integrante_proyecto")
public class IntegranteProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "integrante")
    private String integrante;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToOne
    @JsonIgnoreProperties("integranteProyectos")
    private User integranteProyectoUser;

    @ManyToOne
    @JsonIgnoreProperties("integranteProyectos")
    private Proyecto integranteProyectoProyecto;

    @ManyToOne
    @JsonIgnoreProperties("integranteProyectos")
    private RolesModalidad integranteProyectoRolesModalidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntegrante() {
        return integrante;
    }

    public IntegranteProyecto integrante(String integrante) {
        this.integrante = integrante;
        return this;
    }

    public void setIntegrante(String integrante) {
        this.integrante = integrante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public IntegranteProyecto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public User getIntegranteProyectoUser() {
        return integranteProyectoUser;
    }

    public IntegranteProyecto integranteProyectoUser(User user) {
        this.integranteProyectoUser = user;
        return this;
    }

    public void setIntegranteProyectoUser(User user) {
        this.integranteProyectoUser = user;
    }

    public Proyecto getIntegranteProyectoProyecto() {
        return integranteProyectoProyecto;
    }

    public IntegranteProyecto integranteProyectoProyecto(Proyecto proyecto) {
        this.integranteProyectoProyecto = proyecto;
        return this;
    }

    public void setIntegranteProyectoProyecto(Proyecto proyecto) {
        this.integranteProyectoProyecto = proyecto;
    }

    public RolesModalidad getIntegranteProyectoRolesModalidad() {
        return integranteProyectoRolesModalidad;
    }

    public IntegranteProyecto integranteProyectoRolesModalidad(RolesModalidad rolesModalidad) {
        this.integranteProyectoRolesModalidad = rolesModalidad;
        return this;
    }

    public void setIntegranteProyectoRolesModalidad(RolesModalidad rolesModalidad) {
        this.integranteProyectoRolesModalidad = rolesModalidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntegranteProyecto)) {
            return false;
        }
        return id != null && id.equals(((IntegranteProyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "IntegranteProyecto{" +
            "id=" + getId() +
            ", integrante='" + getIntegrante() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
