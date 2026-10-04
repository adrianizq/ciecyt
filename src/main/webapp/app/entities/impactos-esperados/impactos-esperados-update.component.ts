import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IImpactosEsperados, ImpactosEsperados } from '@/shared/model/impactos-esperados.model';
import ImpactosEsperadosService from './impactos-esperados.service';

const validations: any = {
  impactosEsperados: {
    impacto: {},
    plazo: {},
    indicador: {},
    supuestos: {},
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
export default class ImpactosEsperadosUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private impactosEsperadosService: () => ImpactosEsperadosService;
  public impactosEsperados: IImpactosEsperados = new ImpactosEsperados();

  @Inject private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.impactosEsperadosId) {
        vm.retrieveImpactosEsperados(to.params.impactosEsperadosId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.impactosEsperados.id) {
      this.impactosEsperadosService()
        .update(this.impactosEsperados)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.impactosEsperados.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.impactosEsperadosService()
        .create(this.impactosEsperados)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.impactosEsperados.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    }
  }

  public retrieveImpactosEsperados(impactosEsperadosId): void {
    this.impactosEsperadosService()
      .find(impactosEsperadosId)
      .then(res => {
        this.impactosEsperados = res;
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
