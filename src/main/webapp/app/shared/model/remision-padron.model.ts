export interface IRemisionPadronDocente {
  id?: number;
  rol?: string;
  nombreAlRemitir?: string;
  correoAlRemitir?: string;
}

export interface IRemisionPadron {
  id?: number;
  periodo?: string;
  estado?: string;
  fechaRemision?: string;
  fechaEnvio?: string;
  observaciones?: string;
  remitidoPorLogin?: string;
  remitidoPorNombre?: string;
  facultadId?: number;
  facultadNombre?: string;
  docentes?: IRemisionPadronDocente[];
}

export class RemisionPadron {
  constructor(
    public id?: number,
    public periodo?: string,
    public estado?: string,
    public fechaRemision?: string,
    public fechaEnvio?: string,
    public observaciones?: string,
    public remitidoPorLogin?: string,
    public remitidoPorNombre?: string,
    public facultadId?: number,
    public facultadNombre?: string,
    public docentes?: IRemisionPadronDocente[]
  ) {}
}

export class SolicitudRemision {
  constructor(public facultadId?: number, public periodo?: string, public observaciones?: string) {}
}
