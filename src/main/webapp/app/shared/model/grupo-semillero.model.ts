export interface IGrupoSemillero {
  id?: number;
  nombre?: string;
  tipo?: boolean;
}

export class GrupoSemillero implements IGrupoSemillero {
  constructor(public id?: number, public nombre?: string, public tipo?: boolean) {
    this.tipo = this.tipo || false;
  }
}
