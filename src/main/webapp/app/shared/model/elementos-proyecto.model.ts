import { IProyecto } from 'app/shared/model/proyecto.model';
import { IElementos } from 'app/shared/model/elementos.model';

export interface IElementosProyecto {
  id?: number;
  dato?: string;
  proyecto?: IProyecto;
  elementos?: IElementos;
}

export class ElementosProyecto implements IElementosProyecto {
  constructor(public id?: number, public dato?: string, public proyecto?: IProyecto, public elementos?: IElementos) {}
}
