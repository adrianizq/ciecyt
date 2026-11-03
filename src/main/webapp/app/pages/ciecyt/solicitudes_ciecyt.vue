<template>
  <div>
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-3">
      <div>
        <h2 class="mb-1">Solicitudes y novedades</h2>
        <p class="text-muted mb-0">Bandeja global de solicitudes radicadas por los proyectos.</p>
      </div>
      <div class="d-flex gap-2">
        <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="cargando" @click="cargar">
          <font-awesome-icon icon="sync" :spin="cargando" /> Actualizar
        </button>
      </div>
    </div>

    <div class="card mb-3">
      <div class="card-body">
        <div class="row g-2 align-items-end">
          <div class="col-md-6">
            <label class="form-label small mb-1" for="buscar-solicitud">Buscar</label>
            <input
              id="buscar-solicitud"
              v-model="busqueda"
              type="search"
              class="form-control form-control-sm"
              placeholder="Asunto, proyecto, integrante o texto"
            />
          </div>
          <div class="col-md-3">
            <label class="form-label small mb-1" for="estado-solicitud">Estado</label>
            <select id="estado-solicitud" v-model="filtro" class="form-select form-select-sm">
              <option value="pendientes">Pendientes</option>
              <option value="resueltas">Resueltas</option>
              <option value="todas">Todas</option>
            </select>
          </div>
          <div class="col-md-3 text-md-end">
            <span class="small text-muted">{{ filtradas.length }} de {{ solicitudes.length }} solicitudes</span>
          </div>
        </div>
        <div class="d-flex flex-wrap gap-3 mt-3 small text-muted">
          <span><strong class="text-dark">{{ pendientes }}</strong> pendientes</span>
          <span><strong class="text-dark">{{ resueltas }}</strong> resueltas</span>
        </div>
      </div>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>
    <div v-else-if="filtradas.length === 0" class="alert alert-light border text-center text-muted">
      No hay solicitudes en este filtro.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th>Fecha</th>
            <th>Asunto</th>
            <th>Proyecto</th>
            <th>Integrante</th>
            <th>Texto</th>
            <th>Estado</th>
            <th class="text-end">Acción</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="solicitud in filtradas" :key="solicitud.id">
            <td class="small">{{ fechaCorta(solicitud.fechaSolicitud) }}</td>
            <td class="fw-semibold">{{ solicitud.asunto || 'Sin asunto' }}</td>
            <td>
              <div>{{ solicitud.proyectoTitulo || '—' }}</div>
              <div v-if="solicitud.proyectoId" class="small text-muted">#{{ solicitud.proyectoId }}</div>
            </td>
            <td>
              <div>{{ solicitud.solicitudIntegranteProyectoIntegrante || '—' }}</div>
              <div v-if="solicitud.usuarioLogin" class="small text-muted">{{ solicitud.usuarioLogin }}</div>
            </td>
            <td class="small" style="max-width: 28rem">{{ solicitud.textoSolicitud || '—' }}</td>
            <td>
              <span class="badge" :class="solicitud.estado ? 'bg-success' : 'bg-warning text-dark'">
                {{ solicitud.estado ? 'Resuelta' : 'Pendiente' }}
              </span>
            </td>
            <td class="text-end">
              <button
                v-if="!solicitud.estado"
                type="button"
                class="btn btn-outline-success btn-sm"
                :disabled="guardandoId === solicitud.id"
                @click="marcarResuelta(solicitud)"
              >
                Marcar resuelta
              </button>
              <span v-else class="small text-muted">Cerrada</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import SolicitudService from '@/entities/solicitud/solicitud.service';
import { ISolicitud } from '@/shared/model/solicitud.model';

@Component
export default class SolicitudesCiecyt extends Vue {
  @Inject
  private solicitudService: () => SolicitudService;

  @Inject
  private alertService: () => AlertService;

  public solicitudes: ISolicitud[] = [];
  public cargando = false;
  public guardandoId: number | null = null;
  public busqueda = '';
  public filtro = 'pendientes';

  public mounted(): void {
    this.cargar();
  }

  public get pendientes(): number {
    return this.solicitudes.filter(solicitud => !solicitud.estado).length;
  }

  public get resueltas(): number {
    return this.solicitudes.filter(solicitud => !!solicitud.estado).length;
  }

  public get filtradas(): ISolicitud[] {
    const texto = (this.busqueda || '').trim().toLowerCase();
    return this.solicitudes.filter(solicitud => {
      if (this.filtro === 'pendientes' && solicitud.estado) {
        return false;
      }
      if (this.filtro === 'resueltas' && !solicitud.estado) {
        return false;
      }
      if (!texto) {
        return true;
      }
      return [
        solicitud.asunto,
        solicitud.textoSolicitud,
        solicitud.proyectoTitulo,
        solicitud.solicitudIntegranteProyectoIntegrante,
        solicitud.usuarioLogin,
      ].some(valor => (valor || '').toLowerCase().includes(texto));
    });
  }

  public cargar(): void {
    this.cargando = true;
    this.solicitudService()
      .retrieve({ page: 0, size: 500, sort: ['fechaSolicitud,desc', 'id,desc'] })
      .then(res => {
        this.solicitudes = res.data || [];
        this.cargando = false;
      })
      .catch(err => {
        this.cargando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public marcarResuelta(solicitud: ISolicitud): void {
    this.guardandoId = solicitud.id;
    const actualizada = { ...solicitud, estado: true };
    this.solicitudService()
      .update(actualizada)
      .then(() => {
        solicitud.estado = true;
        this.guardandoId = null;
        this.alertService().showAlert('Solicitud marcada como resuelta', 'success');
      })
      .catch(err => {
        this.guardandoId = null;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public fechaCorta(value: any): string {
    if (!value) {
      return '—';
    }
    const texto = String(value);
    if (/^\d{4}-\d{2}-\d{2}/.test(texto)) {
      const partes = texto.substring(0, 10).split('-');
      return `${partes[2]}/${partes[1]}/${partes[0]}`;
    }
    const fecha = new Date(value);
    return isNaN(fecha.getTime()) ? '—' : fecha.toLocaleDateString('es-CO');
  }
}
</script>
