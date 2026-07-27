export interface IFichaTecnica {
  id?: number;
  tituloProfesional?: string;
  tituloPostgrado?: string;
  experiencia?: string;
  fichaTecnicaUserLogin?: string;
  fichaTecnicaUserId?: number;
}

export class FichaTecnica implements IFichaTecnica {
  constructor(
    public id?: number,
    public tituloProfesional?: string,
    public tituloPostgrado?: string,
    public experiencia?: string,
    public fichaTecnicaUserLogin?: string,
    public fichaTecnicaUserId?: number
  ) {}
}
