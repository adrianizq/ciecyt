import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ModalidadService from '../modalidad/modalidad.service';
import { IModalidad } from '@/shared/model/modalidad.model';

import AlertService from '@/shared/alert/alert.service';
import { ICronogramaCiecyt, CronogramaCiecyt } from '@/shared/model/cronograma-ciecyt.model';
import CronogramaCiecytService from './cronograma-ciecyt.service';

const validations: any = {
  cronogramaCiecyt: {
    tituloCronograma: {},
    fechaInicio: {},
    fechaFin: {},
    observaciones: {},
  },
};

@Component({
  validations,
})
export default class CronogramaCiecytUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cronogramaCiecytService') private cronogramaCiecytService: () => CronogramaCiecytService;
  public cronogramaCiecyt: ICronogramaCiecyt = new CronogramaCiecyt();

  @Inject('modalidadService') private modalidadService: () => ModalidadService;

  public modalidads: IModalidad[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cronogramaCiecytId) {
        vm.retrieveCronogramaCiecyt(to.params.cronogramaCiecytId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cronogramaCiecyt.id) {
      this.cronogramaCiecytService()
        .update(this.cronogramaCiecyt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.cronogramaCiecyt.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cronogramaCiecytService()
        .create(this.cronogramaCiecyt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.cronogramaCiecyt.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCronogramaCiecyt(cronogramaCiecytId): void {
    this.cronogramaCiecytService()
      .find(cronogramaCiecytId)
      .then(res => {
        this.cronogramaCiecyt = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.modalidadService()
      .retrieve()
      .then(res => {
        this.modalidads = res.data;
      });
  }
}
