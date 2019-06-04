import { IProyecto } from 'app/shared/model/proyecto.model';
import { IProducto } from 'app/shared/model/producto.model';

export interface IProductoProyecto {
  id?: number;
  aplica?: boolean;
  descripcion?: string;
  proyecto?: IProyecto;
  producto?: IProducto;
}

export class ProductoProyecto implements IProductoProyecto {
  constructor(
    public id?: number,
    public aplica?: boolean,
    public descripcion?: string,
    public proyecto?: IProyecto,
    public producto?: IProducto
  ) {
    this.aplica = this.aplica || false;
  }
}
