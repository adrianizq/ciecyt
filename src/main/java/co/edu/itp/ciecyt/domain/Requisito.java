package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import co.edu.itp.ciecyt.domain.enumeration.TipoRequisito;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Requisito habilitante exigido por el flujo de trabajo de grado (Acuerdo 29).
 */
@Entity
@Table(name = "requisito")
public class Requisito implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion", length = 2000)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoRequisito tipo;

    @Column(name = "obligatorio")
    private Boolean obligatorio;

    @Column(name = "activo")
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requisito_modalidad_id")
    private Modalidad requisitoModalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "requisito_estado")
    private EnumEstadoProyecto requisitoEstado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoRequisito getTipo() {
        return tipo;
    }

    public void setTipo(TipoRequisito tipo) {
        this.tipo = tipo;
    }

    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Modalidad getRequisitoModalidad() {
        return requisitoModalidad;
    }

    public void setRequisitoModalidad(Modalidad modalidad) {
        this.requisitoModalidad = modalidad;
    }

    public EnumEstadoProyecto getRequisitoEstado() {
        return requisitoEstado;
    }

    public void setRequisitoEstado(EnumEstadoProyecto requisitoEstado) {
        this.requisitoEstado = requisitoEstado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Requisito)) {
            return false;
        }
        return id != null && id.equals(((Requisito) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Requisito{" +
            "id=" + id +
            ", codigo='" + codigo + '\'' +
            ", nombre='" + nombre + '\'' +
            ", tipo=" + tipo +
            '}';
    }
}
