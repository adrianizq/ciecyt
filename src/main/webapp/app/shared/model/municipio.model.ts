export interface IMunicipio {
  id?: number;
  region?: string;
  codigoDaneDepartamento?: string;
  departamento?: string;
  codigoDaneMunicipio?: string;
  municipio?: string;
}

export class Municipio implements IMunicipio {
  constructor(
    public id?: number,
    public region?: string,
    public codigoDaneDepartamento?: string,
    public departamento?: string,
    public codigoDaneMunicipio?: string,
    public municipio?: string
  ) {}
}
