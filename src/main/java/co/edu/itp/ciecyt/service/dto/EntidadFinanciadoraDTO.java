package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.EntidadFinanciadora} entity.
 */
public class EntidadFinanciadoraDTO implements Serializable {

    private Long id;

    private Double valor;

    private Boolean aprobada;


    private Long entidadFinanciadoraEntidadId;

    private String entidadFinanciadoraEntidadEntidad;

    private Long entidadFinanciadoraProyectoId;

    private String entidadFinanciadoraProyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public Long getEntidadFinanciadoraEntidadId() {
        return entidadFinanciadoraEntidadId;
    }

    public void setEntidadFinanciadoraEntidadId(Long entidadId) {
        this.entidadFinanciadoraEntidadId = entidadId;
    }

    public String getEntidadFinanciadoraEntidadEntidad() {
        return entidadFinanciadoraEntidadEntidad;
    }

    public void setEntidadFinanciadoraEntidadEntidad(String entidadEntidad) {
        this.entidadFinanciadoraEntidadEntidad = entidadEntidad;
    }

    public Long getEntidadFinanciadoraProyectoId() {
        return entidadFinanciadoraProyectoId;
    }

    public void setEntidadFinanciadoraProyectoId(Long proyectoId) {
        this.entidadFinanciadoraProyectoId = proyectoId;
    }

    public String getEntidadFinanciadoraProyectoTitulo() {
        return entidadFinanciadoraProyectoTitulo;
    }

    public void setEntidadFinanciadoraProyectoTitulo(String proyectoTitulo) {
        this.entidadFinanciadoraProyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EntidadFinanciadoraDTO entidadFinanciadoraDTO = (EntidadFinanciadoraDTO) o;
        if (entidadFinanciadoraDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), entidadFinanciadoraDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EntidadFinanciadoraDTO{" +
            "id=" + getId() +
            ", valor=" + getValor() +
            ", aprobada='" + isAprobada() + "'" +
            ", entidadFinanciadoraEntidad=" + getEntidadFinanciadoraEntidadId() +
            ", entidadFinanciadoraEntidad='" + getEntidadFinanciadoraEntidadEntidad() + "'" +
            ", entidadFinanciadoraProyecto=" + getEntidadFinanciadoraProyectoId() +
            ", entidadFinanciadoraProyecto='" + getEntidadFinanciadoraProyectoTitulo() + "'" +
            "}";
    }
}
