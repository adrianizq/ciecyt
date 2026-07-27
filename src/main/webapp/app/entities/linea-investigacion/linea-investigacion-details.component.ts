import { Component, Vue, Inject } from 'vue-property-decorator';

import { ILineaInvestigacion } from '@/shared/model/linea-investigacion.model';
import LineaInvestigacionService from './linea-investigacion.service';

@Component
export default class LineaInvestigacionDetails extends Vue {
  @Inject('lineaInvestigacionService') private lineaInvestigacionService: () => LineaInvestigacionService;
  public lineaInvestigacion: ILineaInvestigacion = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.lineaInvestigacionId) {
        vm.retrieveLineaInvestigacion(to.params.lineaInvestigacionId);
      }
    });
  }

  public retrieveLineaInvestigacion(lineaInvestigacionId) {
    this.lineaInvestigacionService()
      .find(lineaInvestigacionId)
      .then(res => {
        this.lineaInvestigacion = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
