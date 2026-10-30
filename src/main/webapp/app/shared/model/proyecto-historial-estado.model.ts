export interface IProyectoHistorialEstado {
  id?: number;
  estadoAnterior?: string;
  estadoNuevo?: string;
  observacion?: string;
  usuarioLogin?: string;
  fechaCambio?: Date;
  proyectoId?: number;
  proyectoTitulo?: string;
}

export class ProyectoHistorialEstado implements IProyectoHistorialEstado {
  constructor(
    public id?: number,
    public estadoAnterior?: string,
    public estadoNuevo?: string,
    public observacion?: string,
    public usuarioLogin?: string,
    public fechaCambio?: Date,
    public proyectoId?: number,
    public proyectoTitulo?: string
  ) {}
}
