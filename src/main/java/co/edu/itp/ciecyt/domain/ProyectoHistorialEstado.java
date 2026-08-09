package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Historial de cambios de estado de un proyecto.
 */
@Entity
@Table(name = "proyecto_historial_estado")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProyectoHistorialEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior")
    private EnumEstadoProyecto estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false)
    private EnumEstadoProyecto estadoNuevo;

    @Column(name = "observacion", columnDefinition = "text")
    private String observacion;

    @Column(name = "usuario_login")
    private String usuarioLogin;

    @Column(name = "fecha_cambio", nullable = false)
    private Instant fechaCambio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_id", nullable = false)
    private Proyecto proyecto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstadoProyecto getEstadoAnterior() {
        return estadoAnterior;
    }

    public ProyectoHistorialEstado estadoAnterior(EnumEstadoProyecto estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
        return this;
    }

    public void setEstadoAnterior(EnumEstadoProyecto estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public EnumEstadoProyecto getEstadoNuevo() {
        return estadoNuevo;
    }

    public ProyectoHistorialEstado estadoNuevo(EnumEstadoProyecto estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
        return this;
    }

    public void setEstadoNuevo(EnumEstadoProyecto estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public String getObservacion() {
        return observacion;
    }

    public ProyectoHistorialEstado observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    public ProyectoHistorialEstado usuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
        return this;
    }

    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public Instant getFechaCambio() {
        return fechaCambio;
    }

    public ProyectoHistorialEstado fechaCambio(Instant fechaCambio) {
        this.fechaCambio = fechaCambio;
        return this;
    }

    public void setFechaCambio(Instant fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public ProyectoHistorialEstado proyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
        return this;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoHistorialEstado)) {
            return false;
        }
        return id != null && id.equals(((ProyectoHistorialEstado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProyectoHistorialEstado{" +
            "id=" + getId() +
            ", estadoAnterior='" + getEstadoAnterior() + "'" +
            ", estadoNuevo='" + getEstadoNuevo() + "'" +
            ", usuarioLogin='" + getUsuarioLogin() + "'" +
            ", fechaCambio='" + getFechaCambio() + "'" +
            "}";
    }
}
