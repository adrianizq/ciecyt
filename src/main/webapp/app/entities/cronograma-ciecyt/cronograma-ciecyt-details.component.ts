import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICronogramaCiecyt } from '@/shared/model/cronograma-ciecyt.model';
import CronogramaCiecytService from './cronograma-ciecyt.service';

@Component
export default class CronogramaCiecytDetails extends Vue {
  @Inject('cronogramaCiecytService') private cronogramaCiecytService: () => CronogramaCiecytService;
  public cronogramaCiecyt: ICronogramaCiecyt = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cronogramaCiecytId) {
        vm.retrieveCronogramaCiecyt(to.params.cronogramaCiecytId);
      }
    });
  }

  public retrieveCronogramaCiecyt(cronogramaCiecytId) {
    this.cronogramaCiecytService()
      .find(cronogramaCiecytId)
      .then(res => {
        this.cronogramaCiecyt = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
