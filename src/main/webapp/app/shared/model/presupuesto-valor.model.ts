export interface IPresupuestoValor {
  id?: number;
  descripcion?: string;
  justificacion?: string;
  cantidad?: number;
  valorUnitario?: number;
  especie?: number;
  dinero?: number;
  ordenVista?: number;
  presupuestoValorRubroRubro?: string;
  presupuestoValorRubroId?: number;
  presupuestoValorProyectoTitulo?: string;
  presupuestoValorProyectoId?: number;
  presupuestoValorEntidadEntidad?: string;
  presupuestoValorEntidadId?: number;
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
    public ordenVista?: number,
    public presupuestoValorRubroRubro?: string,
    public presupuestoValorRubroId?: number,
    public presupuestoValorProyectoTitulo?: string,
    public presupuestoValorProyectoId?: number,
    public presupuestoValorEntidadEntidad?: string,
    public presupuestoValorEntidadId?: number
  ) {}
}
