import { IProyecto } from '@/shared/model/proyecto.model';

export interface IFacultad {
  id?: number;
  codigoFacultad?: string;
  facultad?: string;
  facultadProyectos?: IProyecto[];
}

export class Facultad implements IFacultad {
  constructor(public id?: number, public codigoFacultad?: string, public facultad?: string, public facultadProyectos?: IProyecto[]) {}
}
