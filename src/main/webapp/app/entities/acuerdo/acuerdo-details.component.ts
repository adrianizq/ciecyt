import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAcuerdo } from '@/shared/model/acuerdo.model';
import AcuerdoService from './acuerdo.service';

@Component
export default class AcuerdoDetails extends Vue {
  @Inject('acuerdoService') private acuerdoService: () => AcuerdoService;
  public acuerdo: IAcuerdo = {};

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
    this.$router.go(-1);
  }
}
