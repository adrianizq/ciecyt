export interface IRetroalimentacion {
  id?: number;
  titulo?: string;
  retroalimentacion?: string;
  fechaRetroalimentacion?: Date;
  estadoRetroalimentacion?: Date;
  estadoProyectoFase?: number;
  retroalimentacionProyectoFaseTitulo?: string;
  retroalimentacionProyectoFaseId?: number;
  retroalimentacionUserLogin?: string;
  retroalimentacionUserId?: number;
}

export class Retroalimentacion implements IRetroalimentacion {
  constructor(
    public id?: number,
    public titulo?: string,
    public retroalimentacion?: string,
    public fechaRetroalimentacion?: Date,
    public estadoRetroalimentacion?: Date,
    public estadoProyectoFase?: number,
    public retroalimentacionProyectoFaseTitulo?: string,
    public retroalimentacionProyectoFaseId?: number,
    public retroalimentacionUserLogin?: string,
    public retroalimentacionUserId?: number
  ) {}
}
