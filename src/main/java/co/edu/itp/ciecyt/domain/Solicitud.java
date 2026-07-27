package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Solicitud.
 */
@Entity
@Table(name = "solicitud")
public class Solicitud implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "asunto")
    private String asunto;

    @Column(name = "texto_solicitud")
    private String textoSolicitud;

    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @ManyToOne
    @JsonIgnoreProperties("solicituds")
    private IntegranteProyecto solicitudIntegranteProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEstado() {
        return estado;
    }

    public Solicitud estado(Boolean estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getAsunto() {
        return asunto;
    }

    public Solicitud asunto(String asunto) {
        this.asunto = asunto;
        return this;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getTextoSolicitud() {
        return textoSolicitud;
    }

    public Solicitud textoSolicitud(String textoSolicitud) {
        this.textoSolicitud = textoSolicitud;
        return this;
    }

    public void setTextoSolicitud(String textoSolicitud) {
        this.textoSolicitud = textoSolicitud;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public Solicitud fechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
        return this;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public IntegranteProyecto getSolicitudIntegranteProyecto() {
        return solicitudIntegranteProyecto;
    }

    public Solicitud solicitudIntegranteProyecto(IntegranteProyecto integranteProyecto) {
        this.solicitudIntegranteProyecto = integranteProyecto;
        return this;
    }

    public void setSolicitudIntegranteProyecto(IntegranteProyecto integranteProyecto) {
        this.solicitudIntegranteProyecto = integranteProyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Solicitud)) {
            return false;
        }
        return id != null && id.equals(((Solicitud) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
            "id=" + getId() +
            ", estado='" + isEstado() + "'" +
            ", asunto='" + getAsunto() + "'" +
            ", textoSolicitud='" + getTextoSolicitud() + "'" +
            ", fechaSolicitud='" + getFechaSolicitud() + "'" +
            "}";
    }
}
