import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPregunta } from '@/shared/model/pregunta.model';
import PreguntaService from './pregunta.service';

@Component
export default class PreguntaDetails extends Vue {
  @Inject('preguntaService') private preguntaService: () => PreguntaService;
  public pregunta: IPregunta = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.preguntaId) {
        vm.retrievePregunta(to.params.preguntaId);
      }
    });
  }

  public retrievePregunta(preguntaId) {
    this.preguntaService()
      .find(preguntaId)
      .then(res => {
        this.pregunta = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
