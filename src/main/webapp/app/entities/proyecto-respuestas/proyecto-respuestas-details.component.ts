import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProyectoRespuestas } from '@/shared/model/proyecto-respuestas.model';
import ProyectoRespuestasService from './proyecto-respuestas.service';

@Component
export default class ProyectoRespuestasDetails extends Vue {
  @Inject('proyectoRespuestasService') private proyectoRespuestasService: () => ProyectoRespuestasService;
  public proyectoRespuestas: IProyectoRespuestas = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoRespuestasId) {
        vm.retrieveProyectoRespuestas(to.params.proyectoRespuestasId);
      }
    });
  }

  public retrieveProyectoRespuestas(proyectoRespuestasId) {
    this.proyectoRespuestasService()
      .find(proyectoRespuestasId)
      .then(res => {
        this.proyectoRespuestas = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
