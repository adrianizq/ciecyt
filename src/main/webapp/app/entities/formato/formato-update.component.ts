import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IFormato, Formato } from '@/shared/model/formato.model';
import FormatoService from './formato.service';

const validations: any = {
  formato: {
    formato: {},
    version: {},
    codigo: {},
    fecha: {},
  },
};

@Component({
  validations,
})
export default class FormatoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('formatoService') private formatoService: () => FormatoService;
  public formato: IFormato = new Formato();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.formatoId) {
        vm.retrieveFormato(to.params.formatoId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.formato.id) {
      this.formatoService()
        .update(this.formato)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.formato.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.formatoService()
        .create(this.formato)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.formato.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveFormato(formatoId): void {
    this.formatoService()
      .find(formatoId)
      .then(res => {
        this.formato = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
