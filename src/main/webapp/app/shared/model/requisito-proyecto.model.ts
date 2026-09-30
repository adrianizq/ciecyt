import { EnumEstadoProyecto } from './enumerations/enum-estado-proyecto.model';
import { EnumEstadoRequisito } from './enumerations/enum-estado-requisito.model';
import { TipoRequisito } from './enumerations/tipo-requisito.model';

export interface IRequisitoProyecto {
  id?: number;
  estado?: EnumEstadoRequisito;
  fechaEntrega?: Date;
  fechaValidacion?: Date;
  observacion?: string;
  archivo?: string;
  validadoPor?: string;
  requisitoProyectoRequisitoId?: number;
  requisitoProyectoRequisitoNombre?: string;
  requisitoProyectoRequisitoCodigo?: string;
  requisitoProyectoRequisitoObligatorio?: boolean;
  requisitoProyectoRequisitoTipo?: TipoRequisito;
  requisitoProyectoRequisitoEstado?: EnumEstadoProyecto;
  requisitoProyectoProyectoId?: number;
  requisitoProyectoProyectoTitulo?: string;
}

export class RequisitoProyecto implements IRequisitoProyecto {
  constructor(
    public id?: number,
    public estado?: EnumEstadoRequisito,
    public fechaEntrega?: Date,
    public fechaValidacion?: Date,
    public observacion?: string,
    public archivo?: string,
    public validadoPor?: string,
    public requisitoProyectoRequisitoId?: number,
    public requisitoProyectoRequisitoNombre?: string,
    public requisitoProyectoRequisitoCodigo?: string,
    public requisitoProyectoRequisitoObligatorio?: boolean,
    public requisitoProyectoRequisitoTipo?: TipoRequisito,
    public requisitoProyectoRequisitoEstado?: EnumEstadoProyecto,
    public requisitoProyectoProyectoId?: number,
    public requisitoProyectoProyectoTitulo?: string
  ) {}
}
