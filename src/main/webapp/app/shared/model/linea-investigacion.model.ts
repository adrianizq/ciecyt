export interface ILineaInvestigacion {
  id?: number;
  linea?: string;
  codigoLinea?: string;
  lineaPadreLinea?: string;
  lineaPadreId?: number;
  lineaInvestigacionProgramaPrograma?: string;
  lineaInvestigacionProgramaId?: number;
  lineaInvestigacionFacultadId?: number;
}

export class LineaInvestigacion implements ILineaInvestigacion {
  constructor(
    public id?: number,
    public linea?: string,
    public codigoLinea?: string,
    public lineaPadreLinea?: string,
    public lineaPadreId?: number,
    public lineaInvestigacionProgramaPrograma?: string,
    public lineaInvestigacionProgramaId?: number
  ) {}
}
