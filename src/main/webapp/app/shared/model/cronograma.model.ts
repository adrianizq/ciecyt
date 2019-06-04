import { Moment } from 'moment';
import { IProyecto } from 'app/shared/model/proyecto.model';

export interface ICronograma {
  id?: number;
  actividad?: string;
  duracion?: number;
  fechaIni?: Moment;
  fechaFin?: Moment;
  proyecto?: IProyecto;
}

export class Cronograma implements ICronograma {
  constructor(
    public id?: number,
    public actividad?: string,
    public duracion?: number,
    public fechaIni?: Moment,
    public fechaFin?: Moment,
    public proyecto?: IProyecto
  ) {}
}
