import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { ICategorizacion } from '@/shared/model/categorizacion.model';
import CategorizacionService from './categorizacion.service';

@Component
export default class CategorizacionDetails extends Vue {
  @Inject private categorizacionService: () => CategorizacionService;
  public categorizacion: ICategorizacion = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.categorizacionId) {
        vm.retrieveCategorizacion(to.params.categorizacionId);
      }
    });
  }

  public retrieveCategorizacion(categorizacionId) {
    this.categorizacionService()
      .find(categorizacionId)
      .then(res => {
        this.categorizacion = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
