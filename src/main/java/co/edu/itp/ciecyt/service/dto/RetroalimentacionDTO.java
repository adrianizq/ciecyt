package co.edu.itp.ciecyt.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Retroalimentacion} entity.
 */
public class RetroalimentacionDTO implements Serializable {

    private Long id;

    private String titulo;

    private String retroalimentacion;

    private LocalDate fechaRetroalimentacion;

    private LocalDate estadoRetroalimentacion;

    private Integer estadoProyectoFase;


    private Long retroalimentacionProyectoFaseId;

    private String retroalimentacionProyectoFaseTitulo;

    private Long retroalimentacionUserId;

    private String retroalimentacionUserLogin;

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

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public LocalDate getFechaRetroalimentacion() {
        return fechaRetroalimentacion;
    }

    public void setFechaRetroalimentacion(LocalDate fechaRetroalimentacion) {
        this.fechaRetroalimentacion = fechaRetroalimentacion;
    }

    public LocalDate getEstadoRetroalimentacion() {
        return estadoRetroalimentacion;
    }

    public void setEstadoRetroalimentacion(LocalDate estadoRetroalimentacion) {
        this.estadoRetroalimentacion = estadoRetroalimentacion;
    }

    public Integer getEstadoProyectoFase() {
        return estadoProyectoFase;
    }

    public void setEstadoProyectoFase(Integer estadoProyectoFase) {
        this.estadoProyectoFase = estadoProyectoFase;
    }

    public Long getRetroalimentacionProyectoFaseId() {
        return retroalimentacionProyectoFaseId;
    }

    public void setRetroalimentacionProyectoFaseId(Long proyectoFaseId) {
        this.retroalimentacionProyectoFaseId = proyectoFaseId;
    }

    public String getRetroalimentacionProyectoFaseTitulo() {
        return retroalimentacionProyectoFaseTitulo;
    }

    public void setRetroalimentacionProyectoFaseTitulo(String proyectoFaseTitulo) {
        this.retroalimentacionProyectoFaseTitulo = proyectoFaseTitulo;
    }

    public Long getRetroalimentacionUserId() {
        return retroalimentacionUserId;
    }

    public void setRetroalimentacionUserId(Long userId) {
        this.retroalimentacionUserId = userId;
    }

    public String getRetroalimentacionUserLogin() {
        return retroalimentacionUserLogin;
    }

    public void setRetroalimentacionUserLogin(String userLogin) {
        this.retroalimentacionUserLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RetroalimentacionDTO retroalimentacionDTO = (RetroalimentacionDTO) o;
        if (retroalimentacionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), retroalimentacionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RetroalimentacionDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", retroalimentacion='" + getRetroalimentacion() + "'" +
            ", fechaRetroalimentacion='" + getFechaRetroalimentacion() + "'" +
            ", estadoRetroalimentacion='" + getEstadoRetroalimentacion() + "'" +
            ", estadoProyectoFase=" + getEstadoProyectoFase() +
            ", retroalimentacionProyectoFase=" + getRetroalimentacionProyectoFaseId() +
            ", retroalimentacionProyectoFase='" + getRetroalimentacionProyectoFaseTitulo() + "'" +
            ", retroalimentacionUser=" + getRetroalimentacionUserId() +
            ", retroalimentacionUser='" + getRetroalimentacionUserLogin() + "'" +
            "}";
    }
}
