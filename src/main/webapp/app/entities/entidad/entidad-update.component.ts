import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IEntidad, Entidad } from '@/shared/model/entidad.model';
import EntidadService from './entidad.service';

const validations: any = {
  entidad: {
    entidad: {},
    nit: {},
  },
};

@Component({
  validations,
})
export default class EntidadUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('entidadService') private entidadService: () => EntidadService;
  public entidad: IEntidad = new Entidad();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entidadId) {
        vm.retrieveEntidad(to.params.entidadId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.entidad.id) {
      this.entidadService()
        .update(this.entidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.entidad.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.entidadService()
        .create(this.entidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.entidad.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveEntidad(entidadId): void {
    this.entidadService()
      .find(entidadId)
      .then(res => {
        this.entidad = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
