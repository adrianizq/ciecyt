import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { ICronograma, Cronograma } from '@/shared/model/cronograma.model';
import CronogramaService from './cronograma.service';

const validations: any = {
  cronograma: {
    actividad: {},
    duracion: {},
    fechaInicio: {},
    fechaFin: {},
    ordenVista: {},
  },
};

@Component({
  validations,
})
export default class CronogramaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('cronogramaService') private cronogramaService: () => CronogramaService;
  public cronograma: ICronograma = new Cronograma();

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cronogramaId) {
        vm.retrieveCronograma(to.params.cronogramaId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.cronograma.id) {
      this.cronogramaService()
        .update(this.cronograma)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.cronograma.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.cronogramaService()
        .create(this.cronograma)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.cronograma.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveCronograma(cronogramaId): void {
    this.cronogramaService()
      .find(cronogramaId)
      .then(res => {
        this.cronograma = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
