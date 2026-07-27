import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAdjuntoRetroalimentacion } from '@/shared/model/adjunto-retroalimentacion.model';
import AdjuntoRetroalimentacionService from './adjunto-retroalimentacion.service';

@Component
export default class AdjuntoRetroalimentacionDetails extends Vue {
  @Inject('adjuntoRetroalimentacionService') private adjuntoRetroalimentacionService: () => AdjuntoRetroalimentacionService;
  public adjuntoRetroalimentacion: IAdjuntoRetroalimentacion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adjuntoRetroalimentacionId) {
        vm.retrieveAdjuntoRetroalimentacion(to.params.adjuntoRetroalimentacionId);
      }
    });
  }

  public retrieveAdjuntoRetroalimentacion(adjuntoRetroalimentacionId) {
    this.adjuntoRetroalimentacionService()
      .find(adjuntoRetroalimentacionId)
      .then(res => {
        this.adjuntoRetroalimentacion = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
