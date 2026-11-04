export interface IFestivo {
  id?: number;
  fecha?: any;
  descripcion?: string;
  activo?: boolean;
}

export interface IErrorImportacionFestivo {
  linea: number;
  mensaje: string;
}

export interface IResultadoImportacionFestivos {
  total: number;
  creados: number;
  actualizados: number;
  errores: IErrorImportacionFestivo[];
}

export class Festivo implements IFestivo {
  constructor(public id?: number, public fecha?: any, public descripcion?: string, public activo?: boolean) {
    this.activo = this.activo !== false;
  }
}
