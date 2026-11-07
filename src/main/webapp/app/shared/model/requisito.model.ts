export interface IRequisito {
  id?: number;
  codigo?: string;
  nombre?: string;
  descripcion?: string;
  tipo?: string;
  obligatorio?: boolean;
  activo?: boolean;
  aceptaTipos?: string;
  archivoFormato?: string;
  archivoFormatoContentType?: string;
  nombreArchivoFormato?: string;
  tieneFormato?: boolean;
  requisitoModalidadId?: number;
  requisitoModalidadModalidad?: string;
  requisitoEstado?: string;
}

export class Requisito implements IRequisito {
  constructor(
    public id?: number,
    public codigo?: string,
    public nombre?: string,
    public descripcion?: string,
    public tipo?: string,
    public obligatorio?: boolean,
    public activo?: boolean,
    public aceptaTipos?: string,
    public archivoFormato?: string,
    public archivoFormatoContentType?: string,
    public nombreArchivoFormato?: string,
    public tieneFormato?: boolean,
    public requisitoModalidadId?: number,
    public requisitoModalidadModalidad?: string,
    public requisitoEstado?: string
  ) {}
}
