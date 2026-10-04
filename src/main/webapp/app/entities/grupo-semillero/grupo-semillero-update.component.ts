import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

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
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class GrupoSemilleroUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private grupoSemilleroService: () => GrupoSemilleroService;
  public grupoSemillero: IGrupoSemillero = new GrupoSemillero();
  public isSaving = false;

  @Hook
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
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.grupoSemilleroService()
        .create(this.grupoSemillero)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.grupoSemillero.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
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
