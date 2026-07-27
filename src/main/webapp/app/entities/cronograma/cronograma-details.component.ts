import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICronograma } from '@/shared/model/cronograma.model';
import CronogramaService from './cronograma.service';

@Component
export default class CronogramaDetails extends Vue {
  @Inject('cronogramaService') private cronogramaService: () => CronogramaService;
  public cronograma: ICronograma = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cronogramaId) {
        vm.retrieveCronograma(to.params.cronogramaId);
      }
    });
  }

  public retrieveCronograma(cronogramaId) {
    this.cronogramaService()
      .find(cronogramaId)
      .then(res => {
        this.cronograma = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
