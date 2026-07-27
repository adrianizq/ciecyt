package co.edu.itp.ciecyt.service.dto;
import co.edu.itp.ciecyt.domain.Menu;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Menu} entity.
 */
public class MenuDTO implements Serializable {

    private Long id;

    private String nombre;

    private String url;

    private String icono;

    private Boolean activo;

    private Boolean esPublico;

    private Integer orden;

    private String rol;

    private Long menuPadreId;

    private String menuPadreNombre;

    private List<MenuDTO> childMenus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean isEsPublico() {
        return esPublico;
    }

    public void setEsPublico(Boolean esPublico) {
        this.esPublico = esPublico;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Long getMenuPadreId() {
        return menuPadreId;
    }

    public void setMenuPadreId(Long menuId) {
        this.menuPadreId = menuId;
    }

    public String getMenuPadreNombre() {
        return menuPadreNombre;
    }

    public void setMenuPadreNombre(String menuNombre) {
        this.menuPadreNombre = menuNombre;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuDTO menuDTO = (MenuDTO) o;
        if (menuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), menuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", url='" + getUrl() + "'" +
            ", icono='" + getIcono() + "'" +
            ", activo='" + isActivo() + "'" +
            ", esPublico='" + isEsPublico() + "'" +
            ", orden=" + getOrden() +
            ", menuPadre=" + getMenuPadreId() +
            ", menuPadre='" + getMenuPadreNombre() + "'" +
            "}";
    }

	public List<MenuDTO> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<MenuDTO> childMenus) {
		this.childMenus = childMenus;
	}
}
