<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId="$route.params.proyectoId"></menu-lateral>
    </div>
    <div class="col-sm-8">
      <form name="enviarAdjunto" role="form" novalidate v-on:submit.prevent="save()">
        <div class="row">
          <div class="col-12">
            <div class="form-group">
              <label class="form-control-label" for="proyecto-titulo">
                <h2>{{ documento.titulo }}</h2><br />
                <span>{{ documento.descripcion }}</span>
              </label>
            </div>

            <div class="form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.adjuntoProyectoFase.archivo')" for="adjunto-proyecto-fase-archivo">Archivo</label>
              <div>
                <div v-if="adjuntoProyectoFase.file" class="form-text clearfix">
                  <a class="pull-left" v-on:click="this.descargar" v-text="$t('entity.action.open')">open</a>
                  <span class="pull-left">{{ adjuntoProyectoFase.nombreArchivoOriginal }}</span>
                  <button type="button" class="btn btn-secondary btn-xs pull-right" v-on:click="this.eliminar">
                    <font-awesome-icon icon="times"></font-awesome-icon>
                  </button>
                </div>
                <input v-if="adjuntoProyectoFase.file == null" type="file" ref="file_archivo" id="file_archivo" v-on:change="asignarData($event, adjuntoProyectoFase, 'archivo', false)" v-text="$t('entity.action.addblob')"/>
                <span v-if="adjuntoProyectoFase.file != null">Si desea subir otro adjunto de este documento, deberá eliminar el archivo actual</span>
              </div>
              <input type="hidden" class="form-control" name="archivo" id="adjunto-proyecto-fase-archivo" v-model="adjuntoProyectoFase.archivo" />
              <input type="hidden" class="form-control" name="archivoContentType" id="adjunto-proyecto-fase-archivoContentType" v-model="adjuntoProyectoFase.archivoContentType" />
              <input type="hidden" class="form-control" name="fileName" id="adjunto-proyecto-fase-fileName" v-model="adjuntoProyectoFase.nombreArchivoOriginal" />
            </div>
          </div>
        </div>

        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="volver()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>

          <button type="submit" id="save-entity" class="btn btn-primary" :disabled='isDisabled'>
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import AlertService from '@/shared/alert/alert.service';
import { IAdjuntoProyectoFase, AdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';
import { IFases, Fases } from '@/shared/model/fases.model';
import FasesService from '@/entities/fases/fases.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';

const DOCUMENTOS: any = {
  'certificado-estudiante': {
    key: 'certificado-estudiante',
    titulo: 'Certificado de Estudiante Activo',
    descripcion: 'Expedido por Registro y Control',
  },
  'recibo-pago': {
    key: 'recibo-pago',
    titulo: 'Recibo de Pago de la Opción de Grado',
    descripcion: 'Validado por Tesorería de la institución',
  },
  'record-academico': {
    key: 'record-academico',
    titulo: 'Récord Académico',
    descripcion: 'Expedido por Registro y Control',
  },
  'formato-inscripcion': {
    key: 'formato-inscripcion',
    titulo: 'Formato de Inscripción de la Opción de Grado',
    descripcion: 'Formato de inscripción de la opción de grado',
  },
};

@Component({
  components: { MenuLateral },
})
export default class DocumentoPropuesta extends mixins(JhiDataUtils) {
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('adjuntoProyectoFaseService') private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject('fasesService') private fasesService: () => FasesService;
  @Inject('alertService') private alertService: () => AlertService;

  public adjuntoProyectoFase: IAdjuntoProyectoFase = new AdjuntoProyectoFase();
  public fase: IFases = new Fases();
  public faseFase = 'Documentos';
  public faseId: number;
  public proyecto: IProyecto = new Proyecto();
  public proyId: string = null;
  public tipo: string = null;
  public isSaving = false;

  get documento(): any {
    return DOCUMENTOS[this.tipo] || { titulo: 'Documento', descripcion: '' };
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.tipo = vm.resolveTipo(to.path);
      vm.initRelationships();
    });
  }

  resolveTipo(path: string): string {
    const match = (path || '').match(/\/propuesta\/documento\/([^/]+)/);
    return match ? match[1] : null;
  }

  volver() {
    this.$router.push({ name: 'PropuestaEnviarPropuestaView', params: { proyectoId: this.proyId } });
  }

  descargar() {
    this.adjuntoProyectoFaseService().downloadFile(this.adjuntoProyectoFase.id, this.adjuntoProyectoFase.nombreArchivoOriginal);
  }

  eliminar() {
    this.adjuntoProyectoFaseService().delete(this.adjuntoProyectoFase.id).then(() => {
      this.adjuntoProyectoFase = new AdjuntoProyectoFase();
      this.alertService().showAlert('Documento eliminado', 'info');
    });
  }

  asignarData(event, entity, field, isImage) {
    const fileData = event.target.files[0];
    entity.nombreArchivoOriginal = fileData.name;
    this.setFileData(event, entity, field, isImage);
  }

  async initRelationships() {
    this.proyId = this.$route.params.proyectoId;

    await this.proyectoService()
      .findProyectoIntegrantes(parseInt(this.proyId))
      .then(res => {
        this.proyecto = res.data;
      });

    await this.fasesService()
      .findByFase(this.faseFase)
      .then(res => {
        this.fase = res;
        this.faseId = this.fase.id;
      });

    if (!this.faseId) {
      return;
    }

    await this.adjuntoProyectoFaseService()
      .findAdjuntoProyectoFase(this.proyId, this.faseId)
      .then(res => {
        const docs: IAdjuntoProyectoFase[] = res.data || [];
        const existente = docs.find(d => d.nombreAdjunto === this.tipo);
        if (existente) {
          this.adjuntoProyectoFase = existente;
        } else {
          this.adjuntoProyectoFase = new AdjuntoProyectoFase();
        }
      });
  }

  public save(): void {
    this.isSaving = true;

    if (!this.adjuntoProyectoFase.archivo && !this.adjuntoProyectoFase.file) {
      this.isSaving = false;
      this.alertService().showAlert('Debe seleccionar un archivo', 'warning');
      return;
    }

    this.adjuntoProyectoFase.proyectoFaseProyectoId = this.proyecto.id;
    this.adjuntoProyectoFase.proyectoFaseProyectoTitulo = this.proyecto.titulo;
    this.adjuntoProyectoFase.adjuntoProyectoFaseFaseId = this.faseId;
    this.adjuntoProyectoFase.nombreAdjunto = this.tipo;
    this.adjuntoProyectoFase.fechaCreacion = new Date();

    if (this.adjuntoProyectoFase.id) {
      this.adjuntoProyectoFaseService()
        .update(this.adjuntoProyectoFase)
        .then(param => {
          this.isSaving = false;
          const message = this.$t('ciecytApp.adjuntoProyectoFase.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
          (<any>this).$router.go(0);
        });
    } else {
      this.adjuntoProyectoFaseService()
        .create(this.adjuntoProyectoFase)
        .then(param => {
          this.isSaving = false;
          const message = this.$t('ciecytApp.adjuntoProyectoFase.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
          (<any>this).$router.go(0);
        });
    }
  }

  get isDisabled() {
    return this.adjuntoProyectoFase.file != null;
  }
}
</script>

<style scoped>
</style>
