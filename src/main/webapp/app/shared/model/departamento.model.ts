export interface IDepartamento {
  id?: number;
  codigoDane?: string;
  departamento?: string;
}

export class Departamento implements IDepartamento {
  constructor(public id?: number, public codigoDane?: string, public departamento?: string) {}
}
