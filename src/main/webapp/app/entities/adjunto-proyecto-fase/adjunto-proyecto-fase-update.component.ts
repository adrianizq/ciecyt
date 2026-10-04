import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';
import { mixins } from 'vue-facing-decorator';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

import FasesService from '../fases/fases.service';
import { IFases, Fases } from '@/shared/model/fases.model';
import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdjuntoProyectoFase, AdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import AdjuntoProyectoFaseService from './adjunto-proyecto-fase.service';

const validations: any = {
  adjuntoProyectoFase: {
    nombreAdjunto: {},
    fechaCreacion: {},
    fechaModificacion: {},
    estadoAdjunto: {},
    adjuntoProyectoFase: {},
    nombreArchivoOriginal: {},
    archivo: {},
    fechaInicio: {},
    fechaFin: {},
  },
};

@Component({
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class AdjuntoProyectoFaseUpdate extends mixins(JhiDataUtils) {
  @Inject private alertService: () => AlertService;
  @Inject private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  @Inject private fasesService: () => FasesService;
  @Inject private proyectoService: () => ProyectoService;

  public adjuntoProyectoFase: IAdjuntoProyectoFase = new AdjuntoProyectoFase();

  public fases: IFases[] = [];
  public isSaving = false;
  public proyecto: IProyecto = new Proyecto();
  public proyId: string = null;
  public modalidadId: number;

  descargar() {
    this.adjuntoProyectoFaseService().downloadFile(this.adjuntoProyectoFase.id);
  }
  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adjuntoProyectoFaseId) {
        vm.retrieveAdjuntoProyectoFase(to.params.adjuntoProyectoFaseId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.adjuntoProyectoFase.id) {
      this.adjuntoProyectoFaseService()
        .update(this.adjuntoProyectoFase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.adjuntoProyectoFase.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.adjuntoProyectoFaseService()
        .create(this.adjuntoProyectoFase)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.adjuntoProyectoFase.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    }
  }

  public retrieveAdjuntoProyectoFase(adjuntoProyectoFaseId: any): void {
    this.adjuntoProyectoFaseService()
      .find(adjuntoProyectoFaseId)
      .then(res => {
        this.adjuntoProyectoFase = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  initRelationships() {
    this.proyId = this.$route.params.proyectoId as string;

    /*let res = await this.proyectoService()
    .findProyectoIntegrantes(parseInt(this.$route.params.proyectoId))
      //.find(parseInt(this.$route.params.proyectoId))
      .then(res => {
        this.proyecto = res.data;
        this.modalidadId = this.proyecto.proyectoModalidadId;
        
      });*/

    this.fasesService()
      .retrieve()
      .then(res => {
        this.fases = res;
      });
  }
}
