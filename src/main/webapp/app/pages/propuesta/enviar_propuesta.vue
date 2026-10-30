<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId="$route.params.proyectoId"></menu-lateral>
    </div>
    <div class="col-sm-8"  v-if="!proyecto.preEnviado">
      <form @submit.prevent="solicitarConfirmacion()">
        <div class="row">
          <div class="col-12">

            <div class="mb-3 form-group">
              <label class="form-control-label" for="proyecto-titulo">
               <h2>Enviar la Propuesta</h2><br />

               <ul>
               <li>Título {{proyecto.titulo}} </li>
            <li v-for="l in integrants" v-bind:key="l">{{l.integranteProyectoRolesModalidadRol}}: {{l.integranteProyectoUserLogin}}</li>
             <ol></ol>

        </ul>
                Se va a enviar la Propuesta al Asesor asignado. <br />
                Para realizar esta operación, debe haber diligenciado correctamente los datos de su propuesta
                <br />

                <br />Si está de acuerdo marque la opcion
                <strong>Enviar al Asesor </strong>
                y de click en el boton <strong>Enviar </strong></label
              >

              <div class="mb-3 form-group" v-if="esTesis">
                <h3>Documentación requerida</h3>
                <p>Para la modalidad Tesis debe adjuntar los siguientes documentos antes de enviar la propuesta:</p>
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
                Enviar al Asesor
              </b-form-checkbox>

              <small v-if="!terms" class="form-text text-muted">
                Debe marcar "Enviar al Asesor" para habilitar el botón.
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
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Enviar</span>
          </button>
        </div>
      </form>
    </div>
    <div class="col-sm-8"  v-if="proyecto.preEnviado">
      <div class="alert alert-success" role="alert">
        <h2 class="alert-heading">Propuesta enviada</h2>
        <p>La propuesta <strong>{{ proyecto.titulo }}</strong> fue enviada al asesor para su revisión.</p>
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
            <p>¿Enviar la propuesta <strong>{{ proyecto.titulo }}</strong> al asesor?</p>
            <p class="mb-0">Después del envío no podrá editarla mientras esté en revisión.</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" v-on:click="cancelarConfirmacion">Cancelar</button>
            <button type="button" class="btn btn-primary" :disabled="isSaving" v-on:click="confirmarEnvio">
              Sí, enviar
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
    // El envío de la propuesta actualiza el estado y sincroniza los flags legacy.
    const observacion = 'Estudiante envió la propuesta al asesor';

    try {
      // Un proyecto recién creado nace en PREINSCRITA. El flujo exige pasar por
      // EN_ELABORACION_PROPUESTA (95229) antes de enviarlo al asesor (95230), así que
      // desde la interfaz se hacen los dos pasos en una sola acción.
      if (this.proyecto.estado === 'PREINSCRITA') {
        await this.proyectoService().cambiarEstado(
          this.proyecto.id,
          'EN_ELABORACION_PROPUESTA',
          'Inicio de elaboración de la propuesta'
        );
      }
      const param = await this.proyectoService().cambiarEstado(this.proyecto.id, 'EN_REVISION_ASESOR', observacion);
      this.proyecto = param;
      this.proyecto.preEnviado = true;
      this.isSaving = false;
      await this.initRelationships();
      const message = this.$t('ciecytApp.proyecto.updated', { param: param.id });
      this.alertService().showAlert(message, 'info');
    } catch (err) {
      this.isSaving = false;
      this.errorEnvio = this.mensajeDeError(err);
    }
  }

  private mensajeDeError(err: any): string {
    const detalle = err && err.response && err.response.data ? err.response.data.detail || err.response.data.title : null;
    return detalle ? 'No se pudo enviar la propuesta: ' + detalle : 'No se pudo enviar la propuesta. Intente de nuevo.';
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
