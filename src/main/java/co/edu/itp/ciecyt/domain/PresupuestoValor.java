package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A PresupuestoValor.
 */
@Entity
@Table(name = "presupuesto_valor")
public class PresupuestoValor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "justificacion")
    private String justificacion;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "valor_unitario")
    private Double valorUnitario;

    @Column(name = "especie")
    private Double especie;

    @Column(name = "dinero")
    private Double dinero;

    @Column(name = "orden_vista")
    private Integer ordenVista;

    @ManyToOne
    @JsonIgnoreProperties("presupuestoValors")
    private Rubro presupuestoValorRubro;

    @ManyToOne
    @JsonIgnoreProperties("presupuestoValors")
    private Proyecto presupuestoValorProyecto;

    @ManyToOne
    @JsonIgnoreProperties("presupuestoValors")
    private Entidad presupuestoValorEntidad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public PresupuestoValor descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public PresupuestoValor justificacion(String justificacion) {
        this.justificacion = justificacion;
        return this;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public PresupuestoValor cantidad(Integer cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public PresupuestoValor valorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
        return this;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getEspecie() {
        return especie;
    }

    public PresupuestoValor especie(Double especie) {
        this.especie = especie;
        return this;
    }

    public void setEspecie(Double especie) {
        this.especie = especie;
    }

    public Double getDinero() {
        return dinero;
    }

    public PresupuestoValor dinero(Double dinero) {
        this.dinero = dinero;
        return this;
    }

    public void setDinero(Double dinero) {
        this.dinero = dinero;
    }

    public Integer getOrdenVista() {
        return ordenVista;
    }

    public PresupuestoValor ordenVista(Integer ordenVista) {
        this.ordenVista = ordenVista;
        return this;
    }

    public void setOrdenVista(Integer ordenVista) {
        this.ordenVista = ordenVista;
    }

    public Rubro getPresupuestoValorRubro() {
        return presupuestoValorRubro;
    }

    public PresupuestoValor presupuestoValorRubro(Rubro rubro) {
        this.presupuestoValorRubro = rubro;
        return this;
    }

    public void setPresupuestoValorRubro(Rubro rubro) {
        this.presupuestoValorRubro = rubro;
    }

    public Proyecto getPresupuestoValorProyecto() {
        return presupuestoValorProyecto;
    }

    public PresupuestoValor presupuestoValorProyecto(Proyecto proyecto) {
        this.presupuestoValorProyecto = proyecto;
        return this;
    }

    public void setPresupuestoValorProyecto(Proyecto proyecto) {
        this.presupuestoValorProyecto = proyecto;
    }

    public Entidad getPresupuestoValorEntidad() {
        return presupuestoValorEntidad;
    }

    public PresupuestoValor presupuestoValorEntidad(Entidad entidad) {
        this.presupuestoValorEntidad = entidad;
        return this;
    }

    public void setPresupuestoValorEntidad(Entidad entidad) {
        this.presupuestoValorEntidad = entidad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PresupuestoValor)) {
            return false;
        }
        return id != null && id.equals(((PresupuestoValor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PresupuestoValor{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", justificacion='" + getJustificacion() + "'" +
            ", cantidad=" + getCantidad() +
            ", valorUnitario=" + getValorUnitario() +
            ", especie=" + getEspecie() +
            ", dinero=" + getDinero() +
            ", ordenVista=" + getOrdenVista() +
            "}";
    }
}
