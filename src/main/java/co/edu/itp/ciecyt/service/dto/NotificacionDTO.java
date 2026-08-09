package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Notificacion} entity.
 */
public class NotificacionDTO implements Serializable {

    private Long id;

    private String titulo;

    private String mensaje;

    private String tipo;

    private Boolean leido;

    private Instant fechaCreacion;

    private Instant fechaLectura;

    private Long userId;

    private String userLogin;

    private Long proyectoId;

    private String proyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaLectura() {
        return fechaLectura;
    }

    public void setFechaLectura(Instant fechaLectura) {
        this.fechaLectura = fechaLectura;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Long getProyectoId() {
        return proyectoId;
    }

    public void setProyectoId(Long proyectoId) {
        this.proyectoId = proyectoId;
    }

    public String getProyectoTitulo() {
        return proyectoTitulo;
    }

    public void setProyectoTitulo(String proyectoTitulo) {
        this.proyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificacionDTO)) {
            return false;
        }

        return id != null && id.equals(((NotificacionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NotificacionDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", leido='" + getLeido() + "'" +
            "}";
    }
}
