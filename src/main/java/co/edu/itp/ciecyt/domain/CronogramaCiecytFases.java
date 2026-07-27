package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CronogramaCiecytFases.
 */
@Entity
@Table(name = "cronograma_ciecyt_fases")
public class CronogramaCiecytFases implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "inicio_fase")
    private LocalDate inicioFase;

    @Column(name = "fin_fase")
    private LocalDate finFase;

    @Column(name = "texto_explicativo")
    private String textoExplicativo;

    @ManyToOne
    @JsonIgnoreProperties("cronogramaCiecytFases")
    private CronogramaCiecyt cronogramaCiecytFasesCronogramaCiecyt;

    @ManyToOne
    @JsonIgnoreProperties("cronogramaCiecytFases")
    private Fases cronogramaCiecytFasesFases;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getInicioFase() {
        return inicioFase;
    }

    public CronogramaCiecytFases inicioFase(LocalDate inicioFase) {
        this.inicioFase = inicioFase;
        return this;
    }

    public void setInicioFase(LocalDate inicioFase) {
        this.inicioFase = inicioFase;
    }

    public LocalDate getFinFase() {
        return finFase;
    }

    public CronogramaCiecytFases finFase(LocalDate finFase) {
        this.finFase = finFase;
        return this;
    }

    public void setFinFase(LocalDate finFase) {
        this.finFase = finFase;
    }

    public String getTextoExplicativo() {
        return textoExplicativo;
    }

    public CronogramaCiecytFases textoExplicativo(String textoExplicativo) {
        this.textoExplicativo = textoExplicativo;
        return this;
    }

    public void setTextoExplicativo(String textoExplicativo) {
        this.textoExplicativo = textoExplicativo;
    }

    public CronogramaCiecyt getCronogramaCiecytFasesCronogramaCiecyt() {
        return cronogramaCiecytFasesCronogramaCiecyt;
    }

    public CronogramaCiecytFases cronogramaCiecytFasesCronogramaCiecyt(CronogramaCiecyt cronogramaCiecyt) {
        this.cronogramaCiecytFasesCronogramaCiecyt = cronogramaCiecyt;
        return this;
    }

    public void setCronogramaCiecytFasesCronogramaCiecyt(CronogramaCiecyt cronogramaCiecyt) {
        this.cronogramaCiecytFasesCronogramaCiecyt = cronogramaCiecyt;
    }

    public Fases getCronogramaCiecytFasesFases() {
        return cronogramaCiecytFasesFases;
    }

    public CronogramaCiecytFases cronogramaCiecytFasesFases(Fases fases) {
        this.cronogramaCiecytFasesFases = fases;
        return this;
    }

    public void setCronogramaCiecytFasesFases(Fases fases) {
        this.cronogramaCiecytFasesFases = fases;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CronogramaCiecytFases)) {
            return false;
        }
        return id != null && id.equals(((CronogramaCiecytFases) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CronogramaCiecytFases{" +
            "id=" + getId() +
            ", inicioFase='" + getInicioFase() + "'" +
            ", finFase='" + getFinFase() + "'" +
            ", textoExplicativo='" + getTextoExplicativo() + "'" +
            "}";
    }
}
