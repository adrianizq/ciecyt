export interface IInformacionPasantia {
  id?: number;
  duracionHoras?: number;
  direccion?: string;
  email?: string;
  lunes?: boolean;
  martes?: boolean;
  miercoles?: boolean;
  jueves?: boolean;
  viernes?: boolean;
  sabado?: boolean;
  domingo?: boolean;
  convenio?: string;
  bonoAlimenticio?: boolean;
  apoyoEconomico?: boolean;
  auxilioTransporte?: boolean;
  capacitacion?: boolean;
  otroApoyo?: string;
  nombreEmpresa?: string;
  nitEmpresa?: string;
  direccionEmpresa?: string;
  sectorEconomicoEmpresa?: string;
  representanteLegalEmpresa?: string;
  asesorEmpresa?: string;
  cargoAsesorEmpresa?: string;
  emailAsesorEmpresa?: string;
  municipioEmpresa?: string;
  telefonoContactoEmpresa?: string;
  emailEmpresa?: string;
  departamentoEmpresa?: string;
  identificacionRepresentanteLegal?: string;
  profesionAsesorEmpresa?: string;
  celularAsesorEmpresa?: string;
  //informacionPasantiaProyecto?: IProyecto;
  informacionPasantiaProyectoTitulo?: string;
  informacionPasantiaProyectoId?: number;
}

export class InformacionPasantia implements IInformacionPasantia {
  constructor(
    public id?: number,
    public duracionHoras?: number,
    public direccion?: string,
    public email?: string,
    public lunes?: boolean,
    public martes?: boolean,
    public miercoles?: boolean,
    public jueves?: boolean,
    public viernes?: boolean,
    public sabado?: boolean,
    public domingo?: boolean,
    public convenio?: string,
    public bonoAlimenticio?: boolean,
    public apoyoEconomico?: boolean,
    public auxilioTransporte?: boolean,
    public capacitacion?: boolean,
    public otroApoyo?: string,
    public nombreEmpresa?: string,
    public nitEmpresa?: string,
    public direccionEmpresa?: string,
    public sectorEconomicoEmpresa?: string,
    public representanteLegalEmpresa?: string,
    public asesorEmpresa?: string,
    public cargoAsesorEmpresa?: string,
    public emailAsesorEmpresa?: string,
    public municipioEmpresa?: string,
    public telefonoContactoEmpresa?: string,
    public emailEmpresa?: string,
    public departamentoEmpresa?: string,
    public identificacionRepresentanteLegal?: string,
    public profesionAsesorEmpresa?: string,
    public celularAsesorEmpresa?: string,
    public informacionPasantiaProyectoId?: number,
    public informacionPasantiaProyectoTitulo?: string
  ) {
    this.lunes = this.lunes || false;
    this.martes = this.martes || false;
    this.miercoles = this.miercoles || false;
    this.jueves = this.jueves || false;
    this.viernes = this.viernes || false;
    this.sabado = this.sabado || false;
    this.domingo = this.domingo || false;
    this.bonoAlimenticio = this.bonoAlimenticio || false;
    this.apoyoEconomico = this.apoyoEconomico || false;
    this.auxilioTransporte = this.auxilioTransporte || false;
    this.capacitacion = this.capacitacion || false;
  }
}
