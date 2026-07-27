import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IGrupoSemillero, GrupoSemillero } from '@/shared/model/grupo-semillero.model';
import GrupoSemilleroService from './grupo-semillero.service';

const validations: any = {
  grupoSemillero: {
    nombre: {},
    tipo: {},
  },
};

@Component({
  validations,
})
export default class GrupoSemilleroUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('grupoSemilleroService') private grupoSemilleroService: () => GrupoSemilleroService;
  public grupoSemillero: IGrupoSemillero = new GrupoSemillero();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.grupoSemilleroId) {
        vm.retrieveGrupoSemillero(to.params.grupoSemilleroId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.grupoSemillero.id) {
      this.grupoSemilleroService()
        .update(this.grupoSemillero)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.grupoSemillero.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.grupoSemilleroService()
        .create(this.grupoSemillero)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.grupoSemillero.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveGrupoSemillero(grupoSemilleroId): void {
    this.grupoSemilleroService()
      .find(grupoSemilleroId)
      .then(res => {
        this.grupoSemillero = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
