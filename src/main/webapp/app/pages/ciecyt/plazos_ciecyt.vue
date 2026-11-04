<template>
  <div>
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-3">
      <div>
        <h2 class="mb-1">Plazos y continuidad</h2>
        <p class="text-muted mb-0">
          Alertas globales del proceso: validacion, designaciones, actos, continuidad y vencimientos.
        </p>
      </div>
      <div class="d-flex gap-2">
        <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="cargando" @click="cargar">
          <font-awesome-icon icon="sync" :spin="cargando" /> Actualizar
        </button>
        <button type="button" class="btn btn-outline-warning btn-sm" :disabled="verificando" @click="verificarPlazos">
          <font-awesome-icon icon="bell" :spin="verificando" /> Verificar plazos
        </button>
      </div>
    </div>

    <div class="row g-2 mb-3">
      <div class="col-md-3">
        <div class="card"><div class="card-body py-2">
          <div class="small text-muted">Alertas activas</div>
          <div class="fs-4 fw-semibold">{{ alertas.length }}</div>
        </div></div>
      </div>
      <div class="col-md-3">
        <div class="card"><div class="card-body py-2">
          <div class="small text-muted">Vencidas</div>
          <div class="fs-4 fw-semibold text-danger">{{ vencidas }}</div>
        </div></div>
      </div>
      <div class="col-md-3">
        <div class="card"><div class="card-body py-2">
          <div class="small text-muted">Sin jurado</div>
          <div class="fs-4 fw-semibold">{{ sinJurado }}</div>
        </div></div>
      </div>
      <div class="col-md-3">
        <div class="card"><div class="card-body py-2">
          <div class="small text-muted">Actos pendientes</div>
          <div class="fs-4 fw-semibold">{{ actosPendientes }}</div>
        </div></div>
      </div>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>
    <div v-else-if="alertas.length === 0" class="alert alert-light border text-center text-muted">
      No hay alertas de plazo activas.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th>Proyecto</th>
            <th>Estado</th>
            <th>Alerta</th>
            <th>Detalle</th>
            <th>Plazo</th>
            <th class="text-end">Accion</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="alerta in alertasPaginadas" :key="alerta.proyecto.id + '-' + alerta.tipo">
            <td>
              <div class="fw-semibold">{{ alerta.proyecto.titulo }}</div>
              <div class="small text-muted">
                #{{ alerta.proyecto.id }}<span v-if="alerta.proyecto.programa"> Â· {{ alerta.proyecto.programa }}</span>
              </div>
            </td>
            <td class="small">{{ alerta.proyecto.estado }}</td>
            <td><span class="badge" :class="'bg-' + alerta.variant">{{ alerta.tipo }}</span></td>
            <td class="small">{{ alerta.detalle }}</td>
            <td><span class="badge" :class="'bg-' + alerta.variant">{{ alerta.plazo }}</span></td>
            <td class="text-end">
              <button type="button" class="btn btn-primary btn-sm" @click="gestionar(alerta.filtro)">
                Gestionar
              </button>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mt-3">
        <jhi-item-count :page="page" :total="alertas.length" :itemsPerPage="itemsPerPage"></jhi-item-count>
        <b-pagination size="sm" :total-rows="alertas.length" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import PlazoService from '@/entities/plazo/plazo.service';
import { IProyecto } from '@/shared/model/proyecto.model';
import { PLAZOS_ACUERDO_25, diasHabilesEntre, etiquetaPlazo, evaluarPlazo, variantePlazo } from '@/shared/composables/dias-habiles';
import { continuidadEtiqueta, resumenLimite } from '@/shared/utils/continuidad-proyecto';
import { requiereSustentacion, tieneJurado } from '@/shared/config/opcion_grado';

type AlertaPlazo = {
  proyecto: IProyecto;
  tipo: string;
  detalle: string;
  plazo: string;
  variant: string;
  filtro: string;
};

@Component
export default class PlazosCiecyt extends Vue {
  @Inject
  private proyectoService: () => ProyectoService;

  @Inject
  private plazoService: () => PlazoService;

  @Inject
  private alertService: () => AlertService;

  public proyectos: IProyecto[] = [];
  public cargando = false;
  public verificando = false;
  public page = 1;
  public itemsPerPage = 20;

  public mounted(): void {
    this.cargar();
  }

  public get alertas(): AlertaPlazo[] {
    const filas: AlertaPlazo[] = [];
    const agregar = (proyecto: IProyecto, tipo: string, detalle: string, plazo: string, variant: string, filtro: string) => {
      filas.push({ proyecto, tipo, detalle, plazo, variant, filtro });
    };

    for (const proyecto of this.proyectos) {
      const estado = String(proyecto.estado || '');
      if (estado === 'EN_VALIDACION_DOCUMENTAL') {
        agregar(proyecto, 'Validacion documental', 'Documentacion pendiente de revision', 'Sin fecha', 'warning', 'validar');
      }

      const jurados = this.integrantesConRol(proyecto, 'Jurado');
      if (tieneJurado(proyecto.proyectoModalidadId) && jurados.length === 0 && !!proyecto.enviado) {
        const evaluacion = evaluarPlazo(proyecto.fechaUltimoCambio, PLAZOS_ACUERDO_25.DESIGNACION_JURADO);
        agregar(proyecto, 'Designacion de jurado', 'Sin jurado designado', etiquetaPlazo(evaluacion), variantePlazo(evaluacion), 'jurado');
      }

      const asesores = this.integrantesConRol(proyecto, 'Asesor');
      if (asesores.length === 0 && !!proyecto.enviado) {
        agregar(proyecto, 'Designacion de asesor', 'Sin asesor designado', 'Sin fecha', 'warning', 'asesor');
      }

      if (estado === 'LISTO_PARA_SUSTENTAR' || estado === 'LISTO_PARA_SOCIALIZAR') {
        const tipoActo = requiereSustentacion(proyecto.proyectoModalidadId) ? 'Sustentacion' : 'Socializacion';
        agregar(proyecto, `Programar ${tipoActo.toLowerCase()}`, 'Acto publico pendiente de programar', 'Sin fecha', 'warning', 'acto');
      }

      if (estado === 'SUSTENTACION_PROGRAMADA' || estado === 'SOCIALIZACION_PROGRAMADA') {
        const fecha = proyecto.fechaSustentacionProyecto;
        const dias = diasHabilesEntre(new Date(), fecha);
        const plazo = dias == null ? 'Sin fecha' : dias < 0 ? `Vencido (${Math.abs(dias)} dias)` : `${dias} dias habiles`;
        const variant = dias != null && dias < 0 ? 'danger' : dias != null && dias <= 2 ? 'warning' : 'info';
        agregar(proyecto, 'Acto programado', fecha ? `Fecha: ${this.fechaCorta(fecha)}` : 'Sin fecha registrada', plazo, variant, 'acto');
      }

      const continuidad = resumenLimite(proyecto);
      if (continuidad.alLimite || continuidad.excedido) {
        agregar(
          proyecto,
          'Continuidad',
          continuidadEtiqueta(proyecto),
          `${continuidad.periodosUsados}/${continuidad.totalPeriodos} periodos`,
          continuidad.excedido ? 'danger' : 'warning',
          'continuidad'
        );
      }
    }

    const prioridad: { [key: string]: number } = { danger: 0, warning: 1, info: 2, secondary: 3, success: 4 };
    return filas.sort((a, b) => (prioridad[a.variant] ?? 9) - (prioridad[b.variant] ?? 9));
  }

  public get alertasPaginadas(): AlertaPlazo[] {
    const inicio = (this.page - 1) * this.itemsPerPage;
    return this.alertas.slice(inicio, inicio + this.itemsPerPage);
  }

  public get vencidas(): number {
    return this.alertas.filter(alerta => alerta.variant === 'danger').length;
  }

  public get sinJurado(): number {
    return this.alertas.filter(alerta => alerta.tipo === 'Designacion de jurado').length;
  }

  public get actosPendientes(): number {
    return this.alertas.filter(alerta => alerta.filtro === 'acto').length;
  }

  public cargar(): void {
    this.cargando = true;
    this.proyectoService()
      .retrieveAllProyectosIntegrantes()
      .then(res => {
        this.proyectos = res.data || [];
        this.cargando = false;
      })
      .catch(err => {
        this.proyectos = [];
        this.cargando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public verificarPlazos(): void {
    this.verificando = true;
    this.plazoService()
      .verificar()
      .then(res => {
        this.verificando = false;
        const creadas = res && res.notificacionesCreadas != null ? res.notificacionesCreadas : 0;
        this.alertService().showAlert(`Verificacion completada. Notificaciones nuevas: ${creadas}`, 'success');
      })
      .catch(err => {
        this.verificando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public gestionar(filtro: string): void {
    this.$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro } });
  }

  private integrantesConRol(proyecto: IProyecto, rol: string): any[] {
    return ((proyecto && proyecto.listaIntegrantesProyecto) || []).filter(integrante => {
      const rolIntegrante = integrante.integranteProyectoRolesModalidadRol || '';
      return rol === 'Asesor' ? rolIntegrante.indexOf('Asesor') >= 0 : rolIntegrante === rol;
    });
  }

  public fechaCorta(value: any): string {
    if (!value) {
      return 'â€”';
    }
    const texto = String(value);
    if (/^\d{4}-\d{2}-\d{2}/.test(texto)) {
      const partes = texto.substring(0, 10).split('-');
      return `${partes[2]}/${partes[1]}/${partes[0]}`;
    }
    const fecha = new Date(value);
    return isNaN(fecha.getTime()) ? 'â€”' : fecha.toLocaleDateString('es-CO');
  }
}
</script>

