import { ICicloPropedeutico } from '@/shared/model/ciclo-propedeutico.model';

export interface IModalidad {
  id?: number;
  modalidad?: string;
  contieneLinea?: boolean;
  modalidadCicloPropedeuticos?: ICicloPropedeutico[];
  modalidadAcuerdoAcuerdo?: string;
  modalidadAcuerdoId?: number;
}

export class Modalidad implements IModalidad {
  constructor(
    public id?: number,
    public modalidad?: string,
    public contieneLinea?: boolean,
    public modalidadCicloPropedeuticos?: ICicloPropedeutico[],
    public modalidadAcuerdoAcuerdo?: string,
    public modalidadAcuerdoId?: number
  ) {}
}
