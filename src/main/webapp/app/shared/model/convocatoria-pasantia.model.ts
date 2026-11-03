export interface IConvocatoriaPasantia {
  id?: number;
  titulo?: string;
  descripcion?: string;
  modalidadId?: number;
  entidad?: string;
  convenio?: string;
  cupos?: number;
  fechaApertura?: Date;
  fechaCierre?: Date;
  requisitos?: string;
  criteriosSeleccion?: string;
  estado?: string;
  activo?: boolean;
}

export class ConvocatoriaPasantia implements IConvocatoriaPasantia {
  constructor(
    public id?: number,
    public titulo?: string,
    public descripcion?: string,
    public modalidadId?: number,
    public entidad?: string,
    public convenio?: string,
    public cupos?: number,
    public fechaApertura?: Date,
    public fechaCierre?: Date,
    public requisitos?: string,
    public criteriosSeleccion?: string,
    public estado?: string,
    public activo?: boolean
  ) {}
}
