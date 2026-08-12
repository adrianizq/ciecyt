package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Transición válida entre estados del flujo de trabajo de grado (Acuerdo 29).
 * Determina qué transición puede ejecutarse desde un estado origen hacia un estado
 * destino y qué rol tiene permitido ejecutarla. Si {@code rolRequerido} es nulo o vacío
 * o contiene el comodín {@code *}, cualquier usuario autenticado puede ejecutarla.
 */
@Entity
@Table(name = "transicion_estado")
public class TransicionEstado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_origen")
    private EnumEstadoProyecto estadoOrigen;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_destino")
    private EnumEstadoProyecto estadoDestino;

    @Column(name = "rol_requerido")
    private String rolRequerido;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transicion_estado_modalidad_id")
    private Modalidad transicionEstadoModalidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumEstadoProyecto getEstadoOrigen() {
        return estadoOrigen;
    }

    public void setEstadoOrigen(EnumEstadoProyecto estadoOrigen) {
        this.estadoOrigen = estadoOrigen;
    }

    public EnumEstadoProyecto getEstadoDestino() {
        return estadoDestino;
    }

    public void setEstadoDestino(EnumEstadoProyecto estadoDestino) {
        this.estadoDestino = estadoDestino;
    }

    public String getRolRequerido() {
        return rolRequerido;
    }

    public void setRolRequerido(String rolRequerido) {
        this.rolRequerido = rolRequerido;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Modalidad getTransicionEstadoModalidad() {
        return transicionEstadoModalidad;
    }

    public void setTransicionEstadoModalidad(Modalidad modalidad) {
        this.transicionEstadoModalidad = modalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransicionEstado)) {
            return false;
        }
        return id != null && id.equals(((TransicionEstado) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransicionEstado{" +
            "id=" + id +
            ", estadoOrigen=" + estadoOrigen +
            ", estadoDestino=" + estadoDestino +
            ", rolRequerido='" + rolRequerido + '\'' +
            '}';
    }
}
