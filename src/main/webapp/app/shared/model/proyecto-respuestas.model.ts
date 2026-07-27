export const enum EnumRespuestas {
  CUMPLE = 'CUMPLE',
  NO_CUMPLE = 'NO_CUMPLE',
  NO_APLICA = 'NO_APLICA',
}

export interface IProyectoRespuestas {
  id?: number;
  respuesta?: EnumRespuestas;
  observaciones?: string;
  viable?: boolean;
  proyectoRespuestasPreguntaPregunta?: string;
  proyectoRespuestasPreguntaId?: number;
  proyectoRespuestasProyectoTitulo?: string;
  proyectoRespuestasProyectoId?: number;
  elemento?: string;
  encabezado?: string;
  dato?: string;
  preguntaTipoPreguntaId?: number;
  preguntaTipoPreguntaTipoPregunta?: string;
  siNo?: boolean;
  respuestaTexto?: string;
  respuestaNumero?: string;
  puntajeMaximo?: number;
  faseId?: number;
  authority?: string;
}

export class ProyectoRespuestas implements IProyectoRespuestas {
  constructor(
    public id?: number,
    public respuesta?: EnumRespuestas,
    public observaciones?: string,
    public viable?: boolean,
    public proyectoRespuestasPreguntaPregunta?: string,
    public proyectoRespuestasPreguntaId?: number,
    public proyectoRespuestasProyectoTitulo?: string,
    public proyectoRespuestasProyectoId?: number,
    public elemento?: string,
    public encabezado?: string,
    public dato?: string,
    public preguntaTipoPreguntaId?: number,
    public preguntaTipoPreguntaTipoPregunta?: string,
    public siNo?: boolean,
    public respuestaTexto?: string,
    public respuestaNumero?: string,
    public puntajeMaximo?: number,
    public faseId?: number,
    public authority?: string
  ) {
    this.viable = this.viable || false;
  }
}
