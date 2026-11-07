<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId="$route.params.proyectoId"></menu-lateral>
    </div>
    <div class="col-sm-8" v-if="!esEstadoRadicado">
      <form @submit.prevent="solicitarConfirmacion()">
        <div class="row">
          <div class="col-12">
            <div v-if="alertaEstadoVisible" class="alert" :class="alertaEstado.clase" role="alert">
              <strong>{{ alertaEstado.titulo }}.</strong> {{ alertaEstado.mensaje }}
            </div>

            <div class="mb-3 form-group">
              <label class="form-control-label" for="proyecto-titulo">
                <h2>
                  {{
                    esReenvioJurado
                      ? 'Reenviar propuesta al jurado'
                      : esReenvioAsesor
                        ? 'Reenviar propuesta al asesor'
                        : esSolicitudAval
                          ? 'Solicitar aval al asesor'
                          : 'Radicar la propuesta ante el CIECYT'
                  }}
                </h2>
              </label>
            </div>

            <div class="card mb-3">
              <div class="card-body">
                <h4 class="mb-3">{{ proyecto.titulo }}</h4>
                <div class="row g-2">
                  <div class="col-md-4">
                    <div class="small text-muted">Modalidad</div>
                    <div>{{ proyecto.proyectoModalidadModalidad || 'Sin modalidad' }}</div>
                  </div>
                  <div class="col-md-4">
                    <div class="small text-muted">Programa</div>
                    <div>{{ proyecto.programa || '—' }}</div>
                  </div>
                  <div class="col-md-4">
                    <div class="small text-muted">Ciclo</div>
                    <div>{{ proyecto.ciclo || '—' }}</div>
                  </div>
                  <div class="col-md-6">
                    <div class="small text-muted">Estudiante(s)</div>
                    <div v-for="estudiante in estudiantes" :key="estudiante.id">
                      {{ nombreIntegrante(estudiante) }}
                    </div>
                    <span v-if="estudiantes.length === 0" class="text-muted">Sin registrar</span>
                  </div>
                  <div class="col-md-3">
                    <div class="small text-muted">Asesor</div>
                    <div v-if="asesor">{{ nombreIntegrante(asesor) }}</div>
                    <span v-else class="text-muted">Sin asignar</span>
                  </div>
                  <div class="col-md-3">
                    <div class="small text-muted">Jurado</div>
                    <div v-for="jurado in jurados" :key="jurado.id">{{ nombreIntegrante(jurado) }}</div>
                    <span v-if="jurados.length === 0" class="text-muted">Sin asignar / No aplica</span>
                  </div>
                  <div class="col-md-4">
                    <div class="small text-muted">Estado actual</div>
                    <div>{{ proyecto.estado }}</div>
                  </div>
                  <div class="col-md-4">
                    <div class="small text-muted">Periodo / ciclo académico</div>
                    <div>{{ proyecto.ciclo || '—' }}</div>
                  </div>
                  <div class="col-md-4">
                    <div class="small text-muted">Última actividad</div>
                    <div>{{ fechaHora(proyecto.fechaUltimoCambio) }}</div>
                  </div>
                </div>
              </div>
            </div>

            <div class="mb-3 form-group">
              <h3>Documentación requerida</h3>
              <p class="text-muted">
                El CIECYT valida los requisitos de inscripción. Los documentos extensos de la propuesta se cargan
                en la pantalla <strong>Adjuntar propuesta</strong>.
              </p>

              <div v-if="cargandoRequisitos" class="text-muted">Cargando requisitos...</div>
              <div v-else-if="requisitos.length === 0" class="alert alert-info">
                No hay requisitos configurados para esta modalidad.
              </div>
              <div v-else class="table-responsive">
                <table class="table table-sm table-bordered align-middle">
                  <thead class="table-light">
                    <tr>
                      <th>Documento</th>
                      <th>Estado</th>
                      <th>Observación / validación</th>
                      <th class="text-end">Acciones</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="requisito in requisitos" :key="requisito.id">
                      <td>
                        <strong>{{ requisito.requisitoProyectoRequisitoNombre }}</strong>
                        <span
                          v-if="requisito.requisitoProyectoRequisitoObligatorio"
                          class="badge bg-dark ms-1"
                          >Obligatorio</span
                        >
                        <div v-if="requisito.requisitoProyectoRequisitoDescripcion" class="small text-muted">
                          {{ requisito.requisitoProyectoRequisitoDescripcion }}
                        </div>
                        <div class="small text-muted">{{ requisito.requisitoProyectoRequisitoCodigo }}</div>
                      </td>
                      <td>
                        <span class="badge" :class="claseEstadoRequisito(requisito)">
                          {{ etiquetaEstadoRequisito(requisito) }}
                        </span>
                      </td>
                      <td>
                        <div v-if="requisito.observacion">{{ requisito.observacion }}</div>
                        <div v-if="requisito.fechaValidacion" class="small text-muted">
                          {{ requisito.estado === 'RECHAZADO' ? 'Rechazado' : 'Validado' }} el
                          {{ fechaHora(requisito.fechaValidacion) }}
                          <span v-if="requisito.validadoPor">por {{ requisito.validadoPor }}</span>
                        </div>
                      </td>
                      <td class="text-end" style="min-width: 260px">
                        <div v-if="esRequisitoEditable(requisito)" class="text-start">
                          <file-upload
                            :accept="requisito.requisitoProyectoRequisitoAceptaTipos || '.pdf'"
                            :help="'Máx. 20 MB. Formatos: ' + (requisito.requisitoProyectoRequisitoAceptaTipos || '.pdf')"
                            label="Adjuntar requisito"
                            @change="archivo => asignarArchivo(archivo, requisito)"
                          />
                          <button
                            type="button"
                            class="btn btn-sm btn-primary mt-1"
                            :disabled="guardandoRequisito"
                            @click="entregarRequisito(requisito)"
                          >
                            <font-awesome-icon icon="save"></font-awesome-icon> Entregar
                          </button>
                        </div>
                        <button
                          v-if="requisito.archivo"
                          type="button"
                          class="btn btn-sm btn-outline-primary mt-1"
                          @click="previsualizarRequisito(requisito)"
                        >
                          <font-awesome-icon icon="eye"></font-awesome-icon> Previsualizar
                        </button>
                        <button
                          v-if="requisito.requisitoProyectoRequisitoTieneFormato"
                          type="button"
                          class="btn btn-sm btn-outline-primary mt-1 ms-1"
                          @click="descargarFormato(requisito)"
                        >
                          <font-awesome-icon icon="download"></font-awesome-icon> Descargar formato
                        </button>
                        <router-link
                          :to="{ name: 'PropuestaChecklistView', params: { proyectoId: proyId } }"
                          class="btn btn-sm btn-outline-secondary mt-1 ms-1"
                        >
                          Abrir checklist
                        </router-link>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <small v-if="requisitosObligatorios.length > 0 && !documentosCompletos" class="form-text text-danger">
                Faltan requisitos obligatorios por cargar o corregir antes de radicar.
              </small>
            </div>

            <b-form-checkbox id="terms" v-model="terms" name="terms" class="mb-2">
              {{ textoConfirmacion }}
            </b-form-checkbox>

            <small v-if="!terms" class="form-text text-muted">
              Debe marcar la confirmación para habilitar el botón.
            </small>

            <div v-if="errorEnvio" class="alert alert-danger mt-3" role="alert">{{ errorEnvio }}</div>
          </div>
        </div>

        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
          </button>

          <button type="submit" id="save-entity" class="btn btn-primary" :disabled="isDisabled || isSaving">
            <font-awesome-icon icon="paper-plane"></font-awesome-icon>&nbsp;<span>{{ textoBoton }}</span>
          </button>
        </div>
      </form>
    </div>
    <div class="col-sm-8" v-if="esEstadoRadicado">
      <div class="alert" :class="alertaEstado.clase" role="alert">
        <h2 class="alert-heading">{{ alertaEstado.titulo }}</h2>
        <p>{{ alertaEstado.mensaje }}</p>
        <p class="mb-0">Estado actual: <strong>{{ proyecto.estado }}</strong></p>
      </div>
      <router-link :to="{ name: 'ListadoEstudianteView' }" class="btn btn-primary">
        Volver a Mis Propuestas
      </router-link>
    </div>

    <div
      v-if="mostrarConfirmacion"
      class="modal fade show d-block"
      tabindex="-1"
      role="dialog"
      style="background: rgba(0, 0, 0, 0.5)"
    >
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Confirmar envío</h5>
            <button type="button" class="close" aria-label="Cerrar" v-on:click="cancelarConfirmacion">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <p v-if="esReenvioJurado">¿Reenviar la propuesta <strong>{{ proyecto.titulo }}</strong> al jurado?</p>
            <p v-else-if="esReenvioAsesor">¿Reenviar la propuesta <strong>{{ proyecto.titulo }}</strong> al asesor?</p>
            <p v-else-if="esSolicitudAval">
              ¿Solicitar el aval del asesor para la propuesta <strong>{{ proyecto.titulo }}</strong>?
            </p>
            <p v-else>¿Radicar la propuesta <strong>{{ proyecto.titulo }}</strong> ante el CIECYT?</p>
            <p class="mb-0" v-if="esReenvioJurado">El jurado evaluará la propuesta corregida.</p>
            <p class="mb-0" v-else-if="esReenvioAsesor || esSolicitudAval">
              El asesor revisará la propuesta y emitirá su aval.
            </p>
            <p class="mb-0" v-else>El CIECYT validará la documentación y designará el jurado.</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" v-on:click="cancelarConfirmacion">Cancelar</button>
            <button type="button" class="btn btn-primary" :disabled="isSaving" v-on:click="confirmarEnvio">
              {{ esSolicitudAval || esReenvioAsesor ? 'Sí, enviar' : 'Sí, radicar' }}
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Hook, mixins } from 'vue-facing-decorator';

import MenuLateral from '@/components/propuesta_nueva/menu_lateral_nueva.vue';
import JhiDataUtils from '@/shared/data/data-utils.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
import { IRequisitoProyecto } from '@/shared/model/requisito-proyecto.model';
import { IAdjuntoProyectoFase, AdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import { EnumEstadoRequisito } from '@/shared/model/enumerations/enum-estado-requisito.model';
import { TipoRequisito } from '@/shared/model/enumerations/tipo-requisito.model';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';
import RequisitoProyectoService from '@/entities/requisito-proyecto/requisito-proyecto.service';
import RequisitoService from '@/entities/requisito/requisito.service';
import FasesService from '@/entities/fases/fases.service';

@Component({
  components: { MenuLateral },
})
export default class EnviarPropuesta extends mixins(JhiDataUtils) {
  @Inject private proyectoService: () => ProyectoService;
  @Inject private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject private requisitoProyectoService: () => RequisitoProyectoService;
  @Inject private requisitoService: () => RequisitoService;
  @Inject private fasesService: () => FasesService;
  @Inject private alertService: () => AlertService;

  public integrants: IIntegranteProyecto[] = [];
  public requisitos: IRequisitoProyecto[] = [];
  public terms: Boolean = false;
  public pendientes: { [id: number]: any } = {};
  public adjuntosDocumentos: IAdjuntoProyectoFase[] = [];
  public documentosFaseId: number = null;
  public guardandoRequisito = false;

  public proyecto: IProyecto = new Proyecto();
  public proyId: string = null;
  public isSaving = false;
  public mostrarConfirmacion = false;
  public errorEnvio: string | null = null;
  public modalidadId: any = null;
  public cargandoRequisitos = false;

  previousState() {
    window.history.back();
  }

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initRelationships();
    });
  }

  getNow() {
    const today = new Date();
    const date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
    return date;
  }

  public solicitarConfirmacion(): void {
    if (this.isDisabled || this.isSaving) {
      return;
    }
    this.errorEnvio = null;
    this.mostrarConfirmacion = true;
  }

  public cancelarConfirmacion(): void {
    this.mostrarConfirmacion = false;
  }

  public async confirmarEnvio(): Promise<void> {
    this.mostrarConfirmacion = false;
    await this.save();
  }

  public async save(): Promise<void> {
    this.errorEnvio = null;
    this.isSaving = true;
    const accionAsesor = this.esReenvioAsesor || this.esSolicitudAval;
    const accionJurado = this.esReenvioJurado;

    try {
      let param: IProyecto;
      if (this.esReenvioJurado) {
        param = await this.proyectoService().cambiarEstado(
          this.proyecto.id,
          'EN_REVISION_JURADO_PROPUESTA',
          'Propuesta corregida y reenviada al jurado'
        );
      } else if (this.esReenvioAsesor) {
        param = await this.proyectoService().cambiarEstado(
          this.proyecto.id,
          'EN_REVISION_ASESOR',
          'Propuesta corregida y reenviada al asesor'
        );
      } else if (this.esSolicitudAval) {
        param = await this.proyectoService().cambiarEstado(
          this.proyecto.id,
          'EN_REVISION_ASESOR',
          'Solicitud de aval al asesor'
        );
      } else {
        param = await this.proyectoService().solicitarValidacionDocumental(this.proyecto.id);
      }
      this.proyecto = param;
      this.isSaving = false;
      await this.initRelationships();
      this.alertService().showAlert(
        accionJurado
          ? 'Propuesta enviada al jurado para su evaluación'
          : accionAsesor
            ? 'Propuesta enviada al asesor para su aval'
            : 'Propuesta radicada ante el CIECYT',
        'success'
      );
    } catch (err) {
      this.isSaving = false;
      this.errorEnvio = this.mensajeDeError(err);
    }
  }

  private mensajeDeError(err: any): string {
    const detalle =
      err && err.response && err.response.data ? err.response.data.detail || err.response.data.title : null;
    return detalle
      ? 'No se pudo radicar la propuesta: ' + detalle
      : 'No se pudo radicar la propuesta. Intente de nuevo.';
  }

  get esReenvioJurado(): boolean {
    return this.proyecto.estado === 'CORRECCIONES_JURADO_PROPUESTA';
  }

  get esReenvioAsesor(): boolean {
    return this.proyecto.estado === 'CORRECCIONES_ASESOR';
  }

  get esSolicitudAval(): boolean {
    return this.proyecto.estado === 'HABILITADO';
  }

  get esEstadoRadicado(): boolean {
    if (!this.proyecto || !this.proyecto.id) {
      return false;
    }
    const estado = this.proyecto.estado;
    return (
      estado !== 'PREINSCRITA' &&
      estado !== 'EN_ELABORACION_PROPUESTA' &&
      estado !== 'OBSERVACIONES_DOCUMENTACION' &&
      estado !== 'HABILITADO' &&
      estado !== 'CORRECCIONES_ASESOR' &&
      estado !== 'CORRECCIONES_JURADO_PROPUESTA'
    );
  }

  get alertaEstadoVisible(): boolean {
    return (
      this.esSolicitudAval ||
      this.esReenvioAsesor ||
      this.esReenvioJurado ||
      this.proyecto.estado === 'OBSERVACIONES_DOCUMENTACION'
    );
  }

  get textoBoton(): string {
    if (this.esReenvioJurado) {
      return 'Reenviar al jurado';
    }
    if (this.esReenvioAsesor) {
      return 'Reenviar al asesor';
    }
    return this.esSolicitudAval ? 'Solicitar aval al asesor' : 'Radicar ante CIECYT';
  }

  get textoConfirmacion(): string {
    if (this.esReenvioJurado) {
      return 'Confirmo que incorporé las correcciones del jurado';
    }
    if (this.esReenvioAsesor) {
      return 'Confirmo que incorporé las correcciones del asesor';
    }
    return this.esSolicitudAval
      ? 'Solicito el aval del asesor para esta propuesta'
      : 'Confirmo que la propuesta y sus anexos están completos para radicar';
  }

  get alertaEstado(): { clase: string; titulo: string; mensaje: string } {
    switch (this.proyecto.estado) {
      case 'EN_VALIDACION_DOCUMENTAL':
        return {
          clase: 'alert-info',
          titulo: 'Propuesta radicada ante el CIECYT',
          mensaje:
            'El CIECYT está validando la documentación. Si hay observaciones podrás corregirlas y volver a radicar.',
        };
      case 'OBSERVACIONES_DOCUMENTACION':
        return {
          clase: 'alert-warning',
          titulo: 'Documentación con observaciones',
          mensaje: 'Revisa el checklist, corrige lo señalado y vuelve a radicar la propuesta.',
        };
      case 'HABILITADO':
        return {
          clase: 'alert-success',
          titulo: 'Documentación aprobada por el CIECYT',
          mensaje: 'Ya puedes solicitar el aval del asesor para continuar con la evaluación de la propuesta.',
        };
      case 'CORRECCIONES_ASESOR':
        return {
          clase: 'alert-warning',
          titulo: 'Correcciones del asesor',
          mensaje: 'Revisa la retroalimentación, ajusta la propuesta y reenvíala al asesor.',
        };
      case 'EN_REVISION_ASESOR':
        return {
          clase: 'alert-info',
          titulo: 'En revisión del asesor',
          mensaje: 'El asesor está revisando la propuesta para emitir su aval.',
        };
      case 'APROBADA_POR_ASESOR':
        return {
          clase: 'alert-success',
          titulo: 'Aval del asesor registrado',
          mensaje: 'El CIECYT asignará el jurado que evaluará la propuesta.',
        };
      case 'EN_REVISION_JURADO_PROPUESTA':
        return {
          clase: 'alert-info',
          titulo: 'En revisión del jurado',
          mensaje: 'El jurado evaluará la propuesta en un plazo máximo de 10 días hábiles (Acuerdo 25, art. 10).',
        };
      case 'CORRECCIONES_JURADO_PROPUESTA':
        return {
          clase: 'alert-warning',
          titulo: 'Correcciones del jurado',
          mensaje: 'Revisa la retroalimentación del jurado y envía la propuesta ajustada.',
        };
      case 'VIABLE':
        return {
          clase: 'alert-success',
          titulo: 'Propuesta viable',
          mensaje: 'Puedes iniciar el desarrollo del cronograma de actividades (Acuerdo 25, art. 11).',
        };
      case 'NO_VIABLE':
        return {
          clase: 'alert-danger',
          titulo: 'Propuesta no viable',
          mensaje: 'Debes reformular y volver a presentar la propuesta (Acuerdo 25, art. 10).',
        };
      default:
        return {
          clase: 'alert-info',
          titulo: 'Propuesta radicada',
          mensaje: 'La propuesta ya fue radicada y se encuentra en el flujo de evaluación.',
        };
    }
  }

  public get estudiantes(): IIntegranteProyecto[] {
    return this.integrants.filter(integrante => this.rolDe(integrante).indexOf('ESTUDIANTE') >= 0);
  }

  public get asesor(): IIntegranteProyecto | null {
    return this.integrants.find(integrante => this.rolDe(integrante).indexOf('ASESOR') >= 0) || null;
  }

  public get jurados(): IIntegranteProyecto[] {
    return this.integrants.filter(integrante => this.rolDe(integrante).indexOf('JURADO') >= 0);
  }

  public get requisitosObligatorios(): IRequisitoProyecto[] {
    return this.requisitos.filter(requisito => requisito.requisitoProyectoRequisitoObligatorio);
  }

  public get documentosCompletos(): boolean {
    return this.requisitosObligatorios.every(requisito => {
      if (requisito.requisitoProyectoRequisitoTipo === TipoRequisito.CAMPO) {
        return !!requisito.observacion;
      }
      return requisito.estado === EnumEstadoRequisito.APROBADO || requisito.estado === EnumEstadoRequisito.ENTREGADO;
    });
  }

  public get isDisabled(): boolean {
    if (!this.terms) {
      return true;
    }
    return this.requisitosObligatorios.length > 0 && !this.documentosCompletos;
  }

  public nombreIntegrante(integrante: any): string {
    const nombre = `${integrante.integranteProyectoUserFirstName || ''} ${integrante.integranteProyectoUserLastName || ''}`.trim();
    return (
      nombre ||
      integrante.integranteProyectoExternoNombre ||
      integrante.integranteProyectoUserLogin ||
      integrante.integrante ||
      'Sin nombre'
    );
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
        return 'bg-info';
      case EnumEstadoRequisito.RECHAZADO:
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }

  public previsualizarRequisito(requisito: IRequisitoProyecto): void {
    if (requisito.archivo) {
      const id = Number(requisito.archivo);
      this.adjuntoProyectoFaseService()
        .find(id)
        .then(adjunto => {
          this.adjuntoProyectoFaseService().previewFile(id, adjunto.nombreArchivoOriginal || '');
        })
        .catch(() => {
          this.adjuntoProyectoFaseService().previewFile(id);
        });
    }
  }

  public descargarFormato(requisito: IRequisitoProyecto): void {
    this.requisitoService().downloadFormato(
      requisito.requisitoProyectoRequisitoId,
      requisito.requisitoProyectoRequisitoNombreFormato || 'formato-requisito'
    );
  }

  public esRequisitoEditable(requisito: IRequisitoProyecto): boolean {
    return requisito.estado === EnumEstadoRequisito.PENDIENTE || requisito.estado === EnumEstadoRequisito.RECHAZADO;
  }

  public asignarArchivo(archivo: File | Event | null, requisito: IRequisitoProyecto): void {
    let fileData: File | null = null;
    if (archivo instanceof File) {
      fileData = archivo;
    } else if (archivo && (archivo as any).target && (archivo as any).target.files) {
      fileData = (archivo as any).target.files[0] || null;
    }
    if (!fileData) {
      delete this.pendientes[requisito.id];
      return;
    }
    const pendiente: any = { nombreArchivoOriginal: fileData.name };
    const syntheticEvent: any = { target: { files: [fileData] } };
    this.setFileData(syntheticEvent, pendiente, 'archivo', false);
    this.pendientes[requisito.id] = pendiente;
  }

  public entregarRequisito(requisito: IRequisitoProyecto): void {
    if (requisito.requisitoProyectoRequisitoTipo === TipoRequisito.CAMPO) {
      this.alertService().showAlert('Este requisito se diligencia en el checklist.', 'warning');
      return;
    }
    const pendiente = this.pendientes[requisito.id];
    if (!pendiente || !pendiente.archivo) {
      this.alertService().showAlert('Debe seleccionar un archivo', 'warning');
      return;
    }

    this.guardandoRequisito = true;
    const adjunto = this.adjuntoDeRequisito(requisito) || new AdjuntoProyectoFase();
    adjunto.proyectoFaseProyectoId = this.proyecto.id;
    adjunto.proyectoFaseProyectoTitulo = this.proyecto.titulo;
    adjunto.adjuntoProyectoFaseFaseId = this.documentosFaseId;
    adjunto.nombreAdjunto = requisito.requisitoProyectoRequisitoCodigo;
    adjunto.nombreArchivoOriginal = pendiente.nombreArchivoOriginal;
    adjunto.archivo = pendiente.archivo;
    adjunto.archivoContentType = pendiente.archivoContentType;
    adjunto.fechaCreacion = new Date();

    const guardar = adjunto.id
      ? this.adjuntoProyectoFaseService().update(adjunto)
      : this.adjuntoProyectoFaseService().create(adjunto);

    guardar
      .then((guardado: IAdjuntoProyectoFase) => {
        const indice = this.adjuntosDocumentos.findIndex(item => item.nombreAdjunto === guardado.nombreAdjunto);
        if (indice >= 0) {
          this.adjuntosDocumentos.splice(indice, 1, guardado);
        } else {
          this.adjuntosDocumentos.push(guardado);
        }
        return this.requisitoProyectoService().entregar(requisito.id, String(guardado.id), null);
      })
      .then((actualizado: IRequisitoProyecto) => {
        this.guardandoRequisito = false;
        delete this.pendientes[requisito.id];
        this.reemplazarRequisito(actualizado);
        this.alertService().showAlert('Requisito entregado', 'success');
      })
      .catch(err => {
        this.guardandoRequisito = false;
        this.alertService().showAlert(this.mensajeDeErrorDocumento(err), 'danger');
      });
  }

  private adjuntoDeRequisito(requisito: IRequisitoProyecto): IAdjuntoProyectoFase {
    return this.adjuntosDocumentos.find(
      adjunto => adjunto.nombreAdjunto === requisito.requisitoProyectoRequisitoCodigo
    );
  }

  private reemplazarRequisito(actualizado: IRequisitoProyecto): void {
    const indice = this.requisitos.findIndex(requisito => requisito.id === actualizado.id);
    if (indice >= 0) {
      this.requisitos.splice(indice, 1, actualizado);
    }
  }

  private mensajeDeErrorDocumento(err: any): string {
    const detalle =
      err && err.response && err.response.data
        ? err.response.data.detail || err.response.data.title || err.response.data.message
        : null;
    return detalle || 'No se pudo guardar el documento.';
  }

  public fechaHora(value: any): string {
    if (!value) {
      return '—';
    }
    const fecha = value instanceof Date ? value : new Date(value);
    if (isNaN(fecha.getTime())) {
      return String(value);
    }
    const dos = (numero: number) => String(numero).padStart(2, '0');
    return `${dos(fecha.getDate())}/${dos(fecha.getMonth() + 1)}/${fecha.getFullYear()} ${dos(
      fecha.getHours()
    )}:${dos(fecha.getMinutes())}`;
  }

  private rolDe(integrante: IIntegranteProyecto): string {
    return String(integrante.integranteProyectoRolesModalidadRol || '')
      .toUpperCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');
  }

  retrieveProyecto() {
    return this.proyectoService()
      .findProyectoIntegrantes(parseInt(this.$route.params.proyectoId))
      .then(res => {
        this.proyecto = res.data;
        this.integrants = this.proyecto.listaIntegrantesProyecto || [];
        this.modalidadId = this.proyecto.proyectoModalidadId;
      });
  }

  async cargarRequisitos(): Promise<void> {
    this.cargandoRequisitos = true;
    try {
      const lista = await this.requisitoProyectoService().generarPorProyecto(Number(this.proyId));
      this.requisitos = this.filtrarRequisitosInscripcion(lista || []);
    } catch (e) {
      try {
        const lista = await this.requisitoProyectoService().findByProyecto(Number(this.proyId));
        this.requisitos = this.filtrarRequisitosInscripcion(lista || []);
      } catch (error) {
        this.requisitos = [];
      }
    } finally {
      this.cargandoRequisitos = false;
    }
  }

  private filtrarRequisitosInscripcion(lista: IRequisitoProyecto[]): IRequisitoProyecto[] {
    return lista.filter(
      requisito =>
        !requisito.requisitoProyectoRequisitoEstado ||
        String(requisito.requisitoProyectoRequisitoEstado) === 'EN_VALIDACION_DOCUMENTAL'
    );
  }

  async initRelationships() {
    this.proyId = this.$route.params.proyectoId;
    await this.retrieveProyecto();

    const faseDocumentos = await this.fasesService().findByFase('Documentos');
    this.documentosFaseId = faseDocumentos ? faseDocumentos.id : null;

    await this.cargarRequisitos();
    await this.cargarAdjuntosDocumentos();
  }

  private async cargarAdjuntosDocumentos(): Promise<void> {
    if (!this.documentosFaseId) {
      this.adjuntosDocumentos = [];
      return;
    }
    try {
      const res = await this.adjuntoProyectoFaseService().findAdjuntoProyectoFase(this.proyId, this.documentosFaseId);
      this.adjuntosDocumentos = res.data || [];
    } catch (e) {
      this.adjuntosDocumentos = [];
    }
  }

}
</script>

<style scoped>
</style>
