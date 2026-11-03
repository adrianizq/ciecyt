package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoPostulacion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Postulacion de un estudiante a una convocatoria de pasantia.
 */
@Entity
@Table(
    name = "postulacion_pasantia",
    uniqueConstraints = @UniqueConstraint(name = "ux_postulacion_convocatoria_proyecto", columnNames = { "convocatoria_id", "proyecto_id" })
)
public class PostulacionPasantia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "convocatoria_id", nullable = false)
    @JsonIgnoreProperties("postulaciones")
    private ConvocatoriaPasantia convocatoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proyecto_id", nullable = false)
    @JsonIgnoreProperties("postulaciones")
    private Proyecto proyecto;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EnumEstadoPostulacion estado;

    @Column(name = "fecha_postulacion", nullable = false)
    private LocalDate fechaPostulacion;

    @Column(name = "observacion", columnDefinition = "text")
    private String observacion;

    @Column(name = "fecha_decision")
    private LocalDate fechaDecision;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConvocatoriaPasantia getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(ConvocatoriaPasantia convocatoria) {
        this.convocatoria = convocatoria;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public EnumEstadoPostulacion getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoPostulacion estado) {
        this.estado = estado;
    }

    public LocalDate getFechaPostulacion() {
        return fechaPostulacion;
    }

    public void setFechaPostulacion(LocalDate fechaPostulacion) {
        this.fechaPostulacion = fechaPostulacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDate getFechaDecision() {
        return fechaDecision;
    }

    public void setFechaDecision(LocalDate fechaDecision) {
        this.fechaDecision = fechaDecision;
    }
}
