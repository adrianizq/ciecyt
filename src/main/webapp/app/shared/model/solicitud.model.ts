export interface ISolicitud {
  id?: number;
  estado?: boolean;
  asunto?: string;
  textoSolicitud?: string;
  fechaSolicitud?: Date;
  solicitudIntegranteProyectoIntegrante?: string;
  solicitudIntegranteProyectoId?: number;
  proyectoId?: number;
  proyectoTitulo?: string;
  usuarioLogin?: string;
}

export class Solicitud implements ISolicitud {
  constructor(
    public id?: number,
    public estado?: boolean,
    public asunto?: string,
    public textoSolicitud?: string,
    public fechaSolicitud?: Date,
    public solicitudIntegranteProyectoIntegrante?: string,
    public solicitudIntegranteProyectoId?: number,
    public proyectoId?: number,
    public proyectoTitulo?: string,
    public usuarioLogin?: string
  ) {
    this.estado = this.estado || false;
  }
}
