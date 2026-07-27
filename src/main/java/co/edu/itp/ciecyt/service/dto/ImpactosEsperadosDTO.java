package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.ImpactosEsperados} entity.
 */
public class ImpactosEsperadosDTO implements Serializable {

    private Long id;

    private String impacto;

    private Integer plazo;

    private String indicador;

    private String supuestos;

    private Integer ordenVista;


    private Long impactosEsperadoProyectoId;

    private String impactosEsperadoProyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }

    public Integer getPlazo() {
        return plazo;
    }

    public void setPlazo(Integer plazo) {
        this.plazo = plazo;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getSupuestos() {
        return supuestos;
    }

    public void setSupuestos(String supuestos) {
        this.supuestos = supuestos;
    }

    public Integer getOrdenVista() {
        return ordenVista;
    }

    public void setOrdenVista(Integer ordenVista) {
        this.ordenVista = ordenVista;
    }

    public Long getImpactosEsperadoProyectoId() {
        return impactosEsperadoProyectoId;
    }

    public void setImpactosEsperadoProyectoId(Long proyectoId) {
        this.impactosEsperadoProyectoId = proyectoId;
    }

    public String getImpactosEsperadoProyectoTitulo() {
        return impactosEsperadoProyectoTitulo;
    }

    public void setImpactosEsperadoProyectoTitulo(String proyectoTitulo) {
        this.impactosEsperadoProyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImpactosEsperadosDTO impactosEsperadosDTO = (ImpactosEsperadosDTO) o;
        if (impactosEsperadosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), impactosEsperadosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ImpactosEsperadosDTO{" +
            "id=" + getId() +
            ", impacto='" + getImpacto() + "'" +
            ", plazo=" + getPlazo() +
            ", indicador='" + getIndicador() + "'" +
            ", supuestos='" + getSupuestos() + "'" +
            ", ordenVista=" + getOrdenVista() +
            ", impactosEsperadoProyecto=" + getImpactosEsperadoProyectoId() +
            ", impactosEsperadoProyecto='" + getImpactosEsperadoProyectoTitulo() + "'" +
            "}";
    }
}
