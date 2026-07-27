export interface IProductoProyecto {
  id?: number;
  aplica?: boolean;
  descripcion?: string;
  productoProyectoProductoProducto?: string;
  productoProyectoProductoId?: number;
  productoProyectoProyectoTitulo?: string;
  productoProyectoProyectoId?: number;
}

export class ProductoProyecto implements IProductoProyecto {
  constructor(
    public id?: number,
    public aplica?: boolean,
    public descripcion?: string,
    public productoProyectoProductoProducto?: string,
    public productoProyectoProductoId?: number,
    public productoProyectoProyectoTitulo?: string,
    public productoProyectoProyectoId?: number
  ) {
    this.aplica = this.aplica || false;
  }
}
