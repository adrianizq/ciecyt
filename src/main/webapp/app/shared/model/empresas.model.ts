import { IEntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';

export interface IEmpresas {
  id?: number;
  entidad?: string;
  entidadFinanciadora?: IEntidadFinanciadora;
}

export class Empresas implements IEmpresas {
  constructor(public id?: number, public entidad?: string, public entidadFinanciadora?: IEntidadFinanciadora) {}
}
