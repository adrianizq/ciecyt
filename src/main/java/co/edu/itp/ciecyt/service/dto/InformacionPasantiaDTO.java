package co.edu.itp.ciecyt.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link package co.edu.itp..domain.InformacionPasantia} entity.
 */
public class InformacionPasantiaDTO implements Serializable {

    private Long id;

    private Integer duracionHoras;

    private String direccion;

    private String email;

    private Boolean lunes;

    private Boolean martes;

    private Boolean miercoles;

    private Boolean jueves;

    private Boolean viernes;

    private Boolean sabado;

    private Boolean domingo;

    private String convenio;

    private Boolean bonoAlimenticio;

    private Boolean apoyoEconomico;

    private Boolean auxilioTransporte;

    private Boolean capacitacion;

    private String otroApoyo;

    private String nombreEmpresa;

    private String nitEmpresa;

    private String direccionEmpresa;

    private String sectorEconomicoEmpresa;

    private String representanteLegalEmpresa;

    private String asesorEmpresa;

    private String cargoAsesorEmpresa;

    private String emailAsesorEmpresa;

    private String municipioEmpresa;

    private String telefonoContactoEmpresa;

    private String emailEmpresa;

    private String departamentoEmpresa;

    private String identificacionRepresentanteLegal;

    private String profesionAsesorEmpresa;

    private String celularAsesorEmpresa;

    private Long informacionPasantiaProyectoId;

    private String informacionPasantiaProyectoTitulo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isLunes() {
        return lunes;
    }

    public void setLunes(Boolean lunes) {
        this.lunes = lunes;
    }

    public Boolean isMartes() {
        return martes;
    }

    public void setMartes(Boolean martes) {
        this.martes = martes;
    }

    public Boolean isMiercoles() {
        return miercoles;
    }

    public void setMiercoles(Boolean miercoles) {
        this.miercoles = miercoles;
    }

    public Boolean isJueves() {
        return jueves;
    }

    public void setJueves(Boolean jueves) {
        this.jueves = jueves;
    }

    public Boolean isViernes() {
        return viernes;
    }

    public void setViernes(Boolean viernes) {
        this.viernes = viernes;
    }

    public Boolean isSabado() {
        return sabado;
    }

    public void setSabado(Boolean sabado) {
        this.sabado = sabado;
    }

    public Boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(Boolean domingo) {
        this.domingo = domingo;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public Boolean isBonoAlimenticio() {
        return bonoAlimenticio;
    }

    public void setBonoAlimenticio(Boolean bonoAlimenticio) {
        this.bonoAlimenticio = bonoAlimenticio;
    }

    public Boolean isApoyoEconomico() {
        return apoyoEconomico;
    }

    public void setApoyoEconomico(Boolean apoyoEconomico) {
        this.apoyoEconomico = apoyoEconomico;
    }

    public Boolean isAuxilioTransporte() {
        return auxilioTransporte;
    }

    public void setAuxilioTransporte(Boolean auxilioTransporte) {
        this.auxilioTransporte = auxilioTransporte;
    }

    public Boolean isCapacitacion() {
        return capacitacion;
    }

    public void setCapacitacion(Boolean capacitacion) {
        this.capacitacion = capacitacion;
    }

    public String getOtroApoyo() {
        return otroApoyo;
    }

    public void setOtroApoyo(String otroApoyo) {
        this.otroApoyo = otroApoyo;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getNitEmpresa() {
        return nitEmpresa;
    }

    public void setNitEmpresa(String nitEmpresa) {
        this.nitEmpresa = nitEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getSectorEconomicoEmpresa() {
        return sectorEconomicoEmpresa;
    }

    public void setSectorEconomicoEmpresa(String sectorEconomicoEmpresa) {
        this.sectorEconomicoEmpresa = sectorEconomicoEmpresa;
    }

    public String getRepresentanteLegalEmpresa() {
        return representanteLegalEmpresa;
    }

    public void setRepresentanteLegalEmpresa(String representanteLegalEmpresa) {
        this.representanteLegalEmpresa = representanteLegalEmpresa;
    }

    public String getAsesorEmpresa() {
        return asesorEmpresa;
    }

    public void setAsesorEmpresa(String asesorEmpresa) {
        this.asesorEmpresa = asesorEmpresa;
    }

    public String getCargoAsesorEmpresa() {
        return cargoAsesorEmpresa;
    }

    public void setCargoAsesorEmpresa(String cargoAsesorEmpresa) {
        this.cargoAsesorEmpresa = cargoAsesorEmpresa;
    }

    public String getEmailAsesorEmpresa() {
        return emailAsesorEmpresa;
    }

    public void setEmailAsesorEmpresa(String emailAsesorEmpresa) {
        this.emailAsesorEmpresa = emailAsesorEmpresa;
    }

    public String getMunicipioEmpresa() {
        return municipioEmpresa;
    }

    public void setMunicipioEmpresa(String municipioEmpresa) {
        this.municipioEmpresa = municipioEmpresa;
    }

    public String getTelefonoContactoEmpresa() {
        return telefonoContactoEmpresa;
    }

    public void setTelefonoContactoEmpresa(String telefonoContactoEmpresa) {
        this.telefonoContactoEmpresa = telefonoContactoEmpresa;
    }

    public String getEmailEmpresa() {
        return emailEmpresa;
    }

    public void setEmailEmpresa(String emailEmpresa) {
        this.emailEmpresa = emailEmpresa;
    }

    public String getDepartamentoEmpresa() {
        return departamentoEmpresa;
    }

    public void setDepartamentoEmpresa(String departamentoEmpresa) {
        this.departamentoEmpresa = departamentoEmpresa;
    }

    public String getIdentificacionRepresentanteLegal() {
        return identificacionRepresentanteLegal;
    }

    public void setIdentificacionRepresentanteLegal(String identificacionRepresentanteLegal) {
        this.identificacionRepresentanteLegal = identificacionRepresentanteLegal;
    }

    public String getProfesionAsesorEmpresa() {
        return profesionAsesorEmpresa;
    }

    public void setProfesionAsesorEmpresa(String profesionAsesorEmpresa) {
        this.profesionAsesorEmpresa = profesionAsesorEmpresa;
    }

    public String getCelularAsesorEmpresa() {
        return celularAsesorEmpresa;
    }

    public void setCelularAsesorEmpresa(String celularAsesorEmpresa) {
        this.celularAsesorEmpresa = celularAsesorEmpresa;
    }


    ////////////////////77777777777777
    public Long getInformacionPasantiaProyectoId() {
        return informacionPasantiaProyectoId;
    }

    public void setInformacionPasantiaProyectoId(Long proyectoId) {
        this.informacionPasantiaProyectoId = proyectoId;
    }

    public String getInformacionPasantiaProyectoTitulo() {
        return informacionPasantiaProyectoTitulo;
    }

    public void setInformacionPasantiaProyectoTitulo(String proyectoTitulo) {
        this.informacionPasantiaProyectoTitulo = proyectoTitulo;
    }
    //////////////////////////77777


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InformacionPasantiaDTO informacionPasantiaDTO = (InformacionPasantiaDTO) o;
        if (informacionPasantiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), informacionPasantiaDTO.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }


    @Override
    public String toString() {
        return "InformacionPasantiaDTO{" +
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
            ", informacionPasantiaProyecto=" + getInformacionPasantiaProyectoId() +
            ", informacionPasantiaProyecto='" + getInformacionPasantiaProyectoTitulo() + "'" +
            "}";
    }
}
