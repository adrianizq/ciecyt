export interface IElementoModalidad {
  id?: number;
  elementoId?: number;
  modalidadId?: number;
}

export class ElementoModalidad implements IElementoModalidad {
  constructor(public id?: number, public elementoId?: number, public modalidadId?: number) {}
}
