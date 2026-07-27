import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import CronogramaCiecytService from '../cronograma-ciecyt/cronograma-ciecyt.service';
import { ICronogramaCiecyt } from '@/shared/model/cronograma-ciecyt.model';

import FasesService from '../fases/fases.service';
import { IFases } from '@/shared/model/fases.model';

import AlertService from '@/shared/alert/alert.service';
import { ICronogramaCiecytFases, CronogramaCiecytFases } from '@/shared/model/cronograma-ciecyt-fases.model';
import CronogramaCiecytFasesService from './cronograma-ciecyt-fases.service';

const validations: any = {
  cronogramaCiecytFases: {
    inicioFase: {},
    finFase: {},
    textoExplicativo: {},
  },
};

@Component({
  validations,
})
export default class CronogramaCiecytFasesUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cronogramaCiecytFasesService') private cronogramaCiecytFasesService: () => CronogramaCiecytFasesService;
  public cronogramaCiecytFases: ICronogramaCiecytFases = new CronogramaCiecytFases();

  @Inject('cronogramaCiecytService') private cronogramaCiecytService: () => CronogramaCiecytService;

  public cronogramaCiecyts: ICronogramaCiecyt[] = [];

  @Inject('fasesService') private fasesService: () => FasesService;

  public fases: IFases[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cronogramaCiecytFasesId) {
        vm.retrieveCronogramaCiecytFases(to.params.cronogramaCiecytFasesId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cronogramaCiecytFases.id) {
      this.cronogramaCiecytFasesService()
        .update(this.cronogramaCiecytFases)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.cronogramaCiecytFases.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cronogramaCiecytFasesService()
        .create(this.cronogramaCiecytFases)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.cronogramaCiecytFases.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCronogramaCiecytFases(cronogramaCiecytFasesId): void {
    this.cronogramaCiecytFasesService()
      .find(cronogramaCiecytFasesId)
      .then(res => {
        this.cronogramaCiecytFases = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cronogramaCiecytService()
      .retrieve()
      .then(res => {
        this.cronogramaCiecyts = res.data;
      });
    this.fasesService()
      .retrieve()
      .then(res => {
        this.fases = res.data;
      });
  }
}
