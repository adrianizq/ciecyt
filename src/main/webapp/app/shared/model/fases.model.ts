export interface IFases {
  id?: number;
  fase?: string;
  notificable?: boolean;
  fasesModalidadModalidad?: string;
  fasesModalidadId?: number;
}

export class Fases implements IFases {
  constructor(
    public id?: number,
    public fase?: string,
    public notificable?: boolean,
    public fasesModalidadModalidad?: string,
    public fasesModalidadId?: number
  ) {
    this.notificable = this.notificable || false;
  }
}
