export interface ICronograma {
  id?: number;
  actividad?: string;
  duracion?: number;
  fechaInicio?: Date;
  fechaFin?: Date;
  ordenVista?: number;
  cronogramaProyectoTitulo?: string;
  cronogramaProyectoId?: number;
}

export class Cronograma implements ICronograma {
  constructor(
    public id?: number,
    public actividad?: string,
    public duracion?: number,
    public fechaInicio?: Date,
    public fechaFin?: Date,
    public ordenVista?: number,
    public cronogramaProyectoTitulo?: string,
    public cronogramaProyectoId?: number
  ) {}
}
