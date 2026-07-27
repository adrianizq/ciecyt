package co.edu.itp.ciecyt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import co.edu.itp.ciecyt.domain.enumeration.EnumRespuestas;

/**
 * A ProyectoRespuestas.
 */
@Entity
@Table(name = "proyecto_respuestas")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProyectoRespuestas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "respuesta")
    private EnumRespuestas respuesta;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "viable")
    private Boolean viable;

    @Column(name = "puntaje")
    private Float puntaje;

    @Column(name = "si_no")
    private Boolean siNo;

    @Column(name = "elemento")
    private String elemento;

    @Column(name = "pregunta_tipo_pregunta_id")
    private Long preguntaTipoPreguntaId;

    @Column(name = "pregunta_tipo_pregunta_tipo_pregunta")
    private String preguntaTipoPreguntaTipoPregunta;

    @Column(name = "encabezado")
    private String encabezado;

    @Column(name = "dato")
    private String dato;

    @Column(name = "respuesta_texto")
    private String respuestaTexto;

    @Column(name = "respuesta_numero")
    private Float respuestaNumero;

    @Column(name = "fase_id")
    private Long faseId;

    @Column(name = "authority")
    private String authority;

    @Column(name = "puntaje_maximo")
    private Integer puntajeMaximo;

    @ManyToOne
    @JsonIgnoreProperties(value = "proyectoRespuestas", allowSetters = true)
    private Pregunta proyectoRespuestasPregunta;

    @ManyToOne
    @JsonIgnoreProperties(value = "proyectoRespuestas", allowSetters = true)
    private Proyecto proyectoRespuestasProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumRespuestas getRespuesta() {
        return respuesta;
    }

    public ProyectoRespuestas respuesta(EnumRespuestas respuesta) {
        this.respuesta = respuesta;
        return this;
    }

    public void setRespuesta(EnumRespuestas respuesta) {
        this.respuesta = respuesta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public ProyectoRespuestas observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean isViable() {
        return viable;
    }

    public ProyectoRespuestas viable(Boolean viable) {
        this.viable = viable;
        return this;
    }

    public void setViable(Boolean viable) {
        this.viable = viable;
    }

    public Float getPuntaje() {
        return puntaje;
    }

    public ProyectoRespuestas puntaje(Float puntaje) {
        this.puntaje = puntaje;
        return this;
    }

    public void setPuntaje(Float puntaje) {
        this.puntaje = puntaje;
    }

    public Boolean isSiNo() {
        return siNo;
    }

    public ProyectoRespuestas siNo(Boolean siNo) {
        this.siNo = siNo;
        return this;
    }

    public void setSiNo(Boolean siNo) {
        this.siNo = siNo;
    }

    public String getElemento() {
        return elemento;
    }

    public ProyectoRespuestas elemento(String elemento) {
        this.elemento = elemento;
        return this;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public Long getPreguntaTipoPreguntaId() {
        return preguntaTipoPreguntaId;
    }

    public ProyectoRespuestas preguntaTipoPreguntaId(Long preguntaTipoPreguntaId) {
        this.preguntaTipoPreguntaId = preguntaTipoPreguntaId;
        return this;
    }

    public void setPreguntaTipoPreguntaId(Long preguntaTipoPreguntaId) {
        this.preguntaTipoPreguntaId = preguntaTipoPreguntaId;
    }

    public String getPreguntaTipoPreguntaTipoPregunta() {
        return preguntaTipoPreguntaTipoPregunta;
    }

    public ProyectoRespuestas preguntaTipoPreguntaTipoPregunta(String preguntaTipoPreguntaTipoPregunta) {
        this.preguntaTipoPreguntaTipoPregunta = preguntaTipoPreguntaTipoPregunta;
        return this;
    }

    public void setPreguntaTipoPreguntaTipoPregunta(String preguntaTipoPreguntaTipoPregunta) {
        this.preguntaTipoPreguntaTipoPregunta = preguntaTipoPreguntaTipoPregunta;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public ProyectoRespuestas encabezado(String encabezado) {
        this.encabezado = encabezado;
        return this;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public String getDato() {
        return dato;
    }

    public ProyectoRespuestas dato(String dato) {
        this.dato = dato;
        return this;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getRespuestaTexto() {
        return respuestaTexto;
    }

    public ProyectoRespuestas respuestaTexto(String respuestaTexto) {
        this.respuestaTexto = respuestaTexto;
        return this;
    }

    public void setRespuestaTexto(String respuestaTexto) {
        this.respuestaTexto = respuestaTexto;
    }

    public Float getRespuestaNumero() {
        return respuestaNumero;
    }

    public ProyectoRespuestas respuestaNumero(Float respuestaNumero) {
        this.respuestaNumero = respuestaNumero;
        return this;
    }

    public void setRespuestaNumero(Float respuestaNumero) {
        this.respuestaNumero = respuestaNumero;
    }

    public Long getFaseId() {
        return faseId;
    }

    public ProyectoRespuestas faseId(Long faseId) {
        this.faseId = faseId;
        return this;
    }

    public void setFaseId(Long faseId) {
        this.faseId = faseId;
    }

    public String getAuthority() {
        return authority;
    }

    public ProyectoRespuestas authority(String authority) {
        this.authority = authority;
        return this;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public ProyectoRespuestas puntajeMaximo(Integer puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
        return this;
    }

    public void setPuntajeMaximo(Integer puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }

    public Pregunta getProyectoRespuestasPregunta() {
        return proyectoRespuestasPregunta;
    }

    public ProyectoRespuestas proyectoRespuestasPregunta(Pregunta pregunta) {
        this.proyectoRespuestasPregunta = pregunta;
        return this;
    }

    public void setProyectoRespuestasPregunta(Pregunta pregunta) {
        this.proyectoRespuestasPregunta = pregunta;
    }

    public Proyecto getProyectoRespuestasProyecto() {
        return proyectoRespuestasProyecto;
    }

    public ProyectoRespuestas proyectoRespuestasProyecto(Proyecto proyecto) {
        this.proyectoRespuestasProyecto = proyecto;
        return this;
    }

    public void setProyectoRespuestasProyecto(Proyecto proyecto) {
        this.proyectoRespuestasProyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoRespuestas)) {
            return false;
        }
        return id != null && id.equals(((ProyectoRespuestas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProyectoRespuestas{" +
            "id=" + getId() +
            ", respuesta='" + getRespuesta() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", viable='" + isViable() + "'" +
            ", puntaje=" + getPuntaje() +
            ", siNo='" + isSiNo() + "'" +
            ", elemento='" + getElemento() + "'" +
            ", preguntaTipoPreguntaId=" + getPreguntaTipoPreguntaId() +
            ", preguntaTipoPreguntaTipoPregunta='" + getPreguntaTipoPreguntaTipoPregunta() + "'" +
            ", encabezado='" + getEncabezado() + "'" +
            ", dato='" + getDato() + "'" +
            ", respuestaTexto='" + getRespuestaTexto() + "'" +
            ", respuestaNumero=" + getRespuestaNumero() +
            ", faseId=" + getFaseId() +
            ", authority='" + getAuthority() + "'" +
            ", puntajeMaximo=" + getPuntajeMaximo() +
            "}";
    }
}
