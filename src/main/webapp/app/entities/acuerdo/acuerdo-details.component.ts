import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IAcuerdo } from '@/shared/model/acuerdo.model';
import AcuerdoService from './acuerdo.service';

@Component
export default class AcuerdoDetails extends Vue {
  @Inject private acuerdoService: () => AcuerdoService;
  public acuerdo: IAcuerdo = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.acuerdoId) {
        vm.retrieveAcuerdo(to.params.acuerdoId);
      }
    });
  }

  public retrieveAcuerdo(acuerdoId) {
    this.acuerdoService()
      .find(acuerdoId)
      .then(res => {
        this.acuerdo = res;
      });
  }

  public previousState() {
    this.$router.push({ name: 'Acuerdo' });
  }
}
