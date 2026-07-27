import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFases } from '@/shared/model/fases.model';
import FasesService from './fases.service';

@Component
export default class FasesDetails extends Vue {
  @Inject('fasesService') private fasesService: () => FasesService;
  public fases: IFases = {};

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
    this.$router.go(-1);
  }
}
