package co.edu.itp.ciecyt.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Un docente dentro de una remision ya remitida.
 *
 * <p>Guarda el nombre y el correo tal como estaban el dia de la remision. Si la institucion
 * corrige un nombre o alguien cambia de correo, la remision anterior tiene que seguir diciendo lo
 * que se remitio, que es justo para lo que sirve como evidencia.
 */
@Entity
@Table(name = "remision_padron_docente")
public class RemisionPadronDocente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "remision_padron_docente_remision_id", nullable = false)
    private RemisionPadron remision;

    @ManyToOne
    @JoinColumn(name = "remision_padron_docente_docente_id", nullable = false)
    private User docente;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "nombre_al_remitir")
    private String nombreAlRemitir;

    @Column(name = "correo_al_remitir")
    private String correoAlRemitir;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RemisionPadron getRemision() {
        return remision;
    }

    public void setRemision(RemisionPadron remision) {
        this.remision = remision;
    }

    public User getDocente() {
        return docente;
    }

    public void setDocente(User docente) {
        this.docente = docente;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol == null || rol.trim().isEmpty() ? null : rol.trim().toUpperCase();
    }

    public String getNombreAlRemitir() {
        return nombreAlRemitir;
    }

    public void setNombreAlRemitir(String nombreAlRemitir) {
        this.nombreAlRemitir = nombreAlRemitir;
    }

    public String getCorreoAlRemitir() {
        return correoAlRemitir;
    }

    public void setCorreoAlRemitir(String correoAlRemitir) {
        this.correoAlRemitir = correoAlRemitir;
    }

    @Override
    public String toString() {
        return "RemisionPadronDocente{" +
            "id=" + id +
            ", rol='" + rol + '\'' +
            ", nombreAlRemitir='" + nombreAlRemitir + '\'' +
            '}';
    }
}
