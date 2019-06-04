import { IPreguntas } from 'app/shared/model/preguntas.model';

export interface IFaseTrabajo {
  id?: number;
  faseTrabajo?: string;
  faseTrabajos?: IPreguntas[];
}

export class FaseTrabajo implements IFaseTrabajo {
  constructor(public id?: number, public faseTrabajo?: string, public faseTrabajos?: IPreguntas[]) {}
}
