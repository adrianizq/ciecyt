package co.edu.itp.ciecyt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Retroalimentacion.
 */
@Entity
@Table(name = "retroalimentacion")
public class Retroalimentacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "retroalimentacion")
    private String retroalimentacion;

    @Column(name = "fecha_retroalimentacion")
    private LocalDate fechaRetroalimentacion;

    @Column(name = "estado_retroalimentacion")
    private LocalDate estadoRetroalimentacion;

    @Column(name = "estado_proyecto_fase")
    private Integer estadoProyectoFase;

    @ManyToOne
    @JsonIgnoreProperties("retroalimentacions")
    private ProyectoFase retroalimentacionProyectoFase;

    @ManyToOne
    @JsonIgnoreProperties("retroalimentacions")
    private User retroalimentacionUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Retroalimentacion titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public Retroalimentacion retroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
        return this;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public LocalDate getFechaRetroalimentacion() {
        return fechaRetroalimentacion;
    }

    public Retroalimentacion fechaRetroalimentacion(LocalDate fechaRetroalimentacion) {
        this.fechaRetroalimentacion = fechaRetroalimentacion;
        return this;
    }

    public void setFechaRetroalimentacion(LocalDate fechaRetroalimentacion) {
        this.fechaRetroalimentacion = fechaRetroalimentacion;
    }

    public LocalDate getEstadoRetroalimentacion() {
        return estadoRetroalimentacion;
    }

    public Retroalimentacion estadoRetroalimentacion(LocalDate estadoRetroalimentacion) {
        this.estadoRetroalimentacion = estadoRetroalimentacion;
        return this;
    }

    public void setEstadoRetroalimentacion(LocalDate estadoRetroalimentacion) {
        this.estadoRetroalimentacion = estadoRetroalimentacion;
    }

    public Integer getEstadoProyectoFase() {
        return estadoProyectoFase;
    }

    public Retroalimentacion estadoProyectoFase(Integer estadoProyectoFase) {
        this.estadoProyectoFase = estadoProyectoFase;
        return this;
    }

    public void setEstadoProyectoFase(Integer estadoProyectoFase) {
        this.estadoProyectoFase = estadoProyectoFase;
    }

    public ProyectoFase getRetroalimentacionProyectoFase() {
        return retroalimentacionProyectoFase;
    }

    public Retroalimentacion retroalimentacionProyectoFase(ProyectoFase proyectoFase) {
        this.retroalimentacionProyectoFase = proyectoFase;
        return this;
    }

    public void setRetroalimentacionProyectoFase(ProyectoFase proyectoFase) {
        this.retroalimentacionProyectoFase = proyectoFase;
    }

    public User getRetroalimentacionUser() {
        return retroalimentacionUser;
    }

    public Retroalimentacion retroalimentacionUser(User user) {
        this.retroalimentacionUser = user;
        return this;
    }

    public void setRetroalimentacionUser(User user) {
        this.retroalimentacionUser = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Retroalimentacion)) {
            return false;
        }
        return id != null && id.equals(((Retroalimentacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Retroalimentacion{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", retroalimentacion='" + getRetroalimentacion() + "'" +
            ", fechaRetroalimentacion='" + getFechaRetroalimentacion() + "'" +
            ", estadoRetroalimentacion='" + getEstadoRetroalimentacion() + "'" +
            ", estadoProyectoFase=" + getEstadoProyectoFase() +
            "}";
    }
}
