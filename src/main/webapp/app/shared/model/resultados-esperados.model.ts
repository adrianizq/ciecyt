import { IProyecto } from 'app/shared/model/proyecto.model';

export interface IResultadosEsperados {
  id?: number;
  resultado?: string;
  indicador?: string;
  beneficiario?: string;
  proyecto?: IProyecto;
}

export class ResultadosEsperados implements IResultadosEsperados {
  constructor(
    public id?: number,
    public resultado?: string,
    public indicador?: string,
    public beneficiario?: string,
    public proyecto?: IProyecto
  ) {}
}
