export interface ICategorizacion {
  id?: number;
  categoria?: string;
  descripcion?: string;
  categorizacionProyectoTitulo?: string;
  categorizacionProyectoId?: number;
}

export class Categorizacion implements ICategorizacion {
  constructor(
    public id?: number,
    public categoria?: string,
    public descripcion?: string,
    public categorizacionProyectoTitulo?: string,
    public categorizacionProyectoId?: number
  ) {}
}
