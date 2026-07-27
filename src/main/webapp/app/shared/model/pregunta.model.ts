import { IModalidad } from './modalidad.model';
import { IPreguntaModalidad } from './pregunta-modalidad.model';

export interface IPregunta {
  id?: number;
  encabezado?: string;
  descripcion?: string;
  orden?: number;
  preguntaElemento?: string;
  preguntaElementoId?: number;
  pregunta?: string;
  preguntaTipoPreguntaTipoPregunta?: string;
  preguntaTipoPreguntaId?: number;
  // preguntaModalidadModalidad?: string;
  //preguntaModalidadId?: number;
  //preguntaRolesModalidadRol?: string;
  preguntaRolesModalidadId?: number;
  preguntaFase?: string;
  preguntaFaseId?: number;
  puntajeMaximo?: number;
  preguntaModalidads?: IPreguntaModalidad[];
  authorities?: any[];
}

export class Pregunta implements IPregunta {
  constructor(
    public id?: number,
    public encabezado?: string,
    public descripcion?: string,
    public orden?: number,
    public preguntaElemento?: string,
    public preguntaElementoId?: number,
    public pregunta?: string,
    public preguntaTipoPreguntaTipoPregunta?: string,
    public preguntaTipoPreguntaId?: number,
    //  public preguntaModalidadModalidad?: string,
    //  public preguntaModalidadId?: number,
    //public preguntaRolesModalidadRol?: string,
    //public preguntaRolesModalidadId?: number,
    public preguntaFase?: string,
    public preguntaFaseId?: number,
    public puntajeMaximo?: number,
    public preguntaModalidads?: IPreguntaModalidad[],
    public authorities?: any[]
  ) {}
}
