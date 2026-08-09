package co.edu.itp.ciecyt.domain;

import co.edu.itp.ciecyt.domain.enumeration.EnumEstadoProyecto;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "url")
    private String url;

    @Column(name = "lugar_ejecucion")
    private String lugarEjecucion;

    @Column(name = "duracion")
    private String duracion;

    @Column(name = "fecha_ini")
    private LocalDate fechaIni;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "contrapartida_pesos")
    private Double contrapartidaPesos;

    @Column(name = "contrapartida_especie")
    private Double contrapartidaEspecie;

    @Column(name = "palabras_clave", columnDefinition = "text")
    private String palabrasClave;

    @Column(name = "convocatoria")
    private String convocatoria;

    @Column(name = "tipo", columnDefinition = "text")
    private String tipo;

    @Column(name = "referencias", columnDefinition = "text")
    private String referencias;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "viable")
    private Boolean viable;

    @Column(name = "enviado")
    private Boolean enviado;

    @Column(name = "fecha_envio_propuesta")
    private ZonedDateTime fechaEnvioPropuesta;

    @Column(name = "fecha_envio_proyecto")
    private ZonedDateTime fechaEnvioProyecto;

    @Column(name = "nota")
    private Double nota;

    @Column(name = "conclusion", columnDefinition = "text")
    private String conclusion;

    @Column(name = "recomendaciones", columnDefinition = "text")
    private String recomendaciones;

    @Column(name = "viabilidad", columnDefinition = "text")
    private String viabilidad;

    @Column(name = "fase")
    private String fase;

    @Column(name = "pre_enviado")
    private Boolean preEnviado;

    @Column(name = "fecha_pre_envio_propuesta")
    private LocalDate fechaPreEnvioPropuesta;

    @Column(name = "fecha_viabilidad_propuesta")
    private LocalDate fechaViabilidadPropuesta;

    @Column(name = "fecha_sustentacion_proyecto")
    private LocalDate fechaSustentacionProyecto;

    @Column(name = "sustentar")
    private Boolean sustentar;

    @Column(name = "proyecto_enviado")
    private Boolean proyectoEnviado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EnumEstadoProyecto estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_linea_investigacion_id")
    private LineaInvestigacion proyectoLineaInvestigacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_grupo_semillero_id")
    private GrupoSemillero proyectoGrupoSemillero;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proyecto_modalidad_id")
    private Modalidad proyectoModalidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_linea_linea_investigacion_id")
    private LineaInvestigacion subLineaLineaInvestigacion;

    @Column(name = "programa", columnDefinition = "text")
    private String programa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_programa_id")
    private Programa proyectoPrograma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_fase_id")
    private Fases proyectoFase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proyecto_ciclo_id")
    private Ciclo proyectoCiclo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Proyecto titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUrl() {
        return url;
    }

    public Proyecto url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLugarEjecucion() {
        return lugarEjecucion;
    }

    public Proyecto lugarEjecucion(String lugarEjecucion) {
        this.lugarEjecucion = lugarEjecucion;
        return this;
    }

    public void setLugarEjecucion(String lugarEjecucion) {
        this.lugarEjecucion = lugarEjecucion;
    }

    public String getDuracion() {
        return duracion;
    }

    public Proyecto duracion(String duracion) {
        this.duracion = duracion;
        return this;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public LocalDate getFechaIni() {
        return fechaIni;
    }

    public Proyecto fechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
        return this;
    }

    public void setFechaIni(LocalDate fechaIni) {
        this.fechaIni = fechaIni;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Proyecto fechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getContrapartidaPesos() {
        return contrapartidaPesos;
    }

    public Proyecto contrapartidaPesos(Double contrapartidaPesos) {
        this.contrapartidaPesos = contrapartidaPesos;
        return this;
    }

    public void setContrapartidaPesos(Double contrapartidaPesos) {
        this.contrapartidaPesos = contrapartidaPesos;
    }

    public Double getContrapartidaEspecie() {
        return contrapartidaEspecie;
    }

    public Proyecto contrapartidaEspecie(Double contrapartidaEspecie) {
        this.contrapartidaEspecie = contrapartidaEspecie;
        return this;
    }

    public void setContrapartidaEspecie(Double contrapartidaEspecie) {
        this.contrapartidaEspecie = contrapartidaEspecie;
    }

    public String getPalabrasClave() {
        return palabrasClave;
    }

    public Proyecto palabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
        return this;
    }

    public void setPalabrasClave(String palabrasClave) {
        this.palabrasClave = palabrasClave;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public Proyecto convocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
        return this;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public String getTipo() {
        return tipo;
    }

    public Proyecto tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencias() {
        return referencias;
    }

    public Proyecto referencias(String referencias) {
        this.referencias = referencias;
        return this;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public String getDepartamento() {
        return departamento;
    }

    public Proyecto departamento(String departamento) {
        this.departamento = departamento;
        return this;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Proyecto municipio(String municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public Boolean isViable() {
        return viable;
    }

    public Proyecto viable(Boolean viable) {
        this.viable = viable;
        return this;
    }

    public void setViable(Boolean viable) {
        this.viable = viable;
    }

    public Boolean isEnviado() {
        return enviado;
    }

    public Proyecto enviado(Boolean enviado) {
        this.enviado = enviado;
        return this;
    }

    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    public ZonedDateTime getFechaEnvioPropuesta() {
        return fechaEnvioPropuesta;
    }

    public Proyecto fechaEnvioPropuesta(ZonedDateTime fechaEnvioPropuesta) {
        this.fechaEnvioPropuesta = fechaEnvioPropuesta;
        return this;
    }

    public void setFechaEnvioPropuesta(ZonedDateTime fechaEnvioPropuesta) {
        this.fechaEnvioPropuesta = fechaEnvioPropuesta;
    }

    public ZonedDateTime getFechaEnvioProyecto() {
        return fechaEnvioProyecto;
    }

    public Proyecto fechaEnvioProyecto(ZonedDateTime fechaEnvioProyecto) {
        this.fechaEnvioProyecto = fechaEnvioProyecto;
        return this;
    }

    public void setFechaEnvioProyecto(ZonedDateTime fechaEnvioProyecto) {
        this.fechaEnvioProyecto = fechaEnvioProyecto;
    }

    public Double getNota() {
        return nota;
    }

    public Proyecto nota(Double nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public String getConclusion() {
        return conclusion;
    }

    public Proyecto conclusion(String conclusion) {
        this.conclusion = conclusion;
        return this;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public Proyecto recomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
        return this;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getViabilidad() {
        return viabilidad;
    }

    public Proyecto viabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
        return this;
    }

    public void setViabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
    }

    public String getFase() {
        return fase;
    }

    public Proyecto fase(String fase) {
        this.fase = fase;
        return this;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public Boolean isPreEnviado() {
        return preEnviado;
    }

    public Proyecto preEnviado(Boolean preEnviado) {
        this.preEnviado = preEnviado;
        return this;
    }

    public void setPreEnviado(Boolean preEnviado) {
        this.preEnviado = preEnviado;
    }

    public LocalDate getFechaPreEnvioPropuesta() {
        return fechaPreEnvioPropuesta;
    }

    public Proyecto fechaPreEnvioPropuesta(LocalDate fechaPreEnvioPropuesta) {
        this.fechaPreEnvioPropuesta = fechaPreEnvioPropuesta;
        return this;
    }

    public void setFechaPreEnvioPropuesta(LocalDate fechaPreEnvioPropuesta) {
        this.fechaPreEnvioPropuesta = fechaPreEnvioPropuesta;
    }

    public LocalDate getFechaViabilidadPropuesta() {
        return fechaViabilidadPropuesta;
    }

    public Proyecto fechaViabilidadPropuesta(LocalDate fechaViabilidadPropuesta) {
        this.fechaViabilidadPropuesta = fechaViabilidadPropuesta;
        return this;
    }

    public void setFechaViabilidadPropuesta(LocalDate fechaViabilidadPropuesta) {
        this.fechaViabilidadPropuesta = fechaViabilidadPropuesta;
    }

    public LocalDate getFechaSustentacionProyecto() {
        return fechaSustentacionProyecto;
    }

    public Proyecto fechaSustentacionProyecto(LocalDate fechaSustentacionProyecto) {
        this.fechaSustentacionProyecto = fechaSustentacionProyecto;
        return this;
    }

    public void setFechaSustentacionProyecto(LocalDate fechaSustentacionProyecto) {
        this.fechaSustentacionProyecto = fechaSustentacionProyecto;
    }

    public Boolean isSustentar() {
        return sustentar;
    }

    public Proyecto sustentar(Boolean sustentar) {
        this.sustentar = sustentar;
        return this;
    }

    public void setSustentar(Boolean sustentar) {
        this.sustentar = sustentar;
    }

    public Boolean isProyectoEnviado() {
        return proyectoEnviado;
    }

    public Proyecto proyectoEnviado(Boolean proyectoEnviado) {
        this.proyectoEnviado = proyectoEnviado;
        return this;
    }

    public void setProyectoEnviado(Boolean proyectoEnviado) {
        this.proyectoEnviado = proyectoEnviado;
    }

    public EnumEstadoProyecto getEstado() {
        return estado;
    }

    public Proyecto estado(EnumEstadoProyecto estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EnumEstadoProyecto estado) {
        this.estado = estado;
    }

    public LineaInvestigacion getProyectoLineaInvestigacion() {
        return proyectoLineaInvestigacion;
    }

    public Proyecto proyectoLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.proyectoLineaInvestigacion = lineaInvestigacion;
        return this;
    }

    public void setProyectoLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.proyectoLineaInvestigacion = lineaInvestigacion;
    }

    public GrupoSemillero getProyectoGrupoSemillero() {
        return proyectoGrupoSemillero;
    }

    public Proyecto proyectoGrupoSemillero(GrupoSemillero grupoSemillero) {
        this.proyectoGrupoSemillero = grupoSemillero;
        return this;
    }

    public void setProyectoGrupoSemillero(GrupoSemillero grupoSemillero) {
        this.proyectoGrupoSemillero = grupoSemillero;
    }

    public Modalidad getProyectoModalidad() {
        return proyectoModalidad;
    }

    public Proyecto proyectoModalidad(Modalidad modalidad) {
        this.proyectoModalidad = modalidad;
        return this;
    }

    public void setProyectoModalidad(Modalidad modalidad) {
        this.proyectoModalidad = modalidad;
    }

    public Facultad getFacultad() {
        return facultad;
    }

    public Proyecto facultad(Facultad facultad) {
        this.facultad = facultad;
        return this;
    }

    public void setFacultad(Facultad facultad) {
        this.facultad = facultad;
    }

    public LineaInvestigacion getSubLineaLineaInvestigacion() {
        return subLineaLineaInvestigacion;
    }

    public Proyecto subLineaLineaInvestigacion(LineaInvestigacion subLineaLineaInvestigacion) {
        this.subLineaLineaInvestigacion = subLineaLineaInvestigacion;
        return this;
    }

    public void setSubLineaLineaInvestigacion(LineaInvestigacion subLineaLineaInvestigacion) {
        this.subLineaLineaInvestigacion = subLineaLineaInvestigacion;
    }

    public Programa getProyectoPrograma() {
        return proyectoPrograma;
    }

    public Proyecto proyectoPrograma(Programa programa) {
        this.proyectoPrograma = programa;
        return this;
    }

    public void setProyectoPrograma(Programa programa) {
        this.proyectoPrograma = programa;
    }

    public String getPrograma() {
        return programa;
    }

    public Proyecto programa(String programa) {
        this.programa = programa;
        return this;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public Fases getProyectoFase() {
        return proyectoFase;
    }

    public Proyecto proyectoFase(Fases fase) {
        this.proyectoFase = fase;
        return this;
    }

    public void setProyectoFase(Fases fase) {
        this.proyectoFase = fase;
    }

    public Ciclo getProyectoCiclo() {
        return proyectoCiclo;
    }

    public Proyecto proyectoCiclo(Ciclo ciclo) {
        this.proyectoCiclo = ciclo;
        return this;
    }

    public void setProyectoCiclo(Ciclo ciclo) {
        this.proyectoCiclo = ciclo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Proyecto)) {
            return false;
        }
        return id != null && id.equals(((Proyecto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
