import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICronogramaCiecytFases } from '@/shared/model/cronograma-ciecyt-fases.model';
import CronogramaCiecytFasesService from './cronograma-ciecyt-fases.service';

@Component
export default class CronogramaCiecytFasesDetails extends Vue {
  @Inject('cronogramaCiecytFasesService') private cronogramaCiecytFasesService: () => CronogramaCiecytFasesService;
  public cronogramaCiecytFases: ICronogramaCiecytFases = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cronogramaCiecytFasesId) {
        vm.retrieveCronogramaCiecytFases(to.params.cronogramaCiecytFasesId);
      }
    });
  }

  public retrieveCronogramaCiecytFases(cronogramaCiecytFasesId) {
    this.cronogramaCiecytFasesService()
      .find(cronogramaCiecytFasesId)
      .then(res => {
        this.cronogramaCiecytFases = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
