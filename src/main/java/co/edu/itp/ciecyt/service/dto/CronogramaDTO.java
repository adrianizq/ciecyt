package co.edu.itp.ciecyt.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Cronograma} entity.
 */
public class CronogramaDTO implements Serializable {

    private Long id;

    private String actividad;

    private Integer duracion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Integer ordenVista;


    private Long cronogramaProyectoId;

    private String cronogramaProyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Integer getOrdenVista() {
        return ordenVista;
    }

    public void setOrdenVista(Integer ordenVista) {
        this.ordenVista = ordenVista;
    }

    public Long getCronogramaProyectoId() {
        return cronogramaProyectoId;
    }

    public void setCronogramaProyectoId(Long proyectoId) {
        this.cronogramaProyectoId = proyectoId;
    }

    public String getCronogramaProyectoTitulo() {
        return cronogramaProyectoTitulo;
    }

    public void setCronogramaProyectoTitulo(String proyectoTitulo) {
        this.cronogramaProyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CronogramaDTO cronogramaDTO = (CronogramaDTO) o;
        if (cronogramaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cronogramaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CronogramaDTO{" +
            "id=" + getId() +
            ", actividad='" + getActividad() + "'" +
            ", duracion=" + getDuracion() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", ordenVista=" + getOrdenVista() +
            ", cronogramaProyecto=" + getCronogramaProyectoId() +
            ", cronogramaProyecto='" + getCronogramaProyectoTitulo() + "'" +
            "}";
    }
}
