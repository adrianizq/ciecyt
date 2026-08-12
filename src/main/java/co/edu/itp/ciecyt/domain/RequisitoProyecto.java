package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoRequisito;
import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * Cumplimiento de un requisito habilitante por parte de un proyecto.
 */
@Entity
@Table(name = "requisito_proyecto")
public class RequisitoProyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EnumEstadoRequisito estado;

    @Column(name = "fecha_entrega")
    private Instant fechaEntrega;

    @Column(name = "fecha_validacion")
    private Instant fechaValidacion;

    @Column(name = "observacion", length = 2000)
    private String observacion;

    @Column(name = "archivo")
    private String archivo;

    @Column(name = "validado_por")
    private String validadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisito_proyecto_requisito_id")
    private Requisito requisitoProyectoRequisito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisito_proyecto_proyecto_id")
    private Proyecto requisitoProyectoProyecto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstadoRequisito getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoRequisito estado) {
        this.estado = estado;
    }

    public Instant getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Instant fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Instant getFechaValidacion() {
        return fechaValidacion;
    }

    public void setFechaValidacion(Instant fechaValidacion) {
        this.fechaValidacion = fechaValidacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getValidadoPor() {
        return validadoPor;
    }

    public void setValidadoPor(String validadoPor) {
        this.validadoPor = validadoPor;
    }

    public Requisito getRequisitoProyectoRequisito() {
        return requisitoProyectoRequisito;
    }

    public void setRequisitoProyectoRequisito(Requisito requisito) {
        this.requisitoProyectoRequisito = requisito;
    }

    public Proyecto getRequisitoProyectoProyecto() {
        return requisitoProyectoProyecto;
    }

    public void setRequisitoProyectoProyecto(Proyecto proyecto) {
        this.requisitoProyectoProyecto = proyecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequisitoProyecto)) {
            return false;
        }
        return id != null && id.equals(((RequisitoProyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RequisitoProyecto{" +
            "id=" + id +
            ", estado=" + estado +
            ", requisito=" + (requisitoProyectoRequisito != null ? requisitoProyectoRequisito.getId() : null) +
            ", proyecto=" + (requisitoProyectoProyecto != null ? requisitoProyectoProyecto.getId() : null) +
            '}';
    }
}
