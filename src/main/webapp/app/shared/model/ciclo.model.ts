export interface ICiclo {
  id?: number;
  ciclo?: string;
  modalidadIds?: number[];
  modalidadNombres?: string[];
}

export class Ciclo implements ICiclo {
  constructor(public id?: number, public ciclo?: string, public modalidadIds?: number[], public modalidadNombres?: string[]) {}
}
