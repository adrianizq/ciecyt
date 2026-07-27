package co.edu.itp.ciecyt.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Solicitud} entity.
 */
public class SolicitudDTO implements Serializable {

    private Long id;

    private Boolean estado;

    private String asunto;

    private String textoSolicitud;

    private LocalDate fechaSolicitud;


    private Long solicitudIntegranteProyectoId;

    private String solicitudIntegranteProyectoIntegrante;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTextoSolicitud() {
        return textoSolicitud;
    }

    public void setTextoSolicitud(String textoSolicitud) {
        this.textoSolicitud = textoSolicitud;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Long getSolicitudIntegranteProyectoId() {
        return solicitudIntegranteProyectoId;
    }

    public void setSolicitudIntegranteProyectoId(Long integranteProyectoId) {
        this.solicitudIntegranteProyectoId = integranteProyectoId;
    }

    public String getSolicitudIntegranteProyectoIntegrante() {
        return solicitudIntegranteProyectoIntegrante;
    }

    public void setSolicitudIntegranteProyectoIntegrante(String integranteProyectoIntegrante) {
        this.solicitudIntegranteProyectoIntegrante = integranteProyectoIntegrante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SolicitudDTO solicitudDTO = (SolicitudDTO) o;
        if (solicitudDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), solicitudDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SolicitudDTO{" +
            "id=" + getId() +
            ", estado='" + isEstado() + "'" +
            ", asunto='" + getAsunto() + "'" +
            ", textoSolicitud='" + getTextoSolicitud() + "'" +
            ", fechaSolicitud='" + getFechaSolicitud() + "'" +
            ", solicitudIntegranteProyecto=" + getSolicitudIntegranteProyectoId() +
            ", solicitudIntegranteProyecto='" + getSolicitudIntegranteProyectoIntegrante() + "'" +
            "}";
    }
}
