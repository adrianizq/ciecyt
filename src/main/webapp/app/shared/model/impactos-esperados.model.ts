import { IProyecto } from 'app/shared/model/proyecto.model';

export interface IImpactosEsperados {
  id?: number;
  impacto?: string;
  plazo?: number;
  indicador?: string;
  supuestos?: string;
  proyecto?: IProyecto;
}

export class ImpactosEsperados implements IImpactosEsperados {
  constructor(
    public id?: number,
    public impacto?: string,
    public plazo?: number,
    public indicador?: string,
    public supuestos?: string,
    public proyecto?: IProyecto
  ) {}
}
