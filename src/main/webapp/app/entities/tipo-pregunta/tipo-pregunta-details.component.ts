import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITipoPregunta } from '@/shared/model/tipo-pregunta.model';
import TipoPreguntaService from './tipo-pregunta.service';

@Component
export default class TipoPreguntaDetails extends Vue {
  @Inject('tipoPreguntaService') private tipoPreguntaService: () => TipoPreguntaService;
  public tipoPregunta: ITipoPregunta = {};

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
