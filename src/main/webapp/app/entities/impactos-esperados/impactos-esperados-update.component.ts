import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

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
  validations,
})
export default class ImpactosEsperadosUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('impactosEsperadosService') private impactosEsperadosService: () => ImpactosEsperadosService;
  public impactosEsperados: IImpactosEsperados = new ImpactosEsperados();

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

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
        });
    } else {
      this.impactosEsperadosService()
        .create(this.impactosEsperados)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.impactosEsperados.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
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
