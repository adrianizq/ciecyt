import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IInvestigacionTipo } from '@/shared/model/investigacion-tipo.model';
import InvestigacionTipoService from './investigacion-tipo.service';

@Component
export default class InvestigacionTipoDetails extends Vue {
  @Inject private investigacionTipoService: () => InvestigacionTipoService;
  public investigacionTipo: IInvestigacionTipo = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.investigacionTipoId) {
        vm.retrieveInvestigacionTipo(to.params.investigacionTipoId);
      }
    });
  }

  public retrieveInvestigacionTipo(investigacionTipoId) {
    this.investigacionTipoService()
      .find(investigacionTipoId)
      .then(res => {
        this.investigacionTipo = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
