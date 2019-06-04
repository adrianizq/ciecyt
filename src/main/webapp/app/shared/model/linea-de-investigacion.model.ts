import { IProyecto } from 'app/shared/model/proyecto.model';
import { ILineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';

export interface ILineaDeInvestigacion {
  id?: number;
  linea?: string;
  proyectos?: IProyecto[];
  padre?: ILineaDeInvestigacion;
}

export class LineaDeInvestigacion implements ILineaDeInvestigacion {
  constructor(public id?: number, public linea?: string, public proyectos?: IProyecto[], public padre?: ILineaDeInvestigacion) {}
}
