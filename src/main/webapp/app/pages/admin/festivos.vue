<template>
  <div>
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-4">
      <div>
        <h2 class="mb-1">Días no hábiles</h2>
        <p class="text-muted mb-0">Festivos y días excluidos del cálculo de plazos en días hábiles.</p>
      </div>
      <div class="d-flex flex-wrap gap-2">
        <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="descargandoPlantilla" @click="descargarPlantilla">
          <font-awesome-icon icon="download" /> Plantilla CSV
        </button>
        <button type="button" class="btn btn-outline-primary btn-sm" :disabled="importando" @click="abrirImportacion">
          <font-awesome-icon icon="file-alt" /> Importar CSV
        </button>
        <input id="festivo-archivo" type="file" accept=".csv,text/csv" class="d-none" @change="importarArchivo" />
      </div>
    </div>

    <div v-if="resultadoImportacion" class="alert" :class="erroresImportacion.length ? 'alert-warning' : 'alert-success'">
      <strong>Importación:</strong>
      {{ resultadoImportacion.total }} filas, {{ resultadoImportacion.creados }} creadas y
      {{ resultadoImportacion.actualizados }} actualizadas.
      <div v-if="erroresImportacion.length > 0" class="mt-2">
        <div class="small fw-semibold">Filas con error:</div>
        <ul class="small mb-0">
          <li v-for="error in erroresImportacion" :key="error.linea + '-' + error.mensaje">
            Línea {{ error.linea }}: {{ error.mensaje }}
          </li>
        </ul>
      </div>
    </div>

    <div class="card mb-3">
      <div class="card-body">
        <div class="row g-2 align-items-end">
          <div class="col-md-3">
            <label class="form-label small" for="festivo-fecha">Fecha</label>
            <input id="festivo-fecha" v-model="form.fecha" type="date" class="form-control form-control-sm" />
          </div>
          <div class="col-md-5">
            <label class="form-label small" for="festivo-descripcion">Descripción</label>
            <input id="festivo-descripcion" v-model="form.descripcion" class="form-control form-control-sm" />
          </div>
          <div class="col-md-2">
            <b-form-checkbox v-model="form.activo" class="mt-4">Activo</b-form-checkbox>
          </div>
          <div class="col-md-2 d-flex gap-2">
            <button type="button" class="btn btn-primary btn-sm" :disabled="guardando || !form.fecha || !form.descripcion" @click="guardar">
              {{ editandoId ? 'Actualizar' : 'Crear' }}
            </button>
            <button v-if="editandoId" type="button" class="btn btn-secondary btn-sm" @click="cancelar">Cancelar</button>
          </div>
        </div>
      </div>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>
    <div v-else-if="festivos.length === 0" class="alert alert-light border text-center text-muted">
      No hay días no hábiles registrados.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th>Fecha</th>
            <th>Descripción</th>
            <th>Estado</th>
            <th class="text-end">Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="festivo in festivos" :key="festivo.id">
            <td class="fw-semibold">{{ fechaCorta(festivo.fecha) }}</td>
            <td>{{ festivo.descripcion }}</td>
            <td>
              <span class="badge" :class="festivo.activo !== false ? 'bg-success' : 'bg-secondary'">
                {{ festivo.activo !== false ? 'Activo' : 'Inactivo' }}
              </span>
            </td>
            <td class="text-end">
              <div class="d-flex justify-content-end gap-2">
                <button type="button" class="btn btn-outline-secondary btn-sm" @click="editar(festivo)">Editar</button>
                <button type="button" class="btn btn-outline-danger btn-sm" @click="eliminar(festivo)">Eliminar</button>
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
import FestivoService from '@/entities/festivo/festivo.service';
import { IFestivo, Festivo, IResultadoImportacionFestivos } from '@/shared/model/festivo.model';

@Component
export default class Festivos extends Vue {
  @Inject
  private festivoService: () => FestivoService;

  @Inject
  private alertService: () => AlertService;

  public festivos: IFestivo[] = [];
  public form: any = { ...new Festivo(), fecha: '' };
  public editandoId: number | null = null;
  public cargando = false;
  public guardando = false;
  public importando = false;
  public descargandoPlantilla = false;
  public resultadoImportacion: IResultadoImportacionFestivos | null = null;

  public get erroresImportacion() {
    return this.resultadoImportacion?.errores || [];
  }

  public mounted(): void {
    this.cargar();
  }

  public abrirImportacion(): void {
    const input = document.getElementById('festivo-archivo') as HTMLInputElement | null;
    if (input) {
      input.click();
    }
  }

  public descargarPlantilla(): void {
    this.descargandoPlantilla = true;
    this.festivoService()
      .descargarPlantilla()
      .then(blob => {
        const url = URL.createObjectURL(blob);
        const enlace = document.createElement('a');
        enlace.href = url;
        enlace.download = 'plantilla-festivos.csv';
        document.body.appendChild(enlace);
        enlace.click();
        enlace.remove();
        URL.revokeObjectURL(url);
      })
      .catch(err => this.alertService().showHttpError(this, err.response))
      .finally(() => {
        this.descargandoPlantilla = false;
      });
  }

  public importarArchivo(event: Event): void {
    const input = event.target as HTMLInputElement;
    const archivo = input.files && input.files[0];
    if (!archivo) {
      return;
    }
    this.importando = true;
    this.resultadoImportacion = null;
    const lector = new FileReader();
    lector.onload = () => this.enviarImportacion(String(lector.result || ''), input);
    lector.onerror = () => {
      this.importando = false;
      input.value = '';
      this.alertService().showAlert('No se pudo leer el archivo CSV.', 'danger');
    };
    lector.readAsText(archivo, 'UTF-8');
  }

  private enviarImportacion(contenido: string, input: HTMLInputElement): void {
    this.festivoService()
      .importar(contenido)
      .then(resultado => {
        this.resultadoImportacion = resultado;
        const resumen = `${resultado.total} filas: ${resultado.creados} creadas, ${resultado.actualizados} actualizadas`;
        if (resultado.errores.length > 0) {
          this.alertService().showAlert(`${resumen}. Revise ${resultado.errores.length} error(es).`, 'warning');
        } else {
          this.alertService().showAlert(`${resumen}.`, 'success');
        }
        this.cargar();
      })
      .catch(err => this.alertService().showHttpError(this, err.response))
      .finally(() => {
        this.importando = false;
        input.value = '';
      });
  }

  public cargar(): void {
    this.cargando = true;
    this.festivoService()
      .retrieveAll()
      .then(lista => {
        this.festivos = lista || [];
        this.cargando = false;
      })
      .catch(err => {
        this.cargando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public editar(festivo: IFestivo): void {
    this.editandoId = festivo.id;
    this.form = { ...festivo, fecha: this.fechaInput(festivo.fecha) };
  }

  public cancelar(): void {
    this.editandoId = null;
    this.form = { ...new Festivo(), fecha: '' };
  }

  public guardar(): void {
    if (!this.form.fecha || !this.form.descripcion) {
      this.alertService().showAlert('Indique la fecha y la descripción.', 'warning');
      return;
    }
    this.guardando = true;
    const payload = { ...this.form, fecha: this.form.fecha || null, activo: this.form.activo !== false };
    const accion = this.editandoId
      ? this.festivoService().update({ ...payload, id: this.editandoId })
      : this.festivoService().create(payload);
    accion
      .then(() => {
        this.guardando = false;
        this.alertService().showAlert(this.editandoId ? 'Día no hábil actualizado' : 'Día no hábil creado', 'success');
        this.cancelar();
        this.cargar();
      })
      .catch(err => {
        this.guardando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public eliminar(festivo: IFestivo): void {
    if (!window.confirm(`¿Eliminar el día no hábil "${festivo.descripcion}"?`)) {
      return;
    }
    this.festivoService()
      .delete(festivo.id)
      .then(() => {
        this.alertService().showAlert('Día no hábil eliminado', 'success');
        this.cargar();
      })
      .catch(err => this.alertService().showHttpError(this, err.response));
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
