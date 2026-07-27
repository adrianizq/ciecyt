import { IIntegranteProyecto } from './integrante-proyecto.model';

export interface IProyecto {
  id?: number;
  titulo?: string;
  url?: string;
  lugarEjecucion?: string;
  duracion?: string;
  fechaIni?: Date;
  fechaFin?: Date;
  contrapartidaPesos?: number;
  contrapartidaEspecie?: number;
  departamento?: string;
  municipio?: string;
  palabrasClave?: string;
  convocatoria?: string;
  proyectoLineaInvestigacionLinea?: string;
  proyectoLineaInvestigacionId?: number;
  proyectoGrupoSemilleroNombre?: string;
  proyectoGrupoSemilleroId?: number;
  proyectoModalidadModalidad?: string;
  proyectoModalidadId?: number;
  facultadId?: number;
  subLineaLineaInvestigacionLinea?: string;
  subLineaLineaInvestigacionId?: number;
  programa?: string;
  proyectoProgramaId?: number;
  proyectoCicloId?: number;
  ciclo?: string;
  tipo?: string;
  referencias?: string;
  asesorId?: number;
  nota?: number;
  listaIntegrantesProyecto?: IIntegranteProyecto[];
  tieneJurado?: boolean;
  tieneJuradoViabilidad?: boolean;
  tieneAsesor?: boolean;
  enviado?: boolean;
  preEnviado?: boolean;
  proyectoEnviado?: boolean;
  fechaEnvioPropuesta?: Date;
  fechaEnvioProyecto?: Date;
  recomendaciones?: string;
  recomendacionesJuradoProyecto?: string;
  recomendacionesAsesorPropuesta?: string;
  recomendacionesAsesorProyecto?: string;
  recomendacionesJuradoSustentacion?: string;
  viable?: boolean;
  viabilidad?: string;
}

export class Proyecto implements IProyecto {
  constructor(
    public id?: number,
    public titulo?: string,
    public url?: string,
    public lugarEjecucion?: string,
    public duracion?: string,
    public fechaIni?: Date,
    public fechaFin?: Date,
    public contrapartidaPesos?: number,
    public contrapartidaEspecie?: number,
    public departamento?: string,
    public municipio?: string,
    public palabrasClave?: string,
    public convocatoria?: string,
    public proyectoLineaInvestigacionLinea?: string,
    public proyectoLineaInvestigacionId?: number,
    public proyectoGrupoSemilleroNombre?: string,
    public proyectoGrupoSemilleroId?: number,
    public proyectoModalidadModalidad?: string,
    public proyectoModalidadId?: number,
    public facultadId?: number,
    public subLineaLineaInvestigacionLinea?: string,
    public subLineaLineaInvestigacionId?: number,
    public tipo?: string,
    public referencias?: string,
    public asesorId?: number,
    public nota?: number,
    public proyectoProgramaId?: number,
    public programa?: string,
    public proyectoCicloId?: number,
    public ciclo?: string,
    public listaIntegrantesProyecto?: IIntegranteProyecto[],
    public tieneJurado?: boolean,
    public tieneJuradoViabilidad?: boolean,
    public tieneAsesor?: boolean,
    public enviado?: boolean,
    public preEnviado?: boolean,
    public proyectoEnviado?: boolean,
    public fechaEnvioPropuesta?: Date,
    public fechaEnvioProyecto?: Date,
    public recomendaciones?: string,
    public recomendacionesJuradoProyecto?: string,
    public recomendacionesAsesorPropuesta?: string,
    public recomendacionesAsesorProyecto?: string,
    public recomendacionesJuradoSustentacion?: string,
    public viable?: boolean,
    public viabilidad?: string
  ) {}
}
