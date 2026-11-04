<template>
  <div>
    <div class="mb-4">
      <h2 class="mb-1">Periodos académicos</h2>
      <p class="text-muted mb-0">Catálogo de periodos usado por la remisión del padrón y los procesos de seguimiento.</p>
    </div>

    <div class="card mb-3">
      <div class="card-body">
        <div class="row g-2 align-items-end">
          <div class="col-md-3">
            <label class="form-label small" for="periodo-nombre">Periodo</label>
            <input id="periodo-nombre" v-model="form.nombre" class="form-control form-control-sm" placeholder="2026-1" />
          </div>
          <div class="col-md-2">
            <label class="form-label small" for="periodo-inicio">Inicio</label>
            <input id="periodo-inicio" v-model="form.fechaInicio" type="date" class="form-control form-control-sm" />
          </div>
          <div class="col-md-2">
            <label class="form-label small" for="periodo-fin">Fin</label>
            <input id="periodo-fin" v-model="form.fechaFin" type="date" class="form-control form-control-sm" />
          </div>
          <div class="col-md-2">
            <b-form-checkbox v-model="form.activo" class="mt-4">Activo</b-form-checkbox>
          </div>
          <div class="col-md-3 d-flex gap-2">
            <button type="button" class="btn btn-primary btn-sm" :disabled="guardando || !form.nombre" @click="guardar">
              {{ editandoId ? 'Actualizar' : 'Crear' }}
            </button>
            <button v-if="editandoId" type="button" class="btn btn-secondary btn-sm" @click="cancelar">Cancelar</button>
            <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="cargando" @click="cargar">
              <font-awesome-icon icon="sync" :spin="cargando" /> Actualizar
            </button>
          </div>
        </div>
      </div>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>
    <div v-else-if="periodos.length === 0" class="alert alert-light border text-center text-muted">
      No hay periodos registrados.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th>Periodo</th>
            <th>Inicio</th>
            <th>Fin</th>
            <th>Estado</th>
            <th class="text-end">Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="periodo in periodos" :key="periodo.id">
            <td class="fw-semibold">{{ periodo.nombre }}</td>
            <td>{{ fechaCorta(periodo.fechaInicio) }}</td>
            <td>{{ fechaCorta(periodo.fechaFin) }}</td>
            <td>
              <span class="badge" :class="periodo.activo ? 'bg-success' : 'bg-secondary'">
                {{ periodo.activo ? 'Activo' : 'Inactivo' }}
              </span>
            </td>
            <td class="text-end">
              <div class="d-flex justify-content-end gap-2">
                <button type="button" class="btn btn-outline-secondary btn-sm" @click="editar(periodo)">Editar</button>
                <button type="button" class="btn btn-outline-danger btn-sm" @click="eliminar(periodo)">Eliminar</button>
              </div>
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
import PeriodoService from '@/entities/periodo/periodo.service';
import { IPeriodo, Periodo } from '@/shared/model/periodo.model';

@Component
export default class Periodos extends Vue {
  @Inject
  private periodoService: () => PeriodoService;

  @Inject
  private alertService: () => AlertService;

  public periodos: IPeriodo[] = [];
  public form: any = new Periodo();
  public editandoId: number | null = null;
  public cargando = false;
  public guardando = false;

  public mounted(): void {
    this.cargar();
  }

  public cargar(): void {
    this.cargando = true;
    this.periodoService()
      .retrieveAll()
      .then(lista => {
        this.periodos = lista || [];
        this.cargando = false;
      })
      .catch(err => {
        this.cargando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public editar(periodo: IPeriodo): void {
    this.editandoId = periodo.id;
    this.form = {
      ...periodo,
      fechaInicio: this.fechaInput(periodo.fechaInicio),
      fechaFin: this.fechaInput(periodo.fechaFin),
    };
  }

  public cancelar(): void {
    this.editandoId = null;
    this.form = new Periodo();
  }

  public guardar(): void {
    if (!this.form.nombre) {
      this.alertService().showAlert('Indique el nombre del periodo.', 'warning');
      return;
    }
    this.guardando = true;
    const payload = {
      ...this.form,
      fechaInicio: this.form.fechaInicio || null,
      fechaFin: this.form.fechaFin || null,
      activo: this.form.activo !== false,
    };
    const accion = this.editandoId
      ? this.periodoService().update({ ...payload, id: this.editandoId })
      : this.periodoService().create(payload);
    accion
      .then(() => {
        this.guardando = false;
        this.alertService().showAlert(this.editandoId ? 'Periodo actualizado' : 'Periodo creado', 'success');
        this.cancelar();
        this.cargar();
      })
      .catch(err => {
        this.guardando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public eliminar(periodo: IPeriodo): void {
    if (!window.confirm(`¿Eliminar el periodo "${periodo.nombre}"?`)) {
      return;
    }
    this.periodoService()
      .delete(periodo.id)
      .then(() => {
        this.alertService().showAlert('Periodo eliminado', 'success');
        this.cargar();
      })
      .catch(err => {
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
    return texto;
  }

  private fechaInput(value: any): string {
    return value ? String(value).substring(0, 10) : '';
  }
}
</script>
