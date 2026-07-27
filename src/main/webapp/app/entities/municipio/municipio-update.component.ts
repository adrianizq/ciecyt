import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IMunicipio, Municipio } from '@/shared/model/municipio.model';
import MunicipioService from './municipio.service';

const validations: any = {
  municipio: {
    region: {},
    codigoDaneDepartamento: {},
    departamento: {},
    codigoDaneMunicipio: {},
    municipio: {},
  },
};

@Component({
  validations,
})
export default class MunicipioUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('municipioService') private municipioService: () => MunicipioService;
  public municipio: IMunicipio = new Municipio();
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.municipioId) {
        vm.retrieveMunicipio(to.params.municipioId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.municipio.id) {
      this.municipioService()
        .update(this.municipio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.municipio.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.municipioService()
        .create(this.municipio)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.municipio.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMunicipio(municipioId): void {
    this.municipioService()
      .find(municipioId)
      .then(res => {
        this.municipio = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
