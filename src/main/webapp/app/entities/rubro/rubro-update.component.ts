import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IRubro, Rubro } from '@/shared/model/rubro.model';
import RubroService from './rubro.service';

const validations: any = {
  rubro: {
    rubro: {},
  },
};

@Component({
  validations,
})
export default class RubroUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rubroService') private rubroService: () => RubroService;
  public rubro: IRubro = new Rubro();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rubroId) {
        vm.retrieveRubro(to.params.rubroId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.rubro.id) {
      this.rubroService()
        .update(this.rubro)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.rubro.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rubroService()
        .create(this.rubro)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.rubro.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRubro(rubroId): void {
    this.rubroService()
      .find(rubroId)
      .then(res => {
        this.rubro = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
