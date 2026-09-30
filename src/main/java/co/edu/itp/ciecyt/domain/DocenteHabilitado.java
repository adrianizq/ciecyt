package co.edu.itp.ciecyt.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Un profesor habilitado por su facultad para ser asesor o jurado.
 *
 * <p>El reglamento no designa a cualquier profesor con el rol: el articulo 8 designa al asesor de
 * tesis "a partir de la lista de profesores habilitados por la decanatura", y el paragrafo 2
 * obliga a las decanaturas a remitir esa relacion a CIECYT al inicio de cada periodo academico.
 * Tener el rol en el sistema habilita para evaluar, pero solo estar en esta lista habilita para
 * que se designe a alguien en un proyecto.
 *
 * <p>La lista se revisa cada semestre porque entran docentes nuevos y vuelven los que habian
 * salido, asi que una habilitacion se cierra con fechaHasta y se vuelve a abrir como un registro
 * nuevo: queda el historico de cada vigencia. La designacion, en cambio, dura hasta el final
 * del proceso salvo que el asesor desista, y por eso no se guarda aqui.
 *
 * <p>Una persona puede estar habilitada en varias facultades, y como asesor y como jurado de
 * forma independiente. Solo admite un registro vigente por persona, facultad y rol, y lo
 * garantiza el indice parcial ux_docente_habilitado_vigente.
 */
@Entity
@Table(name = "docente_habilitado")
public class DocenteHabilitado implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ROL_ASESOR = "ASESOR";
    public static final String ROL_JURADO = "JURADO";

    /**
     * El estudiante no se habilita: es parte del proyecto por ser su autor y no aparece en el
     * padron. Se reconoce aqui para eximirlo del chequeo de habilitacion.
     */
    public static final String ROL_ESTUDIANTE_MARKER = "ROLE_ESTUDIANTE";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "docente_habilitado_user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "docente_habilitado_facultad_id", nullable = false)
    private Facultad facultad;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "fecha_desde")
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta")
    private LocalDate fechaHasta;

    @Column(name = "acto_resolucion")
    private String actoResolucion;

    @Column(name = "observaciones")
    private String observaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol == null || rol.trim().isEmpty() ? null : rol.trim().toUpperCase();
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getActoResolucion() {
        return actoResolucion;
    }

    public void setActoResolucion(String actoResolucion) {
        this.actoResolucion = actoResolucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * El registro sigue vigente: no se le puso fecha de cierre.
     */
    public boolean estaVigente() {
        return fechaHasta == null;
    }

    @Override
    public String toString() {
        return "DocenteHabilitado{" +
            "id=" + id +
            ", rol='" + rol + '\'' +
            ", fechaDesde=" + fechaDesde +
            ", fechaHasta=" + fechaHasta +
            '}';
    }
}
