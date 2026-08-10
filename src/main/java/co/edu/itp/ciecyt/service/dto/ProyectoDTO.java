package co.edu.itp.ciecyt.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link co.edu.itp.ciecyt.domain.Proyecto} entity.
 */
public class ProyectoDTO implements Serializable {

    private Long id;

    private String titulo;

    private String url;

    private String lugarEjecucion;

    private String duracion;

    private LocalDate fechaIni;

    private LocalDate fechaFin;

    private Double contrapartidaPesos;

    private Double contrapartidaEspecie;

    private String palabrasClave;

    private String convocatoria;

    private String tipo;

    private String referencias;

    private String departamento;

    private String municipio;

    private Boolean viable;

    private Boolean enviado;

    private ZonedDateTime fechaEnvioPropuesta;

    private ZonedDateTime fechaEnvioProyecto;

    private Double nota;

    private String conclusion;

    private String recomendaciones;

    private String recomendacionesAsesorPropuesta;

    private String recomendacionesAsesorProyecto;

    private String recomendacionesJuradoProyecto;

    private String recomendacionesJuradoSustentacion;

    private String viabilidad;

    private Boolean preEnviado;

    private Boolean sustentar;

    private Boolean proyectoEnviado;

    private Boolean retroalimentacionAsesorEnviada;

    private String estado;

    private Long asesorId;

    private Boolean tieneJurado;

    private Boolean tieneAsesor;

    private List<IntegranteProyectoDTO> listaIntegrantesProyecto;

    private Long proyectoLineaInvestigacionId;

    private String proyectoLineaInvestigacionLinea;

    private Long proyectoGrupoSemilleroId;

    private String proyectoGrupoSemilleroNombre;

    private Long proyectoModalidadId;

    private String proyectoModalidadModalidad;

    private Long facultadId;

    private Long subLineaLineaInvestigacionId;

    private String subLineaLineaInvestigacionLinea;

    private Long proyectoProgramaId;

    private String programa;

    private Long proyectoFaseId;

    private String fase;

    private Long proyectoCicloId;

    private String ciclo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLugarEjecucion() {
        return lugarEjecucion;
    }

    public void setLugarEjecucion(String lugarEjecucion) {
        this.lugarEjecucion = lugarEjecucion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getContrapartidaPesos() {
        return contrapartidaPesos;
    }

    public void setContrapartidaPesos(Double contrapartidaPesos) {
        this.contrapartidaPesos = contrapartidaPesos;
    }

    public Double getContrapartidaEspecie() {
        return contrapartidaEspecie;
    }

    public void setContrapartidaEspecie(Double contrapartidaEspecie) {
        this.contrapartidaEspecie = contrapartidaEspecie;
    }

    public String getPalabrasClave() {
        return palabrasClave;
    }

    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Boolean getViable() {
        return viable;
    }

    public void setViable(Boolean viable) {
        this.viable = viable;
    }

    public Boolean getEnviado() {
        return enviado;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public ZonedDateTime getFechaEnvioPropuesta() {
        return fechaEnvioPropuesta;
    }

    public void setFechaEnvioPropuesta(ZonedDateTime fechaEnvioPropuesta) {
        this.fechaEnvioPropuesta = fechaEnvioPropuesta;
    }

    public ZonedDateTime getFechaEnvioProyecto() {
        return fechaEnvioProyecto;
    }

    public void setFechaEnvioProyecto(ZonedDateTime fechaEnvioProyecto) {
        this.fechaEnvioProyecto = fechaEnvioProyecto;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getRecomendacionesAsesorPropuesta() {
        return recomendacionesAsesorPropuesta;
    }

    public void setRecomendacionesAsesorPropuesta(String recomendacionesAsesorPropuesta) {
        this.recomendacionesAsesorPropuesta = recomendacionesAsesorPropuesta;
    }

    public String getRecomendacionesAsesorProyecto() {
        return recomendacionesAsesorProyecto;
    }

    public void setRecomendacionesAsesorProyecto(String recomendacionesAsesorProyecto) {
        this.recomendacionesAsesorProyecto = recomendacionesAsesorProyecto;
    }

    public String getRecomendacionesJuradoProyecto() {
        return recomendacionesJuradoProyecto;
    }

    public void setRecomendacionesJuradoProyecto(String recomendacionesJuradoProyecto) {
        this.recomendacionesJuradoProyecto = recomendacionesJuradoProyecto;
    }

    public String getRecomendacionesJuradoSustentacion() {
        return recomendacionesJuradoSustentacion;
    }

    public void setRecomendacionesJuradoSustentacion(String recomendacionesJuradoSustentacion) {
        this.recomendacionesJuradoSustentacion = recomendacionesJuradoSustentacion;
    }

    public String getViabilidad() {
        return viabilidad;
    }

    public void setViabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
    }

    public Boolean getPreEnviado() {
        return preEnviado;
    }

    public void setPreEnviado(Boolean preEnviado) {
        this.preEnviado = preEnviado;
    }

    public Boolean getSustentar() {
        return sustentar;
    }

    public void setSustentar(Boolean sustentar) {
        this.sustentar = sustentar;
    }

    public Boolean getProyectoEnviado() {
        return proyectoEnviado;
    }

    public void setProyectoEnviado(Boolean proyectoEnviado) {
        this.proyectoEnviado = proyectoEnviado;
    }

    public Boolean getRetroalimentacionAsesorEnviada() {
        return retroalimentacionAsesorEnviada;
    }

    public void setRetroalimentacionAsesorEnviada(Boolean retroalimentacionAsesorEnviada) {
        this.retroalimentacionAsesorEnviada = retroalimentacionAsesorEnviada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getAsesorId() {
        return asesorId;
    }

    public void setAsesorId(Long asesorId) {
        this.asesorId = asesorId;
    }

    public Boolean getTieneJurado() {
        return tieneJurado;
    }

    public void setTieneJurado(Boolean tieneJurado) {
        this.tieneJurado = tieneJurado;
    }

    public Boolean getTieneAsesor() {
        return tieneAsesor;
    }

    public void setTieneAsesor(Boolean tieneAsesor) {
        this.tieneAsesor = tieneAsesor;
    }

    public List<IntegranteProyectoDTO> getListaIntegrantesProyecto() {
        return listaIntegrantesProyecto;
    }

    public void setListaIntegrantesProyecto(List<IntegranteProyectoDTO> listaIntegrantesProyecto) {
        this.listaIntegrantesProyecto = listaIntegrantesProyecto;
    }

    public Long getProyectoLineaInvestigacionId() {
        return proyectoLineaInvestigacionId;
    }

    public void setProyectoLineaInvestigacionId(Long proyectoLineaInvestigacionId) {
        this.proyectoLineaInvestigacionId = proyectoLineaInvestigacionId;
    }

    public String getProyectoLineaInvestigacionLinea() {
        return proyectoLineaInvestigacionLinea;
    }

    public void setProyectoLineaInvestigacionLinea(String proyectoLineaInvestigacionLinea) {
        this.proyectoLineaInvestigacionLinea = proyectoLineaInvestigacionLinea;
    }

    public Long getProyectoGrupoSemilleroId() {
        return proyectoGrupoSemilleroId;
    }

    public void setProyectoGrupoSemilleroId(Long proyectoGrupoSemilleroId) {
        this.proyectoGrupoSemilleroId = proyectoGrupoSemilleroId;
    }

    public String getProyectoGrupoSemilleroNombre() {
        return proyectoGrupoSemilleroNombre;
    }

    public void setProyectoGrupoSemilleroNombre(String proyectoGrupoSemilleroNombre) {
        this.proyectoGrupoSemilleroNombre = proyectoGrupoSemilleroNombre;
    }

    public Long getProyectoModalidadId() {
        return proyectoModalidadId;
    }

    public void setProyectoModalidadId(Long proyectoModalidadId) {
        this.proyectoModalidadId = proyectoModalidadId;
    }

    public String getProyectoModalidadModalidad() {
        return proyectoModalidadModalidad;
    }

    public void setProyectoModalidadModalidad(String proyectoModalidadModalidad) {
        this.proyectoModalidadModalidad = proyectoModalidadModalidad;
    }

    public Long getFacultadId() {
        return facultadId;
    }

    public void setFacultadId(Long facultadId) {
        this.facultadId = facultadId;
    }

    public Long getSubLineaLineaInvestigacionId() {
        return subLineaLineaInvestigacionId;
    }

    public void setSubLineaLineaInvestigacionId(Long subLineaLineaInvestigacionId) {
        this.subLineaLineaInvestigacionId = subLineaLineaInvestigacionId;
    }

    public String getSubLineaLineaInvestigacionLinea() {
        return subLineaLineaInvestigacionLinea;
    }

    public void setSubLineaLineaInvestigacionLinea(String subLineaLineaInvestigacionLinea) {
        this.subLineaLineaInvestigacionLinea = subLineaLineaInvestigacionLinea;
    }

    public Long getProyectoProgramaId() {
        return proyectoProgramaId;
    }

    public void setProyectoProgramaId(Long proyectoProgramaId) {
        this.proyectoProgramaId = proyectoProgramaId;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Long getProyectoFaseId() {
        return proyectoFaseId;
    }

    public void setProyectoFaseId(Long proyectoFaseId) {
        this.proyectoFaseId = proyectoFaseId;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Long getProyectoCicloId() {
        return proyectoCicloId;
    }

    public void setProyectoCicloId(Long proyectoCicloId) {
        this.proyectoCicloId = proyectoCicloId;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProyectoDTO)) {
            return false;
        }

        return id != null && id.equals(((ProyectoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProyectoDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
