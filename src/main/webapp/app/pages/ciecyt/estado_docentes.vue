<template>
  <div class="estado-docentes">
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-3">
      <div>
        <h2 class="mb-1">Estado de docentes</h2>
        <p class="text-muted mb-0">
          Tesis y jurados por docente, horas semanales asignadas y alertas de asesorías que siguen abiertas en
          periodos anteriores.
        </p>
      </div>
      <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="cargando" @click="cargar">
        <font-awesome-icon icon="sync" :spin="cargando" /> Actualizar
      </button>
    </div>

    <div class="row g-3 mb-3">
      <div class="col-6 col-lg-2">
        <div class="card">
          <div class="card-body py-3">
            <div class="small text-muted">Docentes</div>
            <div class="fs-3 fw-semibold">{{ resumen.docentes }}</div>
          </div>
        </div>
      </div>
      <div class="col-6 col-lg-2">
        <div class="card">
          <div class="card-body py-3">
            <div class="small text-muted">Tesis como asesor</div>
            <div class="fs-3 fw-semibold">{{ resumen.tesisAsesor }}</div>
          </div>
        </div>
      </div>
      <div class="col-6 col-lg-2">
        <div class="card">
          <div class="card-body py-3">
            <div class="small text-muted">Tesis como jurado</div>
            <div class="fs-3 fw-semibold">{{ resumen.tesisJurado }}</div>
          </div>
        </div>
      </div>
      <div class="col-6 col-lg-2">
        <div class="card">
          <div class="card-body py-3">
            <div class="small text-muted">Horas semanales</div>
            <div class="fs-3 fw-semibold">{{ resumen.horas }}</div>
          </div>
        </div>
      </div>
      <div class="col-6 col-lg-2">
        <div class="card">
          <div class="card-body py-3">
            <div class="small text-muted">Asesorías antiguas</div>
            <div class="fs-3 fw-semibold text-danger">{{ resumen.alertas }}</div>
          </div>
        </div>
      </div>
      <div class="col-6 col-lg-2">
        <div class="card">
          <div class="card-body py-3">
            <div class="small text-muted">Periodo actual</div>
            <div class="fs-5 fw-semibold">{{ periodoActual ? periodoActual.nombre : 'Sin definir' }}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="card mb-3">
      <div class="card-body">
        <div class="row g-2 align-items-end">
          <div class="col-12 col-md-3">
            <label class="form-label small" for="filtro-periodo">Periodo</label>
            <select id="filtro-periodo" v-model="periodoFiltro" class="form-select form-select-sm">
              <option value="TODOS">Todos los periodos</option>
              <option v-for="periodo in periodos" :key="periodo.id" :value="String(periodo.id)">
                {{ periodo.nombre }}{{ periodo.activo ? ' (activo)' : '' }}
              </option>
            </select>
          </div>
          <div class="col-12 col-md-2">
            <label class="form-label small" for="filtro-rol">Rol</label>
            <select id="filtro-rol" v-model="rolFiltro" class="form-select form-select-sm">
              <option value="TODOS">Asesor y jurado</option>
              <option value="ASESOR">Solo asesor</option>
              <option value="JURADO">Solo jurado</option>
            </select>
          </div>
          <div class="col-12 col-md-3">
            <label class="form-label small" for="filtro-texto">Buscar docente</label>
            <input
              id="filtro-texto"
              v-model="texto"
              type="search"
              class="form-control form-control-sm"
              placeholder="Nombre, login o facultad"
            />
          </div>
          <div class="col-12 col-md-2">
            <b-form-checkbox v-model="soloAlertas" class="mt-4">Solo con alertas</b-form-checkbox>
          </div>
          <div class="col-12 col-md-2 text-md-end">
            <button
              type="button"
              class="btn btn-outline-secondary btn-sm w-100"
              :disabled="docentesFiltrados.length === 0"
              @click="exportarCsv"
            >
              <font-awesome-icon icon="download" /> Exportar CSV
            </button>
          </div>
        </div>
      </div>
    </div>

    <jhi-loading v-if="cargando"></jhi-loading>
    <div v-else-if="docentesFiltrados.length === 0" class="alert alert-light border text-center text-muted">
      No hay docentes con asesorías o jurados para los filtros seleccionados.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle">
        <thead class="table-light">
          <tr>
            <th>Docente</th>
            <th>Facultad</th>
            <th class="text-center">Tesis asesor</th>
            <th class="text-center">Tesis jurado</th>
            <th class="text-center">Total asesor</th>
            <th class="text-center">Total jurado</th>
            <th style="min-width: 170px">Horas semanales</th>
            <th>Periodo(s)</th>
            <th class="text-center">Alertas</th>
            <th class="text-end">Acciones</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="docente in docentesFiltrados" :key="docente.key">
            <td>
              <div class="fw-semibold">{{ docente.nombre }}</div>
              <small class="text-muted">{{ docente.login }}</small>
              <span v-if="docente.externo" class="badge bg-info ms-1">Externo</span>
            </td>
            <td>{{ docente.facultad || '—' }}</td>
            <td class="text-center">{{ docente.tesisAsesor }}</td>
            <td class="text-center">{{ docente.tesisJurado }}</td>
            <td class="text-center">{{ docente.totalAsesor }}</td>
            <td class="text-center">{{ docente.totalJurado }}</td>
            <td>
              <div v-if="docente.habilitadoId" class="d-flex align-items-center gap-1">
                <input
                  v-model.number="docente.horasEdit"
                  type="number"
                  min="0"
                  class="form-control form-control-sm"
                  style="width: 80px"
                  :disabled="docente.guardandoHoras"
                  :title="'Horas semanales registradas en el padrón'"
                />
                <button
                  type="button"
                  class="btn btn-sm btn-outline-primary"
                  :disabled="docente.guardandoHoras"
                  title="Guardar horas semanales"
                  @click="guardarHoras(docente)"
                >
                  <font-awesome-icon icon="save" :spin="docente.guardandoHoras" />
                </button>
              </div>
              <div v-else class="small text-muted">Sin registro en padrón</div>
            </td>
            <td>
              <span v-for="periodo in docente.periodos" :key="periodo" class="badge bg-secondary me-1 mb-1">
                {{ periodo }}
              </span>
              <span v-if="docente.periodos.length === 0" class="text-muted">—</span>
            </td>
            <td class="text-center">
              <span v-if="docente.alertas > 0" class="badge bg-danger" :title="textoAlertas(docente)">
                {{ docente.alertas }}
              </span>
              <span v-else class="text-muted">—</span>
            </td>
            <td class="text-end text-nowrap">
              <button type="button" class="btn btn-sm btn-outline-secondary" @click="verDetalle(docente)">
                <font-awesome-icon icon="eye" /> Detalle
              </button>
              <button
                type="button"
                class="btn btn-sm btn-outline-success ms-1"
                :disabled="!puedeCertificar(docente)"
                :title="tituloCertificacion(docente)"
                @click="certificar(docente)"
              >
                <font-awesome-icon icon="check-circle" /> Certificar
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="card mt-4">
      <div class="card-header">Reporte de asesorías y jurados por periodo</div>
      <div class="card-body p-0">
        <div class="table-responsive">
          <table class="table table-sm mb-0 align-middle">
            <thead class="table-light">
              <tr>
                <th>Periodo</th>
                <th class="text-center">Asesorías</th>
                <th class="text-center">Jurados</th>
                <th class="text-center">Tesis (asesor)</th>
                <th class="text-center">Tesis (jurado)</th>
                <th class="text-center">Docentes</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="fila in resumenPorPeriodo" :key="fila.periodoId || fila.periodo">
                <td class="fw-semibold">{{ fila.periodo }}</td>
                <td class="text-center">{{ fila.asesorias }}</td>
                <td class="text-center">{{ fila.jurados }}</td>
                <td class="text-center">{{ fila.tesisAsesor }}</td>
                <td class="text-center">{{ fila.tesisJurado }}</td>
                <td class="text-center">{{ fila.docentes }}</td>
              </tr>
              <tr v-if="resumenPorPeriodo.length === 0">
                <td colspan="6" class="text-center text-muted py-3">Sin datos.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div
      v-if="detalle"
      class="modal fade show d-block"
      tabindex="-1"
      role="dialog"
      style="background: rgba(0, 0, 0, 0.5)"
      @click.self="detalle = null"
    >
      <div class="modal-dialog modal-xl modal-dialog-scrollable" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Docente: {{ detalle.nombre }}</h5>
            <button type="button" class="btn-close" aria-label="Cerrar" @click="detalle = null"></button>
          </div>
          <div class="modal-body">
            <div class="row g-2 mb-3">
              <div class="col-md-4">
                <div class="small text-muted">Facultad</div>
                <div>{{ detalle.facultad || '—' }}</div>
              </div>
              <div class="col-md-4">
                <div class="small text-muted">Horas semanales</div>
                <div>{{ detalle.horasSemanales != null ? detalle.horasSemanales + ' h' : 'Sin registrar' }}</div>
              </div>
              <div class="col-md-4">
                <div class="small text-muted">Periodos con designaciones</div>
                <div>{{ detalle.periodos.join(', ') || '—' }}</div>
              </div>
            </div>

            <div class="table-responsive">
              <table class="table table-sm table-bordered align-middle">
                <thead class="table-light">
                  <tr>
                    <th>Proyecto</th>
                    <th>Rol</th>
                    <th>Modalidad</th>
                    <th>Estado</th>
                    <th>Periodo</th>
                    <th>Estudiante(s)</th>
                    <th class="text-center">Alerta</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="asignacion in detalle.asignacionesFiltradas" :key="asignacion.clave">
                    <td>
                      <div class="fw-semibold">{{ asignacion.titulo }}</div>
                      <small class="text-muted">#{{ asignacion.proyectoId }}</small>
                    </td>
                    <td>{{ asignacion.rol }}</td>
                    <td>{{ asignacion.modalidad || '—' }}</td>
                    <td>{{ etiquetaEstado(asignacion.estado) }}</td>
                    <td>{{ asignacion.periodoNombre || 'Sin periodo' }}</td>
                    <td>{{ asignacion.estudiantes.join(', ') || '—' }}</td>
                    <td class="text-center">
                      <span
                        v-if="asignacion.rol === 'ASESOR' && esAsignacionAntigua(asignacion)"
                        class="badge bg-danger"
                        >Asesoría antigua</span
                      >
                      <span v-else class="text-muted">—</span>
                    </td>
                  </tr>
                  <tr v-if="detalle.asignacionesFiltradas.length === 0">
                    <td colspan="7" class="text-center text-muted">Sin designaciones para los filtros.</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="detalle = null">Cerrar</button>
            <button
              type="button"
              class="btn btn-success"
              :disabled="!puedeCertificar(detalle)"
              :title="tituloCertificacion(detalle)"
              @click="certificar(detalle)"
            >
              Certificar
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import { jsPDF } from 'jspdf';

import AlertService from '@/shared/alert/alert.service';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import PeriodoService from '@/entities/periodo/periodo.service';
import FacultadService from '@/entities/facultad/facultad.service';
import DocenteHabilitadoService from '@/entities/docente-habilitado/docente-habilitado.service';
import { IProyecto } from '@/shared/model/proyecto.model';
import { IPeriodo } from '@/shared/model/periodo.model';

interface AsignacionReporte {
  clave: string;
  proyectoId: number;
  titulo: string;
  rol: string;
  modalidadId: number;
  modalidad: string;
  estado: string;
  periodoId: number | null;
  periodoNombre: string | null;
  fecha: any;
  estudiantes: string[];
  facultadId: number | null;
}

interface DocenteReporte {
  key: string;
  userId: number | null;
  externoId: number | null;
  nombre: string;
  login: string;
  externo: boolean;
  facultadId: number | null;
  facultad: string;
  habilitadoId: number | null;
  horasSemanales: number | null;
  horasEdit: number | null;
  guardandoHoras: boolean;
  asignaciones: AsignacionReporte[];
  asignacionesFiltradas: AsignacionReporte[];
  tesisAsesor: number;
  tesisJurado: number;
  totalAsesor: number;
  totalJurado: number;
  periodos: string[];
  alertas: number;
}

const MODALIDAD_TESIS = 9004;
const ESTADO_FINALIZADO = 'FINALIZADO';

@Component
export default class EstadoDocentes extends Vue {
  @Inject
  private proyectoService: () => ProyectoService;

  @Inject
  private periodoService: () => PeriodoService;

  @Inject
  private facultadService: () => FacultadService;

  @Inject
  private docenteHabilitadoService: () => DocenteHabilitadoService;

  @Inject
  private alertService: () => AlertService;

  public proyectos: IProyecto[] = [];
  public periodos: IPeriodo[] = [];
  public facultades: any[] = [];
  public habilitados: any[] = [];
  public cargando = false;

  public periodoFiltro = 'TODOS';
  public rolFiltro = 'TODOS';
  public texto = '';
  public soloAlertas = false;

  public detalle: DocenteReporte | null = null;

  public mounted(): void {
    this.cargar();
  }

  public cargar(): void {
    this.cargando = true;
    Promise.all([
      this.proyectoService().retrieveAllProyectosIntegrantes(),
      this.periodoService().retrieveAll(),
      this.facultadService().retrieve({ page: 0, size: 300, sort: ['facultad,asc'] }),
      this.docenteHabilitadoService().retrieveVigentesGlobal(),
    ])
      .then(([proyectos, periodos, facultades, habilitados]) => {
        this.proyectos = proyectos.data || [];
        this.periodos = (periodos || []).slice().sort((a, b) => this.fechaMs(a.fechaInicio) - this.fechaMs(b.fechaInicio));
        this.facultades = facultades.data || [];
        this.habilitados = habilitados.data || [];
        this.cargando = false;
      })
      .catch(err => {
        this.cargando = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public get periodoActual(): IPeriodo | null {
    if (!this.periodos.length) {
      return null;
    }
    const activo = this.periodos.find(periodo => periodo.activo);
    if (activo) {
      return activo;
    }
    const hoy = new Date().getTime();
    const enRango = this.periodos.find(periodo => {
      const inicio = this.fechaMs(periodo.fechaInicio);
      const fin = this.fechaMs(periodo.fechaFin);
      return (inicio == null || inicio <= hoy) && (fin == null || fin >= hoy);
    });
    if (enRango) {
      return enRango;
    }
    return this.periodos[this.periodos.length - 1];
  }

  public get docentesBase(): DocenteReporte[] {
    const mapa = new Map<string, DocenteReporte>();

    for (const proyecto of this.proyectos) {
      const periodo = this.periodoDe(proyecto);
      const estudiantes = (proyecto.listaIntegrantesProyecto || [])
        .filter(integrante => this.rolNormalizado(integrante.integranteProyectoRolesModalidadRol) === 'ESTUDIANTE')
        .map(integrante => this.nombreIntegrante(integrante));

      for (const integrante of proyecto.listaIntegrantesProyecto || []) {
        const rol = this.rolNormalizado(integrante.integranteProyectoRolesModalidadRol);
        if (rol !== 'ASESOR' && rol !== 'JURADO') {
          continue;
        }
        const userId = integrante.integranteProyectoUserId != null ? Number(integrante.integranteProyectoUserId) : null;
        const externoId =
          integrante.integranteProyectoExternoId != null ? Number(integrante.integranteProyectoExternoId) : null;
        const key = userId ? `u-${userId}` : externoId ? `e-${externoId}` : null;
        if (!key) {
          continue;
        }

        let docente = mapa.get(key);
        if (!docente) {
          docente = {
            key,
            userId,
            externoId,
            nombre: this.nombreIntegrante(integrante),
            login: integrante.integranteProyectoUserLogin || '',
            externo: !!externoId,
            facultadId: proyecto.facultadId != null ? Number(proyecto.facultadId) : null,
            facultad: this.nombreFacultad(proyecto.facultadId),
            habilitadoId: null,
            horasSemanales: null,
            horasEdit: null,
            guardandoHoras: false,
            asignaciones: [],
            asignacionesFiltradas: [],
            tesisAsesor: 0,
            tesisJurado: 0,
            totalAsesor: 0,
            totalJurado: 0,
            periodos: [],
            alertas: 0,
          };
          mapa.set(key, docente);
        }

        if (!docente.facultadId && proyecto.facultadId != null) {
          docente.facultadId = Number(proyecto.facultadId);
          docente.facultad = this.nombreFacultad(proyecto.facultadId);
        }

        const clave = `${proyecto.id}-${rol}`;
        if (!docente.asignaciones.some(asignacion => asignacion.clave === clave)) {
          docente.asignaciones.push({
            clave,
            proyectoId: Number(proyecto.id),
            titulo: proyecto.titulo || 'Sin título',
            rol,
            modalidadId: proyecto.proyectoModalidadId != null ? Number(proyecto.proyectoModalidadId) : null,
            modalidad: proyecto.proyectoModalidadModalidad || '',
            estado: String(proyecto.estado || ''),
            periodoId: periodo ? Number(periodo.id) : null,
            periodoNombre: periodo ? periodo.nombre : null,
            fecha: this.fechaProyecto(proyecto),
            estudiantes,
            facultadId: proyecto.facultadId != null ? Number(proyecto.facultadId) : null,
          });
        }
      }
    }

    for (const docente of mapa.values()) {
      if (!docente.userId) {
        continue;
      }
      const registros = this.habilitados.filter(
        habilitado => habilitado.user && Number(habilitado.user.id) === docente.userId
      );
      if (registros.length > 0) {
        docente.habilitadoId = registros[0].id != null ? Number(registros[0].id) : null;
        const conHoras = registros.filter(registro => registro.horasSemanales != null);
        docente.horasSemanales = conHoras.length
          ? Math.max(...conHoras.map(registro => Number(registro.horasSemanales)))
          : null;
        docente.horasEdit = docente.horasSemanales;
        if (!docente.facultad && registros[0].facultad) {
          docente.facultad = registros[0].facultad.facultad || '';
        }
      }
    }

    return Array.from(mapa.values());
  }

  public get docentesFiltrados(): DocenteReporte[] {
    const texto = this.texto.trim().toLowerCase();
    const resultado: DocenteReporte[] = [];

    for (const docente of this.docentesBase) {
      const asignaciones = docente.asignaciones.filter(asignacion => {
        if (this.rolFiltro !== 'TODOS' && asignacion.rol !== this.rolFiltro) {
          return false;
        }
        if (this.periodoFiltro !== 'TODOS' && String(asignacion.periodoId || '') !== this.periodoFiltro) {
          return false;
        }
        return true;
      });

      if (asignaciones.length === 0) {
        continue;
      }
      if (texto && !`${docente.nombre} ${docente.login} ${docente.facultad}`.toLowerCase().includes(texto)) {
        continue;
      }

      const alertas = asignaciones.filter(
        asignacion => asignacion.rol === 'ASESOR' && this.esAsignacionAntigua(asignacion)
      ).length;
      if (this.soloAlertas && alertas === 0) {
        continue;
      }

      resultado.push({
        ...docente,
        asignacionesFiltradas: asignaciones,
        tesisAsesor: this.contarProyectos(asignaciones, 'ASESOR', true),
        tesisJurado: this.contarProyectos(asignaciones, 'JURADO', true),
        totalAsesor: this.contarProyectos(asignaciones, 'ASESOR', false),
        totalJurado: this.contarProyectos(asignaciones, 'JURADO', false),
        periodos: Array.from(
          new Set(asignaciones.map(asignacion => asignacion.periodoNombre).filter(nombre => !!nombre))
        ) as string[],
        alertas,
      });
    }

    return resultado.sort((a, b) => a.nombre.localeCompare(b.nombre));
  }

  public get resumen(): { docentes: number; tesisAsesor: number; tesisJurado: number; horas: number; alertas: number } {
    const docentes = this.docentesFiltrados;
    return {
      docentes: docentes.length,
      tesisAsesor: docentes.reduce((total, docente) => total + docente.tesisAsesor, 0),
      tesisJurado: docentes.reduce((total, docente) => total + docente.tesisJurado, 0),
      horas: docentes.reduce((total, docente) => total + (docente.horasSemanales || 0), 0),
      alertas: docentes.reduce((total, docente) => total + docente.alertas, 0),
    };
  }

  public get resumenPorPeriodo(): any[] {
    const filas = new Map<string, any>();

    for (const docente of this.docentesBase) {
      for (const asignacion of docente.asignaciones) {
        const clave = asignacion.periodoId ? `p-${asignacion.periodoId}` : 'sin-periodo';
        let fila = filas.get(clave);
        if (!fila) {
          fila = {
            periodoId: asignacion.periodoId,
            periodo: asignacion.periodoNombre || 'Sin periodo',
            asignaciones: new Set<string>(),
            asesorias: new Set<string>(),
            jurados: new Set<string>(),
            tesisAsesor: new Set<string>(),
            tesisJurado: new Set<string>(),
            docentes: new Set<string>(),
          };
          filas.set(clave, fila);
        }
        fila.asignaciones.add(`${docente.key}-${asignacion.proyectoId}-${asignacion.rol}`);
        fila.docentes.add(docente.key);
        if (asignacion.rol === 'ASESOR') {
          fila.asesorias.add(`${docente.key}-${asignacion.proyectoId}`);
          if (asignacion.modalidadId === MODALIDAD_TESIS) {
            fila.tesisAsesor.add(`${docente.key}-${asignacion.proyectoId}`);
          }
        } else {
          fila.jurados.add(`${docente.key}-${asignacion.proyectoId}`);
          if (asignacion.modalidadId === MODALIDAD_TESIS) {
            fila.tesisJurado.add(`${docente.key}-${asignacion.proyectoId}`);
          }
        }
      }
    }

    return Array.from(filas.values())
      .map(fila => ({
        periodoId: fila.periodoId,
        periodo: fila.periodo,
        asesorias: fila.asesorias.size,
        jurados: fila.jurados.size,
        tesisAsesor: fila.tesisAsesor.size,
        tesisJurado: fila.tesisJurado.size,
        docentes: fila.docentes.size,
      }))
      .sort((a, b) => {
        const ia = this.periodos.findIndex(periodo => Number(periodo.id) === Number(a.periodoId));
        const ib = this.periodos.findIndex(periodo => Number(periodo.id) === Number(b.periodoId));
        return ib - ia;
      });
  }

  public puedeCertificar(docente: DocenteReporte): boolean {
    if (!docente || docente.asignacionesFiltradas.length === 0) {
      return false;
    }
    return docente.asignacionesFiltradas.every(asignacion => asignacion.estado === ESTADO_FINALIZADO);
  }

  public tituloCertificacion(docente: DocenteReporte): string {
    if (!docente || docente.asignacionesFiltradas.length === 0) {
      return 'No hay designaciones para certificar.';
    }
    const pendientes = docente.asignacionesFiltradas.filter(asignacion => asignacion.estado !== ESTADO_FINALIZADO);
    if (pendientes.length > 0) {
      return 'Solo se certifica cuando todos los proyectos están FINALIZADOS. Pendientes: ' + pendientes.length + '.';
    }
    return 'Generar certificación de asesorías y jurados.';
  }

  public textoAlertas(docente: DocenteReporte): string {
    return `${docente.alertas} asesoría(s) de periodos anteriores sin finalizar`;
  }

  public verDetalle(docente: DocenteReporte): void {
    this.detalle = docente;
  }

  public guardarHoras(docente: DocenteReporte): void {
    if (!docente.habilitadoId) {
      return;
    }
    const horas = docente.horasEdit != null && !isNaN(Number(docente.horasEdit)) ? Number(docente.horasEdit) : null;
    docente.guardandoHoras = true;
    this.docenteHabilitadoService()
      .actualizarHorasSemanales(docente.habilitadoId, horas)
      .then(res => {
        docente.guardandoHoras = false;
        const guardadas = res.data && res.data.horasSemanales != null ? Number(res.data.horasSemanales) : horas;
        docente.horasSemanales = guardadas;
        docente.horasEdit = guardadas;
        const registro = this.habilitados.find(habilitado => Number(habilitado.id) === docente.habilitadoId);
        if (registro) {
          registro.horasSemanales = guardadas;
        }
        this.alertService().showAlert('Horas semanales actualizadas', 'success');
      })
      .catch(err => {
        docente.guardandoHoras = false;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public certificar(docente: DocenteReporte): void {
    if (!this.puedeCertificar(docente)) {
      this.alertService().showAlert(this.tituloCertificacion(docente), 'warning');
      return;
    }

    const doc = new jsPDF();
    doc.setFontSize(15);
    doc.text('CERTIFICACION DE ASESORIA Y JURADO', 105, 20, { align: 'center' });
    doc.setFontSize(11);
    let y = 35;
    doc.text('El CIECYT hace constar que:', 15, y);
    y += 8;
    doc.text(`Docente: ${docente.nombre}`, 15, y);
    y += 6;
    if (docente.login) {
      doc.text(`Identificacion / login: ${docente.login}`, 15, y);
      y += 6;
    }
    doc.text(`Facultad: ${docente.facultad || 'No registrada'}`, 15, y);
    y += 6;
    doc.text(`Horas semanales registradas: ${docente.horasSemanales != null ? docente.horasSemanales + ' h' : 'sin registrar'}`, 15, y);
    y += 6;
    doc.text(
      `Tesis como asesor: ${docente.tesisAsesor} | Tesis como jurado: ${docente.tesisJurado}`,
      15,
      y
    );
    y += 10;
    doc.text('Designaciones certificadas:', 15, y);
    y += 6;

    for (const asignacion of docente.asignacionesFiltradas) {
      if (y > 265) {
        doc.addPage();
        y = 20;
      }
      doc.setFontSize(10);
      doc.text(`- ${asignacion.rol}: ${asignacion.titulo}`, 18, y);
      y += 5;
      doc.text(
        `  Modalidad: ${asignacion.modalidad || '-'} | Periodo: ${asignacion.periodoNombre || '-'} | Estado: ${this.etiquetaEstado(
          asignacion.estado
        )}`,
        18,
        y
      );
      y += 5;
      doc.text(`  Estudiante(s): ${asignacion.estudiantes.join(', ') || '-'}`, 18, y);
      y += 8;
    }

    y += 4;
    doc.setFontSize(10);
    doc.text('Todos los proyectos relacionados se encuentran FINALIZADOS.', 15, y);
    y += 10;
    doc.text(`Fecha de expedicion: ${this.fechaHora(new Date())}`, 15, y);
    y += 20;
    doc.text('______________________________', 15, y);
    y += 6;
    doc.text('CIECYT - Institucion Universitaria del Putumayo', 15, y);

    const sufijo = this.periodoFiltro !== 'TODOS' ? ` - periodo ${this.nombrePeriodoFiltro()}` : '';
    doc.save(`Certificacion asesoria y jurado - ${docente.nombre}${sufijo}.pdf`);
  }

  public exportarCsv(): void {
    const filas: any[][] = [
      [
        'Docente',
        'Login',
        'Facultad',
        'Tesis asesor',
        'Tesis jurado',
        'Total asesor',
        'Total jurado',
        'Horas semanales',
        'Periodos',
        'Alertas',
      ],
      ...this.docentesFiltrados.map(docente => [
        docente.nombre,
        docente.login,
        docente.facultad,
        docente.tesisAsesor,
        docente.tesisJurado,
        docente.totalAsesor,
        docente.totalJurado,
        docente.horasSemanales != null ? docente.horasSemanales : '',
        docente.periodos.join(', '),
        docente.alertas,
      ]),
    ];
    const contenido = filas
      .map(fila => fila.map(valor => `"${String(valor == null ? '' : valor).replace(/"/g, '""')}"`).join(';'))
      .join('\r\n');
    const blob = new Blob(['\uFEFF' + contenido], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const enlace = document.createElement('a');
    enlace.href = url;
    enlace.download = 'estado-docentes.csv';
    document.body.appendChild(enlace);
    enlace.click();
    document.body.removeChild(enlace);
    URL.revokeObjectURL(url);
  }

  public esAsignacionAntigua(asignacion: AsignacionReporte): boolean {
    if (!asignacion || asignacion.estado === ESTADO_FINALIZADO) {
      return false;
    }
    const actual = this.periodoActual;
    if (!actual) {
      return false;
    }
    if (asignacion.periodoId != null) {
      if (Number(asignacion.periodoId) === Number(actual.id)) {
        return false;
      }
      const inicioAsignacion = this.fechaMs(this.fechaInicioPeriodo(asignacion.periodoId));
      const inicioActual = this.fechaMs(actual.fechaInicio);
      if (inicioAsignacion != null && inicioActual != null) {
        return inicioAsignacion < inicioActual;
      }
      return String(asignacion.periodoNombre || '') < String(actual.nombre || '');
    }
    const fechaAsignacion = this.fechaMs(asignacion.fecha);
    const inicioActual = this.fechaMs(actual.fechaInicio);
    return fechaAsignacion != null && inicioActual != null && fechaAsignacion < inicioActual;
  }

  public etiquetaEstado(estado: string): string {
    return String(estado || 'SIN ESTADO').replace(/_/g, ' ');
  }

  private contarProyectos(asignaciones: AsignacionReporte[], rol: string, soloTesis: boolean): number {
    const ids = new Set<number>();
    for (const asignacion of asignaciones) {
      if (asignacion.rol !== rol) {
        continue;
      }
      if (soloTesis && asignacion.modalidadId !== MODALIDAD_TESIS) {
        continue;
      }
      ids.add(asignacion.proyectoId);
    }
    return ids.size;
  }

  private periodoDe(proyecto: IProyecto): IPeriodo | null {
    const fecha = this.fechaProyecto(proyecto);
    const fechaMs = this.fechaMs(fecha);
    if (fechaMs == null) {
      return null;
    }
    for (const periodo of this.periodos) {
      const inicio = this.fechaMs(periodo.fechaInicio);
      const fin = this.fechaMs(periodo.fechaFin);
      if ((inicio == null || fechaMs >= inicio) && (fin == null || fechaMs <= fin)) {
        return periodo;
      }
    }
    const anio = new Date(fechaMs).getFullYear();
    return this.periodos.find(periodo => String(periodo.nombre || '').includes(String(anio))) || null;
  }

  private fechaProyecto(proyecto: IProyecto): any {
    return proyecto.fechaEnvioPropuesta || proyecto.fechaIni || (proyecto as any).fechaUltimoCambio || null;
  }

  private fechaInicioPeriodo(periodoId: number): any {
    const periodo = this.periodos.find(item => Number(item.id) === Number(periodoId));
    return periodo ? periodo.fechaInicio : null;
  }

  private fechaMs(value: any): number | null {
    if (value == null || value === '') {
      return null;
    }
    const fecha = new Date(value);
    return isNaN(fecha.getTime()) ? null : fecha.getTime();
  }

  private nombreFacultad(facultadId: any): string {
    if (facultadId == null) {
      return '';
    }
    const facultad = this.facultades.find(item => Number(item.id) === Number(facultadId));
    return facultad ? facultad.facultad || facultad.nombre || '' : '';
  }

  private nombreIntegrante(integrante: any): string {
    const nombre = `${integrante.integranteProyectoUserFirstName || ''} ${integrante.integranteProyectoUserLastName || ''}`.trim();
    return (
      nombre ||
      integrante.integranteProyectoExternoNombre ||
      integrante.integranteProyectoUserLogin ||
      integrante.integrante ||
      'Sin nombre'
    );
  }

  private rolNormalizado(rol: any): string {
    const valor = String(rol || '')
      .trim()
      .toUpperCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');
    if (valor.indexOf('ASESOR') >= 0) {
      return 'ASESOR';
    }
    if (valor.indexOf('JURADO') >= 0) {
      return 'JURADO';
    }
    if (valor.indexOf('ESTUDIANTE') >= 0) {
      return 'ESTUDIANTE';
    }
    return valor;
  }

  private nombrePeriodoFiltro(): string {
    const periodo = this.periodos.find(item => String(item.id) === String(this.periodoFiltro));
    return periodo ? periodo.nombre || '' : '';
  }

  private fechaHora(value: any): string {
    const fecha = value instanceof Date ? value : new Date(value);
    if (isNaN(fecha.getTime())) {
      return '—';
    }
    const dos = (numero: number) => String(numero).padStart(2, '0');
    return `${dos(fecha.getDate())}/${dos(fecha.getMonth() + 1)}/${fecha.getFullYear()} ${dos(
      fecha.getHours()
    )}:${dos(fecha.getMinutes())}`;
  }
}
</script>

<style scoped>
.estado-docentes {
  padding-bottom: 1.5rem;
}
</style>
