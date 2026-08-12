<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId="$route.params.proyectoId"></menu-lateral>
    </div>
    <div class="col-sm-8"  v-if="!proyecto.preEnviado">
      <form @submit.prevent="save()">
        <div class="row">
          <div class="col-12">

            <div class="form-group">
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

              <div class="form-group" v-if="esTesis">
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

              <b-form-checkbox type="checkbox"
              class="form-control"
              name="terms"
              value="terms"
              id='terms'
              v-model='terms'

              >
                Enviar al Asesor
              </b-form-checkbox>
            </div>
          </div>
        </div>

        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>

          <button type="submit" id="save-entity" class="btn btn-primary" :disabled='isDisabled'>
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Enviar</span>
          </button>
        </div>
      </form>
    </div>
    <div class="col-sm-8"  v-if="proyecto.preEnviado">
     <h2>Enviar la Propuesta</h2><br />
    La Propuesta ya ha sido enviada al Asesor para su revisión
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';

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
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('adjuntoProyectoFaseService') private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject('fasesService') private fasesService: () => FasesService;
  @Inject('alertService') private alertService: () => AlertService;

  public integrants: IIntegranteProyecto[] = [];
  public terms: Boolean = false;

  public proyecto: IProyecto = new Proyecto();
  public proyId: string = null;
  public isSaving = false;
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

  public save(): void {
    this.isSaving = true;
    // El envío de la propuesta actualiza el estado y sincroniza los flags legacy.
    const estado = 'EN_REVISION_ASESOR';
    const observacion = 'Estudiante envió la propuesta al asesor';

    this.proyectoService()
      .cambiarEstado(this.proyecto.id, estado, observacion)
      .then(param => {
        this.isSaving = false;
        (<any>this).$router.go(0);
        const message = this.$t('ciecytApp.proyecto.updated', { param: param.id });
        this.alertService().showAlert(message, 'info');
      })
      .catch(() => {
        this.isSaving = false;
        this.alertService().showAlert('Error al enviar la propuesta', 'danger');
      });
  }

  retrieveProyecto() {
    return this.proyectoService()
      .findProyectoIntegrantes(parseInt(this.$route.params.proyectoId))
      .then(res => {
        this.proyecto = res.data;
        this.integrants = this.proyecto.listaIntegrantesProyecto;
        this.modalidadId = this.proyecto.proyectoModalidadId;
        console.log(res.data.listaIntegrantesProyecto);
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
