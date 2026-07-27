export interface ICronogramaCiecytFases {
  id?: number;
  inicioFase?: Date;
  finFase?: Date;
  textoExplicativo?: string;
  cronogramaCiecytFasesCronogramaCiecytTituloCronograma?: string;
  cronogramaCiecytFasesCronogramaCiecytId?: number;
  cronogramaCiecytFasesFasesFase?: string;
  cronogramaCiecytFasesFasesId?: number;
}

export class CronogramaCiecytFases implements ICronogramaCiecytFases {
  constructor(
    public id?: number,
    public inicioFase?: Date,
    public finFase?: Date,
    public textoExplicativo?: string,
    public cronogramaCiecytFasesCronogramaCiecytTituloCronograma?: string,
    public cronogramaCiecytFasesCronogramaCiecytId?: number,
    public cronogramaCiecytFasesFasesFase?: string,
    public cronogramaCiecytFasesFasesId?: number
  ) {}
}
