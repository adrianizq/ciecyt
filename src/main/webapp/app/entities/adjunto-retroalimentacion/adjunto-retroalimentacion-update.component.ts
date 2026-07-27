import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import RetroalimentacionService from '../retroalimentacion/retroalimentacion.service';
import { IRetroalimentacion } from '@/shared/model/retroalimentacion.model';

import AlertService from '@/shared/alert/alert.service';
import { IAdjuntoRetroalimentacion, AdjuntoRetroalimentacion } from '@/shared/model/adjunto-retroalimentacion.model';
import AdjuntoRetroalimentacionService from './adjunto-retroalimentacion.service';

const validations: any = {
  adjuntoRetroalimentacion: {
    nombreAdjunto: {},
    fechaCreacion: {},
    fechaModificacion: {},
    estadoAdjunto: {},
    adjuntoRetroalimentacion: {},
    nombreArchivoOriginal: {},
    fechaInicio: {},
    fechaFin: {},
  },
};

@Component({
  validations,
})
export default class AdjuntoRetroalimentacionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('adjuntoRetroalimentacionService') private adjuntoRetroalimentacionService: () => AdjuntoRetroalimentacionService;
  public adjuntoRetroalimentacion: IAdjuntoRetroalimentacion = new AdjuntoRetroalimentacion();

  @Inject('retroalimentacionService') private retroalimentacionService: () => RetroalimentacionService;

  public retroalimentacions: IRetroalimentacion[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adjuntoRetroalimentacionId) {
        vm.retrieveAdjuntoRetroalimentacion(to.params.adjuntoRetroalimentacionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.adjuntoRetroalimentacion.id) {
      this.adjuntoRetroalimentacionService()
        .update(this.adjuntoRetroalimentacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.adjuntoRetroalimentacion.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.adjuntoRetroalimentacionService()
        .create(this.adjuntoRetroalimentacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.adjuntoRetroalimentacion.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAdjuntoRetroalimentacion(adjuntoRetroalimentacionId): void {
    this.adjuntoRetroalimentacionService()
      .find(adjuntoRetroalimentacionId)
      .then(res => {
        this.adjuntoRetroalimentacion = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.retroalimentacionService()
      .retrieve()
      .then(res => {
        this.retroalimentacions = res.data;
      });
  }
}
