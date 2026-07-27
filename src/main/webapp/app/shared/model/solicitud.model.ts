export interface ISolicitud {
  id?: number;
  estado?: boolean;
  asunto?: string;
  textoSolicitud?: string;
  fechaSolicitud?: Date;
  solicitudIntegranteProyectoIntegrante?: string;
  solicitudIntegranteProyectoId?: number;
}

export class Solicitud implements ISolicitud {
  constructor(
    public id?: number,
    public estado?: boolean,
    public asunto?: string,
    public textoSolicitud?: string,
    public fechaSolicitud?: Date,
    public solicitudIntegranteProyectoIntegrante?: string,
    public solicitudIntegranteProyectoId?: number
  ) {
    this.estado = this.estado || false;
  }
}
