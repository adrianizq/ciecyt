package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Estado permitido dentro del flujo de una modalidad (Acuerdo 29).
 * Define la secuencia de estados por modalidad y su orden.
 */
@Entity
@Table(name = "estado_modalidad")
public class EstadoModalidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EnumEstadoProyecto estado;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_modalidad_modalidad_id")
    private Modalidad estadoModalidadModalidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstadoProyecto getEstado() {
        return estado;
    }

    public void setEstado(EnumEstadoProyecto estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Modalidad getEstadoModalidadModalidad() {
        return estadoModalidadModalidad;
    }

    public void setEstadoModalidadModalidad(Modalidad modalidad) {
        this.estadoModalidadModalidad = modalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstadoModalidad)) {
            return false;
        }
        return id != null && id.equals(((EstadoModalidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EstadoModalidad{" +
            "id=" + id +
            ", estado=" + estado +
            ", orden=" + orden +
            '}';
    }
}
