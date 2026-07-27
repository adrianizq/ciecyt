package co.edu.itp.ciecyt.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.CronogramaCiecyt} entity.
 */
public class CronogramaCiecytDTO implements Serializable {

    private Long id;

    private String tituloCronograma;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String observaciones;


    private Long cronogramaCiecytModalidadId;

    private String cronogramaCiecytModalidadModalidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTituloCronograma() {
        return tituloCronograma;
    }

    public void setTituloCronograma(String tituloCronograma) {
        this.tituloCronograma = tituloCronograma;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getCronogramaCiecytModalidadId() {
        return cronogramaCiecytModalidadId;
    }

    public void setCronogramaCiecytModalidadId(Long modalidadId) {
        this.cronogramaCiecytModalidadId = modalidadId;
    }

    public String getCronogramaCiecytModalidadModalidad() {
        return cronogramaCiecytModalidadModalidad;
    }

    public void setCronogramaCiecytModalidadModalidad(String modalidadModalidad) {
        this.cronogramaCiecytModalidadModalidad = modalidadModalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CronogramaCiecytDTO cronogramaCiecytDTO = (CronogramaCiecytDTO) o;
        if (cronogramaCiecytDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cronogramaCiecytDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CronogramaCiecytDTO{" +
            "id=" + getId() +
            ", tituloCronograma='" + getTituloCronograma() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", cronogramaCiecytModalidad=" + getCronogramaCiecytModalidadId() +
            ", cronogramaCiecytModalidad='" + getCronogramaCiecytModalidadModalidad() + "'" +
            "}";
    }
}
