<template>
  <div>
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-3">
      <div>
        <h2 class="mb-1">Asesores externos</h2>
        <p class="text-muted mb-0">
          Registro y verificacion de idoneidad de profesionales externos por facultad (Acuerdo 025, art. 8 y 9).
        </p>
      </div>
      <button type="button" class="btn btn-primary btn-sm" @click="nuevo">
        <font-awesome-icon icon="plus" /> Registrar externo
      </button>
    </div>

    <div class="card mb-3">
      <div class="card-body">
        <div class="row g-2 align-items-end">
          <div class="col-md-6">
            <label class="form-label small mb-1" for="facultad-externo">Facultad</label>
            <select id="facultad-externo" v-model.number="facultadId" class="form-select form-select-sm" @change="cargar">
              <option :value="null">Seleccione una facultad</option>
              <option v-for="facultad in facultades" :key="facultad.id" :value="facultad.id">
                {{ facultad.facultad }}
              </option>
            </select>
          </div>
          <div class="col-md-3">
            <label class="form-label small mb-1" for="rol-externo">Rol</label>
            <select id="rol-externo" v-model="rolFiltro" class="form-select form-select-sm" @change="cargar">
              <option value="">Todos</option>
              <option v-for="rol in roles" :key="rol" :value="rol">{{ rol }}</option>
            </select>
          </div>
          <div class="col-md-3 text-md-end">
            <span class="small text-muted">{{ externosFiltrados.length }} de {{ externos.length }} registros</span>
          </div>
        </div>
      </div>
    </div>

    <div v-if="mostrarFormulario" class="card mb-3">
      <div class="card-body">
        <h5 class="mb-3">Registrar profesional externo</h5>
        <div class="row g-2">
          <div class="col-md-4">
            <label class="form-label small">Nombres</label>
            <input v-model="form.nombres" class="form-control form-control-sm" />
          </div>
          <div class="col-md-4">
            <label class="form-label small">Apellidos</label>
            <input v-model="form.apellidos" class="form-control form-control-sm" />
          </div>
          <div class="col-md-4">
            <label class="form-label small">Documento</label>
            <input v-model="form.numeroDocumento" class="form-control form-control-sm" />
          </div>
          <div class="col-md-4">
            <label class="form-label small">Correo</label>
            <input v-model="form.correoElectronico" type="email" class="form-control form-control-sm" />
          </div>
          <div class="col-md-4">
            <label class="form-label small">Telefono</label>
            <input v-model="form.telefono" class="form-control form-control-sm" />
          </div>
          <div class="col-md-4">
            <label class="form-label small">Rol</label>
            <select v-model="form.rol" class="form-select form-select-sm">
              <option v-for="rol in roles" :key="rol" :value="rol">{{ rol }}</option>
            </select>
          </div>
          <div class="col-md-6">
            <label class="form-label small">Titulos academicos</label>
            <input v-model="form.titulos" class="form-control form-control-sm" />
          </div>
          <div class="col-md-6">
            <label class="form-label small">Institucion de origen</label>
            <input v-model="form.institucionOrigen" class="form-control form-control-sm" />
          </div>
          <div class="col-md-6">
            <label class="form-label small">Fuente de verificacion</label>
            <input v-model="form.fuenteVerificacion" class="form-control form-control-sm" />
          </div>
          <div class="col-md-6">
            <label class="form-label small">Observaciones</label>
            <input v-model="form.observaciones" class="form-control form-control-sm" />
          </div>
        </div>
        <div class="mt-3 d-flex gap-2">
          <button type="button" class="btn btn-primary btn-sm" :disabled="guardando" @click="guardar">Registrar</button>
          <button type="button" class="btn btn-secondary btn-sm" @click="mostrarFormulario = false">Cancelar</button>
        </div>
      </div>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>
    <div v-else-if="!facultadId" class="alert alert-light border text-center text-muted">
      Seleccione una facultad para consultar sus profesionales externos.
    </div>
    <div v-else-if="externosFiltrados.length === 0" class="alert alert-light border text-center text-muted">
      No hay profesionales externos registrados para este filtro.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th>Profesional</th>
            <th>Documento</th>
            <th>Rol</th>
            <th>Institucion</th>
            <th>Contacto</th>
            <th>Estado</th>
            <th>Idoneidad</th>
            <th class="text-end">Accion</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="externo in externosFiltrados" :key="externo.id">
            <td class="fw-semibold">{{ nombre(externo) }}</td>
            <td>{{ externo.numeroDocumento || '—' }}</td>
            <td>{{ externo.rol || '—' }}</td>
            <td>{{ externo.institucionOrigen || '—' }}</td>
            <td class="small">
              <div>{{ externo.correoElectronico || '—' }}</div>
              <div class="text-muted">{{ externo.telefono || '' }}</div>
            </td>
            <td>
              <span class="badge" :class="externo.activo !== false ? 'bg-success' : 'bg-secondary'">
                {{ externo.activo !== false ? 'Activo' : 'Inactivo' }}
              </span>
            </td>
            <td>
              <span class="badge" :class="externo.idoneidadVerificada ? 'bg-success' : 'bg-warning text-dark'">
                {{ externo.idoneidadVerificada ? 'Verificada' : 'Pendiente' }}
              </span>
              <div v-if="externo.fechaVerificacionIdoneidad" class="small text-muted">
                {{ fechaCorta(externo.fechaVerificacionIdoneidad) }}
              </div>
            </td>
            <td class="text-end">
              <div class="d-flex flex-wrap justify-content-end gap-2">
                <button type="button" class="btn btn-outline-secondary btn-sm" @click="editar(externo)">Editar</button>
                <button
                  v-if="!externo.idoneidadVerificada"
                  type="button"
                  class="btn btn-outline-success btn-sm"
                  :disabled="verificandoId === externo.id"
                  @click="verificar(externo)"
                >
                  Verificar
                </button>
                <button
                  v-if="externo.activo !== false"
                  type="button"
                  class="btn btn-outline-danger btn-sm"
                  @click="desactivar(externo)"
                >
                  Desactivar
                </button>
                <button v-else type="button" class="btn btn-outline-success btn-sm" @click="activar(externo)">
                  Activar
                </button>
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
import FacultadService from '@/entities/facultad/facultad.service';
import AsesorExternoService from '@/entities/asesor-externo/asesor-externo.service';
import { IFacultad } from '@/shared/model/facultad.model';
import { IAsesorExterno, SolicitudAsesorExterno } from '@/shared/model/asesor-externo.model';

@Component
export default class AsesoresExternosCiecyt extends Vue {
  @Inject
  private facultadService: () => FacultadService;

  @Inject
  private asesorExternoService: () => AsesorExternoService;

  @Inject
  private alertService: () => AlertService;

  public facultades: IFacultad[] = [];
  public externos: IAsesorExterno[] = [];
  public facultadId: number | null = null;
  public rolFiltro = '';
  public cargando = false;
  public guardando = false;
  public verificandoId: number | null = null;
  public editandoId: number | null = null;
  public mostrarFormulario = false;
  public roles = ['Asesor', 'Jurado', 'Asesor Internacional', 'Coordinador'];
  public form: any = new SolicitudAsesorExterno();

  public mounted(): void {
    this.cargarFacultades();
  }

  public get externosFiltrados(): IAsesorExterno[] {
    if (!this.rolFiltro) {
      return this.externos;
    }
    return this.externos.filter(
      externo => (externo.rol || '').toUpperCase() === this.rolFiltro.toUpperCase()
    );
  }

  public cargarFacultades(): void {
    this.facultadService()
      .retrieve({ page: 0, size: 100, sort: ['facultad,asc'] })
      .then(res => {
        this.facultades = res.data || [];
      })
      .catch(err => {
        this.facultades = [];
        this.alertService().showHttpError(this, err.response);
      });
  }

  public nuevo(): void {
    this.editandoId = null;
    this.form = new SolicitudAsesorExterno();
    this.form.rol = 'Asesor';
    this.mostrarFormulario = true;
  }

  public editar(externo: IAsesorExterno): void {
    this.editandoId = externo.id;
    this.form = { ...externo };
    this.mostrarFormulario = true;
  }

  public cargar(): void {
    if (!this.facultadId) {
      this.externos = [];
      return;
    }
    this.cargando = true;
    this.asesorExternoService()
      .retrieveDeFacultad(this.facultadId)
      .then(res => {
        this.externos = res.data || [];
        this.cargando = false;
      })
      .catch(err => {
        this.externos = [];
        this.cargando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public guardar(): void {
    if (!this.facultadId) {
      this.alertService().showAlert('Seleccione la facultad del profesional externo.', 'warning');
      return;
    }
    if (!this.form.nombres || !this.form.apellidos || !this.form.numeroDocumento || !this.form.rol) {
      this.alertService().showAlert('Nombres, apellidos, documento y rol son obligatorios.', 'warning');
      return;
    }
    this.guardando = true;
    const solicitud = new SolicitudAsesorExterno(
      this.facultadId,
      this.form.nombres,
      this.form.apellidos,
      this.form.numeroDocumento,
      this.form.correoElectronico,
      this.form.telefono,
      this.form.titulos,
      this.form.institucionOrigen,
      this.form.rol,
      this.form.fuenteVerificacion,
      this.form.observaciones
    );
    const editando = !!this.editandoId;
    const accion = this.editandoId
      ? this.asesorExternoService().actualizar(this.editandoId, this.form)
      : this.asesorExternoService().registrar(solicitud);
    accion
      .then(() => {
        this.guardando = false;
        this.mostrarFormulario = false;
        this.editandoId = null;
        this.form = new SolicitudAsesorExterno();
        this.alertService().showAlert(
          editando ? 'Profesional externo actualizado' : 'Profesional externo registrado. Verifique su idoneidad para poder designarlo.',
          'success'
        );
        this.cargar();
      })
      .catch(err => {
        this.guardando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public activar(externo: IAsesorExterno): void {
    this.asesorExternoService()
      .activar(externo.id)
      .then(() => {
        this.alertService().showAlert('Profesional externo activado', 'success');
        this.cargar();
      })
      .catch(err => this.alertService().showHttpError(this, err.response));
  }

  public desactivar(externo: IAsesorExterno): void {
    if (!window.confirm(`¿Desactivar a ${this.nombre(externo)}?`)) {
      return;
    }
    this.asesorExternoService()
      .desactivar(externo.id)
      .then(() => {
        this.alertService().showAlert('Profesional externo desactivado', 'success');
        this.cargar();
      })
      .catch(err => this.alertService().showHttpError(this, err.response));
  }

  public verificar(externo: IAsesorExterno): void {
    this.verificandoId = externo.id;
    this.asesorExternoService()
      .verificar(externo.id)
      .then(() => {
        this.verificandoId = null;
        this.alertService().showAlert('Idoneidad verificada. El profesional ya puede ser designado.', 'success');
        this.cargar();
      })
      .catch(err => {
        this.verificandoId = null;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public nombre(externo: IAsesorExterno): string {
    const nombre = `${externo.nombres || ''} ${externo.apellidos || ''}`.trim();
    return nombre || 'Sin nombre';
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
