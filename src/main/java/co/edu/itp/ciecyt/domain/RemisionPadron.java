package co.edu.itp.ciecyt.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * La relacion de profesores habilitados que una decanatura remite a CIECYT.
 *
 * <p>El paragrafo 2 del articulo 8 y el del articulo 9 obligan a remitirla al inicio de cada
 * periodo academico. La obligacion no es solo tener la lista al dia: es haberla remitido, y poder
 * demostrar cuando y que se remitio. Mantener el padron al dia no cumple por si solo el parrafo,
 * asi que esta entidad guarda la remision como un hecho fechado e inmutable.
 *
 * <p>Una remision enviada no se edita. Si despues la decanatura habilita a alguien o da de baja a
 * otro, eso pertenece al padron y se vera en la siguiente remision; corregir una ya enviada
 * dejaria constancia de algo que no fue lo que se remitio.
 *
 * <p>El periodo va como texto porque todavia no existe un calendario academico en el sistema. Es
 * el unico dato que no se puede deducir: cuando exista ese calendario, esta columna se cambia por
 * una llave foranea.
 */
@Entity
@Table(name = "remision_padron")
public class RemisionPadron implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ESTADO_BORRADOR = "BORRADOR";
    public static final String ESTADO_ENVIADO = "ENVIADO";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "remision_padron_facultad_id", nullable = false)
    private Facultad facultad;

    /**
     * Periodo academico al que corresponde la remision, tal como lo nombra la institucion.
     */
    @Column(name = "periodo", nullable = false)
    private String periodo;

    @Column(name = "fecha_remision", nullable = false)
    private LocalDateTime fechaRemision;

    /**
     * Quien remitio. Se guarda aunque despues la persona se desvincule: es la evidencia de quien
     * acted en ese momento.
     */
    @ManyToOne
    @JoinColumn(name = "remision_padron_remitido_por_id", nullable = false)
    private User remitidoPor;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "observaciones")
    private String observaciones;

    @OneToMany(mappedBy = "remision", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RemisionPadronDocente> docentes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo == null || periodo.trim().isEmpty() ? null : periodo.trim();
    }

    public LocalDateTime getFechaRemision() {
        return fechaRemision;
    }

    public void setFechaRemision(LocalDateTime fechaRemision) {
        this.fechaRemision = fechaRemision;
    }

    public User getRemitidoPor() {
        return remitidoPor;
    }

    public void setRemitidoPor(User remitidoPor) {
        this.remitidoPor = remitidoPor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<RemisionPadronDocente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<RemisionPadronDocente> docentes) {
        this.docentes = docentes;
    }

    /**
     * Copia un docente habilitado a la remision. Se guarda el nombre del momento: si despues
     * cambia, la remision debe seguir diciendo a quien se remitio.
     */
    public void agregarDocente(User docente, String rol, String nombre, String correo) {
        RemisionPadronDocente item = new RemisionPadronDocente();
        item.setRemision(this);
        item.setDocente(docente);
        item.setRol(rol);
        item.setNombreAlRemitir(nombre);
        item.setCorreoAlRemitir(correo);
        this.docentes.add(item);
    }

    public long contarPorRol(String rol) {
        return docentes.stream().filter(d -> rol.equals(d.getRol())).count();
    }

    public boolean estaEnviada() {
        return ESTADO_ENVIADO.equals(estado);
    }

    @Override
    public String toString() {
        return "RemisionPadron{" +
            "id=" + id +
            ", periodo='" + periodo + '\'' +
            ", estado='" + estado + '\'' +
            ", fechaRemision=" + fechaRemision +
            '}';
    }
}
