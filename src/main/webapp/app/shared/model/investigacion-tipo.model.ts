export interface IInvestigacionTipo {
  id?: number;
  investigacionTipo?: string;
  investigacionTipoDescripcion?: string;
  tipo?: string;
  tipoDescripcion?: string;
}

export class InvestigacionTipo implements IInvestigacionTipo {
  constructor(
    public id?: number,
    public investigacionTipo?: string,
    public investigacionTipoDescripcion?: string,
    public tipo?: string,
    public tipoDescripcion?: string
  ) {}
}
