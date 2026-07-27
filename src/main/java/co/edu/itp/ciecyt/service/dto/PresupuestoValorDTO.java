package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.PresupuestoValor} entity.
 */
public class PresupuestoValorDTO implements Serializable {

    private Long id;

    private String descripcion;

    private String justificacion;

    private Integer cantidad;

    private Double valorUnitario;

    private Double especie;

    private Double dinero;

    private Integer ordenVista;


    private Long presupuestoValorRubroId;

    private String presupuestoValorRubroRubro;

    private Long presupuestoValorProyectoId;

    private String presupuestoValorProyectoTitulo;

    private Long presupuestoValorEntidadId;

    private String presupuestoValorEntidadEntidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getEspecie() {
        return especie;
    }

    public void setEspecie(Double especie) {
        this.especie = especie;
    }

    public Double getDinero() {
        return dinero;
    }

    public void setDinero(Double dinero) {
        this.dinero = dinero;
    }

    public Integer getOrdenVista() {
        return ordenVista;
    }

    public void setOrdenVista(Integer ordenVista) {
        this.ordenVista = ordenVista;
    }

    public Long getPresupuestoValorRubroId() {
        return presupuestoValorRubroId;
    }

    public void setPresupuestoValorRubroId(Long rubroId) {
        this.presupuestoValorRubroId = rubroId;
    }

    public String getPresupuestoValorRubroRubro() {
        return presupuestoValorRubroRubro;
    }

    public void setPresupuestoValorRubroRubro(String rubroRubro) {
        this.presupuestoValorRubroRubro = rubroRubro;
    }

    public Long getPresupuestoValorProyectoId() {
        return presupuestoValorProyectoId;
    }

    public void setPresupuestoValorProyectoId(Long proyectoId) {
        this.presupuestoValorProyectoId = proyectoId;
    }

    public String getPresupuestoValorProyectoTitulo() {
        return presupuestoValorProyectoTitulo;
    }

    public void setPresupuestoValorProyectoTitulo(String proyectoTitulo) {
        this.presupuestoValorProyectoTitulo = proyectoTitulo;
    }

    public Long getPresupuestoValorEntidadId() {
        return presupuestoValorEntidadId;
    }

    public void setPresupuestoValorEntidadId(Long entidadId) {
        this.presupuestoValorEntidadId = entidadId;
    }

    public String getPresupuestoValorEntidadEntidad() {
        return presupuestoValorEntidadEntidad;
    }

    public void setPresupuestoValorEntidadEntidad(String entidadEntidad) {
        this.presupuestoValorEntidadEntidad = entidadEntidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PresupuestoValorDTO presupuestoValorDTO = (PresupuestoValorDTO) o;
        if (presupuestoValorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), presupuestoValorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PresupuestoValorDTO{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", justificacion='" + getJustificacion() + "'" +
            ", cantidad=" + getCantidad() +
            ", valorUnitario=" + getValorUnitario() +
            ", especie=" + getEspecie() +
            ", dinero=" + getDinero() +
            ", ordenVista=" + getOrdenVista() +
            ", presupuestoValorRubro=" + getPresupuestoValorRubroId() +
            ", presupuestoValorRubro='" + getPresupuestoValorRubroRubro() + "'" +
            ", presupuestoValorProyecto=" + getPresupuestoValorProyectoId() +
            ", presupuestoValorProyecto='" + getPresupuestoValorProyectoTitulo() + "'" +
            ", presupuestoValorEntidad=" + getPresupuestoValorEntidadId() +
            ", presupuestoValorEntidad='" + getPresupuestoValorEntidadEntidad() + "'" +
            "}";
    }
}
