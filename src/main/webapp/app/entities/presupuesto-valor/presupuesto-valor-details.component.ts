import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPresupuestoValor } from '@/shared/model/presupuesto-valor.model';
import PresupuestoValorService from './presupuesto-valor.service';

@Component
export default class PresupuestoValorDetails extends Vue {
  @Inject('presupuestoValorService') private presupuestoValorService: () => PresupuestoValorService;
  public presupuestoValor: IPresupuestoValor = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.presupuestoValorId) {
        vm.retrievePresupuestoValor(to.params.presupuestoValorId);
      }
    });
  }

  public retrievePresupuestoValor(presupuestoValorId) {
    this.presupuestoValorService()
      .find(presupuestoValorId)
      .then(res => {
        this.presupuestoValor = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
