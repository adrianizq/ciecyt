package co.edu.itp.ciecyt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A InformacionPasantia.
 */
@Entity
@Table(name = "informacion_pasantia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InformacionPasantia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "duracion_horas")
    private Integer duracionHoras;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "email")
    private String email;

    @Column(name = "lunes")
    private Boolean lunes;

    @Column(name = "martes")
    private Boolean martes;

    @Column(name = "miercoles")
    private Boolean miercoles;

    @Column(name = "jueves")
    private Boolean jueves;

    @Column(name = "viernes")
    private Boolean viernes;

    @Column(name = "sabado")
    private Boolean sabado;

    @Column(name = "domingo")
    private Boolean domingo;

    @Column(name = "convenio")
    private String convenio;

    @Column(name = "bono_alimenticio")
    private Boolean bonoAlimenticio;

    @Column(name = "apoyo_economico")
    private Boolean apoyoEconomico;

    @Column(name = "auxilio_transporte")
    private Boolean auxilioTransporte;

    @Column(name = "capacitacion")
    private Boolean capacitacion;

    @Column(name = "otro_apoyo")
    private String otroApoyo;

    @Column(name = "nombre_empresa")
    private String nombreEmpresa;

    @Column(name = "nit_empresa")
    private String nitEmpresa;

    @Column(name = "direccion_empresa")
    private String direccionEmpresa;

    @Column(name = "sector_economico_empresa")
    private String sectorEconomicoEmpresa;

    @Column(name = "representante_legal_empresa")
    private String representanteLegalEmpresa;

    @Column(name = "asesor_empresa")
    private String asesorEmpresa;

    @Column(name = "cargo_asesor_empresa")
    private String cargoAsesorEmpresa;

    @Column(name = "email_asesor_empresa")
    private String emailAsesorEmpresa;

    @Column(name = "municipio_empresa")
    private String municipioEmpresa;

    @Column(name = "telefono_contacto_empresa")
    private String telefonoContactoEmpresa;

    @Column(name = "email_empresa")
    private String emailEmpresa;

    @Column(name = "departamento_empresa")
    private String departamentoEmpresa;

    @Column(name = "identificacion_representante_legal")
    private String identificacionRepresentanteLegal;

    @Column(name = "profesion_asesor_empresa")
    private String profesionAsesorEmpresa;

    @Column(name = "celular_asesor_empresa")
    private String celularAsesorEmpresa;

    @OneToOne
    @JsonIgnoreProperties("informacionPasantiaProyectos")
    private Proyecto informacionPasantiaProyecto;

   // @OneToOne
   // @JsonIgnoreProperties("informacionPasantiaProyectos")
    //@JoinColumn(unique = true)
   // private Proyecto informacionPasantiaProyecto;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public InformacionPasantia duracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
        return this;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public String getDireccion() {
        return direccion;
    }

    public InformacionPasantia direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public InformacionPasantia email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isLunes() {
        return lunes;
    }

    public InformacionPasantia lunes(Boolean lunes) {
        this.lunes = lunes;
        return this;
    }

    public void setLunes(Boolean lunes) {
        this.lunes = lunes;
    }

    public Boolean isMartes() {
        return martes;
    }

    public InformacionPasantia martes(Boolean martes) {
        this.martes = martes;
        return this;
    }

    public void setMartes(Boolean martes) {
        this.martes = martes;
    }

    public Boolean isMiercoles() {
        return miercoles;
    }

    public InformacionPasantia miercoles(Boolean miercoles) {
        this.miercoles = miercoles;
        return this;
    }

    public void setMiercoles(Boolean miercoles) {
        this.miercoles = miercoles;
    }

    public Boolean isJueves() {
        return jueves;
    }

    public InformacionPasantia jueves(Boolean jueves) {
        this.jueves = jueves;
        return this;
    }

    public void setJueves(Boolean jueves) {
        this.jueves = jueves;
    }

    public Boolean isViernes() {
        return viernes;
    }

    public InformacionPasantia viernes(Boolean viernes) {
        this.viernes = viernes;
        return this;
    }

    public void setViernes(Boolean viernes) {
        this.viernes = viernes;
    }

    public Boolean isSabado() {
        return sabado;
    }

    public InformacionPasantia sabado(Boolean sabado) {
        this.sabado = sabado;
        return this;
    }

    public void setSabado(Boolean sabado) {
        this.sabado = sabado;
    }

    public Boolean isDomingo() {
        return domingo;
    }

    public InformacionPasantia domingo(Boolean domingo) {
        this.domingo = domingo;
        return this;
    }

    public void setDomingo(Boolean domingo) {
        this.domingo = domingo;
    }

    public String getConvenio() {
        return convenio;
    }

    public InformacionPasantia convenio(String convenio) {
        this.convenio = convenio;
        return this;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public Boolean isBonoAlimenticio() {
        return bonoAlimenticio;
    }

    public InformacionPasantia bonoAlimenticio(Boolean bonoAlimenticio) {
        this.bonoAlimenticio = bonoAlimenticio;
        return this;
    }

    public void setBonoAlimenticio(Boolean bonoAlimenticio) {
        this.bonoAlimenticio = bonoAlimenticio;
    }

    public Boolean isApoyoEconomico() {
        return apoyoEconomico;
    }

    public InformacionPasantia apoyoEconomico(Boolean apoyoEconomico) {
        this.apoyoEconomico = apoyoEconomico;
        return this;
    }

    public void setApoyoEconomico(Boolean apoyoEconomico) {
        this.apoyoEconomico = apoyoEconomico;
    }

    public Boolean isAuxilioTransporte() {
        return auxilioTransporte;
    }

    public InformacionPasantia auxilioTransporte(Boolean auxilioTransporte) {
        this.auxilioTransporte = auxilioTransporte;
        return this;
    }

    public void setAuxilioTransporte(Boolean auxilioTransporte) {
        this.auxilioTransporte = auxilioTransporte;
    }

    public Boolean isCapacitacion() {
        return capacitacion;
    }

    public InformacionPasantia capacitacion(Boolean capacitacion) {
        this.capacitacion = capacitacion;
        return this;
    }

    public void setCapacitacion(Boolean capacitacion) {
        this.capacitacion = capacitacion;
    }

    public String getOtroApoyo() {
        return otroApoyo;
    }

    public InformacionPasantia otroApoyo(String otroApoyo) {
        this.otroApoyo = otroApoyo;
        return this;
    }

    public void setOtroApoyo(String otroApoyo) {
        this.otroApoyo = otroApoyo;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public InformacionPasantia nombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
        return this;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public InformacionPasantia nitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
        return this;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public InformacionPasantia direccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
        return this;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getSectorEconomicoEmpresa() {
        return sectorEconomicoEmpresa;
    }

    public InformacionPasantia sectorEconomicoEmpresa(String sectorEconomicoEmpresa) {
        this.sectorEconomicoEmpresa = sectorEconomicoEmpresa;
        return this;
    }

    public void setSectorEconomicoEmpresa(String sectorEconomicoEmpresa) {
        this.sectorEconomicoEmpresa = sectorEconomicoEmpresa;
    }

    public String getRepresentanteLegalEmpresa() {
        return representanteLegalEmpresa;
    }

    public InformacionPasantia representanteLegalEmpresa(String representanteLegalEmpresa) {
        this.representanteLegalEmpresa = representanteLegalEmpresa;
        return this;
    }

    public void setRepresentanteLegalEmpresa(String representanteLegalEmpresa) {
        this.representanteLegalEmpresa = representanteLegalEmpresa;
    }

    public String getAsesorEmpresa() {
        return asesorEmpresa;
    }

    public InformacionPasantia asesorEmpresa(String asesorEmpresa) {
        this.asesorEmpresa = asesorEmpresa;
        return this;
    }

    public void setAsesorEmpresa(String asesorEmpresa) {
        this.asesorEmpresa = asesorEmpresa;
    }

    public String getCargoAsesorEmpresa() {
        return cargoAsesorEmpresa;
    }

    public InformacionPasantia cargoAsesorEmpresa(String cargoAsesorEmpresa) {
        this.cargoAsesorEmpresa = cargoAsesorEmpresa;
        return this;
    }

    public void setCargoAsesorEmpresa(String cargoAsesorEmpresa) {
        this.cargoAsesorEmpresa = cargoAsesorEmpresa;
    }

    public String getEmailAsesorEmpresa() {
        return emailAsesorEmpresa;
    }

    public InformacionPasantia emailAsesorEmpresa(String emailAsesorEmpresa) {
        this.emailAsesorEmpresa = emailAsesorEmpresa;
        return this;
    }

    public void setEmailAsesorEmpresa(String emailAsesorEmpresa) {
        this.emailAsesorEmpresa = emailAsesorEmpresa;
    }

    public String getMunicipioEmpresa() {
        return municipioEmpresa;
    }

    public InformacionPasantia municipioEmpresa(String municipioEmpresa) {
        this.municipioEmpresa = municipioEmpresa;
        return this;
    }

    public void setMunicipioEmpresa(String municipioEmpresa) {
        this.municipioEmpresa = municipioEmpresa;
    }

    public String getTelefonoContactoEmpresa() {
        return telefonoContactoEmpresa;
    }

    public InformacionPasantia telefonoContactoEmpresa(String telefonoContactoEmpresa) {
        this.telefonoContactoEmpresa = telefonoContactoEmpresa;
        return this;
    }

    public void setTelefonoContactoEmpresa(String telefonoContactoEmpresa) {
        this.telefonoContactoEmpresa = telefonoContactoEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public InformacionPasantia emailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
        return this;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getDepartamentoEmpresa() {
        return departamentoEmpresa;
    }

    public InformacionPasantia departamentoEmpresa(String departamentoEmpresa) {
        this.departamentoEmpresa = departamentoEmpresa;
        return this;
    }

    public void setDepartamentoEmpresa(String departamentoEmpresa) {
        this.departamentoEmpresa = departamentoEmpresa;
    }

    public String getIdentificacionRepresentanteLegal() {
        return identificacionRepresentanteLegal;
    }

    public InformacionPasantia identificacionRepresentanteLegal(String identificacionRepresentanteLegal) {
        this.identificacionRepresentanteLegal = identificacionRepresentanteLegal;
        return this;
    }

    public void setIdentificacionRepresentanteLegal(String identificacionRepresentanteLegal) {
        this.identificacionRepresentanteLegal = identificacionRepresentanteLegal;
    }

    public String getProfesionAsesorEmpresa() {
        return profesionAsesorEmpresa;
    }

    public InformacionPasantia profesionAsesorEmpresa(String profesionAsesorEmpresa) {
        this.profesionAsesorEmpresa = profesionAsesorEmpresa;
        return this;
    }

    public void setProfesionAsesorEmpresa(String profesionAsesorEmpresa) {
        this.profesionAsesorEmpresa = profesionAsesorEmpresa;
    }

    public String getCelularAsesorEmpresa() {
        return celularAsesorEmpresa;
    }

    public InformacionPasantia celularAsesorEmpresa(String celularAsesorEmpresa) {
        this.celularAsesorEmpresa = celularAsesorEmpresa;
        return this;
    }

    public void setCelularAsesorEmpresa(String celularAsesorEmpresa) {
        this.celularAsesorEmpresa = celularAsesorEmpresa;
    }



    public Proyecto getInformacionPasantiaProyecto() {

        return informacionPasantiaProyecto;
    }

    public InformacionPasantia informacionPasantiaProyecto(Proyecto proyecto) {
        this.informacionPasantiaProyecto = proyecto;
        return this;
    }

    public void setInformacionPasantiaProyecto(Proyecto proyecto) {

        this.informacionPasantiaProyecto = proyecto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InformacionPasantia)) {
            return false;
        }
        return id != null && id.equals(((InformacionPasantia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InformacionPasantia{" +
            "id=" + getId() +
            ", duracionHoras=" + getDuracionHoras() +
            ", direccion='" + getDireccion() + "'" +
            ", email='" + getEmail() + "'" +
            ", lunes='" + isLunes() + "'" +
            ", martes='" + isMartes() + "'" +
            ", miercoles='" + isMiercoles() + "'" +
            ", jueves='" + isJueves() + "'" +
            ", viernes='" + isViernes() + "'" +
            ", sabado='" + isSabado() + "'" +
            ", domingo='" + isDomingo() + "'" +
            ", convenio=" + getConvenio() +
            ", bonoAlimenticio='" + isBonoAlimenticio() + "'" +
            ", apoyoEconomico='" + isApoyoEconomico() + "'" +
            ", auxilioTransporte='" + isAuxilioTransporte() + "'" +
            ", capacitacion='" + isCapacitacion() + "'" +
            ", otroApoyo='" + getOtroApoyo() + "'" +
            ", nombreEmpresa='" + getNombreEmpresa() + "'" +
            ", nitEmpresa='" + getNitEmpresa() + "'" +
            ", direccionEmpresa='" + getDireccionEmpresa() + "'" +
            ", sectorEconomicoEmpresa='" + getSectorEconomicoEmpresa() + "'" +
            ", representanteLegalEmpresa='" + getRepresentanteLegalEmpresa() + "'" +
            ", asesorEmpresa='" + getAsesorEmpresa() + "'" +
            ", cargoAsesorEmpresa='" + getCargoAsesorEmpresa() + "'" +
            ", emailAsesorEmpresa='" + getEmailAsesorEmpresa() + "'" +
            ", municipioEmpresa='" + getMunicipioEmpresa() + "'" +
            ", telefonoContactoEmpresa='" + getTelefonoContactoEmpresa() + "'" +
            ", emailEmpresa='" + getEmailEmpresa() + "'" +
            ", departamentoEmpresa='" + getDepartamentoEmpresa() + "'" +
            ", identificacionRepresentanteLegal='" + getIdentificacionRepresentanteLegal() + "'" +
            ", profesionAsesorEmpresa='" + getProfesionAsesorEmpresa() + "'" +
            ", celularAsesorEmpresa='" + getCelularAsesorEmpresa() + "'" +
            "}";
    }
}
