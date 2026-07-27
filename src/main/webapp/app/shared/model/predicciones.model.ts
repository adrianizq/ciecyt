import { IIntegranteProyecto } from './integrante-proyecto.model';

export interface IPredicciones {
  predicciones?: string;
}

export class Predicciones implements IPredicciones {
  constructor(public predicciones?: string) {}
}
