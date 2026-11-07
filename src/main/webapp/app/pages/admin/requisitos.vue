<template>
  <div class="row">
    <div class="col-12">
      <h2 class="mb-1">Formatos de requisitos</h2>
      <p class="text-muted">
        Cargue aqui el formato institucional que el estudiante podra descargar en el checklist y en las pantallas de
        envio de propuesta. Formatos permitidos: PDF o Word, maximo 20 MB.
      </p>

      <div class="row mb-3">
        <div class="col-md-6 mb-2">
          <input v-model="filtro" type="text" class="form-control" placeholder="Buscar por codigo o nombre..." />
        </div>
        <div class="col-md-4 mb-2">
          <select v-model="modalidadFiltro" class="form-control">
            <option value="">Todas las modalidades</option>
            <option v-for="modalidad in modalidades" :key="modalidad" :value="modalidad">{{ modalidad }}</option>
          </select>
        </div>
        <div class="col-md-2 mb-2 text-right">
          <button type="button" class="btn btn-outline-secondary w-100" :disabled="cargando" @click="cargar">
            Actualizar
          </button>
        </div>
      </div>

      <div v-if="cargando" class="alert alert-info">Cargando requisitos...</div>
      <div v-else-if="requisitosFiltrados.length === 0" class="alert alert-info">No hay requisitos que coincidan.</div>
      <div v-else class="table-responsive">
        <table class="table table-sm table-bordered align-middle">
          <thead class="table-light">
            <tr>
              <th style="width: 16%">Codigo</th>
              <th style="width: 30%">Requisito</th>
              <th style="width: 14%">Modalidad</th>
              <th style="width: 18%">Formato actual</th>
              <th style="width: 22%">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="requisito in requisitosFiltrados" :key="requisito.id">
              <td><code>{{ requisito.codigo }}</code></td>
              <td>
                <div>{{ requisito.nombre }}</div>
                <small v-if="requisito.descripcion" class="text-muted">{{ requisito.descripcion }}</small>
              </td>
              <td>{{ requisito.requisitoModalidadModalidad || 'Comun' }}</td>
              <td>
                <span v-if="requisito.tieneFormato" class="text-success">{{ requisito.nombreArchivoFormato }}</span>
                <span v-else class="text-muted">Sin formato</span>
              </td>
              <td>
                <input
                  type="file"
                  class="form-control form-control-sm mb-2"
                  accept=".pdf,.doc,.docx"
                  :disabled="guardando[requisito.id]"
                  @change="subirFormato($event, requisito)"
                />
                <div v-if="requisito.tieneFormato" class="d-flex flex-wrap">
                  <button
                    type="button"
                    class="btn btn-sm btn-outline-primary mr-2 mb-1"
                    :disabled="guardando[requisito.id]"
                    @click="descargar(requisito)"
                  >
                    <font-awesome-icon icon="download"></font-awesome-icon>&nbsp;Descargar
                  </button>
                  <button
                    type="button"
                    class="btn btn-sm btn-outline-danger mb-1"
                    :disabled="guardando[requisito.id]"
                    @click="eliminar(requisito)"
                  >
                    <font-awesome-icon icon="trash"></font-awesome-icon>&nbsp;Eliminar
                  </button>
                </div>
                <small v-if="guardando[requisito.id]" class="text-muted d-block">Guardando...</small>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import RequisitoService from '@/entities/requisito/requisito.service';
import { IRequisito } from '@/shared/model/requisito.model';

@Component
export default class AdminRequisitos extends Vue {
  @Inject private requisitoService: () => RequisitoService;
  @Inject private alertService: () => AlertService;

  public requisitos: IRequisito[] = [];
  public cargando = true;
  public filtro = '';
  public modalidadFiltro = '';
  public guardando: { [id: number]: boolean } = {};

  get modalidades(): string[] {
    const valores: string[] = [];
    this.requisitos.forEach(requisito => {
      const modalidad = requisito.requisitoModalidadModalidad;
      if (modalidad && valores.indexOf(modalidad) === -1) {
        valores.push(modalidad);
      }
    });
    return valores.sort((a, b) => a.localeCompare(b));
  }

  get requisitosFiltrados(): IRequisito[] {
    const texto = this.filtro.trim().toLowerCase();
    return this.requisitos.filter(requisito => {
      const coincideModalidad =
        !this.modalidadFiltro || requisito.requisitoModalidadModalidad === this.modalidadFiltro;
      if (!coincideModalidad) {
        return false;
      }
      if (!texto) {
        return true;
      }
      const codigo = String(requisito.codigo || '').toLowerCase();
      const nombre = String(requisito.nombre || '').toLowerCase();
      return codigo.indexOf(texto) !== -1 || nombre.indexOf(texto) !== -1;
    });
  }

  mounted() {
    this.cargar();
  }

  async cargar() {
    this.cargando = true;
    try {
      const res = await this.requisitoService().retrieve();
      this.requisitos = (res.data && res.data.content) || res.data || [];
    } catch (err) {
      this.alertService().showHttpError(null, err);
    } finally {
      this.cargando = false;
    }
  }

  async subirFormato(event: Event, requisito: IRequisito) {
    const input = event.target as HTMLInputElement;
    const file = input && input.files ? input.files[0] : null;
    if (!file) {
      return;
    }
    if (file.size > 20 * 1024 * 1024) {
      this.alertService().showAlert('El formato no debe superar los 20 MB.', 'danger');
      input.value = '';
      return;
    }

    this.guardando[requisito.id] = true;
    try {
      const base64 = await this.leerBase64(file);
      requisito.archivoFormato = base64;
      requisito.archivoFormatoContentType = file.type || 'application/octet-stream';
      requisito.nombreArchivoFormato = file.name;
      const actualizado = await this.requisitoService().update(requisito);
      requisito.tieneFormato = true;
      requisito.nombreArchivoFormato = actualizado.nombreArchivoFormato || file.name;
      this.alertService().showAlert('Formato cargado correctamente.', 'success');
    } catch (err) {
      this.alertService().showHttpError(null, err);
    } finally {
      this.guardando[requisito.id] = false;
      input.value = '';
    }
  }

  descargar(requisito: IRequisito) {
    this.requisitoService().downloadFormato(requisito.id, requisito.nombreArchivoFormato);
  }

  async eliminar(requisito: IRequisito) {
    if (!window.confirm('¿Eliminar el formato de este requisito?')) {
      return;
    }
    this.guardando[requisito.id] = true;
    try {
      await this.requisitoService().deleteFormato(requisito.id);
      requisito.tieneFormato = false;
      requisito.nombreArchivoFormato = null;
      requisito.archivoFormato = null;
      requisito.archivoFormatoContentType = null;
      this.alertService().showAlert('Formato eliminado.', 'info');
    } catch (err) {
      this.alertService().showHttpError(null, err);
    } finally {
      this.guardando[requisito.id] = false;
    }
  }

  private leerBase64(file: File): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = () => {
        const resultado = String(reader.result || '');
        const base64 = resultado.indexOf('base64,') >= 0 ? resultado.substring(resultado.indexOf('base64,') + 7) : resultado;
        resolve(base64);
      };
      reader.onerror = reject;
      reader.readAsDataURL(file);
    });
  }
}
</script>

<style scoped></style>
