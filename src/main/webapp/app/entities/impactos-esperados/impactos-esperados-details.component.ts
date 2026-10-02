import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IImpactosEsperados } from '@/shared/model/impactos-esperados.model';
import ImpactosEsperadosService from './impactos-esperados.service';

@Component
export default class ImpactosEsperadosDetails extends Vue {
  @Inject private impactosEsperadosService: () => ImpactosEsperadosService;
  public impactosEsperados: IImpactosEsperados = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.impactosEsperadosId) {
        vm.retrieveImpactosEsperados(to.params.impactosEsperadosId);
      }
    });
  }

  public retrieveImpactosEsperados(impactosEsperadosId) {
    this.impactosEsperadosService()
      .find(impactosEsperadosId)
      .then(res => {
        this.impactosEsperados = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
