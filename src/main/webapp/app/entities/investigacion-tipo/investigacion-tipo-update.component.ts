import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IInvestigacionTipo, InvestigacionTipo } from '@/shared/model/investigacion-tipo.model';
import InvestigacionTipoService from './investigacion-tipo.service';

const validations: any = {
  investigacionTipo: {
    investigacionTipo: {},
    investigacionTipoDescripcion: {},
    tipo: {},
    tipoDescripcion: {},
  },
};

@Component({
  validations,
})
export default class InvestigacionTipoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('investigacionTipoService') private investigacionTipoService: () => InvestigacionTipoService;
  public investigacionTipo: IInvestigacionTipo = new InvestigacionTipo();

  public investigacionTips: IInvestigacionTipo[] = [];

  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.investigacionTipoId) {
        vm.retrieveInvestigacionTipo(to.params.investigacionTipoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.investigacionTipo.id) {
      this.investigacionTipoService()
        .update(this.investigacionTipo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.investigacionTipo.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.investigacionTipoService()
        .create(this.investigacionTipo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.investigacionTipo.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveInvestigacionTipo(investigacionTipoId): void {
    this.investigacionTipoService()
      .find(investigacionTipoId)
      .then(res => {
        this.investigacionTipo = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.investigacionTipoService()
      .retrieve()
      .then(res => {
        this.investigacionTips = res.data;
      });
  }
}
