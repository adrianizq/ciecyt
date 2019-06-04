import { IProyecto } from 'app/shared/model/proyecto.model';
import { IRubros } from 'app/shared/model/rubros.model';

export interface IPresupuestoValor {
  id?: number;
  descripcion?: string;
  justificacion?: string;
  cantidad?: number;
  valorUnitario?: number;
  especie?: number;
  dinero?: number;
  entidadFinanciadora?: string;
  proyecto?: IProyecto;
  rubros?: IRubros;
}

export class PresupuestoValor implements IPresupuestoValor {
  constructor(
    public id?: number,
    public descripcion?: string,
    public justificacion?: string,
    public cantidad?: number,
    public valorUnitario?: number,
    public especie?: number,
    public dinero?: number,
    public entidadFinanciadora?: string,
    public proyecto?: IProyecto,
    public rubros?: IRubros
  ) {}
}
