import { IElementosProyecto } from 'app/shared/model/elementos-proyecto.model';
import { IPreguntas } from 'app/shared/model/preguntas.model';

export interface IElementos {
  id?: number;
  elemento?: string;
  elementosProyectos?: IElementosProyecto[];
  preguntas?: IPreguntas[];
}

export class Elementos implements IElementos {
  constructor(
    public id?: number,
    public elemento?: string,
    public elementosProyectos?: IElementosProyecto[],
    public preguntas?: IPreguntas[]
  ) {}
}
