import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IResultadosEsperados, ResultadosEsperados } from '@/shared/model/resultados-esperados.model';
import ResultadosEsperadosService from './resultados-esperados.service';

const validations: any = {
  resultadosEsperados: {
    resultado: {},
    indicador: {},
    beneficiario: {},
    ordenVista: {},
  },
};

@Component({
  validations,
})
export default class ResultadosEsperadosUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('resultadosEsperadosService') private resultadosEsperadosService: () => ResultadosEsperadosService;
  public resultadosEsperados: IResultadosEsperados = new ResultadosEsperados();

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resultadosEsperadosId) {
        vm.retrieveResultadosEsperados(to.params.resultadosEsperadosId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.resultadosEsperados.id) {
      this.resultadosEsperadosService()
        .update(this.resultadosEsperados)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.resultadosEsperados.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.resultadosEsperadosService()
        .create(this.resultadosEsperados)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.resultadosEsperados.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveResultadosEsperados(resultadosEsperadosId): void {
    this.resultadosEsperadosService()
      .find(resultadosEsperadosId)
      .then(res => {
        this.resultadosEsperados = res;
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
