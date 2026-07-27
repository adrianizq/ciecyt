export interface IProducto {
  id?: number;
  producto?: string;
}

export class Producto implements IProducto {
  constructor(public id?: number, public producto?: string) {}
}
