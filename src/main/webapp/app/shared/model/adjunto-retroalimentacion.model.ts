export interface IAdjuntoRetroalimentacion {
  id?: number;
  nombreAdjunto?: string;
  fechaCreacion?: Date;
  fechaModificacion?: Date;
  estadoAdjunto?: number;
  adjuntoRetroalimentacion?: string;
  nombreArchivoOriginal?: string;
  fechaInicio?: Date;
  fechaFin?: Date;
  adjuntoRetroalimentacionProyectoId?: number;
  archivoContentType?: string;
  authority?: string;
  archivo?: any;
  adjuntoRetroalimentacionFaseId?: number;
  file?: any;
}

export class AdjuntoRetroalimentacion implements IAdjuntoRetroalimentacion {
  constructor(
    public id?: number,
    public nombreAdjunto?: string,
    public fechaCreacion?: Date,
    public fechaModificacion?: Date,
    public estadoAdjunto?: number,
    public adjuntoRetroalimentacion?: string,
    public nombreArchivoOriginal?: string,
    public fechaInicio?: Date,
    public fechaFin?: Date,
    public adjuntoRetroalimentacionProyectoId?: number,
    public archivoContentType?: string,
    public authority?: string,
    public archivo?: any,
    public adjuntoRetroalimentacionFaseId?: number,
    public file?: any
  ) {}
}
