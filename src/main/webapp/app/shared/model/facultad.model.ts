import { IProyecto } from 'app/shared/model/proyecto.model';
import { IFacultad } from 'app/shared/model/facultad.model';

export interface IFacultad {
  id?: number;
  nombre?: string;
  proyectos?: IProyecto[];
  padre?: IFacultad;
}

export class Facultad implements IFacultad {
  constructor(public id?: number, public nombre?: string, public proyectos?: IProyecto[], public padre?: IFacultad) {}
}
