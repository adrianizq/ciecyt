import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'proyecto',
        loadChildren: './proyecto/proyecto.module#CiecytProyectoModule'
      },
      {
        path: 'linea-de-investigacion',
        loadChildren: './linea-de-investigacion/linea-de-investigacion.module#CiecytLineaDeInvestigacionModule'
      },
      {
        path: 'grupo-semillero',
        loadChildren: './grupo-semillero/grupo-semillero.module#CiecytGrupoSemilleroModule'
      },
      {
        path: 'facultad',
        loadChildren: './facultad/facultad.module#CiecytFacultadModule'
      },
      {
        path: 'modalidad-de-grado',
        loadChildren: './modalidad-de-grado/modalidad-de-grado.module#CiecytModalidadDeGradoModule'
      },
      {
        path: 'categorizacion',
        loadChildren: './categorizacion/categorizacion.module#CiecytCategorizacionModule'
      },
      {
        path: 'empresas',
        loadChildren: './empresas/empresas.module#CiecytEmpresasModule'
      },
      {
        path: 'entidad-financiadora',
        loadChildren: './entidad-financiadora/entidad-financiadora.module#CiecytEntidadFinanciadoraModule'
      },
      {
        path: 'elementos',
        loadChildren: './elementos/elementos.module#CiecytElementosModule'
      },
      {
        path: 'elementos-proyecto',
        loadChildren: './elementos-proyecto/elementos-proyecto.module#CiecytElementosProyectoModule'
      },
      {
        path: 'producto',
        loadChildren: './producto/producto.module#CiecytProductoModule'
      },
      {
        path: 'producto-proyecto',
        loadChildren: './producto-proyecto/producto-proyecto.module#CiecytProductoProyectoModule'
      },
      {
        path: 'rubros',
        loadChildren: './rubros/rubros.module#CiecytRubrosModule'
      },
      {
        path: 'presupuesto-valor',
        loadChildren: './presupuesto-valor/presupuesto-valor.module#CiecytPresupuestoValorModule'
      },
      {
        path: 'impactos-esperados',
        loadChildren: './impactos-esperados/impactos-esperados.module#CiecytImpactosEsperadosModule'
      },
      {
        path: 'cronograma',
        loadChildren: './cronograma/cronograma.module#CiecytCronogramaModule'
      },
      {
        path: 'resultados-esperados',
        loadChildren: './resultados-esperados/resultados-esperados.module#CiecytResultadosEsperadosModule'
      },
      {
        path: 'preguntas',
        loadChildren: './preguntas/preguntas.module#CiecytPreguntasModule'
      },
      {
        path: 'fase-trabajo',
        loadChildren: './fase-trabajo/fase-trabajo.module#CiecytFaseTrabajoModule'
      },
      {
        path: 'pregunta-respuesta',
        loadChildren: './pregunta-respuesta/pregunta-respuesta.module#CiecytPreguntaRespuestaModule'
      },
      {
        path: 'tipo-pregunta',
        loadChildren: './tipo-pregunta/tipo-pregunta.module#CiecytTipoPreguntaModule'
      },
      {
        path: 'integrantes-proyecto',
        loadChildren: './integrantes-proyecto/integrantes-proyecto.module#CiecytIntegrantesProyectoModule'
      },
      {
        path: 'ficha-tecnica',
        loadChildren: './ficha-tecnica/ficha-tecnica.module#CiecytFichaTecnicaModule'
      },
      {
        path: 'solicitud',
        loadChildren: './solicitud/solicitud.module#CiecytSolicitudModule'
      },
      {
        path: 'roles-usuario-proyecto',
        loadChildren: './roles-usuario-proyecto/roles-usuario-proyecto.module#CiecytRolesUsuarioProyectoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytEntityModule {}
