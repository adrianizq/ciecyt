import { IProductoProyecto } from 'app/shared/model/producto-proyecto.model';

export interface IProducto {
  id?: number;
  producto?: string;
  productoProyectos?: IProductoProyecto[];
}

export class Producto implements IProducto {
  constructor(public id?: number, public producto?: string, public productoProyectos?: IProductoProyecto[]) {}
}
