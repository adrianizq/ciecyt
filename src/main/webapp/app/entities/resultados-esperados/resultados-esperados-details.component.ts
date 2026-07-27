import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResultadosEsperados } from '@/shared/model/resultados-esperados.model';
import ResultadosEsperadosService from './resultados-esperados.service';

@Component
export default class ResultadosEsperadosDetails extends Vue {
  @Inject('resultadosEsperadosService') private resultadosEsperadosService: () => ResultadosEsperadosService;
  public resultadosEsperados: IResultadosEsperados = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.resultadosEsperadosId) {
        vm.retrieveResultadosEsperados(to.params.resultadosEsperadosId);
      }
    });
  }

  public retrieveResultadosEsperados(resultadosEsperadosId) {
    this.resultadosEsperadosService()
      .find(resultadosEsperadosId)
      .then(res => {
        this.resultadosEsperados = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
