export interface IAsesorExternoFacultad {
  id?: number;
  codigoFacultad?: string;
  facultad?: string;
}

export interface IAsesorExterno {
  id?: number;
  nombres?: string;
  apellidos?: string;
  numeroDocumento?: string;
  correoElectronico?: string;
  telefono?: string;
  titulos?: string;
  institucionOrigen?: string;
  rol?: string;
  idoneidadVerificada?: boolean;
  fechaVerificacionIdoneidad?: string;
  verificadorLogin?: string;
  fuenteVerificacion?: string;
  observaciones?: string;
  facultad?: IAsesorExternoFacultad;
}

export class SolicitudAsesorExterno {
  constructor(
    public facultadId?: number,
    public nombres?: string,
    public apellidos?: string,
    public numeroDocumento?: string,
    public correoElectronico?: string,
    public telefono?: string,
    public titulos?: string,
    public institucionOrigen?: string,
    public rol?: string,
    public fuenteVerificacion?: string,
    public observaciones?: string
  ) {}
}
