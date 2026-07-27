export interface ICicloPropedeutico {
  id?: number;
  ciclo?: string;
  modalidadId?: number;
  cicloPropedeuticoAcuerdoAcuerdo?: string;
  cicloPropedeuticoAcuerdoId?: number;
}

export class CicloPropedeutico implements ICicloPropedeutico {
  constructor(
    public id?: number,
    public ciclo?: string,
    public modalidadId?: number,
    public cicloPropedeuticoAcuerdoAcuerdo?: string,
    public cicloPropedeuticoAcuerdoId?: number
  ) {}
}
