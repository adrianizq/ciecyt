<template>
  <div>
    <div class="mb-4">
      <h2 class="mb-1">Ciclos académicos</h2>
      <p class="text-muted mb-0">Catálogo de ciclos usado por programas, proyectos y filtros de la bandeja.</p>
    </div>

    <div class="card mb-3">
      <div class="card-body">
        <div class="row g-2 align-items-end">
          <div class="col-md-6">
            <label class="form-label small" for="ciclo-nombre">Nombre del ciclo</label>
            <input id="ciclo-nombre" v-model="form.ciclo" class="form-control form-control-sm" maxlength="100" />
          </div>
          <div class="col-md-6 d-flex gap-2">
            <button type="button" class="btn btn-primary btn-sm" :disabled="guardando || !form.ciclo" @click="guardar">
              {{ editandoId ? 'Actualizar' : 'Crear' }}
            </button>
            <button v-if="editandoId" type="button" class="btn btn-secondary btn-sm" @click="cancelar">
              Cancelar
            </button>
            <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="cargando" @click="cargar">
              <font-awesome-icon icon="sync" :spin="cargando" /> Actualizar
            </button>
          </div>
          <div class="col-12">
            <label class="form-label small">Modalidades del ciclo</label>
            <div class="d-flex flex-wrap gap-3">
              <div v-for="modalidad in modalidades" :key="modalidad.id" class="form-check">
                <input
                  :id="'ciclo-modalidad-' + modalidad.id"
                  v-model="form.modalidadIds"
                  type="checkbox"
                  class="form-check-input"
                  :value="modalidad.id"
                />
                <label class="form-check-label small" :for="'ciclo-modalidad-' + modalidad.id">
                  {{ modalidad.modalidad }}
                </label>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>
    <div v-else-if="ciclos.length === 0" class="alert alert-light border text-center text-muted">
      No hay ciclos registrados.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th>ID</th>
            <th>Ciclo</th>
            <th>Modalidades</th>
            <th class="text-end">Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="ciclo in ciclos" :key="ciclo.id">
            <td>{{ ciclo.id }}</td>
            <td class="fw-semibold">{{ ciclo.ciclo }}</td>
            <td>
              <span v-for="nombre in ciclo.modalidadNombres || []" :key="nombre" class="badge bg-secondary me-1 mb-1">
                {{ nombre }}
              </span>
            </td>
            <td class="text-end">
              <div class="d-flex justify-content-end gap-2">
                <button type="button" class="btn btn-outline-secondary btn-sm" @click="editar(ciclo)">Editar</button>
                <button type="button" class="btn btn-outline-danger btn-sm" @click="eliminar(ciclo)">Eliminar</button>
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
import CicloService from '@/entities/ciclo/ciclo.service';
import ModalidadService from '@/entities/modalidad/modalidad.service';
import { Ciclo, ICiclo } from '@/shared/model/ciclo.model';
import { IModalidad } from '@/shared/model/modalidad.model';

@Component
export default class Ciclos extends Vue {
  @Inject
  private cicloService: () => CicloService;

  @Inject
  private modalidadService: () => ModalidadService;

  @Inject
  private alertService: () => AlertService;

  public ciclos: ICiclo[] = [];
  public modalidades: IModalidad[] = [];
  public form: any = { ...new Ciclo(), modalidadIds: [] };
  public editandoId: number | null = null;
  public cargando = false;
  public guardando = false;

  public mounted(): void {
    this.cargar();
    this.cargarModalidades();
  }

  public cargarModalidades(): void {
    this.modalidadService()
      .retrieve({ page: 0, size: 200, sort: ['modalidad,asc'] })
      .then(res => {
        this.modalidades = res.data || [];
      })
      .catch(() => {
        this.modalidades = [];
      });
  }

  public cargar(): void {
    this.cargando = true;
    this.cicloService()
      .retrieveAll()
      .then(lista => {
        this.ciclos = lista || [];
        this.cargando = false;
      })
      .catch(err => {
        this.cargando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public editar(ciclo: ICiclo): void {
    this.editandoId = ciclo.id;
    this.form = { ...ciclo, modalidadIds: ciclo.modalidadIds || [] };
  }

  public cancelar(): void {
    this.editandoId = null;
    this.form = { ...new Ciclo(), modalidadIds: [] };
  }

  public guardar(): void {
    if (!this.form.ciclo) {
      this.alertService().showAlert('Indique el nombre del ciclo.', 'warning');
      return;
    }
    this.guardando = true;
    const accion = this.editandoId
      ? this.cicloService().update({ ...this.form, id: this.editandoId })
      : this.cicloService().create(this.form);
    accion
      .then(() => {
        this.guardando = false;
        this.alertService().showAlert(this.editandoId ? 'Ciclo actualizado' : 'Ciclo creado', 'success');
        this.cancelar();
        this.cargar();
      })
      .catch(err => {
        this.guardando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public eliminar(ciclo: ICiclo): void {
    if (!window.confirm(`¿Eliminar el ciclo "${ciclo.ciclo}"?`)) {
      return;
    }
    this.cicloService()
      .delete(ciclo.id)
      .then(() => {
        this.alertService().showAlert('Ciclo eliminado', 'success');
        this.cargar();
      })
      .catch(err => {
        this.alertService().showHttpError(this, err.response);
      });
  }
}
</script>
