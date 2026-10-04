import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

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
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class CronogramaCiecytUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private cronogramaCiecytService: () => CronogramaCiecytService;
  public cronogramaCiecyt: ICronogramaCiecyt = new CronogramaCiecyt();

  @Inject private modalidadService: () => ModalidadService;

  public modalidads: IModalidad[] = [];
  public isSaving = false;

  @Hook
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
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.cronogramaCiecytService()
        .create(this.cronogramaCiecyt)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.cronogramaCiecyt.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
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
