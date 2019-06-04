import { IProyecto } from 'app/shared/model/proyecto.model';

export interface IGrupoSemillero {
  id?: number;
  nombre?: string;
  tipo?: boolean;
  proyectos?: IProyecto[];
}

export class GrupoSemillero implements IGrupoSemillero {
  constructor(public id?: number, public nombre?: string, public tipo?: boolean, public proyectos?: IProyecto[]) {
    this.tipo = this.tipo || false;
  }
}
