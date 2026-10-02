import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';
import { mixins } from 'vue-facing-decorator';

import { IAdjuntoProyectoFase } from '@/shared/model/adjunto-proyecto-fase.model';
import AdjuntoProyectoFaseService from './adjunto-proyecto-fase.service';
import JhiDataUtils from '@/shared/data/data-utils.service';

@Component
export default class AdjuntoProyectoFaseDetails extends mixins(JhiDataUtils) {
  @Inject private adjuntoProyectoFaseService: () => AdjuntoProyectoFaseService;
  public adjuntoProyectoFase: IAdjuntoProyectoFase = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.adjuntoProyectoFaseId) {
        vm.retrieveAdjuntoProyectoFase(to.params.adjuntoProyectoFaseId);
      }
    });
  }

  public retrieveAdjuntoProyectoFase(adjuntoProyectoFaseId) {
    this.adjuntoProyectoFaseService()
      .find(adjuntoProyectoFaseId)
      .then(res => {
        this.adjuntoProyectoFase = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
