import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

export interface IRolesUsuarioProyecto {
  id?: number;
  rol?: number;
  descripcion?: string;
  integrantesProyectos?: IIntegrantesProyecto[];
}

export class RolesUsuarioProyecto implements IRolesUsuarioProyecto {
  constructor(public id?: number, public rol?: number, public descripcion?: string, public integrantesProyectos?: IIntegrantesProyecto[]) {}
}
