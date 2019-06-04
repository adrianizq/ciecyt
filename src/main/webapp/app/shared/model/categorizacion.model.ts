import { IProyecto } from 'app/shared/model/proyecto.model';

export interface ICategorizacion {
  id?: number;
  categoria?: string;
  proyecto?: IProyecto;
}

export class Categorizacion implements ICategorizacion {
  constructor(public id?: number, public categoria?: string, public proyecto?: IProyecto) {}
}
