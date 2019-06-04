import { IElementos } from 'app/shared/model/elementos.model';
import { IPreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';
import { IFaseTrabajo } from 'app/shared/model/fase-trabajo.model';

export interface IPreguntas {
  id?: number;
  pregunta?: string;
  elementos?: IElementos;
  preguntaRespuestas?: IPreguntaRespuesta[];
  faseTrabajo?: IFaseTrabajo;
}

export class Preguntas implements IPreguntas {
  constructor(
    public id?: number,
    public pregunta?: string,
    public elementos?: IElementos,
    public preguntaRespuestas?: IPreguntaRespuesta[],
    public faseTrabajo?: IFaseTrabajo
  ) {}
}
