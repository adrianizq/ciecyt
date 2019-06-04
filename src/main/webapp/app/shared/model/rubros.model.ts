import { IPresupuestoValor } from 'app/shared/model/presupuesto-valor.model';

export interface IRubros {
  id?: number;
  rubro?: string;
  presupuestoValors?: IPresupuestoValor[];
}

export class Rubros implements IRubros {
  constructor(public id?: number, public rubro?: string, public presupuestoValors?: IPresupuestoValor[]) {}
}
