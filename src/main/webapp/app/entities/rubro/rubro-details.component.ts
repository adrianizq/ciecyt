import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRubro } from '@/shared/model/rubro.model';
import RubroService from './rubro.service';

@Component
export default class RubroDetails extends Vue {
  @Inject('rubroService') private rubroService: () => RubroService;
  public rubro: IRubro = {};

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
