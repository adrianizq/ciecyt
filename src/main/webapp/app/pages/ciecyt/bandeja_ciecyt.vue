<template>
  <div class="ciecyt-bandeja">
    <div class="d-flex flex-wrap justify-content-between align-items-start gap-2 mb-3">
      <div>
        <h2 id="page-heading" class="mb-1">Bandeja del CIECYT</h2>
        <p class="text-muted mb-0">Radicación, validación documental y gestión de las propuestas.</p>
      </div>
      <div class="d-flex gap-2">
        <button type="button" class="btn btn-outline-secondary btn-sm" :disabled="isFetching" @click="retrieveAllProyectos">
          <font-awesome-icon icon="sync" :spin="isFetching" /> Actualizar
        </button>
        <button type="button" class="btn btn-outline-primary btn-sm" @click="downloadPdf">
          <font-awesome-icon icon="file-alt" /> Descargar informe
        </button>
      </div>
    </div>

    <div class="card mb-3">
      <div class="card-body">
        <div class="row g-2 align-items-end">
          <div class="col-md-5">
            <label class="form-label small mb-1" for="buscar-ciecyt">Buscar</label>
            <input
              id="buscar-ciecyt"
              v-model="busqueda"
              type="search"
              class="form-control form-control-sm"
              placeholder="Título, estudiante o programa"
            />
          </div>
          <div class="col-md-4">
            <label class="form-label small mb-1" for="programa-ciecyt">Programa</label>
            <select id="programa-ciecyt" v-model="programaFiltro" class="form-select form-select-sm">
              <option :value="null">Todos los programas</option>
              <option v-for="programa in programas" :key="programa.id" :value="programa.id">{{ programa.programa }}</option>
            </select>
          </div>
          <div class="col-md-3 text-md-end">
            <span class="small text-muted">{{ proyectosFiltrados.length }} de {{ proyects.length }} proyectos</span>
          </div>
        </div>

        <div class="d-flex flex-wrap gap-2 mt-3">
          <button
            v-for="filtro in filtros"
            :key="filtro.id"
            type="button"
            class="btn btn-sm"
            :class="filtroActivo === filtro.id ? 'btn-primary' : 'btn-outline-secondary'"
            @click="filtroActivo = filtro.id"
          >
            {{ filtro.etiqueta }}
            <span class="badge ms-1" :class="filtroActivo === filtro.id ? 'bg-light text-dark' : 'bg-secondary'">
              {{ contarFiltro(filtro.id) }}
            </span>
          </button>
        </div>
      </div>
    </div>

    <jhi-loading v-if="isFetching"></jhi-loading>
    <div v-else-if="proyectosFiltrados.length === 0" class="alert alert-light border text-center text-muted">
      No hay proyectos en este filtro.
    </div>
    <div v-else class="table-responsive">
      <table class="table table-hover align-middle mb-0">
        <thead class="table-light">
          <tr>
            <th>Estado</th>
            <th>Propuesta</th>
            <th>Estudiante(s)</th>
            <th>Modalidad</th>
            <th>Asesor</th>
            <th>Jurado</th>
            <th>Última actividad</th>
            <th class="text-end">Acción</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="proyecto in proyectosFiltrados"
            :key="proyecto.id"
            :class="{ 'table-warning': esProyectoNuevo(proyecto) }"
          >
            <td>
              <span class="badge" :class="claseEstadoProyecto(proyecto.estado)">{{ etiquetaEstadoProyecto(proyecto.estado) }}</span>
              <div v-if="tieneContinuidadEspecial(proyecto)" class="small text-muted mt-1">
                {{ continuidadEtiquetaCiecyt(proyecto) }}
              </div>
            </td>
            <td>
              <button type="button" class="btn btn-link p-0 text-start fw-semibold text-decoration-none" @click="abrirRequisitos(proyecto)">
                {{ proyecto.titulo }}
              </button>
              <div class="small text-muted">
                <span v-if="esProyectoNuevo(proyecto)" class="badge-nuevo">Sin leer</span>
                #{{ proyecto.id }}<span v-if="proyecto.programa"> · {{ proyecto.programa }}</span>
              </div>
            </td>
            <td>
              <template v-if="estudiantesDe(proyecto).length">
                <div v-for="estudiante in estudiantesDe(proyecto)" :key="estudiante.id" class="mb-1">
                  <div>{{ nombreIntegrante(estudiante) }}</div>
                  <div class="small text-muted">{{ estudiante.integranteProyectoUserNuip || 'Sin documento' }}</div>
                </div>
              </template>
              <span v-else class="text-muted">—</span>
            </td>
            <td>{{ proyecto.proyectoModalidadModalidad || '—' }}</td>
            <td>
              <template v-if="asesorDe(proyecto)">
                <div>{{ nombreIntegrante(asesorDe(proyecto)) }}</div>
                <button type="button" class="btn btn-link btn-sm p-0" @click="irAAsignarAsesor(proyecto)">Cambiar</button>
              </template>
              <button v-else type="button" class="btn btn-outline-primary btn-sm" @click="irAAsignarAsesor(proyecto)">
                Asignar
              </button>
            </td>
            <td>
              <template v-if="juradosDe(proyecto).length">
                <div v-for="jurado in juradosDe(proyecto)" :key="jurado.id">{{ nombreIntegrante(jurado) }}</div>
                <button type="button" class="btn btn-link btn-sm p-0" @click="irAAsignarJurado(proyecto)">Cambiar</button>
              </template>
              <button
                v-else-if="proyecto.enviado"
                type="button"
                class="btn btn-outline-primary btn-sm"
                @click="irAAsignarJurado(proyecto)"
              >
                Asignar
              </button>
              <span v-else class="text-muted">—</span>
            </td>
            <td>{{ fechaHora(proyecto.fechaUltimoCambio) }}</td>
            <td class="text-end">
              <button
                type="button"
                class="btn btn-sm"
                :class="proyecto.estado === 'EN_VALIDACION_DOCUMENTAL' ? 'btn-success' : 'btn-primary'"
                @click="abrirRequisitos(proyecto)"
              >
                {{ proyecto.estado === 'EN_VALIDACION_DOCUMENTAL' ? 'Validar' : 'Gestionar' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <b-modal
      id="modal-requisitos"
      size="xl"
      scrollable
      ok-only
      ok-title="Cerrar"
      :title="proyectoRequisitos ? proyectoRequisitos.titulo : 'Detalle de la propuesta'"
      @hidden="cerrarRequisitos"
    >
      <div v-if="proyectoRequisitos">
        <div class="d-flex flex-wrap align-items-center gap-2 mb-2">
          <span class="badge" :class="claseEstadoProyecto(proyectoRequisitos.estado)">
            {{ etiquetaEstadoProyecto(proyectoRequisitos.estado) }}
          </span>
          <span class="small text-muted">
            #{{ proyectoRequisitos.id }} · {{ proyectoRequisitos.proyectoModalidadModalidad }} ·
            {{ proyectoRequisitos.programa }} · Última actividad: {{ fechaHora(proyectoRequisitos.fechaUltimoCambio) }}
          </span>
          <router-link
            class="btn btn-outline-secondary btn-sm ms-auto"
            :to="{ name: 'PropuestaInformacionGeneralNuevaEditView', params: { proyectoId: proyectoRequisitos.id } }"
          >
            Ver propuesta completa
          </router-link>
        </div>

        <div class="row g-3 mb-3">
          <div class="col-md-4">
            <div class="small text-muted">Estudiante(s)</div>
            <div v-for="estudiante in estudiantesDe(proyectoRequisitos)" :key="estudiante.id">
              {{ nombreIntegrante(estudiante) }}
              <span class="small text-muted">({{ estudiante.integranteProyectoUserNuip || 'sin documento' }})</span>
            </div>
            <span v-if="estudiantesDe(proyectoRequisitos).length === 0" class="text-muted">—</span>
          </div>
          <div class="col-md-4">
            <div class="small text-muted">Asesor</div>
            <div v-if="asesorDe(proyectoRequisitos)">{{ nombreIntegrante(asesorDe(proyectoRequisitos)) }}</div>
            <span v-else class="text-muted">Sin asignar</span>
          </div>
          <div class="col-md-4">
            <div class="small text-muted">Jurado</div>
            <div v-for="jurado in juradosDe(proyectoRequisitos)" :key="jurado.id">{{ nombreIntegrante(jurado) }}</div>
            <span v-if="juradosDe(proyectoRequisitos).length === 0" class="text-muted">Sin asignar</span>
          </div>
        </div>

        <ul class="nav nav-pills mb-3">
          <li v-for="seccion in seccionesDetalle" :key="seccion.id" class="nav-item">
            <button
              type="button"
              class="nav-link"
              :class="{ active: detalleSeccion === seccion.id }"
              @click="detalleSeccion = seccion.id"
            >
              {{ seccion.etiqueta }}
            </button>
          </li>
        </ul>

        <div v-if="detalleSeccion === 'documentos'">
          <div v-if="cargandoAdjuntos" class="text-muted">Cargando documentos...</div>
          <div v-else-if="adjuntos.length === 0" class="alert alert-warning mb-0">
            El estudiante no tiene documentos cargados para esta propuesta.
          </div>
          <div v-else class="list-group">
            <div
              v-for="adjunto in adjuntos"
              :key="adjunto.id"
              class="list-group-item d-flex flex-wrap justify-content-between align-items-center gap-2"
            >
              <div>
                <div class="fw-semibold">{{ nombreAdjunto(adjunto) }}</div>
                <div class="small text-muted">
                  {{ adjunto.adjuntoProyectoFaseFaseFase || 'Documento' }}
                  <span v-if="adjunto.nombreArchivoOriginal"> · {{ adjunto.nombreArchivoOriginal }}</span>
                  <span v-if="adjunto.fechaCreacion"> · {{ fechaCorta(adjunto.fechaCreacion) }}</span>
                </div>
              </div>
              <button type="button" class="btn btn-outline-primary btn-sm" @click="descargarAdjunto(adjunto)">
                <font-awesome-icon icon="download" /> Descargar
              </button>
            </div>
          </div>
        </div>

        <div v-if="detalleSeccion === 'requisitos'">
          <div class="d-flex flex-wrap justify-content-between align-items-center gap-2 mb-2">
            <div>
              <strong>{{ requisitosAprobados }} de {{ requisitos.length }}</strong> requisitos aprobados.
              <span v-if="requisitosObligatoriosPendientes > 0" class="text-danger">
                Faltan {{ requisitosObligatoriosPendientes }} obligatorios.
              </span>
            </div>
            <button
              v-if="requisitosPendientes.length > 0"
              type="button"
              class="btn btn-success btn-sm"
              :disabled="guardandoRequisito"
              @click="aprobarTodosRequisitos"
            >
              <font-awesome-icon icon="check-circle" /> Aprobar todos
            </button>
          </div>

          <div v-if="cargandoRequisitos" class="text-muted">Cargando requisitos...</div>
          <div v-else-if="requisitos.length === 0" class="alert alert-warning">
            Este proyecto todavía no tiene requisitos de inscripción cargados al sistema.
          </div>
          <div v-else>
            <div v-for="requisito in requisitos" :key="requisito.id" class="border rounded p-2 mb-2">
              <div class="d-flex justify-content-between align-items-start">
                <div>
                  <strong>{{ requisito.requisitoProyectoRequisitoNombre }}</strong>
                  <div class="text-muted small">{{ requisito.requisitoProyectoRequisitoCodigo }}</div>
                  <span v-if="requisito.requisitoProyectoRequisitoObligatorio" class="badge bg-dark mt-1">Obligatorio</span>
                </div>
                <span class="badge" :class="claseEstadoRequisito(requisito)">{{ etiquetaEstadoRequisito(requisito) }}</span>
              </div>

              <div class="small mt-2 text-muted">
                <span v-if="requisito.fechaEntrega">Entregado el {{ dateToString(requisito.fechaEntrega) }}</span>
                <span v-if="requisito.validadoPor"> · validado por {{ requisito.validadoPor }}</span>
                <span v-if="requisito.fechaValidacion"> el {{ dateToString(requisito.fechaValidacion) }}</span>
              </div>

              <div class="mt-2">
                <label class="small mb-0" :for="'observacion-' + requisito.id">
                  Observación<span v-if="esCampoRequisito(requisito)"> / dato entregado</span>
                </label>
                <textarea
                  :id="'observacion-' + requisito.id"
                  v-model="observacionesRequisito[requisito.id]"
                  class="form-control form-control-sm"
                  rows="2"
                ></textarea>
              </div>

              <div class="btn-group mt-2">
                <button
                  type="button"
                  class="btn btn-success btn-sm"
                  :disabled="guardandoRequisito"
                  @click="validarRequisito(requisito, true)"
                >
                  Aprobar
                </button>
                <button
                  type="button"
                  class="btn btn-danger btn-sm"
                  :disabled="guardandoRequisito"
                  @click="validarRequisito(requisito, false)"
                >
                  Rechazar
                </button>
              </div>
            </div>
          </div>

          <div v-if="requisitosAdicionalesACargar.length > 0" class="alert alert-info mt-3">
            <strong>Documentos adicionales según Acuerdo 25, art. 5 par. 2 y Tabla 3.</strong>
            <ul class="mb-0 mt-2">
              <li v-for="requisito in requisitosAdicionalesACargar" :key="requisito.codigo">
                <strong>{{ requisito.nombre }}</strong>
                <span v-if="requisito.firmantes.length > 0"> (firmado por {{ requisito.firmantes.join(', ') }})</span>
                <div class="small text-muted">{{ requisito.descripcion }}</div>
              </li>
            </ul>
          </div>

          <div
            v-if="proyectoRequisitos.estado === 'EN_VALIDACION_DOCUMENTAL'"
            class="border rounded p-3 mt-3 bg-light"
          >
            <h5 class="mb-2">Validación documental</h5>
            <p class="small text-muted mb-2">
              Cuando todos los requisitos obligatorios estén aprobados, habilite la propuesta para que el estudiante solicite
              el aval del asesor. Si falta algo, registre la observación y devuélvala.
            </p>
            <label class="small mb-0" for="observacion-documentacion">Observación general (obligatoria al observar)</label>
            <textarea
              id="observacion-documentacion"
              v-model="observacionDocumentacion"
              class="form-control form-control-sm mb-2"
              rows="2"
            ></textarea>
            <div class="btn-group">
              <button
                type="button"
                class="btn btn-success btn-sm"
                :disabled="guardandoDocumentacion"
                @click="validarDocumentacionProyecto(true)"
              >
                Habilitar propuesta
              </button>
              <button
                type="button"
                class="btn btn-warning btn-sm"
                :disabled="guardandoDocumentacion"
                @click="validarDocumentacionProyecto(false)"
              >
                Observar documentación
              </button>
            </div>
          </div>
        </div>

        <div v-if="detalleSeccion === 'gestion'">
          <div class="row g-3">
            <div class="col-md-6">
              <h6>Asignaciones</h6>
              <div class="d-flex flex-wrap gap-2">
                <button type="button" class="btn btn-outline-primary btn-sm" @click="irAAsignarAsesor(proyectoRequisitos)">
                  Asignar/cambiar asesor
                </button>
                <button type="button" class="btn btn-outline-primary btn-sm" @click="irAAsignarJurado(proyectoRequisitos)">
                  Asignar/cambiar jurado
                </button>
              </div>
            </div>
            <div class="col-md-6">
              <h6>Acto público</h6>
              <div v-if="esListoParaProgramarActo(proyectoRequisitos)">
                <div class="d-flex gap-2">
                  <input
                    v-model="fechasActo[proyectoRequisitos.id]"
                    type="date"
                    class="form-control form-control-sm"
                  />
                  <button
                    type="button"
                    class="btn btn-primary btn-sm"
                    :disabled="!fechasActo[proyectoRequisitos.id]"
                    @click="programarActo(proyectoRequisitos)"
                  >
                    Programar
                  </button>
                </div>
                <div class="small text-muted mt-1">{{ nombreActo(proyectoRequisitos.proyectoModalidadId) }}</div>
              </div>
              <div v-else-if="esActoProgramado(proyectoRequisitos)">
                <div class="mb-2">
                  {{ fechaActo(proyectoRequisitos) }} · {{ nombreActo(proyectoRequisitos.proyectoModalidadId) }}
                </div>
                <button type="button" class="btn btn-success btn-sm" @click="registrarActoRealizado(proyectoRequisitos)">
                  Registrar realizada
                </button>
              </div>
              <div v-else class="small text-muted">No aplica en este estado.</div>
            </div>

            <div class="col-12">
              <h6>Continuidad</h6>
              <div class="d-flex flex-wrap align-items-center gap-2">
                <span class="badge" :class="continuidadBadge(proyectoRequisitos)">
                  {{ continuidadEtiquetaCiecyt(proyectoRequisitos) }}
                </span>
                <button
                  v-if="!proyectoRequisitos.estadoContinuidad || proyectoRequisitos.estadoContinuidad === 'REGULAR'"
                  type="button"
                  class="btn btn-primary btn-sm"
                  @click="iniciarContinuidad(proyectoRequisitos)"
                >
                  Iniciar continuidad
                </button>
                <button
                  v-if="
                    proyectoRequisitos.estadoContinuidad === 'CONTINUIDAD' &&
                    !proyectoRequisitos.continuidadPeriodoAdicional
                  "
                  type="button"
                  class="btn btn-outline-secondary btn-sm"
                  @click="otorgarAplazamiento(proyectoRequisitos)"
                >
                  Otorgar aplazamiento
                </button>
                <button
                  v-if="
                    proyectoRequisitos.estadoContinuidad === 'CONTINUIDAD' ||
                    proyectoRequisitos.estadoContinuidad === 'APLAZADO'
                  "
                  type="button"
                  class="btn btn-warning btn-sm"
                  :disabled="renovarBloqueado(proyectoRequisitos)"
                  @click="registrarRenovacionContinuidad(proyectoRequisitos)"
                >
                  Renovar ({{ proyectoRequisitos.periodosContinuidadUsados || 0 }}/{{ marcadorContinuidadCiecyt(proyectoRequisitos) }})
                </button>
              </div>
              <div v-if="alertaLimiteActiva(proyectoRequisitos)" class="alert alert-danger mt-2 mb-0 small">
                <strong>Límite de continuidad alcanzado.</strong>
                Han transcurrido los {{ resumenLimiteCiecyt(proyectoRequisitos).totalPeriodos }} periodos académicos del
                Acuerdo 25. Si la sustentación o socialización no se realiza, el estudiante pierde el derecho a optar al grado.
              </div>
            </div>

            <div class="col-12">
              <h6>Plazos</h6>
              <div v-if="plazosDelProyecto(proyectoRequisitos).length === 0" class="small text-muted">Sin plazos activos.</div>
              <div v-for="plazo in plazosDelProyecto(proyectoRequisitos)" :key="plazo.id" class="mb-1">
                <span class="small text-muted">{{ plazo.etiqueta }}:</span>
                <span class="badge" :class="'bg-' + plazo.variant">{{ plazo.estado }}</span>
              </div>
            </div>
          </div>
        </div>

        <div v-if="detalleSeccion === 'historial'">
          <div v-if="cargandoHistorial" class="text-muted">Cargando historial...</div>
          <div v-else-if="historial.length === 0" class="text-muted">Sin movimientos registrados.</div>
          <ul v-else class="list-unstyled mb-0">
            <li v-for="movimiento in historial" :key="movimiento.id" class="border-start ps-3 pb-3">
              <div class="fw-semibold">{{ etiquetaEstadoProyecto(movimiento.estadoNuevo) }}</div>
              <div class="small text-muted">
                {{ fechaHora(movimiento.fechaCambio) }} · {{ movimiento.usuarioLogin || 'sistema' }}
              </div>
              <div v-if="movimiento.observacion" class="small">{{ movimiento.observacion }}</div>
            </li>
          </ul>
        </div>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import NotificacionService from '@/shared/alert/notificacion.service';
import { INotificacion } from '@/shared/model/notificacion.model';
import { IProyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IPrograma } from '@/shared/model/programa.model';
import ProgramaService from '@/entities/programa/programa.service';
import RequisitoProyectoService from '@/entities/requisito-proyecto/requisito-proyecto.service';
import { IRequisitoProyecto } from '@/shared/model/requisito-proyecto.model';
import { EnumEstadoRequisito } from '@/shared/model/enumerations/enum-estado-requisito.model';
import { TipoRequisito } from '@/shared/model/enumerations/tipo-requisito.model';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';
import { IAdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import ProyectoHistorialEstadoService from '@/entities/proyecto-historial-estado/proyecto-historial-estado.service';
import { IProyectoHistorialEstado } from '@/shared/model/proyecto-historial-estado.model';
import { jsPDF } from 'jspdf';
import { estadoActoProgramado, estadoListoParaActo, requiereSustentacion } from '@/shared/config/opcion_grado';
import { PLAZOS_ACUERDO_25, evaluarPlazo, etiquetaPlazo, variantePlazo } from '@/shared/composables/dias-habiles';
import { continuidadEtiqueta, marcadorContinuidad, resumenLimite as resumenLimiteContinuidad } from '@/shared/utils/continuidad-proyecto';
import { requisitosAdicionalesPorModalidad, RequisitoAdicional } from '@/shared/config/requisitos-modalidad';

@Component
export default class BandejaCiecyt extends Vue {
  @Inject
  private proyectoService: () => ProyectoService;

  @Inject
  private programaService: () => ProgramaService;

  @Inject
  private requisitoProyectoService: () => RequisitoProyectoService;

  @Inject
  private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;

  @Inject
  private proyectoHistorialEstadoService: () => ProyectoHistorialEstadoService;

  @Inject
  private notificacionService: () => NotificacionService;

  @Inject
  private alertService: () => AlertService;

  public proyects: IProyecto[] = [];
  public programas: IPrograma[] = [];
  public isFetching = false;

  public busqueda = '';
  public programaFiltro: number | null = null;
  public filtroActivo = 'todos';
  public filtros = [
    { id: 'todos', etiqueta: 'Todos' },
    { id: 'validar', etiqueta: 'Por validar' },
    { id: 'jurado', etiqueta: 'Por asignar jurado' },
    { id: 'acto', etiqueta: 'Por programar acto' },
    { id: 'continuidad', etiqueta: 'Continuidad' },
  ];

  public seccionesDetalle = [
    { id: 'requisitos', etiqueta: 'Requisitos' },
    { id: 'documentos', etiqueta: 'Documentos' },
    { id: 'gestion', etiqueta: 'Gestión' },
    { id: 'historial', etiqueta: 'Historial' },
  ];
  public detalleSeccion = 'requisitos';

  public proyectoRequisitos: IProyecto = null;
  public requisitos: IRequisitoProyecto[] = [];
  public observacionesRequisito: { [id: number]: string } = {};
  public cargandoRequisitos = false;
  public guardandoRequisito = false;
  public guardandoDocumentacion = false;
  public observacionDocumentacion = '';

  public adjuntos: IAdjuntoProyectoFase[] = [];
  public cargandoAdjuntos = false;
  public historial: IProyectoHistorialEstado[] = [];
  public cargandoHistorial = false;

  public notificacionesNoLeidas: INotificacion[] = [];
  public proyectosNuevos: number[] = [];
  public fechasActo: { [proyectoId: number]: string } = {};

  public mounted(): void {
    this.retrieveAllProyectos();
    this.cargarNotificacionesNoLeidas();
  }

  public retrieveAllProyectos(): void {
    this.isFetching = true;
    this.proyectoService()
      .retrieveAllProyectosIntegrantes()
      .then(res => {
        this.proyects = res.data || [];
        this.isFetching = false;
      })
      .catch(err => {
        this.isFetching = false;
        this.alertService().showHttpError(this, err.response);
      });

    this.programaService()
      .retrieve()
      .then(res => {
        this.programas = res.data || [];
      })
      .catch(() => {
        this.programas = [];
      });
  }

  public get proyectosFiltrados(): IProyecto[] {
    const texto = (this.busqueda || '').trim().toLowerCase();
    return this.proyects.filter(proyecto => {
      if (!this.coincideFiltro(proyecto, this.filtroActivo)) {
        return false;
      }
      if (this.programaFiltro && proyecto.proyectoProgramaId !== this.programaFiltro) {
        return false;
      }
      if (!texto) {
        return true;
      }
      const estudiantes = this.estudiantesDe(proyecto)
        .map(estudiante => this.nombreIntegrante(estudiante))
        .join(' ');
      return [proyecto.titulo, proyecto.programa, estudiantes].some(valor => (valor || '').toLowerCase().includes(texto));
    });
  }

  public contarFiltro(filtroId: string): number {
    return this.proyects.filter(proyecto => this.coincideFiltro(proyecto, filtroId)).length;
  }

  public coincideFiltro(proyecto: IProyecto, filtroId: string): boolean {
    switch (filtroId) {
      case 'validar':
        return proyecto.estado === 'EN_VALIDACION_DOCUMENTAL';
      case 'jurado':
        return this.juradosDe(proyecto).length === 0 && !!proyecto.enviado;
      case 'acto':
        return this.esListoParaProgramarActo(proyecto);
      case 'continuidad':
        return this.tieneContinuidadEspecial(proyecto) || this.alertaLimiteActiva(proyecto);
      default:
        return true;
    }
  }

  public tieneContinuidadEspecial(proyecto: IProyecto): boolean {
    return !!proyecto.estadoContinuidad && String(proyecto.estadoContinuidad) !== 'REGULAR';
  }

  public estudiantesDe(proyecto: IProyecto): any[] {
    return ((proyecto && proyecto.listaIntegrantesProyecto) || []).filter(
      integrante => integrante.integranteProyectoRolesModalidadRol === 'Estudiante'
    );
  }

  public asesorDe(proyecto: IProyecto): any {
    return (
      ((proyecto && proyecto.listaIntegrantesProyecto) || []).find(
        integrante => integrante.integranteProyectoRolesModalidadRol === 'Asesor'
      ) || null
    );
  }

  public juradosDe(proyecto: IProyecto): any[] {
    return ((proyecto && proyecto.listaIntegrantesProyecto) || []).filter(
      integrante => integrante.integranteProyectoRolesModalidadRol === 'Jurado'
    );
  }

  public nombreIntegrante(integrante: any): string {
    if (!integrante) {
      return '';
    }
    const nombre = `${integrante.integranteProyectoUserFirstName || ''} ${integrante.integranteProyectoUserLastName || ''}`.trim();
    return nombre || integrante.integranteProyectoUserLogin || 'Sin nombre';
  }

  public etiquetaEstadoProyecto(estado: string | undefined): string {
    switch (estado) {
      case 'PREINSCRITA':
        return 'Preinscrita';
      case 'EN_VALIDACION_DOCUMENTAL':
        return 'En validación documental';
      case 'OBSERVACIONES_DOCUMENTACION':
        return 'Documentación observada';
      case 'HABILITADO':
        return 'Habilitada';
      case 'EN_ELABORACION_PROPUESTA':
        return 'En elaboración de la propuesta';
      case 'EN_ELABORACION_PROYECTO':
        return 'En elaboración del proyecto';
      case 'EN_REVISION_ASESOR':
        return 'En revisión del asesor';
      case 'CORRECCIONES_ASESOR':
        return 'Correcciones del asesor';
      case 'APROBADA_POR_ASESOR':
        return 'Aprobada por el asesor';
      case 'EN_REVISION_JURADO_PROPUESTA':
        return 'En revisión del jurado';
      case 'CORRECCIONES_JURADO_PROPUESTA':
        return 'Correcciones del jurado';
      case 'EN_REVISION_ASESOR_PROYECTO':
        return 'En revisión del asesor (proyecto)';
      case 'CORRECCIONES_ASESOR_PROYECTO':
        return 'Correcciones del asesor (proyecto)';
      case 'EN_REVISION_JURADO_PROYECTO':
        return 'En revisión del jurado (proyecto)';
      case 'CORRECCIONES_JURADO_PROYECTO':
        return 'Correcciones del jurado (proyecto)';
      case 'AJUSTES_SUSTENTACION':
        return 'Ajustes de sustentación';
      case 'NO_VIABLE':
        return 'No viable';
      case 'VIABLE':
        return 'Viable';
      case 'LISTO_PARA_SUSTENTAR':
        return 'Lista para sustentar';
      case 'SUSTENTACION_PROGRAMADA':
        return 'Sustentación programada';
      case 'SUSTENTACION_REALIZADA':
        return 'Sustentación realizada';
      case 'EN_EVALUACION_SUSTENTACION':
        return 'En evaluación de sustentación';
      case 'LISTO_PARA_SOCIALIZAR':
        return 'Lista para socializar';
      case 'SOCIALIZACION_PROGRAMADA':
        return 'Socialización programada';
      case 'SOCIALIZACION_REALIZADA':
        return 'Socialización realizada';
      case 'EN_EVALUACION_SOCIALIZACION':
        return 'En evaluación de socialización';
      case 'NOTA_DEFINITIVA':
        return 'Nota definitiva';
      case 'FINALIZADO':
        return 'Finalizado';
      default:
        return estado ? estado.replace(/_/g, ' ') : 'Sin estado';
    }
  }

  public claseEstadoProyecto(estado: string | undefined): string {
    switch (estado) {
      case 'EN_VALIDACION_DOCUMENTAL':
      case 'CORRECCIONES_ASESOR':
      case 'CORRECCIONES_JURADO_PROPUESTA':
      case 'CORRECCIONES_ASESOR_PROYECTO':
      case 'CORRECCIONES_JURADO_PROYECTO':
      case 'AJUSTES_SUSTENTACION':
        return 'bg-warning text-dark';
      case 'OBSERVACIONES_DOCUMENTACION':
      case 'NO_VIABLE':
        return 'bg-danger';
      case 'HABILITADO':
      case 'VIABLE':
      case 'APROBADA_POR_ASESOR':
        return 'bg-success';
      case 'EN_REVISION_ASESOR':
      case 'EN_REVISION_JURADO_PROPUESTA':
      case 'EN_REVISION_ASESOR_PROYECTO':
      case 'EN_REVISION_JURADO_PROYECTO':
      case 'EN_EVALUACION_SUSTENTACION':
      case 'EN_EVALUACION_SOCIALIZACION':
        return 'bg-info text-dark';
      case 'LISTO_PARA_SUSTENTAR':
      case 'SUSTENTACION_PROGRAMADA':
      case 'LISTO_PARA_SOCIALIZAR':
      case 'SOCIALIZACION_PROGRAMADA':
        return 'bg-primary';
      case 'FINALIZADO':
      case 'NOTA_DEFINITIVA':
        return 'bg-secondary';
      default:
        return 'bg-secondary';
    }
  }

  public fechaHora(value: any): string {
    if (!value) {
      return '—';
    }
    const fecha = new Date(value);
    if (isNaN(fecha.getTime())) {
      return '—';
    }
    return fecha.toLocaleString('es-CO', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  public fechaCorta(value: any): string {
    if (!value) {
      return '';
    }
    const fecha = new Date(value);
    if (isNaN(fecha.getTime())) {
      return '';
    }
    return fecha.toLocaleDateString('es-CO', { day: '2-digit', month: '2-digit', year: 'numeric' });
  }

  public esProyectoNuevo(proyecto: any): boolean {
    return !!proyecto && this.proyectosNuevos.indexOf(proyecto.id) >= 0;
  }

  public cargarNotificacionesNoLeidas(): void {
    this.notificacionService()
      .findNoLeidas()
      .then(lista => {
        this.notificacionesNoLeidas = lista || [];
        this.proyectosNuevos = this.notificacionesNoLeidas
          .map(notificacion => notificacion.proyectoId)
          .filter(proyectoId => proyectoId != null);
      })
      .catch(() => {
        this.notificacionesNoLeidas = [];
        this.proyectosNuevos = [];
      });
  }

  public marcarNotificacionesProyecto(proyecto: any): void {
    if (!proyecto) {
      return;
    }
    this.notificacionesNoLeidas
      .filter(notificacion => notificacion.proyectoId === proyecto.id && notificacion.id != null)
      .forEach(notificacion => {
        this.notificacionService().marcarLeida(notificacion.id).catch(() => {});
      });
    this.notificacionesNoLeidas = this.notificacionesNoLeidas.filter(notificacion => notificacion.proyectoId !== proyecto.id);
    this.proyectosNuevos = this.proyectosNuevos.filter(proyectoId => proyectoId !== proyecto.id);
  }

  public abrirRequisitos(proyecto: IProyecto): void {
    this.marcarNotificacionesProyecto(proyecto);
    this.proyectoRequisitos = proyecto;
    this.detalleSeccion = proyecto.estado === 'EN_VALIDACION_DOCUMENTAL' ? 'requisitos' : 'documentos';
    this.requisitos = [];
    this.observacionesRequisito = {};
    this.observacionDocumentacion = '';
    this.guardandoDocumentacion = false;
    this.cargandoRequisitos = true;
    this.adjuntos = [];
    this.cargandoAdjuntos = true;
    this.historial = [];
    this.cargandoHistorial = true;
    (this.$bvModal as any).show('modal-requisitos');
    this.cargarRequisitos(proyecto.id);
    this.cargarAdjuntos(proyecto.id);
    this.cargarHistorial(proyecto.id);
  }

  public cerrarRequisitos(): void {
    this.proyectoRequisitos = null;
    this.requisitos = [];
    this.observacionesRequisito = {};
    this.observacionDocumentacion = '';
    this.adjuntos = [];
    this.historial = [];
  }

  public cargarRequisitos(proyectoId: number): void {
    this.requisitoProyectoService()
      .findByProyecto(proyectoId)
      .then(lista => {
        this.requisitos = lista || [];
        this.requisitos.forEach(requisito => {
          this.observacionesRequisito[requisito.id] = requisito.observacion;
        });
        this.cargandoRequisitos = false;
      })
      .catch(() => {
        this.cargandoRequisitos = false;
        this.alertService().showAlert('No se pudieron cargar los requisitos del proyecto', 'danger');
      });
  }

  public cargarAdjuntos(proyectoId: number): void {
    this.adjuntoProyectoFaseService()
      .findByProyecto(proyectoId)
      .then(lista => {
        this.adjuntos = lista || [];
        this.cargandoAdjuntos = false;
      })
      .catch(() => {
        this.cargandoAdjuntos = false;
        this.adjuntos = [];
      });
  }

  public cargarHistorial(proyectoId: number): void {
    this.proyectoHistorialEstadoService()
      .findByProyecto(proyectoId)
      .then(lista => {
        this.historial = lista || [];
        this.cargandoHistorial = false;
      })
      .catch(() => {
        this.cargandoHistorial = false;
        this.historial = [];
      });
  }

  public nombreAdjunto(adjunto: IAdjuntoProyectoFase): string {
    return adjunto.nombreAdjunto || adjunto.nombreArchivoOriginal || adjunto.file || `Documento ${adjunto.id}`;
  }

  public descargarAdjunto(adjunto: IAdjuntoProyectoFase): void {
    this.adjuntoProyectoFaseService().downloadFile(adjunto.id, this.nombreAdjunto(adjunto));
  }

  public get requisitosAprobados(): number {
    return this.requisitos.filter(requisito => requisito.estado === EnumEstadoRequisito.APROBADO).length;
  }

  public get requisitosPendientes(): IRequisitoProyecto[] {
    return this.requisitos.filter(requisito => requisito.estado !== EnumEstadoRequisito.APROBADO);
  }

  public get requisitosObligatoriosPendientes(): number {
    return this.requisitos.filter(
      requisito => requisito.requisitoProyectoRequisitoObligatorio && requisito.estado !== EnumEstadoRequisito.APROBADO
    ).length;
  }

  public get requisitosAdicionalesACargar(): RequisitoAdicional[] {
    if (!this.proyectoRequisitos) {
      return [];
    }
    return requisitosAdicionalesPorModalidad(this.proyectoRequisitos.proyectoModalidadId);
  }

  public async aprobarTodosRequisitos(): Promise<void> {
    const pendientes = this.requisitosPendientes.slice();
    if (pendientes.length === 0) {
      return;
    }
    if (!window.confirm(`¿Aprobar ${pendientes.length} requisito(s) pendiente(s)?`)) {
      return;
    }
    this.guardandoRequisito = true;
    for (const requisito of pendientes) {
      try {
        const actualizado = await this.requisitoProyectoService().validar(
          requisito.id,
          true,
          this.observacionesRequisito[requisito.id] || ''
        );
        const indice = this.requisitos.findIndex(item => item.id === actualizado.id);
        if (indice >= 0) {
          this.requisitos.splice(indice, 1, actualizado);
        }
        this.observacionesRequisito[actualizado.id] = actualizado.observacion;
      } catch {
        this.alertService().showAlert(
          `No se pudo aprobar el requisito ${requisito.requisitoProyectoRequisitoNombre || requisito.id}`,
          'danger'
        );
      }
    }
    this.guardandoRequisito = false;
    this.alertService().showAlert('Requisitos aprobados', 'success');
  }

  public validarRequisito(requisito: IRequisitoProyecto, aprobado: boolean): void {
    const observacion = this.observacionesRequisito[requisito.id];
    if (!aprobado && !observacion) {
      this.alertService().showAlert('Indique la observación del rechazo', 'warning');
      return;
    }

    this.guardandoRequisito = true;
    this.requisitoProyectoService()
      .validar(requisito.id, aprobado, observacion)
      .then(actualizado => {
        this.guardandoRequisito = false;
        const indice = this.requisitos.findIndex(item => item.id === actualizado.id);
        if (indice >= 0) {
          this.requisitos.splice(indice, 1, actualizado);
        }
        this.observacionesRequisito[actualizado.id] = actualizado.observacion;
        this.alertService().showAlert(aprobado ? 'Requisito aprobado' : 'Requisito rechazado', 'success');
      })
      .catch(() => {
        this.guardandoRequisito = false;
        this.alertService().showAlert('No se pudo validar el requisito', 'danger');
      });
  }

  public validarDocumentacionProyecto(aprobado: boolean): void {
    if (!this.proyectoRequisitos) {
      return;
    }
    if (!aprobado && !this.observacionDocumentacion) {
      this.alertService().showAlert('Indique la observación de la documentación', 'warning');
      return;
    }

    this.guardandoDocumentacion = true;
    this.proyectoService()
      .validarDocumentacion(this.proyectoRequisitos.id, aprobado, this.observacionDocumentacion)
      .then(() => {
        this.guardandoDocumentacion = false;
        (this.$bvModal as any).hide('modal-requisitos');
        this.alertService().showAlert(
          aprobado
            ? 'Documentación aprobada: propuesta habilitada para solicitar el aval del asesor'
            : 'Documentación observada: se notificó al estudiante para que corrija y vuelva a radicar',
          'success'
        );
        this.retrieveAllProyectos();
      })
      .catch(err => {
        this.guardandoDocumentacion = false;
        const detalle = err && err.response && err.response.data ? err.response.data.detail : null;
        this.alertService().showAlert(detalle || 'No se pudo validar la documentación', 'danger');
      });
  }

  public esCampoRequisito(requisito: IRequisitoProyecto): boolean {
    return requisito.requisitoProyectoRequisitoTipo === TipoRequisito.CAMPO;
  }

  public etiquetaEstadoRequisito(requisito: IRequisitoProyecto): string {
    switch (requisito.estado) {
      case EnumEstadoRequisito.APROBADO:
        return 'Aprobado';
      case EnumEstadoRequisito.ENTREGADO:
        return 'Entregado';
      case EnumEstadoRequisito.RECHAZADO:
        return 'Rechazado';
      default:
        return 'Pendiente';
    }
  }

  public claseEstadoRequisito(requisito: IRequisitoProyecto): string {
    switch (requisito.estado) {
      case EnumEstadoRequisito.APROBADO:
        return 'bg-success';
      case EnumEstadoRequisito.ENTREGADO:
        return 'bg-info text-dark';
      case EnumEstadoRequisito.RECHAZADO:
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }

  public irAAsignarAsesor(proyecto: IProyecto): void {
    this.$router.push({ name: 'AsignarAsesorView', params: { proyectoId: proyecto.id } });
  }

  public irAAsignarJurado(proyecto: IProyecto): void {
    this.$router.push({ name: 'AsignarJuradoView', params: { proyectoId: proyecto.id } });
  }

  public continuidadEtiquetaCiecyt(proyecto: IProyecto): string {
    return continuidadEtiqueta(proyecto);
  }

  public marcadorContinuidadCiecyt(proyecto: IProyecto): string {
    return marcadorContinuidad(proyecto);
  }

  public resumenLimiteCiecyt(proyecto: IProyecto) {
    return resumenLimiteContinuidad(proyecto);
  }

  public alertaLimiteActiva(proyecto: IProyecto): boolean {
    if (String(proyecto.estadoContinuidad) === 'CONTINUIDAD_PERDIDA') {
      return false;
    }
    return resumenLimiteContinuidad(proyecto).alLimite;
  }

  public renovarBloqueado(proyecto: IProyecto): boolean {
    const resumen = resumenLimiteContinuidad(proyecto);
    return resumen.alLimite || resumen.excedido;
  }

  public continuidadBadge(proyecto: IProyecto): string {
    const resumen = resumenLimiteContinuidad(proyecto);
    if (String(proyecto.estadoContinuidad) === 'CONTINUIDAD_PERDIDA') {
      return 'bg-danger';
    }
    if (resumen.excedido) {
      return 'bg-danger';
    }
    if (resumen.alLimite) {
      return 'bg-warning text-dark';
    }
    if (String(proyecto.estadoContinuidad) === 'CONTINUIDAD') {
      return 'bg-info text-dark';
    }
    return 'bg-secondary';
  }

  public plazosDelProyecto(proyecto: IProyecto): Array<{ id: string; etiqueta: string; estado: string; variant: string }> {
    const plazos: Array<{ id: string; etiqueta: string; diasPlazo: number; fechaInicio: string | Date | undefined }> = [];

    if (proyecto.fechaInicioContinuidad) {
      const totalPeriodos = proyecto.continuidadPeriodoAdicional ? 4 : 3;
      plazos.push({
        id: 'continuidad',
        etiqueta: `Periodo de continuidad (${totalPeriodos} hábiles)`,
        diasPlazo: totalPeriodos,
        fechaInicio: proyecto.fechaInicioContinuidad,
      });
    }

    if (proyecto.fechaSustentacionProyecto && proyecto.estado === 'SUSTENTACION_PROGRAMADA') {
      plazos.push({
        id: 'sustentacion',
        etiqueta: 'Realizar sustentación (10 hábiles)',
        diasPlazo: PLAZOS_ACUERDO_25.EVALUACION_DOCUMENTO_FINAL,
        fechaInicio: proyecto.fechaSustentacionProyecto,
      });
    }

    return plazos.map(plazo => {
      const estado = evaluarPlazo(plazo.fechaInicio, plazo.diasPlazo);
      return {
        id: plazo.id,
        etiqueta: plazo.etiqueta,
        estado: etiquetaPlazo(estado),
        variant: variantePlazo(estado),
      };
    });
  }

  public nombreActo(modalidadId: number | undefined | null): string {
    return requiereSustentacion(modalidadId) ? 'Sustentación' : 'Socialización';
  }

  public esListoParaProgramarActo(proyecto: IProyecto): boolean {
    return proyecto.estado === estadoListoParaActo(proyecto.proyectoModalidadId);
  }

  public esActoProgramado(proyecto: IProyecto): boolean {
    return proyecto.estado === estadoActoProgramado(proyecto.proyectoModalidadId);
  }

  public fechaActo(proyecto: IProyecto): string {
    return proyecto.fechaSustentacionProyecto ? this.dateToString(proyecto.fechaSustentacionProyecto) : 'sin fecha';
  }

  public dateToString(value: any): string {
    if (!value) {
      return '';
    }
    return typeof value === 'string' ? value.substring(0, 10) : String(value).substring(0, 10);
  }

  public programarActo(proyecto: IProyecto): void {
    const fecha = this.fechasActo[proyecto.id];
    if (!fecha) {
      this.alertService().showAlert('Seleccione la fecha del acto público.', 'danger');
      return;
    }
    const tipo = this.nombreActo(proyecto.proyectoModalidadId).toLowerCase();
    this.proyectoService()
      .programarActo(proyecto.id, fecha)
      .then(() => {
        this.alertService().showAlert(`${tipo} programada para el ${fecha}.`, 'success');
        this.cerrarModalYRecargar();
      })
      .catch(err => {
        this.alertService().showAlert(this.errorMsgContinuidad(err), 'danger');
      });
  }

  public registrarActoRealizado(proyecto: IProyecto): void {
    const tipo = this.nombreActo(proyecto.proyectoModalidadId).toLowerCase();
    this.proyectoService()
      .registrarActoRealizado(proyecto.id)
      .then(() => {
        this.alertService().showAlert(
          `${tipo} registrada como realizada. El responsable de evaluarla fue notificado.`,
          'success'
        );
        this.cerrarModalYRecargar();
      })
      .catch(err => {
        this.alertService().showAlert(this.errorMsgContinuidad(err), 'danger');
      });
  }

  public iniciarContinuidad(proyecto: IProyecto): void {
    this.proyectoService()
      .iniciarContinuidad(proyecto.id)
      .then(() => {
        this.alertService().showAlert(
          'Continuidad iniciada: el estudiante dispone de tres (3) periodos académicos (Acuerdo 025, art. 10, parágrafo 3).',
          'success'
        );
        this.cerrarModalYRecargar();
      })
      .catch(err => {
        this.alertService().showAlert(this.errorMsgContinuidad(err), 'danger');
      });
  }

  public registrarRenovacionContinuidad(proyecto: IProyecto): void {
    this.proyectoService()
      .registrarRenovacionContinuidad(proyecto.id)
      .then(() => {
        this.alertService().showAlert('Renovación de continuidad registrada (Acuerdo 025, art. 10, parágrafo 4).', 'success');
        this.cerrarModalYRecargar();
      })
      .catch(err => {
        this.alertService().showAlert(this.errorMsgContinuidad(err), 'danger');
      });
  }

  public otorgarAplazamiento(proyecto: IProyecto): void {
    this.proyectoService()
      .otorgarAplazamientoContinuidad(proyecto.id)
      .then(() => {
        this.alertService().showAlert(
          'Periodo académico adicional otorgado: el estudiante dispone de 3+1 periodos de continuidad (Acuerdo 025, art. 10, parágrafo 6).',
          'success'
        );
        this.cerrarModalYRecargar();
      })
      .catch(err => {
        this.alertService().showAlert(this.errorMsgContinuidad(err), 'danger');
      });
  }

  public errorMsgContinuidad(err: any): any {
    return (
      (err && err.response && err.response.data && (err.response.data.title || err.response.data.message)) ||
      (err && err.message) ||
      err
    );
  }

  private cerrarModalYRecargar(): void {
    (this.$bvModal as any).hide('modal-requisitos');
    this.retrieveAllProyectos();
  }

  public downloadPdf(): void {
    const doc = new jsPDF();
    doc.setFontSize(14);
    doc.text('Bandeja del CIECYT - Proyectos y propuestas', 10, 15);
    doc.setFontSize(9);
    let y = 25;
    this.proyectosFiltrados.forEach((proyecto, index) => {
      if (y > 270) {
        doc.addPage();
        y = 20;
      }
      doc.text(`${index + 1}. ${proyecto.titulo || 'Sin título'} (#${proyecto.id})`, 10, y);
      y += 5;
      doc.text(
        `Estado: ${this.etiquetaEstadoProyecto(proyecto.estado)} | Modalidad: ${proyecto.proyectoModalidadModalidad || '—'}`,
        10,
        y
      );
      y += 5;
      doc.text(
        `Estudiante(s): ${this.estudiantesDe(proyecto).map(estudiante => this.nombreIntegrante(estudiante)).join(', ') || '—'}`,
        10,
        y
      );
      y += 5;
      const asesor = this.asesorDe(proyecto);
      const jurados = this.juradosDe(proyecto).map(jurado => this.nombreIntegrante(jurado)).join(', ');
      doc.text(`Asesor: ${asesor ? this.nombreIntegrante(asesor) : 'Sin asignar'} | Jurado: ${jurados || 'Sin asignar'}`, 10, y);
      y += 5;
      doc.text(`Última actividad: ${this.fechaHora(proyecto.fechaUltimoCambio)}`, 10, y);
      y += 8;
    });
    doc.save('Bandeja CIECYT - Proyectos y propuestas.pdf');
  }
}
</script>

<style scoped>
.ciecyt-bandeja {
  padding-bottom: 1.5rem;
}

.badge-nuevo {
  display: inline-block;
  margin-right: 0.35rem;
  padding: 0.15rem 0.4rem;
  border-radius: 0.25rem;
  background: #c4a94d;
  color: #003366;
  font-size: 0.65rem;
  font-weight: 700;
  letter-spacing: 0.03em;
  text-transform: uppercase;
  vertical-align: middle;
}

.table > :not(caption) > * > * {
  vertical-align: middle;
}

.nav-pills .nav-link {
  padding: 0.35rem 0.75rem;
  font-size: 0.875rem;
}
</style>
