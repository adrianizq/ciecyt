export interface IUsuario {
  id?: number;
  usuario?: string;
  descripcion?: string;
}

export class Usuario implements IUsuario {
  constructor(public id?: number, public usuario?: string, public descripcion?: string) {}
}
