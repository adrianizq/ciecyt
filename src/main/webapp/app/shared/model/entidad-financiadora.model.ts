import { IProyecto } from 'app/shared/model/proyecto.model';
import { IEmpresas } from 'app/shared/model/empresas.model';

export interface IEntidadFinanciadora {
  id?: number;
  valor?: number;
  estadoFinanciacion?: boolean;
  proyecto?: IProyecto;
  empresas?: IEmpresas[];
}

export class EntidadFinanciadora implements IEntidadFinanciadora {
  constructor(
    public id?: number,
    public valor?: number,
    public estadoFinanciacion?: boolean,
    public proyecto?: IProyecto,
    public empresas?: IEmpresas[]
  ) {
    this.estadoFinanciacion = this.estadoFinanciacion || false;
  }
}
