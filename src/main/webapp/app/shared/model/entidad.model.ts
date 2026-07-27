export interface IEntidad {
  id?: number;
  entidad?: string;
  nit?: string;
}

export class Entidad implements IEntidad {
  constructor(public id?: number, public entidad?: string, public nit?: string) {}
}
