import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IFases } from '@/shared/model/fases.model';
import FasesService from './fases.service';

@Component
export default class FasesDetails extends Vue {
  @Inject private fasesService: () => FasesService;
  public fases: IFases = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fasesId) {
        vm.retrieveFases(to.params.fasesId);
      }
    });
  }

  public retrieveFases(fasesId) {
    this.fasesService()
      .find(fasesId)
      .then(res => {
        this.fases = res;
      });
  }

  public previousState() {
    this.$router.push({ name: 'Fases' });
  }
}
