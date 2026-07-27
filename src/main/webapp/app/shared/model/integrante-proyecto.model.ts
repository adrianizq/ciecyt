export interface IIntegranteProyecto {
  id?: number;
  integrante?: string;
  descripcion?: string;
  integranteProyectoUserLogin?: string;
  integranteProyectoUserId?: number;
  integranteProyectoUserFirstName?: string;
  integranteProyectoUserLastName?: string;
  integranteProyectoProyectoTitulo?: string;
  integranteProyectoProyectoId?: number;
  integranteProyectoRolesModalidadRol?: string;
  integranteProyectoRolesModalidadId?: number;
}

export class IntegranteProyecto implements IIntegranteProyecto {
  constructor(
    public id?: number,
    public integrante?: string,
    public descripcion?: string,
    public integranteProyectoUserLogin?: string,
    public integranteProyectoUserId?: number,
    public integranteProyectoUserFirstName?: string,
    public integranteProyectoUserLastName?: string,
    public integranteProyectoProyectoTitulo?: string,
    public integranteProyectoProyectoId?: number,
    public integranteProyectoRolesModalidadRol?: string,
    public integranteProyectoRolesModalidadId?: number
  ) {}
}
