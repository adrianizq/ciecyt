import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

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
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class CronogramaUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private cronogramaService: () => CronogramaService;
  public cronograma: ICronograma = new Cronograma();

  @Inject private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  @Hook
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
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.cronogramaService()
        .create(this.cronograma)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.cronograma.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
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
