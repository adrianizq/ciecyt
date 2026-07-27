export interface ICronogramaCiecyt {
  id?: number;
  tituloCronograma?: string;
  fechaInicio?: Date;
  fechaFin?: Date;
  observaciones?: string;
  cronogramaCiecytModalidadModalidad?: string;
  cronogramaCiecytModalidadId?: number;
}

export class CronogramaCiecyt implements ICronogramaCiecyt {
  constructor(
    public id?: number,
    public tituloCronograma?: string,
    public fechaInicio?: Date,
    public fechaFin?: Date,
    public observaciones?: string,
    public cronogramaCiecytModalidadModalidad?: string,
    public cronogramaCiecytModalidadId?: number
  ) {}
}
