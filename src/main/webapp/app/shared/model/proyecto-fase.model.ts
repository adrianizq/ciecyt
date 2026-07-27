export interface IProyectoFase {
  id?: number;
  titulo?: string;
  cumplida?: boolean;
  fechaCumplimiento?: Date;
  observaciones?: string;
  proyectoFaseFasesFase?: string;
  proyectoFaseFasesId?: number;
  proyectoFaseProyectoTitulo?: string;
  proyectoFaseProyectoId?: number;
}

export class ProyectoFase implements IProyectoFase {
  constructor(
    public id?: number,
    public titulo?: string,
    public cumplida?: boolean,
    public fechaCumplimiento?: Date,
    public observaciones?: string,
    public proyectoFaseFasesFase?: string,
    public proyectoFaseFasesId?: number,
    public proyectoFaseProyectoTitulo?: string,
    public proyectoFaseProyectoId?: number
  ) {
    this.cumplida = this.cumplida || false;
  }
}
