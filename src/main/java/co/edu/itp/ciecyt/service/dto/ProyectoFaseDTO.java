package co.edu.itp.ciecyt.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.ProyectoFase} entity.
 */
public class ProyectoFaseDTO implements Serializable {

    private Long id;

    private String titulo;

    private Boolean cumplida;

    private LocalDate fechaCumplimiento;

    private String observaciones;


    private Long proyectoFaseFasesId;

    private String proyectoFaseFasesFase;

    private Long proyectoFaseProyectoId;

    private String proyectoFaseProyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Boolean isCumplida() {
        return cumplida;
    }

    public void setCumplida(Boolean cumplida) {
        this.cumplida = cumplida;
    }

    public LocalDate getFechaCumplimiento() {
        return fechaCumplimiento;
    }

    public void setFechaCumplimiento(LocalDate fechaCumplimiento) {
        this.fechaCumplimiento = fechaCumplimiento;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getProyectoFaseFasesId() {
        return proyectoFaseFasesId;
    }

    public void setProyectoFaseFasesId(Long fasesId) {
        this.proyectoFaseFasesId = fasesId;
    }

    public String getProyectoFaseFasesFase() {
        return proyectoFaseFasesFase;
    }

    public void setProyectoFaseFasesFase(String fasesFase) {
        this.proyectoFaseFasesFase = fasesFase;
    }

    public Long getProyectoFaseProyectoId() {
        return proyectoFaseProyectoId;
    }

    public void setProyectoFaseProyectoId(Long proyectoId) {
        this.proyectoFaseProyectoId = proyectoId;
    }

    public String getProyectoFaseProyectoTitulo() {
        return proyectoFaseProyectoTitulo;
    }

    public void setProyectoFaseProyectoTitulo(String proyectoTitulo) {
        this.proyectoFaseProyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProyectoFaseDTO proyectoFaseDTO = (ProyectoFaseDTO) o;
        if (proyectoFaseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proyectoFaseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProyectoFaseDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", cumplida='" + isCumplida() + "'" +
            ", fechaCumplimiento='" + getFechaCumplimiento() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", proyectoFaseFases=" + getProyectoFaseFasesId() +
            ", proyectoFaseFases='" + getProyectoFaseFasesFase() + "'" +
            ", proyectoFaseProyecto=" + getProyectoFaseProyectoId() +
            ", proyectoFaseProyecto='" + getProyectoFaseProyectoTitulo() + "'" +
            "}";
    }
}
