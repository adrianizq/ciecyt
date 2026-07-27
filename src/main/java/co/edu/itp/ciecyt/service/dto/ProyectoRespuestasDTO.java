package co.edu.itp.ciecyt.service.dto;

import co.edu.itp.ciecyt.domain.ProyectoRespuestas;
import co.edu.itp.ciecyt.domain.enumeration.EnumRespuestas;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.ProyectoRespuestas} entity.
 */
public class ProyectoRespuestasDTO implements Serializable {

    private Long id;

    private EnumRespuestas respuesta;

    private String observaciones;

    private Boolean viable;

    private Float puntaje;

    private Boolean siNo;

    private String elemento;

    private Long preguntaTipoPreguntaId;

    private String preguntaTipoPreguntaTipoPregunta;

    private String encabezado;

    private String dato;

    private String respuestaTexto;

    private Float respuestaNumero;

    private Long proyectoRespuestasPreguntaId;

    private String proyectoRespuestasPreguntaPregunta;

    private Long proyectoRespuestasProyectoId;

    private String proyectoRespuestasProyectoTitulo;

    private Long faseId;

    private String authority;

    private Integer puntajeMaximo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumRespuestas getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(EnumRespuestas respuesta) {
        this.respuesta = respuesta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean isViable() {
        return viable;
    }

    public void setViable(Boolean viable) {
        this.viable = viable;
    }

    public Float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Float puntaje) {
        this.puntaje = puntaje;
    }

    public Boolean isSiNo() {
        return siNo;
    }

    public void setSiNo(Boolean siNo) {
        this.siNo = siNo;
    }

    public String getElemento() {
        return elemento;
    }

    public void setElemento(String elemento) {
        this.elemento = elemento;
    }

    public Long getPreguntaTipoPreguntaId() {
        return preguntaTipoPreguntaId;
    }

    public void setPreguntaTipoPreguntaId(Long preguntaTipoPreguntaId) {
        this.preguntaTipoPreguntaId = preguntaTipoPreguntaId;
    }

    public String getPreguntaTipoPreguntaTipoPregunta() {
        return preguntaTipoPreguntaTipoPregunta;
    }

    public void setPreguntaTipoPreguntaTipoPregunta(String preguntaTipoPreguntaTipoPregunta) {
        this.preguntaTipoPreguntaTipoPregunta = preguntaTipoPreguntaTipoPregunta;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getRespuestaTexto() {
        return respuestaTexto;
    }

    public void setRespuestaTexto(String respuestaTexto) {
        this.respuestaTexto = respuestaTexto;
    }

    public Float getRespuestaNumero() {
        return respuestaNumero;
    }

    public void setRespuestaNumero(Float respuestaNumero) {
        this.respuestaNumero = respuestaNumero;
    }

    public Long getProyectoRespuestasPreguntaId() {
        return proyectoRespuestasPreguntaId;
    }

    public void setProyectoRespuestasPreguntaId(Long preguntaId) {
        this.proyectoRespuestasPreguntaId = preguntaId;
    }

    public String getProyectoRespuestasPreguntaPregunta() {
        return proyectoRespuestasPreguntaPregunta;
    }

    public void setProyectoRespuestasPreguntaPregunta(String preguntaPregunta) {
        this.proyectoRespuestasPreguntaPregunta = preguntaPregunta;
    }

    public Long getProyectoRespuestasProyectoId() {
        return proyectoRespuestasProyectoId;
    }

    public void setProyectoRespuestasProyectoId(Long proyectoId) {
        this.proyectoRespuestasProyectoId = proyectoId;
    }

    public String getProyectoRespuestasProyectoTitulo() {
        return proyectoRespuestasProyectoTitulo;
    }

    public void setProyectoRespuestasProyectoTitulo(String proyectoTitulo) {
        this.proyectoRespuestasProyectoTitulo = proyectoTitulo;
    }

    public Long getFaseId() {
        return faseId;
    }

    public void setFaseId(Long faseId) {
        this.faseId = faseId;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Integer getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(Integer puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoRespuestasDTO)) {
            return false;
        }

        return id != null && id.equals(((ProyectoRespuestasDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProyectoRespuestasDTO{" +
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
            ", proyectoRespuestasPreguntaId=" + getProyectoRespuestasPreguntaId() +
            ", proyectoRespuestasPreguntaPregunta='" + getProyectoRespuestasPreguntaPregunta() + "'" +
            ", proyectoRespuestasProyectoId=" + getProyectoRespuestasProyectoId() +
            ", proyectoRespuestasProyectoTitulo='" + getProyectoRespuestasProyectoTitulo() + "'" +
            "}";
    }
}
