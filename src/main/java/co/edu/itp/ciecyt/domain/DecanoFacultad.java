package co.edu.itp.ciecyt.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DecanoFacultad.
 *
 * El cargo de decano es un dato y no una fila de jhi_user_authority porque el alcance es por
 * facultad: la misma persona puede ser asesora en otra facultad y decana en una sola. Una
 * facultad tiene un (1) decano vigente; al cambiar la persona se cierra el registro anterior
 * (fechaHasta) y queda el historico. La unicidad del decano vigente la garantiza el indice
 * parcial ux_decano_facultad_vigente.
 */
@Entity
@Table(name = "decano_facultad")
public class DecanoFacultad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "decano_facultad_facultad_id", nullable = false)
    private Facultad facultad;

    @ManyToOne
    @JoinColumn(name = "decano_facultad_user_id", nullable = false)
    private User user;

    @Column(name = "cargo")
    private String cargo = "Decano";

    @Column(name = "es_delegado")
    private Boolean esDelegado = Boolean.FALSE;

    @Column(name = "fecha_desde")
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDate fechaHasta;

    @Column(name = "acto_resolucion")
    private String actoResolucion;

    @Column(name = "observaciones")
    private String observaciones;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public DecanoFacultad facultad(Facultad facultad) {
        this.facultad = facultad;
        return this;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public User getUser() {
        return user;
    }

    public DecanoFacultad user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCargo() {
        return cargo;
    }

    public DecanoFacultad cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Boolean getEsDelegado() {
        return esDelegado;
    }

    public DecanoFacultad esDelegado(Boolean esDelegado) {
        this.esDelegado = esDelegado;
        return this;
    }

    public void setEsDelegado(Boolean esDelegado) {
        this.esDelegado = esDelegado;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public DecanoFacultad fechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
        return this;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public DecanoFacultad fechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
        return this;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getActoResolucion() {
        return actoResolucion;
    }

    public DecanoFacultad actoResolucion(String actoResolucion) {
        this.actoResolucion = actoResolucion;
        return this;
    }

    public void setActoResolucion(String actoResolucion) {
        this.actoResolucion = actoResolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public DecanoFacultad observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Un decano esta vigente mientras no tenga fecha de cierre.
     */
    public boolean isVigente() {
        return fechaHasta == null;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DecanoFacultad)) {
            return false;
        }
        return id != null && id.equals(((DecanoFacultad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DecanoFacultad{" +
            "id=" + getId() +
            ", facultad=" + (facultad != null ? facultad.getId() : null) +
            ", user=" + (user != null ? user.getLogin() : null) +
            ", cargo='" + getCargo() + "'" +
            ", esDelegado='" + getEsDelegado() + "'" +
            ", fechaDesde=" + getFechaDesde() +
            ", fechaHasta=" + getFechaHasta() +
            "}";
    }
}
