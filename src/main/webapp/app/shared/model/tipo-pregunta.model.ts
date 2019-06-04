import { IPreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';

export interface ITipoPregunta {
  id?: number;
  tipoPregunta?: string;
  tipoDato?: string;
  preguntaRespuestas?: IPreguntaRespuesta[];
}

export class TipoPregunta implements ITipoPregunta {
  constructor(
    public id?: number,
    public tipoPregunta?: string,
    public tipoDato?: string,
    public preguntaRespuestas?: IPreguntaRespuesta[]
  ) {}
}
