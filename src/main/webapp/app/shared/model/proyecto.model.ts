import { Moment } from 'moment';
import { IEntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';
import { IElementosProyecto } from 'app/shared/model/elementos-proyecto.model';
import { IProductoProyecto } from 'app/shared/model/producto-proyecto.model';
import { IPresupuestoValor } from 'app/shared/model/presupuesto-valor.model';
import { IImpactosEsperados } from 'app/shared/model/impactos-esperados.model';
import { ICronograma } from 'app/shared/model/cronograma.model';
import { IResultadosEsperados } from 'app/shared/model/resultados-esperados.model';
import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';
import { ILineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';
import { IGrupoSemillero } from 'app/shared/model/grupo-semillero.model';
import { IFacultad } from 'app/shared/model/facultad.model';
import { IModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';

export interface IProyecto {
  id?: number;
  titulo?: string;
  url?: string;
  lugarEjecucion?: string;
  duracion?: string;
  fechaIni?: Moment;
  fechaFin?: Moment;
  contrapartidaPesos?: number;
  contrapartidaEspecie?: number;
  palabraClave?: string;
  convocatoria?: string;
  entidadFinanciadoras?: IEntidadFinanciadora[];
  elementosProyectos?: IElementosProyecto[];
  productoProyectos?: IProductoProyecto[];
  presupuestoValors?: IPresupuestoValor[];
  impactosEsperados?: IImpactosEsperados[];
  cronogramas?: ICronograma[];
  resultadosEsperados?: IResultadosEsperados[];
  integrantesProyectos?: IIntegrantesProyecto[];
  lineaDeInvestigacion?: ILineaDeInvestigacion;
  grupoSemillero?: IGrupoSemillero;
  facultad?: IFacultad;
  modalidadDeGrado?: IModalidadDeGrado;
}

export class Proyecto implements IProyecto {
  constructor(
    public id?: number,
    public titulo?: string,
    public url?: string,
    public lugarEjecucion?: string,
    public duracion?: string,
    public fechaIni?: Moment,
    public fechaFin?: Moment,
    public contrapartidaPesos?: number,
    public contrapartidaEspecie?: number,
    public palabraClave?: string,
    public convocatoria?: string,
    public entidadFinanciadoras?: IEntidadFinanciadora[],
    public elementosProyectos?: IElementosProyecto[],
    public productoProyectos?: IProductoProyecto[],
    public presupuestoValors?: IPresupuestoValor[],
    public impactosEsperados?: IImpactosEsperados[],
    public cronogramas?: ICronograma[],
    public resultadosEsperados?: IResultadosEsperados[],
    public integrantesProyectos?: IIntegrantesProyecto[],
    public lineaDeInvestigacion?: ILineaDeInvestigacion,
    public grupoSemillero?: IGrupoSemillero,
    public facultad?: IFacultad,
    public modalidadDeGrado?: IModalidadDeGrado
  ) {}
}
