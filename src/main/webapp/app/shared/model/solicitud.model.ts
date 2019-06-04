import { Moment } from 'moment';
import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

export interface ISolicitud {
  id?: number;
  estado?: boolean;
  asunto?: string;
  fechaCreancion?: Moment;
  integrantesProyecto?: IIntegrantesProyecto;
}

export class Solicitud implements ISolicitud {
  constructor(
    public id?: number,
    public estado?: boolean,
    public asunto?: string,
    public fechaCreancion?: Moment,
    public integrantesProyecto?: IIntegrantesProyecto
  ) {
    this.estado = this.estado || false;
  }
}
