export interface IPostulacionPasantia {
  id?: number;
  convocatoriaId?: number;
  convocatoriaTitulo?: string;
  proyectoId?: number;
  proyectoTitulo?: string;
  usuarioId?: number;
  usuarioLogin?: string;
  usuarioNombre?: string;
  estado?: string;
  fechaPostulacion?: Date;
  observacion?: string;
  fechaDecision?: Date;
}

export class PostulacionPasantia implements IPostulacionPasantia {
  constructor(
    public id?: number,
    public convocatoriaId?: number,
    public convocatoriaTitulo?: string,
    public proyectoId?: number,
    public proyectoTitulo?: string,
    public usuarioId?: number,
    public usuarioLogin?: string,
    public usuarioNombre?: string,
    public estado?: string,
    public fechaPostulacion?: Date,
    public observacion?: string,
    public fechaDecision?: Date
  ) {}
}
