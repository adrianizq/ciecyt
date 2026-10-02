import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

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
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class FasesUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private fasesService: () => FasesService;
  public fases: IFases = new Fases();

  @Inject private modalidadService: () => ModalidadService;

  public modalidads: IModalidad[] = [];
  public isSaving = false;

  @Hook
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
