import { IProyecto } from 'app/shared/model/proyecto.model';
import { IPreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';
import { ISolicitud } from 'app/shared/model/solicitud.model';
import { IUser } from 'app/core/user/user.model';
import { IRolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';

export interface IIntegrantesProyecto {
  id?: number;
  proyecto?: IProyecto;
  preguntaRespuestas?: IPreguntaRespuesta[];
  solicituds?: ISolicitud[];
  user?: IUser;
  rolesUsuarioProyecto?: IRolesUsuarioProyecto;
}

export class IntegrantesProyecto implements IIntegrantesProyecto {
  constructor(
    public id?: number,
    public proyecto?: IProyecto,
    public preguntaRespuestas?: IPreguntaRespuesta[],
    public solicituds?: ISolicitud[],
    public user?: IUser,
    public rolesUsuarioProyecto?: IRolesUsuarioProyecto
  ) {}
}
