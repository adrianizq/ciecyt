import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ModalidadService from '../modalidad/modalidad.service';
import { IModalidad } from '@/shared/model/modalidad.model';

import AlertService from '@/shared/alert/alert.service';
import { IFases, Fases } from '@/shared/model/fases.model';
import FasesService from './fases.service';

const validations: any = {
  fases: {
    fase: {},
    notificable: {},
  },
};

@Component({
  validations,
})
export default class FasesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('fasesService') private fasesService: () => FasesService;
  public fases: IFases = new Fases();

  @Inject('modalidadService') private modalidadService: () => ModalidadService;

  public modalidads: IModalidad[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fasesId) {
        vm.retrieveFases(to.params.fasesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.fases.id) {
      this.fasesService()
        .update(this.fases)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.fases.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.fasesService()
        .create(this.fases)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.fases.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveFases(fasesId): void {
    this.fasesService()
      .find(fasesId)
      .then(res => {
        this.fases = res;
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
