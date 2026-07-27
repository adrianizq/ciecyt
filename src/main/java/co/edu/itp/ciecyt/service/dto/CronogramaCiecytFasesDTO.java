package co.edu.itp.ciecyt.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.CronogramaCiecytFases} entity.
 */
public class CronogramaCiecytFasesDTO implements Serializable {

    private Long id;

    private LocalDate inicioFase;

    private LocalDate finFase;

    private String textoExplicativo;


    private Long cronogramaCiecytFasesCronogramaCiecytId;

    private String cronogramaCiecytFasesCronogramaCiecytTituloCronograma;

    private Long cronogramaCiecytFasesFasesId;

    private String cronogramaCiecytFasesFasesFase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicioFase() {
        return inicioFase;
    }

    public void setInicioFase(LocalDate inicioFase) {
        this.inicioFase = inicioFase;
    }

    public LocalDate getFinFase() {
        return finFase;
    }

    public void setFinFase(LocalDate finFase) {
        this.finFase = finFase;
    }

    public String getTextoExplicativo() {
        return textoExplicativo;
    }

    public void setTextoExplicativo(String textoExplicativo) {
        this.textoExplicativo = textoExplicativo;
    }

    public Long getCronogramaCiecytFasesCronogramaCiecytId() {
        return cronogramaCiecytFasesCronogramaCiecytId;
    }

    public void setCronogramaCiecytFasesCronogramaCiecytId(Long cronogramaCiecytId) {
        this.cronogramaCiecytFasesCronogramaCiecytId = cronogramaCiecytId;
    }

    public String getCronogramaCiecytFasesCronogramaCiecytTituloCronograma() {
        return cronogramaCiecytFasesCronogramaCiecytTituloCronograma;
    }

    public void setCronogramaCiecytFasesCronogramaCiecytTituloCronograma(String cronogramaCiecytTituloCronograma) {
        this.cronogramaCiecytFasesCronogramaCiecytTituloCronograma = cronogramaCiecytTituloCronograma;
    }

    public Long getCronogramaCiecytFasesFasesId() {
        return cronogramaCiecytFasesFasesId;
    }

    public void setCronogramaCiecytFasesFasesId(Long fasesId) {
        this.cronogramaCiecytFasesFasesId = fasesId;
    }

    public String getCronogramaCiecytFasesFasesFase() {
        return cronogramaCiecytFasesFasesFase;
    }

    public void setCronogramaCiecytFasesFasesFase(String fasesFase) {
        this.cronogramaCiecytFasesFasesFase = fasesFase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CronogramaCiecytFasesDTO cronogramaCiecytFasesDTO = (CronogramaCiecytFasesDTO) o;
        if (cronogramaCiecytFasesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cronogramaCiecytFasesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CronogramaCiecytFasesDTO{" +
            "id=" + getId() +
            ", inicioFase='" + getInicioFase() + "'" +
            ", finFase='" + getFinFase() + "'" +
            ", textoExplicativo='" + getTextoExplicativo() + "'" +
            ", cronogramaCiecytFasesCronogramaCiecyt=" + getCronogramaCiecytFasesCronogramaCiecytId() +
            ", cronogramaCiecytFasesCronogramaCiecyt='" + getCronogramaCiecytFasesCronogramaCiecytTituloCronograma() + "'" +
            ", cronogramaCiecytFasesFases=" + getCronogramaCiecytFasesFasesId() +
            ", cronogramaCiecytFasesFases='" + getCronogramaCiecytFasesFasesFase() + "'" +
            "}";
    }
}
