import { IProyecto } from 'app/shared/model/proyecto.model';

export interface IModalidadDeGrado {
  id?: number;
  modalidad?: string;
  proyectos?: IProyecto[];
}

export class ModalidadDeGrado implements IModalidadDeGrado {
  constructor(public id?: number, public modalidad?: string, public proyectos?: IProyecto[]) {}
}
