import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { ICronogramaCiecytFases } from '@/shared/model/cronograma-ciecyt-fases.model';
import CronogramaCiecytFasesService from './cronograma-ciecyt-fases.service';

@Component
export default class CronogramaCiecytFasesDetails extends Vue {
  @Inject private cronogramaCiecytFasesService: () => CronogramaCiecytFasesService;
  public cronogramaCiecytFases: ICronogramaCiecytFases = {};

  @Hook
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
