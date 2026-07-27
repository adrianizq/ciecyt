import { IFacultad } from './facultad.model';

export interface IPrograma {
  id?: number;
  programa?: string;
  descripcion?: string;
  codigoInterno?: string;
  codigoSnies?: string;
  creditos?: number;
  ciclo?: string;
  resolucion?: string;
  titulo?: string;
  duracionSemestres?: number;
  programaFacultadId?: number;
  programaFacultad?: IFacultad[];
  
}

export class Programa implements IPrograma {
  constructor(
    public id?: number,
    public programa?: string,
    public descripcion?: string,
    public codigoInterno?: string,
    public codigoSnies?: string,
    public creditos?: number,
    public ciclo?: string,
    public resolucion?: string,
    public titulo?: string,
    public duracionSemestres?: number,
    public programaFacultadId?: number,
    public programaFacultad?: IFacultad[],
   
  ) {}
}
