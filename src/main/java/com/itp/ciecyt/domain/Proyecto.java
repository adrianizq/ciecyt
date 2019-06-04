package com.itp.ciecyt.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Proyecto.
 */
@Entity
@Table(name = "proyecto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @Column(name = "palabra_clave")
    private String palabraClave;

    @Column(name = "convocatoria")
    private String convocatoria;

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EntidadFinanciadora> entidadFinanciadoras = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ElementosProyecto> elementosProyectos = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProductoProyecto> productoProyectos = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PresupuestoValor> presupuestoValors = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ImpactosEsperados> impactosEsperados = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cronograma> cronogramas = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ResultadosEsperados> resultadosEsperados = new HashSet<>();

    @OneToMany(mappedBy = "proyecto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<IntegrantesProyecto> integrantesProyectos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("proyectos")
    private LineaDeInvestigacion lineaDeInvestigacion;

    @ManyToOne
    @JsonIgnoreProperties("proyectos")
    private GrupoSemillero grupoSemillero;

    @ManyToOne
    @JsonIgnoreProperties("proyectos")
    private Facultad facultad;

    @ManyToOne
    @JsonIgnoreProperties("proyectos")
    private ModalidadDeGrado modalidadDeGrado;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public String getPalabraClave() {
        return palabraClave;
    }

    public Proyecto palabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
        return this;
    }

    public void setPalabraClave(String palabraClave) {
        this.palabraClave = palabraClave;
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

    public Set<EntidadFinanciadora> getEntidadFinanciadoras() {
        return entidadFinanciadoras;
    }

    public Proyecto entidadFinanciadoras(Set<EntidadFinanciadora> entidadFinanciadoras) {
        this.entidadFinanciadoras = entidadFinanciadoras;
        return this;
    }

    public Proyecto addEntidadFinanciadora(EntidadFinanciadora entidadFinanciadora) {
        this.entidadFinanciadoras.add(entidadFinanciadora);
        entidadFinanciadora.setProyecto(this);
        return this;
    }

    public Proyecto removeEntidadFinanciadora(EntidadFinanciadora entidadFinanciadora) {
        this.entidadFinanciadoras.remove(entidadFinanciadora);
        entidadFinanciadora.setProyecto(null);
        return this;
    }

    public void setEntidadFinanciadoras(Set<EntidadFinanciadora> entidadFinanciadoras) {
        this.entidadFinanciadoras = entidadFinanciadoras;
    }

    public Set<ElementosProyecto> getElementosProyectos() {
        return elementosProyectos;
    }

    public Proyecto elementosProyectos(Set<ElementosProyecto> elementosProyectos) {
        this.elementosProyectos = elementosProyectos;
        return this;
    }

    public Proyecto addElementosProyecto(ElementosProyecto elementosProyecto) {
        this.elementosProyectos.add(elementosProyecto);
        elementosProyecto.setProyecto(this);
        return this;
    }

    public Proyecto removeElementosProyecto(ElementosProyecto elementosProyecto) {
        this.elementosProyectos.remove(elementosProyecto);
        elementosProyecto.setProyecto(null);
        return this;
    }

    public void setElementosProyectos(Set<ElementosProyecto> elementosProyectos) {
        this.elementosProyectos = elementosProyectos;
    }

    public Set<ProductoProyecto> getProductoProyectos() {
        return productoProyectos;
    }

    public Proyecto productoProyectos(Set<ProductoProyecto> productoProyectos) {
        this.productoProyectos = productoProyectos;
        return this;
    }

    public Proyecto addProductoProyecto(ProductoProyecto productoProyecto) {
        this.productoProyectos.add(productoProyecto);
        productoProyecto.setProyecto(this);
        return this;
    }

    public Proyecto removeProductoProyecto(ProductoProyecto productoProyecto) {
        this.productoProyectos.remove(productoProyecto);
        productoProyecto.setProyecto(null);
        return this;
    }

    public void setProductoProyectos(Set<ProductoProyecto> productoProyectos) {
        this.productoProyectos = productoProyectos;
    }

    public Set<PresupuestoValor> getPresupuestoValors() {
        return presupuestoValors;
    }

    public Proyecto presupuestoValors(Set<PresupuestoValor> presupuestoValors) {
        this.presupuestoValors = presupuestoValors;
        return this;
    }

    public Proyecto addPresupuestoValor(PresupuestoValor presupuestoValor) {
        this.presupuestoValors.add(presupuestoValor);
        presupuestoValor.setProyecto(this);
        return this;
    }

    public Proyecto removePresupuestoValor(PresupuestoValor presupuestoValor) {
        this.presupuestoValors.remove(presupuestoValor);
        presupuestoValor.setProyecto(null);
        return this;
    }

    public void setPresupuestoValors(Set<PresupuestoValor> presupuestoValors) {
        this.presupuestoValors = presupuestoValors;
    }

    public Set<ImpactosEsperados> getImpactosEsperados() {
        return impactosEsperados;
    }

    public Proyecto impactosEsperados(Set<ImpactosEsperados> impactosEsperados) {
        this.impactosEsperados = impactosEsperados;
        return this;
    }

    public Proyecto addImpactosEsperados(ImpactosEsperados impactosEsperados) {
        this.impactosEsperados.add(impactosEsperados);
        impactosEsperados.setProyecto(this);
        return this;
    }

    public Proyecto removeImpactosEsperados(ImpactosEsperados impactosEsperados) {
        this.impactosEsperados.remove(impactosEsperados);
        impactosEsperados.setProyecto(null);
        return this;
    }

    public void setImpactosEsperados(Set<ImpactosEsperados> impactosEsperados) {
        this.impactosEsperados = impactosEsperados;
    }

    public Set<Cronograma> getCronogramas() {
        return cronogramas;
    }

    public Proyecto cronogramas(Set<Cronograma> cronogramas) {
        this.cronogramas = cronogramas;
        return this;
    }

    public Proyecto addCronograma(Cronograma cronograma) {
        this.cronogramas.add(cronograma);
        cronograma.setProyecto(this);
        return this;
    }

    public Proyecto removeCronograma(Cronograma cronograma) {
        this.cronogramas.remove(cronograma);
        cronograma.setProyecto(null);
        return this;
    }

    public void setCronogramas(Set<Cronograma> cronogramas) {
        this.cronogramas = cronogramas;
    }

    public Set<ResultadosEsperados> getResultadosEsperados() {
        return resultadosEsperados;
    }

    public Proyecto resultadosEsperados(Set<ResultadosEsperados> resultadosEsperados) {
        this.resultadosEsperados = resultadosEsperados;
        return this;
    }

    public Proyecto addResultadosEsperados(ResultadosEsperados resultadosEsperados) {
        this.resultadosEsperados.add(resultadosEsperados);
        resultadosEsperados.setProyecto(this);
        return this;
    }

    public Proyecto removeResultadosEsperados(ResultadosEsperados resultadosEsperados) {
        this.resultadosEsperados.remove(resultadosEsperados);
        resultadosEsperados.setProyecto(null);
        return this;
    }

    public void setResultadosEsperados(Set<ResultadosEsperados> resultadosEsperados) {
        this.resultadosEsperados = resultadosEsperados;
    }

    public Set<IntegrantesProyecto> getIntegrantesProyectos() {
        return integrantesProyectos;
    }

    public Proyecto integrantesProyectos(Set<IntegrantesProyecto> integrantesProyectos) {
        this.integrantesProyectos = integrantesProyectos;
        return this;
    }

    public Proyecto addIntegrantesProyecto(IntegrantesProyecto integrantesProyecto) {
        this.integrantesProyectos.add(integrantesProyecto);
        integrantesProyecto.setProyecto(this);
        return this;
    }

    public Proyecto removeIntegrantesProyecto(IntegrantesProyecto integrantesProyecto) {
        this.integrantesProyectos.remove(integrantesProyecto);
        integrantesProyecto.setProyecto(null);
        return this;
    }

    public void setIntegrantesProyectos(Set<IntegrantesProyecto> integrantesProyectos) {
        this.integrantesProyectos = integrantesProyectos;
    }

    public LineaDeInvestigacion getLineaDeInvestigacion() {
        return lineaDeInvestigacion;
    }

    public Proyecto lineaDeInvestigacion(LineaDeInvestigacion lineaDeInvestigacion) {
        this.lineaDeInvestigacion = lineaDeInvestigacion;
        return this;
    }

    public void setLineaDeInvestigacion(LineaDeInvestigacion lineaDeInvestigacion) {
        this.lineaDeInvestigacion = lineaDeInvestigacion;
    }

    public GrupoSemillero getGrupoSemillero() {
        return grupoSemillero;
    }

    public Proyecto grupoSemillero(GrupoSemillero grupoSemillero) {
        this.grupoSemillero = grupoSemillero;
        return this;
    }

    public void setGrupoSemillero(GrupoSemillero grupoSemillero) {
        this.grupoSemillero = grupoSemillero;
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

    public ModalidadDeGrado getModalidadDeGrado() {
        return modalidadDeGrado;
    }

    public Proyecto modalidadDeGrado(ModalidadDeGrado modalidadDeGrado) {
        this.modalidadDeGrado = modalidadDeGrado;
        return this;
    }

    public void setModalidadDeGrado(ModalidadDeGrado modalidadDeGrado) {
        this.modalidadDeGrado = modalidadDeGrado;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    public String toString() {
        return "Proyecto{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", url='" + getUrl() + "'" +
            ", lugarEjecucion='" + getLugarEjecucion() + "'" +
            ", duracion='" + getDuracion() + "'" +
            ", fechaIni='" + getFechaIni() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", contrapartidaPesos=" + getContrapartidaPesos() +
            ", contrapartidaEspecie=" + getContrapartidaEspecie() +
            ", palabraClave='" + getPalabraClave() + "'" +
            ", convocatoria='" + getConvocatoria() + "'" +
            "}";
    }
}
