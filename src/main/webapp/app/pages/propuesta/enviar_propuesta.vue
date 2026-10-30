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
               <h2>{{ esReenvioJurado ? 'Reenviar propuesta al jurado' : (esReenvioAsesor ? 'Reenviar propuesta al asesor' : (esSolicitudAval ? 'Solicitar aval al asesor' : 'Radicar la propuesta ante el CIECYT')) }}</h2><br />

               <ul>
               <li>Título {{proyecto.titulo}} </li>
            <li v-for="l in integrants" v-bind:key="l">{{l.integranteProyectoRolesModalidadRol}}: {{l.integranteProyectoUserLogin}}</li>
             <ol></ol>

        </ul>
                <template v-if="esReenvioJurado">
                  El jurado solicitó correcciones. Ajusta la propuesta, incorpora los cambios y reenvíala para
                  continuar con la evaluación (Acuerdo 25, art. 10).
                </template>
                <template v-else-if="esReenvioAsesor">
                  El asesor solicitó correcciones. Ajusta la propuesta, incorpora los cambios y reenvíala para
                  que emita su aval (Acuerdo 25, art. 8 y art. 10).
                </template>
                <template v-else-if="esSolicitudAval">
                  El CIECYT aprobó la documentación. Solicita al asesor su aval para continuar con la evaluación
                  de la propuesta (Acuerdo 25, art. 8).
                </template>
                <template v-else>
                  La propuesta y sus anexos se radicarán ante el CIECYT. Esa dependencia validará la documentación
                  y designará el jurado que evaluará la propuesta (Acuerdo 25, art. 5 y art. 28).
                </template>
                <br /><br />
                Si está de acuerdo marque la opción
                <strong>{{ esReenvioJurado ? 'Reenviar al jurado' : (esReenvioAsesor ? 'Reenviar al asesor' : (esSolicitudAval ? 'Solicitar aval al asesor' : 'Radicar ante el CIECYT')) }}</strong>
                y haga clic en el botón <strong>{{ textoBoton }}</strong>.</label
              >

              <div class="mb-3 form-group" v-if="esTesis">
                <h3>Documentación requerida</h3>
                <p>Para la modalidad Tesis debe adjuntar los siguientes documentos antes de radicar la propuesta:</p>
                <table class="table table-sm table-bordered">
                  <thead>
                    <tr>
                      <th>Documento</th>
                      <th>Estado</th>
                      <th>Acciones</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="(d, i) in documentos" :key="i">
                      <td>
                        <strong>{{ d.titulo }}</strong><br />
                        <small>{{ d.descripcion }}</small>
                      </td>
                      <td>
                        <span v-if="d.adjunto && d.adjunto.file" class="text-success">Cargado</span>
                        <span v-else class="text-danger">Pendiente</span>
                      </td>
                      <td>
                        <router-link
                          :to="{ name: d.route, params: { proyectoId: proyId } }"
                          class="btn btn-sm btn-outline-primary"
                        >
                          {{ d.adjunto && d.adjunto.file ? 'Ver / Reemplazar' : 'Cargar' }}
                        </router-link>
                        <button
                          v-if="d.adjunto && d.adjunto.file"
                          type="button"
                          class="btn btn-sm btn-outline-secondary"
                          v-on:click="descargarDoc(d.adjunto)"
                        >
                          Descargar
                        </button>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>

              <b-form-checkbox
                id="terms"
                v-model="terms"
                name="terms"
                class="mb-2"
              >
                {{ textoConfirmacion }}
              </b-form-checkbox>

              <small v-if="!terms" class="form-text text-muted">
                Debe marcar la confirmación para habilitar el botón.
              </small>
              <small v-else-if="esTesis && !documentosCompletos" class="form-text text-danger">
                Faltan documentos obligatorios por cargar.
              </small>

              <div v-if="errorEnvio" class="alert alert-danger mt-3" role="alert">{{ errorEnvio }}</div>
            </div>
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
            <p v-else-if="esSolicitudAval">¿Solicitar el aval del asesor para la propuesta <strong>{{ proyecto.titulo }}</strong>?</p>
            <p v-else>¿Radicar la propuesta <strong>{{ proyecto.titulo }}</strong> ante el CIECYT?</p>
            <p class="mb-0" v-if="esReenvioJurado">El jurado evaluará la propuesta corregida.</p>
            <p class="mb-0" v-else-if="esReenvioAsesor || esSolicitudAval">El asesor revisará la propuesta y emitirá su aval.</p>
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
import { Component, Inject, Vue, Hook } from 'vue-facing-decorator';

import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';

import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
import { IAdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';
import FasesService from '@/entities/fases/fases.service';

@Component({
  components: { MenuLateral },
})
export default class EnviarPropuesta extends Vue {
  @Inject  private proyectoService: () => ProyectoService;
  @Inject  private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject  private fasesService: () => FasesService;
  @Inject  private alertService: () => AlertService;

  public integrants: IIntegranteProyecto[] = [];
  public terms: Boolean = false;

  public proyecto: IProyecto = new Proyecto();
  public proyId: string = null;
  public isSaving = false;
  public mostrarConfirmacion = false;
  public errorEnvio: string | null = null;
  public modalidadId: any = null;
  public docFaseId: number = null;
  public faseDocumentos = 'Documentos';
  public documentos: any[] = [
    {
      key: 'certificado-estudiante',
      titulo: 'Certificado de Estudiante Activo',
      descripcion: 'Expedido por Registro y Control',
      route: 'PropuestaDocumentoCertificadoView',
      adjunto: null,
    },
    {
      key: 'recibo-pago',
      titulo: 'Recibo de Pago de la Opción de Grado',
      descripcion: 'Validado por Tesorería de la institución',
      route: 'PropuestaDocumentoReciboView',
      adjunto: null,
    },
    {
      key: 'record-academico',
      titulo: 'Récord Académico',
      descripcion: 'Expedido por Registro y Control',
      route: 'PropuestaDocumentoRecordView',
      adjunto: null,
    },
    {
      key: 'formato-inscripcion',
      titulo: 'Formato de Inscripción de la Opción de Grado',
      descripcion: 'Formato de inscripción de la opción de grado',
      route: 'PropuestaDocumentoFormatoView',
      adjunto: null,
    },
  ];

  // El template llamaba previousState() pero el metodo no existia, asi que el boton
  // Cancel/atrás fallaba con un TypeError y no hacia nada.
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
    const detalle = err && err.response && err.response.data ? err.response.data.detail || err.response.data.title : null;
    return detalle ? 'No se pudo radicar la propuesta: ' + detalle : 'No se pudo radicar la propuesta. Intente de nuevo.';
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
          mensaje: 'El CIECYT está validando la documentación. Si hay observaciones podrás corregirlas y volver a radicar.',
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

  retrieveProyecto() {
    return this.proyectoService()
      .findProyectoIntegrantes(parseInt(this.$route.params.proyectoId))
      .then(res => {
        this.proyecto = res.data;
        this.integrants = this.proyecto.listaIntegrantesProyecto;
        this.modalidadId = this.proyecto.proyectoModalidadId;
      });
  }

  async initRelationships() {
    this.proyId = this.$route.params.proyectoId;

    await this.retrieveProyecto();

    if (this.esTesis) {
      await this.fasesService()
        .findByFase(this.faseDocumentos)
        .then(res => {
          this.docFaseId = res.id;
        });

      if (this.docFaseId) {
        await this.adjuntoProyectoFaseService()
          .findAdjuntoProyectoFase(this.proyId, this.docFaseId)
          .then(res => {
            const docs: IAdjuntoProyectoFase[] = res.data || [];
            this.documentos.forEach(d => {
              const match = docs.find(x => x.nombreAdjunto === d.key);
              d.adjunto = match || null;
            });
          });
      }
    }
  }

  descargarDoc(adjunto) {
    this.adjuntoProyectoFaseService().downloadFile(adjunto.id, adjunto.nombreArchivoOriginal);
  }

  get esTesis() {
    return Number(this.modalidadId) === 9004;
  }

  get documentosCompletos() {
    return this.documentos.every(d => d.adjunto && d.adjunto.file);
  }

  get isDisabled() {
    if (!this.terms) {
      return true;
    }
    return this.esTesis && !this.documentosCompletos;
  }
}
</script>

<style scoped>
</style>
