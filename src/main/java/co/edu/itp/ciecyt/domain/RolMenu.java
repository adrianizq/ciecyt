package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A RolMenu.
 */
@Entity
@Table(name = "rol_menu")
public class RolMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "permitir_acceso")
    private Boolean permitirAcceso;

    @Column(name = "permitir_crear")
    private Boolean permitirCrear;

    @Column(name = "permitir_editar")
    private Boolean permitirEditar;

    @Column(name = "permitir_eliminar")
    private Boolean permitirEliminar;

    @Column(name = "auth_name")
    private String authName;

    @ManyToOne
    @JsonIgnoreProperties("rolMenus")
    private Menu rolMenuMenu;
/*
    @ManyToOne
    @JsonIgnoreProperties("rolAuthority")
    private Authority rolAuthority;
*/    
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPermitirAcceso() {
        return permitirAcceso;
    }

    public RolMenu permitirAcceso(Boolean permitirAcceso) {
        this.permitirAcceso = permitirAcceso;
        return this;
    }

    public void setPermitirAcceso(Boolean permitirAcceso) {
        this.permitirAcceso = permitirAcceso;
    }

    public Boolean isPermitirCrear() {
        return permitirCrear;
    }

    public RolMenu permitirCrear(Boolean permitirCrear) {
        this.permitirCrear = permitirCrear;
        return this;
    }

    public void setPermitirCrear(Boolean permitirCrear) {
        this.permitirCrear = permitirCrear;
    }

    public Boolean isPermitirEditar() {
        return permitirEditar;
    }

    public RolMenu permitirEditar(Boolean permitirEditar) {
        this.permitirEditar = permitirEditar;
        return this;
    }

    public void setPermitirEditar(Boolean permitirEditar) {
        this.permitirEditar = permitirEditar;
    }

    public Boolean isPermitirEliminar() {
        return permitirEliminar;
    }

    public RolMenu permitirEliminar(Boolean permitirEliminar) {
        this.permitirEliminar = permitirEliminar;
        return this;
    }

    public void setPermitirEliminar(Boolean permitirEliminar) {
        this.permitirEliminar = permitirEliminar;
    }

    public String getAuthName() {
        return authName;
    }

    public RolMenu authName(String authName) {
        this.authName = authName;
        return this;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Menu getRolMenuMenu() {
        return rolMenuMenu;
    }

    public RolMenu rolMenuMenu(Menu menu) {
        this.rolMenuMenu = menu;
        return this;
    }

    public void setRolMenuMenu(Menu menu) {
        this.rolMenuMenu = menu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RolMenu)) {
            return false;
        }
        return id != null && id.equals(((RolMenu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RolMenu{" +
            "id=" + getId() +
            ", permitirAcceso='" + isPermitirAcceso() + "'" +
            ", permitirCrear='" + isPermitirCrear() + "'" +
            ", permitirEditar='" + isPermitirEditar() + "'" +
            ", permitirEliminar='" + isPermitirEliminar() + "'" +
            ", authName='" + getAuthName() + "'" +
            "}";
    }
}
