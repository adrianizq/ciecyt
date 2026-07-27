import { Component, Vue, Inject } from 'vue-property-decorator';

import { IInvestigacionTipo } from '@/shared/model/investigacion-tipo.model';
import InvestigacionTipoService from './investigacion-tipo.service';

@Component
export default class InvestigacionTipoDetails extends Vue {
  @Inject('investigacionTipoService') private investigacionTipoService: () => InvestigacionTipoService;
  public investigacionTipo: IInvestigacionTipo = {};

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
        console.log(res);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
