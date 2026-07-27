import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { ITipoPregunta, TipoPregunta } from '@/shared/model/tipo-pregunta.model';
import TipoPreguntaService from './tipo-pregunta.service';

const validations: any = {
  tipoPregunta: {
    tipoPregunta: {},
    tipoDato: {},
  },
};

@Component({
  validations,
})
export default class TipoPreguntaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('tipoPreguntaService') private tipoPreguntaService: () => TipoPreguntaService;
  public tipoPregunta: ITipoPregunta = new TipoPregunta();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoPreguntaId) {
        vm.retrieveTipoPregunta(to.params.tipoPreguntaId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.tipoPregunta.id) {
      this.tipoPreguntaService()
        .update(this.tipoPregunta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.tipoPregunta.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.tipoPreguntaService()
        .create(this.tipoPregunta)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.tipoPregunta.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveTipoPregunta(tipoPreguntaId): void {
    this.tipoPreguntaService()
      .find(tipoPreguntaId)
      .then(res => {
        this.tipoPregunta = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
