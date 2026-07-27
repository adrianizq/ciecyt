import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRetroalimentacion } from '@/shared/model/retroalimentacion.model';
import RetroalimentacionService from './retroalimentacion.service';

@Component
export default class RetroalimentacionDetails extends Vue {
  @Inject('retroalimentacionService') private retroalimentacionService: () => RetroalimentacionService;
  public retroalimentacion: IRetroalimentacion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.retroalimentacionId) {
        vm.retrieveRetroalimentacion(to.params.retroalimentacionId);
      }
    });
  }

  public retrieveRetroalimentacion(retroalimentacionId) {
    this.retroalimentacionService()
      .find(retroalimentacionId)
      .then(res => {
        this.retroalimentacion = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
