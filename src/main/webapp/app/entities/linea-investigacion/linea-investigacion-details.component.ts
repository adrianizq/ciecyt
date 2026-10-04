import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { ILineaInvestigacion } from '@/shared/model/linea-investigacion.model';
import LineaInvestigacionService from './linea-investigacion.service';

@Component
export default class LineaInvestigacionDetails extends Vue {
  @Inject private lineaInvestigacionService: () => LineaInvestigacionService;
  public lineaInvestigacion: ILineaInvestigacion = {};

  @Hook
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
    this.$router.push({ name: 'LineaInvestigacion' });
  }
}
