package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RolesModalidad.
 */
@Entity
@Table(name = "roles_modalidad")
public class RolesModalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rol")
    private String rol;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "calificador")
    private Boolean calificador;

    @Column(name = "roles_modalidad_authority_name")
    private String rolesModalidadAuthorityName;

    @ManyToOne
    @JsonIgnoreProperties("rolesModalidads")
    private Modalidad rolesModalidadModalidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public RolesModalidad rol(String rol) {
        this.rol = rol;
        return this;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
    public String getRolesModalidadAuthorityName() {
        return rolesModalidadAuthorityName;
    }

    public RolesModalidad rolesModalidadAuthorityName(String rolesModalidadAuthorityName) {
        this.rolesModalidadAuthorityName = rolesModalidadAuthorityName;
        return this;
    }

    public void setRolesModalidadAuthorityName(String rolesModalidadAuthorityName) {
        this.rolesModalidadAuthorityName = rolesModalidadAuthorityName;
    }
    

    public Integer getCantidad() {
        return cantidad;
    }

    public RolesModalidad cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean isCalificador() {
        return calificador;
    }

    public RolesModalidad calificador(Boolean calificador) {
        this.calificador = calificador;
        return this;
    }

    public void setCalificador(Boolean calificador) {
        this.calificador = calificador;
    }

    public Modalidad getRolesModalidadModalidad() {
        return rolesModalidadModalidad;
    }

    public RolesModalidad rolesModalidadModalidad(Modalidad modalidad) {
        this.rolesModalidadModalidad = modalidad;
        return this;
    }

    public void setRolesModalidadModalidad(Modalidad modalidad) {
        this.rolesModalidadModalidad = modalidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RolesModalidad)) {
            return false;
        }
        return id != null && id.equals(((RolesModalidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RolesModalidad{" +
            "id=" + getId() +
            ", rol='" + getRol() + "'" +
            ", rolesModalidadAuthorityName='" + getRolesModalidadAuthorityName() + "'" +
            ", cantidad=" + getCantidad() +
            ", calificador='" + isCalificador() + "'" +
            "}";
    }
}
