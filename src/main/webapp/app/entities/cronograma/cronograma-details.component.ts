import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { ICronograma } from '@/shared/model/cronograma.model';
import CronogramaService from './cronograma.service';

@Component
export default class CronogramaDetails extends Vue {
  @Inject private cronogramaService: () => CronogramaService;
  public cronograma: ICronograma = {};

  @Hook
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
    this.$router.push({ name: 'Cronograma' });
  }
}
