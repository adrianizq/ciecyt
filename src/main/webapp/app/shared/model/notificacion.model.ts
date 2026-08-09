export interface INotificacion {
  id?: number;
  titulo?: string;
  mensaje?: string;
  tipo?: string;
  leido?: boolean;
  fechaCreacion?: Date;
  fechaLectura?: Date;
  userId?: number;
  userLogin?: string;
  proyectoId?: number;
  proyectoTitulo?: string;
}

export class Notificacion implements INotificacion {
  constructor(
    public id?: number,
    public titulo?: string,
    public mensaje?: string,
    public tipo?: string,
    public leido?: boolean,
    public fechaCreacion?: Date,
    public fechaLectura?: Date,
    public userId?: number,
    public userLogin?: string,
    public proyectoId?: number,
    public proyectoTitulo?: string
  ) {}
}
