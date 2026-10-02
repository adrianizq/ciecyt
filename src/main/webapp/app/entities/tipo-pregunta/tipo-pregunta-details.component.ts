import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { ITipoPregunta } from '@/shared/model/tipo-pregunta.model';
import TipoPreguntaService from './tipo-pregunta.service';

@Component
export default class TipoPreguntaDetails extends Vue {
  @Inject private tipoPreguntaService: () => TipoPreguntaService;
  public tipoPregunta: ITipoPregunta = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoPreguntaId) {
        vm.retrieveTipoPregunta(to.params.tipoPreguntaId);
      }
    });
  }

  public retrieveTipoPregunta(tipoPreguntaId) {
    this.tipoPreguntaService()
      .find(tipoPreguntaId)
      .then(res => {
        this.tipoPregunta = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
