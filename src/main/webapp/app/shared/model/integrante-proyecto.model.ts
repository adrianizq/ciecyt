export interface IIntegranteProyecto {
  id?: number;
  integrante?: string;
  descripcion?: string;
  integranteProyectoUserLogin?: string;
  integranteProyectoUserId?: number;
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
    public integranteProyectoProyectoTitulo?: string,
    public integranteProyectoProyectoId?: number,
    public integranteProyectoRolesModalidadRol?: string,
    public integranteProyectoRolesModalidadId?: number
  ) {}
}
