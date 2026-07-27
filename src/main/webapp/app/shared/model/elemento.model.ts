import { IElementoModalidad } from './elemento-modalidad.model';

export interface IElemento {
  id?: number;
  elemento?: string;
  descripcion?: string;
  orden?: number;
  elementoFormatoFormato?: string;
  elementoFormatoId?: number;
  elementoFasesFase?: string;
  elementoFasesId?: number;
  elementoModalidads?: IElementoModalidad[];
  modalidadId?: number;
}

export class Elemento implements IElemento {
  constructor(
    public id?: number,
    public elemento?: string,
    public descripcion?: string,
    public orden?: number,
    public elementoFormatoFormato?: string,
    public elementoFormatoId?: number,
    public elementoFasesFase?: string,
    public elementoFasesId?: number,
    public modalidadId?: number
  ) {}
}
