package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumGenero;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import co.edu.itp.ciecyt.domain.enumeration.EnumGenero;

/**
 * A UserInfo.
 */
@Entity
@Table(name = "user_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "nuip")
    private String nuip;

    @Column(name = "codigo_itp")
    private String codigoItp;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private EnumGenero genero;

    @Column(name = "celular")
    private String celular;

    @Column(name = "telefono")
    private String telefono;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "foto")
    private String foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @OneToOne(optional = false)
    @NotNull
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNuip() {
        return nuip;
    }

    public UserInfo nuip(String nuip) {
        this.nuip = nuip;
        return this;
    }

    public void setNuip(String nuip) {
        this.nuip = nuip;
    }

    public String getCodigoItp() {
        return codigoItp;
    }

    public UserInfo codigoItp(String codigoItp) {
        this.codigoItp = codigoItp;
        return this;
    }

    public void setCodigoItp(String codigoItp) {
        this.codigoItp = codigoItp;
    }

    public EnumGenero getGenero() {
        return this.genero;
    }

    public UserInfo genero(EnumGenero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(EnumGenero genero) {
        this.genero = genero;
    }

    public String getCelular() {
        return celular;
    }

    public UserInfo celular(String celular) {
        this.celular = celular;
        return this;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public UserInfo telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public UserInfo foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public UserInfo fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public User getUser() {
        return user;
    }

    public UserInfo user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserInfo)) {
            return false;
        }
        return id != null && id.equals(((UserInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInfo{" +
            "id=" + getId() +
            ", nuip='" + getNuip() + "'" +
            ", codigoItp='" + getCodigoItp() + "'" +
            ", genero='" + getGenero() + "'" +
            ", celular='" + getCelular() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            "}";
    }
}
