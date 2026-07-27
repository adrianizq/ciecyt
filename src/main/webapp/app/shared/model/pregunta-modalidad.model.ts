export interface IPreguntaModalidad {
  id?: number;
  preguntaId?: number;
  modalidad2Id?: number;
}

export class PreguntaModalidad implements IPreguntaModalidad {
  constructor(public id?: number, public preguntaId?: number, public modalidad2Id?: number) {}
}
