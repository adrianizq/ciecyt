export interface IResultadosEsperados {
  id?: number;
  resultado?: string;
  indicador?: string;
  beneficiario?: string;
  ordenVista?: number;
  resultadosEsperadosProyectoTitulo?: string;
  resultadosEsperadosProyectoId?: number;
}

export class ResultadosEsperados implements IResultadosEsperados {
  constructor(
    public id?: number,
    public resultado?: string,
    public indicador?: string,
    public beneficiario?: string,
    public ordenVista?: number,
    public resultadosEsperadosProyectoTitulo?: string,
    public resultadosEsperadosProyectoId?: number
  ) {}
}
