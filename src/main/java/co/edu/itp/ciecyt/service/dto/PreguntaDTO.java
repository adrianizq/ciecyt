package co.edu.itp.ciecyt.service.dto;

import co.edu.itp.ciecyt.domain.Authority;
import co.edu.itp.ciecyt.domain.Modalidad;
import co.edu.itp.ciecyt.domain.Pregunta;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Pregunta} entity.
 */
public class PreguntaDTO implements Serializable {

    private Long id;

    private String encabezado;

    private String descripcion;

    private String pregunta;

    private Float puntaje;

    private Float puntajeMaximo;


    private Long orden;
    private Long preguntaTipoPreguntaId;

    private String preguntaTipoPreguntaTipoPregunta;

    private Long preguntaElementoId;

    private String preguntaElementoElemento;

    private Long preguntaFaseId;

    private String preguntaFaseFase;

    private List<PreguntaAuthorityDTO> authorities;

    private List<PreguntaModalidadDTO> preguntaModalidads;



    public List<PreguntaAuthorityDTO> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<PreguntaAuthorityDTO> authorities) {
        this.authorities = authorities;
    }

    public List<PreguntaModalidadDTO> getPreguntaModalidads() {
        return preguntaModalidads;
    }

    public void setPreguntaModalidads(List<PreguntaModalidadDTO> preguntaModalidads) {
        this.preguntaModalidads = preguntaModalidads;
    }


    public Long getOrden() {
        return orden;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public Float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Float puntaje) {
        this.puntaje = puntaje;
    }

    public Float getPuntajeMaximo() {
        return puntajeMaximo;
    }

    public void setPuntajeMaximo(Float puntajeMaximo) {
        this.puntajeMaximo = puntajeMaximo;
    }

    public Long getPreguntaTipoPreguntaId() {
        return preguntaTipoPreguntaId;
    }

    public void setPreguntaTipoPreguntaId(Long tipoPreguntaId) {
        this.preguntaTipoPreguntaId = tipoPreguntaId;
    }

    public String getPreguntaTipoPreguntaTipoPregunta() {
        return preguntaTipoPreguntaTipoPregunta;
    }

    public void setPreguntaTipoPreguntaTipoPregunta(String tipoPreguntaTipoPregunta) {
        this.preguntaTipoPreguntaTipoPregunta = tipoPreguntaTipoPregunta;
    }

    public Long getPreguntaElementoId() {
        return preguntaElementoId;
    }

    public void setPreguntaElementoId(Long elementoId) {
        this.preguntaElementoId = elementoId;
    }

    public String getPreguntaElementoElemento() {
        return preguntaElementoElemento;
    }

    public void setPreguntaElementoElemento(String elementoElemento) {
        this.preguntaElementoElemento = elementoElemento;
    }

    public Long getPreguntaFaseId() {
        return preguntaFaseId;
    }

    public void setPreguntaFaseId(Long fasesId) {
        this.preguntaFaseId = fasesId;
    }

    public String getPreguntaFaseFase() {
        return preguntaFaseFase;
    }

    public void setPreguntaFaseFase(String fasesFase) {
        this.preguntaFaseFase = fasesFase;
    }

    /*public List<PreguntaAuthorityDTO> getListPreguntaAuthorityDTO() {
        return ListPreguntaAuthorityDTO;
    }

    public void setListPreguntaAuthorityDTO(List<PreguntaAuthorityDTO> listPreguntaAuthorityDTO) {
        ListPreguntaAuthorityDTO = listPreguntaAuthorityDTO;
    }

    public List<PreguntaModalidadDTO> getListPreguntaModalidadDTO() {
        return ListPreguntaModalidadDTO;
    }

    public void setListPreguntaModalidadDTO(List<PreguntaModalidadDTO> listPreguntaModalidadDTO) {
        ListPreguntaModalidadDTO = listPreguntaModalidadDTO;
    }

     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PreguntaDTO)) {
            return false;
        }

        return id != null && id.equals(((PreguntaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PreguntaDTO{" +
            "id=" + getId() +
            ", encabezado='" + getEncabezado() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", pregunta='" + getPregunta() + "'" +
            ", puntaje=" + getPuntaje() +
            ", puntajeMaximo=" + getPuntajeMaximo() +
            ", preguntaTipoPreguntaId=" + getPreguntaTipoPreguntaId() +
            ", preguntaTipoPreguntaTipoPregunta='" + getPreguntaTipoPreguntaTipoPregunta() + "'" +
            ", preguntaElementoId=" + getPreguntaElementoId() +
            ", preguntaElementoElemento='" + getPreguntaElementoElemento() + "'" +
            ", preguntaFaseId=" + getPreguntaFaseId() +
            ", preguntaFaseFase='" + getPreguntaFaseFase() + "'" +
            ", orden='" + getOrden() + "'" +
            "}";
    }


}
