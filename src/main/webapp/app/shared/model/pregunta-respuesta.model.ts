import { IPreguntas } from 'app/shared/model/preguntas.model';
import { ITipoPregunta } from 'app/shared/model/tipo-pregunta.model';
import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

export interface IPreguntaRespuesta {
  id?: number;
  preguntas?: IPreguntas;
  tipoPregunta?: ITipoPregunta;
  integrantesProyecto?: IIntegrantesProyecto;
}

export class PreguntaRespuesta implements IPreguntaRespuesta {
  constructor(
    public id?: number,
    public preguntas?: IPreguntas,
    public tipoPregunta?: ITipoPregunta,
    public integrantesProyecto?: IIntegrantesProyecto
  ) {}
}
