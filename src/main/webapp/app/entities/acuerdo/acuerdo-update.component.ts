import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IAcuerdo, Acuerdo } from '@/shared/model/acuerdo.model';
import AcuerdoService from './acuerdo.service';

const validations: any = {
  acuerdo: {
    acuerdo: {},
    version: {},
    codigo: {},
    fecha: {},
  },
};

@Component({
  validations,
})
export default class AcuerdoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('acuerdoService') private acuerdoService: () => AcuerdoService;
  public acuerdo: IAcuerdo = new Acuerdo();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.acuerdoId) {
        vm.retrieveAcuerdo(to.params.acuerdoId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.acuerdo.id) {
      this.acuerdoService()
        .update(this.acuerdo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.acuerdo.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.acuerdoService()
        .create(this.acuerdo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.acuerdo.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveAcuerdo(acuerdoId): void {
    this.acuerdoService()
      .find(acuerdoId)
      .then(res => {
        this.acuerdo = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
