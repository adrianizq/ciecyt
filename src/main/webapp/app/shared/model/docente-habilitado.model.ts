export interface IDocenteHabilitadoUsuario {
  id?: number;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
}

export interface IDocenteHabilitadoFacultad {
  id?: number;
  codigoFacultad?: string;
  facultad?: string;
}

export interface IDocenteHabilitado {
  id?: number;
  user?: IDocenteHabilitadoUsuario;
  facultad?: IDocenteHabilitadoFacultad;
  rol?: string;
  fechaDesde?: string;
  fechaHasta?: string;
  actoResolucion?: string;
  observaciones?: string;
}

/**
 * Lo que se envia para dar de alta o cerrar una habilitacion. Deliberadamente no incluye la
 * vigencia: la fecha de cierre la calcula el backend, para que nobody la fije desde el navegador.
 */
export class SolicitudHabilitacion {
  constructor(
    public userId?: number,
    public login?: string,
    public facultadId?: number,
    public rol?: string,
    public actoResolucion?: string,
    public observaciones?: string
  ) {}
}
