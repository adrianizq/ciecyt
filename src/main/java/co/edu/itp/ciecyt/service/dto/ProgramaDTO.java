package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Programa} entity.
 */
public class ProgramaDTO implements Serializable {
    
    private Long id;

    private String programa;

    private String descripcion;

    private String codigoInterno;

    private String codigoSnies;

    private Integer creditos;

    private String ciclo;

    private String resolucion;

    private String titulo;

    private Integer duracionSemestres;


    private Long programaFacultadId;

    private String programaFacultadFacultad;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getCodigoSnies() {
        return codigoSnies;
    }

    public void setCodigoSnies(String codigoSnies) {
        this.codigoSnies = codigoSnies;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracionSemestres() {
        return duracionSemestres;
    }

    public void setDuracionSemestres(Integer duracionSemestres) {
        this.duracionSemestres = duracionSemestres;
    }

    public Long getProgramaFacultadId() {
        return programaFacultadId;
    }

    public void setProgramaFacultadId(Long facultadId) {
        this.programaFacultadId = facultadId;
    }

    public String getProgramaFacultadFacultad() {
        return programaFacultadFacultad;
    }

    public void setProgramaFacultadFacultad(String facultadFacultad) {
        this.programaFacultadFacultad = facultadFacultad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgramaDTO)) {
            return false;
        }

        return id != null && id.equals(((ProgramaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProgramaDTO{" +
            "id=" + getId() +
            ", programa='" + getPrograma() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", codigoInterno='" + getCodigoInterno() + "'" +
            ", codigoSnies='" + getCodigoSnies() + "'" +
            ", creditos=" + getCreditos() +
            ", ciclo='" + getCiclo() + "'" +
            ", resolucion='" + getResolucion() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", duracionSemestres=" + getDuracionSemestres() +
            ", programaFacultadId=" + getProgramaFacultadId() +
            ", programaFacultadFacultad='" + getProgramaFacultadFacultad() + "'" +
            "}";
    }
}
