export interface IPeriodo {
  id?: number;
  nombre?: string;
  fechaInicio?: any;
  fechaFin?: any;
  activo?: boolean;
}

export class Periodo implements IPeriodo {
  constructor(public id?: number, public nombre?: string, public fechaInicio?: any, public fechaFin?: any, public activo?: boolean) {
    this.activo = this.activo !== false;
  }
}
