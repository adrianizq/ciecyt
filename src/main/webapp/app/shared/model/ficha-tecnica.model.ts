import { IUser } from 'app/core/user/user.model';

export interface IFichaTecnica {
  id?: number;
  tituloProfecional?: string;
  tituloPosgrado?: string;
  experencia?: string;
  user?: IUser;
}

export class FichaTecnica implements IFichaTecnica {
  constructor(
    public id?: number,
    public tituloProfecional?: string,
    public tituloPosgrado?: string,
    public experencia?: string,
    public user?: IUser
  ) {}
}
