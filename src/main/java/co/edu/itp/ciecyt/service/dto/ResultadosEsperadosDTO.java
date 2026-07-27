package co.edu.itp.ciecyt.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.ResultadosEsperados} entity.
 */
public class ResultadosEsperadosDTO implements Serializable {

    private Long id;

    private String resultado;

    private String indicador;

    private String beneficiario;

    private Integer ordenVista;


    private Long resultadosEsperadosProyectoId;

    private String resultadosEsperadosProyectoTitulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public Integer getOrdenVista() {
        return ordenVista;
    }

    public void setOrdenVista(Integer ordenVista) {
        this.ordenVista = ordenVista;
    }

    public Long getResultadosEsperadosProyectoId() {
        return resultadosEsperadosProyectoId;
    }

    public void setResultadosEsperadosProyectoId(Long proyectoId) {
        this.resultadosEsperadosProyectoId = proyectoId;
    }

    public String getResultadosEsperadosProyectoTitulo() {
        return resultadosEsperadosProyectoTitulo;
    }

    public void setResultadosEsperadosProyectoTitulo(String proyectoTitulo) {
        this.resultadosEsperadosProyectoTitulo = proyectoTitulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResultadosEsperadosDTO resultadosEsperadosDTO = (ResultadosEsperadosDTO) o;
        if (resultadosEsperadosDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resultadosEsperadosDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResultadosEsperadosDTO{" +
            "id=" + getId() +
            ", resultado='" + getResultado() + "'" +
            ", indicador='" + getIndicador() + "'" +
            ", beneficiario='" + getBeneficiario() + "'" +
            ", ordenVista=" + getOrdenVista() +
            ", resultadosEsperadosProyecto=" + getResultadosEsperadosProyectoId() +
            ", resultadosEsperadosProyecto='" + getResultadosEsperadosProyectoTitulo() + "'" +
            "}";
    }
}
