import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IRubro } from '@/shared/model/rubro.model';
import RubroService from './rubro.service';

@Component
export default class RubroDetails extends Vue {
  @Inject private rubroService: () => RubroService;
  public rubro: IRubro = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rubroId) {
        vm.retrieveRubro(to.params.rubroId);
      }
    });
  }

  public retrieveRubro(rubroId) {
    this.rubroService()
      .find(rubroId)
      .then(res => {
        this.rubro = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
