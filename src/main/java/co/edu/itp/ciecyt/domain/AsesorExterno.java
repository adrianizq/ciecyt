package co.edu.itp.ciecyt.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Un profesional externo a la institucion, gestionado para cubrir una asesoria o un jurado
 * cuando la lista habilitada de la facultad no tiene disponibilidad.
 *
 * <p>El paragrafo 1 del articulo 8 y el paragrafo 1 del articulo 9 del Acuerdo 25 exigen que
 * quien actua por fuera de la lista no tenga vinculo laboral con la institucion y que su
 * idoneidad academica sea verificada por el CIECYT. Por eso la persona externa no tiene una
 * cuenta de usuario institucional: es un dato verificado del que no se deriva un vinculo
 * laboral, y su designacion queda en el integrante_proyecto igual que la de un habilitado.
 */
@Entity
@Table(name = "asesor_externo")
public class AsesorExterno implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ROL_ASESOR = "ASESOR";
    public static final String ROL_JURADO = "JURADO";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombres", nullable = false)
    private String nombres;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    @Column(name = "telefono")
    private String telefono;

    /**
     * Titulos, trayectoria y demas que consta para la verificacion de idoneidad.
     */
    @Column(name = "titulos", length = 2000)
    private String titulos;

    @Column(name = "institucion_origen")
    private String institucionOrigen;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "idoneidad_verificada", nullable = false)
    private Boolean idoneidadVerificada;

    @Column(name = "fecha_verificacion_idoneidad")
    private LocalDate fechaVerificacionIdoneidad;

    /**
     * Login del usuario del CIECYT que registro la verificacion de idoneidad.
     */
    @Column(name = "verificador_login")
    private String verificadorLogin;

    @Column(name = "fuente_verificacion", length = 2000)
    private String fuenteVerificacion;

    @Column(name = "observaciones", length = 2000)
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "asesor_externo_facultad_id", nullable = false)
    private Facultad facultad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTitulos() {
        return titulos;
    }

    public void setTitulos(String titulos) {
        this.titulos = titulos;
    }

    public String getInstitucionOrigen() {
        return institucionOrigen;
    }

    public void setInstitucionOrigen(String institucionOrigen) {
        this.institucionOrigen = institucionOrigen;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol == null || rol.trim().isEmpty() ? null : rol.trim().toUpperCase();
    }

    public Boolean getIdoneidadVerificada() {
        return idoneidadVerificada;
    }

    public void setIdoneidadVerificada(Boolean idoneidadVerificada) {
        this.idoneidadVerificada = idoneidadVerificada;
    }

    public LocalDate getFechaVerificacionIdoneidad() {
        return fechaVerificacionIdoneidad;
    }

    public void setFechaVerificacionIdoneidad(LocalDate fechaVerificacionIdoneidad) {
        this.fechaVerificacionIdoneidad = fechaVerificacionIdoneidad;
    }

    public String getVerificadorLogin() {
        return verificadorLogin;
    }

    public void setVerificadorLogin(String verificadorLogin) {
        this.verificadorLogin = verificadorLogin;
    }

    public String getFuenteVerificacion() {
        return fuenteVerificacion;
    }

    public void setFuenteVerificacion(String fuenteVerificacion) {
        this.fuenteVerificacion = fuenteVerificacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    /**
     * Sus datos identifican por completo al profesional (no es un usuario del sistema).
     */
    public String getNombreCompleto() {
        return ((nombres == null ? "" : nombres) + " " + (apellidos == null ? "" : apellidos)).trim();
    }

    @Override
    public String toString() {
        return "AsesorExterno{" +
            "id=" + id +
            ", documento='" + numeroDocumento + '\'' +
            ", rol='" + rol + '\'' +
            ", idoneidadVerificada=" + idoneidadVerificada +
            '}';
    }
}