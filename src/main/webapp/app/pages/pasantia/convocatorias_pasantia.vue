<template>
  <div>
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-3">
      <div>
        <h2 class="mb-1">Convocatorias de pasantía</h2>
        <p class="text-muted mb-0">
          Convocatorias publicadas por el CIECYT para las plazas de pasantía. Los estudiantes se postulan y el CIECYT
          registra la selección.
        </p>
      </div>
      <button v-if="esGestor" type="button" class="btn btn-primary btn-sm" @click="nuevaConvocatoria">
        <font-awesome-icon icon="plus" /> Nueva convocatoria
      </button>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>

    <div v-else>
      <div v-if="esGestor && mostrarFormulario" class="card mb-3">
        <div class="card-body">
          <h5 class="mb-3">{{ form.id ? 'Editar convocatoria' : 'Nueva convocatoria' }}</h5>
          <div class="row g-2">
            <div class="col-md-6">
              <label class="form-label small">Título</label>
              <input v-model="form.titulo" class="form-control form-control-sm" maxlength="255" />
            </div>
            <div class="col-md-3">
              <label class="form-label small">Modalidad</label>
              <select v-model.number="form.modalidadId" class="form-select form-select-sm">
                <option :value="null">Todas las pasantías</option>
                <option v-for="modalidad in modalidades" :key="modalidad.id" :value="modalidad.id">
                  {{ modalidad.nombre }}
                </option>
              </select>
            </div>
            <div class="col-md-3">
              <label class="form-label small">Cupos</label>
              <input v-model.number="form.cupos" type="number" min="1" class="form-control form-control-sm" />
            </div>
            <div class="col-md-6">
              <label class="form-label small">Entidad / empresa</label>
              <input v-model="form.entidad" class="form-control form-control-sm" />
            </div>
            <div class="col-md-6">
              <label class="form-label small">Convenio</label>
              <input v-model="form.convenio" class="form-control form-control-sm" />
            </div>
            <div class="col-md-3">
              <label class="form-label small">Apertura</label>
              <input v-model="form.fechaApertura" type="date" class="form-control form-control-sm" />
            </div>
            <div class="col-md-3">
              <label class="form-label small">Cierre</label>
              <input v-model="form.fechaCierre" type="date" class="form-control form-control-sm" />
            </div>
            <div class="col-md-3">
              <label class="form-label small">Estado</label>
              <select v-model="form.estado" class="form-select form-select-sm">
                <option v-for="estado in estadosConvocatoria" :key="estado" :value="estado">{{ estado }}</option>
              </select>
            </div>
            <div class="col-12">
              <label class="form-label small">Descripción</label>
              <textarea v-model="form.descripcion" class="form-control form-control-sm" rows="2"></textarea>
            </div>
            <div class="col-md-6">
              <label class="form-label small">Requisitos / términos de referencia</label>
              <textarea v-model="form.requisitos" class="form-control form-control-sm" rows="3"></textarea>
            </div>
            <div class="col-md-6">
              <label class="form-label small">Criterios de selección</label>
              <textarea v-model="form.criteriosSeleccion" class="form-control form-control-sm" rows="3"></textarea>
            </div>
          </div>
          <div class="mt-3 d-flex gap-2">
            <button type="button" class="btn btn-primary btn-sm" :disabled="guardando" @click="guardarConvocatoria">
              Guardar
            </button>
            <button type="button" class="btn btn-secondary btn-sm" @click="mostrarFormulario = false">Cancelar</button>
          </div>
        </div>
      </div>

      <div v-if="esGestor || esDecano">
        <div v-if="convocatorias.length === 0" class="alert alert-light border text-center text-muted">
          No hay convocatorias registradas.
        </div>
        <div v-else class="table-responsive">
          <table class="table table-hover align-middle mb-0">
            <thead class="table-light">
              <tr>
                <th>Título</th>
                <th>Modalidad</th>
                <th>Entidad</th>
                <th>Fechas</th>
                <th>Cupos</th>
                <th>Estado</th>
                <th class="text-end">Acciones</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="convocatoria in convocatorias" :key="convocatoria.id">
                <td>
                  <div class="fw-semibold">{{ convocatoria.titulo }}</div>
                  <div class="small text-muted">{{ convocatoria.descripcion }}</div>
                </td>
                <td>{{ etiquetaModalidad(convocatoria.modalidadId) }}</td>
                <td>{{ convocatoria.entidad || '—' }}</td>
                <td class="small">{{ fechaCorta(convocatoria.fechaApertura) }} - {{ fechaCorta(convocatoria.fechaCierre) }}</td>
                <td>{{ convocatoria.cupos || '—' }}</td>
                <td>
                  <span class="badge" :class="claseEstadoConvocatoria(convocatoria.estado)">{{ convocatoria.estado }}</span>
                </td>
                <td class="text-end">
                  <div class="d-flex flex-wrap justify-content-end gap-2">
                    <button type="button" class="btn btn-outline-primary btn-sm" @click="verPostulaciones(convocatoria)">
                      Postulaciones
                    </button>
                    <button v-if="esGestor" type="button" class="btn btn-outline-secondary btn-sm" @click="editarConvocatoria(convocatoria)">
                      Editar
                    </button>
                    <button
                      v-if="esGestor && convocatoria.estado === 'ABIERTA'"
                      type="button"
                      class="btn btn-outline-warning btn-sm"
                      @click="cambiarEstado(convocatoria, 'CERRADA')"
                    >
                      Cerrar
                    </button>
                    <button
                      v-if="esGestor && convocatoria.estado === 'CERRADA'"
                      type="button"
                      class="btn btn-outline-success btn-sm"
                      @click="cambiarEstado(convocatoria, 'ABIERTA')"
                    >
                      Abrir
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div v-if="esEstudiante && !esGestor">
        <h5 class="mt-3">Convocatorias abiertas</h5>
        <div v-if="convocatoriasAbiertas.length === 0" class="alert alert-light border text-center text-muted">
          No hay convocatorias abiertas en este momento.
        </div>
        <div v-else class="row g-3">
          <div v-for="convocatoria in convocatoriasAbiertas" :key="convocatoria.id" class="col-md-6">
            <div class="card h-100">
              <div class="card-body">
                <div class="d-flex justify-content-between gap-2">
                  <h6 class="mb-1">{{ convocatoria.titulo }}</h6>
                  <span class="badge" :class="claseEstadoConvocatoria(convocatoria.estado)">{{ convocatoria.estado }}</span>
                </div>
                <div class="small text-muted">
                  {{ etiquetaModalidad(convocatoria.modalidadId) }} · {{ convocatoria.entidad || 'Sin entidad' }}
                </div>
                <div class="small mt-2">
                  <div><strong>Apertura:</strong> {{ fechaCorta(convocatoria.fechaApertura) }}</div>
                  <div><strong>Cierre:</strong> {{ fechaCorta(convocatoria.fechaCierre) }}</div>
                  <div><strong>Cupos:</strong> {{ convocatoria.cupos || 'Sin límite' }}</div>
                </div>
                <div v-if="convocatoria.requisitos" class="small mt-2">
                  <strong>Requisitos:</strong> {{ convocatoria.requisitos }}
                </div>
                <div v-if="convocatoria.criteriosSeleccion" class="small mt-1">
                  <strong>Criterios:</strong> {{ convocatoria.criteriosSeleccion }}
                </div>
                <button
                  type="button"
                  class="btn btn-primary btn-sm mt-3"
                  :disabled="yaPostulado(convocatoria)"
                  @click="abrirPostulacion(convocatoria)"
                >
                  {{ yaPostulado(convocatoria) ? 'Ya postulado' : 'Postularme' }}
                </button>
              </div>
            </div>
          </div>
        </div>

        <h5 class="mt-4">Mis postulaciones</h5>
        <div v-if="misPostulaciones.length === 0" class="alert alert-light border text-center text-muted">
          Aún no tiene postulaciones.
        </div>
        <div v-else class="table-responsive">
          <table class="table table-sm align-middle mb-0">
            <thead class="table-light">
              <tr>
                <th>Convocatoria</th>
                <th>Proyecto</th>
                <th>Fecha</th>
                <th>Estado</th>
                <th>Observación</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="postulacion in misPostulaciones" :key="postulacion.id">
                <td>{{ postulacion.convocatoriaTitulo }}</td>
                <td>{{ postulacion.proyectoTitulo }}</td>
                <td>{{ fechaCorta(postulacion.fechaPostulacion) }}</td>
                <td>
                  <span class="badge" :class="claseEstadoPostulacion(postulacion.estado)">{{ postulacion.estado }}</span>
                </td>
                <td class="small">{{ postulacion.observacion || '—' }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <b-modal v-model="modalPostulacionesVisible" size="xl" ok-only ok-title="Cerrar" title="Postulaciones">
      <div v-if="cargandoPostulaciones" class="text-muted">Cargando postulaciones...</div>
      <div v-else-if="postulaciones.length === 0" class="text-muted">No hay postulaciones para esta convocatoria.</div>
      <div v-else class="table-responsive">
        <table class="table table-sm align-middle mb-0">
          <thead class="table-light">
            <tr>
              <th>Estudiante</th>
              <th>Proyecto</th>
              <th>Fecha</th>
              <th>Estado</th>
              <th>Observación</th>
              <th v-if="esGestor" class="text-end">Decisión</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="postulacion in postulaciones" :key="postulacion.id">
              <td>
                <div class="fw-semibold">{{ postulacion.usuarioNombre || postulacion.usuarioLogin || '—' }}</div>
                <div v-if="postulacion.usuarioLogin && postulacion.usuarioLogin !== postulacion.usuarioNombre" class="small text-muted">
                  {{ postulacion.usuarioLogin }}
                </div>
              </td>
              <td>{{ postulacion.proyectoTitulo }}</td>
              <td>{{ fechaCorta(postulacion.fechaPostulacion) }}</td>
              <td>
                <span class="badge" :class="claseEstadoPostulacion(postulacion.estado)">{{ postulacion.estado }}</span>
              </td>
              <td class="small">{{ postulacion.observacion || '—' }}</td>
              <td v-if="esGestor" class="text-end">
                <div class="d-flex justify-content-end gap-2">
                  <button
                    v-if="postulacion.estado === 'PENDIENTE'"
                    type="button"
                    class="btn btn-success btn-sm"
                    @click="seleccionarPostulacion(postulacion)"
                  >
                    Seleccionar
                  </button>
                  <button
                    v-if="postulacion.estado === 'PENDIENTE'"
                    type="button"
                    class="btn btn-danger btn-sm"
                    @click="rechazarPostulacion(postulacion)"
                  >
                    Rechazar
                  </button>
                </div>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </b-modal>

    <b-modal v-model="modalPostulacionVisible" ok-only ok-title="Cancelar" title="Postularme a la convocatoria">
      <div v-if="convocatoriaPostulacion" class="mb-3">
        <div class="fw-semibold">{{ convocatoriaPostulacion.titulo }}</div>
        <div class="small text-muted">{{ etiquetaModalidad(convocatoriaPostulacion.modalidadId) }}</div>
      </div>
      <div class="mb-3">
        <label class="form-label small">Proyecto de pasantía</label>
        <select v-model.number="proyectoSeleccionado" class="form-select form-select-sm">
          <option :value="null">Seleccione un proyecto</option>
          <option v-for="proyecto in proyectosEstudiante" :key="proyecto.id" :value="proyecto.id">
            {{ proyecto.titulo }} (#{{ proyecto.id }})
          </option>
        </select>
        <div v-if="proyectosEstudiante.length === 0" class="small text-danger mt-1">
          No tiene un proyecto de pasantía compatible. Créelo primero en "Mis propuestas".
        </div>
      </div>
      <div class="mb-3">
        <label class="form-label small">Observación (opcional)</label>
        <textarea v-model="observacionPostulacion" class="form-control form-control-sm" rows="2"></textarea>
      </div>
      <button type="button" class="btn btn-primary" :disabled="!proyectoSeleccionado || guardandoPostulacion" @click="guardarPostulacionEstudiante">
        Enviar postulación
      </button>
    </b-modal>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import { IProyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { ConvocatoriaPasantia, IConvocatoriaPasantia } from '@/shared/model/convocatoria-pasantia.model';
import { IPostulacionPasantia } from '@/shared/model/postulacion-pasantia.model';
import ConvocatoriaPasantiaService from '@/entities/convocatoria-pasantia/convocatoria-pasantia.service';

@Component
export default class ConvocatoriasPasantia extends Vue {
  @Inject
  private convocatoriaPasantiaService: () => ConvocatoriaPasantiaService;

  @Inject
  private proyectoService: () => ProyectoService;

  @Inject
  private alertService: () => AlertService;

  public convocatorias: IConvocatoriaPasantia[] = [];
  public convocatoriasAbiertas: IConvocatoriaPasantia[] = [];
  public misPostulaciones: IPostulacionPasantia[] = [];
  public postulaciones: IPostulacionPasantia[] = [];
  public proyectosEstudiante: IProyecto[] = [];
  public convocatoriaSeleccionada: IConvocatoriaPasantia = null;
  public convocatoriaPostulacion: IConvocatoriaPasantia = null;
  public form: any = new ConvocatoriaPasantia();
  public mostrarFormulario = false;
  public modalPostulacionesVisible = false;
  public modalPostulacionVisible = false;
  public proyectoSeleccionado: number = null;
  public observacionPostulacion = '';
  public cargando = false;
  public guardando = false;
  public guardandoPostulacion = false;
  public cargandoPostulaciones = false;

  public modalidades = [
    { id: 9001, nombre: 'Pasantía Tecnológica' },
    { id: 9002, nombre: 'Pasantía Investigativa Profesional' },
    { id: 9003, nombre: 'Pasantía Internacional' },
  ];
  public estadosConvocatoria = ['ABIERTA', 'CERRADA', 'EN_SELECCION', 'FINALIZADA'];

  public get authorities(): string[] {
    const account = this.$store.getters.account;
    return account && account.authorities ? account.authorities : [];
  }

  public get esGestor(): boolean {
    return this.authorities.indexOf('ROLE_CIECYT') >= 0 || this.authorities.indexOf('ROLE_ADMIN') >= 0;
  }

  public get esDecano(): boolean {
    return this.authorities.indexOf('ROLE_DECANO') >= 0;
  }

  public get esEstudiante(): boolean {
    return this.authorities.indexOf('ROLE_ESTUDIANTE') >= 0;
  }

  public mounted(): void {
    if (this.esGestor || this.esDecano) {
      this.cargarConvocatorias();
    }
    if (this.esEstudiante && !this.esGestor) {
      this.cargarAbiertas();
      this.cargarMisPostulaciones();
    }
  }

  public cargarConvocatorias(): void {
    this.cargando = true;
    this.convocatoriaPasantiaService()
      .findAll()
      .then(lista => {
        this.convocatorias = lista || [];
        this.cargando = false;
      })
      .catch(() => {
        this.convocatorias = [];
        this.cargando = false;
      });
  }

  public cargarAbiertas(): void {
    this.cargando = true;
    this.convocatoriaPasantiaService()
      .findAbiertas()
      .then(lista => {
        this.convocatoriasAbiertas = lista || [];
        this.cargando = false;
      })
      .catch(() => {
        this.convocatoriasAbiertas = [];
        this.cargando = false;
      });
  }

  public cargarMisPostulaciones(): void {
    this.convocatoriaPasantiaService()
      .findMisPostulaciones()
      .then(lista => {
        this.misPostulaciones = lista || [];
      })
      .catch(() => {
        this.misPostulaciones = [];
      });
  }

  public nuevaConvocatoria(): void {
    this.form = new ConvocatoriaPasantia();
    this.form.estado = 'ABIERTA';
    this.mostrarFormulario = true;
  }

  public editarConvocatoria(convocatoria: IConvocatoriaPasantia): void {
    this.form = {
      ...convocatoria,
      fechaApertura: this.fechaInput(convocatoria.fechaApertura),
      fechaCierre: this.fechaInput(convocatoria.fechaCierre),
    };
    this.mostrarFormulario = true;
  }

  public guardarConvocatoria(): void {
    if (!this.form.titulo) {
      this.alertService().showAlert('El título es obligatorio.', 'warning');
      return;
    }
    this.guardando = true;
    const esNueva = !this.form.id;
    const payload = {
      ...this.form,
      modalidadId: this.form.modalidadId || null,
      cupos: this.form.cupos || null,
      fechaApertura: this.form.fechaApertura || null,
      fechaCierre: this.form.fechaCierre || null,
    };
    const accion = esNueva
      ? this.convocatoriaPasantiaService().create(payload)
      : this.convocatoriaPasantiaService().update(payload);
    accion
      .then(() => {
        this.guardando = false;
        this.mostrarFormulario = false;
        this.alertService().showAlert(esNueva ? 'Convocatoria creada' : 'Convocatoria actualizada', 'success');
        this.cargarConvocatorias();
      })
      .catch(err => {
        this.guardando = false;
        const detalle = err && err.response && err.response.data ? err.response.data.detail || err.response.data.title : null;
        this.alertService().showAlert(detalle || 'No se pudo guardar la convocatoria', 'danger');
      });
  }

  public cambiarEstado(convocatoria: IConvocatoriaPasantia, estado: string): void {
    this.convocatoriaPasantiaService()
      .cambiarEstado(convocatoria.id, estado)
      .then(() => {
        this.alertService().showAlert(`Convocatoria ${estado.toLowerCase()}`, 'success');
        this.cargarConvocatorias();
      })
      .catch(err => {
        const detalle = err && err.response && err.response.data ? err.response.data.detail || err.response.data.title : null;
        this.alertService().showAlert(detalle || 'No se pudo cambiar el estado', 'danger');
      });
  }

  public verPostulaciones(convocatoria: IConvocatoriaPasantia): void {
    this.convocatoriaSeleccionada = convocatoria;
    this.modalPostulacionesVisible = true;
    this.cargandoPostulaciones = true;
    this.convocatoriaPasantiaService()
      .findPostulaciones(convocatoria.id)
      .then(lista => {
        this.postulaciones = lista || [];
        this.cargandoPostulaciones = false;
      })
      .catch(() => {
        this.postulaciones = [];
        this.cargandoPostulaciones = false;
      });
  }

  public seleccionarPostulacion(postulacion: IPostulacionPasantia): void {
    this.convocatoriaPasantiaService()
      .seleccionar(postulacion.id)
      .then(() => {
        this.alertService().showAlert('Postulación seleccionada', 'success');
        if (this.convocatoriaSeleccionada) {
          this.verPostulaciones(this.convocatoriaSeleccionada);
        }
        this.cargarConvocatorias();
      })
      .catch(err => {
        const detalle = err && err.response && err.response.data ? err.response.data.detail || err.response.data.title : null;
        this.alertService().showAlert(detalle || 'No se pudo seleccionar la postulación', 'danger');
      });
  }

  public rechazarPostulacion(postulacion: IPostulacionPasantia): void {
    const observacion = window.prompt('Observación del rechazo', '');
    if (observacion === null) {
      return;
    }
    this.convocatoriaPasantiaService()
      .rechazar(postulacion.id, observacion)
      .then(() => {
        this.alertService().showAlert('Postulación rechazada', 'success');
        if (this.convocatoriaSeleccionada) {
          this.verPostulaciones(this.convocatoriaSeleccionada);
        }
      })
      .catch(err => {
        const detalle = err && err.response && err.response.data ? err.response.data.detail || err.response.data.title : null;
        this.alertService().showAlert(detalle || 'No se pudo rechazar la postulación', 'danger');
      });
  }

  public yaPostulado(convocatoria: IConvocatoriaPasantia): boolean {
    return this.misPostulaciones.some(postulacion => postulacion.convocatoriaId === convocatoria.id);
  }

  public abrirPostulacion(convocatoria: IConvocatoriaPasantia): void {
    this.convocatoriaPostulacion = convocatoria;
    this.proyectoSeleccionado = null;
    this.observacionPostulacion = '';
    this.proyectosEstudiante = [];
    this.modalPostulacionVisible = true;
    const account = this.$store.getters.account;
    if (!account) {
      return;
    }
    this.proyectoService()
      .retrieveProyectoIntegranteAuthority(account.id, 'ROLE_ESTUDIANTE', { page: 0, size: 100, sort: ['id,desc'] })
      .then(res => {
        const proyectos: IProyecto[] = res.data || [];
        this.proyectosEstudiante = proyectos.filter(proyecto => {
          const modalidad = Number(proyecto.proyectoModalidadId);
          const compatible = [9001, 9002, 9003].indexOf(modalidad) >= 0;
          const mismaModalidad = !convocatoria.modalidadId || Number(convocatoria.modalidadId) === modalidad;
          return compatible && mismaModalidad;
        });
      })
      .catch(() => {
        this.proyectosEstudiante = [];
      });
  }

  public guardarPostulacionEstudiante(): void {
    if (!this.convocatoriaPostulacion || !this.proyectoSeleccionado) {
      return;
    }
    this.guardandoPostulacion = true;
    this.convocatoriaPasantiaService()
      .postular(this.convocatoriaPostulacion.id, this.proyectoSeleccionado, this.observacionPostulacion)
      .then(() => {
        this.guardandoPostulacion = false;
        this.modalPostulacionVisible = false;
        this.alertService().showAlert('Postulación enviada. El CIECYT revisará su solicitud.', 'success');
        this.cargarMisPostulaciones();
        this.cargarAbiertas();
      })
      .catch(err => {
        this.guardandoPostulacion = false;
        const detalle = err && err.response && err.response.data ? err.response.data.detail || err.response.data.title : null;
        this.alertService().showAlert(detalle || 'No se pudo enviar la postulación', 'danger');
      });
  }

  public etiquetaModalidad(modalidadId: any): string {
    const modalidad = this.modalidades.find(item => item.id === Number(modalidadId));
    return modalidad ? modalidad.nombre : 'Pasantía';
  }

  public claseEstadoConvocatoria(estado: any): string {
    switch (estado) {
      case 'ABIERTA':
        return 'bg-success';
      case 'CERRADA':
        return 'bg-secondary';
      case 'EN_SELECCION':
        return 'bg-info text-dark';
      case 'FINALIZADA':
        return 'bg-primary';
      default:
        return 'bg-secondary';
    }
  }

  public claseEstadoPostulacion(estado: any): string {
    switch (estado) {
      case 'SELECCIONADO':
        return 'bg-success';
      case 'RECHAZADO':
        return 'bg-danger';
      case 'RETIRADO':
        return 'bg-secondary';
      default:
        return 'bg-warning text-dark';
    }
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
    if (isNaN(fecha.getTime())) {
      return '—';
    }
    return fecha.toLocaleDateString('es-CO', { day: '2-digit', month: '2-digit', year: 'numeric' });
  }

  private fechaInput(value: any): string {
    if (!value) {
      return '';
    }
    return String(value).substring(0, 10);
  }
}
</script>
