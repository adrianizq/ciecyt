export interface IRolesModalidad {
  id?: number;
  rol?: string;
  cantidad?: number;
  calificador?: boolean;
  rolesModalidadModalidadModalidad?: string;
  rolesModalidadModalidadId?: number;
  //rolesModalidadAuthority?: string,
  rolesModalidadAuthorityName?: string;
}

export class RolesModalidad implements IRolesModalidad {
  constructor(
    public id?: number,
    public rol?: string,
    public cantidad?: number,
    public calificador?: boolean,
    public rolesModalidadModalidadModalidad?: string,
    public rolesModalidadModalidadId?: number,
    //public rolesModalidadAuthority?: string,
    public rolesModalidadAuthorityName?: string
  ) {
    this.calificador = this.calificador || false;
  }
}
